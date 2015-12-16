package server;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.willwinder.universalgcodesender.AbstractController;
import com.willwinder.universalgcodesender.CommUtils;
import com.willwinder.universalgcodesender.FirmwareUtils;
import com.willwinder.universalgcodesender.MainWindowServer;
import com.willwinder.universalgcodesender.Settings;
import com.willwinder.universalgcodesender.i18n.Localization;

import visual.MainWindowRemoteController;


//BufferedReader entradaS = new BufferedReader(new InputStreamReader(client.getInputStream()));
//String msgS = entradaS.readLine().toString();
//System.out.println(msgS);
//entradaS.close();

@SuppressWarnings("serial")
public class ServerController extends MainWindowRemoteController {
	
	static ServerSocket server;
	static Socket client;
	public static AbstractController controller;
	private Settings settings;
	
	static int size;
	
	public static ArrayList<String> commands = new ArrayList<String>();
//	publi	c static Sender sending;
	
	public ServerController(int porta) throws IOException{
		this.setVisible(true);
		loadPortSelector();
		this.openCloseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opencloseButtonActionPerformed(e);
				
			}
		});
		this.refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadPortSelector();
				
			}
		});
		
		this.sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendButtonActionPerformed(commands);
			}
		});
		try
		{
				
		 server = new ServerSocket(porta);
		 System.out.println("Server disponível na porta: "+ porta);
		}
		catch(IOException e ){
			e.printStackTrace();
		}
		
	}
	public static void conexao() throws Exception
	{
		while(true){
			client = server.accept();
			System.out.println("Cliente conectado: "+ client.getInetAddress().getHostAddress());
			PrintStream saida = new PrintStream(client.getOutputStream());
			saida.flush();
			saida.println("Conectado!");
			//saida.close();
			//client.close();
			recebimento();
	        
		}
	}
	
	public static void recebimento() throws Exception
	{
		while(true){
			//client = server.accept();
			BufferedReader entradaS = new BufferedReader(new InputStreamReader(client.getInputStream()));
			if(entradaS.ready())
			{
				String msgS = entradaS.readLine().toString();
				System.out.println(msgS);
				
				if(msgS.equals("M")){
					autorizar_envio();
					executaComandoManual();
				}
				else if(msgS.equals("X") | msgS.equals("Y") | msgS.equals("Z")){
					autorizar_envio();
					executaReset(msgS.toCharArray()[0]);
				}
				else if(msgS.equals("reset")){
					autorizar_envio();
					executaResetCordenadas();
				}
				else if(msgS.equals("resetTo")){
					autorizar_envio();
					executaResetToCordenadas();
				}
				else if(msgS.equals("soft")){
					autorizar_envio();
					executaSoftReset();
				}
				else if(msgS.equals("s")){
					autorizar_envio();
					preparar_recebimento();
					sendButtonActionPerformed(commands);
				}
				
				
				if(msgS.equals("exit")){
					client.close();
					break;
				}
			}
//			if(!client.isConnected()){
//				client.close();
//				entradaS.close();
//				System.out.println("saí");
//				break;
//			}
		}
	}
	
	private static void executaSoftReset() throws Exception {
		controller.issueSoftReset();
	}
	private static void executaResetToCordenadas() throws Exception {
        controller.returnToHome();
        // The return to home command uses G91 to lift the tool.
        //G91Mode = true;
		
	}
	private static void executaResetCordenadas() throws Exception {
		controller.resetCoordinatesToZero();
	}
	public static void autorizar_envio() throws IOException{
		PrintStream saida = new PrintStream(client.getOutputStream());
		saida.flush();
		saida.println("ok");
	}
	
	public static void preparar_recebimento() throws IOException{
			BufferedReader entradaT = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(!entradaT.ready());
			String msgS = entradaT.readLine().toString();
			size = Integer.parseInt(msgS);
			autorizar_envio();
			System.out.println(size);	
			for(int i = 0 ; i < size ; i++){
				BufferedReader entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
				while(!entrada.ready());
				String msg = entradaT.readLine().toString();
				System.out.println(msg);
				autorizar_envio();
				commands.add(msg);
			}	
	}
	
	private void opencloseButtonActionPerformed(java.awt.event.ActionEvent evt){
		if( this.openCloseButton.getText().equalsIgnoreCase("open") ) {
            // Hook the view up to the model
            String firmware = this.firmWareTextField.getText().toString();
            this.controller = FirmwareUtils.getControllerFor(firmware);
            //applySettingsToController(this.controller);           
            Boolean ret = openCommConnection();
            this.openCloseButton.setText("close");
		}
		else {
            this.closeCommConnection();
            
        }
	}
		private boolean openCommConnection() {
	        boolean connected = false;
	        try {	     
	            String port = commPortComboBox.getSelectedItem().toString();
	            int portRate = Integer.parseInt(baudComboBox.getSelectedItem().toString());
	            connected = controller.openCommPort(port, portRate);

	        } catch (PortInUseException e) {
	            
	            
	            //Localization.getString("")
	            StringBuilder message = new StringBuilder()
	                    .append(Localization.getString("mainWindow.error.rxtx"))
	                    .append("(")
	                    .append(e.getClass().getName())
	                    .append("): ")
	                    .append(e.getMessage()).append("\n\n")
	            
	                    .append(Localization.getString("mainWindow.error.rxtxMac1"))
	                    .append(String.format(Localization.getString("mainWindow.error.rxtxMac2"), "\"/var/lock\""))
	                    .append("\n     sudo mkdir /var/lock")
	                    .append("\n     sudo chmod 777 /var/lock");
	        }catch (Exception e) {
	            e.printStackTrace();	  	                 
	        }
	        return connected;
	    }
	    
	    private void closeCommConnection() {
	    	this.openCloseButton.setText("open");
	        this.controller.closeCommPort();
	        this.controller = null;
	    }
		
		private void applySettingsToController(AbstractController controller) {
	        // Apply settings settings to controller.
	        if (settings.isOverrideSpeedSelected()) {
	            double value = settings.getOverrideSpeedValue();
	            controller.setSpeedOverride(value);
	        } else {
	            controller.setSpeedOverride(-1);
	        }

	        try {
	            controller.setMaxCommandLength(settings.getMaxCommandLength());
	            controller.setTruncateDecimalLength(settings.getTruncateDecimalLength());
	            controller.setSingleStepMode(settings.isSingleStepMode());
	            controller.setStatusUpdatesEnabled(settings.isStatusUpdatesEnabled());
	            controller.setStatusUpdateRate(settings.getStatusUpdateRate());
	            controller.setRemoveAllWhitespace(settings.isRemoveAllWhitespace());
	            controller.setConvertArcsToLines(settings.isConvertArcsToLines());
	            controller.setSmallArcThreshold(settings.getSmallArcThreshold());
	            controller.setSmallArcSegmentLength(settings.getSmallArcSegmentLength());
	        } catch (Exception ex) {

	            StringBuilder message = new StringBuilder()
	                    .append(Localization.getString("mainWindow.error.firmwareSetting"))
	                    .append(": \n    ")
	                    .append(Localization.getString("firmware.feature.maxCommandLength")).append("\n    ")
	                    .append(Localization.getString("firmware.feature.truncateDecimal")).append("\n    ")
	                    .append(Localization.getString("firmware.feature.singleStep")).append("\n    ")
	                    .append(Localization.getString("firmware.feature.removeWhitespace")).append("\n    ")
	                    .append(Localization.getString("firmware.feature.linesToArc")).append("\n    ")
	                    .append(Localization.getString("firmware.feature.statusUpdates")).append("\n    ")
	                    .append(Localization.getString("firmware.feature.statusUpdateRate"));	            
	      
	        }
	    }	
	
	
	public static void executaComandoManual() throws Exception{
		BufferedReader entradaT = new BufferedReader(new InputStreamReader(client.getInputStream()));
		while(!entradaT.ready());
		String msgS = entradaT.readLine().toString();
		autorizar_envio();
		controller.queueStringForComm(msgS);
	}
	public static void executaReset(char eixo) throws Exception{
		controller.resetCoordinateToZero(eixo);
	}
	
    private void loadPortSelector() {
        commPortComboBox.removeAllItems();
        
        List<CommPortIdentifier> portList = CommUtils.getSerialPortList();

        if (portList.size() < 1) {
            //this.displayErrorDialog(Localization.getString("mainWindow.error.noSerialPort"));
        } else {
            // Sort?
            //java.util.Collections.sort(portList);

            java.util.Iterator<CommPortIdentifier> portIter = portList.iterator();

            while ( portIter.hasNext() ) {
                CommPortIdentifier portIdentifier = portIter.next();
                commPortComboBox.addItem(portIdentifier.getName());
            }

            commPortComboBox.setSelectedIndex(0);
        }
    }
    
    
	 public static void sendButtonActionPerformed(List<String> commands){
		 try {
	            if (true) {
	                controller.preprocessAndAppendGcodeCommand("G90");
	            }
	
	            controller.appendGcodeCommands(commands, null);

	            controller.beginStreaming();
	        } catch (Exception e) {
	       
	                     e.printStackTrace();
	           
	        }
 	}
	
	
	public static void main(String[] args) throws Exception 
	{
		ServerController server = new ServerController(12346);
		conexao();
		

	}

}

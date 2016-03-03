package server;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import threads.TrataClients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
	
	private ServerSocket server;
	private Socket client;
	public AbstractController controller;
	private Settings settings;
	
	public  ArrayList<String> commands = new ArrayList<String>();
	public TrataClients tratador;
	
	public ServerController(int porta) throws IOException{
		this.setVisible(true);
		loadPortSelector();
		textPane1.setText("Server Controller initialized with IP " + getMyIp() + " and port " + porta);
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
		 System.out.println("Server dispon�vel na porta: "+ porta);
		}
		catch(IOException e ){
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(ServerController.EXIT_ON_CLOSE); 
	}
	public void conexao()
	{
		while(true){
			try {
				client = server.accept();
				System.out.println("Cliente conectado: "+ client.getInetAddress().getHostAddress());
				PrintStream saida = new PrintStream(client.getOutputStream());
				saida.flush();
				saida.println("Connected!");
				textPane1.setText(textPane1.getText() + "\nConection with remote client: " +client.getInetAddress().getHostAddress() );
				tratador = new TrataClients(server);
				tratador.start();
			} 
			catch (IOException e) {
				e.printStackTrace();
	            textPane1.setText(textPane1.getText() + "\nDisconnected from remote client");
			}	        
			try {
				if(!client.isClosed()){				
					recebimento();				
				}
			} catch (Exception e) {
				e.printStackTrace();
	            textPane1.setText(textPane1.getText() + "\nDisconnected from remote client");
			}
		}
	}
	
	public void recebimento() throws Exception
	{
		while(true){
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
					tratador.interrupt();
					server.setSoTimeout(0);
					break;
				}
			}
		}
	}
	
	private void executaSoftReset() throws Exception {
		controller.issueSoftReset();
	}
	private void executaResetToCordenadas() throws Exception {
        controller.returnToHome();
        // The return to home command uses G91 to lift the tool.
        //G91Mode = true;
		
	}
	private void executaResetCordenadas() throws Exception {
		controller.resetCoordinatesToZero();
	}
	public void autorizar_envio() throws IOException{
		PrintStream saida = new PrintStream(client.getOutputStream());
		saida.flush();
		saida.println("ok");
	}
	
	@SuppressWarnings("unchecked")
	public void preparar_recebimento() throws IOException{
			commands.removeAll(commands);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(!entrada.ready());
			Object obj = (Object)entrada.readLine();
			autorizar_envio();
			String temp =  obj.toString();
			temp = temp.substring(1,temp.length() - 1);
			String [] tempList = temp.split(",");
			for(int i = 0 ; i< tempList.length; i++)
			{
				commands.add(tempList[i]);
				System.out.println(tempList[i]);
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
	            textPane1.setText(textPane1.getText() + "\nConnected with device on port " + port + " with success!");
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
	            textPane1.setText(textPane1.getText() + "\nOpening error");
	        }catch (Exception e) {
	            e.printStackTrace();	  
	            textPane1.setText(textPane1.getText() + "\nOpening error");
	        }
	        return connected;
	    }
	    
	    private void closeCommConnection() {
	    	this.openCloseButton.setText("open");
	        this.controller.closeCommPort();
	        this.controller = null;
            textPane1.setText(textPane1.getText() + "\nConnection with device closed");
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
	
	
	public void executaComandoManual() throws Exception{
		BufferedReader entradaT = new BufferedReader(new InputStreamReader(client.getInputStream()));
		while(!entradaT.ready());
		String msgS = entradaT.readLine().toString();
		autorizar_envio();
		controller.queueStringForComm(msgS);
	}
	public void executaReset(char eixo) throws Exception{
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
    
    
	 public void sendButtonActionPerformed(List<String> commands){
		 try {
			 	controller.isReadyToStreamFile();
	            if (true) {
	                controller.preprocessAndAppendGcodeCommand("G90");
	            }
	
	            controller.appendGcodeCommands(commands, null);

	            controller.beginStreaming();
	        } catch (Exception e) {
	       
	                     e.printStackTrace();
	           
	        }
 	}
	/**
	 *  By Jc
	 *  pega o IP local
	 * @return
	 */
	 public String getMyIp()
	{
		String myIp;
		InetAddress ia = null;  
	   	try {  
	   	    ia = InetAddress.getLocalHost();  
	   	} catch (UnknownHostException e) 
	   	{  
	   	    e.printStackTrace();  
	   	}  
	   	myIp = ia.getHostAddress();
	   	return myIp;
	}
	
	public static void main(String[] args) throws Exception 
	{
		ServerController server = new ServerController(12346);
		server.conexao();
	}
}

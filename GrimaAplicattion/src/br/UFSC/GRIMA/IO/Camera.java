package br.UFSC.GRIMA.IO;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.us.sosia.video.stream.agent.StreamClient;
import main.java.us.sosia.video.stream.agent.StreamClientAgent;
import main.java.us.sosia.video.stream.agent.ui.SingleVideoDisplayWindow;
import main.java.us.sosia.video.stream.agent.ui.VideoPanel;
import main.java.us.sosia.video.stream.handler.StreamFrameListener;
import br.UFSC.GRIMA.dataStructure.Device;

public class Camera implements ActionListener, StreamFrameListener {
	private String name;
	private String compactName;
	private int port;
	private Device device;
	private ClientCamera clientCamera;
	private JMenuItem menuItem;
	private volatile long count = 0;
	/////////viewDevicesComponents///////////
	private JComboBox<String> deviceCombobox;
	private JButton viewButton;
	private JMenuItem viewMenu;
	////////viewComponents////
	private Logger logger;
	private StreamClientAgent clientAgent;
	public static Dimension defaultCameraDimension = new Dimension(640, 480);
	private VideoPanel videoPanel;
	private JFrame videoFrame;
	private VideoPanel videoFramePanel;
	
/////////////////////////////////////////Constructor//////////////////////////////////////////////////////////////
	public Camera(String name, int port, ClientCamera clientCamera) {
		/////////adjusting webCam's name/////
		String[] newName = name.split(" ");
		String result = "";
		for(int i = 0; i < newName.length - 1; i++) {
			result = result + newName[i] + " " ;
		}
		if(result.length() > 1)
			result = result.substring(0, result.length() - 1);
		setName(result);
		if(result.length() > 9) {
			result = result.substring(0, 9) + "...";
		}
		setCompactName(result);
		setPort(port);
		setClientCamera(clientCamera);
		logger = LoggerFactory.getLogger(StreamClient.class);
	}
/////////////////////////////////////////Methods////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		if (deviceCombobox != null) {
			if(e.getSource().equals(deviceCombobox)) {
				if(deviceCombobox.getSelectedIndex() > 0) {
					Device device = clientCamera.getIoControl().getController().getAllDevices().get(deviceCombobox.getSelectedIndex() - 1);
					if(getDevice() != null) {
						if(device.getCameras().contains(this))
							device.getCameras().remove(this);
					}
					setDevice(device);
					if(!device.getCameras().contains(this))
						device.getCameras().add(this);
				}
				else {
					if(device != null) {
						if(device.getCameras().contains(this))
							device.getCameras().remove(this);
						setDevice(null);
					}
				}
				clientCamera.getIoControl().getController().getMainInterface().getViewDevicesEvents().setDevices();
			}
		}
		if(viewButton != null) {
			if(e.getSource().equals(viewButton))
				adjustVideoFrame();
		}
		if(viewMenu != null) {
			if(e.getSource().equals(viewMenu))
				adjustVideoFrame();
		}
	}
	@Override
	public void onFrameReceived(BufferedImage image) {
		// TODO Auto-generated method stub
		logger.info("frame received :{}",count++);
		if(videoFramePanel != null)
			videoFramePanel.updateImage(image);
		if (videoPanel != null) 
			videoPanel.updateImage(image);
		if((videoPanel == null)&&(videoFrame == null)) {
			StreamClientAgent clientAgent = getClientAgent();
			setClientAgent(null);
			clientAgent.stop();
		}
	}
	public void adjustVideoFrame() {
		if (videoFrame != null) {
			videoFrame.setVisible(true);
			videoFrame.toFront();
		}
		else {
			setVideoFrame(new JFrame(name));
			adjustClientAgent();
			//setup the videoWindow
			setVideoFramePanel(new VideoPanel());
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			((GridBagLayout)panel.getLayout()).columnWidths = new int[] {0, 0, 0};
			((GridBagLayout)panel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
			((GridBagLayout)panel.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
			panel.add(videoFramePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			videoFrame.add(panel);
			videoFrame.setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/cameraIcon.png")).getImage());
			videoFrame.setSize(defaultCameraDimension);
			videoFrame.addWindowListener(new WindowAdapter(){
				 public void windowClosing(WindowEvent e) {
					 videoFrame.dispose();
					 videoFramePanel.close();
					 setVideoFramePanel(null);
					 setVideoFrame(null);
			     }
			});
			videoFrame.setVisible(true);
		}
	}
	public void adjustVideoPanel() {
		adjustClientAgent();
		setVideoPanel(new VideoPanel());
	}
	public void destroyVideoPanel() {
		if(videoPanel != null) {
			videoPanel.close();
			setVideoPanel(null);
		}
	}
	public void adjustClientAgent() {
		if(this.clientAgent == null) {
//			logger = LoggerFactory.getLogger(StreamClient.class);
			logger.info("setup dimension :{}",defaultCameraDimension);
			setClientAgent(new StreamClientAgent(this, defaultCameraDimension));
			clientAgent.connect(new InetSocketAddress(clientCamera.getIp(), port));
		}
	}
/////////////////////////////////////////Getters and Setters/////////////////////////////////////////////////////
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public JMenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(JMenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public ClientCamera getClientCamera() {
		return clientCamera;
	}
	public void setClientCamera(ClientCamera clientCamera) {
		this.clientCamera = clientCamera;
	}
	public JComboBox<String> getDeviceCombobox() {
		return deviceCombobox;
	}
	public void setDeviceCombobox(JComboBox<String> deviceCombobox) {
		this.deviceCombobox = deviceCombobox;
	}
	public JButton getViewButton() {
		return viewButton;
	}
	public void setViewButton(JButton viewButton) {
		this.viewButton = viewButton;
	}
	public JMenuItem getViewMenu() {
		return viewMenu;
	}
	public void setViewMenu(JMenuItem viewMenu) {
		this.viewMenu = viewMenu;
	}
	public JFrame getVideoFrame() {
		return videoFrame;
	}
	public void setVideoFrame(JFrame videoFrame) {
		this.videoFrame = videoFrame;
	}
	public VideoPanel getVideoPanel() {
		return videoPanel;
	}
	public void setVideoPanel(VideoPanel videoPanel) {
		this.videoPanel = videoPanel;
	}
	public VideoPanel getVideoFramePanel() {
		return videoFramePanel;
	}
	public void setVideoFramePanel(VideoPanel videoFramePanel) {
		this.videoFramePanel = videoFramePanel;
	}
	public StreamClientAgent getClientAgent() {
		return clientAgent;
	}
	public void setClientAgent(StreamClientAgent clientAgent) {
		this.clientAgent = clientAgent;
	}
	public String getCompactName() {
		return compactName;
	}
	public void setCompactName(String compactName) {
		this.compactName = compactName;
	}

}

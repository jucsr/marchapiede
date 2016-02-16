package br.UFSC.GRIMA.visual;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dialog.ModalExclusionType;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.NameList;

import br.UFSC.GRIMA.IO.Agent;
import br.UFSC.GRIMA.IO.Camera;
import br.UFSC.GRIMA.dataStructure.Device;

public class ViewDevicesEvents extends ViewDevicesWindow implements ActionListener{
	private MainInterface mainInterface;
///////////////////////////////////Constructor/////////////////////////////////////////////////////////////////////
	public ViewDevicesEvents (MainInterface mainInterface) {
		this.setMainInterface(mainInterface);
		this.addDeviceButton.addActionListener(this);
		this.addCameraButton.addActionListener(this);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				windowClosed(windowEvent);
			}
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				mainInterface.setEnabled(true);
				mainInterface.toFront();
				mainInterface.setViewDevicesEvents(null);
				ArrayList<Device> devices = mainInterface.getMainExecution().getAllDevices();
				for (int i = 0; i < devices.size(); i++) {
					devices.get(i).setChangeNameButton(null);
					devices.get(i).setNameTextField(null);
				}
				if (mainInterface.deviceInfoButton.isSelected()) {
					mainInterface.destroyViewDevicesPanel();
					mainInterface.setViewDevicesPanel();
					mainInterface.deviceInfoButton.setSelected(true);
				}
				else if (mainInterface.deviceMonitoringButton.isSelected()) {
					mainInterface.destroyDeviceMonitoringPanel();
					mainInterface.setDeviceMonitoringPanel();
					mainInterface.deviceMonitoringButton.setSelected(true);
				}
				else if (mainInterface.panelMonitoringButton.isSelected()) {
					mainInterface.destroyPanelMonitoringLayout();
					mainInterface.setPanelMonitoringLayout();
					mainInterface.panelMonitoringButton.setSelected(true);
				}
				if (!devices.isEmpty()) {
					mainInterface.mainAgentField.setText(devices.get(0).getAgent().getAgentName());
				}
			}
		});
		setDevices();
		setCameras();
		this.setVisible(true);
	}
//////////////////////////////////Methods/////////////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(addDeviceButton)) {
			this.setEnabled(false);
			new AddAgentEvents(this);
		}
		else if (e.getSource().equals(addCameraButton)) {
			this.setEnabled(false);
			new AddCameraEvents(this);
		}
	}
	public void setDevices() {
		ArrayList<Device> devices = mainInterface.getMainExecution().getAllDevices();
		devicesPanel.removeAll();
		double[] rowWeights = new double[devices.size() + 2];
		rowWeights[rowWeights.length - 1] = 1.0;
		((GridBagLayout)devicesPanel.getLayout()).rowWeights = rowWeights;
		
		JLabel name = new JLabel("Name");
		JLabel agent = new JLabel("Agent");
		JLabel uuid = new JLabel("UUID");
		JLabel cameras = new JLabel("Cameras");
		devicesPanel.add(agent, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 5), 0, 0));
		devicesPanel.add(name, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 5), 0, 0));
		devicesPanel.add(uuid, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 5), 0, 0));
		devicesPanel.add(cameras, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		for(int line = 0; line < devices.size(); line++) {
			JTextField agentName = new JTextField(devices.get(line).getAgent().getAgentName());
			JTextField deviceName = new JTextField(devices.get(line).getName());
			JTextField uuidName = new JTextField(devices.get(line).getUuid());
			JTextField numCameras = new JTextField("" + devices.get(line).getCameras().size());
			agentName.setEditable(false);
			uuidName.setEditable(false);
			numCameras.setEditable(false);
			JButton changeName = new JButton(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/changeIcon.gif")));
			changeName.setToolTipText("Refresh the name of the following device.");
			JPanel nameContainer = new JPanel();
			nameContainer.setLayout(new GridBagLayout());
			((GridBagLayout)nameContainer.getLayout()).columnWidths = new int[] {45, 0, 0};
			((GridBagLayout)nameContainer.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)nameContainer.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
			((GridBagLayout)nameContainer.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
			nameContainer.add(deviceName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			nameContainer.add(changeName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			devices.get(line).setNameTextField(deviceName);
			devices.get(line).setChangeNameButton(changeName);
			changeName.addActionListener(devices.get(line));
			/////////////////////adding to device panel///////////////////////////////
			devicesPanel.add(agentName, new GridBagConstraints(0, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			devicesPanel.add(nameContainer, new GridBagConstraints(1, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			devicesPanel.add(uuidName, new GridBagConstraints(2, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			devicesPanel.add(numCameras, new GridBagConstraints(3, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
		}
		this.revalidate();
		this.repaint();
	}
	public void setCameras() {
		cameraPanel.removeAll();
		ArrayList<Camera> cameras = mainInterface.getMainExecution().getAllCameras();
		double[] rowWeights = new double[cameras.size() + 2];
		rowWeights[rowWeights.length - 1] = 1.0;
		((GridBagLayout)cameraPanel.getLayout()).rowWeights = rowWeights;
		
		JLabel ip = new JLabel("IP");
		JLabel name = new JLabel("Name");
		JLabel device = new JLabel("Device");
		JLabel view = new JLabel("View");
		cameraPanel.add(ip, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		cameraPanel.add(name, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		cameraPanel.add(device, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		cameraPanel.add(view, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		for(int line = 0; line < cameras.size(); line++) {
			JTextField ipName = new JTextField(cameras.get(line).getClientCamera().getIp());
			JTextField cameraName = new JTextField(cameras.get(line).getCompactName());
			ipName.setEditable(false);
			cameraName.setEditable(false);
			cameraName.setToolTipText(cameras.get(line).getName());
			JComboBox<String> deviceCombobox = new JComboBox<String>();
			ArrayList<Device>devices = mainInterface.getMainExecution().getAllDevices();
			deviceCombobox.addItem(" ");
			for(int i = 0; i < devices.size(); i++) 
				deviceCombobox.addItem(devices.get(i).getName());
			if(cameras.get(line).getDevice() != null) 
				deviceCombobox.setSelectedIndex(devices.indexOf(cameras.get(line).getDevice()) + 1);
			JButton viewButton = new JButton();
			viewButton.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/viewIcon.png")));
			cameraPanel.add(ipName, new GridBagConstraints(0, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			cameraPanel.add(cameraName, new GridBagConstraints(1, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			cameraPanel.add(deviceCombobox, new GridBagConstraints(2, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			cameraPanel.add(viewButton, new GridBagConstraints(3, line + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			cameras.get(line).setViewButton(viewButton);
			cameras.get(line).setDeviceCombobox(deviceCombobox);
			viewButton.addActionListener(cameras.get(line));
			deviceCombobox.addActionListener(cameras.get(line));
		}
		this.revalidate();
		this.repaint();
	}
////////////////////////Getters and Setters//////////////////////////////////////////////////////////////////////
	public MainInterface getMainInterface() {
		return mainInterface;
	}

	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}
}

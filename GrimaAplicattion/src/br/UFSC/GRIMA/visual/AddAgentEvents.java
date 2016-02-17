package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import br.UFSC.GRIMA.IO.Agent;
import br.UFSC.GRIMA.IO.ClientCamera;
import br.UFSC.GRIMA.IO.MainExecution;
import br.UFSC.GRIMA.dataStructure.Device;

public class AddAgentEvents extends AddAgentWindow implements ActionListener {
	private MainInterface mainInterface;
	public AddAgentEvents (ViewDevicesEvents viewDevicesEvents) {
		super(viewDevicesEvents);
		setMainInterface(viewDevicesEvents.getMainInterface());
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				windowClosed(windowEvent);
			}
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				viewDevicesEvents.setDevices();
				viewDevicesEvents.setCameras();
				viewDevicesEvents.setEnabled(true);
				viewDevicesEvents.toFront();
			}
		});
		this.setVisible(true);
	}
	public AddAgentEvents(MainInterface mainInterface) {
		super(mainInterface);
		setMainInterface(mainInterface);
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				windowClosed(windowEvent);
			}
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
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
				mainInterface.setEnabled(true);
				mainInterface.toFront();
			}
		});
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)) {
			this.setEnabled(false);
			WaitEvents waitEvents = new WaitEvents(this, "Creating new Agent.");
			waitEvents.setVisible(true);
			String conf = mainInterface.getMainExecution().addAgent(textFieldIP.getText());
			if (conf == null)
				this.dispose();
			else {
				JOptionPane.showMessageDialog(this, conf, "Error in Agent", JOptionPane.ERROR_MESSAGE);
				this.setEnabled(true);
			}
			if(!textFieldName2.getText().equals("")) {
				MainExecution controller = mainInterface.getMainExecution();
				boolean sucess = controller.addClientCamera(textFieldName2.getText());
				if(sucess && (conf == null)) {
					Agent lastAgent =  controller.getIoControl().getAgents().get(controller.getIoControl().getAgents().size() - 1);
					if(lastAgent.getDevices().size() == 1) {
						ClientCamera client = controller.getIoControl().getClientCameras().get(controller.getIoControl().getClientCameras().size() - 1);
						for(int i = 0; i < client.getCameras().size(); i++) {
							client.getCameras().get(i).setDevice(lastAgent.getDevices().get(0));
							lastAgent.getDevices().get(0).getCameras().add(client.getCameras().get(i));
						}
					}
				}
			}
			waitEvents.dispose();
		}
		else if (e.getSource().equals(cancelButton)) {
			this.dispose();
		}
	}
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}
}

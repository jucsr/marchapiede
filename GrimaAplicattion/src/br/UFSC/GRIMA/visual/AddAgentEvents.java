package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import br.UFSC.GRIMA.IO.Agent;
import br.UFSC.GRIMA.IO.ClientCamera;

public class AddAgentEvents extends AddAgentWindow implements ActionListener {
	private ViewDevicesEvents viewDevicesEvents;
	
	public AddAgentEvents (ViewDevicesEvents viewDevicesEvents) {
		super(viewDevicesEvents);
		this.viewDevicesEvents = viewDevicesEvents;
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
			String conf = viewDevicesEvents.getMainInterface().getMainExecution().addAgent(textFieldName.getText(), textFieldIP.getText());
			waitEvents.dispose();
			if (conf == null)
				this.dispose();
			else {
				JOptionPane.showMessageDialog(this, conf, "Error in Agent", JOptionPane.ERROR_MESSAGE);
				this.setEnabled(true);
			}
			if(!textFieldName2.getText().equals("")) {
				boolean sucess = viewDevicesEvents.getMainInterface().getMainExecution().addClientCamera(textFieldName2.getText());
				if(sucess && (conf == null)) {
					Agent lastAgent =  viewDevicesEvents.getMainInterface().getMainExecution().getIoControl().getAgents().get(viewDevicesEvents.getMainInterface().getMainExecution().getIoControl().getAgents().size() - 1);
					if(lastAgent.getDevices().size() == 1) {
						ClientCamera client = viewDevicesEvents.getMainInterface().getMainExecution().getIoControl().getClientCameras().get(viewDevicesEvents.getMainInterface().getMainExecution().getIoControl().getClientCameras().size() - 1);
						for(int i = 0; i < client.getCameras().size(); i++) {
							client.getCameras().get(i).setDevice(lastAgent.getDevices().get(0));
							lastAgent.getDevices().get(0).getCameras().add(client.getCameras().get(i));
						}
					}
				}
			}
		}
		else if (e.getSource().equals(cancelButton)) {
			this.dispose();
		}
	}
}

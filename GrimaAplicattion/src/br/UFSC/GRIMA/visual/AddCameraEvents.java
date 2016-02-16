package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.IO.Agent;
import br.UFSC.GRIMA.IO.ClientCamera;

public class AddCameraEvents extends AddCameraWindow implements ActionListener{
	private ViewDevicesEvents viewDevicesEvents;
///////////////////constructor//////////////////////////////////////
	public AddCameraEvents (ViewDevicesEvents viewDevicesEvents) {
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
				viewDevicesEvents.toFront();
			}
		});
		this.setVisible(true);
	}
//////////////////////////methods/////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(okButton)) {
			this.setEnabled(false);
			WaitEvents waitEvents = new WaitEvents(this, "Connecting to camera streams.");
			waitEvents.setVisible(true);
			if(!textFieldIP.getText().equals("")) {
				boolean sucess = viewDevicesEvents.getMainInterface().getMainExecution().addClientCamera(textFieldIP.getText());
				if(sucess)
					this.dispose();
				else 
					this.setEnabled(true);
			}
			else {
				JOptionPane.showMessageDialog(this, "The ip field is empty, please insert a valid ip.", "Error", JOptionPane.ERROR_MESSAGE);
				this.setEnabled(true);
			}
			waitEvents.dispose();
		}
		else if (e.getSource().equals(cancelButton)) {
			this.dispose();
		}
	}
/////////////////////////Getters and setters///////////////////////////
	public ViewDevicesEvents getViewDevicesEvents() {
		return viewDevicesEvents;
	}

	public void setViewDevicesEvents(ViewDevicesEvents viewDevicesEvents) {
		this.viewDevicesEvents = viewDevicesEvents;
	}
	

}

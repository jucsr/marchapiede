package br.UFSC.GRIMA.dataStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.UFSC.GRIMA.IO.Agent;
import br.UFSC.GRIMA.IO.Camera;
import br.UFSC.GRIMA.application.entities.devices.DeviceType;
import br.UFSC.GRIMA.application.entities.streams.DeviceStreamType;

public class Device implements ActionListener{
	private String name;
	private String uuid;
	private int agentPosition;
	private ArrayList<Component> components;
	private ArrayList<Camera> cameras;
	private Agent agent;
////////////////ConfigureDeviceComponents
	private JButton changeNameButton;
	private JTextField nameTextField;
///////////////////////Construtor/////////////////////////////////////////////////////
	public Device(DeviceStreamType currentObject,DeviceType probeObject, int agentPosition, Agent agent) {
		setName(currentObject.getName());
		setUuid(currentObject.getUuid());
		setAgentPosition(agentPosition);
		setCameras(new ArrayList<Camera>());
		setAgent(agent);
		setComponents(new ArrayList<Component>());
		for (int i = 0; i < currentObject.getComponentStream().size(); i++) {
			components.add(new Component(currentObject.getComponentStream().get(i), probeObject, i, this));
			
		}
		
	}
////////////////////////////////Methods/////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		if(changeNameButton != null) {
			if(e.getSource().equals(changeNameButton)) {
				String name = nameTextField.getText();
				if(name.equals(""))
					JOptionPane.showMessageDialog(agent.getIoControl().getController().getMainInterface(), "Invalid name!!", "Error in Change", JOptionPane.ERROR_MESSAGE);
				else if(agent.getDeviceByName(name) == null) {
					setName(name);
					agent.getIoControl().getController().getMainInterface().getViewDevicesEvents().setCameras();
				}
				else {
					JOptionPane.showMessageDialog(agent.getIoControl().getController().getMainInterface(), "This name is already in use by another Device.", "Error in Change", JOptionPane.ERROR_MESSAGE);
					nameTextField.setText(this.name);
				}
			}
		}
	}
////////////////////////////////Getters and Setters////////////////////////////////////////////
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAgentPosition() {
		return agentPosition;
	}
	public void setAgentPosition(int agentPosition) {
		this.agentPosition = agentPosition;
	}
	public ArrayList<Camera> getCameras() {
		return cameras;
	}
	public void setCameras(ArrayList<Camera> cameras) {
		this.cameras = cameras;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public ArrayList<Component> getComponents() {
		return components;
	}
	public void setComponents(ArrayList<Component> components) {
		this.components = components;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public JButton getChangeNameButton() {
		return changeNameButton;
	}
	public void setChangeNameButton(JButton changeNameButton) {
		this.changeNameButton = changeNameButton;
	}
	public JTextField getNameTextField() {
		return nameTextField;
	}
	public void setNameTextField(JTextField nameTextField) {
		this.nameTextField = nameTextField;
	}
	
}

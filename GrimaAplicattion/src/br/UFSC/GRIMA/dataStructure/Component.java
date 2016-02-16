package br.UFSC.GRIMA.dataStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.xml.bind.JAXBElement;

import br.UFSC.GRIMA.application.entities.devices.ComponentType;
import br.UFSC.GRIMA.application.entities.devices.DeviceType;
import br.UFSC.GRIMA.application.entities.streams.ComponentStreamType;

public class Component implements ActionListener{
	private String component;
	private String name;
	private String componentID;
	private String componentType;
	private int agentPosition;
	private Device device;
	private ArrayList<Variable> variables;
	///DeviceMonitoringPanelComponents
	private JToggleButton componentButton;
	private JPanel componentPanel;
//////////////////////Constructor//////////////////////////////////////////////////////////////////
	public Component(ComponentStreamType currentObject, DeviceType probeObject, int agentPosition, Device device) {
		setComponent(currentObject.getComponent());
		setName(currentObject.getName());
		setComponentID(currentObject.getComponentId());
		setAgentPosition(agentPosition);
		setDevice(device);
		int componentType = 0;
		int componentPosition = 0;
		for(int i = 0; i < probeObject.getComponents().getComponent().size(); i++) {
			for (int j = 0; j < probeObject.getComponents().getComponent().get(i).getValue().getComponents().getComponent().size(); j++) {
				if (probeObject.getComponents().getComponent().get(i).getValue().getComponents().getComponent().get(j).getValue().getId().equals(componentID)) {
					setComponentType(probeObject.getComponents().getComponent().get(i).getName().getLocalPart());
					componentType = i;
					componentPosition = j;
				}
			}
		}
		JAXBElement<? extends ComponentType> probeParameter;
		setVariables(new ArrayList<Variable>());
		if (currentObject.getName().toUpperCase().equals("AXES")) {
			setComponentType("Axes");
			probeParameter = probeObject.getComponents().getComponent().get(0);
		}
		else if (currentObject.getName().toUpperCase().equals("CONTROLLER")) {
			setComponentType("Controller");
			probeParameter = probeObject.getComponents().getComponent().get(1);
		}
		else if (currentObject.getName().toUpperCase().equals("SYSTEMS")) {
			setComponentType("System");
			probeParameter = probeObject.getComponents().getComponent().get(2);
		}
		else if (getComponentType() == null) {
			probeParameter = null;
			setComponentType("Unknown");
		}
		else
			probeParameter = probeObject.getComponents().getComponent().get(componentType).getValue().getComponents().getComponent().get(componentPosition);
		try {
			for(int i = 0; i < currentObject.getSamples().getSample().size(); i++) 
				variables.add(new Variable(currentObject.getSamples(), probeParameter, i, this));
		}catch(Exception e){}
		try {
			for(int i = 0; i < currentObject.getEvents().getEvent().size(); i++) 
				variables.add(new Variable(currentObject.getEvents(), probeParameter, i, this));
		}catch(Exception e){}
		try {
			for(int i = 0; i < currentObject.getCondition().getCondition().size(); i++) 
				variables.add(new Variable(currentObject.getCondition() ,probeParameter, i, this));
		}catch(Exception e){}
		
	}
/////////////////////////Methods/////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(componentButton != null) {
			if(e.getSource().equals(componentButton)) {
				if(componentButton.isSelected()) {
					device.getAgent().getIoControl().getController().getMainInterface().getDeviceMonitoringPanelEvents().setComponentMonitored(this);
				}
				else {
					device.getAgent().getIoControl().getController().getMainInterface().getDeviceMonitoringPanelEvents().destroyComponentMonitored(this);
				}
			}
		}
	}

/////////////////////Getters and Setters//////////////////////////////////////////////////////////	
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComponentID() {
		return componentID;
	}
	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}

	public int getAgentPosition() {
		return agentPosition;
	}
	public void setAgentPosition(int agentPosition) {
		this.agentPosition = agentPosition;
	}
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public JToggleButton getComponentButton() {
		return componentButton;
	}

	public void setComponentButton(JToggleButton componentButton) {
		this.componentButton = componentButton;
		if(componentButton != null){
			componentButton.addActionListener(this);
		}
	}
	public JPanel getComponentPanel() {
		return componentPanel;
	}
	public void setComponentPanel(JPanel componentPanel) {
		this.componentPanel = componentPanel;
	}

	
}

package br.UFSC.GRIMA.dataStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.RepaintManager;
import javax.xml.bind.JAXBElement;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import br.UFSC.GRIMA.application.entities.devices.ComponentType;
import br.UFSC.GRIMA.application.entities.streams.ConditionListType;
import br.UFSC.GRIMA.application.entities.streams.EventType;
import br.UFSC.GRIMA.application.entities.streams.EventsType;
import br.UFSC.GRIMA.application.entities.streams.SampleType;
import br.UFSC.GRIMA.application.entities.streams.SamplesType;

public class Variable implements ActionListener, SeriesChangeListener {
	private String name;
	private String dataItemID;
	private char division;
	private char type;
	private String assignment;
	private ArrayList<String> categoryStrings;
	private String unit;
	private TimeSeries dataSerie; //null until becomes a monitored variable
	private int[] timeRange;
	private int agentPosition;
	private Component component;
	/////////////ViewDevicesComponents/////
	private JCheckBox startMonitoringPanel;
	private JToggleButton varMonitored;
	private JToggleButton varSaving;
	//////////////DeviceMonitoringPanelComponents//////
	private JCheckBox monitoringCheckBox;
	private JTextField deviceMonitoringRegister;
	////////////createPanelComponent
	private JButton closeButton;
//////////////////////Constructor//////////////////////////////////////////////////////////////////
	public Variable (SamplesType currentObject, JAXBElement<? extends ComponentType> probeObject, int agentPosition, Component component) {
		setName(currentObject.getSample().get(agentPosition).getValue().getName());
		setDataItemID(currentObject.getSample().get(agentPosition).getValue().getDataItemId());
		setDivision('S');
		commonStrartup(component, agentPosition, probeObject);
		setType(typeIdentification(currentObject.getSample().get(agentPosition).getValue().getValue()));
	}
	public Variable (EventsType currentObject, JAXBElement<? extends ComponentType> probeObject, int agentPosition, Component component) {
		setName(currentObject.getEvent().get(agentPosition).getValue().getName());
		setDataItemID(currentObject.getEvent().get(agentPosition).getValue().getDataItemId());
		setDivision('E');
		commonStrartup(component, agentPosition, probeObject);
		setType(typeIdentification(currentObject.getEvent().get(agentPosition).getValue().getValue()));
	}
	public Variable (ConditionListType currentObject, JAXBElement<? extends ComponentType> probeObject, int agentPosition, Component component) {
		setName(currentObject.getCondition().get(agentPosition).getValue().getName());
		setDataItemID(currentObject.getCondition().get(agentPosition).getValue().getDataItemId());
		setDivision('C');
		commonStrartup(component, agentPosition, probeObject);
		setType(typeIdentification(currentObject.getCondition().get(agentPosition).getName().getLocalPart()));
		
	}
/////////////////////////Methods/////////////////////////////////////////////////////////////////
	public void commonStrartup(Component component, int agentPosition, JAXBElement<? extends ComponentType> probeObject) {
		setCategoryStrings(new ArrayList<String>());
		setTimeRange(component.getDevice().getAgent().getIoControl().getController().getDefaultTimeRange());
		setAgentPosition(agentPosition);
		setComponent(component);
		if (probeObject != null) {
			for(int i = 0; i < probeObject.getValue().getDataItems().getDataItem().size(); i++) {
				if (probeObject.getValue().getDataItems().getDataItem().get(i).getId().equals(dataItemID)) {
					setAssignment(probeObject.getValue().getDataItems().getDataItem().get(i).getType());
					if (probeObject.getValue().getDataItems().getDataItem().get(i).getUnits() != null)
						setUnit(probeObject.getValue().getDataItems().getDataItem().get(i).getUnits());
					else
						setUnit("Adimensional");
				}
				
			}
		}
		else {
			setUnit("Adimensional");
			setAssignment("Unknown");
		}
	}
	public char typeIdentification(String value) {
		if(type != 'i') {
			if(value.toUpperCase().equals("UNAVAILABLE"))
				return 'n';
			else {
				try	{
					((Double)(Double.parseDouble(value.replace(',', '.')))).doubleValue();
					return '1';
				}
				catch (Exception e) {
					return 'c';
				}
			}
		}
		else return 'i';
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (startMonitoringPanel != null) {
			if(e.getSource().equals(startMonitoringPanel)) {
				if(startMonitoringPanel.isSelected())
					component.getDevice().getAgent().getIoControl().getController().getNewMonitoringPanelList().add(this);
				else 
					component.getDevice().getAgent().getIoControl().getController().getNewMonitoringPanelList().remove(this);
			}
		}
		if (varMonitored != null) {
			if (e.getSource().equals(varMonitored)) {
				if(varMonitored.isSelected()) {
					if (dataSerie == null) {
						if(name != null) 
							setDataSerie(new TimeSeries(name));
						else
							setDataSerie(new TimeSeries(dataItemID));
					}
					component.getDevice().getAgent().getIoControl().getLoadExecution().addToVariableList(this);
				}
				else 
					varMonitored.setSelected(!component.getDevice().getAgent().getIoControl().getLoadExecution().removeFromVariableList(this, true));
			}
		}
		if (varSaving != null) {
			if(e.getSource().equals(varSaving)) {
				if(varSaving.isSelected()) 
					varSaving.setSelected(component.getDevice().getAgent().getIoControl().getLoadExecution().addToSaveList(this, true));
				else
					component.getDevice().getAgent().getIoControl().getLoadExecution().removeFromSaveList(this);
			}
		}
		if (monitoringCheckBox != null) {
			if(e.getSource().equals(monitoringCheckBox)) {
				if (monitoringCheckBox.isSelected()) 
					component.getDevice().getAgent().getIoControl().getController().getMainInterface().getDeviceMonitoringPanelEvents().setVariablesMonitored(this);
				else
					component.getDevice().getAgent().getIoControl().getController().getMainInterface().getDeviceMonitoringPanelEvents().destroyVariablesMonitored(this);
			}
		}
	}
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		// TODO Auto-generated method stub
		if ((monitoringCheckBox != null) && (component.getDevice().getAgent().getIoControl().getController().getMainInterface().getDeviceMonitoringPanelEvents() != null)) {
			if (monitoringCheckBox.isSelected() && !dataSerie.isEmpty() && component.getDevice().getAgent().getIoControl().getController().getMainInterface().getDeviceMonitoringPanelEvents().playPause.isSelected())
				deviceMonitoringRegister.setText(getLastValue());
		}
	}
	public  String getLastValue() {
		if ((dataSerie != null) && (!dataSerie.isEmpty())) {
			if (dataSerie.getValue(dataSerie.getItemCount() - 1) == null) {
				return null;
			}
			else if (type == '1') 
				return dataSerie.getValue(dataSerie.getItemCount() - 1).toString();
			else
				return categoryStrings.get(dataSerie.getValue(dataSerie.getItemCount() - 1).intValue());
		}
		else
			return null;
	}
	public int getCategoryPosition (String s) {
		if(!categoryStrings.contains(s)) 
			categoryStrings.add(s);
		return categoryStrings.indexOf(s);	
	}
	public void setVariableToIrregular() {
		setType('i');
		String last;
		if(!categoryStrings.isEmpty())
			last = categoryStrings.get(dataSerie.getValue(dataSerie.getItemCount() - 1).intValue());
		else
			last = ("" + dataSerie.getValue(dataSerie.getItemCount() - 1));
		Millisecond timestamp = (Millisecond) dataSerie.getTimePeriod(dataSerie.getItemCount() - 1);
		dataSerie.clear();
		dataSerie.add(timestamp, 0);
		categoryStrings.clear();
		categoryStrings.add(last);
	}
///////////////////////////////////////////////////////Getters and Setters/////////////////////////////////////////////////////////////////////////
	public String getDataItemID() {
		return dataItemID;
	}
	public void setDataItemID(String dataItemID) {
		this.dataItemID = dataItemID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getDivision() {
		return division;
	}
	public void setDivision(char division) {
		this.division = division;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public ArrayList<String> getCategoryStrings() {
		return categoryStrings;
	}
	public void setCategoryStrings(ArrayList<String> categoryStrings) {
		this.categoryStrings = categoryStrings;
	}
	public TimeSeries getDataSerie() {
		return dataSerie;
	}
	public void setDataSerie(TimeSeries dataSerie) {
		this.dataSerie = dataSerie;
	}
	public int getAgentPosition() {
		return agentPosition;
	}
	public void setAgentPosition(int agentPosition) {
		this.agentPosition = agentPosition;
	}
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}
	public JCheckBox getStartMonitoringPanel() {
		return startMonitoringPanel;
	}
	public void setStartMonitoringPanel(JCheckBox startMonitoringPanel) {
		this.startMonitoringPanel = startMonitoringPanel;
		if (startMonitoringPanel != null) {
			startMonitoringPanel.addActionListener(this);
			startMonitoringPanel.setSelected(component.getDevice().getAgent().getIoControl().getController().getNewMonitoringPanelList().contains(this));
		}
	}
	public JToggleButton getVarMonitored() {
		return varMonitored;
	}
	public void setVarMonitored(JToggleButton varMonitored) {
		this.varMonitored = varMonitored;
		if(varMonitored != null) {
			varMonitored.addActionListener(this);
			varMonitored.setSelected(component.getDevice().getAgent().getIoControl().getLoadExecution().getVariableList().contains(this));
		}
	}
	public JToggleButton getVarSaving() {
		return varSaving;
	}
	public void setVarSaving(JToggleButton varSaving) {
		this.varSaving = varSaving;
		if(varSaving != null) {
			varSaving.addActionListener(this);
			varSaving.setSelected(component.getDevice().getAgent().getIoControl().getLoadExecution().getDataSaveList().contains(this));
		}
	}
	public int[] getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(int[] timeRange) {
		this.timeRange = timeRange;
	}
	public JCheckBox getMonitoringCheckBox() {
		return monitoringCheckBox;
	}
	public void setMonitoringCheckBox(JCheckBox monitoringCheckBox) {
		this.monitoringCheckBox = monitoringCheckBox;
		if(monitoringCheckBox != null) {
			monitoringCheckBox.addActionListener(this);
		}
	}
	public JTextField getDeviceMonitoringRegister() {
		return deviceMonitoringRegister;
	}
	public void setDeviceMonitoringRegister(JTextField deviceMonitoringRegister) {
		this.deviceMonitoringRegister = deviceMonitoringRegister;
	}
	public JButton getCloseButton() {
		return closeButton;
	}
	public void setCloseButton(JButton closeButton) {
		this.closeButton = closeButton;
	}
	
	
}

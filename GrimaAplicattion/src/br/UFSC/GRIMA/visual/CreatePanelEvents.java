package br.UFSC.GRIMA.visual;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.UFSC.GRIMA.dataStructure.*;
import br.UFSC.GRIMA.operational.CategoryMonitoringUnit;
import br.UFSC.GRIMA.operational.NumericMonitoringUnit;

public class CreatePanelEvents extends CreatePanelWindow implements ActionListener{
	private MainInterface mainInterface;
	private int[] timeRange;
	private ArrayList<Variable> variables;
	private String panelName;
	private Device currentDevice;
	private Component currentComponent;
	private Variable currentVariable;
////////////////////////////////////////////Constructor///////////////////////////////////////////////////////////////////
	public CreatePanelEvents(MainInterface mainInterface, ArrayList<Variable> variables) {
		///////////inicialization//////////////
		setMainInterface(mainInterface);
		setTimeRange((int[]) mainInterface.getMainExecution().getDefaultTimeRange().clone());
		if (variables != null)
			setVariables(variables);
		else
			setVariables(new ArrayList<Variable>());
		configureWorkspace();
		///////////Create and set Default name////
		String defaultName = null;
		int i = 0;
		while(defaultName == null) {
			boolean create = true;
			for (int j = 0; j < mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().size(); j++) {
				if(mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().get(j).getName().equals("Panel"+ i)) {
					create = false;
					break;
				}
			}
			if (create) {
				defaultName = ("Panel"+ i);
			}
			i++;
		}
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				windowClosing(windowEvent);
			}
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (variables != null) {
		    		for (int i = 0; i < variables.size(); i++) 
		    			variables.get(i).setCloseButton(null);
		    	}
				mainInterface.setEnabled(true);
				mainInterface.toFront();
				if (mainInterface.deviceInfoButton.isSelected()) {
					mainInterface.destroyViewDevicesPanel();
					mainInterface.setViewDevicesPanel();
					mainInterface.deviceInfoButton.setSelected(true);
				}
				if (mainInterface.deviceMonitoringButton.isSelected()) {
					mainInterface.destroyDeviceMonitoringPanel();
					mainInterface.setDeviceMonitoringPanel();
					mainInterface.deviceMonitoringButton.setSelected(true);
				}
				if (mainInterface.panelMonitoringButton.isSelected()) {
					mainInterface.destroyPanelMonitoringLayout();
					mainInterface.setPanelMonitoringLayout();
					mainInterface.panelMonitoringButton.setSelected(true);
				}
			}
		});
		nameField.setText(defaultName);
		panelName = defaultName;
		hourField.setText("" + timeRange[0]);
		minuteField.setText("" + timeRange[1]);
		secondField.setText("" + timeRange[2]);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		////////////////types of chart/////////////////////////////////
		model.addElement("LineChart");
		model.addElement("StepLineChart");
		model.addElement("AreaChart");
		model.addElement("2DLineChart");
		///////////////////////////////////////////////////////////////
		nameField.addActionListener(this);
		charTypeComboBox.setModel(model);
		DefaultComboBoxModel<String> deviceModel = new DefaultComboBoxModel<String>();
		ArrayList<Device>devices = mainInterface.getMainExecution().getAllDevices();
		componentCombobox.setModel(new DefaultComboBoxModel<String>());
		variableCombobox.setModel(new DefaultComboBoxModel<String>());
		for (int j = 0; j < devices.size(); j++) {
			deviceModel.addElement(devices.get(j).getName());
			if (j == 0) {
				deviceModel.setSelectedItem(devices.get(j).getName());
				for (int k = 0; k < devices.get(0).getComponents().size(); k++) {
					componentCombobox.addItem(devices.get(0).getComponents().get(k).getComponent() + "-" + devices.get(0).getComponents().get(k).getName());
					if (k == 0) {
						for (int l = 0; l < devices.get(0).getComponents().get(0).getVariables().size(); l++) {
							if (devices.get(0).getComponents().get(0).getVariables().get(l).getName() != null) 
								variableCombobox.addItem(devices.get(0).getComponents().get(0).getVariables().get(l).getName());
							else
								variableCombobox.addItem(devices.get(0).getComponents().get(0).getVariables().get(l).getDataItemID());
							if (l == 0) {
								setCurrentVariable(devices.get(0).getComponents().get(0).getVariables().get(0));
								setCurrentComponent(devices.get(0).getComponents().get(0));
								setCurrentDevice(devices.get(0));
							}
						}
					}
				}
			}
		}
		deviceCombobox.setModel(deviceModel);
		deviceCombobox.addActionListener(this);
		componentCombobox.addActionListener(this);
		variableCombobox.addActionListener(this);
		addVariableButton.addActionListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}
//////////////////////////////////////////////Methods/////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(nameField)) {
			boolean sucess = true;
			for (int i = 0; i < mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().size(); i++) {
				if(mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().get(i).getName().equals(nameField.getText())) {
					JOptionPane.showMessageDialog(this, "This name is curently used in another Panel", "Error", JOptionPane.ERROR_MESSAGE);
					nameField.setText(panelName);
					sucess = false;
				}
			}
			if(sucess) 
				panelName = nameField.getText();
		}
		else if(e.getSource().equals(deviceCombobox)) {
			Device device = mainInterface.getMainExecution().getAllDevices().get(deviceCombobox.getSelectedIndex());
			setCurrentDevice(device);
			componentCombobox.removeActionListener(this);
			componentCombobox.removeAllItems();
			for (int i = 0; i < device.getComponents().size(); i++) {
				componentCombobox.addItem(device.getComponents().get(i).getComponent() + "-" + device.getComponents().get(i).getName());
			}
			componentCombobox.addActionListener(this);
			componentCombobox.setSelectedItem(0);
			this.actionPerformed(new ActionEvent(componentCombobox, 0, null));
		}
		else if(e.getSource().equals(componentCombobox)) {
			Component component;
			if (componentCombobox.getSelectedIndex() >= 0)
				component = currentDevice.getComponents().get(componentCombobox.getSelectedIndex());
			else
				component = currentDevice.getComponents().get(0);
			setCurrentComponent(component);
			variableCombobox.removeActionListener(this);
			variableCombobox.removeAllItems();
			for (int i = 0; i < component.getVariables().size(); i++) {
				if (component.getVariables().get(i).getName() != null) 
					variableCombobox.addItem(component.getVariables().get(i).getName());
				else
					variableCombobox.addItem(component.getVariables().get(i).getDataItemID());
			}
			variableCombobox.addActionListener(this);
			variableCombobox.setSelectedItem(0);
			this.actionPerformed(new ActionEvent(variableCombobox, 0, null));
		}
		else if(e.getSource().equals(variableCombobox)) {
			if(variableCombobox.getSelectedIndex() >= 0)
				setCurrentVariable(currentComponent.getVariables().get(variableCombobox.getSelectedIndex()));
			else
				setCurrentVariable(currentComponent.getVariables().get(0));
		}
		else if(e.getSource().equals(addVariableButton)) {
			if((deviceCombobox.getSelectedIndex() < 0) || (componentCombobox.getSelectedIndex() < 0) || (variableCombobox.getSelectedIndex() < 0)) {
				JOptionPane.showMessageDialog(this, "One or more fields are not selected, please set all registers to add a new variable.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (variables.contains(currentVariable))
				JOptionPane.showMessageDialog(this, "The selected variable is already in the monitoring variables list.", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				variables.add(currentVariable);
				configureWorkspace();
			}
		}
		else if (e.getSource().equals(cancelButton)) {
			this.dispose();
		}
		else if (e.getSource().equals(okButton)) {
			boolean sucess = true;
			if(variables.isEmpty()) {
				sucess = false;
				JOptionPane.showMessageDialog(this, "Select at least one variable to create the panel.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			for(int i = 0; i < variables.size(); i++) {
				if(variables.get(i).getType() != variables.get(0).getType()) {
					JOptionPane.showMessageDialog(this, "The type of the variables desn't mismatch, the panel can only be created from same type variables.", "Error", JOptionPane.ERROR_MESSAGE);
					sucess = false;
					break;
				}
			}
			if (nameField.equals("")) {
				sucess = false;
				JOptionPane.showMessageDialog(this, "You cannot create a new panel without a name.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			for(int i = 0; i < mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().size(); i++) {
				if (nameField.getText().equals(mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().get(i).getName())) {
					sucess = false;
					JOptionPane.showMessageDialog(this, "There is another panel with this name, please change it and try again.", "Error", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			if (sucess) {
				if ((variables.get(0).getType() == 'i') || (variables.get(0).getType() == 'n')) {
					JOptionPane.showMessageDialog(this, "The panel can only be created from categoric or numeric variables, any other type is not allowed.", "Error", JOptionPane.ERROR_MESSAGE);
					sucess = false;
				}
				if ((charTypeComboBox.getSelectedItem().equals("LineChart")) || (charTypeComboBox.getSelectedItem().equals("AreaChart")) || (charTypeComboBox.getSelectedItem().equals("2DLineChart"))) {
					if(variables.get(0).getType() == 'c') {
						JOptionPane.showMessageDialog(this, "You cannot use this type of chart for categoric variables, please select the StepLineChart chartType.", "Error", JOptionPane.ERROR_MESSAGE);
						sucess = false;
					}
				}
				else if (charTypeComboBox.getSelectedItem().equals("StepLineChart")) {
					if(variables.get(0).getType() == '1') {
						JOptionPane.showMessageDialog(this, "You cannot use this type of chart for numeric variables, please select the LineChart or AreaChart chartType.", "Error", JOptionPane.ERROR_MESSAGE);
						sucess = false;
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "ChartType cannot be identified by the application.", "Error", JOptionPane.ERROR_MESSAGE);
					sucess = false;
				}
				int hour = 0;
				try {
					hour = Integer.parseInt(hourField.getText());
					if ((hour >= 0)&&(hour <= 23)) 
						timeRange[0] = hour;
					else {
						JOptionPane.showMessageDialog(this, "Irregular argument, please insert a number between 0 and 23.", "Error", JOptionPane.ERROR_MESSAGE);
						hourField.setText("" + timeRange[0]);
						sucess = false;
					}
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Irregular argument, please insert an integer number.", "Error", JOptionPane.ERROR_MESSAGE);
					sucess = false;
				}			
				int minute = 0;
				try {
					minute = Integer.parseInt(minuteField.getText());
					if ((minute >= 0)&&(minute <= 59)) 
						timeRange[1] = minute;
					else {
						JOptionPane.showMessageDialog(this, "Irregular argument, please insert a number between 0 and 59.", "Error", JOptionPane.ERROR_MESSAGE);
						minuteField.setText("" + timeRange[1]);
						sucess = false;
					}
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Irregular argument, please insert an integer number.", "Error", JOptionPane.ERROR_MESSAGE);
					sucess = false;
				}			
				int second = 0;
				try {
					second = Integer.parseInt(secondField.getText());
					if ((second >= 0)&&(second <= 59)) 
						timeRange[2] = second;
					else {
						JOptionPane.showMessageDialog(this, "Irregular argument, please insert a number between 0 and 59.", "Error", JOptionPane.ERROR_MESSAGE);
						secondField.setText("" + timeRange[2]);
						sucess = false;
					}	
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Irregular argument, please insert an integer number.", "Error", JOptionPane.ERROR_MESSAGE);
					sucess = false;
				}
				if(sucess) {
					if ((second == 0)&&(minute == 0)&&(hour == 0)) {
						sucess = false;
						JOptionPane.showMessageDialog(this, "Invalid Time Range, it cannot be zero.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(sucess) {
					if((charTypeComboBox.getSelectedItem().equals("LineChart")) || (charTypeComboBox.getSelectedItem().equals("AreaChart")))
						mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().add(new NumericMonitoringUnit(nameField.getText(), mainInterface.getMainExecution().getPanelMonitoringSystem(), timeRange, (String) charTypeComboBox.getSelectedItem(), this.variables, variables.get(0).getType()));
					else if (charTypeComboBox.getSelectedItem().equals("StepLineChart")) 
						mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().add(new CategoryMonitoringUnit(nameField.getText(), mainInterface.getMainExecution().getPanelMonitoringSystem(), timeRange, (String) charTypeComboBox.getSelectedItem(), this.variables, variables.get(0).getType()));
							
					mainInterface.setMenuConfigurePanel();
					this.dispose();
				}
			}
		}
		else {
			for (int i = 0; i < variables.size(); i++) {
				if (e.getSource().equals(variables.get(i).getCloseButton())) {
					variables.get(i).setCloseButton(null);
					variables.remove(variables.get(i));
					configureWorkspace();
					break;
				}
			}
			
		}
		revalidate();
		repaint();
	}
	public void addVariable(Variable variable) {
			JLabel type = new JLabel();
			if(variable.getType() == '1') {
				type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/numberTypeIcon.png")));
				type.setToolTipText("Numeric Variable Type: this variable shows its information through number values.");
			}
			else if(variable.getType() == 'c') { 
				type = new JLabel((new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/categoryTypeIcon.png"))));
				type.setToolTipText("Category Variable Type: this variable shows its information through categories that describe some kind of information.");
			}
			else if(variable.getType() == 'i') {
				type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
				type.setToolTipText("Irregular Variable Type: this variable shows values that is neither numeric nor category variable Type.");
			}
			else {
				type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/nullTypeIcon.png")));
				type.setToolTipText("Null Variable Type: this variable didn't show any register yet, so the application cannot identify its type.");
			}
			JTextField var;
			if(variable.getName() != null)
				 var = new JTextField(variable.getName());
			else
				var = new JTextField(variable.getDataItemID());
			JLabel twoPoint = new JLabel(":");
			JTextField comp = new JTextField(variable.getComponent().getComponent() + "-" + variable.getComponent().getName());
			JLabel twoPoint1 = new JLabel(":");
			JTextField device = new JTextField(variable.getComponent().getDevice().getName());
			var.setEditable(false);
			comp.setEditable(false);
			device.setEditable(false);
			JButton close = new JButton("X");
			workspace.add(type, new GridBagConstraints(0, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			workspace.add(var, new GridBagConstraints(1, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			workspace.add(twoPoint, new GridBagConstraints(2, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			workspace.add(comp, new GridBagConstraints(3, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			workspace.add(twoPoint1, new GridBagConstraints(4, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			workspace.add(device, new GridBagConstraints(5, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			workspace.add(close, new GridBagConstraints(6, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			close.addActionListener(this);
			variable.setCloseButton(close);
			revalidate();
			repaint();
		}
	public void removeVariable(Variable variable) {
		variables.remove(variable);
		configureWorkspace();
	}
	public void configureWorkspace() {
		workspace.removeAll();
		JLabel type = new JLabel("Type");
		JLabel variable = new JLabel("Variable");
		JLabel component = new JLabel("Component");
		JLabel device = new JLabel("Device");
		workspace.add(type, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(variable, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(component, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(device, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		//////////////////cria painel das Variaveis////////////////////////
		double[] growList = new double[variables.size() + 2];
		growList[growList.length - 1] = 1.0;
		((GridBagLayout)workspace.getLayout()).rowWeights = growList;
		for(int j = 0; j < variables.size(); j++) 
			addVariable(variables.get(j));
		revalidate();
		repaint();
	}
///////////////////////////////////////////getters and Setters////////////////////////////////////////////////////////////
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}
	public int[] getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(int[] timeRange) {
		this.timeRange = timeRange;
	}
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	public Device getCurrentDevice() {
		return currentDevice;
	}
	public void setCurrentDevice(Device currentDevice) {
		this.currentDevice = currentDevice;
	}
	public Component getCurrentComponent() {
		return currentComponent;
	}
	public void setCurrentComponent(Component currentComponent) {
		this.currentComponent = currentComponent;
	}
	public Variable getCurrentVariable() {
		return currentVariable;
	}
	public void setCurrentVariable(Variable currentVariable) {
		this.currentVariable = currentVariable;
	}
	
}

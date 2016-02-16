package br.UFSC.GRIMA.visual;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import br.UFSC.GRIMA.dataStructure.Component;
import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.operational.DeviceMonitoringSystem;

public class DeviceMonitoringPanelEvents extends DeviceMonitoringPanel implements ActionListener {
	private MainInterface mainInterface;
//////////////////////////////////////////////constructor///////////////////////////////////////////////////////////
	public DeviceMonitoringPanelEvents(MainInterface mainInterface) {
		setMainInterface(mainInterface);
		DeviceMonitoringSystem deviceMonitoringSystem = mainInterface.getMainExecution().getDeviceMonitoringSystem();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		ArrayList<Device> devices = mainInterface.getMainExecution().getAllDevices();
		model.addElement(" ");
		for(int i = 0; i < devices.size(); i++)
			model.addElement(devices.get(i).getName());
		deviceComboBox.setModel(model);
		uuidField.setEditable(false);
		agentField.setEditable(false);
		playPause.setSelected(true);
		playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/pause.png")));
		if(deviceMonitoringSystem.getSelectedDevice() != null) {
			model.setSelectedItem(deviceMonitoringSystem.getSelectedDevice().getName());
			setDeviceMonitored(deviceMonitoringSystem.getSelectedDevice());
		}
		deviceComboBox.addActionListener(this);
		playPause.addActionListener(this);
		mainInterface.workSpace.add(this, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		revalidate();
		repaint();
	}
////////////////////////////////////////////Methods////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(deviceComboBox)) {
			if(deviceComboBox.getSelectedIndex() <= 0) {
				setDeviceMonitored(null);
			}
			else {
				ArrayList<Device> devices = mainInterface.getMainExecution().getAllDevices();
				setNewDeviceMonitored(devices.get(deviceComboBox.getSelectedIndex() - 1));
			}
		}
		else if (e.getSource().equals(cameraComboBox)) {
			cameraPanel.removeAll();
			if(mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedCamera() != null) {
				if(mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedCamera().getVideoPanel() != null) 
					mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedCamera().destroyVideoPanel();
			}
			if(cameraComboBox.getSelectedIndex() != 0) 
				mainInterface.getMainExecution().getDeviceMonitoringSystem().setSelectedCamera(mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedDevice().getCameras().get(cameraComboBox.getSelectedIndex() - 1), cameraPanel);
			cameraPanel.repaint();
		}
		else if (e.getSource().equals(playPause)) {
			if(playPause.isSelected()) 
				playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/pause.png")));
			else
				playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/play.png")));
		}
	}
	public void setNewDeviceMonitored(Device device) {
		cameraComboBox.removeAllItems();
		uuidField.setText(null);
		agentField.setText(null);
		mainInterface.getMainExecution().getDeviceMonitoringSystem().setSelectedCamera(null);
		mainInterface.getMainExecution().getDeviceMonitoringSystem().setSelectedComponents(new ArrayList<Component>());
		mainInterface.getMainExecution().getDeviceMonitoringSystem().setSelectedVariables(new ArrayList<Variable>());
		setDeviceMonitored(device);
	}
	public void setDeviceMonitored(Device device) {
		buttonsPanel.removeAll();
		informationPanel.removeAll();
		cameraPanel.removeAll();
		if(mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedCamera() != null) {
			if(mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedCamera().getVideoPanel() != null) 
				mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedCamera().destroyVideoPanel();
		}
		mainInterface.getMainExecution().getDeviceMonitoringSystem().setSelectedDevice(device);
		if(device != null) {
			DeviceMonitoringSystem deviceMonitoringSystem = mainInterface.getMainExecution().getDeviceMonitoringSystem();
			DefaultComboBoxModel<String> cameraModel = new DefaultComboBoxModel<String>();
			cameraModel.addElement(" ");
			for(int i = 0; i < deviceMonitoringSystem.getSelectedDevice().getCameras().size(); i++) 
				cameraModel.addElement(deviceMonitoringSystem.getSelectedDevice().getCameras().get(i).getName());
			if (deviceMonitoringSystem.getSelectedCamera() != null) {
				cameraModel.setSelectedItem(deviceMonitoringSystem.getSelectedCamera().getName());
				deviceMonitoringSystem.setSelectedCamera(deviceMonitoringSystem.getSelectedCamera(), cameraPanel); /////////////////////////configurar método posteriormente
			}
			cameraComboBox.setModel(cameraModel);
			uuidField.setText(deviceMonitoringSystem.getSelectedDevice().getUuid());
			agentField.setText(deviceMonitoringSystem.getSelectedDevice().getAgent().getAgentName());
			ArrayList<Component>axes = new ArrayList<Component>();
			ArrayList<Component>controller = new ArrayList<Component>();
			ArrayList<Component>system = new ArrayList<Component>();
			ArrayList<Component>others = new ArrayList<Component>();
			for(int i = 0; i < device.getComponents().size(); i++) {
				if (device.getComponents().get(i).getComponentType().toUpperCase().equals("AXES"))
					axes.add(device.getComponents().get(i));
				else if (device.getComponents().get(i).getComponentType().toUpperCase().equals("CONTROLLER"))
					controller.add(device.getComponents().get(i));
				else if (device.getComponents().get(i).getComponentType().toUpperCase().equals("SYSTEMS"))
					system.add(device.getComponents().get(i));
				else
					others.add(device.getComponents().get(i));
			}
			int line = 0;
			if(!axes.isEmpty()) {
				JPanel axesPanel = new JPanel();
				axesPanel.setBorder(new TitledBorder("Axes"));
				axesPanel.setLayout(new GridBagLayout());
				((GridBagLayout)axesPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)axesPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)axesPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)axesPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
				buttonsPanel.add(axesPanel, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				line++;
				for(int i = 0; i < axes.size(); i++) {
					JToggleButton componentButton = new JToggleButton();
					if(deviceMonitoringSystem.getSelectedComponents().contains(axes.get(i))) {
						componentButton.setSelected(true);
						setComponentMonitored(axes.get(i));
					}
					componentButton.setText(axes.get(i).getComponent() + "-" + axes.get(i).getName());
					axes.get(i).setComponentButton(componentButton);
					axesPanel.add(componentButton, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
				}
			}
			if(!controller.isEmpty()) {
				JPanel controllerPanel = new JPanel();
				controllerPanel.setBorder(new TitledBorder("Controller"));
				controllerPanel.setLayout(new GridBagLayout());
				((GridBagLayout)controllerPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)controllerPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)controllerPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)controllerPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
				buttonsPanel.add(controllerPanel, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				line++;
				for(int i = 0; i < controller.size(); i++) {
					JToggleButton componentButton = new JToggleButton();
					if(deviceMonitoringSystem.getSelectedComponents().contains(controller.get(i))) {
						componentButton.setSelected(true);
						setComponentMonitored(controller.get(i));
					}
					componentButton.setText(controller.get(i).getComponent() + "-" + controller.get(i).getName());
					controller.get(i).setComponentButton(componentButton);
					controllerPanel.add(componentButton, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
				}
			}
			if(!system.isEmpty()) {
				JPanel systemsPanel = new JPanel();
				systemsPanel.setBorder(new TitledBorder("Systems"));
				systemsPanel.setLayout(new GridBagLayout());
				((GridBagLayout)systemsPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)systemsPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)systemsPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)systemsPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
				buttonsPanel.add(systemsPanel, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				line++;
				for(int i = 0; i < system.size(); i++) {
					JToggleButton componentButton = new JToggleButton();
					if(deviceMonitoringSystem.getSelectedComponents().contains(system.get(i))) {
						componentButton.setSelected(true);
						setComponentMonitored(system.get(i));
					}
					componentButton.setText(system.get(i).getComponent() + "-" + system.get(i).getName());
					system.get(i).setComponentButton(componentButton);
					systemsPanel.add(componentButton, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
				}
			}
			if(!others.isEmpty()) {
				JPanel othersPanel = new JPanel();
				othersPanel.setBorder(new TitledBorder("Others"));
				othersPanel.setLayout(new GridBagLayout());
				((GridBagLayout)othersPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)othersPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)othersPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)othersPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
				buttonsPanel.add(othersPanel, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				for(int i = 0; i < others.size(); i++) {
					JToggleButton componentButton = new JToggleButton();
					if(deviceMonitoringSystem.getSelectedComponents().contains(others.get(i))) {
						componentButton.setSelected(true);
						setComponentMonitored(others.get(i));
					}
					componentButton.setText(others.get(i).getComponent() + "-" + others.get(i).getName());
					others.get(i).setComponentButton(componentButton);
					othersPanel.add(componentButton, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
				}
			}
			cameraComboBox.addActionListener(this);
		}
		revalidate();
		repaint();
	}
	public void setComponentMonitored(Component component) {
		DeviceMonitoringSystem deviceMonitoringSystem = mainInterface.getMainExecution().getDeviceMonitoringSystem();
		if (!deviceMonitoringSystem.getSelectedComponents().contains(component)) 
			deviceMonitoringSystem.getSelectedComponents().add(component);
		ArrayList<Variable>sample = new ArrayList<Variable>();
		ArrayList<Variable>event = new ArrayList<Variable>();
		ArrayList<Variable>condition = new ArrayList<Variable>();
		for (int i = 0; i < component.getVariables().size(); i++) {
			if(component.getVariables().get(i).getDivision() == 'S')
				sample.add(component.getVariables().get(i));
			else if(component.getVariables().get(i).getDivision() == 'E')
				event.add(component.getVariables().get(i));
			else if(component.getVariables().get(i).getDivision() == 'C')
				condition.add(component.getVariables().get(i));
		}
		JPanel componentPanel = new JPanel();
		componentPanel.setBorder(new TitledBorder(component.getComponent() + "-" + component.getName()));
		componentPanel.setLayout(new GridBagLayout());
		((GridBagLayout)componentPanel.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)componentPanel.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)componentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
		((GridBagLayout)componentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
		int line = 0;
		component.setComponentPanel(componentPanel);
		if(!sample.isEmpty()) {
			JPanel samplePanel = new JPanel();
			samplePanel.setBorder(new TitledBorder("Sample"));
			samplePanel.setLayout(new GridBagLayout());
			((GridBagLayout)samplePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 50};
			((GridBagLayout)samplePanel.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)samplePanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)samplePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
			componentPanel.add(samplePanel, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			line++;
			addVariablestoDeviceMonitoring(sample, samplePanel);
		}
		if(!event.isEmpty()) {
			JPanel eventPanel = new JPanel();
			eventPanel.setBorder(new TitledBorder("Event:"));
			eventPanel.setLayout(new GridBagLayout());
			((GridBagLayout)eventPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 50};
			((GridBagLayout)eventPanel.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)eventPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)eventPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
			componentPanel.add(eventPanel, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			line++;
			addVariablestoDeviceMonitoring(event, eventPanel);
		}
		if(!condition.isEmpty()) {
			JPanel conditionPanel = new JPanel();
			conditionPanel.setBorder(new TitledBorder("Condition:"));
			conditionPanel.setLayout(new GridBagLayout());
			((GridBagLayout)conditionPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 50};
			((GridBagLayout)conditionPanel.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)conditionPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)conditionPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
			componentPanel.add(conditionPanel, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			addVariablestoDeviceMonitoring(condition, conditionPanel);
		}
		informationPanel.add(componentPanel, new GridBagConstraints(0, component.getDevice().getComponents().indexOf(component), 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));	
		revalidate();
		repaint();
	}
	public void destroyComponentMonitored(Component component) {
		DeviceMonitoringSystem deviceMonitoringSystem = mainInterface.getMainExecution().getDeviceMonitoringSystem();
		if (deviceMonitoringSystem.getSelectedComponents().contains(component)) {
			deviceMonitoringSystem.getSelectedComponents().remove(component);
		}
		for(int i = 0; i < component.getVariables().size(); i++) {
			component.getVariables().get(i).setMonitoringCheckBox(null);
			component.getVariables().get(i).setDeviceMonitoringRegister(null);
		}
		informationPanel.remove(component.getComponentPanel());
		component.setComponentPanel(null);
		revalidate();
		repaint();
	}
	public void addVariablestoDeviceMonitoring(ArrayList<Variable> variables, JPanel container) {
		for(int i = 0; i < variables.size(); i++) {
			JCheckBox varMonitored = new JCheckBox();
			JLabel name = new JLabel("name:");
			JTextField vName = new JTextField(variables.get(i).getName());
			vName.setEditable(false);
			JLabel id = new JLabel("id:");
			JTextField idName = new JTextField(variables.get(i).getDataItemID());
			idName.setEditable(false);
			JLabel twoPoints = new JLabel(":");
			JTextField value = new JTextField();
			value.setEditable(false);
			JLabel units = new JLabel(variables.get(i).getUnit());
			container.add(varMonitored, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			container.add(name, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			container.add(vName, new GridBagConstraints(2, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			container.add(id, new GridBagConstraints(3, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			container.add(idName, new GridBagConstraints(4, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			container.add(twoPoints, new GridBagConstraints(5, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			container.add(value, new GridBagConstraints(6, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			container.add(units, new GridBagConstraints(7, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			variables.get(i).setMonitoringCheckBox(varMonitored);
			variables.get(i).setDeviceMonitoringRegister(value);
			if(mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedVariables().contains(variables.get(i))) 
				setVariablesMonitored(variables.get(i));
		}
		
	}
	public void setVariablesMonitored(Variable variable) {
		if (!mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedVariables().contains(variable))
			mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedVariables().add(variable);
		mainInterface.getMainExecution().getIoControl().getLoadExecution().addToVariableList(variable);
		variable.getMonitoringCheckBox().setSelected(true);
		revalidate();
		repaint();
	}
	public void destroyVariablesMonitored(Variable variable) {
		variable.getDeviceMonitoringRegister().setText(null);
		if(mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedVariables().contains(variable)) {
			mainInterface.getMainExecution().getDeviceMonitoringSystem().getSelectedVariables().remove(variable);
		}
		revalidate();
		repaint();
	}
////////////////////////////////////////////Getters And Setters//////////////////////////////////////////////////////////
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}
}

package br.UFSC.GRIMA.visual;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import br.UFSC.GRIMA.IO.Agent;
import br.UFSC.GRIMA.IO.Camera;
import br.UFSC.GRIMA.IO.MainExecution;
import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.operational.MonitoringUnit;

public class MainInterface extends MainWindow implements ActionListener {
	private MainExecution mainExecution;
	private DeviceMonitoringPanelEvents deviceMonitoringPanelEvents;
	private PanelMonitoringPanel panelMonitoringPanel;
	private ViewDevicesEvents viewDevicesEvents;
//////////////////////////Constructor///////////////////////////////////////////////////////////////////
	public MainInterface(MainExecution mainExecution)	{
		setMainExecution(mainExecution);
		menuExit.addActionListener(this);
		menuPreferences.addActionListener(this);
		menuDeviceConfigure.addActionListener(this);
		menuDatabase.addActionListener(this);
		menuAgents.addActionListener(this);
		menuAddAgent.addActionListener(this);
		menuDeviceInfo.addActionListener(this);
		menuCameraInfo.addActionListener(this);
		menuAddCamera.addActionListener(this);
		menuViewPanels.addActionListener(this);
		menuAddPanel.addActionListener(this);
		menuAbout.addActionListener(this);
		deviceInfoButton.addActionListener(this);
		deviceMonitoringButton.addActionListener(this);
		panelMonitoringButton.addActionListener(this);
		currentTimeField.setEditable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
//////////////////////////Methods///////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(menuExit)) 
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if (e.getSource().equals(menuPreferences)) {
//			this.setEnabled(false);
			///////////fazer Preferences Window
			new PreferencesEvents();
		}
		else if (e.getSource().equals(menuDeviceConfigure) || e.getSource().equals(menuDeviceInfo) || e.getSource().equals(menuCameraInfo)) {
			this.setEnabled(false);
			setViewDevicesEvents(new ViewDevicesEvents(this));
		}
		else if (e.getSource().equals(menuDatabase)) {
			/////////////fazer configure Database
		}
		else if (e.getSource().equals(menuAgents)) {
			/////////////fazer janela view Agents
		}
		else if (e.getSource().equals(menuAddAgent)) {
			this.setEnabled(false);
			new AddAgentEvents(this);
		}
		else if (e.getSource().equals(menuAddCamera)) {
			this.setEnabled(false);
			new AddCameraEvents(this);
		}
		else if (e.getSource().equals(menuViewPanels)) {
			panelMonitoringButton.doClick();//////////////////ficar de baga
		}
		else if (e.getSource().equals(menuAddPanel)) {
			this.setEnabled(false);
			if (deviceInfoButton.isSelected())
				new ConfigurePanelEvents(this, (ArrayList<Variable>) mainExecution.getNewMonitoringPanelList().clone());
			else if (deviceMonitoringButton.isSelected())
				new ConfigurePanelEvents(this, (ArrayList<Variable>) mainExecution.getDeviceMonitoringSystem().getSelectedVariables().clone());
			else
				new ConfigurePanelEvents(this, null);
		}
		else if (e.getSource().equals(menuAbout)) {
			this.setEnabled(false);
			AboutWindow aboutWindow = new AboutWindow(this);
			aboutWindow.okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					aboutWindow.dispose();
				}
			});
			MainInterface mainInterface = this;
			aboutWindow.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					windowClosed(e);
				}
				@Override
				public void windowClosed(WindowEvent e) {
					mainInterface.setEnabled(true);
					mainInterface.toFront();
				}
			});
			aboutWindow.setVisible(true);
//			System.out.println("cheogu");
		}
		if (e.getSource().equals(deviceInfoButton) || e.getSource().equals(deviceMonitoringButton) || e.getSource().equals(panelMonitoringButton)) {
			if(!((JToggleButton) e.getSource()).isSelected()) {
				((JToggleButton) e.getSource()).setSelected(true);
			}
			else {
				if (e.getSource().equals(deviceInfoButton)) {
					if (deviceMonitoringButton.isSelected()) {
						destroyDeviceMonitoringPanel();
					}
					if (panelMonitoringButton.isSelected()) {
						destroyPanelMonitoringLayout();
					}
					setViewDevicesPanel();
				}
				else if (e.getSource().equals(deviceMonitoringButton)) {
					if (deviceInfoButton.isSelected()) {
						destroyViewDevicesPanel();
					}
					if (panelMonitoringButton.isSelected()) {
						destroyPanelMonitoringLayout();
					}
					setDeviceMonitoringPanel();
				}
				else if (e.getSource().equals(panelMonitoringButton)) {
					if (deviceInfoButton.isSelected()) {
						destroyViewDevicesPanel();
					}
					if (deviceMonitoringButton.isSelected()) {
						destroyDeviceMonitoringPanel();
					}
					setPanelMonitoringLayout();
				}
			}
		}
	}
	public void setMenuDeviceMonitor() {
		menuDevices.removeAll();
		ArrayList<Device> devices = mainExecution.getAllDevices();
		for(int i = 0; i < devices.size(); i++) {
			JMenuItem deviceMenu = new JMenuItem(devices.get(i).getName());
			devices.get(i).setMonitoringMenu(deviceMenu);
			deviceMenu.addActionListener(devices.get(i));
			menuDevices.add(deviceMenu);
		}
	}
	public void setMenuViewCameras() {
		menuView.removeAll();
		ArrayList<Camera>cameras = mainExecution.getAllCameras();
		for(int i = 0; i < cameras.size(); i++) {
			JMenuItem cameraMenu = new JMenuItem(cameras.get(i).getCompactName());
			cameraMenu.setToolTipText(cameras.get(i).getName());
			cameras.get(i).setViewMenu(cameraMenu);
			cameraMenu.addActionListener(cameras.get(i));
			menuView.add(cameraMenu);
		}
	}
	public void setMenuConfigurePanel() {
		menuPanels.removeAll();
		ArrayList<MonitoringUnit> monitoringUnits = mainExecution.getPanelMonitoringSystem().getMonitoringUnits();
		for(int i = 0; i < monitoringUnits.size(); i++) {
			JMenuItem panelMenu = new JMenuItem(monitoringUnits.get(i).getName());
			monitoringUnits.get(i).setMenuItem(panelMenu);
			panelMenu.addActionListener(monitoringUnits.get(i));
			menuPanels.add(panelMenu);
		}
	}
	public void setViewDevicesPanel() {
		ViewDevicesPanel viewDevicesPanel = new ViewDevicesPanel();
		workSpace.add(viewDevicesPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		ArrayList<Device> devices = mainExecution.getAllDevices();
		for (int i = 0; i < devices.size(); i++) {
			JPanel devicePanel = new JPanel();
			devicePanel.setBorder(new TitledBorder(devices.get(i).getName()));
			devicePanel.setLayout(new GridBagLayout());
			((GridBagLayout)devicePanel.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)devicePanel.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)devicePanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)devicePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
			JLabel uuid = new JLabel("UUID:");
			JTextField uuidName = new JTextField(devices.get(i).getUuid());
			uuidName.setEditable(false);
			JLabel agent = new JLabel("Agent:");
			JTextField agentName = new JTextField(devices.get(i).getAgent().getAgentName());
			agentName.setEditable(false);
			JLabel components = new JLabel("Components: ");
			devicePanel.add(uuid, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			devicePanel.add(uuidName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			devicePanel.add(agent, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			devicePanel.add(agentName, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			devicePanel.add(components, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			viewDevicesPanel.devicesPanel.add(devicePanel, new GridBagConstraints(i, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			for(int j = 0; j < devices.get(i).getComponents().size(); j++) {
				JPanel componentPanel = new JPanel();
				componentPanel.setBorder(new TitledBorder(devices.get(i).getComponents().get(j).getComponent() + "-" + devices.get(i).getComponents().get(j).getName()));
				componentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)componentPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)componentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)componentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)componentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
				JLabel id = new JLabel("ID:");
				JTextField idName = new JTextField(devices.get(i).getComponents().get(j).getComponentID());
				idName.setEditable(false);
				JLabel type = new JLabel("Type:");
				JTextField typeName = new JTextField(devices.get(i).getComponents().get(j).getComponentType());
				typeName.setEditable(false);
				JLabel variables = new JLabel("Variables: ");
				componentPanel.add(id, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				componentPanel.add(idName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				componentPanel.add(type, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				componentPanel.add(typeName, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				componentPanel.add(variables, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				devicePanel.add(componentPanel, new GridBagConstraints(0, j + 2, 4, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				ArrayList<Variable> sample = new ArrayList<Variable>();
				ArrayList<Variable> event = new ArrayList<Variable>();
				ArrayList<Variable> condition = new ArrayList<Variable>();
				for(int k = 0; k < devices.get(i).getComponents().get(j).getVariables().size(); k++) {
					if (devices.get(i).getComponents().get(j).getVariables().get(k).getDivision() == 'S')
						sample.add(devices.get(i).getComponents().get(j).getVariables().get(k));
					else if (devices.get(i).getComponents().get(j).getVariables().get(k).getDivision() == 'E')
						event.add(devices.get(i).getComponents().get(j).getVariables().get(k));
					else if (devices.get(i).getComponents().get(j).getVariables().get(k).getDivision() == 'C')
						condition.add(devices.get(i).getComponents().get(j).getVariables().get(k));
				}
				int line = 2;
				if(!sample.isEmpty()) {
					JPanel samplePanel = new JPanel();
					samplePanel.setBorder(new TitledBorder("Sample"));
					samplePanel.setLayout(new GridBagLayout());
					((GridBagLayout)samplePanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)samplePanel.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)samplePanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
					((GridBagLayout)samplePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
					componentPanel.add(samplePanel, new GridBagConstraints(0, line, 4, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					line++;
					addVariablestoViewDevice(sample, samplePanel);
				}
				if(!event.isEmpty()) {
					JPanel eventPanel = new JPanel();
					eventPanel.setBorder(new TitledBorder("Event:"));
					eventPanel.setLayout(new GridBagLayout());
					((GridBagLayout)eventPanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)eventPanel.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)eventPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
					((GridBagLayout)eventPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
					componentPanel.add(eventPanel, new GridBagConstraints(0, line, 4, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					line++;
					addVariablestoViewDevice(event, eventPanel);
				}
				if(!condition.isEmpty()) {
					JPanel conditionPanel = new JPanel();
					conditionPanel.setBorder(new TitledBorder("Condition:"));
					conditionPanel.setLayout(new GridBagLayout());
					((GridBagLayout)conditionPanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)conditionPanel.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)conditionPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
					((GridBagLayout)conditionPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
					componentPanel.add(conditionPanel, new GridBagConstraints(0, line, 4, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					addVariablestoViewDevice(condition, conditionPanel);
				}
				
			}
		}
		revalidate();
		repaint();
	}
	public void setDeviceMonitoringPanel() {
		setDeviceMonitoringPanelEvents(new DeviceMonitoringPanelEvents(this));
		revalidate();
		repaint();
	}
	public void setPanelMonitoringLayout() {
		setPanelMonitoringPanel(new PanelMonitoringPanel());
		workSpace.add(panelMonitoringPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		double[] buttonsLayout = new double[mainExecution.getPanelMonitoringSystem().getMonitoringUnits().size() + 1];
		buttonsLayout[buttonsLayout.length - 1] = 1.0;
		((GridBagLayout)panelMonitoringPanel.buttonPanel.getLayout()).rowWeights = buttonsLayout;
		double[] panelLayout = new double[mainExecution.getPanelMonitoringSystem().getMonitoringUnits().size()];
		for(int i = 0; i < panelLayout.length; i++)
			panelLayout[i] = 1.0;
		((GridBagLayout)panelMonitoringPanel.panelSupport.getLayout()).columnWeights = panelLayout;
		for(int i = 0; i < mainExecution.getPanelMonitoringSystem().getMonitoringUnits().size(); i++) {
			JToggleButton panelButton = new JToggleButton(mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).getName());
			panelButton.setSelected(true);
			panelMonitoringPanel.buttonPanel.add(panelButton, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			JPanel monitoringPanel = new JPanel();
			mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).initPanel(monitoringPanel, panelButton); //////////////////mudar o container posteriormente
			mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).refreshChart();
			panelMonitoringPanel.panelSupport.add(monitoringPanel, new GridBagConstraints(i, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
		}
		revalidate();
		repaint();
	}
	public void destroyViewDevicesPanel() {
		ArrayList<Agent> agents = mainExecution.getAllAgents();
		for (int i = 0; i < agents.size(); i++) {
			for(int j = 0; j < agents.get(i).getDevices().size(); j++) {
				for(int k = 0; k < agents.get(i).getDevices().get(j).getComponents().size(); k++) {
					for (int l = 0; l <  agents.get(i).getDevices().get(j).getComponents().get(k).getVariables().size(); l++) {
						agents.get(i).getDevices().get(j).getComponents().get(k).getVariables().get(l).setStartMonitoringPanel(null);
						agents.get(i).getDevices().get(j).getComponents().get(k).getVariables().get(l).setVarMonitored(null);
						agents.get(i).getDevices().get(j).getComponents().get(k).getVariables().get(l).setVarSaving(null);
					}
				}
			}
		}
		workSpace.removeAll();
		deviceInfoButton.setSelected(false);
	}
	public void destroyDeviceMonitoringPanel() {
		if(mainExecution.getDeviceMonitoringSystem().getSelectedCamera() != null) {
			deviceMonitoringPanelEvents.cameraPanel.removeAll();
			mainExecution.getDeviceMonitoringSystem().getSelectedCamera().destroyVideoPanel();
		}
		setDeviceMonitoringPanelEvents(null);
		workSpace.removeAll();
		deviceMonitoringButton.setSelected(false);
	}
	public void destroyPanelMonitoringLayout() {
		for(int i = 0; i < mainExecution.getPanelMonitoringSystem().getMonitoringUnits().size(); i++)
			mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).destroyPanelInstance();
		workSpace.removeAll();
		panelMonitoringButton.setSelected(false);
	}
	public void setLoopTime(Long millis) {
		loopTimeField.setText(millis + " milliseconds");
	}
/////////////////////supportMethods///////////////////////////////////
	
	public void addVariablestoViewDevice(ArrayList<Variable> variables, JPanel panel) {
		for(int a = 0; a < variables.size();a++) {
			JCheckBox monitorInit = new JCheckBox();
			JLabel variable = new JLabel("Name:");
			JTextField variableName = new JTextField(variables.get(a).getName());
			variableName.setEditable(false);
			JLabel variableId = new JLabel("ID:");
			JTextField variableIdName = new JTextField(variables.get(a).getDataItemID());
			variableIdName.setEditable(false);
			JLabel units = new JLabel("Units:");
			JTextField unitsName = new JTextField(variables.get(a).getUnit());
			unitsName.setEditable(false);
			JLabel varType;
			if(variables.get(a).getType() == '1') {
				varType = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/numberTypeIcon.png")));
				varType.setToolTipText("Numeric Variable Type: this variable shows its information through number values.");
			}
			else if(variables.get(a).getType() == 'c') { 
				varType = new JLabel((new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/categoryTypeIcon.png"))));
				varType.setToolTipText("Category Variable Type: this variable shows its information through categories that describe some kind of information.");
			}
			else if(variables.get(a).getType() == 'i') {
				varType = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
				varType.setToolTipText("Irregular Variable Type: this variable show values that is neither numeric nor category variable Type.");
			}
			else {
				varType = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/nullTypeIcon.png")));
				varType.setToolTipText("Null Variable Type: this variable didn't show any register yet, so the application cannot identify its type.");
			}
			JToggleButton monitored = new JToggleButton(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/monitoringIcon.png")));
			JToggleButton saving = new JToggleButton(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/downloadIcon.png")));
			variables.get(a).setStartMonitoringPanel(monitorInit);
			variables.get(a).setVarMonitored(monitored);
			variables.get(a).setVarSaving(saving);
			panel.add(monitorInit, new GridBagConstraints(0, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(variable, new GridBagConstraints(1, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(variableName, new GridBagConstraints(2, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(variableId, new GridBagConstraints(3, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(variableIdName, new GridBagConstraints(4, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(units, new GridBagConstraints(5, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(unitsName, new GridBagConstraints(6, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(varType, new GridBagConstraints(7, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(monitored, new GridBagConstraints(8, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			panel.add(saving, new GridBagConstraints(9, a, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
		}
	}
	public void setCurrentTime(String s) {
		currentTimeField.setText(s);
	}
	
////////////////////////////////Getters and Setters////////////////////////////////////////////////////////
	public MainExecution getMainExecution() {
		return mainExecution;
	}

	public void setMainExecution(MainExecution mainExecution) {
		this.mainExecution = mainExecution;
	}
	public DeviceMonitoringPanelEvents getDeviceMonitoringPanelEvents() {
		return deviceMonitoringPanelEvents;
	}
	public void setDeviceMonitoringPanelEvents(DeviceMonitoringPanelEvents deviceMonitoringPanelEvents) {
		this.deviceMonitoringPanelEvents = deviceMonitoringPanelEvents;
	}
	public PanelMonitoringPanel getPanelMonitoringPanel() {
		return panelMonitoringPanel;
	}
	public void setPanelMonitoringPanel(PanelMonitoringPanel panelMonitoringPanel) {
		this.panelMonitoringPanel = panelMonitoringPanel;
	}
	public ViewDevicesEvents getViewDevicesEvents() {
		return viewDevicesEvents;
	}
	public void setViewDevicesEvents(ViewDevicesEvents viewDevicesEvents) {
		this.viewDevicesEvents = viewDevicesEvents;
	}
}

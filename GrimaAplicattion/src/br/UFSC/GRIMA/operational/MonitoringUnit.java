package br.UFSC.GRIMA.operational;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.visual.ConfigurePanelEvents;

public abstract class MonitoringUnit implements ActionListener {
	///////////vital informations///////////////////////////////////////////////////////
	private String name;
	private PanelMonitoringSystem panelMonitoringSystem;
	private int[] timeRange;
	private String chartType;
	private ArrayList<Variable> variables;
	private char panelType;
	private int minimumWhidth;
	private int minimumHeight;
	private boolean visible = true;
	private JMenuItem menuItem;
	///////////RunTimeCreateInformations//////////
	private JPanel monitoringPanel;
	//////////////panel Components///////////////
	private JToggleButton playPause;
	private JToggleButton panelButton;
	private JButton settingButton;
/////////////////////////////////////////////Constructor/////////////////////////////////////////////////////////////////
	public MonitoringUnit(String name, PanelMonitoringSystem panelMonitoringSystem, int[] timeRange, String chartType, ArrayList<Variable> variables, char panelType) {
		// TODO Auto-generated constructor stub
		setName(name);
		setPanelMonitoringSystem(panelMonitoringSystem);
		setTimeRange(timeRange);
		setChartType(chartType);
		setVariables(variables);
		setPanelType(panelType);
		setMinimumWhidth(300);
		setMinimumHeight(300);
	}
////////////////////////////////////////Methods///////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		if(settingButton != null) {
			if(e.getSource().equals(settingButton)) {
				panelMonitoringSystem.getController().getMainInterface().setEnabled(false);
				new ConfigurePanelEvents(this);
			}
		}
		if(e.getSource().equals(playPause)) {
			freezeChart(playPause.isSelected());
			if(playPause.isSelected()) 
				playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/pause.png")));
			else
				playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/play.png")));
		}
		else if(e.getSource().equals(panelButton)) {
			monitoringPanel.setVisible(panelButton.isSelected());
			setVisible(panelButton.isSelected());
		}
		else if(e.getSource().equals(menuItem)) {
			panelMonitoringSystem.getController().getMainInterface().setEnabled(false);
			new ConfigurePanelEvents(this);
		}
		else
			actionPerformed2(e);
	}
	public abstract void actionPerformed2(ActionEvent e);
	public abstract void freezeChart(boolean freeze);
	public void initPanel(JPanel monitoringPanel, JToggleButton panelButton) {
		setMonitoringPanel(monitoringPanel);
		setPanelButton(panelButton);
		panelButton.addActionListener(this);
		monitoringPanel.setBorder(new TitledBorder(name));
		monitoringPanel.setLayout(new GridBagLayout());
		((GridBagLayout)monitoringPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
		((GridBagLayout)monitoringPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)monitoringPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
		((GridBagLayout)monitoringPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		JLabel timeR = new JLabel("Time Range:");
		JTextField timeName = new JTextField(getTimeRange()[0] + "h :  " + getTimeRange()[1] + "m :  " + getTimeRange()[2] + "s");
		timeName.setEditable(false);
		JToggleButton playPause = new JToggleButton();
		playPause.setSelected(true);
		playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/pause.png")));
		JButton settings = new JButton(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/Gears-icon.png")));
		JLabel chartT = new JLabel("Chart Type: ");
		JTextField chartName = new JTextField(getChartType());
		chartName.setEditable(false);
		JPanel variables = new JPanel();
		variables.setLayout(new GridBagLayout());
		((GridBagLayout)variables.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)variables.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)variables.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
		((GridBagLayout)variables.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
		variables.setBorder(new TitledBorder(""));
		JLabel typeField = new JLabel("Type ");
		JLabel varField = new JLabel("Variable");
		JLabel valueField = new JLabel("Value");
		JLabel unitsField = new JLabel("Units");
		JLabel displayField = new JLabel("Display");
		variables.add(typeField, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variables.add(varField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variables.add(valueField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variables.add(unitsField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variables.add(displayField, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		for(int j = 0; j < getVariables().size(); j++) {
			JLabel type = new JLabel();
			if(getVariables().get(j).getType() == '1') {
				type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/numberTypeIcon.png")));
				type.setToolTipText("Numeric Variable Type: this variable shows its information through number values.");
			}
			else if(getVariables().get(j).getType() == 'c') { 
				type = new JLabel((new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/categoryTypeIcon.png"))));
				type.setToolTipText("Category Variable Type: this variable shows its information through categories that describe some kind of information.");
			}
			else if(getVariables().get(j).getType() == 'i') {
				type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
				type.setToolTipText("Irregular Variable Type: this variable shows values that is neither numeric nor category variable Type.");
			}
			else { 
				type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/nullTypeIcon.png")));
				type.setToolTipText("Null Variable Type: this variable didn't show any register yet, so the application cannot identify its type.");
			}
			JLabel var;
			if (getVariables().get(j).getName() != null) 
				var = new JLabel(getVariables().get(j).getName());
			else
				var = new JLabel(getVariables().get(j).getDataItemID());
			JLabel twoPoints = new JLabel(":");
			JTextField value = new JTextField();
			value.setEditable(false);
			JLabel units = new JLabel(getVariables().get(j).getUnit());
//			JToggleButton display = new JToggleButton();
//			display.setSelected(true);
			variables.add(type, new GridBagConstraints(0, j + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			variables.add(var, new GridBagConstraints(1, j + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			variables.add(twoPoints, new GridBagConstraints(2, j + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			variables.add(value, new GridBagConstraints(3, j + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			variables.add(units, new GridBagConstraints(4, j + 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
//			variables.add(display, new GridBagConstraints(5, j + 1, 1, 1, 0.0, 0.0,
//					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//					new Insets(0, 0, 5, 5), 0, 0));
//			mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).getVariableBuffers().get(j).setTypeLabel(type);
//			mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).getVariableBuffers().get(j).setValueTextField(value);
//			mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).getVariableBuffers().get(j).setDisplayButton(display);
//			display.addActionListener(mainExecution.getPanelMonitoringSystem().getMonitoringUnits().get(i).getVariableBuffers().get(j));
			addVariableControl(getVariables().get(j), variables, j + 1, type, value);
		}
		monitoringPanel.add(timeR, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		monitoringPanel.add(timeName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		monitoringPanel.add(playPause, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		monitoringPanel.add(settings, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		monitoringPanel.add(chartT, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		monitoringPanel.add(chartName, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		monitoringPanel.add(variables, new GridBagConstraints(0, 3, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		setPlayPause(playPause);
		setSettingButton(settings);
		panelButton.setSelected(visible);
		panelButton.addActionListener(this);
		playPause.addActionListener(this);
		settings.addActionListener(this);
		initAditionalPanelElements();
		panelMonitoringSystem.getController().getMainInterface().revalidate();
		panelMonitoringSystem.getController().getMainInterface().repaint();
	}
	public abstract void addVariableControl(Variable variable, JPanel container, int line, JLabel typeLabel, JTextField valueField);
	public abstract void initAditionalPanelElements();
	public void refreshChart() {
		refreshChart2();
		panelMonitoringSystem.getController().getMainInterface().revalidate();
		panelMonitoringSystem.getController().getMainInterface().repaint();
	}
	public abstract void refreshChart2();
	public abstract void destroyPanelInstance(); 
/////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(int[] timeRange) {
		this.timeRange = timeRange;
	}
	public PanelMonitoringSystem getPanelMonitoringSystem() {
		return panelMonitoringSystem;
	}
	public void setPanelMonitoringSystem(PanelMonitoringSystem panelMonitoringSystem) {
		this.panelMonitoringSystem = panelMonitoringSystem;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	public int getMinimumWhidth() {
		return minimumWhidth;
	}
	public void setMinimumWhidth(int minimumWhidth) {
		this.minimumWhidth = minimumWhidth;
	}
	public char getPanelType() {
		return panelType;
	}
	public void setPanelType(char panelType) {
		this.panelType = panelType;
	}
	public JMenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(JMenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public int getMinimumHeight() {
		return minimumHeight;
	}
	public void setMinimumHeight(int minimumHeight) {
		this.minimumHeight = minimumHeight;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		if(panelMonitoringSystem.getMonitoringUnits().indexOf(this) >= 0) {
			if(visible)
				((GridBagLayout)panelMonitoringSystem.getController().getMainInterface().getPanelMonitoringPanel().panelSupport.getLayout()).columnWeights[panelMonitoringSystem.getMonitoringUnits().indexOf(this)] = 1.0;
			else
				((GridBagLayout)panelMonitoringSystem.getController().getMainInterface().getPanelMonitoringPanel().panelSupport.getLayout()).columnWeights[panelMonitoringSystem.getMonitoringUnits().indexOf(this)] = 0.0;
		}
		this.visible = visible;
	}
	public JToggleButton getPlayPause() {
		return playPause;
	}
	public void setPlayPause(JToggleButton playPause) {
		this.playPause = playPause;
	}
	public JToggleButton getPanelButton() {
		return panelButton;
	}
	public void setPanelButton(JToggleButton panelButton) {
		this.panelButton = panelButton;
	}
	public JButton getSettingButton() {
		return settingButton;
	}
	public void setSettingButton(JButton settingButton) {
		this.settingButton = settingButton;
	}
	public JPanel getMonitoringPanel() {
		return monitoringPanel;
	}
	public void setMonitoringPanel(JPanel monitoringPanel) {
		this.monitoringPanel = monitoringPanel;
	}
}

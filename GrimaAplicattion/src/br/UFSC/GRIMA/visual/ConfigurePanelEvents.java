package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.operational.CategoryMonitoringUnit;
import br.UFSC.GRIMA.operational.MonitoringUnit;
import br.UFSC.GRIMA.operational.NumericMonitoringUnit;

public class ConfigurePanelEvents extends ConfigurePanelWindow implements ActionListener {
	private MonitoringUnit monitoringUnit;
/////////////////constructor/////////////////////////////////////////////////////////////
	public ConfigurePanelEvents(MonitoringUnit monitoringUnit) {
		setMonitoringUnit(monitoringUnit);
		this.setTitle("Configure " + monitoringUnit.getName());
		hourFiled.setValue(monitoringUnit.getTimeRange()[0]);
		minuteField.setValue(monitoringUnit.getTimeRange()[1]);
		secondField.setValue(monitoringUnit.getTimeRange()[2]);
		whidthField.setValue(monitoringUnit.getMinimumWhidth());
		heightField.setValue(monitoringUnit.getMinimumHeight());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				windowClosed(windowEvent);
			}
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		    	MainInterface mainInterface = monitoringUnit.getPanelMonitoringSystem().getController().getMainInterface();
				mainInterface.setEnabled(true);
				mainInterface.toFront();
				mainInterface.setViewDevicesEvents(null);
				ArrayList<Device> devices = mainInterface.getMainExecution().getAllDevices();
				for (int i = 0; i < devices.size(); i++) {
					devices.get(i).setChangeNameButton(null);
					devices.get(i).setNameTextField(null);
				}
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
			}
		});
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		////////////////types of chart/////////////////////////////////
		if (monitoringUnit.getPanelType() == 'c')
			model.addElement("StepLineChart");
		else {
			model.addElement("LineChart");
			model.addElement("AreaChart");
		}
		chartTypeCombobox.setModel(model);
		chartTypeCombobox.setSelectedItem(monitoringUnit.getChartType());
		minimizeButton.addActionListener(this);
		maximizeButton.addActionListener(this);
		removePanelButton.addActionListener(this);
		clonePanelButton.addActionListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}
///////////////////////////////////Methods///////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(minimizeButton)) {
			monitoringUnit.getPanelButton().setSelected(false);
			if(monitoringUnit.getMonitoringPanel() != null)
				monitoringUnit.setVisible(false);
			monitoringUnit.setVisible(false);
			this.dispose();
		}
		else if(e.getSource().equals(maximizeButton)) {
			for(int i = 0; i < monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().size(); i++) {
				 monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).getPanelButton().setSelected(false);
				if(monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).getMonitoringPanel() != null)
					monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).setVisible(false);
				monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).setVisible(false);
			}
			monitoringUnit.getPanelButton().doClick();
			this.dispose();
		}
		else if(e.getSource().equals(removePanelButton)) {
			monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().remove(monitoringUnit);
			this.dispose();
		}
		else if(e.getSource().equals(clonePanelButton)) {
			String defaultName = null;
			int i = 0;
			while(defaultName == null) {
				boolean create = true;
				for (int j = 0; j < monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().size(); j++) {
					if(monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(j).getName().equals( "Clone"+ i)) {
						create = false;
						break;
					}
				}
				if (create) {
					defaultName = ("Clone"+ i);
				}
				i++;
			}
			if((monitoringUnit.getChartType().equals("LineChart")) || (monitoringUnit.getChartType().equals("AreaChart")))
				monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().add(new NumericMonitoringUnit(defaultName, monitoringUnit.getPanelMonitoringSystem(), monitoringUnit.getTimeRange(), monitoringUnit.getChartType(), monitoringUnit.getVariables(), monitoringUnit.getPanelType()));
			if(monitoringUnit.getChartType().equals("StepLineChart"))
				monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().add(new CategoryMonitoringUnit(defaultName, monitoringUnit.getPanelMonitoringSystem(), monitoringUnit.getTimeRange(), monitoringUnit.getChartType(), monitoringUnit.getVariables(), monitoringUnit.getPanelType()));
			this.dispose();
		}
		else if(e.getSource().equals(cancelButton))
			this.dispose();
		else if(e.getSource().equals(okButton)) {
			int[] timeRange = new int[]{(int)hourFiled.getValue(), (int)minuteField.getValue(), (int)secondField.getValue()};
			if((timeRange[0] == 0) && (timeRange[1] == 0) && (timeRange[2] == 0)) 
				JOptionPane.showMessageDialog(null, "The Time Range cannot be empty, insert a valid Time Range.", "Time Range Error", JOptionPane.ERROR_MESSAGE);
			else {
				monitoringUnit.setTimeRange(timeRange);
				monitoringUnit.setChartType((String)chartTypeCombobox.getSelectedItem());
				monitoringUnit.setMinimumHeight((int) heightField.getValue());
				monitoringUnit.setMinimumWhidth((int) whidthField.getValue());
				this.dispose();
			}
		}
	}
///////////////////////////////Getters and Setters//////////////////////////////////////////
//
	public MonitoringUnit getMonitoringUnit() {
		return monitoringUnit;
	}
	public void setMonitoringUnit(MonitoringUnit monitoringUnit) {
		this.monitoringUnit = monitoringUnit;
	}
}

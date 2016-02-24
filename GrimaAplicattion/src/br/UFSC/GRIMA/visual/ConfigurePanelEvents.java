package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.operational.MonitoringUnit;

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
		removePanelButton.addActionListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}
///////////////////////////////////Methods///////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(removePanelButton)) {
			monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().remove(monitoringUnit);
			this.dispose();
		}
		else if(e.getSource().equals(cancelButton))
			this.dispose();
		else if(e.getSource().equals(okButton)) {
//			int[] timeRange = new int{hourFiled.getValue(), minuteField.getValue(), secondField.getValue()};
			monitoringUnit.setChartType((String)chartTypeCombobox.getSelectedItem());
			
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

package br.UFSC.GRIMA.operational;

import java.util.ArrayList;

import br.UFSC.GRIMA.IO.MainExecution;
import br.UFSC.GRIMA.dataStructure.Variable;

public class PanelMonitoringSystem {
	private MainExecution controller;
	private ArrayList<MonitoringUnit> monitoringUnits;
////////////////////////////////////Constructor/////////////////////////////////////////////////////////////
	public PanelMonitoringSystem (MainExecution mainExecution) {
		setController(mainExecution);
		setMonitoringUnits(new ArrayList<MonitoringUnit>());
	}
/////////////////////////////////Methods///////////////////////////////////////////////////////////////////////
	public boolean isVariableMonitored(Variable variable) {
		for(int i = 0; i < monitoringUnits.size(); i++) {
			if(monitoringUnits.get(i).getVariables().contains(variable))
				return true;
		}
		return false;
	}
///////////////////////////////Getters and Setters////////////////////////////////////////////////////////////////////
	public MainExecution getController() {
		return controller;
	}
	public void setController(MainExecution controller) {
		this.controller = controller;
	}
	public ArrayList<MonitoringUnit> getMonitoringUnits() {
		return monitoringUnits;
	}
	public void setMonitoringUnits(ArrayList<MonitoringUnit> monitoringUnits) {
		this.monitoringUnits = monitoringUnits;
	}
}

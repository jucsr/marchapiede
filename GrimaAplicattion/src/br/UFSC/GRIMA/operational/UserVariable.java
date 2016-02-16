package br.UFSC.GRIMA.operational;

import java.util.ArrayList;

import org.jfree.data.time.TimeSeries;

import br.UFSC.GRIMA.dataStructure.Variable;

public class UserVariable {
	private String name;
	private char type;
	private String unit;
	private TimeSeries dataSerie;
	private ArrayList<Variable> variables;
	private MonitoringUnit monitoringUnit;
//////////////////////////////////////Getters and Setters////////////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	public MonitoringUnit getMonitoringUnit() {
		return monitoringUnit;
	}
	public void setMonitoringUnit(MonitoringUnit monitoringUnit) {
		this.monitoringUnit = monitoringUnit;
	}
	public TimeSeries getDataSerie() {
		return dataSerie;
	}
	public void setDataSerie(TimeSeries dataSerie) {
		this.dataSerie = dataSerie;
	}
	
}

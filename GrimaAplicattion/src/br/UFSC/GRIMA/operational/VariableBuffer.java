package br.UFSC.GRIMA.operational;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;

import br.UFSC.GRIMA.dataStructure.Variable;

public class VariableBuffer implements SeriesChangeListener, ActionListener {
	private Variable variable;
	private MonitoringUnit monitoringUnit;
	private TimeSeries dataSerie;
	private int categoriesInVariable;
	private boolean inCalc;
	//////////panelComponents
	private JLabel typeLabel;
	private JTextField valueTextField;
	private JToggleButton displayButton;
/////////////////////////////////////constructor///////////////////////////////////////////////////////////////////
	public VariableBuffer(Variable variable, MonitoringUnit monitoringUnit) {
		setVariable(variable);
		setMonitoringUnit(monitoringUnit);
		setInCalc(false);
		monitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
		if (variable.getName() != null) 
			setDataSerie(new TimeSeries(variable.getName()));
		else
			setDataSerie(new TimeSeries(variable.getDataItemID()));
		dataSerie.setNotify(false);
		for(int i = 0; i < variable.getDataSerie().getItemCount(); i++) 
			dataSerie.addOrUpdate(variable.getDataSerie().getDataItem(i));
		if(variable.getType() == 'c') {
			for (int i = 0; i < dataSerie.getItemCount(); i++)
				dataSerie.update(i, monitoringUnit.getCategoryStrings().indexOf(variable.getCategoryStrings().get(dataSerie.getValue(i).intValue())));
		}
		variable.getDataSerie().addChangeListener(this);
	}
/////////////////////////////////////Methods///////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(displayButton)) {
			if (variable.getType() == 'i')
				displayButton.setSelected(false);
			else if (displayButton.isSelected())
				monitoringUnit.getChartDataset().addSeries(dataSerie);
			else
				monitoringUnit.getChartDataset().removeSeries(dataSerie);
		}
	}
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		if ((variable.getType() == 'i')&&(dataSerie != null)) 
			setVariableToIrregular();
		if (!variable.getDataSerie().isEmpty()) {
			if (variable.getType() != 'i') {
				addToSerie(variable.getDataSerie().getDataItem(variable.getDataSerie().getItemCount() - 1));
				if(isInCalc() && (monitoringUnit.getCalculateCombobox().getSelectedIndex() >= 0) && monitoringUnit.getStartPause().isSelected()) 
					updateCalculateField();
			}
			if (monitoringUnit.getPlayPause() != null) {
				if(monitoringUnit.getPlayPause().isSelected())
					valueTextField.setText(variable.getLastValue());
			}
		}
	}
	public void addToSerie(TimeSeriesDataItem item) {
		try {
			if (variable.getType() == '1')
				dataSerie.addOrUpdate((TimeSeriesDataItem)item.clone());
			if (variable.getType() == 'c') {
				TimeSeriesDataItem nItem = (TimeSeriesDataItem)item.clone();
				dataSerie.addOrUpdate(nItem.getPeriod(), getCategoryPosition(variable.getCategoryStrings().get(nItem.getValue().intValue())));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		///////////////Discart medium value/////////////////
		TimeSeries serie = dataSerie;
		if (serie.getItemCount() >= 3) {
			if((serie.getValue(serie.getItemCount() - 1) == null)&&(serie.getValue(serie.getItemCount() - 2) == null)&&(serie.getValue(serie.getItemCount() - 3) == null))
				serie.delete(serie.getItemCount()-2, serie.getItemCount()-2); /// deleta o penúltimo registro
			else if (serie.getValue(serie.getItemCount() - 1) != null) {
				if (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 2)) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 3))))
					serie.delete(serie.getItemCount()-2, serie.getItemCount()-2); /// deleta o penúltimo registro
			}
		}
		//////////////discart old values////////////////////////
		if (((variable.getType() == '1') || (variable.getType() == 'c')) && (serie.getItemCount() > 1)) {
			XMLGregorianCalendar iniTime =(XMLGregorianCalendar) variable.getComponent().getDevice().getAgent().getCreationTime().clone();
			int second = iniTime.getSecond() - monitoringUnit.getTimeRange()[2];
			int minute = iniTime.getMinute() - monitoringUnit.getTimeRange()[1];
			int hour = iniTime.getHour() - monitoringUnit.getTimeRange()[0];
			int day = iniTime.getDay();
			int month = iniTime.getMonth();
			int year = iniTime.getYear();
			if (second < 0) {
				second = second + 60;
				minute--;
			}
			if (minute < 0) {
				minute = minute + 60;
				hour--;
			}
			if (hour < 0) {
				hour = hour + 24;
				day--;
			}
			if (day < 1) {
				XMLGregorianCalendar correction = iniTime;
				try {
					correction.setMonth(iniTime.getMonth() - 1);
				}
				catch (IllegalArgumentException e) {
					correction.setYear(iniTime.getYear() - 1);
					correction.setMonth(1);
				}
				day = day + correction.toGregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
				month--;
			}
			if (month < 1) {
				month = month + 12;
				year--;
			}
			iniTime.setTime(hour, minute, second);
			iniTime.setDay(day);
			iniTime.setMonth(month);
			iniTime.setYear(year);
			Millisecond inicialTime = new Millisecond(iniTime.toGregorianCalendar().getTime());
			for (int j = 0; j < serie.getItemCount() - 1;j++) {
				if (inicialTime.compareTo(serie.getTimePeriod(j)) <= 0) {
					break;
				}
				else if (inicialTime.compareTo(serie.getTimePeriod(j+1)) < 0) {
					if ((variable.getType() == '1') && (serie.getValue(j) != null) && (serie.getValue(j + 1) != null)) {
						//faz uma aproximacao linear em t0
						double y1 = serie.getValue(j).doubleValue();
						double y2 = serie.getValue(j+1).doubleValue();
						long x0 = inicialTime.getLastMillisecond();
						long x1 = serie.getTimePeriod(j).getLastMillisecond();
						long x2 = serie.getTimePeriod(j+1).getLastMillisecond();
						double a =  (double) ((y2 - y1 )/(x2 - x1));
						double b = (double) (y1 - a*x1);
						double yn = a*x0 + b;
						serie.addOrUpdate(inicialTime, yn);
					}
					else {
						serie.addOrUpdate(inicialTime, serie.getValue(j));
					}
					serie.delete(0, j);
				}
			}
		}
		if(variable.getType() == 'c') {
			ArrayList<Number> categoriesInVariable = new ArrayList<Number>();
			for(int i = 0; i < dataSerie.getItemCount(); i++) {
				if(!categoriesInVariable.contains(dataSerie.getValue(i)))
					categoriesInVariable.add(dataSerie.getValue(i));
			}
			if(categoriesInVariable.size() < getCategoriesInVariable())
				monitoringUnit.categoryRemove();
			setCategoriesInVariable(categoriesInVariable.size());
		}
		dataSerie.setNotify(true);
		dataSerie.setNotify(false);
	}
	public void updateCalculateField() {
		Long dif = new Long(0);
		if ((monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Integrate")) || (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Derivate"))) {
			if (monitoringUnit.getLastMillisecond() != 0)
				dif =  dataSerie.getTimePeriod(dataSerie.getItemCount() - 1).getLastMillisecond() - monitoringUnit.getLastMillisecond();
			monitoringUnit.setLastMillisecond(dataSerie.getTimePeriod(dataSerie.getItemCount() - 1).getLastMillisecond());
		}
		if (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Integrate")) {
			try {
				if(dif != 0) {
					double deltat = dif.doubleValue()/1000.0; ///milliseconds to seconds
					monitoringUnit.setResult(monitoringUnit.getResult() + dataSerie.getValue(dataSerie.getItemCount() - 1).doubleValue()*deltat);
					if(monitoringUnit.getPlayPause().isSelected())
						monitoringUnit.getCalcResult().setText("" + monitoringUnit.getResult());
				}
			}catch(Exception e) {
				e.printStackTrace();
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Derivate")) {
			try {
				if(dif != 0 ){
					double deltat = dif.doubleValue()/1000.0; ///milliseconds to seconds
					monitoringUnit.setResult((dataSerie.getValue(dataSerie.getItemCount() - 1).doubleValue() - dataSerie.getValue(dataSerie.getItemCount() - 2).doubleValue())/deltat);
					if(monitoringUnit.getPlayPause().isSelected())
						monitoringUnit.getCalcResult().setText("" + monitoringUnit.getResult());
				}
			}catch(Exception e) {
				e.printStackTrace();
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Maximum")) {
			if(monitoringUnit.getPlayPause().isSelected())
				monitoringUnit.getCalcResult().setText("" + dataSerie.getMaxY());
		}
		else if (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Minimum")) {
			if(monitoringUnit.getPlayPause().isSelected())
				monitoringUnit.getCalcResult().setText("" + dataSerie.getMinY());
		}
		else if (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Average2")) {
			Double res = calculateAverage(2);
			if (res != null) {
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText(res.toString());
			}
			else {
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Average5")) {
			Double res = calculateAverage(5);
			if (res != null) {
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText(res.toString());
			}
			else {
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (monitoringUnit.getCalculateCombobox().getSelectedItem().equals("Average10")) {
			Double res = calculateAverage(10);
			if (res != null) {
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText(res.toString());
			}
			else {
				if(monitoringUnit.getPlayPause().isSelected())
					monitoringUnit.getCalcResult().setText("Er");
			}
		}
	}
	public Double calculateAverage(int points) {
		if(dataSerie.getItemCount() > points) {
			double sum = 0;
			for(int i = 0; i < points; i++) {
				Long deltat = dataSerie.getTimePeriod(dataSerie.getItemCount() - 1 - i).getFirstMillisecond() - dataSerie.getTimePeriod(dataSerie.getItemCount() - 2 - i).getFirstMillisecond();
				sum = sum + deltat * dataSerie.getValue(dataSerie.getItemCount() - 2 - i).doubleValue();
			}
			Long deltat = dataSerie.getTimePeriod(dataSerie.getItemCount() - 1).getFirstMillisecond() - dataSerie.getTimePeriod(dataSerie.getItemCount() - points - 1).getFirstMillisecond();
			return sum/deltat;
		}
		else
			return null;
	}
	public int getCategoryPosition(String s) {
		if (!monitoringUnit.getCategoryStrings().contains(s))
			monitoringUnit.categoryAdd(s);
		return monitoringUnit.getCategoryStrings().indexOf(s);
	}
	public void setNewCategoryData(int[] correction) {
		if(variable.getType() != 'i') {
			try {
				for(int i = 0; i < dataSerie.getItemCount(); i++) {
					dataSerie.update(i, (double) (dataSerie.getValue(i).intValue() - correction[dataSerie.getValue(i).intValue()]));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void setVariableToIrregular() {
		monitoringUnit.getChartDataset().removeSeries(dataSerie);
		dataSerie = null;
		typeLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
		typeLabel.setToolTipText("Irregular Variable Type: this variable show values that is neither numeric nor category variable Type.");
		displayButton.setSelected(false);
		if(monitoringUnit.getPanelType() == 'c')
			monitoringUnit.categoryRemove();
	}
//////////////////////////////////////Getters and Setters////////////////////////////////////////////////////////

	public TimeSeries getDataSerie() {
		return dataSerie;
	}

	public void setDataSerie(TimeSeries dataSerie) {
		this.dataSerie = dataSerie;
	}
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	public MonitoringUnit getMonitoringUnit() {
		return monitoringUnit;
	}
	public void setMonitoringUnit(MonitoringUnit monitoringUnit) {
		this.monitoringUnit = monitoringUnit;
	}
	public JLabel getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(JLabel typeLabel) {
		this.typeLabel = typeLabel;
	}

	public JTextField getValueTextField() {
		return valueTextField;
	}

	public void setValueTextField(JTextField valueTextField) {
		this.valueTextField = valueTextField;
	}

	public JToggleButton getDisplayButton() {
		return displayButton;
	}

	public void setDisplayButton(JToggleButton displayButton) {
		this.displayButton = displayButton;
	}
	public boolean isInCalc() {
		return inCalc;
	}
	public void setInCalc(boolean inCalc) {
		this.inCalc = inCalc;
	}
	public int getCategoriesInVariable() {
		return categoriesInVariable;
	}
	public void setCategoriesInVariable(int categoriesInVariable) {
		this.categoriesInVariable = categoriesInVariable;
	}
}

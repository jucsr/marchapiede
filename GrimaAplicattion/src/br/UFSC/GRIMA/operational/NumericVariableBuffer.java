package br.UFSC.GRIMA.operational;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class NumericVariableBuffer implements SeriesChangeListener, ActionListener {
	private Variable variable;
	private NumericMonitoringUnit numericMonitoringUnit;
	private TwoDMonitoringUnit twoDMonitoringUnit;
	private TimeSeries dataSerie;
	private boolean inCalc;
	//////////panelComponents
	private JLabel typeLabel;
	private JTextField valueTextField;
	private JToggleButton displayButton;
	private JLabel displayLabel;
/////////////////////////////////////////Constructor///////////////////////////////////////////////////////////////
	public NumericVariableBuffer(Variable variable, NumericMonitoringUnit monitoringUnit) {
		setVariable(variable);
		setNumericMonitoringUnit(monitoringUnit);
		setInCalc(false);
		monitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
		if (variable.getName() != null) 
			setDataSerie(new TimeSeries(variable.getName()));
		else
			setDataSerie(new TimeSeries(variable.getDataItemID()));
		dataSerie.setNotify(false);
		for(int i = 0; i < variable.getDataSerie().getItemCount(); i++) 
			dataSerie.addOrUpdate(variable.getDataSerie().getDataItem(i));
		variable.getDataSerie().addChangeListener(this);
	}
	public NumericVariableBuffer(Variable variable, TwoDMonitoringUnit monitoringUnit) {
		setVariable(variable);
		setTwoDMonitoringUnit(monitoringUnit);
		setInCalc(false);
		monitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
		if (variable.getName() != null) 
			setDataSerie(new TimeSeries(variable.getName()));
		else
			setDataSerie(new TimeSeries(variable.getDataItemID()));
		dataSerie.setNotify(false);
		for(int i = 0; i < variable.getDataSerie().getItemCount(); i++) 
			dataSerie.addOrUpdate(variable.getDataSerie().getDataItem(i));
		variable.getDataSerie().addChangeListener(this);
	}
////////////////////////////////////////Methods/////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(displayButton)) {
			if (variable.getType() == 'i')
				displayButton.setSelected(false);
			else if(numericMonitoringUnit != null) {
				if (displayButton.isSelected())
					numericMonitoringUnit.getChartDataset().addSeries(dataSerie);
				else
					numericMonitoringUnit.getChartDataset().removeSeries(dataSerie);
			}
		}
	}
		
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		if ((variable.getType() == 'i')&&(dataSerie != null)) 
			setVariableToIrregular();
		if (!variable.getDataSerie().isEmpty()) {
			if (variable.getType() != 'i') {
				addToSerie(variable.getDataSerie().getDataItem(variable.getDataSerie().getItemCount() - 1));
				if(isInCalc() && (numericMonitoringUnit.getCalculateCombobox().getSelectedIndex() >= 0) && numericMonitoringUnit.getStartPause().isSelected()) 
					updateCalculateField();
			}
			if (numericMonitoringUnit != null) {
				if (numericMonitoringUnit.getPlayPause() != null) {
					if(numericMonitoringUnit.getPlayPause().isSelected())
						valueTextField.setText(variable.getLastValue());
				}
			}
			else if(twoDMonitoringUnit != null) {
				if (twoDMonitoringUnit.getPlayPause() != null) {
					if(twoDMonitoringUnit.getPlayPause().isSelected())
						valueTextField.setText(variable.getLastValue());
				}
			}
		}
	}
	public void addToSerie(TimeSeriesDataItem item) {
		try {
			dataSerie.addOrUpdate((TimeSeriesDataItem)item.clone());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//////////////Discart medium value/////////////////
		TimeSeries serie = dataSerie;
		if (serie.getItemCount() > 3) {
			if((serie.getValue(serie.getItemCount() - 1) == null)&&(serie.getValue(serie.getItemCount() - 2) == null)&&(serie.getValue(serie.getItemCount() - 3) == null) && (serie.getValue(serie.getItemCount() - 4) == null))
				serie.delete(serie.getItemCount()-3, serie.getItemCount()-3); /// deleta o penúltimo registro
			else if (serie.getValue(serie.getItemCount() - 1) != null) {
				if (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 2)) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 3))) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 4))))
					serie.delete(serie.getItemCount()-3, serie.getItemCount()-3); /// deleta o penúltimo registro
			}
		}
		//////////////discart old values////////////////////////
		if (((variable.getType() == '1') || (variable.getType() == 'c')) && (serie.getItemCount() > 1)) {
			XMLGregorianCalendar iniTime =(XMLGregorianCalendar) variable.getComponent().getDevice().getAgent().getCreationTime().clone();
			int second;
			int minute;
			int hour;
			if(numericMonitoringUnit != null) {
				second = iniTime.getSecond() - numericMonitoringUnit.getTimeRange()[2];
				minute = iniTime.getMinute() - numericMonitoringUnit.getTimeRange()[1];
				hour = iniTime.getHour() - numericMonitoringUnit.getTimeRange()[0];
			}
			else {
				second = iniTime.getSecond() - twoDMonitoringUnit.getTimeRange()[2];
				minute = iniTime.getMinute() - twoDMonitoringUnit.getTimeRange()[1];
				hour = iniTime.getHour() - twoDMonitoringUnit.getTimeRange()[0];
			}
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
			while(serie.getItemCount() > 2) {
				if(inicialTime.compareTo(serie.getTimePeriod(0)) <= 0)
					break;
				if(inicialTime.compareTo(serie.getTimePeriod(0)) > 0) {
					if (inicialTime.compareTo(serie.getTimePeriod(1)) < 0) {
						if ((variable.getType() == '1') && (serie.getValue(0) != null) && (serie.getValue(1) != null)) {
							//faz uma aproximacao linear em t0
							double y1 = serie.getValue(0).doubleValue();
							double y2 = serie.getValue(1).doubleValue();
							long x0 = inicialTime.getLastMillisecond();
							long x1 = serie.getTimePeriod(0).getLastMillisecond();
							long x2 = serie.getTimePeriod(1).getLastMillisecond();
							double a =  (double) ((y2 - y1 )/(x2 - x1));
							double b = (double) (y1 - a*x1);
							double yn = a*x0 + b;
							serie.addOrUpdate(inicialTime, yn);
						}
						else {
							serie.addOrUpdate(inicialTime, serie.getValue(0));
						}
					}
					serie.delete(0, 0);
				}
			}
		}
		dataSerie.setNotify(true);
		dataSerie.setNotify(false);
		
	}
	public void updateCalculateField() {
		Long dif = new Long(0);
		if ((numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Integrate")) || (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Derivate"))) {
			if (numericMonitoringUnit.getLastMillisecond() != 0)
				dif =  dataSerie.getTimePeriod(dataSerie.getItemCount() - 1).getLastMillisecond() - numericMonitoringUnit.getLastMillisecond();
			numericMonitoringUnit.setLastMillisecond(dataSerie.getTimePeriod(dataSerie.getItemCount() - 1).getLastMillisecond());
		}
		if (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Integrate")) {
			try {
				if(dif != 0) {
					double deltat = dif.doubleValue()/1000.0; ///milliseconds to seconds
					numericMonitoringUnit.setResult(numericMonitoringUnit.getResult() + dataSerie.getValue(dataSerie.getItemCount() - 1).doubleValue()*deltat);
				}
				if(numericMonitoringUnit.getPlayPause().isSelected())
						numericMonitoringUnit.getCalcResult().setText("" + numericMonitoringUnit.getResult());
				}
			catch(Exception e) {
				e.printStackTrace();
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Derivate")) {
			try {
				if(dif != 0 ){
					double deltat = dif.doubleValue()/1000.0; ///milliseconds to seconds
					numericMonitoringUnit.setResult((dataSerie.getValue(dataSerie.getItemCount() - 1).doubleValue() - dataSerie.getValue(dataSerie.getItemCount() - 2).doubleValue())/deltat);
					if(numericMonitoringUnit.getPlayPause().isSelected())
						numericMonitoringUnit.getCalcResult().setText("" + numericMonitoringUnit.getResult());
				}
			}catch(Exception e) {
				e.printStackTrace();
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Maximum")) {
			if(numericMonitoringUnit.getPlayPause().isSelected())
				numericMonitoringUnit.getCalcResult().setText("" + dataSerie.getMaxY());
		}
		else if (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Minimum")) {
			if(numericMonitoringUnit.getPlayPause().isSelected())
				numericMonitoringUnit.getCalcResult().setText("" + dataSerie.getMinY());
		}
		else if (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Average2")) {
			Double res = calculateAverage(2);
			if (res != null) {
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText(res.toString());
			}
			else {
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Average5")) {
			Double res = calculateAverage(5);
			if (res != null) {
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText(res.toString());
			}
			else {
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText("Er");
			}
		}
		else if (numericMonitoringUnit.getCalculateCombobox().getSelectedItem().equals("Average10")) {
			Double res = calculateAverage(10);
			if (res != null) {
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText(res.toString());
			}
			else {
				if(numericMonitoringUnit.getPlayPause().isSelected())
					numericMonitoringUnit.getCalcResult().setText("Er");
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
	public void setVariableToIrregular() {
		if (numericMonitoringUnit != null)
			numericMonitoringUnit.getChartDataset().removeSeries(dataSerie);
		else{}
			//do something
		dataSerie = null;
		typeLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
		typeLabel.setToolTipText("Irregular Variable Type: this variable show values that is neither numeric nor category variable Type.");
		displayButton.setSelected(false);
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
	public NumericMonitoringUnit getNumericMonitoringUnit() {
		return numericMonitoringUnit;
	}
	public void setNumericMonitoringUnit(NumericMonitoringUnit monitoringUnit) {
		this.numericMonitoringUnit = monitoringUnit;
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
	public TwoDMonitoringUnit getTwoDMonitoringUnit() {
		return twoDMonitoringUnit;
	}
	public void setTwoDMonitoringUnit(TwoDMonitoringUnit twoDMonitoringUnit) {
		this.twoDMonitoringUnit = twoDMonitoringUnit;
	}
	public JLabel getDisplayLabel() {
		return displayLabel;
	}
	public void setDisplayLabel(JLabel displayLabel) {
		this.displayLabel = displayLabel;
	}
}

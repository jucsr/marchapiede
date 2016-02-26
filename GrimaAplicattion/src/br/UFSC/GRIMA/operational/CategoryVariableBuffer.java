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

public class CategoryVariableBuffer implements SeriesChangeListener, ActionListener {
	private Variable variable;
	private CategoryMonitoringUnit categoryMonitoringUnit;
	private TimeSeries dataSerie;
	private int categoriesInVariable;
	//////////panelComponents
	private JLabel typeLabel;
	private JTextField valueTextField;
	private JToggleButton displayButton;
/////////////////////////////////////constructor///////////////////////////////////////////////////////////////////
	public CategoryVariableBuffer(Variable variable, CategoryMonitoringUnit categoryMonitoringUnit) {
		setVariable(variable);
		setCategoryMonitoringUnit(categoryMonitoringUnit);
		categoryMonitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
		if (variable.getName() != null) 
			setDataSerie(new TimeSeries(variable.getName()));
		else
			setDataSerie(new TimeSeries(variable.getDataItemID()));
		dataSerie.setNotify(false);
		for(int i = 0; i < variable.getDataSerie().getItemCount(); i++) 
			dataSerie.addOrUpdate(variable.getDataSerie().getDataItem(i));
		for (int i = 0; i < dataSerie.getItemCount(); i++)
			dataSerie.update(i, categoryMonitoringUnit.getCategoryStrings().indexOf(variable.getCategoryStrings().get(dataSerie.getValue(i).intValue())));
		variable.getDataSerie().addChangeListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(displayButton)) {
			if (variable.getType() == 'i')
				displayButton.setSelected(false);
			else if (displayButton.isSelected())
				categoryMonitoringUnit.getChartDataset().addSeries(dataSerie);
			else
				categoryMonitoringUnit.getChartDataset().removeSeries(dataSerie);
		}
	}
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		if ((variable.getType() == 'i')&&(dataSerie != null)) 
			setVariableToIrregular();
		if (!variable.getDataSerie().isEmpty()) {
			if (variable.getType() != 'i') 
				addToSerie(variable.getDataSerie().getDataItem(variable.getDataSerie().getItemCount() - 1));
			if (categoryMonitoringUnit.getPlayPause() != null) {
				if(categoryMonitoringUnit.getPlayPause().isSelected())
					valueTextField.setText(variable.getLastValue());
			}
		}
	}
	public void addToSerie(TimeSeriesDataItem item) {
		try {
			TimeSeriesDataItem nItem = (TimeSeriesDataItem)item.clone();
			dataSerie.addOrUpdate(nItem.getPeriod(), getCategoryPosition(variable.getCategoryStrings().get(nItem.getValue().intValue())));
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
		if (((variable.getType() == 'c')) && (serie.getItemCount() > 1)) {
			XMLGregorianCalendar iniTime =(XMLGregorianCalendar) variable.getComponent().getDevice().getAgent().getCreationTime().clone();
			int second = iniTime.getSecond() - categoryMonitoringUnit.getTimeRange()[2];
			int minute = iniTime.getMinute() - categoryMonitoringUnit.getTimeRange()[1];
			int hour = iniTime.getHour() - categoryMonitoringUnit.getTimeRange()[0];
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
		ArrayList<Number> categoriesInVariable = new ArrayList<Number>();
		for(int i = 0; i < dataSerie.getItemCount(); i++) {
			if(!categoriesInVariable.contains(dataSerie.getValue(i)))
				categoriesInVariable.add(dataSerie.getValue(i));
		}
		if(categoriesInVariable.size() < getCategoriesInVariable())
			categoryMonitoringUnit.categoryRemove();
		setCategoriesInVariable(categoriesInVariable.size());
		dataSerie.setNotify(true);
		dataSerie.setNotify(false);
	}
	public int getCategoryPosition(String s) {
		if (!categoryMonitoringUnit.getCategoryStrings().contains(s))
			categoryMonitoringUnit.categoryAdd(s);
		return categoryMonitoringUnit.getCategoryStrings().indexOf(s);
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
		categoryMonitoringUnit.getChartDataset().removeSeries(dataSerie);
		dataSerie = null;
		typeLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
		typeLabel.setToolTipText("Irregular Variable Type: this variable show values that is neither numeric nor category variable Type.");
		displayButton.setSelected(false);
		if(categoryMonitoringUnit.getPanelType() == 'c')
			categoryMonitoringUnit.categoryRemove();
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
	public CategoryMonitoringUnit getCategoryMonitoringUnit() {
		return categoryMonitoringUnit;
	}
	public void setCategoryMonitoringUnit(CategoryMonitoringUnit categoryMonitoringUnit) {
		this.categoryMonitoringUnit = categoryMonitoringUnit;
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
	public int getCategoriesInVariable() {
		return categoriesInVariable;
	}
	public void setCategoriesInVariable(int categoriesInVariable) {
		this.categoriesInVariable = categoriesInVariable;
	}
}

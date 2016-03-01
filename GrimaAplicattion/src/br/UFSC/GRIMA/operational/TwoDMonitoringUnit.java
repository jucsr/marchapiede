package br.UFSC.GRIMA.operational;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.UFSC.GRIMA.dataStructure.Variable;

public class TwoDMonitoringUnit extends MonitoringUnit implements SeriesChangeListener {
	private ArrayList<VariableRegister> variableRegisters;
	private NumericVariableBuffer xAxis;
	private NumericVariableBuffer yAxis;
	private ArrayList<RegularTimePeriod> timeRegister;
	private XYSeries valueRegister;
	private XYSeriesCollection chartDataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private Variable xSelected;
	private Variable ySelected;
	private Double xLoad;
	private Double yLoad;
	//////////////layout Components//////////////////
	private JComboBox<String> xCombobox;
	private JComboBox<String> yComboBox;
//////////////////////////////////////////////////Constructor///////////////////////////////////////////////////////////////////////////////////
	public TwoDMonitoringUnit(String name,PanelMonitoringSystem panelMonitoringSystem, int[] timeRange, String chartType, ArrayList<Variable> variables, char panelType, Variable xAxis, Variable yAxis) {
		super(name, panelMonitoringSystem, timeRange, chartType, variables, panelType);
		// TODO Auto-generated constructor stub
		setxSelected(xAxis);
		setySelected(yAxis);
	}
////////////////////////////////////////////////////Methods/////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(xAxis.getDataSerie())) {
			if(yLoad != null) {
				timeRegister.add(xAxis.getDataSerie().getTimePeriod((xAxis.getDataSerie().getItemCount() - 1)));
				valueRegister.add(xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1), yLoad);
				yLoad = null;
			}
			else 
				xLoad = xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		else if(e.getSource().equals(yAxis.getDataSerie())) {
			if(xLoad != null) {
				timeRegister.add(yAxis.getDataSerie().getTimePeriod((yAxis.getDataSerie().getItemCount() - 1)));
				valueRegister.add(yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1), xLoad);
				xLoad = null;
			}
			else 
				yLoad = xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		System.out.println(valueRegister.getItemCount());
//		if (((variable.getType() == '1') || (variable.getType() == 'c')) && (serie.getItemCount() > 1)) {
//			XMLGregorianCalendar iniTime =(XMLGregorianCalendar) variable.getComponent().getDevice().getAgent().getCreationTime().clone();
//			int second;
//			int minute;
//			int hour;
//			if(numericMonitoringUnit != null) {
//				second = iniTime.getSecond() - numericMonitoringUnit.getTimeRange()[2];
//				minute = iniTime.getMinute() - numericMonitoringUnit.getTimeRange()[1];
//				hour = iniTime.getHour() - numericMonitoringUnit.getTimeRange()[0];
//			}
//			else {
//				second = iniTime.getSecond() - twoDMonitoringUnit.getTimeRange()[2];
//				minute = iniTime.getMinute() - twoDMonitoringUnit.getTimeRange()[1];
//				hour = iniTime.getHour() - twoDMonitoringUnit.getTimeRange()[0];
//			}
//			int day = iniTime.getDay();
//			int month = iniTime.getMonth();
//			int year = iniTime.getYear();
//			if (second < 0) {
//				second = second + 60;
//				minute--;
//			}
//			if (minute < 0) {
//				minute = minute + 60;
//				hour--;
//			}
//			if (hour < 0) {
//				hour = hour + 24;
//				day--;
//			}
//			if (day < 1) {
//				XMLGregorianCalendar correction = iniTime;
//				try {
//					correction.setMonth(iniTime.getMonth() - 1);
//				}
//				catch (IllegalArgumentException e) {
//					correction.setYear(iniTime.getYear() - 1);
//					correction.setMonth(1);
//				}
//				day = day + correction.toGregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
//				month--;
//			}
//			if (month < 1) {
//				month = month + 12;
//				year--;
//			}
//			iniTime.setTime(hour, minute, second);
//			iniTime.setDay(day);
//			iniTime.setMonth(month);
//			iniTime.setYear(year);
//			Millisecond inicialTime = new Millisecond(iniTime.toGregorianCalendar().getTime());
//			for (int j = 0; j < serie.getItemCount() - 1;j++) {
//				if (inicialTime.compareTo(serie.getTimePeriod(j)) <= 0) {
//					break;
//				}
//				else if (inicialTime.compareTo(serie.getTimePeriod(j+1)) < 0) {
//					if ((variable.getType() == '1') && (serie.getValue(j) != null) && (serie.getValue(j + 1) != null)) {
//						//faz uma aproximacao linear em t0
//						double y1 = serie.getValue(j).doubleValue();
//						double y2 = serie.getValue(j+1).doubleValue();
//						long x0 = inicialTime.getLastMillisecond();
//						long x1 = serie.getTimePeriod(j).getLastMillisecond();
//						long x2 = serie.getTimePeriod(j+1).getLastMillisecond();
//						double a =  (double) ((y2 - y1 )/(x2 - x1));
//						double b = (double) (y1 - a*x1);
//						double yn = a*x0 + b;
//						serie.addOrUpdate(inicialTime, yn);
//					}
//					else {
//						serie.addOrUpdate(inicialTime, serie.getValue(j));
//					}
//					serie.delete(0, j);
//				}
//			}
//		}
	}

	@Override
	public void actionPerformed2(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freezeChart(boolean freeze) {
		// TODO Auto-generated method stub
		chart.setNotify(false);
	}

	@Override
	public void addVariableControl(Variable variable, JPanel container, int line, JLabel typeLabel, JTextField valueField) {
		// TODO Auto-generated method stub
		if(variable.equals(xSelected) || variable.equals(ySelected)) {
			JToggleButton display = new JToggleButton();
			display.setSelected(true);
			container.add(display, new GridBagConstraints(5, line, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			NumericVariableBuffer numericvariableBuffer = new NumericVariableBuffer(variable, this);
			if (variable.equals(xSelected))
				setxAxis(numericvariableBuffer);
			else
				setyAxys(numericvariableBuffer);
			numericvariableBuffer.setTypeLabel(typeLabel);
			numericvariableBuffer.setValueTextField(valueField);
			numericvariableBuffer.setDisplayButton(display);
			display.addActionListener(numericvariableBuffer);
		}
		else {
			if(variableRegisters == null)
				setVariableRegisters(new ArrayList<VariableRegister>());
			variableRegisters.add(new VariableRegister(variable, this, typeLabel, valueField));
		}
		
	}

	@Override
	public void initAditionalPanelElements() {
		// TODO Auto-generated method stub
		init2DSerie();
		xAxis.getDataSerie().addChangeListener(this);
		yAxis.getDataSerie().addChangeListener(this);
		setChartDataset(new XYSeriesCollection(valueRegister));
	}

	@Override
	public void refreshChart2() {
		// TODO Auto-generated method stub
		String nameX = null;
		if(xSelected.getName() != null) 
			nameX = xSelected.getName();
		else
			xSelected.getDataItemID();
		String nameY = null;
		if(ySelected.getName() != null) 
			nameY = ySelected.getName();
		else
			ySelected.getDataItemID();
		setChart(ChartFactory.createXYLineChart("", nameX, nameY, chartDataset));
		((GridBagLayout)getMonitoringPanel().getLayout()).rowHeights[2] = getMinimumHeight();
		getMonitoringPanel().setPreferredSize(new Dimension(getMinimumWhidth(),  (int) Math.round(getMonitoringPanel().getPreferredSize().getHeight())));
		setVisible(isVisible());
		getMonitoringPanel().setVisible(isVisible());
		if (chartPanel != null) 
			getMonitoringPanel().remove(chartPanel);
		setChartPanel(new ChartPanel(chart));
		getMonitoringPanel().add(chartPanel, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
	}

	@Override
	public void destroyPanelInstance() {
		// TODO Auto-generated method stub
		try {
			xSelected.getDataSerie().removeChangeListener(xAxis);
			ySelected.getDataSerie().removeChangeListener(yAxis);
			for(int i = 0; i < variableRegisters.size(); i++) 
				variableRegisters.get(i).getVariable().getDataSerie().removeChangeListener(variableRegisters.get(i));
		}catch(Exception e){}
		setVariableRegisters(null);
		setChartPanel(null);
		setChart(null);
		setChartDataset(null);
		setMonitoringPanel(null);
		setPlayPause(null);
		setxAxis(null);
		setyAxys(null);
		setxCombobox(null);
		setyComboBox(null);
	}
	
	public void init2DSerie() {
		setValueRegister(new XYSeries("", false, true));
		setTimeRegister(new ArrayList<RegularTimePeriod>());
		/////////////////finding first closer pair of the serie///////////////////////////////////////////////
		if(!xSelected.getDataSerie().isEmpty() && !ySelected.getDataSerie().isEmpty()) {
			int xIndex = 0;
			int yIndex = 0;
			if (xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() > ySelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond()) {
				yIndex = findItemClosedTo((Millisecond)xSelected.getDataSerie().getTimePeriod(xIndex), ySelected.getDataSerie());
				timeRegister.add(xSelected.getDataSerie().getTimePeriod(xIndex));
			}
			else {
				xIndex = findItemClosedTo((Millisecond)ySelected.getDataSerie().getTimePeriod(yIndex), xSelected.getDataSerie());
				timeRegister.add(ySelected.getDataSerie().getTimePeriod(yIndex));
			}
			valueRegister.add(xSelected.getDataSerie().getValue(xIndex), ySelected.getDataSerie().getValue(yIndex));
			while((xIndex < xSelected.getDataSerie().getItemCount()) && (yIndex < ySelected.getDataSerie().getItemCount())) {
				int comp = ((Millisecond)(xSelected.getDataSerie().getTimePeriod(xIndex))).compareTo(ySelected.getDataSerie().getTimePeriod(yIndex));
				if(comp == 0) {
					timeRegister.add(xSelected.getDataSerie().getTimePeriod(xIndex));
					valueRegister.add(xSelected.getDataSerie().getValue(xIndex), ySelected.getDataSerie().getValue(yIndex));
					if(xIndex < xSelected.getDataSerie().getItemCount())
						xIndex++;
					if(yIndex <  ySelected.getDataSerie().getItemCount())
						yIndex++;
					continue;
				}
				if (comp < 0) {
					timeRegister.add(ySelected.getDataSerie().getTimePeriod(yIndex));
					valueRegister.add(xSelected.getDataSerie().getValue(xIndex), ySelected.getDataSerie().getValue(yIndex - 1));
					if(xIndex <  xSelected.getDataSerie().getItemCount())
						xIndex++;
					else
						yIndex++;
					continue;
				}
				//else
				timeRegister.add(xSelected.getDataSerie().getTimePeriod(xIndex));
				valueRegister.add(xSelected.getDataSerie().getValue(xIndex - 1), ySelected.getDataSerie().getValue(yIndex));
				if(yIndex <  ySelected.getDataSerie().getItemCount())
					yIndex++;
				else
					xIndex++;
			}
		}
	}
	public int findItemClosedTo(Millisecond param, TimeSeries list) {
		int closer;
		for(closer = 0; closer < list.getItemCount() - 1; closer++) {
			long distDown = Math.abs(list.getTimePeriod(closer).getFirstMillisecond() - param.getFirstMillisecond());
			long distUp = Math.abs(list.getTimePeriod(closer + 1).getFirstMillisecond() - param.getFirstMillisecond());
			if(distDown <= distUp)
				break;
		}
		return closer;
	}
	
//////////////////////////////////////////////////////////Getters and Setters/////////////////////////////////////////////////////////////////////////////
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	public ChartPanel getChartPanel() {
		return chartPanel;
	}
	public void setChartPanel(ChartPanel chartPanel) {
		this.chartPanel = chartPanel;
	}
	public NumericVariableBuffer getxAxis() {
		return xAxis;
	}
	public void setxAxis(NumericVariableBuffer xAxis) {
		this.xAxis = xAxis;
	}
	public NumericVariableBuffer getyAxys() {
		return yAxis;
	}
	public void setyAxys(NumericVariableBuffer yAxys) {
		this.yAxis = yAxys;
	}
	public ArrayList<RegularTimePeriod> getTimeRegister() {
		return timeRegister;
	}
	public void setTimeRegister(ArrayList<RegularTimePeriod> timeRegister) {
		this.timeRegister = timeRegister;
	}
	public XYSeries getValueRegister() {
		return valueRegister;
	}
	public void setValueRegister(XYSeries valueRegister) {
		this.valueRegister = valueRegister;
	}
	public Variable getxSelected() {
		return xSelected;
	}
	public void setxSelected(Variable xSelected) {
		this.xSelected = xSelected;
	}
	public Variable getySelected() {
		return ySelected;
	}
	public void setySelected(Variable ySelected) {
		this.ySelected = ySelected;
	}
	public JComboBox<String> getxCombobox() {
		return xCombobox;
	}
	public void setxCombobox(JComboBox<String> xCombobox) {
		this.xCombobox = xCombobox;
	}
	public JComboBox<String> getyComboBox() {
		return yComboBox;
	}
	public void setyComboBox(JComboBox<String> yComboBox) {
		this.yComboBox = yComboBox;
	}
	public XYSeriesCollection getChartDataset() {
		return chartDataset;
	}
	public void setChartDataset(XYSeriesCollection chartDataset) {
		this.chartDataset = chartDataset;
	}
	public ArrayList<VariableRegister> getVariableRegisters() {
		return variableRegisters;
	}
	public void setVariableRegisters(ArrayList<VariableRegister> variableRegisters) {
		this.variableRegisters = variableRegisters;
	}
	
}

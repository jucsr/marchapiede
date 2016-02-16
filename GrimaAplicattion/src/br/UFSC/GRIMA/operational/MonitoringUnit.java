package br.UFSC.GRIMA.operational;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.time.TimeSeriesCollection;

import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.visual.MainInterface;

public class MonitoringUnit implements ActionListener{
	///////////vital informations//////////////
	private String name;
	private PanelMonitoringSystem panelMonitoringSystem;
	private int[] timeRange;
	private String chartType;
	private ArrayList<Variable> variables;
	private char panelType;
	///////////RunTimeCreateInformations//////////
	private ArrayList<String> categoryStrings;
	private ArrayList<VariableBuffer> variableBuffers;
	private TimeSeriesCollection chartDataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private JPanel monitoringPanel;
	//////////////panel Components///////////////
	private JToggleButton playPause;
	private JToggleButton panelButton;
	private JComboBox<String> calculateCombobox;
	private JComboBox<String> calcTargetCombobox;
	private JTextField calcResult;
	private JToggleButton startPause;
	private JButton reset;
	private double result = 0;
	private long lastMillisecond = 0;
////////////////////////////Constructor//////////////////////////////////////////////////////////////
	public MonitoringUnit(String name, PanelMonitoringSystem panelMonitoringSystem, int[] timeRange, String chartType, ArrayList<Variable> variables, char panelType) {
		setName(name);
		setPanelMonitoringSystem(panelMonitoringSystem);
		setTimeRange(timeRange);
		setChartType(chartType);
		setVariables(variables);
		setPanelType(panelType);
	}
/////////////////////////////////Methods///////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(playPause)) {
			chart.setNotify(playPause.isSelected());
			if(playPause.isSelected()) 
				playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/pause.png")));
			else
				playPause.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/play.png")));
		}
		else if(e.getSource().equals(panelButton)) {
			monitoringPanel.setVisible(panelButton.isSelected());
		}
		if(calcTargetCombobox != null) {
			if (e.getSource().equals(calcTargetCombobox)) {
				if (calcTargetCombobox.getSelectedIndex() >= 0 ) {
					if(variables.get(calcTargetCombobox.getSelectedIndex()).getType() == '1') {
						for(int i = 0; i < variableBuffers.size(); i++) 
							variableBuffers.get(i).setInCalc(false);
						variableBuffers.get(calcTargetCombobox.getSelectedIndex()).setInCalc(true);
						startPause.setSelected(false);
						result = 0;
						lastMillisecond = 0;
						calcResult.setText("");
					}
				}
			}
		}
		if(reset != null) {
			if (e.getSource().equals(reset) || e.getSource().equals(calculateCombobox)) {
				result = 0;
				lastMillisecond = 0;
				calcResult.setText("");
				startPause.setSelected(false);
			}
		}
		
		
		
	}
	public void initPanel() {
		setVariableBuffers(new ArrayList<VariableBuffer>());
		chartDataset = new TimeSeriesCollection();
		if (panelType == 'c') {
			setCategoryStrings(new ArrayList<String>());
			for(int i = 0; i < variables.size(); i++) {
				if (variables.get(i).getType() != 'i') {
					for(int j = 0; j < variables.get(i).getCategoryStrings().size(); j++) {
						if (!this.categoryStrings.contains(variables.get(i).getCategoryStrings().get(j)))
							categoryStrings.add(variables.get(i).getCategoryStrings().get(j));
					}
				}
			}
		}
		for(int i = 0; i < variables.size(); i++) {
			variableBuffers.add(new VariableBuffer(variables.get(i), this));
			chartDataset.addSeries(variableBuffers.get(i).getDataSerie());
		}
	}
	public void refreshChart() {
		if (this.panelType == '1') {
			if(chartType.equals("LineChart")) {
				chart = ChartFactory.createTimeSeriesChart(null, "time", "values", chartDataset, true, false, false);
			}
			else if (chartType.equals("AreaChart")) {
				DateAxis xAxis = new DateAxis("time");
				ValueAxis yAxis = new NumberAxis("values");
				XYItemRenderer renderer = new XYAreaRenderer();
				XYPlot plot = new XYPlot(chartDataset, xAxis, yAxis, renderer);
				setChart(new JFreeChart(null, new Font("Tahoma", 0, 18), plot, true));
			}
		}
		else if(this.panelType == 'c') {
			String[] s = new String[categoryStrings.size()];
			s= categoryStrings.toArray(s);
			if(chartType.equals("StepLineChart")) {
				DateAxis xAxis = new DateAxis("time");
				ValueAxis yAxis = new SymbolAxis("status", s);
				XYStepRenderer renderer = new XYStepRenderer();
				XYPlot plot = new XYPlot(chartDataset, xAxis, yAxis, renderer);
				setChart(new JFreeChart(null, new Font("Tahoma", 0, 18), plot, true));
			}
		}
		if (chartPanel != null) 
			monitoringPanel.remove(chartPanel);
		setChartPanel(new ChartPanel(chart));
		monitoringPanel.add(chartPanel, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		panelMonitoringSystem.getController().getMainInterface().revalidate();
		panelMonitoringSystem.getController().getMainInterface().repaint();
	}
	public void destroyPanelInstance() {
		try {
		for(int i = 0; i < variables.size(); i++) 
			variables.get(i).getDataSerie().removeChangeListener(variableBuffers.get(i));
		}catch(Exception e){}
		setVariableBuffers(null);
		setCategoryStrings(null);
		setChartPanel(null);
		setChart(null);
		setChartDataset(null);
		setMonitoringPanel(null);
		setPlayPause(null);
		setCalcResult(null);
		setCalcTargetCombobox(null);
		setCalculateCombobox(null);
	}
	public void categoryAdd(String str) {
		System.out.println("categoryAdd init");
		categoryStrings.add(str);
		refreshChart();
	}
	public void categoryRemove() {
		System.out.println("categoryRemove init");
		boolean[] existence = new boolean[categoryStrings.size()];
		for(int i = 0; i < variableBuffers.size();i++) {
			if (variables.get(i).getType() != 'i') {
				for (int j = 0; j < variableBuffers.get(i).getDataSerie().getItemCount(); j++) 
					existence[variableBuffers.get(i).getDataSerie().getValue(j).intValue()] = true;
			}
		}
		boolean noChange = true;
		for (int j = 0; j < existence.length; j++) 
			noChange = noChange && existence[j];
		if (!noChange) {
			int[] correction = new int[existence.length];
			ArrayList<String>newCategoryStrings = new ArrayList<String>();
			correction[0] = 0;
			for(int j = 0; j < existence.length; j++) {
				if (j != 0)
					correction[j] = correction[j - 1];
				if(existence[j]) 
					newCategoryStrings.add(categoryStrings.get(j));
				else {
					correction[j]++;
				}
			}
			setCategoryStrings(newCategoryStrings);
			for (int i = 0; i < variableBuffers.size(); i++)
				variableBuffers.get(i).setNewCategoryData(correction);
		}
		refreshChart();
	}
	
///////////////////////////Getters and Setters//////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PanelMonitoringSystem getPanelMonitoringSystem() {
		return panelMonitoringSystem;
	}
	public void setPanelMonitoringSystem(PanelMonitoringSystem panelMonitoringSystem) {
		this.panelMonitoringSystem = panelMonitoringSystem;
	}
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public TimeSeriesCollection getChartDataset() {
		return chartDataset;
	}
	public void setChartDataset(TimeSeriesCollection chartDataset) {
		this.chartDataset = chartDataset;
	}
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	public int[] getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(int[] timeRange) {
		this.timeRange = timeRange;
	}
	public ArrayList<VariableBuffer> getVariableBuffers() {
		return variableBuffers;
	}
	public void setVariableBuffers(ArrayList<VariableBuffer> variableBuffers) {
		this.variableBuffers = variableBuffers;
	}
	public char getPanelType() {
		return panelType;
	}
	public void setPanelType(char panelType) {
		this.panelType = panelType;
	}
	public ArrayList<String> getCategoryStrings() {
		return categoryStrings;
	}
	public void setCategoryStrings(ArrayList<String> categoryStrings) {
		this.categoryStrings = categoryStrings;
	}
	public ChartPanel getChartPanel() {
		return chartPanel;
	}
	public void setChartPanel(ChartPanel chartPanel) {
//		if(chartPanel != null) {
//			chartPanel.setPreferredSize(new Dimension(400, 200));
//		}
		this.chartPanel = chartPanel;
	}
	public JToggleButton getPlayPause() {
		return playPause;
	}
	public void setPlayPause(JToggleButton playPause) {
		this.playPause = playPause;
	}
	public JComboBox<String> getCalculateCombobox() {
		return this.calculateCombobox;
	}
	public void setCalculateCombobox(JComboBox<String> calculateCombobox) {
		this.calculateCombobox = calculateCombobox;
	}
	public JComboBox<String> getCalcTargetCombobox() {
		return calcTargetCombobox;
	}
	public void setCalcTargetCombobox(JComboBox<String> calcTargetCombobox) {
		this.calcTargetCombobox = calcTargetCombobox;
	}
	public JTextField getCalcResult() {
		return calcResult;
	}
	public void setCalcResult(JTextField calcResult) {
		this.calcResult = calcResult;
	}
	public JPanel getMonitoringPanel() {
		return monitoringPanel;
	}
	public void setMonitoringPanel(JPanel monitoringPanel) {
		this.monitoringPanel = monitoringPanel;
	}
	public JToggleButton getStartPause() {
		return startPause;
	}
	public void setStartPause(JToggleButton startPause) {
		this.startPause = startPause;
	}
	public JButton getReset() {
		return reset;
	}
	public void setReset(JButton reset) {
		this.reset = reset;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	public long getLastMillisecond() {
		return lastMillisecond;
	}
	public void setLastMillisecond(long lastMillisecond) {
		this.lastMillisecond = lastMillisecond;
	}
	public JToggleButton getPanelButton() {
		return panelButton;
	}
	public void setPanelButton(JToggleButton panelButton) {
		this.panelButton = panelButton;
	}
}

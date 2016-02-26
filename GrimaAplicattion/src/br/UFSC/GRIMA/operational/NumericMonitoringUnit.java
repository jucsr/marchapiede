package br.UFSC.GRIMA.operational;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeriesCollection;

import br.UFSC.GRIMA.dataStructure.Variable;

public class NumericMonitoringUnit extends MonitoringUnit {
	private ArrayList<NumericVariableBuffer> numericVariableBuffers;
	private TimeSeriesCollection chartDataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	////////////components////////
	private JComboBox<String> calculateCombobox;
	private JComboBox<String> calcTargetCombobox;
	private JTextField calcResult;
	private JToggleButton startPause;
	private JButton reset;
	private double result = 0;
	private long lastMillisecond = 0;
/////////////////////////////////////////Constructor/////////////////////////////////////////////////////////////////////////
	public NumericMonitoringUnit(String name, PanelMonitoringSystem panelMonitoringSystem, int[] timeRange,	String chartType, ArrayList<Variable> variables, char panelType) {
		super(name, panelMonitoringSystem, timeRange, chartType, variables, panelType);
		// TODO Auto-generated constructor stub
	}
/////////////////////////////////////////Methods///////////////////////////////////////////////////////////////////////////////
	@Override
	public void freezeChart(boolean freeze) {
		// TODO Auto-generated method stub
		chart.setNotify(freeze);
	}
	
	@Override
	public void actionPerformed2(ActionEvent e) {
		if(calcTargetCombobox != null) {
			if (e.getSource().equals(calcTargetCombobox)) {
				if (calcTargetCombobox.getSelectedIndex() >= 0 ) {
					if(getVariables().get(calcTargetCombobox.getSelectedIndex()).getType() == '1') {
						for(int i = 0; i < numericVariableBuffers.size(); i++) 
							numericVariableBuffers.get(i).setInCalc(false);
						numericVariableBuffers.get(calcTargetCombobox.getSelectedIndex()).setInCalc(true);
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
	
	@Override
	public void addVariableControl(Variable variable, JPanel container, int line, JLabel typeLabel, JTextField valueField) {
		// TODO Auto-generated method stub
		if(numericVariableBuffers == null)
			setNumericVariableBuffers(new ArrayList<NumericVariableBuffer>());
		NumericVariableBuffer numericvariableBuffer = new NumericVariableBuffer(variable, this);
		numericVariableBuffers.add(numericvariableBuffer);
		JToggleButton display = new JToggleButton();
		display.setSelected(true);
		container.add(display, new GridBagConstraints(5, line, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		numericvariableBuffer.setTypeLabel(typeLabel);
		numericvariableBuffer.setValueTextField(valueField);
		numericvariableBuffer.setDisplayButton(display);
		display.addActionListener(numericvariableBuffer);
	}

	@Override
	public void initAditionalPanelElements() {
		// TODO Auto-generated method stub
		chartDataset = new TimeSeriesCollection();
		for(int i = 0; i < numericVariableBuffers.size(); i++) 
			chartDataset.addSeries(numericVariableBuffers.get(i).getDataSerie());
		JPanel calcPanel = new JPanel();
		calcPanel.setLayout(new GridBagLayout());
		((GridBagLayout)calcPanel.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)calcPanel.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)calcPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
		((GridBagLayout)calcPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
		JLabel calc = new JLabel("Calculate");
		JLabel str = new JLabel("In"); 
		JToggleButton start = new JToggleButton("Start");
		JButton reset = new JButton("reset");
		JComboBox<String> operationCombobox = new JComboBox<String>();
		JComboBox<String> target = new JComboBox<String>();
		
		JLabel twoPoints = new JLabel(":");
		JTextField field = new JTextField();
		field.setEditable(false);
		calcPanel.add(calc, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		calcPanel.add(str, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		calcPanel.add(start, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		calcPanel.add(reset, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		calcPanel.add(operationCombobox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		calcPanel.add(target, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		calcPanel.add(twoPoints, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		calcPanel.add(field, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		getMonitoringPanel().add(calcPanel, new GridBagConstraints(0, 4, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		operationCombobox.setModel(new DefaultComboBoxModel<String>());
		operationCombobox.addItem("Integrate");
		operationCombobox.addItem("Derivate");
		operationCombobox.addItem("Maximum");
		operationCombobox.addItem("Minimum");
		operationCombobox.addItem("Average2");
		operationCombobox.addItem("Average5");
		operationCombobox.addItem("Average10");
		target.setModel(new DefaultComboBoxModel<String>());
		for(int j = 0; j < getVariables().size(); j++) {
			if (getVariables().get(j).getName() != null)
				target.addItem(getVariables().get(j).getName());
			else
				target.addItem(getVariables().get(j).getDataItemID());
		}
		setCalculateCombobox(operationCombobox);
		setCalcTargetCombobox(target);
		setCalcResult(field);
		setStartPause(start);
		setReset(reset);
		target.setSelectedItem(0);
		reset.addActionListener(this);
		target.addActionListener(this);
	}
	@Override
	public void refreshChart2() {
		// TODO Auto-generated method stub
		if(getChartType().equals("LineChart")) {
			DateAxis xAxis = new DateAxis("time");
			ValueAxis yAxis = new NumberAxis("values");
			XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
			XYPlot plot = new XYPlot(chartDataset, xAxis, yAxis, renderer);
			setChart(new JFreeChart(null, new Font("Tahoma", 0, 18), plot, true));
		}
		else if (getChartType().equals("AreaChart")) {
			DateAxis xAxis = new DateAxis("time");
			ValueAxis yAxis = new NumberAxis("values");
			XYItemRenderer renderer = new XYAreaRenderer();
			XYPlot plot = new XYPlot(chartDataset, xAxis, yAxis, renderer);
			setChart(new JFreeChart(null, new Font("Tahoma", 0, 18), plot, true));
		}
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
	public void destroyPanelInstance() {
		try {
		for(int i = 0; i < getVariables().size(); i++) 
			getVariables().get(i).getDataSerie().removeChangeListener(numericVariableBuffers.get(i));
		}catch(Exception e){}
		setNumericVariableBuffers(null);
		setChartPanel(null);
		setChart(null);
		setChartDataset(null);
		setMonitoringPanel(null);
		setPlayPause(null);
		setCalcResult(null);
		setCalcTargetCombobox(null);
		setCalculateCombobox(null);
	}
///////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////////////////////
	public TimeSeriesCollection getChartDataset() {
		return chartDataset;
	}
	public void setChartDataset(TimeSeriesCollection chartDataset) {
		this.chartDataset = chartDataset;
	}
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
	public JComboBox<String> getCalculateCombobox() {
		return calculateCombobox;
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
	public ArrayList<NumericVariableBuffer> getNumericVariableBuffers() {
		return numericVariableBuffers;
	}
	public void setNumericVariableBuffers(ArrayList<NumericVariableBuffer> numericVariableBuffers) {
		this.numericVariableBuffers = numericVariableBuffers;
	}
	
}

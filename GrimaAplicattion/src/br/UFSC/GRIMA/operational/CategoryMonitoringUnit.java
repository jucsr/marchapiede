package br.UFSC.GRIMA.operational;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.time.TimeSeriesCollection;

import br.UFSC.GRIMA.dataStructure.Variable;

public class CategoryMonitoringUnit extends MonitoringUnit {
	private ArrayList<String> categoryStrings;
	private ArrayList<CategoryVariableBuffer> categoryVariableBuffers;
	private TimeSeriesCollection chartDataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
//////////////////////////////////////////////////////constructor//////////////////////////////////////////////////////////////////
	public CategoryMonitoringUnit(String name,PanelMonitoringSystem panelMonitoringSystem, int[] timeRange,	String chartType, ArrayList<Variable> variables, char panelType) {
		super(name, panelMonitoringSystem, timeRange, chartType, variables, panelType);
		// TODO Auto-generated constructor stub
	}
/////////////////////////////////////////////////////Methods///////////////////////////////////////////////////////////////////////////////
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
	public void addVariableControl(Variable variable, JPanel container,	int line, JLabel typeLabel, JTextField valueField) {
		// TODO Auto-generated method stub
		if(categoryStrings == null) {
			setCategoryStrings(new ArrayList<String>());
			for(int i = 0; i < getVariables().size(); i++) {
				if (getVariables().get(i).getType() != 'i') {
					for(int j = 0; j < getVariables().get(i).getCategoryStrings().size(); j++) {
						if (!this.categoryStrings.contains(getVariables().get(i).getCategoryStrings().get(j)))
							categoryStrings.add(getVariables().get(i).getCategoryStrings().get(j));
					}
				}
			}
		}
		if(categoryVariableBuffers == null)
			setCategoryVariableBuffers(new ArrayList<CategoryVariableBuffer>());
		CategoryVariableBuffer categoryVariableBuffer = new CategoryVariableBuffer (variable, this);
		categoryVariableBuffers.add(categoryVariableBuffer);
		JToggleButton display = new JToggleButton();
		display.setSelected(true);
		container.add(display, new GridBagConstraints(5, line, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		categoryVariableBuffer.setTypeLabel(typeLabel);
		categoryVariableBuffer.setValueTextField(valueField);
		categoryVariableBuffer.setDisplayButton(display);
		display.addActionListener(categoryVariableBuffer);
	}

	@Override
	public void initAditionalPanelElements() {
		// TODO Auto-generated method stub
		chartDataset = new TimeSeriesCollection();
		for(int i = 0; i < categoryVariableBuffers.size(); i++) 
			chartDataset.addSeries(categoryVariableBuffers.get(i).getDataSerie());
	}

	@Override
	public void refreshChart2() {
		// TODO Auto-generated method stub
		String[] s = new String[categoryStrings.size()];
		s= categoryStrings.toArray(s);
		if(getChartType().equals("StepLineChart")) {
			DateAxis xAxis = new DateAxis("time");
			ValueAxis yAxis = new SymbolAxis("status", s);
			XYStepRenderer renderer = new XYStepRenderer();
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

	@Override
	public void destroyPanelInstance() {
		try {
		for(int i = 0; i < categoryVariableBuffers.size(); i++) 
			getVariables().get(i).getDataSerie().removeChangeListener(categoryVariableBuffers.get(i));
		}catch(Exception e){}
		setCategoryVariableBuffers(null);
		setCategoryStrings(null);
		setChartPanel(null);
		setChart(null);
		setChartDataset(null);
		setMonitoringPanel(null);
		setPlayPause(null);
	}
	public void categoryAdd(String str) {
		categoryStrings.add(str);
		refreshChart();
	}
	public void categoryRemove() {
		boolean[] existence = new boolean[categoryStrings.size()];
		for(int i = 0; i < categoryVariableBuffers.size();i++) {
			if (getVariables().get(i).getType() != 'i') {
				for (int j = 0; j < categoryVariableBuffers.get(i).getDataSerie().getItemCount(); j++) {
					if(categoryVariableBuffers.get(i).getDataSerie().getValue(j) != null)
						existence[categoryVariableBuffers.get(i).getDataSerie().getValue(j).intValue()] = true;
				}
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
			for (int i = 0; i < categoryVariableBuffers.size(); i++)
				categoryVariableBuffers.get(i).setNewCategoryData(correction);
		}
		refreshChart();
	}
////////////////////////////////////////////////////////////Getters and Setters////////////////////////////////////////////////////////////////////////////////
	public ArrayList<String> getCategoryStrings() {
		return categoryStrings;
	}
	public void setCategoryStrings(ArrayList<String> categoryStrings) {
		this.categoryStrings = categoryStrings;
	}
	public ArrayList<CategoryVariableBuffer> getCategoryVariableBuffers() {
		return categoryVariableBuffers;
	}
	public void setCategoryVariableBuffers(ArrayList<CategoryVariableBuffer> categoryVariableBuffers) {
		this.categoryVariableBuffers = categoryVariableBuffers;
	}
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
}

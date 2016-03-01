package br.UFSC.GRIMA.operational;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;

import br.UFSC.GRIMA.dataStructure.Variable;

public class VariableRegister implements SeriesChangeListener {
	private Variable variable;
	private TwoDMonitoringUnit twoDMonitoringUnit;
	private char varType;
	//////////panelComponents
	private JLabel typeLabel;
	private JTextField valueTextField;
/////////////////////////////////////////Constructor////////////////////////////////////////////////////
	public VariableRegister(Variable variable, TwoDMonitoringUnit monitoringUnit, JLabel typeLabel, JTextField valueTextField) {
		// TODO Auto-generated constructor stub
		setVariable(variable);
		setTwoDMonitoringUnit(monitoringUnit);
		setTypeLabel(typeLabel);
		setValueTextField(valueTextField);
		setVarType(variable.getType());
		variable.getDataSerie().addChangeListener(this);
	}
////////////////////////////////////////Methods////////////////////////////////////////////////////////
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		// TODO Auto-generated method stub
		if ((variable.getType() == 'i')&&(varType != 'i')) {
			typeLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
			typeLabel.setToolTipText("Irregular Variable Type: this variable show values that is neither numeric nor category variable Type.");
			setVarType('i');
		}
		else if(twoDMonitoringUnit != null) {
			if (twoDMonitoringUnit.getPlayPause() != null) {
				if(twoDMonitoringUnit.getPlayPause().isSelected())
					valueTextField.setText(variable.getLastValue());
			}
		}
	}
/////////////////////////////////////////Getters and Setters//////////////////////////////////////////////
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	public TwoDMonitoringUnit getTwoDMonitoringUnit() {
		return twoDMonitoringUnit;
	}
	public void setTwoDMonitoringUnit(TwoDMonitoringUnit twoDMonitoringUnit) {
		this.twoDMonitoringUnit = twoDMonitoringUnit;
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
	public char getVarType() {
		return varType;
	}
	public void setVarType(char varType) {
		this.varType = varType;
	}
	
}

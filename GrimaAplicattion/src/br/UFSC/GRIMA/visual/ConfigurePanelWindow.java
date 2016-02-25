/*
 * Created by JFormDesigner on Wed Feb 24 15:16:12 BRT 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class ConfigurePanelWindow extends JFrame {
	public ConfigurePanelWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel3 = new JPanel();
		label2 = new JLabel();
		hourFiled = new JSpinner();
		label3 = new JLabel();
		minuteField = new JSpinner();
		label4 = new JLabel();
		secondField = new JSpinner();
		label5 = new JLabel();
		panel2 = new JPanel();
		label6 = new JLabel();
		whidthField = new JSpinner();
		label7 = new JLabel();
		heightField = new JSpinner();
		label8 = new JLabel();
		checkBox1 = new JCheckBox();
		panel4 = new JPanel();
		label9 = new JLabel();
		chartTypeCombobox = new JComboBox();
		panel1 = new JPanel();
		minimizeButton = new JButton();
		maximizeButton = new JButton();
		removePanelButton = new JButton();
		clonePanelButton = new JButton();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconeLogo.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//======== panel3 ========
				{
					panel3.setLayout(new GridBagLayout());
					((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 45, 0, 45, 0, 45, 0, 0};
					((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//---- label2 ----
					label2.setText("Time Range");
					panel3.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- hourFiled ----
					hourFiled.setModel(new SpinnerNumberModel(0, 0, 23, 1));
					panel3.add(hourFiled, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label3 ----
					label3.setText("h");
					panel3.add(label3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- minuteField ----
					minuteField.setModel(new SpinnerNumberModel(0, 0, 59, 1));
					panel3.add(minuteField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label4 ----
					label4.setText("m");
					panel3.add(label4, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- secondField ----
					secondField.setModel(new SpinnerNumberModel(0, 0, 59, 1));
					panel3.add(secondField, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label5 ----
					label5.setText("s");
					panel3.add(label5, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== panel2 ========
				{
					panel2.setLayout(new GridBagLayout());
					((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
					((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//---- label6 ----
					label6.setText("Chart Minimum Size:");
					panel2.add(label6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- whidthField ----
					whidthField.setModel(new SpinnerNumberModel(0, 0, null, 10));
					panel2.add(whidthField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label7 ----
					label7.setText("X");
					panel2.add(label7, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- heightField ----
					heightField.setModel(new SpinnerNumberModel(0, 0, null, 10));
					panel2.add(heightField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- label8 ----
					label8.setText("pixels");
					panel2.add(label8, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- checkBox1 ----
				checkBox1.setText("Log Scale");
				contentPanel.add(checkBox1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== panel4 ========
				{
					panel4.setLayout(new GridBagLayout());
					((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
					((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//---- label9 ----
					label9.setText("Chart Type:");
					panel4.add(label9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
					panel4.add(chartTypeCombobox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== panel1 ========
				{
					panel1.setBorder(new TitledBorder("Panel Options"));
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

					//---- minimizeButton ----
					minimizeButton.setText("Minimize");
					panel1.add(minimizeButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- maximizeButton ----
					maximizeButton.setText("Maximize");
					panel1.add(maximizeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- removePanelButton ----
					removePanelButton.setText("Remove");
					panel1.add(removePanelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- clonePanelButton ----
					clonePanelButton.setText("Clone Panel");
					panel1.add(clonePanelButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				contentPanel.add(panel1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(405, 300);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel3;
	private JLabel label2;
	protected JSpinner hourFiled;
	private JLabel label3;
	protected JSpinner minuteField;
	private JLabel label4;
	protected JSpinner secondField;
	private JLabel label5;
	private JPanel panel2;
	private JLabel label6;
	protected JSpinner whidthField;
	private JLabel label7;
	protected JSpinner heightField;
	private JLabel label8;
	private JCheckBox checkBox1;
	private JPanel panel4;
	private JLabel label9;
	protected JComboBox chartTypeCombobox;
	private JPanel panel1;
	protected JButton minimizeButton;
	protected JButton maximizeButton;
	protected JButton removePanelButton;
	protected JButton clonePanelButton;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

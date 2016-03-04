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
		panel5 = new JPanel();
		label1 = new JLabel();
		nameField = new JTextField();
		panel3 = new JPanel();
		label2 = new JLabel();
		hourField = new JSpinner();
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
		logScaleCheckbox = new JCheckBox();
		panel4 = new JPanel();
		label9 = new JLabel();
		chartTypeCombobox = new JComboBox();
		panel11 = new JPanel();
		panel6 = new JPanel();
		deviceCombobox = new JComboBox<>();
		componentCombobox = new JComboBox<>();
		variableCombobox = new JComboBox<>();
		addVariableButton = new JButton();
		scrollPane1 = new JScrollPane();
		panel7 = new JPanel();
		scrollPane2 = new JScrollPane();
		workspace = new JPanel();
		optionPanel = new JPanel();
		minimizeButton = new JButton();
		maximizeButton = new JButton();
		removePanelButton = new JButton();
		clonePanelButton = new JButton();
		axisSelectPanel = new JPanel();
		panel9 = new JPanel();
		label10 = new JLabel();
		xAxisCombobox = new JComboBox();
		panel10 = new JPanel();
		label11 = new JLabel();
		yAxisCombobox = new JComboBox();
		zAxisPanel = new JPanel();
		label12 = new JLabel();
		zAxisCombobox = new JComboBox();
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
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

				//======== panel5 ========
				{
					panel5.setLayout(new GridBagLayout());
					((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//---- label1 ----
					label1.setText("Panel Name");
					panel5.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- nameField ----
					nameField.setText("Painel0");
					panel5.add(nameField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));
				}
				contentPanel.add(panel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

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

					//---- hourField ----
					hourField.setModel(new SpinnerNumberModel(0, 0, 23, 1));
					panel3.add(hourField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
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
				contentPanel.add(panel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
				contentPanel.add(panel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- logScaleCheckbox ----
				logScaleCheckbox.setText("Log Scale");
				contentPanel.add(logScaleCheckbox, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
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
				contentPanel.add(panel4, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== panel11 ========
				{
					panel11.setBorder(new TitledBorder("Panel Variables"));
					panel11.setLayout(new GridBagLayout());
					((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

					//======== panel6 ========
					{
						panel6.setLayout(new GridBagLayout());
						((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 0.0, 1.0E-4};
						((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- deviceCombobox ----
						deviceCombobox.setModel(new DefaultComboBoxModel<>(new String[] {
							" "
						}));
						panel6.add(deviceCombobox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- componentCombobox ----
						componentCombobox.setModel(new DefaultComboBoxModel<>(new String[] {
							" "
						}));
						panel6.add(componentCombobox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- variableCombobox ----
						variableCombobox.setModel(new DefaultComboBoxModel<>(new String[] {
							" "
						}));
						panel6.add(variableCombobox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- addVariableButton ----
						addVariableButton.setText("+");
						panel6.add(addVariableButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel11.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== scrollPane1 ========
					{
						scrollPane1.setBorder(null);

						//======== panel7 ========
						{
							panel7.setBorder(new BevelBorder(BevelBorder.LOWERED));
							panel7.setLayout(new GridBagLayout());
							((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0};
							((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
							((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== scrollPane2 ========
							{
								scrollPane2.setBorder(null);

								//======== workspace ========
								{
									workspace.setBorder(null);
									workspace.setLayout(new GridBagLayout());
									((GridBagLayout)workspace.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
									((GridBagLayout)workspace.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)workspace.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
									((GridBagLayout)workspace.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
								}
								scrollPane2.setViewportView(workspace);
							}
							panel7.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						scrollPane1.setViewportView(panel7);
					}
					panel11.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel11, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== optionPanel ========
				{
					optionPanel.setBorder(new TitledBorder("Panel Options"));
					optionPanel.setLayout(new GridBagLayout());
					((GridBagLayout)optionPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)optionPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)optionPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
					((GridBagLayout)optionPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

					//---- minimizeButton ----
					minimizeButton.setText("Minimize");
					optionPanel.add(minimizeButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- maximizeButton ----
					maximizeButton.setText("Maximize");
					optionPanel.add(maximizeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- removePanelButton ----
					removePanelButton.setText("Remove");
					optionPanel.add(removePanelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- clonePanelButton ----
					clonePanelButton.setText("Clone Panel");
					optionPanel.add(clonePanelButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				contentPanel.add(optionPanel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== axisSelectPanel ========
				{
					axisSelectPanel.setBorder(new TitledBorder("Axis Select"));
					axisSelectPanel.setLayout(new GridBagLayout());
					((GridBagLayout)axisSelectPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)axisSelectPanel.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)axisSelectPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
					((GridBagLayout)axisSelectPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//======== panel9 ========
					{
						panel9.setLayout(new GridBagLayout());
						((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- label10 ----
						label10.setText("Abssice");
						panel9.add(label10, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
						panel9.add(xAxisCombobox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					axisSelectPanel.add(panel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//======== panel10 ========
					{
						panel10.setLayout(new GridBagLayout());
						((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- label11 ----
						label11.setText("Ordinate");
						panel10.add(label11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
						panel10.add(yAxisCombobox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					axisSelectPanel.add(panel10, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//======== zAxisPanel ========
					{
						zAxisPanel.setLayout(new GridBagLayout());
						((GridBagLayout)zAxisPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)zAxisPanel.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)zAxisPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)zAxisPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- label12 ----
						label12.setText("Applicate");
						zAxisPanel.add(label12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
						zAxisPanel.add(zAxisCombobox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					axisSelectPanel.add(zAxisPanel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(axisSelectPanel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
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
		setSize(460, 520);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel5;
	private JLabel label1;
	protected JTextField nameField;
	private JPanel panel3;
	private JLabel label2;
	protected JSpinner hourField;
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
	protected JCheckBox logScaleCheckbox;
	private JPanel panel4;
	private JLabel label9;
	protected JComboBox chartTypeCombobox;
	private JPanel panel11;
	private JPanel panel6;
	protected JComboBox<String> deviceCombobox;
	protected JComboBox<String> componentCombobox;
	protected JComboBox<String> variableCombobox;
	protected JButton addVariableButton;
	private JScrollPane scrollPane1;
	private JPanel panel7;
	private JScrollPane scrollPane2;
	protected JPanel workspace;
	protected JPanel optionPanel;
	protected JButton minimizeButton;
	protected JButton maximizeButton;
	protected JButton removePanelButton;
	protected JButton clonePanelButton;
	protected JPanel axisSelectPanel;
	private JPanel panel9;
	private JLabel label10;
	protected JComboBox xAxisCombobox;
	private JPanel panel10;
	private JLabel label11;
	protected JComboBox yAxisCombobox;
	protected JPanel zAxisPanel;
	private JLabel label12;
	protected JComboBox zAxisCombobox;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

/*
 * Created by JFormDesigner on Wed Feb 17 15:39:22 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class PreferencesWindow extends JFrame {
	public PreferencesWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		panel3 = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		setDatabaseButton = new JButton();
		checkBox1 = new JCheckBox();
		panel4 = new JPanel();
		label1 = new JLabel();
		dataBaseUserField = new JTextField();
		label2 = new JLabel();
		dataBaseSenhaField = new JTextField();
		label3 = new JLabel();
		dataBaseIPField = new JTextField();
		label4 = new JLabel();
		dataBaseFolderField = new JTextField();
		panel5 = new JPanel();
		panel6 = new JPanel();
		label5 = new JLabel();
		textField2 = new JTextField();
		label6 = new JLabel();
		panel8 = new JPanel();
		textField3 = new JTextField();
		label11 = new JLabel();
		panel9 = new JPanel();
		radioGeneral = new JRadioButton();
		radioSplitted = new JRadioButton();
		generalTimeRangePanel = new JPanel();
		label7 = new JLabel();
		panel7 = new JPanel();
		textField4 = new JTextField();
		label8 = new JLabel();
		textField5 = new JTextField();
		label9 = new JLabel();
		textField6 = new JTextField();
		label10 = new JLabel();
		setTimeRangeButton = new JButton();
		splitTimeRangePanel = new JPanel();
		numericTimeRangePanel = new JPanel();
		label12 = new JLabel();
		panel11 = new JPanel();
		textField7 = new JTextField();
		label13 = new JLabel();
		textField8 = new JTextField();
		label14 = new JLabel();
		textField9 = new JTextField();
		label15 = new JLabel();
		setTimeRangeButton2 = new JButton();
		categoricTimeRangePanel3 = new JPanel();
		label16 = new JLabel();
		panel12 = new JPanel();
		textField10 = new JTextField();
		label17 = new JLabel();
		textField11 = new JTextField();
		label18 = new JLabel();
		textField12 = new JTextField();
		label19 = new JLabel();
		setTimeRangeButton3 = new JButton();
		buttonBar = new JPanel();
		okButton = new JButton();

		//======== this ========
		setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconeLogo.png")).getImage());
		setTitle("Set Preferences");
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
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== scrollPane1 ========
				{

					//======== panel3 ========
					{
						panel3.setLayout(new GridBagLayout());
						((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

						//======== panel1 ========
						{
							panel1.setBorder(new TitledBorder("Database Info"));
							panel1.setLayout(new GridBagLayout());
							((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
							((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
							((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

							//======== panel2 ========
							{
								panel2.setLayout(new GridBagLayout());
								((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//---- setDatabaseButton ----
								setDatabaseButton.setText("Set Database");
								setDatabaseButton.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/downloadIcon.png")));
								panel2.add(setDatabaseButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));
							}
							panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//---- checkBox1 ----
							checkBox1.setText("Autosave Monitored Variables");
							checkBox1.setSelected(true);
							panel1.add(checkBox1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel4 ========
							{
								panel4.setLayout(new GridBagLayout());
								((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
								((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

								//---- label1 ----
								label1.setText("User:");
								panel4.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- dataBaseUserField ----
								dataBaseUserField.setEditable(false);
								panel4.add(dataBaseUserField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label2 ----
								label2.setText("Senha:");
								panel4.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- dataBaseSenhaField ----
								dataBaseSenhaField.setEditable(false);
								panel4.add(dataBaseSenhaField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label3 ----
								label3.setText("ip:");
								panel4.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- dataBaseIPField ----
								dataBaseIPField.setEditable(false);
								panel4.add(dataBaseIPField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label4 ----
								label4.setText("Folder:");
								panel4.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- dataBaseFolderField ----
								dataBaseFolderField.setEditable(false);
								panel4.add(dataBaseFolderField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel1.add(panel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel3.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel5 ========
						{
							panel5.setBorder(new TitledBorder("Time Information"));
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

							//======== panel6 ========
							{
								panel6.setLayout(new GridBagLayout());
								((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

								//---- label5 ----
								label5.setText("Last Time Registered:");
								panel6.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- textField2 ----
								textField2.setEditable(false);
								panel6.add(textField2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label6 ----
								label6.setText("Last Loop Time:");
								panel6.add(label6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//======== panel8 ========
								{
									panel8.setLayout(new GridBagLayout());
									((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
									((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- textField3 ----
									textField3.setEditable(false);
									panel8.add(textField3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label11 ----
									label11.setText("Milliseconds");
									panel8.add(label11, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel6.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel5.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel9 ========
							{
								panel9.setLayout(new GridBagLayout());
								((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
								((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//---- radioGeneral ----
								radioGeneral.setText("General Time Range");
								radioGeneral.setSelected(true);
								panel9.add(radioGeneral, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- radioSplitted ----
								radioSplitted.setText("Splitted Time Range");
								panel9.add(radioSplitted, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel5.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== generalTimeRangePanel ========
							{
								generalTimeRangePanel.setLayout(new GridBagLayout());
								((GridBagLayout)generalTimeRangePanel.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)generalTimeRangePanel.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)generalTimeRangePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)generalTimeRangePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//---- label7 ----
								label7.setText("General Default Time Range: ");
								generalTimeRangePanel.add(label7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//======== panel7 ========
								{
									panel7.setLayout(new GridBagLayout());
									((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
									((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
									((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
									panel7.add(textField4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label8 ----
									label8.setText("h:  ");
									panel7.add(label8, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
									panel7.add(textField5, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label9 ----
									label9.setText("min:   ");
									panel7.add(label9, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
									panel7.add(textField6, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- label10 ----
									label10.setText("seg ");
									panel7.add(label10, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- setTimeRangeButton ----
									setTimeRangeButton.setText("Apply");
									setTimeRangeButton.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/changeIcon.gif")));
									panel7.add(setTimeRangeButton, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								generalTimeRangePanel.add(panel7, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel5.add(generalTimeRangePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== splitTimeRangePanel ========
							{
								splitTimeRangePanel.setVisible(false);
								splitTimeRangePanel.setLayout(new GridBagLayout());
								((GridBagLayout)splitTimeRangePanel.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)splitTimeRangePanel.getLayout()).rowHeights = new int[] {0, 0, 0};
								((GridBagLayout)splitTimeRangePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)splitTimeRangePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

								//======== numericTimeRangePanel ========
								{
									numericTimeRangePanel.setLayout(new GridBagLayout());
									((GridBagLayout)numericTimeRangePanel.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)numericTimeRangePanel.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)numericTimeRangePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)numericTimeRangePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- label12 ----
									label12.setText("Numeric Default Time Range: ");
									numericTimeRangePanel.add(label12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//======== panel11 ========
									{
										panel11.setLayout(new GridBagLayout());
										((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
										((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
										((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
										panel11.add(textField7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label13 ----
										label13.setText("h:  ");
										panel11.add(label13, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));
										panel11.add(textField8, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label14 ----
										label14.setText("min:   ");
										panel11.add(label14, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));
										panel11.add(textField9, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label15 ----
										label15.setText("seg ");
										panel11.add(label15, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- setTimeRangeButton2 ----
										setTimeRangeButton2.setText("Apply");
										setTimeRangeButton2.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/changeIcon.gif")));
										panel11.add(setTimeRangeButton2, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									numericTimeRangePanel.add(panel11, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								splitTimeRangePanel.add(numericTimeRangePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== categoricTimeRangePanel3 ========
								{
									categoricTimeRangePanel3.setLayout(new GridBagLayout());
									((GridBagLayout)categoricTimeRangePanel3.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)categoricTimeRangePanel3.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)categoricTimeRangePanel3.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)categoricTimeRangePanel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- label16 ----
									label16.setText("Categoric Default Time Range: ");
									categoricTimeRangePanel3.add(label16, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//======== panel12 ========
									{
										panel12.setLayout(new GridBagLayout());
										((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
										((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
										((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
										panel12.add(textField10, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label17 ----
										label17.setText("h:  ");
										panel12.add(label17, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));
										panel12.add(textField11, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label18 ----
										label18.setText("min:   ");
										panel12.add(label18, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));
										panel12.add(textField12, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label19 ----
										label19.setText("seg ");
										panel12.add(label19, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- setTimeRangeButton3 ----
										setTimeRangeButton3.setText("Apply");
										setTimeRangeButton3.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/changeIcon.gif")));
										panel12.add(setTimeRangeButton3, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									categoricTimeRangePanel3.add(panel12, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								splitTimeRangePanel.add(categoricTimeRangePanel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel5.add(splitTimeRangePanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel3.add(panel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					scrollPane1.setViewportView(panel3);
				}
				contentPanel.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(radioGeneral);
		buttonGroup1.add(radioSplitted);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JScrollPane scrollPane1;
	private JPanel panel3;
	private JPanel panel1;
	private JPanel panel2;
	protected JButton setDatabaseButton;
	private JCheckBox checkBox1;
	private JPanel panel4;
	private JLabel label1;
	protected JTextField dataBaseUserField;
	private JLabel label2;
	protected JTextField dataBaseSenhaField;
	private JLabel label3;
	protected JTextField dataBaseIPField;
	private JLabel label4;
	protected JTextField dataBaseFolderField;
	private JPanel panel5;
	private JPanel panel6;
	private JLabel label5;
	private JTextField textField2;
	private JLabel label6;
	private JPanel panel8;
	private JTextField textField3;
	private JLabel label11;
	private JPanel panel9;
	private JRadioButton radioGeneral;
	private JRadioButton radioSplitted;
	protected JPanel generalTimeRangePanel;
	private JLabel label7;
	private JPanel panel7;
	private JTextField textField4;
	private JLabel label8;
	private JTextField textField5;
	private JLabel label9;
	private JTextField textField6;
	private JLabel label10;
	protected JButton setTimeRangeButton;
	protected JPanel splitTimeRangePanel;
	protected JPanel numericTimeRangePanel;
	private JLabel label12;
	private JPanel panel11;
	private JTextField textField7;
	private JLabel label13;
	private JTextField textField8;
	private JLabel label14;
	private JTextField textField9;
	private JLabel label15;
	protected JButton setTimeRangeButton2;
	protected JPanel categoricTimeRangePanel3;
	private JLabel label16;
	private JPanel panel12;
	private JTextField textField10;
	private JLabel label17;
	private JTextField textField11;
	private JLabel label18;
	private JTextField textField12;
	private JLabel label19;
	protected JButton setTimeRangeButton3;
	private JPanel buttonBar;
	protected JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

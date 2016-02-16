/*
 * Created by JFormDesigner on Wed Jan 20 23:50:54 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class CreatePanelWindow extends JFrame {
	public CreatePanelWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		label1 = new JLabel();
		nameField = new JTextField();
		panel3 = new JPanel();
		label2 = new JLabel();
		hourField = new JTextField();
		label3 = new JLabel();
		minuteField = new JTextField();
		label4 = new JLabel();
		secondField = new JTextField();
		label5 = new JLabel();
		panel4 = new JPanel();
		label6 = new JLabel();
		charTypeComboBox = new JComboBox<>();
		button1 = new JButton();
		panel5 = new JPanel();
		deviceCombobox = new JComboBox<>();
		componentCombobox = new JComboBox<>();
		variableCombobox = new JComboBox<>();
		addVariableButton = new JButton();
		panel6 = new JPanel();
		scrollPane1 = new JScrollPane();
		workspace = new JPanel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Configure New Monitoring Panel");
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
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

				//======== panel1 ========
				{
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

					//======== panel2 ========
					{
						panel2.setLayout(new GridBagLayout());
						((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- label1 ----
						label1.setText("Panel Name");
						panel2.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- nameField ----
						nameField.setText("Painel0");
						panel2.add(nameField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel1.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
						hourField.setText("0");
						panel3.add(hourField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- label3 ----
						label3.setText("h");
						panel3.add(label3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- minuteField ----
						minuteField.setText("0");
						panel3.add(minuteField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- label4 ----
						label4.setText("m");
						panel3.add(label4, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- secondField ----
						secondField.setText("0");
						panel3.add(secondField, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- label5 ----
						label5.setText("s");
						panel3.add(label5, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel1.add(panel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== panel4 ========
					{
						panel4.setLayout(new GridBagLayout());
						((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
						((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- label6 ----
						label6.setText("Chart Type:");
						panel4.add(label6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- charTypeComboBox ----
						charTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
							" "
						}));
						panel4.add(charTypeComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- button1 ----
						button1.setText("New Variable");
						panel4.add(button1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel1.add(panel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== panel5 ========
					{
						panel5.setLayout(new GridBagLayout());
						((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 0.0, 1.0E-4};
						((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- deviceCombobox ----
						deviceCombobox.setModel(new DefaultComboBoxModel<>(new String[] {
							" "
						}));
						panel5.add(deviceCombobox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- componentCombobox ----
						componentCombobox.setModel(new DefaultComboBoxModel<>(new String[] {
							" "
						}));
						panel5.add(componentCombobox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- variableCombobox ----
						variableCombobox.setModel(new DefaultComboBoxModel<>(new String[] {
							" "
						}));
						panel5.add(variableCombobox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- addVariableButton ----
						addVariableButton.setText("+");
						panel5.add(addVariableButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel1.add(panel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== panel6 ========
					{
						panel6.setBorder(new BevelBorder(BevelBorder.LOWERED));
						panel6.setLayout(new GridBagLayout());
						((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

						//======== scrollPane1 ========
						{
							scrollPane1.setBorder(null);

							//======== workspace ========
							{
								workspace.setBorder(null);
								workspace.setLayout(new GridBagLayout());
								((GridBagLayout)workspace.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
								((GridBagLayout)workspace.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)workspace.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
								((GridBagLayout)workspace.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
							}
							scrollPane1.setViewportView(workspace);
						}
						panel6.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel1.add(panel6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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
		setSize(420, 350);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel1;
	private JPanel panel2;
	private JLabel label1;
	protected JTextField nameField;
	private JPanel panel3;
	private JLabel label2;
	protected JTextField hourField;
	private JLabel label3;
	protected JTextField minuteField;
	private JLabel label4;
	public JTextField secondField;
	private JLabel label5;
	private JPanel panel4;
	private JLabel label6;
	protected JComboBox<String> charTypeComboBox;
	private JButton button1;
	private JPanel panel5;
	protected JComboBox<String> deviceCombobox;
	protected JComboBox<String> componentCombobox;
	protected JComboBox<String> variableCombobox;
	protected JButton addVariableButton;
	private JPanel panel6;
	private JScrollPane scrollPane1;
	protected JPanel workspace;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

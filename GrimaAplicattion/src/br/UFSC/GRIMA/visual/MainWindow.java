/*
 * Created by JFormDesigner on Wed Jan 27 13:34:15 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;

/**
 * @author Jc
 */
public class MainWindow extends JFrame {
	public MainWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem2 = new JMenuItem();
		menu2 = new JMenu();
		menuItem1 = new JMenuItem();
		panel7 = new JPanel();
		label1 = new JLabel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		button1 = new JButton();
		deviceConfigureButton = new JButton();
		button3 = new JButton();
		panel4 = new JPanel();
		panel5 = new JPanel();
		label2 = new JLabel();
		createPanelButton = new JButton();
		deviceInfoButton = new JToggleButton();
		deviceMonitoringButton = new JToggleButton();
		panelMonitoringButton = new JToggleButton();
		panel6 = new JPanel();
		label3 = new JLabel();
		mainAgentField = new JTextField();
		label4 = new JLabel();
		currentTimeField = new JTextField();
		splitPane1 = new JSplitPane();
		scrollPane2 = new JScrollPane();
		workSpace = new JPanel();
		scrollPane1 = new JScrollPane();
		textPane1 = new JTextPane();

		//======== this ========
		setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconeLogo.png")).getImage());
		setTitle("Application (Main Window)");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");
				menu1.setFont(new Font("Verdana", Font.PLAIN, 12));

				//---- menuItem2 ----
				menuItem2.setText("exit");
				menuItem2.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/process-stop.png")));
				menuItem2.setFont(new Font("Verdana", Font.PLAIN, 12));
				menu1.add(menuItem2);
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("Help");
				menu2.setFont(new Font("Verdana", Font.PLAIN, 12));

				//---- menuItem1 ----
				menuItem1.setText("About");
				menuItem1.setIcon(null);
				menuItem1.setFont(new Font("Verdana", Font.PLAIN, 12));
				menu2.add(menuItem1);
			}
			menuBar1.add(menu2);
		}
		setJMenuBar(menuBar1);

		//======== panel7 ========
		{
			panel7.setLayout(new GridBagLayout());
			((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0};
			((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
			((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
			((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

			//---- label1 ----
			label1.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/logofinal.png")));
			panel7.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//======== panel2 ========
			{
				panel2.setLayout(new GridBagLayout());
				((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
				((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//======== panel3 ========
				{
					panel3.setLayout(new GridBagLayout());
					((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//---- button1 ----
					button1.setText("Properties");
					button1.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/Gears-icon.png")));
					panel3.add(button1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- deviceConfigureButton ----
					deviceConfigureButton.setText("Configure Devices");
					deviceConfigureButton.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/machineIcon.png")));
					panel3.add(deviceConfigureButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- button3 ----
					button3.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconInterrogacao.png")));
					panel3.add(button3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				panel2.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//======== panel4 ========
				{
					panel4.setLayout(new GridBagLayout());
					((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
					((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//======== panel5 ========
					{
						panel5.setLayout(new GridBagLayout());
						((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
						((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

						//---- label2 ----
						label2.setText("Requests:");
						panel5.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- createPanelButton ----
						createPanelButton.setText("New Panel");
						panel5.add(createPanelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- deviceInfoButton ----
						deviceInfoButton.setText("Device Information");
						panel5.add(deviceInfoButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- deviceMonitoringButton ----
						deviceMonitoringButton.setText("Device Monitoring");
						panel5.add(deviceMonitoringButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- panelMonitoringButton ----
						panelMonitoringButton.setText("Panel Monitoring");
						panel5.add(panelMonitoringButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel4.add(panel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//======== panel6 ========
					{
						panel6.setLayout(new GridBagLayout());
						((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

						//---- label3 ----
						label3.setText("Main Agent:");
						panel6.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
						panel6.add(mainAgentField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- label4 ----
						label4.setText("Reference Time:");
						panel6.add(label4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
						panel6.add(currentTimeField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel4.add(panel6, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				panel2.add(panel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			panel7.add(panel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));
		}
		contentPane.add(panel7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//======== splitPane1 ========
		{
			splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane1.setResizeWeight(0.8);

			//======== scrollPane2 ========
			{

				//======== workSpace ========
				{
					workSpace.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					workSpace.setLayout(new GridBagLayout());
					((GridBagLayout)workSpace.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)workSpace.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)workSpace.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)workSpace.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
				}
				scrollPane2.setViewportView(workSpace);
			}
			splitPane1.setTopComponent(scrollPane2);

			//======== scrollPane1 ========
			{

				//---- textPane1 ----
				textPane1.setEditable(false);
				textPane1.setFont(new Font("Verdana", Font.PLAIN, 12));
				scrollPane1.setViewportView(textPane1);
			}
			splitPane1.setBottomComponent(scrollPane1);
		}
		contentPane.add(splitPane1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		setSize(815, 455);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	protected JMenuItem menuItem2;
	private JMenu menu2;
	protected JMenuItem menuItem1;
	private JPanel panel7;
	private JLabel label1;
	private JPanel panel2;
	private JPanel panel3;
	private JButton button1;
	protected JButton deviceConfigureButton;
	private JButton button3;
	private JPanel panel4;
	private JPanel panel5;
	private JLabel label2;
	protected JButton createPanelButton;
	protected JToggleButton deviceInfoButton;
	protected JToggleButton deviceMonitoringButton;
	protected JToggleButton panelMonitoringButton;
	private JPanel panel6;
	private JLabel label3;
	protected JTextField mainAgentField;
	private JLabel label4;
	protected JTextField currentTimeField;
	private JSplitPane splitPane1;
	private JScrollPane scrollPane2;
	protected JPanel workSpace;
	private JScrollPane scrollPane1;
	protected JTextPane textPane1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

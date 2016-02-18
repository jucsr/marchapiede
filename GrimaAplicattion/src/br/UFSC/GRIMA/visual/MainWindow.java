/*
 * Created by JFormDesigner on Wed Jan 27 13:34:15 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

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
		menuExit = new JMenuItem();
		menu2 = new JMenu();
		menuPreferences = new JMenuItem();
		menuDeviceConfigure = new JMenuItem();
		menuDatabase = new JMenuItem();
		menu3 = new JMenu();
		menuAgents = new JMenuItem();
		menuAddAgent = new JMenuItem();
		menu4 = new JMenu();
		menuDeviceInfo = new JMenuItem();
		menuDevices = new JMenu();
		menu6 = new JMenu();
		menuCameraInfo = new JMenuItem();
		menuAddCamera = new JMenuItem();
		menuView = new JMenu();
		menu8 = new JMenu();
		menuPanels = new JMenu();
		menuViewPanels = new JMenuItem();
		menuAddPanel = new JMenuItem();
		menu5 = new JMenu();
		menuAbout = new JMenuItem();
		panel7 = new JPanel();
		panel2 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		deviceInfoButton = new JToggleButton();
		deviceMonitoringButton = new JToggleButton();
		panelMonitoringButton = new JToggleButton();
		panel6 = new JPanel();
		label4 = new JLabel();
		currentTimeField = new JTextField();
		label1 = new JLabel();
		loopTimeField = new JTextField();
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
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");
				menu1.setFont(new Font("Verdana", Font.PLAIN, 12));

				//---- menuExit ----
				menuExit.setText("exit");
				menuExit.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/process-stop.png")));
				menuExit.setFont(new Font("Verdana", Font.PLAIN, 12));
				menu1.add(menuExit);
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("Configure");
				menu2.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuPreferences ----
				menuPreferences.setText("Preferences");
				menuPreferences.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/Gears-icon.png")));
				menuPreferences.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu2.add(menuPreferences);

				//---- menuDeviceConfigure ----
				menuDeviceConfigure.setText("Configure Imput Data");
				menuDeviceConfigure.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/machineIcon.png")));
				menuDeviceConfigure.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu2.add(menuDeviceConfigure);

				//---- menuDatabase ----
				menuDatabase.setText("Configure Database");
				menuDatabase.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/downloadIcon.png")));
				menuDatabase.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu2.add(menuDatabase);
			}
			menuBar1.add(menu2);

			//======== menu3 ========
			{
				menu3.setText("Agent");
				menu3.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuAgents ----
				menuAgents.setText("Agents info");
				menuAgents.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/agentIcon.png")));
				menuAgents.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu3.add(menuAgents);

				//---- menuAddAgent ----
				menuAddAgent.setText("Add Agent");
				menuAddAgent.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/plusIcon.png")));
				menuAddAgent.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu3.add(menuAddAgent);
			}
			menuBar1.add(menu3);

			//======== menu4 ========
			{
				menu4.setText("Device");
				menu4.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuDeviceInfo ----
				menuDeviceInfo.setText("Devices info");
				menuDeviceInfo.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/machineIcon.png")));
				menuDeviceInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu4.add(menuDeviceInfo);

				//======== menuDevices ========
				{
					menuDevices.setText("Monitor");
					menuDevices.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/viewIcon.png")));
					menuDevices.setFont(new Font("Dialog", Font.PLAIN, 12));
				}
				menu4.add(menuDevices);
			}
			menuBar1.add(menu4);

			//======== menu6 ========
			{
				menu6.setText("Webcam");
				menu6.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuCameraInfo ----
				menuCameraInfo.setText("Camera Info");
				menuCameraInfo.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/cameraIcon.png")));
				menuCameraInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu6.add(menuCameraInfo);

				//---- menuAddCamera ----
				menuAddCamera.setText("Add Camera");
				menuAddCamera.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/plusIcon.png")));
				menuAddCamera.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu6.add(menuAddCamera);

				//======== menuView ========
				{
					menuView.setText("View");
					menuView.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/viewIcon.png")));
					menuView.setFont(new Font("Dialog", Font.PLAIN, 12));
				}
				menu6.add(menuView);
			}
			menuBar1.add(menu6);

			//======== menu8 ========
			{
				menu8.setText("Panels");
				menu8.setFont(new Font("Dialog", Font.PLAIN, 12));

				//======== menuPanels ========
				{
					menuPanels.setText("Configure");
					menuPanels.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/Gears-icon.png")));
					menuPanels.setFont(new Font("Dialog", Font.PLAIN, 12));
				}
				menu8.add(menuPanels);

				//---- menuViewPanels ----
				menuViewPanels.setText("View Panels");
				menuViewPanels.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/monitoringIcon.png")));
				menuViewPanels.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu8.add(menuViewPanels);

				//---- menuAddPanel ----
				menuAddPanel.setText("Add Panel");
				menuAddPanel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/plusIcon.png")));
				menuAddPanel.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu8.add(menuAddPanel);
			}
			menuBar1.add(menu8);

			//======== menu5 ========
			{
				menu5.setText("Help");
				menu5.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuAbout ----
				menuAbout.setText("About");
				menuAbout.setActionCommand("About");
				menuAbout.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu5.add(menuAbout);
			}
			menuBar1.add(menu5);
		}
		setJMenuBar(menuBar1);

		//======== panel7 ========
		{
			panel7.setLayout(new GridBagLayout());
			((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//======== panel2 ========
			{
				panel2.setLayout(new GridBagLayout());
				((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//======== panel4 ========
				{
					panel4.setLayout(new GridBagLayout());
					((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {6, 0, 0, 1, 0};
					((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//======== panel5 ========
					{
						panel5.setLayout(new GridBagLayout());
						((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
						((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- deviceInfoButton ----
						deviceInfoButton.setText("Device Information");
						panel5.add(deviceInfoButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- deviceMonitoringButton ----
						deviceMonitoringButton.setText("Device Monitoring");
						panel5.add(deviceMonitoringButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- panelMonitoringButton ----
						panelMonitoringButton.setText("Panel Monitoring");
						panel5.add(panelMonitoringButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel4.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//======== panel6 ========
					{
						panel6.setLayout(new GridBagLayout());
						((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

						//---- label4 ----
						label4.setText("Reference Time:");
						panel6.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- currentTimeField ----
						currentTimeField.setEditable(false);
						panel6.add(currentTimeField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- label1 ----
						label1.setText("Loop Time:");
						panel6.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- loopTimeField ----
						loopTimeField.setEditable(false);
						panel6.add(loopTimeField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel4.add(panel6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				panel2.add(panel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel7.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//======== splitPane1 ========
		{
			splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane1.setResizeWeight(0.8);
			splitPane1.setOneTouchExpandable(true);
			splitPane1.setBorder(new EtchedBorder());

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
		contentPane.add(splitPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		setSize(815, 455);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	protected JMenuItem menuExit;
	private JMenu menu2;
	protected JMenuItem menuPreferences;
	protected JMenuItem menuDeviceConfigure;
	protected JMenuItem menuDatabase;
	private JMenu menu3;
	protected JMenuItem menuAgents;
	protected JMenuItem menuAddAgent;
	private JMenu menu4;
	protected JMenuItem menuDeviceInfo;
	public JMenu menuDevices;
	private JMenu menu6;
	protected JMenuItem menuCameraInfo;
	protected JMenuItem menuAddCamera;
	public JMenu menuView;
	private JMenu menu8;
	public JMenu menuPanels;
	protected JMenuItem menuViewPanels;
	protected JMenuItem menuAddPanel;
	private JMenu menu5;
	protected JMenuItem menuAbout;
	private JPanel panel7;
	private JPanel panel2;
	private JPanel panel4;
	private JPanel panel5;
	protected JToggleButton deviceInfoButton;
	protected JToggleButton deviceMonitoringButton;
	protected JToggleButton panelMonitoringButton;
	private JPanel panel6;
	private JLabel label4;
	protected JTextField currentTimeField;
	private JLabel label1;
	protected JTextField loopTimeField;
	private JSplitPane splitPane1;
	private JScrollPane scrollPane2;
	protected JPanel workSpace;
	private JScrollPane scrollPane1;
	protected JTextPane textPane1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

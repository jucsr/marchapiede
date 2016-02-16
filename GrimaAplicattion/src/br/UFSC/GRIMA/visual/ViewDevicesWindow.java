/*
 * Created by JFormDesigner on Wed Jan 27 14:22:55 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class ViewDevicesWindow extends JFrame {
	public ViewDevicesWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel2 = new JPanel();
		addDeviceButton = new JButton();
		addCameraButton = new JButton();
		button3 = new JButton();
		button4 = new JButton();
		splitPane1 = new JSplitPane();
		scrollPane1 = new JScrollPane();
		devicesPanel = new JPanel();
		scrollPane2 = new JScrollPane();
		cameraPanel = new JPanel();

		//======== this ========
		setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconeLogo.png")).getImage());
		setTitle("Configure Devices");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

			//======== panel2 ========
			{
				panel2.setLayout(new GridBagLayout());
				((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
				((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//---- addDeviceButton ----
				addDeviceButton.setText("Add Agent");
				addDeviceButton.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/machineIcon.png")));
				panel2.add(addDeviceButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- addCameraButton ----
				addCameraButton.setText("Add Camera");
				addCameraButton.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/cameraIcon.png")));
				panel2.add(addCameraButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- button3 ----
				button3.setText("Configure Database");
				button3.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/downloadIcon.png")));
				panel2.add(button3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- button4 ----
				button4.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconInterrogacao.png")));
				panel2.add(button4, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== splitPane1 ========
			{
				splitPane1.setResizeWeight(0.5);
				splitPane1.setOneTouchExpandable(true);

				//======== scrollPane1 ========
				{

					//======== devicesPanel ========
					{
						devicesPanel.setBorder(new TitledBorder("Devices"));
						devicesPanel.setLayout(new GridBagLayout());
						((GridBagLayout)devicesPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)devicesPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)devicesPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0E-4};
						((GridBagLayout)devicesPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
					}
					scrollPane1.setViewportView(devicesPanel);
				}
				splitPane1.setLeftComponent(scrollPane1);

				//======== scrollPane2 ========
				{

					//======== cameraPanel ========
					{
						cameraPanel.setBorder(new TitledBorder("Cameras"));
						cameraPanel.setLayout(new GridBagLayout());
						((GridBagLayout)cameraPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)cameraPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)cameraPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 0.0, 1.0E-4};
						((GridBagLayout)cameraPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
					}
					scrollPane2.setViewportView(cameraPanel);
				}
				splitPane1.setRightComponent(scrollPane2);
			}
			panel1.add(splitPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		setSize(690, 425);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel2;
	protected JButton addDeviceButton;
	protected JButton addCameraButton;
	private JButton button3;
	private JButton button4;
	private JSplitPane splitPane1;
	private JScrollPane scrollPane1;
	protected JPanel devicesPanel;
	private JScrollPane scrollPane2;
	protected JPanel cameraPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

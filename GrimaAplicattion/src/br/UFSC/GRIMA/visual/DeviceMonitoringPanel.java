/*
 * Created by JFormDesigner on Wed Jan 27 15:34:52 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;

/**
 * @author Jc
 */
public class DeviceMonitoringPanel extends JPanel {
	public DeviceMonitoringPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel2 = new JPanel();
		label1 = new JLabel();
		deviceComboBox = new JComboBox<>();
		label2 = new JLabel();
		cameraComboBox = new JComboBox<>();
		playPause = new JToggleButton();
		button1 = new JButton();
		panel3 = new JPanel();
		label3 = new JLabel();
		uuidField = new JTextField();
		label4 = new JLabel();
		agentField = new JTextField();
		splitPane1 = new JSplitPane();
		scrollPane2 = new JScrollPane();
		buttonsPanel = new JPanel();
		splitPane2 = new JSplitPane();
		scrollPane1 = new JScrollPane();
		informationPanel = new JPanel();
		scrollPane3 = new JScrollPane();
		cameraPanel = new JPanel();

		//======== this ========
		setLayout(new GridBagLayout());
		((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//======== panel2 ========
			{
				panel2.setLayout(new GridBagLayout());
				((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
				((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//---- label1 ----
				label1.setText("Device:");
				panel2.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- deviceComboBox ----
				deviceComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
					" "
				}));
				panel2.add(deviceComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- label2 ----
				label2.setText("Camera:");
				panel2.add(label2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cameraComboBox ----
				cameraComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
					" "
				}));
				panel2.add(cameraComboBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
				panel2.add(playPause, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- button1 ----
				button1.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/Gears-icon.png")));
				panel2.add(button1, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== panel3 ========
			{
				panel3.setLayout(new GridBagLayout());
				((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
				((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.5, 1.0, 0.5, 1.0, 1.0E-4};
				((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//---- label3 ----
				label3.setText("UUID:");
				label3.setHorizontalAlignment(SwingConstants.RIGHT);
				panel3.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel3.add(uuidField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label4 ----
				label4.setText("Agent:");
				label4.setHorizontalAlignment(SwingConstants.RIGHT);
				panel3.add(label4, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel3.add(agentField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));
			}
			panel1.add(panel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//======== splitPane1 ========
		{
			splitPane1.setResizeWeight(0.25);
			splitPane1.setOneTouchExpandable(true);

			//======== scrollPane2 ========
			{

				//======== buttonsPanel ========
				{
					buttonsPanel.setLayout(new GridBagLayout());
					((GridBagLayout)buttonsPanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)buttonsPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
					((GridBagLayout)buttonsPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)buttonsPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
				}
				scrollPane2.setViewportView(buttonsPanel);
			}
			splitPane1.setLeftComponent(scrollPane2);

			//======== splitPane2 ========
			{
				splitPane2.setResizeWeight(0.5);

				//======== scrollPane1 ========
				{

					//======== informationPanel ========
					{
						informationPanel.setLayout(new GridBagLayout());
						((GridBagLayout)informationPanel.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)informationPanel.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)informationPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)informationPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
					}
					scrollPane1.setViewportView(informationPanel);
				}
				splitPane2.setLeftComponent(scrollPane1);

				//======== scrollPane3 ========
				{

					//======== cameraPanel ========
					{
						cameraPanel.setLayout(new GridBagLayout());
						((GridBagLayout)cameraPanel.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)cameraPanel.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)cameraPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)cameraPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
					}
					scrollPane3.setViewportView(cameraPanel);
				}
				splitPane2.setRightComponent(scrollPane3);
			}
			splitPane1.setRightComponent(splitPane2);
		}
		add(splitPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel2;
	private JLabel label1;
	public JComboBox<String> deviceComboBox;
	private JLabel label2;
	protected JComboBox<String> cameraComboBox;
	public JToggleButton playPause;
	protected JButton button1;
	private JPanel panel3;
	private JLabel label3;
	protected JTextField uuidField;
	private JLabel label4;
	protected JTextField agentField;
	private JSplitPane splitPane1;
	private JScrollPane scrollPane2;
	protected JPanel buttonsPanel;
	private JSplitPane splitPane2;
	private JScrollPane scrollPane1;
	protected JPanel informationPanel;
	private JScrollPane scrollPane3;
	protected JPanel cameraPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

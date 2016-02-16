/*
 * Created by JFormDesigner on Sun Jan 17 12:36:37 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class ViewDevicesPanel extends JPanel {
	public ViewDevicesPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		viewDevicesScrollPael = new JScrollPane();
		devicesPanel = new JPanel();

		//======== this ========
		setBorder(new TitledBorder("Devices"));
		setLayout(new GridBagLayout());
		((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

		//======== viewDevicesScrollPael ========
		{

			//======== devicesPanel ========
			{
				devicesPanel.setLayout(new GridBagLayout());
				((GridBagLayout)devicesPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)devicesPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)devicesPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)devicesPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
			}
			viewDevicesScrollPael.setViewportView(devicesPanel);
		}
		add(viewDevicesScrollPael, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane viewDevicesScrollPael;
	protected JPanel devicesPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

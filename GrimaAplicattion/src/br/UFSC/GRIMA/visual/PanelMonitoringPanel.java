/*
 * Created by JFormDesigner on Fri Jan 22 12:55:42 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class PanelMonitoringPanel extends JPanel {
	public PanelMonitoringPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		splitPane1 = new JSplitPane();
		scrollPane2 = new JScrollPane();
		buttonPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		panelSupport = new JPanel();

		//======== this ========
		setPreferredSize(new Dimension(800, 300));
		setLayout(new GridBagLayout());
		((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

		//======== splitPane1 ========
		{
			splitPane1.setResizeWeight(0.1);
			splitPane1.setOneTouchExpandable(true);

			//======== scrollPane2 ========
			{

				//======== buttonPanel ========
				{
					buttonPanel.setLayout(new GridBagLayout());
					((GridBagLayout)buttonPanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)buttonPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)buttonPanel.getLayout()).columnWeights = new double[] {0.5, 1.0E-4};
					((GridBagLayout)buttonPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
				}
				scrollPane2.setViewportView(buttonPanel);
			}
			splitPane1.setLeftComponent(scrollPane2);

			//======== scrollPane1 ========
			{

				//======== panelSupport ========
				{
					panelSupport.setLayout(new GridBagLayout());
					((GridBagLayout)panelSupport.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)panelSupport.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panelSupport.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
					((GridBagLayout)panelSupport.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
				}
				scrollPane1.setViewportView(panelSupport);
			}
			splitPane1.setRightComponent(scrollPane1);
		}
		add(splitPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JSplitPane splitPane1;
	private JScrollPane scrollPane2;
	public JPanel buttonPanel;
	private JScrollPane scrollPane1;
	public JPanel panelSupport;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

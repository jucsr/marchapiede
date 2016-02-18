/*
 * Created by JFormDesigner on Thu Jan 14 17:38:54 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class WaitWindow extends JDialog {
	public WaitWindow(Frame owner) {
		super(owner);
		initComponents();
	}

	public WaitWindow(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel2 = new JPanel();
		panel1 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		textProgress = new JLabel();
		panel3 = new JPanel();
		progressBar = new JProgressBar();

		//======== this ========
		setTitle("Please Wait");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {15, 305, 10, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 10, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//======== panel2 ========
		{
			panel2.setBorder(new EtchedBorder());
			panel2.setLayout(new GridBagLayout());
			((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 120, 0, 0};
			((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {110, 0, 0, 0};
			((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
			((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};

			//======== panel1 ========
			{
				panel1.setLayout(new GridBagLayout());
				((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {15, 0, 10, 0};
				((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

				//---- label1 ----
				label1.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/waitIcon.png")));
				panel1.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label2 ----
				label2.setText("Please Wait");
				panel1.add(label2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));
			}
			panel2.add(panel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- textProgress ----
			textProgress.setText(" ");
			textProgress.setVerticalAlignment(SwingConstants.TOP);
			panel2.add(textProgress, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		}
		contentPane.add(panel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//======== panel3 ========
		{
			panel3.setLayout(new GridBagLayout());
			((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {10, 0};
			((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- progressBar ----
			progressBar.setIndeterminate(true);
			panel3.add(progressBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel2;
	private JPanel panel1;
	private JLabel label1;
	private JLabel label2;
	protected JLabel textProgress;
	private JPanel panel3;
	protected JProgressBar progressBar;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

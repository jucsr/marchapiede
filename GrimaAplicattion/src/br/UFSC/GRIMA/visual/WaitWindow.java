/*
 * Created by JFormDesigner on Thu Jan 14 17:38:54 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;

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
		label1 = new JLabel();
		label2 = new JLabel();
		textProgress = new JLabel();
		progressBar = new JProgressBar();

		//======== this ========
		setTitle("Please Wait");
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());

		//======== panel2 ========
		{
			panel2.setLayout(new GridBagLayout());
			((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 115, 0};
			((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {110, 70, 0};
			((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
			((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

			//---- label1 ----
			label1.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/waitIcon.png")));
			panel2.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label2 ----
			label2.setText("Please Wait");
			panel2.add(label2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- textProgress ----
			textProgress.setText(" ");
			textProgress.setVerticalAlignment(SwingConstants.TOP);
			panel2.add(textProgress, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel2);
		contentPane.add(progressBar);
		setSize(285, 255);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel2;
	private JLabel label1;
	private JLabel label2;
	protected JLabel textProgress;
	protected JProgressBar progressBar;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

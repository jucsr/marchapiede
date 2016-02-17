/*
 * Created by JFormDesigner on Wed Feb 17 19:11:20 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class AboutWindow extends JDialog {
	public AboutWindow(Frame owner) {
		super(owner);
		initComponents();
	}

	public AboutWindow(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel3 = new JPanel();
		panel4 = new JPanel();
		label4 = new JLabel();
		label5 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label6 = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();

		//======== this ========
		setTitle("About");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

		//======== panel3 ========
		{
			panel3.setLayout(new GridBagLayout());
			((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

			//======== panel4 ========
			{
				panel4.setBorder(new BevelBorder(BevelBorder.LOWERED));
				panel4.setBackground(Color.white);
				panel4.setLayout(new GridBagLayout());
				((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
				((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

				//---- label4 ----
				label4.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/logofinal.png")));
				panel4.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label5 ----
				label5.setText("Developer: M\u00e1rcio Sumariva Nandi - Undergraduate in Control and Automation Engineering.");
				label5.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				panel4.add(label5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label2 ----
				label2.setText("Developer: Igor Berinc\u00e1 - Undergraduate in Control and Automation Engineering");
				label2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				panel4.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label3 ----
				label3.setText("Developer: Julio Benavente - PHD in Mechanic Engineering");
				label3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				panel4.add(label3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label6 ----
				label6.setText("Supervisor: Jo\u00e3o Carlos Espindola Ferreira - PHD in Mechanic Engineering");
				label6.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				panel4.add(label6, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(0, 0, 5, 0), 0, 0));
			}
			panel3.add(panel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		}
		contentPane.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

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
		contentPane.add(buttonBar, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		setSize(555, 300);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel3;
	private JPanel panel4;
	private JLabel label4;
	private JLabel label5;
	private JLabel label2;
	private JLabel label3;
	private JLabel label6;
	private JPanel buttonBar;
	public JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

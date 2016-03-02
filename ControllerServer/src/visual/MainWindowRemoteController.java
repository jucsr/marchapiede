package visual;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.layout.*;

public class MainWindowRemoteController extends JFrame {
	public MainWindowRemoteController() {
		initComponents();
	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel4 = new JPanel();
		panel2 = new JPanel();
		label1 = new JLabel();
		panel3 = new JPanel();
		label2 = new JLabel();
		commPortComboBox = new JComboBox();
		refreshButton = new JButton();
		label3 = new JLabel();
		baudComboBox = new JComboBox<>();
		openCloseButton = new JButton();
		label4 = new JLabel();
		firmWareTextField = new JTextField();
		sendButton = new JButton();
		scrollPane1 = new JScrollPane();
		textPane1 = new JTextPane();

		//======== this ========
		setIconImage(new ImageIcon(getClass().getResource("/images/serverController.png")).getImage());
		setTitle("Server Controller");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout());

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {10, 0, 10, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {10, 0, 0, 10, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

			//======== panel4 ========
			{
				panel4.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
				panel4.setLayout(new GridBagLayout());
				((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {15, 355, 10, 0};
				((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 105, 5, 0};
				((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
				((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

				//======== panel2 ========
				{
					panel2.setLayout(new GridBagLayout());
					((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

					//---- label1 ----
					label1.setText("Connection");
					label1.setFont(new Font("Gabriola", Font.BOLD, 20));
					label1.setAlignmentX(50.0F);
					panel2.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				}
				panel4.add(panel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//======== panel3 ========
				{
					panel3.setBorder(new EtchedBorder());
					panel3.setLayout(new GridBagLayout());
					((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {5, 0, 4, 0, 4, 0, 5, 0};
					((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
					((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

					//---- label2 ----
					label2.setText("PORT");
					label2.setFont(new Font("Constantia", Font.BOLD, 12));
					panel3.add(label2, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));

					//---- commPortComboBox ----
					commPortComboBox.setFont(new Font("Constantia", Font.BOLD, 12));
					panel3.add(commPortComboBox, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));

					//---- refreshButton ----
					refreshButton.setText("REFRESH");
					refreshButton.setFont(new Font("Constantia", Font.BOLD, 12));
					panel3.add(refreshButton, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));

					//---- label3 ----
					label3.setText("BAUD");
					label3.setFont(new Font("Constantia", Font.BOLD, 12));
					panel3.add(label3, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));

					//---- baudComboBox ----
					baudComboBox.setToolTipText("9600");
					baudComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
						"9600",
						"19200",
						"38400",
						"57600",
						"115200"
					}));
					baudComboBox.setFont(new Font("Constantia", Font.BOLD, 12));
					panel3.add(baudComboBox, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));

					//---- openCloseButton ----
					openCloseButton.setText("open");
					openCloseButton.setFont(new Font("Constantia", Font.BOLD, 12));
					panel3.add(openCloseButton, new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));

					//---- label4 ----
					label4.setText("FirmWare");
					label4.setFont(new Font("Constantia", Font.BOLD, 12));
					label4.setVisible(false);
					panel3.add(label4, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));

					//---- firmWareTextField ----
					firmWareTextField.setText("GRBL");
					firmWareTextField.setFont(new Font("Constantia", Font.BOLD, 12));
					firmWareTextField.setVisible(false);
					panel3.add(firmWareTextField, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				panel4.add(panel3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- sendButton ----
				sendButton.setText("Send");
				sendButton.setFont(new Font("Constantia", Font.BOLD, 12));
				panel4.add(sendButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//======== scrollPane1 ========
				{

					//---- textPane1 ----
					textPane1.setFont(new Font("Geometr212 BkCn BT", Font.PLAIN, 12));
					scrollPane1.setViewportView(textPane1);
				}
				panel4.add(scrollPane1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			}
			panel1.add(panel4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel1);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel4;
	private JPanel panel2;
	private JLabel label1;
	private JPanel panel3;
	private JLabel label2;
	public JComboBox commPortComboBox;
	public JButton refreshButton;
	private JLabel label3;
	protected JComboBox<String> baudComboBox;
	public JButton openCloseButton;
	private JLabel label4;
	public JTextField firmWareTextField;
	public JButton sendButton;
	private JScrollPane scrollPane1;
	public JTextPane textPane1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

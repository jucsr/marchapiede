package visual;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;

public class MainWindowRemoteController extends JFrame {
	public MainWindowRemoteController() {
		initComponents();
	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		label1 = new JLabel();
		panel3 = new JPanel();
		label2 = new JLabel();
		commPortComboBox = new JComboBox();
		refreshButton = new JButton();
		label3 = new JLabel();
		baudComboBox = new JComboBox<>();
		openCloseButton = new JButton();
		panel4 = new JPanel();
		label4 = new JLabel();
		firmWareTextField = new JTextField();
		sendButton = new JButton();
		panel2 = new JPanel();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout());

		//======== panel1 ========
		{
			panel1.setLayout(new GridLayout(5, 5));

			//---- label1 ----
			label1.setText("Connection");
			label1.setFont(new Font("Tahoma", Font.BOLD, 14));
			label1.setAlignmentX(50.0F);
			panel1.add(label1);

			//======== panel3 ========
			{
				panel3.setLayout(new FormLayout(
					"default, $lcgap, default:grow, 3*($lcgap, default)",
					"3*(default, $lgap), default"));

				//---- label2 ----
				label2.setText("PORT");
				panel3.add(label2, cc.xywh(1, 1, 2, 1));
				panel3.add(commPortComboBox, cc.xy(3, 1));

				//---- refreshButton ----
				refreshButton.setText("REFRESH");
				panel3.add(refreshButton, cc.xy(5, 1));

				//---- label3 ----
				label3.setText("BAUD");
				panel3.add(label3, cc.xy(1, 3));

				//---- baudComboBox ----
				baudComboBox.setToolTipText("9600");
				baudComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
					"9600"
				}));
				panel3.add(baudComboBox, cc.xy(3, 3));

				//---- openCloseButton ----
				openCloseButton.setText("open");
				panel3.add(openCloseButton, cc.xy(5, 3));
			}
			panel1.add(panel3);

			//======== panel4 ========
			{
				panel4.setLayout(new FormLayout(
					"default, 2*($lcgap, default:grow)",
					"default, $lgap, default"));

				//---- label4 ----
				label4.setText("FirmWare");
				panel4.add(label4, cc.xy(1, 1));

				//---- firmWareTextField ----
				firmWareTextField.setText("GRBL");
				panel4.add(firmWareTextField, cc.xy(3, 1));
			}
			panel1.add(panel4);

			//---- sendButton ----
			sendButton.setText("ENVIAR!");
			panel1.add(sendButton);
		}
		contentPane.add(panel1);

		//======== panel2 ========
		{
			panel2.setLayout(new GridLayout());
		}
		contentPane.add(panel2);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JLabel label1;
	private JPanel panel3;
	private JLabel label2;
	public JComboBox commPortComboBox;
	public JButton refreshButton;
	private JLabel label3;
	protected JComboBox<String> baudComboBox;
	public JButton openCloseButton;
	private JPanel panel4;
	private JLabel label4;
	public JTextField firmWareTextField;
	public JButton sendButton;
	private JPanel panel2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

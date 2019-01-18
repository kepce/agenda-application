import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditTaskFrame extends JFrame{

	private JLabel prevTaskName;
	private JTextField newTaskName = new JTextField();
	private JLabel prevStatus;
	private JTextField newStatus = new JTextField();
	private JLabel prevDueDate;
	private JTextField newDueDate = new JTextField();
	private JPanel topPanel = new JPanel();

	
	public EditTaskFrame(String title, MainAppFrame mainAppFrame) {
		super(title);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		/////
		/////
		/////
		/////
	}
}

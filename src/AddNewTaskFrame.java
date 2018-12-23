import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AddNewTaskFrame extends JFrame{

	private JButton addButton = new JButton("Add New Task");
	private JLabel taskNameLabel = new JLabel("Task Name: ");
	private JTextField taskName = new JTextField();
	private JLabel taskDescriptionLabel = new JLabel("Task Description: ");
	private JTextArea taskDescription = new JTextArea(4,25);
	private JLabel dueDateLabel = new JLabel("Due Date: ");
	private JTextField dueDateDay = new JTextField();
	private JTextField dueDateMonth = new JTextField();
	private JTextField dueDateYear = new JTextField();
	
	private JPanel topPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel innerPanel1 = new JPanel();
	private JPanel innerPanel2 = new JPanel();
	private JPanel innerPanel3 = new JPanel();
	
	public AddNewTaskFrame(String title) {
		super(title);
		
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		taskName.setColumns(25);
		innerPanel1.add(taskNameLabel);
		innerPanel1.add(taskName);
		topPanel.add(innerPanel1);
		
		taskDescription.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		innerPanel2.add(taskDescriptionLabel);
		innerPanel2.add(taskDescription);
		topPanel.add(innerPanel2);
		
		dueDateDay.setColumns(2);
		dueDateMonth.setColumns(2);
		dueDateYear.setColumns(4);
		innerPanel3.add(dueDateLabel);
		innerPanel3.add(dueDateDay);
		innerPanel3.add(dueDateMonth);
		innerPanel3.add(dueDateYear);
		topPanel.add(innerPanel3);
		
		addButton.addActionListener(e->{
			
			// add new task function <---- continue from here
			
		});
		
		bottomPanel.add(addButton);
		topPanel.add(bottomPanel);
		
		add(topPanel);

		
		setVisible(true);
		setResizable(false);
		setSize(330,280);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AddNewTaskFrame2 extends JFrame{

	private JButton addButton = new JButton("Add New Task");
	private JLabel taskNameLabel = new JLabel("Task Name: ");
	private JTextField taskName = new JTextField();
	private JLabel statusLabel = new JLabel("Status: ");
	private JCheckBox isCompletedCheckBox = new JCheckBox("Completed");

	private JLabel dueDateLabel = new JLabel("Due Date: ");
	private JTextField dueDateDay = new JTextField();
	private JTextField dueDateMonth = new JTextField();
	private JTextField dueDateYear = new JTextField();
	
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel topPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel innerPanel1 = new JPanel();
	private JPanel innerPanel2 = new JPanel();
	private JPanel innerPanel3 = new JPanel();
	private JPanel innerPanel4 = new JPanel();

	private JLabel repeatLabel = new JLabel("Repeat on:");
	
	private JCheckBox[] daysOfTheWeek = {
			new JCheckBox("Mo"),
			new JCheckBox("Tu"),
			new JCheckBox("We"),
			new JCheckBox("Th"),
			new JCheckBox("Fr"),
			new JCheckBox("Sa"),
			new JCheckBox("Su")
	};

	
	
	public AddNewTaskFrame2(String title, MainAppFrame mainAppFrame) {
		super(title);
	
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		taskName.setColumns(25);
		innerPanel1.add(taskNameLabel);
		innerPanel1.add(taskName);
		topPanel.add(innerPanel1);
		
		innerPanel2.add(statusLabel);
		innerPanel2.add(isCompletedCheckBox);
		topPanel.add(innerPanel2);
		
		innerPanel4.add(repeatLabel);
		for(JCheckBox cb : daysOfTheWeek)
			innerPanel4.add(cb);
		topPanel.add(innerPanel4);
		
		dueDateDay.setColumns(2);
		dueDateMonth.setColumns(2);
		dueDateYear.setColumns(4);
		innerPanel3.add(dueDateLabel);
		innerPanel3.add(dueDateDay);
		innerPanel3.add(dueDateMonth);
		innerPanel3.add(dueDateYear);
		topPanel.add(innerPanel3);
		
		addButton.addActionListener(e->{
			String formattedDate = String.format("%s/%s/%s", dueDateDay.getText(), dueDateMonth.getText(), dueDateYear.getText());
			mainAppFrame.getTaskTable().addNewTask(taskName.getText(), getTaskStatus(), formattedDate);
			mainAppFrame.getTaskTable().getDataBaseConnection().update(ScriptSQL.insertRow(mainAppFrame.getYear(), mainAppFrame.getDayOfYear(), taskName.getText(), getTaskStatus(), formattedDate));
			this.dispose();
		});
		
		bottomPanel.add(addButton);
		topPanel.add(bottomPanel);
		
		this.add(topPanel);

		this.setVisible(true);
		this.setResizable(false);
		this.setSize(330, 280);
		this.setLocation((dim.width - this.getHeight())/2, (dim.height - this.getHeight())/2);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private String getTaskStatus() {
		if(isCompletedCheckBox.isSelected())
			return "Completed";
		else
			return "Not Completed";
	}
}

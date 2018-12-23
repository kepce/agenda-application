import java.awt.BorderLayout;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;

public class MainAppFrame extends JFrame{

	private JButton leftButton = new JButton(" < ");
	private JButton rightButton = new JButton(" > ");
	private JButton todayButton = new JButton("Today");
	public TaskTable taskTable = new TaskTable();
	private JComboBox comboBox;
	private JButton okButton = new JButton(" OK ");
	private Calendar calendar = Calendar.getInstance();
	private SimpleCalendar cal = new SimpleCalendar();
	private JLabel dateLabel = new JLabel(""+calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
	
	private int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	
	public MainAppFrame(String title) {
		super(title);
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		rightButton.addActionListener(e->{
			dayOfYear++;
			if(dayOfYear >= 366) {
				dayOfYear = 1;
				year++;
				calendar.set(Calendar.YEAR, year);
			}
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
			dateLabel.setText(""+calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
			taskTable.updateTable(year, dayOfYear);
		});
		
		leftButton.addActionListener(e->{
			dayOfYear--;
			if(dayOfYear <= 0) {
				dayOfYear = 365;
				year--;
				calendar.set(Calendar.YEAR, year);
			}
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
			dateLabel.setText(""+calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
			taskTable.updateTable(year, dayOfYear);
		});
		
		topPanel.add(leftButton);
		topPanel.add(dateLabel);
		topPanel.add(rightButton);
		
		String tableOptions[] = {"Add New Task",
									"Task Completed",
									"Edit Task",
									"Delete Task"};
		
		comboBox = new JComboBox(tableOptions);
		
		okButton.addActionListener(e->{
			String selectedItem = comboBox.getItemAt(comboBox.getSelectedIndex()).toString();
			if(selectedItem.equals("Add New Task")) {
				AddNewTaskFrame newTaskFrame = new AddNewTaskFrame("Add a New Task");
			}else if(selectedItem.equals("Task Completed")) {
				
			}else if(selectedItem.equals("Edit Task")) {
				
			}else if(selectedItem.equals("Delete Task")) {
				
			}
		});
		
		bottomPanel.add(comboBox);
		bottomPanel.add(okButton);
		
		add(topPanel,BorderLayout.PAGE_START);

		add(bottomPanel, BorderLayout.PAGE_END);
		add(taskTable, BorderLayout.CENTER);
		
		setVisible(true);
		setSize(600, 450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
}

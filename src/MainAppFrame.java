import java.awt.BorderLayout;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

public class MainAppFrame extends JFrame{

	private JButton leftButton = new JButton(" < ");
	private JButton rightButton = new JButton(" > ");
	private JButton todayButton = new JButton("Today"); // add later to the main window to jump today's date
	public TaskTable taskTable = new TaskTable();
	private JComboBox comboBox;
	private JButton okButton = new JButton(" OK ");
	private Calendar calendar = Calendar.getInstance();
	private JLabel dateLabel = new JLabel(""+calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
	
	public int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR); // add get() method for integers
	public int year = Calendar.getInstance().get(Calendar.YEAR);
	
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
		
		String tableActionOptions[] = {"Add New Task",
									   "Task Completed",
									   "Edit Task",
									   "Delete Task"};
		
		comboBox = new JComboBox(tableActionOptions);
		
		okButton.addActionListener(e->{
			String selectedItem = comboBox.getItemAt(comboBox.getSelectedIndex()).toString();
			if(selectedItem.equals("Add New Task")) {
				AddNewTaskFrame newTaskFrame = new AddNewTaskFrame("Add a New Task", this);
			}else if(selectedItem.equals("Task Completed")) {
				taskTable.taskDB.update(ScriptSQL.updateStatus(year, dayOfYear, taskTable.table.getModel().getValueAt(taskTable.getSelectedRow(), 0).toString(), true));
				taskTable.updateTable(year, dayOfYear);
			}else if(selectedItem.equals("Edit Task")) {
				
			}else if(selectedItem.equals("Delete Task")) {
				taskTable.taskDB.update(ScriptSQL.deleteRow(year, dayOfYear, taskTable.table.getModel().getValueAt(taskTable.getSelectedRow(), 0).toString()));
				taskTable.updateTable(year, dayOfYear);
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

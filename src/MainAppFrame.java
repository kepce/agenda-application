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
	private JButton todayButton = new JButton("Today"); // add later to the main window to jump today's date - now has no function
	private JComboBox comboBox;
	private JButton okButton = new JButton(" OK ");
	private Calendar calendar = Calendar.getInstance();
	private JLabel dateLabel = new JLabel(""+calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	private int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR); 
	private int year = Calendar.getInstance().get(Calendar.YEAR); 
	private TaskTable taskTable = new TaskTable();
	
	
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
				try {
					String selectedTaskName = taskTable.getTable().getModel().getValueAt(taskTable.getSelectedRow(), 0).toString();
					taskTable.getDataBaseConnection().update(ScriptSQL.updateStatus(year, dayOfYear, selectedTaskName, true));
					taskTable.updateTable(year, dayOfYear);
				}catch(Exception ex) {
					showNotSelectedWarning(ex);
				}
			}else if(selectedItem.equals("Edit Task")) {
				//taskTable.taskDB.update(ScriptSQL.updateAll(year, dayOfYear, selectedTaskName, newTaskName, isCompleted, newDueDate));
			}else if(selectedItem.equals("Delete Task")) {
				try {
					String selectedTaskName = taskTable.getTable().getModel().getValueAt(taskTable.getSelectedRow(), 0).toString();
					DeleteTaskFrame deleteTaskFrame = new DeleteTaskFrame("Warning!", selectedTaskName, this);
				}catch(Exception ex) {
					showNotSelectedWarning(ex);
				}
			}
		});
		
		bottomPanel.add(comboBox);
		bottomPanel.add(okButton);
		
		this.add(topPanel,BorderLayout.PAGE_START);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.add(taskTable, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setSize(600, 450);
		this.setResizable(false);
		this.setLocation((dim.width - this.getHeight())/2, (dim.height - this.getHeight())/2);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	private void showNotSelectedWarning(Exception ex) {
		JOptionPane.showMessageDialog(this, "You have not selected any task.", "Warning!", JOptionPane.WARNING_MESSAGE);
		ex.printStackTrace();
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getDayOfYear() {
		return this.dayOfYear;
	}
	
	public TaskTable getTaskTable() {
		return this.taskTable;
	}
}

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

public class MainAppFrame extends JFrame{

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd / MM / uuuu");
	private static final DateTimeFormatter TABLE_NAME_FORMAT = DateTimeFormatter.ofPattern("uuuu_DDD");
	
	private JButton leftButton = new JButton(" < ");
	private JButton rightButton = new JButton(" > ");
	private JComboBox comboBox;
	private JButton okButton = new JButton(" OK ");
	private LocalDate date = LocalDate.now();
	private JLabel dateLabel = new JLabel(date.format(DATE_FORMAT));
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private TaskTable taskTable;
	
	
	public MainAppFrame(String title) {
		super(title);
		
		taskTable = new TaskTable(this);

		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		rightButton.addActionListener(e->{
			date = date.plusDays(1);
			dateLabel.setText(date.format(DATE_FORMAT));
			taskTable.updateTable(date);
		});
		
		leftButton.addActionListener(e->{
			date = date.minusDays(1);
			dateLabel.setText(date.format(DATE_FORMAT));
			taskTable.updateTable(date);
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
				this.setEnabled(false);
			}else if(selectedItem.equals("Task Completed")) {
				try {
					String selectedTaskName = taskTable.getTable().getModel().getValueAt(taskTable.getSelectedRow(), 0).toString();
					taskTable.getDataBaseConnection().update(ScriptSQL.updateStatus(getTableNameOf(date), selectedTaskName, true));
					taskTable.updateTable(date);
				}catch(Exception ex) {
					showNotSelectedWarning(ex);
				}
			}else if(selectedItem.equals("Edit Task")) {
				// <<<<<<
				// <<<<<< Edit Task Frame
				// <<<<<
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
	
	public TaskTable getTaskTable() {
		return this.taskTable;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public String getTableName() {
		return this.date.format(TABLE_NAME_FORMAT);
	}
	
	public String getTableNameOf(LocalDate date) {
		return date.format(TABLE_NAME_FORMAT);
	}
}


/*
 * No Edit Task Frame
 * 
 * task complete dediðinde mor olanlarý yesil yapmýyor
 * yapsan bile ileri geri oklarýný kullanýnca tekrar mora dönecek
 * 
 * renkleri kapa ac özelligi gelebilir
 * 
 * database icin login sayfasý gelebilir
 * 
 * tasklarla alakalý acýklama olabilir
 * 
 * kodu düzenle
 * database connectioni maine al - IMPROVEMENT
 * taskdatabase ismi degistir
 * 
 * her seyi tek tabloya koy
 * 
 * 
 * */





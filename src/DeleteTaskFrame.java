import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DeleteTaskFrame extends JFrame{

	private JLabel warningLabel;
	private JButton deleteButton = new JButton("Delete");
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");
	private JPanel bottomPanel;
	private EmptyBorder emptyBorder;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private DataBaseConnection dbc;
	
	public DeleteTaskFrame(String title, String selectedTaskName, MainAppFrame mainAppFrame) {
		super(title);
		
		dbc = DataBaseConnection.getInstance();
		this.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		
		String warningMessage = "Are you sure you want to delete the task: \"" + selectedTaskName + "\" ?";
		warningLabel = new JLabel(warningMessage);
		emptyBorder = new EmptyBorder(10,15,10,10);
		warningLabel.setBorder(emptyBorder);
		add(warningLabel, BorderLayout.CENTER);
		
		deleteButton.addActionListener(e->{
			String selectedTaskDate = mainAppFrame.getTaskTable().getTable().getModel().getValueAt(mainAppFrame.getTaskTable().getSelectedRow(), 2).toString();
			if(selectedTaskDate.equals("Daily Task")) {
				dbc.update(ScriptSQL.deleteRowFromRepTable(selectedTaskName));
			}else {
				dbc.update(ScriptSQL.deleteRow(selectedTaskName));
			}
			mainAppFrame.getTaskTable().updateTable(mainAppFrame.getDate());
			this.dispose();
		});
		
		cancelButton.addActionListener(e->{
			this.dispose();
		});
		
		bottomPanel.add(deleteButton);
		bottomPanel.add(cancelButton);
		add(bottomPanel, BorderLayout.PAGE_END);
		
		this.setSize(400, 120);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocation((dim.width - this.getHeight())/2, (dim.height - this.getHeight())/2);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

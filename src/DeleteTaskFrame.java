import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

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
	
	public DeleteTaskFrame(String title, String selectedTaskName, MainAppFrame mainAppFrame) {
		super(title);
		this.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		
		String warningMessage = "Are you sure you want to delete the task: \"" + selectedTaskName + "\" ?";
		warningLabel = new JLabel(warningMessage);
		emptyBorder = new EmptyBorder(10,15,10,10);
		warningLabel.setBorder(emptyBorder);
		add(warningLabel, BorderLayout.CENTER);
		
		deleteButton.addActionListener(e->{
			mainAppFrame.taskTable.taskDB.update(ScriptSQL.deleteRow(mainAppFrame.year, mainAppFrame.dayOfYear, mainAppFrame.taskTable.table.getModel().getValueAt(mainAppFrame.taskTable.getSelectedRow(), 0).toString()));
			mainAppFrame.taskTable.updateTable(mainAppFrame.year, mainAppFrame.dayOfYear);
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

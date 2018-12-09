import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;

public class MainAppFrame extends JFrame{

	private JButton leftButton = new JButton(" < ");
	private JButton rightButton = new JButton(" > ");
	public TaskTable taskTable = new TaskTable();
	private JComboBox comboBox = new JComboBox();
	private JButton okButton = new JButton(" OK ");
	private JLabel dateLabel = new JLabel("09.12.2018");
	
	public MainAppFrame(String title) {
		super(title);
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		topPanel.add(leftButton);
		topPanel.add(rightButton);

		bottomPanel.add(comboBox);
		bottomPanel.add(okButton);
		
		add(topPanel,BorderLayout.PAGE_START);

		add(bottomPanel, BorderLayout.PAGE_END);
		add(taskTable, BorderLayout.CENTER);
		
		setVisible(true);
		setSize(700, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
}

package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

import classes.Constants;

public class AddTaskGUI {

	private JFrame frmTaskAdder;
	private JTextField textFieldShownTasks;
	private JButton btnAddTask;
	public static boolean flag;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTaskGUI window = new AddTaskGUI();
					window.frmTaskAdder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AddTaskGUI() 
	{
		initialize();
	}

	private void initialize() 
	{
		frmTaskAdder = new JFrame();
		frmTaskAdder.setTitle("TASK ADDER\n");
		frmTaskAdder.setResizable(false);
		frmTaskAdder.setBounds(100, 100, 426, 205);
		frmTaskAdder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTaskAdder.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(6, 6, 414, 166);
		frmTaskAdder.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblWriteTask = new JLabel("DESCRIPTION");
		lblWriteTask.setBounds(166, 10, 109, 16);
		panel.add(lblWriteTask);
		
		JLabel lblshowDate = new JLabel("Date:");
		lblshowDate.setBounds(20, 74, 61, 16);
		panel.add(lblshowDate);
		
		JLabel lblcheckImportant = new JLabel("Important:");
		lblcheckImportant.setBounds(20, 111, 68, 16);
		panel.add(lblcheckImportant);
		
		JLabel lblStatus = new JLabel("In process of creating a task");
		lblStatus.setToolTipText("A label will show the status of the process of adding a task");
		lblStatus.setForeground(Color.RED);
		lblStatus.setBounds(10, 139, 394, 16);
		panel.add(lblStatus);
		
		textFieldShownTasks = new JTextField();
		textFieldShownTasks.setToolTipText("Write here what the task is");
		textFieldShownTasks.setBounds(10, 30, 394, 27);
		panel.add(textFieldShownTasks);
		textFieldShownTasks.setColumns(10);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setToolTipText("Choose the date here");
		dateChooser.setBounds(131, 69, 124, 26);
		panel.add(dateChooser);
		
		JComboBox<String> comboBoxImportant = new JComboBox<String>();
		comboBoxImportant.setToolTipText("Choose whether the task is important or not that much");
		comboBoxImportant.setModel(new DefaultComboBoxModel<String>(new String[] {"NO", "YES"}));
		comboBoxImportant.setBounds(141, 107, 114, 27);
		panel.add(comboBoxImportant);
		
		btnAddTask = new JButton("ADD TASK");
		btnAddTask.setToolTipText("Click to add the newly created task\nNote: the date cannot be before the current date\n");
		btnAddTask.setForeground(Color.BLUE);
		btnAddTask.setBounds(267, 69, 136, 62);
		panel.add(btnAddTask);
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String description = textFieldShownTasks.getText();
				Date chosenDate = dateChooser.getDate();
				String strChosenDate = Constants.DateToString(chosenDate);
				
				Date reformedChosenDate = new Date();
				try {
					reformedChosenDate = new SimpleDateFormat(Constants.DateFormat).parse(strChosenDate);
				} catch (ParseException e1) {
					reformedChosenDate = new Date();
				}
				
				String strImportant = String.valueOf(comboBoxImportant.getSelectedItem());
				if (strImportant == "NO")
				{
					strImportant = "false";
				}
				else if (strImportant == "YES")
				{
					strImportant = "true";
				}
				
				boolean important = Boolean.parseBoolean(strImportant);
				boolean completed = false;
				
				if (reformedChosenDate == null)
				{
					lblStatus.setText("Please select date to create and add a task");
				}
				else if (reformedChosenDate.compareTo(MWindowGUI.reformedToday) >= 0)
				{
					MWindowGUI.mainlogic.factory.AddTask(description, reformedChosenDate, completed, important);
					MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
					lblStatus.setText("Task created successfully");
					
					frmTaskAdder.dispose();
				}
				else if(reformedChosenDate.compareTo(MWindowGUI.reformedToday) < 0)
				{
					lblStatus.setText("Error, please try again (chosen date is earlier than current one)");
				}
				MWindowGUI.window.refreshMW();
				MWindowGUI.lists.refreshTW();
			}
		});
		frmTaskAdder.setVisible(true);
	}
}

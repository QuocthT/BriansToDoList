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

public class EditTaskGUI {

	private JFrame frmTaskEditor;
	public JTextField textFieldShownTasks;
	public JDateChooser dateChooser;
	public JComboBox<String> comboBoxImportant;
	private JButton btnDone;
	public static boolean flag;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditTaskGUI window = new EditTaskGUI();
					window.frmTaskEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EditTaskGUI() 
	{
		initialize();
		
	}

	private void initialize() 
	{
		frmTaskEditor = new JFrame();
		frmTaskEditor.setTitle("TASK EDITOR");
		frmTaskEditor.setResizable(false);
		frmTaskEditor.setBounds(100, 100, 427, 207);
		frmTaskEditor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTaskEditor.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 414, 166);
		frmTaskEditor.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblWriteTask = new JLabel("REWRITE THE DESCRIPTION");
		lblWriteTask.setBounds(129, 10, 178, 16);
		panel.add(lblWriteTask);
		
		JLabel lblshowDate = new JLabel("Date:");
		lblshowDate.setBounds(20, 74, 61, 16);
		panel.add(lblshowDate);
		
		JLabel lblcheckImportant = new JLabel("Important:");
		lblcheckImportant.setBounds(20, 111, 68, 16);
		panel.add(lblcheckImportant);
		
		JLabel lblStatus = new JLabel("In process of creating a task");
		lblStatus.setToolTipText("A label will show the status of the process of editing a task");
		lblStatus.setForeground(Color.RED);
		lblStatus.setBounds(10, 139, 394, 16);
		panel.add(lblStatus);
		
		textFieldShownTasks = new JTextField();
		textFieldShownTasks.setToolTipText("Write here what the task is");
		textFieldShownTasks.setBounds(10, 30, 394, 27);
		panel.add(textFieldShownTasks);
		textFieldShownTasks.setColumns(10);
		
		dateChooser = new JDateChooser();
		dateChooser.setToolTipText("Choose the date here");
		dateChooser.setBounds(131, 69, 124, 26);
		panel.add(dateChooser);
		
		comboBoxImportant = new JComboBox<String>();
		comboBoxImportant.setToolTipText("Choose whether the task is important or not that much");
		comboBoxImportant.setModel(new DefaultComboBoxModel<String>(new String[] {"NO", "YES"}));
		comboBoxImportant.setBounds(141, 107, 114, 27);
		panel.add(comboBoxImportant);
		frmTaskEditor.setVisible(true);
		
		btnDone = new JButton("DONE");
		btnDone.setToolTipText("Click to perform changes to a chosen task\n");
		btnDone.setForeground(new Color(0, 128, 0));
		btnDone.setBounds(267, 69, 136, 62);
		panel.add(btnDone);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				String description = textFieldShownTasks.getText();
				Date chosenDate = dateChooser.getDate();
				String strChosenDate = Constants.DateToString(chosenDate);
				
				Date reformedChosenDate = new Date();
				try {
					reformedChosenDate = new SimpleDateFormat(Constants.DateFormat).parse(strChosenDate);
				} catch (ParseException e2) {
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
					lblStatus.setText("Please select date to correctly edit a task");
				}
				else
				{
					MWindowGUI.mainlogic.editedTask.setDescription(description);
					MWindowGUI.mainlogic.editedTask.setDueDate(reformedChosenDate);
					MWindowGUI.mainlogic.editedTask.setCompleted(completed);
					MWindowGUI.mainlogic.editedTask.setImportant(important);
					MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
					MWindowGUI.window.refreshMW();
					MWindowGUI.lists.refreshTW();
					frmTaskEditor.dispose();
				}
				
			}
		});
		frmTaskEditor.setVisible(true);
	}
}

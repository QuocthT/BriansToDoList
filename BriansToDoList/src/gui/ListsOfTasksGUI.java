package gui;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;

import classes.Constants;
import classes.Task;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import java.awt.GridLayout;

public class ListsOfTasksGUI {

	private JFrame frmListsOfTasks;
	JList<Task> listCompletedTasks;
	DefaultListModel<Task> completedTasksList;
	
	JList<Task> listActiveTasks;
	DefaultListModel<Task> activeTasksList;
	
	JList<Task> listImportantTasks;
	DefaultListModel<Task> importantTasksList;
	
	JList<Task> listOverdueTasks;
	DefaultListModel<Task> overdueTasksList;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListsOfTasksGUI window2 = new ListsOfTasksGUI();
					window2.frmListsOfTasks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ListsOfTasksGUI() 
	{
		initialize();
	}
	
	private void reorderTWLists(DefaultListModel<Task> modelTasksList )
	{
		int listSize = modelTasksList.getSize();
		Task[] listTasks = new Task[listSize];
		for(int i = 0; i < listSize;i++)
		{
			listTasks[i] = (Task)modelTasksList.getElementAt(i);
		}
		sortArrayTasks_2(listTasks);
		for (int i = 0; i < listSize;i++) 
		{
			modelTasksList.setElementAt(listTasks[i], i);
		}     
	}
	
	private void sortArrayTasks_2(Task[] list) 
	{
		Task tmp;
		if (list.length == 1) return;
		for (int i = 0; i < list.length; i++) 
		{
			for (int j = i + 1; j < list.length; j++) 
			{
				Date date1 = list[i].getDueDate();
				Date date2 = list[j].getDueDate();
				if(date1.compareTo(date2) > 0) 
				{
			        tmp = list[i];
			        list[i] = list[j];
			        list[j] = tmp;
		        }
		    }
		} 
	}
	
	public void refreshTW()
	{
		completedTasksList.clear();
		activeTasksList.clear();
		importantTasksList.clear();
		overdueTasksList.clear();
		
		for(int i = 0; i < MWindowGUI.mainlogic.factory.TaskCount();i++)
		{
			Date chosendate = MWindowGUI.mainlogic.factory.GetTaskTime(i);
			Boolean checkCompleted = MWindowGUI.mainlogic.factory.GetTaskCompleted(i);
			Boolean checkImportant = MWindowGUI.mainlogic.factory.GetTaskImportant(i);
			
			if (checkCompleted == true) 
			{
				completedTasksList.addElement(MWindowGUI.mainlogic.factory.GetTask(i));
			}
			
			if (checkCompleted == false && chosendate.compareTo(MWindowGUI.reformedToday) >= 0) 
			{
				activeTasksList.addElement(MWindowGUI.mainlogic.factory.GetTask(i));
			}
			
			if (checkImportant == true && checkCompleted == false) 
			{
				importantTasksList.addElement(MWindowGUI.mainlogic.factory.GetTask(i));
			}
			
			if (checkCompleted == false && chosendate.compareTo(MWindowGUI.reformedToday) < 0)
			{
				overdueTasksList.addElement(MWindowGUI.mainlogic.factory.GetTask(i));
			}
			reorderTWLists(activeTasksList);
			reorderTWLists(completedTasksList);
			reorderTWLists(importantTasksList);
			reorderTWLists(overdueTasksList);
		}
	}

	private void initialize() 
	{
		frmListsOfTasks = new JFrame();
		frmListsOfTasks.setTitle("LISTS OF TASKS");
		frmListsOfTasks.setBounds(100, 100, 670, 636);
		frmListsOfTasks.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmListsOfTasks.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		activeTasksList = new DefaultListModel<>();
		listActiveTasks = new JList<>(activeTasksList);
		listActiveTasks.setToolTipText("Left Click the task to edit or remove it");
		
		importantTasksList = new DefaultListModel<>();
		listImportantTasks = new JList<>(importantTasksList);
		listImportantTasks.setToolTipText("Left Click the task to edit or remove it");
		
		completedTasksList = new DefaultListModel<>();
		listCompletedTasks = new JList<>(completedTasksList);
		listCompletedTasks.setToolTipText("Left Click the task to edit or remove it");
		
		overdueTasksList = new DefaultListModel<>();
		listOverdueTasks = new JList<>(overdueTasksList);
		listOverdueTasks.setToolTipText("Left Click the task to edit or remove it");
		
		JSplitPane splitPaneAll_2 = new JSplitPane();
		splitPaneAll_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmListsOfTasks.getContentPane().add(splitPaneAll_2);
		splitPaneAll_2.setDividerLocation(295);
		
		JSplitPane splitPanelCurrent = new JSplitPane();
		splitPaneAll_2.setLeftComponent(splitPanelCurrent);
		splitPanelCurrent.setDividerLocation(335);
		
		JSplitPane splitPaneMissed = new JSplitPane();
		splitPaneAll_2.setRightComponent(splitPaneMissed);
		splitPaneMissed.setDividerLocation(335);
		
		JLabel lblLabelDD4 = new JLabel("Date and Description:");
		lblLabelDD4.setEnabled(false);
		
		JLabel lblLabelDD5 = new JLabel("Date and Description:");
		lblLabelDD5.setEnabled(false);
		
		JLabel lblLabelDD6 = new JLabel("Date and Description:");
		lblLabelDD6.setEnabled(false);
		
		JLabel lblLabelDD7 = new JLabel("Date and Description:");
		lblLabelDD7.setEnabled(false);
		
		JScrollPane scrollPaneActive = new JScrollPane(listActiveTasks);
		scrollPaneActive.setForeground(Color.GREEN);
		scrollPaneActive.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "ACTIVE", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(34, 139, 34)));
		splitPanelCurrent.setLeftComponent(scrollPaneActive);
		scrollPaneActive.setViewportView(listActiveTasks);
		scrollPaneActive.setColumnHeaderView(lblLabelDD4);
		
		JScrollPane scrollPaneImportant = new JScrollPane(listImportantTasks);
		scrollPaneImportant.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "IMPORTANT", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 0, 128)));
		splitPanelCurrent.setRightComponent(scrollPaneImportant);
		scrollPaneImportant.setViewportView(listImportantTasks);
		scrollPaneImportant.setColumnHeaderView(lblLabelDD5);
		
		JScrollPane scrollPaneCompleted = new JScrollPane(listCompletedTasks);
		scrollPaneCompleted.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "COMPLETED", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(65, 105, 225)));
		splitPaneMissed.setLeftComponent(scrollPaneCompleted);
		scrollPaneCompleted.setViewportView(listCompletedTasks);
		scrollPaneCompleted.setColumnHeaderView(lblLabelDD6);
		
		JScrollPane scrollPanelOverdue = new JScrollPane(listOverdueTasks);
		scrollPanelOverdue.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "OVERDUE", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		splitPaneMissed.setRightComponent(scrollPanelOverdue);
		scrollPanelOverdue.setViewportView(listOverdueTasks);
		scrollPanelOverdue.setColumnHeaderView(lblLabelDD7);
		
		JPopupMenu popupMenuActive = new JPopupMenu();
		popupMenuActive.setToolTipText("Mark as Completed");
		addPopup(listActiveTasks, popupMenuActive);
		
		JMenuItem mntmMACactive = new JMenuItem("Mark as Completed");
		mntmMACactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listActiveTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuActive.add(mntmMACactive);
		
		JMenuItem mntmMAIactive = new JMenuItem("Mark as Incompleted");
		mntmMAIactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listActiveTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuActive.add(mntmMAIactive);
		
		JMenuItem mntmMAIMactive = new JMenuItem("Mark as Important");
		mntmMAIMactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listActiveTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuActive.add(mntmMAIMactive);
		
		JMenuItem mntmMAUIMactive = new JMenuItem("Mark as Unimportant");
		mntmMAUIMactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listActiveTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
			}
		});
		popupMenuActive.add(mntmMAUIMactive);
		
		JMenuItem mntmRTactive = new JMenuItem("Remove task");
		mntmRTactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listActiveTasks);
				Task chosentask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				String strDate = Constants.DateToString(chosentask.getDueDate());
				String message = "Are you sure you want to delete the task \"" + chosentask.getDescription() + " " + strDate + "\"";
				if (JOptionPane.showConfirmDialog(frmListsOfTasks, 
			            message, "Remove Task?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
				MWindowGUI.mainlogic.factory.RemoveTask(factoryIndex);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
				}
			}
		});
		popupMenuActive.add(mntmRTactive);
		
		JMenuItem mntmETactive = new JMenuItem("Edit task");
		mntmETactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listActiveTasks);
				MWindowGUI.mainlogic.editedTask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				MWindowGUI.initEditor();
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuActive.add(mntmETactive);
		
		JPopupMenu popupMenuImportant = new JPopupMenu();
		popupMenuImportant.setToolTipText("Mark as Completed");
		addPopup(listImportantTasks, popupMenuImportant);
		
		JMenuItem mntmMACimportant = new JMenuItem("Mark as Completed");
		mntmMACimportant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listImportantTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuImportant.add(mntmMACimportant);
		
		JMenuItem mntmMAIimportant = new JMenuItem("Mark as Incompleted");
		mntmMAIimportant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listImportantTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuImportant.add(mntmMAIimportant);
		
		JMenuItem mntmMAIMimportant = new JMenuItem("Mark as Important");
		mntmMAIMimportant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listImportantTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuImportant.add(mntmMAIMimportant);
		
		JMenuItem mntmMAUIMimportant = new JMenuItem("Mark as Unimportant");
		mntmMAUIMimportant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listImportantTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as Incompleted" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuImportant.add(mntmMAUIMimportant);
		
		JMenuItem mntmRTimportant = new JMenuItem("Remove task");
		mntmRTimportant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listImportantTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Remove task"+ Integer.toString(factoryIndex));
				Task chosentask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				String strDate = Constants.DateToString(chosentask.getDueDate());
				String message = "Are you sure you want to delete the task \"" + chosentask.getDescription() + " " + strDate + "\"";
				if (JOptionPane.showConfirmDialog(frmListsOfTasks, 
			            message, "Remove Task?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
				MWindowGUI.mainlogic.factory.RemoveTask(factoryIndex);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
				}
			}
		});
		popupMenuImportant.add(mntmRTimportant);
		
		JMenuItem mntmETimportant = new JMenuItem("Edit task");
		mntmETimportant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listImportantTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Edit Task"+ Integer.toString(factoryIndex));
				MWindowGUI.mainlogic.editedTask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				MWindowGUI.initEditor();
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuImportant.add(mntmETimportant);
		
		JPopupMenu popupMenuCompleted = new JPopupMenu();
		popupMenuCompleted.setToolTipText("Mark as Completed");
		addPopup(listCompletedTasks, popupMenuCompleted);
		
		JMenuItem mntmMACcompleted = new JMenuItem("Mark as Completed");
		mntmMACcompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listCompletedTasks);
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuCompleted.add(mntmMACcompleted);
		
		JMenuItem mntmMAIcompleted = new JMenuItem("Mark as Incompleted");
		mntmMAIcompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listCompletedTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as Incompleted" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuCompleted.add(mntmMAIcompleted);
		
		JMenuItem mntmMAIMcompleted = new JMenuItem("Mark as Important");
		mntmMAIMcompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listCompletedTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as Incompleted" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuCompleted.add(mntmMAIMcompleted);
		
		JMenuItem mntmMAUIMcompleted = new JMenuItem("Mark as Unimportant");
		mntmMAUIMcompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listCompletedTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as Incompleted" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuCompleted.add(mntmMAUIMcompleted);
		
		JMenuItem mntmRTcompleted = new JMenuItem("Remove task");
		mntmRTcompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listCompletedTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Remove task"+ Integer.toString(factoryIndex));
				Task chosentask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				String strDate = Constants.DateToString(chosentask.getDueDate());
				String message = "Are you sure you want to delete the task \"" + chosentask.getDescription() + " " + strDate + "\"";
				if (JOptionPane.showConfirmDialog(frmListsOfTasks, 
			            message, "Remove Task?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
				MWindowGUI.mainlogic.factory.RemoveTask(factoryIndex);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
				}
			}
		});
		popupMenuCompleted.add(mntmRTcompleted);
		
		JMenuItem mntmETcompleted = new JMenuItem("Edit task");
		mntmETcompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listCompletedTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Edit Task"+ Integer.toString(factoryIndex));
				MWindowGUI.mainlogic.editedTask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				MWindowGUI.initEditor();
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuCompleted.add(mntmETcompleted);
		
		JPopupMenu popupMenuOverdue = new JPopupMenu();
		popupMenuOverdue.setToolTipText("Mark as Completed");
		addPopup(listOverdueTasks, popupMenuOverdue);
		
		JMenuItem mntmMACoverdue = new JMenuItem("Mark as Completed");
		mntmMACoverdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listOverdueTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as overdue" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuOverdue.add(mntmMACoverdue);
		
		JMenuItem mntmMAIoverdue = new JMenuItem("Mark as Incompleted");
		mntmMAIoverdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listOverdueTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as Inoverdue" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuOverdue.add(mntmMAIoverdue);
		
		JMenuItem mntmMAIMoverdue = new JMenuItem("Mark as Important");
		mntmMAIMoverdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listOverdueTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as Inoverdue" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(true);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuOverdue.add(mntmMAIMoverdue);
		
		JMenuItem mntmMAUIMoverdue = new JMenuItem("Mark as Unimportant");
		mntmMAUIMoverdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listOverdueTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Mark as Inoverdue" + Integer.toString(factoryIndex));
				Task task = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(false);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuOverdue.add(mntmMAUIMoverdue);
		
		JMenuItem mntmRToverdue = new JMenuItem("Remove task");
		mntmRToverdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listOverdueTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Remove task"+ Integer.toString(factoryIndex));
				Task chosentask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				String strDate = Constants.DateToString(chosentask.getDueDate());
				String message = "Are you sure you want to delete the task \"" + chosentask.getDescription() + " " + strDate + "\"";
				if (JOptionPane.showConfirmDialog(frmListsOfTasks, 
			            message, "Remove Task?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
				MWindowGUI.mainlogic.factory.RemoveTask(factoryIndex);
				MWindowGUI.mainlogic.saveTasksToFile(MWindowGUI.filePath);
				MWindowGUI.window.refreshMW();
				refreshTW();
				}
			}
		});
		popupMenuOverdue.add(mntmRToverdue);
		
		JMenuItem mntmEToverdue = new JMenuItem("Edit task");
		mntmEToverdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = MWindowGUI.getTaskfromList(listOverdueTasks);
				//JOptionPane.showMessageDialog(mntmMACtoday, "Edit Task"+ Integer.toString(factoryIndex));
				MWindowGUI.mainlogic.editedTask = MWindowGUI.mainlogic.factory.GetTask(factoryIndex);
				MWindowGUI.initEditor();
				MWindowGUI.window.refreshMW();
				refreshTW();
			}
		});
		popupMenuOverdue.add(mntmEToverdue);
		
		refreshTW();
		frmListsOfTasks.setVisible(true);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

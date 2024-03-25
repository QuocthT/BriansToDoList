package gui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

import classes.Constants;
import classes.MainLogic;
import classes.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JList;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;

public class MWindowGUI {

	private JFrame frmToDoList;
	private JLabel lblDateHour;
	
	private DefaultListModel<Task> todayTasksList;
	private JList<Task> listTodayTasks;
	private DefaultListModel<String> statusTodayTasksList;
	private JList<String> listTodayTasksStatus;
	
	private DefaultListModel<Task> tomorrowTasksList;
	private JList<Task> listTomorrowTasks;
	private DefaultListModel<String> statusTomorrowTasksList;
	private JList<String> listTomorrowTasksStatus;
	
	private DefaultListModel<Task> weekTasksList;
	private JList<Task> listWeekTasks;
	private DefaultListModel<String> statusWeekTasksList;
	private JList<String> listWeekTasksStatus;
	
	//public static String filePath = "/Users/dave1601/eclipse-workspace/Todolist/docs/mytasks.txt";
	public static String filePath = "docs/mytasks.txt";
	public static MainLogic mainlogic = new MainLogic(filePath);
	public static MWindowGUI window;
	public static ListsOfTasksGUI lists;
	public static Date reformedToday;
	public static Date reformedTomorrow;
	public static Date reformedWeek;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calendar calendar = Calendar.getInstance();
					Date today = calendar.getTime();
					String strToday = Constants.DateToString(today);
					
					calendar.add(Calendar.DAY_OF_YEAR,1);
					Date tomorrow = calendar.getTime();
					String strTomorrow = Constants.DateToString(tomorrow);
					
					calendar.add(Calendar.DAY_OF_YEAR,6);
					Date week = calendar.getTime();
					String strWeek = Constants.DateToString(week);
					
					try {
						reformedToday = new SimpleDateFormat(Constants.DateFormat).parse(strToday);
					} catch (ParseException e) {
						reformedToday = new Date();
					}
					
					try {
						reformedTomorrow = new SimpleDateFormat(Constants.DateFormat).parse(strTomorrow);
					} catch (ParseException e) {
						reformedTomorrow = new Date();
					}
					
					try {
						reformedWeek= new SimpleDateFormat(Constants.DateFormat).parse(strWeek);
					} catch (ParseException e) {
						reformedWeek = new Date();
					}
					
					window = new MWindowGUI();
					window.frmToDoList.setVisible(true);
					window.frmToDoList.addWindowListener(new java.awt.event.WindowAdapter() {
					    @Override
					    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					        if (JOptionPane.showConfirmDialog(window.frmToDoList, 
					            "Are you sure you want to close this window?", "Close Window?", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
						    {
						        System.exit(0);
						    }
					        else
					        {
					        	window.frmToDoList.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					        }
					    }
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static int getTaskfromList(JList<Task> list) 
	{
		int listIndex = list.getSelectedIndex();
		if (listIndex < 0)
		{
			return -1;
		}
		Task task = list.getModel().getElementAt(listIndex);
		int factoryIndex = mainlogic.factory.FindTask(task);
		if (factoryIndex < 0)
		{
			return -1;
		}
		return factoryIndex;
	}
	
	private void reorderMWLists(DefaultListModel<Task> modelTasksList, DefaultListModel<String> modelStatusTasksList )
	{
		int listSize = modelTasksList.getSize();
		Task[] listTasks = new Task[listSize];
		String[] listStatusTasks = new String[listSize];
		for(int i = 0; i < listSize;i++)
		{
			listTasks[i] = (Task)modelTasksList.getElementAt(i);
			listStatusTasks[i] = (String)modelStatusTasksList.getElementAt(i);
		}
		sortArrayTasks(listTasks, listStatusTasks);
		for (int i = 0; i < listSize;i++) 
		{
			modelTasksList.setElementAt(listTasks[i], i);
			modelStatusTasksList.setElementAt(listStatusTasks[i], i);
		}    
	}
	
	private void sortArrayTasks(Task[] list, String[] listStatus) 
	{
		Task tmp;
		String strTmp = "";
		if (list.length == 1) return;
		for (int i = 0; i < list.length; i++) 
		{
			for (int j = i + 1; j < list.length; j++) 
			{
				Date date1 = list[i].getDueDate();
				Date date2 = list[j].getDueDate();
				String task1Status = listStatus[i];
				String task2Status = listStatus[j];
				
				if(date1.compareTo(date2) > 0) 
				{
			        tmp = list[i];
			        list[i] = list[j];
			        list[j] = tmp;
			        
			        strTmp = task1Status;
			        task1Status = task2Status;
			        task2Status = strTmp;
		        }
		    }
		} 
	}
		
	public void refreshMW() 
	{
		todayTasksList.clear();
		statusTodayTasksList.clear();
		
		tomorrowTasksList.clear();
		statusTomorrowTasksList.clear();
		
		weekTasksList.clear();
		statusWeekTasksList.clear();
		
		for(int i = 0; i < mainlogic.factory.TaskCount();i++)
		{
			Date chosendate = mainlogic.factory.GetTaskTime(i);
			boolean statusCompleted = mainlogic.factory.GetTaskCompleted(i);
			
			if (chosendate.compareTo(reformedToday) == 0 && statusCompleted == false)
			{
				todayTasksList.addElement(mainlogic.factory.GetTask(i));
				statusTodayTasksList.addElement(mainlogic.factory.GetTaskStatus(i));
			}
			
			if (chosendate.compareTo(reformedToday) < 0 && statusCompleted == false)
			{
				todayTasksList.addElement(mainlogic.factory.GetTask(i));
				statusTodayTasksList.addElement(mainlogic.factory.GetTaskStatus(i) + "Overdue");
			}
			
			if (chosendate.compareTo(reformedToday) >= 0 && statusCompleted == false)
			{
				if (chosendate.compareTo(reformedTomorrow) == 0 )
				{
					tomorrowTasksList.addElement(mainlogic.factory.GetTask(i));
					statusTomorrowTasksList.addElement(mainlogic.factory.GetTaskStatus(i));
				}
				
				if (chosendate.compareTo(reformedWeek) < 0)
				{
					weekTasksList.addElement(mainlogic.factory.GetTask(i));
					statusWeekTasksList.addElement(mainlogic.factory.GetTaskStatus(i));
				}
			}			
		}
		reorderMWLists(weekTasksList, statusWeekTasksList);
	}

	public static void initEditor() 
	{
		EditTaskGUI UFGUI = new EditTaskGUI();
		UFGUI.textFieldShownTasks.setText(mainlogic.editedTask.getDescription());
		UFGUI.dateChooser.setDate(mainlogic.editedTask.getDueDate());
		boolean statusImp = mainlogic.editedTask.getImportant();
		if (statusImp == false)
		{
			UFGUI.comboBoxImportant.setSelectedIndex(0);
		}
		else
		{
			UFGUI.comboBoxImportant.setSelectedIndex(1);
		}
	}

	public MWindowGUI() 
	{
		initialize();
		clock();
	}

	private void clock()
	{
		Thread clock = new Thread()
		{
			public void run()
			{
				try {
					for(;;) {
					Calendar cal = new GregorianCalendar();
					int day = cal.get(Calendar.DAY_OF_MONTH);
					int month = cal.get(Calendar.MONTH) + 1;
					int year = cal.get(Calendar.YEAR);
					int second = cal.get(Calendar.SECOND);
					int minute = cal.get(Calendar.MINUTE);
					int hour = cal.get(Calendar.HOUR);
					
					lblDateHour.setText("Time  "+ hour+":"+minute+":"+second+"  Date "+year+"/"+month+"/"+day);
					
					sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();	
	}
	
	private void initialize() {
		frmToDoList = new JFrame();
		frmToDoList.setVisible(true);
		frmToDoList.setBackground(Color.WHITE);
		frmToDoList.setForeground(Color.WHITE);
		frmToDoList.setTitle("TO DO LIST");
		frmToDoList.setBounds(100, 100, 785, 701);
		frmToDoList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		todayTasksList = new DefaultListModel<>();
		statusTodayTasksList = new DefaultListModel<>();
		statusTodayTasksList.addElement("something");
		listTodayTasks = new JList<>(todayTasksList);
		listTodayTasks.setToolTipText("Left Click the task to edit or remove it");
		listTodayTasks.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		listTodayTasksStatus = new JList<>(statusTodayTasksList);
		listTodayTasksStatus.setToolTipText("A today's tasks list showing whether the tasks \nare important, completed, active or overdue");
		listTodayTasksStatus.setForeground(Color.RED);
		listTodayTasksStatus.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		
		tomorrowTasksList = new DefaultListModel<>();
		statusTomorrowTasksList = new DefaultListModel<>();
		statusTomorrowTasksList.addElement("something");
		listTomorrowTasks = new JList<>(tomorrowTasksList);
		listTomorrowTasks.setToolTipText("Left Click the task to edit or remove it");
		listTomorrowTasks.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		listTomorrowTasksStatus = new JList<>(statusTomorrowTasksList);
		listTomorrowTasksStatus.setToolTipText("A tomorrow's tasks list showing whether the tasks \nare important, completed, active or overdue");
		listTomorrowTasksStatus.setForeground(Color.RED);
		listTomorrowTasksStatus.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			
		weekTasksList = new DefaultListModel<>();
		statusWeekTasksList = new DefaultListModel<>();
		statusWeekTasksList.addElement("something");
		frmToDoList.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		listWeekTasks = new JList<>(weekTasksList);
		listWeekTasks.setToolTipText("Left Click the task to edit or remove it");
		listWeekTasks.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		listWeekTasksStatus = new JList<>(statusWeekTasksList);
		listWeekTasksStatus.setToolTipText("A week's tasks list showing whether the tasks \nare important, completed, active or overdue");
		listWeekTasksStatus.setForeground(Color.RED);
		listWeekTasksStatus.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		
		JSplitPane splitPaneAll = new JSplitPane();
		frmToDoList.getContentPane().add(splitPaneAll);
		splitPaneAll.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneAll.setDividerLocation(325);
		
		JSplitPane splitPaneTop = new JSplitPane();
		splitPaneAll.setLeftComponent(splitPaneTop);
		splitPaneTop.setDividerLocation(385);
		
		JSplitPane splitPaneBot = new JSplitPane();
		splitPaneAll.setRightComponent(splitPaneBot);
		splitPaneBot.setDividerLocation(385);
		
		JLabel lblLabelDD = new JLabel("Date and Description:");
		lblLabelDD.setEnabled(false);
		
		JLabel lblLabelDD2 = new JLabel("Date and Description:");
		lblLabelDD2.setEnabled(false);
		
		JLabel lblLabelDD3 = new JLabel("Date and Description:");
		lblLabelDD3.setEnabled(false);
		
		JScrollPane scrollPaneToday = new JScrollPane();
		scrollPaneToday.setBorder(new TitledBorder(null, "Today's Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPaneToday.setBounds(16, 53, 600, 411);
		scrollPaneToday.setViewportView(listTodayTasks);
		scrollPaneToday.setRowHeaderView(listTodayTasksStatus);
		splitPaneTop.setRightComponent(scrollPaneToday);
		scrollPaneToday.setColumnHeaderView(lblLabelDD);
		
		JScrollPane scrollPaneTomorrow = new JScrollPane();
		scrollPaneTomorrow.setBorder(new TitledBorder(null, "Tomorrow's Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPaneBot.setLeftComponent(scrollPaneTomorrow);
		scrollPaneTomorrow.setViewportView(listTomorrowTasks);
		scrollPaneTomorrow.setRowHeaderView(listTomorrowTasksStatus);
		scrollPaneTomorrow.setColumnHeaderView(lblLabelDD2);
		
		JScrollPane scrollPaneWeek = new JScrollPane();
		scrollPaneWeek.setBorder(new TitledBorder(null, "Week's Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPaneBot.setRightComponent(scrollPaneWeek);
		scrollPaneWeek.setViewportView(listWeekTasks);
		scrollPaneWeek.setRowHeaderView(listWeekTasksStatus);
		scrollPaneWeek.setColumnHeaderView(lblLabelDD3);
		
		JPopupMenu popupMenuToday = new JPopupMenu();
		popupMenuToday.setToolTipText("Mark as Completed");
		addPopup(listTodayTasks, popupMenuToday);
		
		JMenuItem mntmMACtoday = new JMenuItem("Mark as Completed");
		mntmMACtoday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTodayTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(true);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();	
			}
		});
		popupMenuToday.add(mntmMACtoday);
		
		JMenuItem mntmMAItoday = new JMenuItem("Mark as Incompleted");
		mntmMAItoday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTodayTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(false);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();	
			}
		});
		popupMenuToday.add(mntmMAItoday);
		
		JMenuItem mntmMAIMtoday = new JMenuItem("Mark as Important");
		mntmMAIMtoday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTodayTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(true);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();	
			}
		});
		popupMenuToday.add(mntmMAIMtoday);
		
		JMenuItem mntmMAUIMtoday = new JMenuItem("Mark as Unimportant");
		mntmMAUIMtoday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTodayTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(false);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();	
			}
		});
		popupMenuToday.add(mntmMAUIMtoday);
		
		JMenuItem mntmRTtoday = new JMenuItem("Remove task");
		mntmRTtoday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTodayTasks);
				Task chosentask = mainlogic.factory.GetTask(factoryIndex);
				String strDate = Constants.DateToString(chosentask.getDueDate());
				String message = "Are you sure you want to delete the task \"" + chosentask.getDescription() + " " + strDate + "\"";
				if (JOptionPane.showConfirmDialog(window.frmToDoList, 
			            message, "Remove Task?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					mainlogic.factory.RemoveTask(factoryIndex);
					mainlogic.saveTasksToFile(filePath);
					refreshMW();
					lists.refreshTW();	
				}
			}
		});
		popupMenuToday.add(mntmRTtoday);
		
		JMenuItem mntmETtoday = new JMenuItem("Edit task");
		mntmETtoday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTodayTasks);
				mainlogic.editedTask = mainlogic.factory.GetTask(factoryIndex);
				initEditor();
				refreshMW();
				lists.refreshTW();	
			}
		});
		popupMenuToday.add(mntmETtoday);
		
		JPopupMenu popupMenuTomorrow = new JPopupMenu();
		popupMenuTomorrow.setToolTipText("Mark as Completed");
		addPopup(listTomorrowTasks, popupMenuTomorrow);
		
		JMenuItem mntmMACtomorrow = new JMenuItem("Mark as Completed");
		mntmMACtomorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTomorrowTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(true);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuTomorrow.add(mntmMACtomorrow);
		
		JMenuItem mntmMAItomorrow = new JMenuItem("Mark as Incompleted");
		mntmMAItomorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTomorrowTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(false);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuTomorrow.add(mntmMAItomorrow);
		
		JMenuItem mntmMAIMtomorrow = new JMenuItem("Mark as Important");
		mntmMAIMtomorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTomorrowTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(true);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuTomorrow.add(mntmMAIMtomorrow);
		
		JMenuItem mntmMAUIMtomorrow = new JMenuItem("Mark as Unimportant");
		mntmMAUIMtomorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTomorrowTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(false);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuTomorrow.add(mntmMAUIMtomorrow);
		
		JMenuItem mntmRTtomorrow = new JMenuItem("Remove task");
		mntmRTtomorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTomorrowTasks);
				Task chosentask = mainlogic.factory.GetTask(factoryIndex);
				String strDate = Constants.DateToString(chosentask.getDueDate());
				String message = "Are you sure you want to delete the task \"" + chosentask.getDescription() + " " + strDate + "\"";
				if (JOptionPane.showConfirmDialog(window.frmToDoList, 
			            message, "Remove Task?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
				mainlogic.factory.RemoveTask(factoryIndex);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
				}
			}
		});
		popupMenuTomorrow.add(mntmRTtomorrow);
		
		JMenuItem mntmETtomorrow = new JMenuItem("Edit task");
		mntmETtomorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listTomorrowTasks);
				mainlogic.editedTask = mainlogic.factory.GetTask(factoryIndex);
				initEditor();
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuTomorrow.add(mntmETtomorrow);
		
		JPopupMenu popupMenuWeek = new JPopupMenu();
		popupMenuWeek.setToolTipText("Mark as Completed");
		addPopup(listWeekTasks, popupMenuWeek);
		
		JMenuItem mntmMACweek = new JMenuItem("Mark as Completed");
		mntmMACweek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listWeekTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(true);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuWeek.add(mntmMACweek);
		
		JMenuItem mntmMAIweek = new JMenuItem("Mark as Incompleted");
		mntmMAIweek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listWeekTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setCompleted(false);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuWeek.add(mntmMAIweek);
		
		JMenuItem mntmMAIMweek = new JMenuItem("Mark as Important");
		mntmMAIMweek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listWeekTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(true);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuWeek.add(mntmMAIMweek);
		
		JMenuItem mntmMAUIMweek = new JMenuItem("Mark as Unimportant");
		mntmMAUIMweek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listWeekTasks);
				Task task = mainlogic.factory.GetTask(factoryIndex);
				task.setImportant(false);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuWeek.add(mntmMAUIMweek);
		
		JMenuItem mntmRTweek = new JMenuItem("Remove task");
		mntmRTweek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listWeekTasks);
				Task chosentask = mainlogic.factory.GetTask(factoryIndex);
				String strDate = Constants.DateToString(chosentask.getDueDate());
				String message = "Are you sure you want to delete the task \"" + chosentask.getDescription() + " " + strDate + "\"";
				if (JOptionPane.showConfirmDialog(window.frmToDoList, 
			            message, "Remove Task?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
				mainlogic.factory.RemoveTask(factoryIndex);
				mainlogic.saveTasksToFile(filePath);
				refreshMW();
				lists.refreshTW();
				}
			}
		});
		popupMenuWeek.add(mntmRTweek);
		
		JMenuItem mntmETweek = new JMenuItem("Edit task");
		mntmETweek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int factoryIndex = getTaskfromList(listWeekTasks);
				mainlogic.editedTask = mainlogic.factory.GetTask(factoryIndex);
				initEditor();
				refreshMW();
				lists.refreshTW();
			}
		});
		popupMenuWeek.add(mntmETweek);
		
		JPanel panelData = new JPanel();
		splitPaneTop.setLeftComponent(panelData);
		panelData.setLayout(null);
		
		JLabel lblNiceDayPNG = new JLabel("");
		lblNiceDayPNG.setBounds(16, 34, 350, 149);
		panelData.add(lblNiceDayPNG);
		lblNiceDayPNG.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNiceDayPNG.setBackground(Color.WHITE);
		lblNiceDayPNG.setIcon(new ImageIcon(MWindowGUI.class.getResource("/images/niceday.png")));
			
		lblDateHour = new JLabel("DATE & HOUR");
		lblDateHour.setBounds(16, 6, 289, 22);
		panelData.add(lblDateHour);
		lblDateHour.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
			
		JButton btnListsTasks = new JButton("LISTS OF TASKS");
		btnListsTasks.setBounds(26, 195, 141, 102);
		panelData.add(btnListsTasks);
		btnListsTasks.setForeground(Color.BLUE);
		btnListsTasks.setBackground(Color.GREEN);
		btnListsTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lists = new ListsOfTasksGUI();
			}
		});
		btnListsTasks.setToolTipText("Click to display completed, active, important and overdue tasks");
			
		JButton btnAddTask = new JButton("ADD TASK");
		btnAddTask.setBounds(207, 195, 141, 102);
		panelData.add(btnAddTask);
		btnAddTask.setForeground(new Color(255, 99, 71));
		btnAddTask.setToolTipText("Click to add a new task");
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddTaskGUI();
			}
		});
		
		refreshMW();
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

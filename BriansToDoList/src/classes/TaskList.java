package classes;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	myTasks fileRAF = new myTasks();
	public TaskList() {}
	
	public void ReadTasks(String fileName) 
	{
		fileRAF.readTasks(fileName, this);
	}
	
	public void WriteTasks(String fileName) 
	{
		fileRAF.saveTasks(fileName, this);
	}
	
	void AddTask(Task t)
	{
		this.add(t);
	}
	
	void DeleteTask(Task t)
	{
		this.remove(t);
	}
	
}
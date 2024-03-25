package classes;

import java.util.Date;

public class Factory 
{
	TaskList mainTasklist;
	
	public Factory(String filePath)
	{
		this.mainTasklist = new TaskList();
		this.mainTasklist.ReadTasks(filePath);
	}
	
	public void AddTask(String description, Date date, boolean completed, boolean important)
	{
		Task newtask = new Task(description, date, completed, important);
		mainTasklist.AddTask(newtask);
	}
	
	public void RemoveTask(int i)
	{
		Task task = mainTasklist.get(i);
		mainTasklist.DeleteTask(task);
	}
	
	public int TaskCount() 
	{
		return mainTasklist.size();
	}
	
	public String GetTaskDescription(int i) 
	{
		Task task = mainTasklist.get(i);
		return task.getDescription();
	}
	
	public String GetTaskStatus(int i)
	{
		String result = "";
		if (GetTaskImportant(i) == true)
		{
			result += "! ";
		}
		if (GetTaskCompleted(i) == true)
		{
			result += "Done";
		}
		if (GetTaskCompleted(i) == false)
		{
			result += " ";
		}
		return result;
	}
	
	public Date GetTaskTime(int i)
	{
		Task task = mainTasklist.get(i);
		return task.getDueDate();
	}
	
	public boolean GetTaskCompleted(int i)
	{
		Task task = mainTasklist.get(i);
		return task.getCompleted();
	}
	
	public boolean GetTaskImportant(int i)
	{
		Task task = mainTasklist.get(i);
		return task.getImportant();
	}
	
	public Task GetTask(int i)
	{
		Task task = mainTasklist.get(i);
		return task;
	}
	
	public int FindTask(Task task)
	{
		return mainTasklist.indexOf(task);
	}
}


package classes;

import java.io.Serializable;
import java.util.Date;


public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String description;
	Date dueDate;
	boolean completed = true;
	boolean important;
	
	public Task(String description, Date date, boolean completed, boolean important) 
	{
		this.description = description;
		this.dueDate = date;
		this.completed = completed;
		this.important = important;
	}
	
	public @Override String toString()
	{
		String result = "[" + Constants.DateToString(dueDate) + "]  " + description;
		return result;
	}

	public String getDescription() 
	{
		return this.description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public Date getDueDate() 
	{
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) 
	{
		this.dueDate = dueDate;
	}
	
	public boolean getCompleted() 
	{
		return this.completed;
	}

	public void setCompleted(boolean completed) 
	{
		this.completed = completed;
	}

	public boolean getImportant() 
	{
		return this.important;
	}

	public void setImportant(boolean important) 
	{
		this.important = important;
	}
	
	
}




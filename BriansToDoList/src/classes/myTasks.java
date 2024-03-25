package classes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class myTasks {
	
	public void readTasks(String filePath, List<Task> list)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			while (true) {
	            String line = reader.readLine();
	            if (line == null) {
	                break;
	            }
	            // Split line on semicolon.
	            String[] tokens = line.split(";");
	            String strDate = tokens[0];
	            Date date;
				try {
					date = new SimpleDateFormat(Constants.DateFormat).parse(strDate);
				} catch (ParseException e) {
					date = new Date();
				}  
				
	            String description = tokens[1];
	            String strCompleted = tokens[2];
	            String strImportant = tokens[3];
	            boolean completed = Boolean.parseBoolean(strCompleted);
	        	boolean important = Boolean.parseBoolean(strImportant);
	        	Task t = new Task(description, date, completed, important);
		        list.add(t);
	        }
			reader.close();
			
		} catch (IOException e) {
			System.out.println("An error occurred while reading tasks from a file");
			e.printStackTrace();
		}	
	}
	
	public void saveTasks(String filePath, TaskList list)
	{
		try {
			new FileWriter(filePath, false).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter myWriter = new FileWriter(filePath);
			int length = list.size();
			for(int i = 0; i < length;i++)
			{
				Task task = list.get(i);
				String description = task.getDescription();
				
				Date date = task.getDueDate();
				String strDate = Constants.DateToString(date);
				
				boolean completed = task.getCompleted();
				String strCompleted = String.valueOf(completed);
				
				boolean important = task.getImportant();
	            String strImportant = String.valueOf(important);
				
				myWriter.write(strDate + ";" + description + ";" + strCompleted + ";" + strImportant);
				myWriter.write("\n");
			}
		    myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred while saving tasks");
			e.printStackTrace();
		}
	}
}
	


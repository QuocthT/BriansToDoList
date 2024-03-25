package classes;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainLogic extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField taskName;
	public Factory factory;
	public Task editedTask;
	
	public MainLogic(String filePath)
	{
		factory = new Factory(filePath);
	}
	
	public void saveTasksToFile(String filePath)
	{
		factory.mainTasklist.WriteTasks(filePath);
	}
}

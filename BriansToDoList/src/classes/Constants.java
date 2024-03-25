package classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants 
{
	public static String DateFormat = "dd-MM-yyyy";
	public static String DateToString(Date date)
	{
	    DateFormat dateFormat = new SimpleDateFormat(Constants.DateFormat);   
	    return dateFormat.format(date);
	}
}

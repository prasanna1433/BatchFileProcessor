/**
 * 
 */
package batch;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Prasanna
 *
 */

public class Batch {
	//Maintains N commands that are parsed from the batch file
	String workingDir;
	Map<String,Command> commands=new LinkedHashMap<String,Command>();
	public static int indicator=0;
	
	/*This method adds commands into map
	 * */
	void addCommand(Command command)
	{
		commands.put("command"+indicator, command);
		indicator++;
	}
	
	/*This method returns the working directory
	 * */
	String getWorkingDir()
	{
		return workingDir;
	}
	
	/*This method returns the command map
	 * */
	Map<String,Command> getCommands()
	{
		return commands;
	}
	
}

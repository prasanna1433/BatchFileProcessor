package batch;

import java.util.Map;

import org.w3c.dom.Element;

import exception.ProcessException;

public abstract class Command {
	public static Map<String,String> fileMapping;  //this variable is used for mapping the file id to file path
	public Map<String,String> commandAttributes;   //this variable is used to map the command attributes to the command
	abstract String describe();                    //this method is used for describing the execution of the command
    abstract void execute(String workingDir);      //this method contains the execution section of each command
    abstract public void parse(Element element) throws ProcessException; //this method parses each command attributes
}


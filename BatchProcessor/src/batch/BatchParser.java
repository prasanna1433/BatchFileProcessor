package batch;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exception.ProcessException;

public class BatchParser {
	/*Responsible for visiting each XML element in the XML document and generating the correct Command subclass 
	from that element*/
	public Batch buildBatch(File batchFile)
	{
		// read the xml 
		// for every element call the buildCommand
		try {			
			
			Batch batchCommand=new Batch();
			
			FileInputStream fis = new FileInputStream(batchFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);

			Element pnode = doc.getDocumentElement();
			NodeList nodes = pnode.getChildNodes();
			
			for (int idx = 0; idx < nodes.getLength(); idx++) {
				Node node = nodes.item(idx);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					batchCommand.addCommand(buildCommand(elem));                              //call the build command method
				}
			}				
			return batchCommand;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;	
		}		
	}
	
/*This function identifies the command and calls appropriate parse method
 * */
	
Command buildCommand(Element elem) throws ProcessException
{
	//call parse 
	//call addCommand
	try{
	
	String cmdName=elem.getNodeName();
	
	if(cmdName==null){
		throw new ProcessException("The command name is NULL");
	}
	else if("cmd".equalsIgnoreCase(cmdName)){
		CmdCommand cmd=new CmdCommand();
		System.out.println("Parsing cmd");
		cmd.parse(elem);    	
		return cmd;
	}
	else if ("pipe".equalsIgnoreCase(cmdName)) {
		PipeCommand pipeCmd=new PipeCommand();
		System.out.println("Parsing pipe");
		pipeCmd.parse(elem);
		return pipeCmd;		
	}
	else if ("file".equalsIgnoreCase(cmdName)) {
		FileCommand fileCmd=new FileCommand();
		System.out.println("Parsing file");
		fileCmd.parse(elem);
		return fileCmd;
	}
	else if ("wd".equalsIgnoreCase(cmdName)) {
		WDCommand wdCmd=new WDCommand();
		System.out.println("Parsing wd");
		wdCmd.parse(elem);
		return wdCmd;
	}
	else{
		throw new ProcessException("The command name is invalid: "+cmdName);
	}
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		e.printStackTrace();
		System.exit(0);
		return null;
	}		
}
}

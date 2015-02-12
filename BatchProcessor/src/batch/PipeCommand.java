package batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
//import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import exception.ProcessException;

public class PipeCommand extends Command {
	String describe(){
		return "Pipe command on work";
		
	}
	void execute(String workingDir){
		try{
		//generating the command to be executed
		List<String> command = new ArrayList<String>();
		command.add(commandAttributes.get("path0").toString());
		command.add(commandAttributes.get("args00").toString());
		command.add(commandAttributes.get("args01").toString());
		
		//check if the files located within the file path
		if(fileMapping.get(commandAttributes.get("inID0"))==null)
	    	throw new ProcessException("Unable to locate the file:"+commandAttributes.get("inID0").toString());
	    else if(fileMapping.get(commandAttributes.get("outID1"))==null)
	    	throw new ProcessException("Unable to locate the file:"+commandAttributes.get("outID1").toString());		    	
	    else{
			System.out.println("command: "+commandAttributes.get("args01"));
			System.out.println(commandAttributes.get("args01")+" deferring execution");
			//build the first process
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory(new File(workingDir));
		File wd = builder.directory();

		final Process process = builder.start();
		OutputStream os = process.getOutputStream();
		FileInputStream fis = new FileInputStream(new File(wd, fileMapping.get(commandAttributes.get("inID0"))));
		int achar;
		while ((achar = fis.read()) != -1) {
			os.write(achar);
		}
		os.close();          //stores the input read from the file in output stream
		InputStream is = process.getInputStream();
		
		List<String> command1 = new ArrayList<String>();
		command1.add(commandAttributes.get("path1").toString());
		command1.add(commandAttributes.get("args10").toString());
		command1.add(commandAttributes.get("args11").toString());
		
		System.out.println("command: "+commandAttributes.get("args11"));
		System.out.println(commandAttributes.get("args11")+" deferring execution");
		
		ProcessBuilder builder1 = new ProcessBuilder(command1);	
		builder1.directory(new File(workingDir));
		File wd1 = builder.directory();
		FileOutputStream fos = new FileOutputStream(new File(wd1,fileMapping.get(commandAttributes.get("outID1"))));
		
		final Process process1 = builder1.start();
		InputStream is1 = process1.getInputStream();
		OutputStream os1 = process1.getOutputStream();	
		
		//piping the input to the output stream of the second process
		while ((achar = is.read()) != -1) {
		os1.write(achar);
		}
		os1.close();
		
		//writing into the output file 
		while ((achar = is1.read()) != -1) {
			fos.write(achar);		
		}
		
		fos.close();
		fis.close();
	    }
		}
		catch(ProcessException pe){
			System.out.println(pe.getMessage());
			pe.printStackTrace();
			System.exit(0);
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	public void parse(Element element){
		try
		{
		NodeList n1=element.getElementsByTagName("cmd");            //separates the nodes in the command
		commandAttributes=new LinkedHashMap<String,String>();
		int j=0;
		for(int i=0;i<2;i++)
		{
		j=0;
		element=(Element)n1.item(i);
		
		String id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in CMD Command");
		}
		commandAttributes.put("id"+i, id);
		
		String path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in CMD Command");
		}
		commandAttributes.put("path"+i, path);

		// Arguments must be passed to ProcessBuilder as a list of
		// individual strings. 
		List<String> cmdArgs = new ArrayList<String>();
		String arg = element.getAttribute("args");
		if (!(arg == null || arg.isEmpty())) {
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				cmdArgs.add(tok);
			}
		}
		for(String argi: cmdArgs) {
			commandAttributes.put("args"+i+j, argi);
			j++;
		}

		String inID = element.getAttribute("in");
		if (!(inID == null || inID.isEmpty())) {
			commandAttributes.put("inID"+i,inID);
		}

		String outID = element.getAttribute("out");
		if (!(outID == null || outID.isEmpty())) {
			commandAttributes.put("outID"+i, outID);
		}
		}
		}
		
		catch(ProcessException pe){
			System.out.println(pe.getMessage());
			pe.printStackTrace();
			System.exit(0);
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}


}

package batch;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

import exception.ProcessException;

//import exception.ProcessException;

public class CmdCommand extends Command {
	
	String describe()
	{
		return "CMD Command on work";
	}
	
	void execute(String workingDir) { 
		try{
							
			List<String> command = new ArrayList<String>();
			
			
			//this block will be executed for sorting the words
			if(commandAttributes.get("path").equals("sort") && commandAttributes.get("args0")==null){
				
			    command.add(commandAttributes.get("path").toString());
			    if(fileMapping.get(commandAttributes.get("inID"))==null) //check if the file is found
			    	throw new ProcessException("Unable to locate the file:"+commandAttributes.get("inID").toString());
			    else
			    	command.add(fileMapping.get(commandAttributes.get("inID")).toString());
				
			    System.out.println("Sort on Work");
			    
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(command);
				builder.directory(new File(workingDir));
				builder.redirectError(new File(workingDir,"error.txt"));
				builder.redirectOutput(new File(workingDir,fileMapping.get(commandAttributes.get("outID"))));		
		       
				final Process process1 = builder.start();
				process1.waitFor();
				
				System.out.println(commandAttributes.get("id")+" has exited");
				}
				
			    //this block will be executed for reverse sorting the words
			else if(commandAttributes.get("path").equals("sort") && commandAttributes.get("args0").equals("/R")){
					//adding the command into a string
			        command.add(commandAttributes.get("path").toString());	                               
			        command.add(commandAttributes.get("args0"));
			        command.add(fileMapping.get(commandAttributes.get("inID")).toString());
			        
			        System.out.println("Reverse Sort on Work");
			        
			        //using process builder to build and execute the command
					ProcessBuilder builder = new ProcessBuilder();
					builder.command(command);
					builder.directory(new File(workingDir));
					builder.redirectError(new File(workingDir,"error.txt"));
					builder.redirectOutput(new File(workingDir,fileMapping.get(commandAttributes.get("outID"))));
					
					final Process process2 = builder.start();
					process2.waitFor();
					
					System.out.println(commandAttributes.get("id")+" has exited");
					
				}
					
				//this block is executed for executing the dir command
			else if(commandAttributes.get("path").equals("cmd")){
					
			        command.add(commandAttributes.get("path").toString());   
			        command.add(commandAttributes.get("args0"));
			        command.add(commandAttributes.get("args1"));
			        
			        System.out.println("Writing dir to the file : "+fileMapping.get(commandAttributes.get("outID")));
			       			        
					ProcessBuilder builder = new ProcessBuilder();
					builder.command(command);
					builder.directory(new File(workingDir));
					builder.redirectError(new File(workingDir,"error.txt"));
					builder.redirectOutput(new File(workingDir,fileMapping.get(commandAttributes.get("outID"))));
					
					final Process process3 = builder.start();
					process3.waitFor();
					
					System.out.println(commandAttributes.get("id")+" has exited");
				}				
				
				//this block is executed for adding the number in a file and storing it in another file
			else if(commandAttributes.get("path").equals("java.exe")  &&  commandAttributes.get("args1").equals("addLines.jar")){
					
		
			        command.add(commandAttributes.get("path").toString());   
			        command.add(commandAttributes.get("args0"));
			        command.add(commandAttributes.get("args1"));
					if(fileMapping.get(commandAttributes.get("inID"))==null)
				    	throw new ProcessException("Unable to locate the file:"+commandAttributes.get("inID").toString());
				    else if(fileMapping.get(commandAttributes.get("outID"))==null)
				    	throw new ProcessException("Unable to locate the file:"+commandAttributes.get("outID").toString());	
				    else
				    	{
					ProcessBuilder builder = new ProcessBuilder();
					System.out.println("command: "+commandAttributes.get("args1"));
					System.out.println(commandAttributes.get("args1")+" deferring execution");
					builder.command(command);
					builder.directory(new File(workingDir));					
					builder.redirectInput(new File(workingDir,fileMapping.get(commandAttributes.get("inID"))));
					builder.redirectError(new File(workingDir,"error.txt"));
					builder.redirectOutput(new File(workingDir,fileMapping.get(commandAttributes.get("outID"))));
					
					final Process process3 = builder.start();
					process3.waitFor();
					
					System.out.println(commandAttributes.get("id")+" has exited");			
				    	}
													
				}
				
				//this block is executed for finding the average of the numbers in a given file and storing it in another file

				if(commandAttributes.get("path").equals("java.exe")  &&  commandAttributes.get("args1").equals("avgFile.jar")){
			
					command.add(commandAttributes.get("path").toString());   
					command.add(commandAttributes.get("args0"));
					command.add(commandAttributes.get("args1"));
						if(fileMapping.get(commandAttributes.get("inID"))==null)
					    	throw new ProcessException("Unable to locate the file:"+commandAttributes.get("inID").toString());
					    else if(fileMapping.get(commandAttributes.get("outID"))==null)
					    	throw new ProcessException("Unable to locate the file:"+commandAttributes.get("outID").toString());		    	
					    else
					    {
							System.out.println("command: "+commandAttributes.get("args1"));
							System.out.println(commandAttributes.get("args1")+" deferring execution");
				    
						ProcessBuilder builder = new ProcessBuilder();
						builder.command(command);
						builder.directory(new File(workingDir));
						builder.redirectInput(new File(workingDir,fileMapping.get(commandAttributes.get("inID"))));
						builder.redirectError(new File(workingDir,"error.txt"));
						builder.redirectOutput(new File(workingDir,fileMapping.get(commandAttributes.get("outID"))));
			
						final Process process4 = builder.start();
						process4.waitFor();
								
						System.out.println(commandAttributes.get("id")+" has exited");
					    }
				}				
				
			}
		
		catch(ProcessException pe){
			System.out.println(pe.getMessage());
			pe.printStackTrace();
			System.exit(0);
		}
			
		
			catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.exit(0);
			}
	}
	
	/*Function to parse cmd tag*/	
	public void parse(Element element) {
		try{
		commandAttributes=new LinkedHashMap<String,String>();
		int i=0;
		String id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in CMD Command");
		}
		commandAttributes.put("id", id);
		
		String path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in CMD Command");
		}
		commandAttributes.put("path", path);

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
			commandAttributes.put("args"+i, argi);
			i++;
		}
		
		String inID = element.getAttribute("in");
		if (!(inID == null || inID.isEmpty())) {
			
			commandAttributes.put("inID",inID);
		}
		
		String outID = element.getAttribute("out");
		if (!(outID == null || outID.isEmpty())) {
			commandAttributes.put("outID", outID);
			}
		
		commandAttributes.put("errorText", "error.txt");
		
		/*for(Map.Entry<String,String> it: commandAttributes.entrySet()){
			System.out.println(it.getKey()+"="+it.getValue());
		}*/
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
}

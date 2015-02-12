package batch;

import java.util.LinkedHashMap;
//import java.util.Map;

import org.w3c.dom.Element;

import exception.ProcessException;

public class FileCommand extends Command {
	String describe(){
		return "File Command on work";		
	}
	
	void execute(String workingDir){
		System.out.println("Filecommand on:"+fileMapping.get(commandAttributes.get("id")));				
	}
	
	public void parse(Element element) throws ProcessException {
		try{
		commandAttributes=new LinkedHashMap<String,String>();
		if(fileMapping==null)
		fileMapping=new LinkedHashMap<String,String>();
		
		String id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in File Command");
		}
		commandAttributes.put("id", id);
		
		String path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in File Command");
		}
		commandAttributes.put("path", path);
		fileMapping.put(id,path);

		/*for(Map.Entry<String,String> it: commandAttributes.entrySet()){
			System.out.println(it.getKey()+"="+it.getValue());
		}*/
		}
	
		catch(ProcessException pe){
			System.out.println(pe.getMessage());
			pe.printStackTrace();
			System.exit(0);
		}
		catch(Exception e){
		}
		}
	}


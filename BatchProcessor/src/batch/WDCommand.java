package batch;

//import java.util.ArrayList;
import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.StringTokenizer;



import org.w3c.dom.Element;

import exception.ProcessException;

public class WDCommand extends Command{
	String describe(){
		return "The working directory will be set to work";		
	}
	
	void execute(String workingDir){		
		System.out.println("Working Directory set to: "+commandAttributes.get("path"));
	}
	
	public void parse(Element element){
		try{
		commandAttributes=new LinkedHashMap<String,String>();
		
		String id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in WD Command");
		}
		commandAttributes.put("id", id);
		
		String path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in WD Command");
		}
		commandAttributes.put("path", path);
		
		/*for(Map.Entry<String,String> it: commandAttributes.entrySet()){
			System.out.println(it.getKey()+"="+it.getValue());
		}*/
		}
		catch(ProcessException pe){
			System.out.println(pe.getMessage());
			pe.printStackTrace();
			System.exit(0);
		}
	}

}

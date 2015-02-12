package batch;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import exception.ProcessException;

public class BatchProcessor {

	public static void main(String[] args) {
		try{
		//call buildBatch
		//call execute Batch
		if(args[0]==null)	
			throw new ProcessException("Please Specify the batch file location");
		else{
		File fileInput=new File(args[0]);	             //read the file from args and pass it to buildBatch
		BatchParser btParser=new BatchParser();
		Batch batch=btParser.buildBatch(fileInput);	
		
		BatchProcessor btProcessor=new BatchProcessor(); //pass the batch returned to executeBatch
		btProcessor.executeBatch(batch);
		}
		}
		catch(ProcessException pe){
			System.out.println(pe.getMessage());
			pe.printStackTrace();
			System.exit(0);
		}

	}
	
	/*This is where the real execution of the batch takes place.Each command is executed individually from this function
	 * */
	
	void executeBatch(Batch batch){
		//call describe
		//call execute(String workingDir)
		int iteration=0,iterations=0;
		
		Map<String,Command> finalCommands=new LinkedHashMap<String,Command>();
		finalCommands=batch.getCommands();
		iterations=finalCommands.size();                                         //gets the number of commands in the batch
		
		for(iteration=0;iteration<iterations;iteration++)
		{
		Command getCommand=finalCommands.get("command"+iteration);
		System.out.println(getCommand.describe());
		getCommand.execute("work");
		}
		System.out.println("Finished Batch");
	}

}

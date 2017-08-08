package assignment.exhibitmonitor.task;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import assignment.exhibitmonitor.InputFile;
import assignment.exhibitmonitor.utility.ApplicationContext;
import assignment.exhibitmonitor.utility.xmlReader;

public class Poller implements Runnable {
	private boolean scanFolder = true;
	xmlReader reader = new xmlReader();
	
	public void run() {
		while(scanFolder) {
			File folder = new File("D:\\Users\\seantan\\Desktop\\Playground\\InputFolder");
			File[] directoryFiles = folder.listFiles();
			if(directoryFiles != null) {
				for(File file: directoryFiles) {
					if(isValid(file) && !isDuplicate(file) && isOnTime(file)) {
						File processedFolder = new File("D:\\Users\\seantan\\Desktop\\Playground\\ProcessFolder\\" + file.getName());
						boolean changedFolder = file.renameTo(processedFolder);
						
						Calendar cal = Calendar.getInstance();
						ApplicationContext.processedFiles.put(file.getName(), cal.getTime()); // if the 3 conditions are passed, they are added to the processed folder and processed map
						if(!changedFolder) {
							System.out.println("File not moved to processed folder");
						}
						Thread workerThread = new Thread(new Worker(processedFolder));
						workerThread.start();
					}
					else {
						boolean fileDeleted = file.delete();
					}
			}
		}
	}
}
	
	
	public boolean isValid(File file) {
		for(InputFile inputfile : ApplicationContext.inputFileList) {
			if(inputfile.getFileName().equals(file.getName())) {
				return true;
				}	 	
			}
			return false;
			}
	
	
	public boolean isDuplicate(File file) {
		if(ApplicationContext.processedFiles.size() == 0) {
			return false;
		}
		
		Date date = ApplicationContext.processedFiles.get(file.getName()); // return dates
		if(date == null) {
			return false;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		if(Calendar.getInstance().get(Calendar.YEAR) == cal.get(Calendar.YEAR) && 
				Calendar.getInstance().get(Calendar.DAY_OF_YEAR) ==  cal.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		else 
		{
			return false;
		}
						
		
		}
	
	
	public boolean isOnTime(File file) {
		long lastModifiedInMilliseconds = file.lastModified(); 
		long inputDateInMilliseconds = 0l;
		
		for(InputFile inputfile: ApplicationContext.inputFileList) {
			if(inputfile.getFileName().equals(file.getName())) {
				inputDateInMilliseconds = inputfile.getFileDate().getTime();
			}	
		}
		
		if(lastModifiedInMilliseconds < inputDateInMilliseconds) {
			return true;
		}
		else {
			return false;
		}
	
		
	}

}
	
	

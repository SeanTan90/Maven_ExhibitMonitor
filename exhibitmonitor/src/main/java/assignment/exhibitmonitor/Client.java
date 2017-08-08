package assignment.exhibitmonitor;


import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import assignment.exhibitmonitor.task.Poller;
import assignment.exhibitmonitor.task.Worker;
import assignment.exhibitmonitor.utility.ApplicationContext;
import assignment.exhibitmonitor.utility.xmlReader;

public class Client {

	public static void main(String[] args) {
		
		String file = "D:\\Users\\seantan\\Desktop\\Playground\\config.xml";
		
		xmlReader reader = new xmlReader();
		reader.readXML(file);
		reader.parseXmlFile();
		
		
		Poller poll = new Poller();
		Thread pollerThread = new Thread(poll);
		
		pollerThread.start(); //start the poll thread
		
		DBConnector DBConnect = new DBConnector();
		
		Thread addValidRecord = new Thread(new ValidRecordDB(ApplicationContext.getValidFiles(), DBConnect));
		addValidRecord.start();
		
		Thread addInvalidRecord = new Thread(new InvalidRecordDB(ApplicationContext.getInvalidFiles(), DBConnect));
		addInvalidRecord.start();
		
		
		if(!ApplicationContext.outputFileList.isEmpty()) {
			for(OutputFile outputfile : ApplicationContext.getOutputFileList()) {
				Calendar deadline = Calendar.getInstance();
				deadline.set(Calendar.HOUR_OF_DAY, 2);
				deadline.set(Calendar.MINUTE, 0);
				deadline.set(Calendar.SECOND, 0);
						
				Timer timer = new Timer();
				timer.schedule(new Output(DBConnect, outputfile), deadline.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
			}
		}
		
		
	}

}

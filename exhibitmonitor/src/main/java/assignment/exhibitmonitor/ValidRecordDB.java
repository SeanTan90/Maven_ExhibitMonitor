package assignment.exhibitmonitor;

import java.util.Vector;

public class ValidRecordDB implements Runnable {
	private Vector<Record> validFiles;
	private DBConnector DBConnect;
	
	public ValidRecordDB(Vector<Record> validFiles, DBConnector DBConnect) {
		this.validFiles = validFiles;
		this.DBConnect = DBConnect;
	}
	
	public void run() {
		while(true) {
			synchronized(validFiles) {
				while(validFiles.isEmpty()) {
					try {
						validFiles.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				DBConnect.validRecordUpload(validFiles.get(0));
			}
		}
		
	}
}

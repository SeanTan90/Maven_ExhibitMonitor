package assignment.exhibitmonitor;

import java.util.Vector;

public class InvalidRecordDB implements Runnable {
	private Vector<Record> invalidFiles;
	private DBConnector DBConnect;
	
	
	public InvalidRecordDB(Vector<Record> invalidFiles, DBConnector DBConnect) {
		this.invalidFiles = invalidFiles;
		this.DBConnect = DBConnect;
	}
	
	public void run() {
		while(true) {
			synchronized(invalidFiles) {
				while(invalidFiles.isEmpty()) {
				try {
					invalidFiles.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					}
				}
				DBConnect.invalidRecordUpload(invalidFiles.get(0));
			}
		}
	}
}


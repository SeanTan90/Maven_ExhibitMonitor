package assignment.exhibitmonitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.Vector;

public class Output extends TimerTask {
	private DBConnector DBConnect;
	private OutputFile outputfile;

	public Output(DBConnector DBConnect, OutputFile outputfile) {
		this.DBConnect = DBConnect;
		this.outputfile = outputfile; 
	}
	
	@Override
	public void run() {
			File folder = new File("D:\\Users\\seantan\\Desktop\\Playground\\ArchiveFolder" + outputfile.getFileName());
			FileWriter FW = null;
			BufferedWriter BW = null;
			int lines = 0;
			
			try {
				FW = new FileWriter(folder);
				BW = new BufferedWriter(FW);			
			
			for(String fileName: outputfile.getDependencies()) {
				Vector<String> record = DBConnect.getRecords(fileName);
				for(String str: record) {
					BW.write(str);
					BW.newLine();
					lines++;
				}
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if(FW != null) {
					FW.close();
				}
				if(BW != null) {
					BW.close();
				}
				if(lines == 0) {
					folder.delete();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	
	}

}

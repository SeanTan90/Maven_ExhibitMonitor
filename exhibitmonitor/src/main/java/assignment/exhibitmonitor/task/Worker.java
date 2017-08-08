package assignment.exhibitmonitor.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import assignment.exhibitmonitor.Field;
import assignment.exhibitmonitor.InputFile;
import assignment.exhibitmonitor.Record;
import assignment.exhibitmonitor.utility.ApplicationContext;

public class Worker implements Runnable {
	private File file;

	public Worker(File file) {
		this.file = file;
	}
	
	public void run() {
		FileReader FR = null;
		BufferedReader BR = null;
		String line = "";
		int serialNo = 1;
		
		try {
		FR = new FileReader(file);
		BR = new BufferedReader(FR);
		while((line = BR.readLine()) != null) {
			String[] csvData = line.split(",");
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();			
			boolean validFile = isValid(csvData);
			if(validFile) {
				synchronized(ApplicationContext.validFiles) {
				ApplicationContext.validFiles.add(new Record(serialNo, file.getName(), date, line));
				ApplicationContext.validFiles.notifyAll();
				}
			}
			else {
				synchronized(ApplicationContext.invalidFiles) {
				ApplicationContext.invalidFiles.add(new Record(serialNo, file.getName(), date, line));
				ApplicationContext.invalidFiles.notifyAll();
				}
			}
				serialNo++;
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(BR != null) {
					BR.close();
				}
				if(FR !=null) {
					FR.close();
					} 
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
			}

	private boolean isValid(String[] csvData) {
		for(InputFile inFile : ApplicationContext.inputFileList) {
		if(inFile.getFileName().equals(file.getName()) && csvData.length == inFile.getFieldList().size()) {
			return isFieldValid(csvData, inFile.getFieldList());
			}
		}
		return false;
	}

	private boolean isFieldValid(String[] csvData, List<Field> fieldList) {
		for(int i=0; i < fieldList.size(); i++) {
			if(fieldList.get(i).getType().equals("int")) {
				try{
					Integer.parseInt(csvData[i]);
				} catch (NumberFormatException e) {
					return false;
				}
			}
			else if(fieldList.get(i).getType().equals("double")) {
				try{
					Double.parseDouble(csvData[i]);
				} catch (NumberFormatException e) {
					return false;
				}
				
			}
			else if(fieldList.get(i).getType().equals("date")) {
				try{
					DateFormat dateformat = DateFormat.getInstance();
					dateformat.parse(csvData[i]);
				} catch(ParseException e) {
					return false;
				}
			} 
			else
			{
				return true;
			}
		}
		return false;
	}
}


package assignment.exhibitmonitor.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import assignment.exhibitmonitor.InputFile;
import assignment.exhibitmonitor.OutputFile;
import assignment.exhibitmonitor.Record;

public class ApplicationContext {
	
	public static List<InputFile> inputFileList = new ArrayList<InputFile>();
	public static List<OutputFile> outputFileList = new ArrayList<OutputFile>();
	public static Map<String, Date> processedFiles = new HashMap<String, Date>();
	public static Vector<Record> invalidFiles = new Vector<Record>();
	public static Vector<Record> validFiles = new Vector<Record>();
	
	
	public static Map<String, Date> getProcessedFiles() {
		return processedFiles;
	}


	public static void setProcessedFiles(Map<String, Date> processedFiles) {
		ApplicationContext.processedFiles = processedFiles;
	}


	public static Vector<Record> getInvalidFiles() {
		return invalidFiles;
	}


	public static void setInvalidFiles(Vector<Record> invalidFiles) {
		ApplicationContext.invalidFiles = invalidFiles;
	}


	public static Vector<Record> getValidFiles() {
		return validFiles;
	}


	public static void setValidFiles(Vector<Record> validFiles) {
		ApplicationContext.validFiles = validFiles;
	}


	public static List<InputFile> getInputFileList() {
		return inputFileList;
	}
	
	
	public static void setInputFileList(List<InputFile> inputFileList) {
		ApplicationContext.inputFileList = inputFileList;
	}

	 

	public static List<OutputFile> getOutputFileList() {
		return outputFileList;
	}

	public static void setOutputFileList(List<OutputFile> outputFileList) {
		ApplicationContext.outputFileList = outputFileList;
	}

	public ApplicationContext(List<InputFile> inputFileList, List<OutputFile> outputFileList) {
		this.inputFileList = inputFileList;
		this.outputFileList = outputFileList;
	}
	
	
}

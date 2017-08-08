package assignment.exhibitmonitor;

import java.util.Date;
import java.util.List;

public class OutputFile {
	private String fileName;
	private Date fileDate;
	private List<String> dependencies;
	
	
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Date getFileDate() {
		return fileDate;
	}


	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}


	public List<String> getDependencies() {
		return dependencies;
	}


	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}


	public OutputFile(String fileName, Date fileDate, List<String> dependencies) {
		this.fileName = fileName;
		this.fileDate = fileDate;
		this.dependencies = dependencies;
	}
}

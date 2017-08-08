package assignment.exhibitmonitor;

import java.util.Date;
import java.util.List;

public class InputFile {
	private String fileName;
	private Date fileDate;
	private List<Field> fieldList;
	
	public InputFile(String fileName, Date fileDate, List<Field> fieldList) {
		this.fileName = fileName;
		this.fileDate = fileDate;
		this.fieldList = fieldList;
	}

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

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}
}

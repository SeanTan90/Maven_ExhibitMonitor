package assignment.exhibitmonitor;

import java.util.Date;

public class Record {
	private String fileName;
	private Date fileDate;
	private int recordNo;
	private String record;
	
	public Record(int recordNo, String fileName, Date fileDate, String record) {
		this.recordNo = recordNo;
		this.fileName = fileName;
		this.fileDate = fileDate;	
		this.record = record;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Date getFileDate() {
		return fileDate;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

}

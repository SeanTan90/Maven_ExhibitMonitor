package assignment.exhibitmonitor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import assignment.exhibitmonitor.utility.ApplicationContext;
		

public class DBConnector {
	static Connection con = null;
	static ResultSet rs = null;
	
	
public synchronized void validRecordUpload(Record r) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		
		PreparedStatement stat1 = null;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?useSSL=false","root", "root");
			synchronized(con) {
			stat1 = con.prepareStatement("INSERT INTO VALIDRECORDS (Record_No, File_Name, Date, Record) VALUES (?,?,?,?)");
			stat1.setInt(1, r.getRecordNo());
			stat1.setString(2, r.getFileName());
			stat1.setDate(3, new Date(r.getFileDate().getTime()));
			stat1.setString(4, r.getRecord());
			stat1.execute();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ApplicationContext.getValidFiles().remove(0);
			try{
				if(stat1 != null) {
					stat1.close();
				}
				if(con != null) {
					con.close();
				} 
			} catch(SQLException e) {
					e.printStackTrace();
					}
				}
	}			
	
public synchronized void invalidRecordUpload(Record r) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		PreparedStatement stat3 = null;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?useSSL=false","root", "root");
			synchronized(con) {
			stat3 = con.prepareStatement("INSERT INTO INVALIDRECORDS (Record_No, File_Name, Date, Record) VALUES (?,?,?,?)" );
			stat3.setInt(1, r.getRecordNo());
			stat3.setString(2, r.getFileName());
			stat3.setDate(3, new Date(r.getFileDate().getTime()));
			stat3.setString(4, r.getRecord());
			System.out.println(r.getFileName());
			stat3.execute();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} 
		finally {
			ApplicationContext.getInvalidFiles().remove(0);
			try {
				if(stat3 != null) {
					stat3.close();
				}

				if(con != null) {
					con.close();
				}
			} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}

public synchronized Vector<String> getRecords(String fileName) {
	
	Vector<String> record = new Vector<String>();
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	PreparedStatement stat5 = null;
	
	try {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?useSSL=false","root", "root");
		synchronized(con) {
			
			stat5 = con.prepareStatement("SELECT * FROM VALIDRECORDS WHERE File_Name = ?");
			stat5.setString(1, fileName);
			rs = stat5.executeQuery();
			while(rs.next()) {
				record.add(rs.getString("Record"));
			}
		}
	} 
	catch(SQLException e) {
		e.printStackTrace();
	} 
	finally {
		try {
			if(stat5 != null) {
			stat5.close();
			}
			if(con != null) {
				con.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			}
		}
		return record;
	}
		
}
	


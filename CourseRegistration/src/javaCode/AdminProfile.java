package javaCode;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import dbConnect.connect;

public class AdminProfile {

	// Setting the session details
	private int eid;
	private String firstname;
	private String lastname;
	private String password;
	private Date dob;
	private String ssn;
	private static AdminProfile adminProfile;
	
	public static AdminProfile getInstance(){
		if(adminProfile == null){
			adminProfile = new AdminProfile();
		}
		return adminProfile;
	}
	
	public static AdminProfile getNewInstance(){
		adminProfile = new AdminProfile();
		return adminProfile;
	}
	
	// Deleting the current instance when the admin logs out.
	public static void deleteInstance(){
		adminProfile = null;
	}
	
	// Getting the current admin's employee id.
	public int getEid(){
		return eid;
	}
	
	// Setting the employee id of the current logged in admin
	public void setEid(int eid){
		this.eid = eid;
	}
	
	// Getting the date of birth of admin.
	public Date getDob(){
		return dob;
	}
	
	// Setting the date of birth of logged in admin.
	public void setDob(Date dob){
		this.dob = dob;
	}
	
	// Getiing the first name of admin.
	public String getFirstname(){
		return firstname;
	}
	
	// Setting the first name of admin.
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	// Getiing the last name of admin.
	public String getLastname(){
		return lastname;
	}
		
	// Setting the last name of admin.
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	// Getting the SSN of admin.
	public String getSsn(){
		return ssn;
	}
			
	// Setting the SSN of admin.
	public void setSsn(String ssn){
		this.ssn = ssn;
	}
	// Getting the password of admin.
	public String getPassword(){
		return password;
	}
				
	// Setting the password of admin.
	public void setPassword(String password){
		this.password = password;
	}	
	
	// Setting up the profile.
	public void setupProfile(ResultSet r){
		try{
			setDob(r.getDate("Dateofbirth"));
			setEid(r.getInt("Employee_id"));
			setFirstname(r.getString("First_Name"));
			setLastname(r.getString("Last_Name"));
			setPassword(r.getString("psswd"));
			setSsn(r.getString("SSN"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}

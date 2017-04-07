package javaCode;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StudentProfile {

	// Setting the session details
	private int sid;
	private String firstname;
	private String lastname;
	private String password;
	private Date dob;
	private String level_class;
	private String residency_class;
	private float gpa;
	private String email;
	private String phone;
	private String username;
	private String dept;
	
	private static StudentProfile studentProfile;
	
	public static StudentProfile getInstance(){
		if(studentProfile == null){
			studentProfile = new StudentProfile();
		}
		return studentProfile;
	}
	
	public static StudentProfile getNewInstance(){
		studentProfile = new StudentProfile();
		return studentProfile;
	}
	
	// Deleting the current instance when the student logs out.
	public static void deleteInstance(){
		studentProfile = null;
	}
	
	// Getting the current student's student id.
	public int getSid(){
		return sid;
	}
	
	// Setting the student id of the current logged in student.
	public void setSid(int sid){
		this.sid = sid;
	}
	
	// Getting the date of birth of logged in student.
	public Date getDob(){
		return dob;
	}
	
	// Setting the date of birth of logged in student.
	public void setDob(Date dob){
		this.dob = dob;
	}
	
	// Getting the first name of student.
	public String getFirstname(){
		return firstname;
	}
	
	// Setting the first name of student.
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	// Getting the last name of student.
	public String getLastname(){
		return lastname;
	}
		
	// Setting the last name of student.
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	// Getting the level classification of student.
	public String getLevelclass(){
		return level_class;
	}
			
	// Setting the level classification of student.
	public void setLevelclass(String level_class){
		this.level_class = level_class;
	}
	
	// Getting the password of student.
	public String getPassword(){
		return password;
	}
				
	// Setting the password of student.
	public void setPassword(String password){
		this.password = password;
	}	

	// Getting the residency classification of student.
	public String getResidencyclass(){
		return residency_class;
	}
			
	// Setting the residency classification of student.
	public void setResidencyclass(String residency_class){
		this.residency_class = residency_class;
	}
	
	// Getting the gpa of student.
	public float getGPA(){
		return gpa;
	}
			
	// Setting the gpa of student.
	public void setGPA(float gpa){
		this.gpa = gpa;
	}
	
	// Getting the email of student.
	public String getEmail(){
		return email;
	}
				
	// Setting the email of student.
	public void setEmail(String email){
		this.email = email;
	}	
	
	// Getting the contact number of student.
	public String getPhone(){
		return phone;
	}
					
	// Setting the contact number of student.
	public void setPhonel(String phone){
		this.phone = phone;
	}	
		
	// Getting the username of student.
	public String getUsername(){
		return username;
	}
					
	// Setting the username of student.
	public void setUsername(String username){
		this.username = username;
	}	

	// Getting the department of student.
	public String getDepte(){
		return dept;
	}
					
	// Setting the department of student.
	public void setDept(String dept){
		this.dept = dept;
	}		
	
	// Setting up the profile.
	public void setupProfile(ResultSet r){
		try{
			setDob(r.getDate("Dateofbirth"));
			setSid(r.getInt("Student_id"));
			setFirstname(r.getString("First_Name"));
			setLastname(r.getString("Last_Name"));
			setPassword(r.getString("psswd"));
			setGPA(r.getFloat("GPA"));
			setLevelclass(r.getString("level_class"));
			setResidencyclass(r.getString("residency_class"));
			setEmail(r.getString("email"));
			setPhonel(r.getString("phone"));
			setUsername(r.getString("username"));
			setDept(r.getString("department"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}

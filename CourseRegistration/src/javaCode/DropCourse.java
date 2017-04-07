package javaCode;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dbConnect.connect;

public class DropCourse {
	
	public static void drop_course(Scanner ip){
		try{
			System.out.println("\n** Drop a Course **");
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.get_enrolled);
			p1.setInt(1, StudentProfile.getInstance().getSid());
			ResultSet r1 = p1.executeQuery();
			ArrayList<AvailableClasses> cdata = new ArrayList<AvailableClasses>();
			while(r1.next()){
				AvailableClasses ac = new AvailableClasses();
				
				PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.select_course_name);
				p2.setString(1, r1.getString("COURSE_ID"));
				ResultSet r2 = p2.executeQuery();	
				if(r2.next()){
					ac.cname = r2.getString("COURSE_NAME");
				}
				ac.course_id = r1.getString("COURSE_ID");
				ac.sem = r1.getString("SEMESTER");
				ac.fname = r1.getString("FACULTY");
				ac.credit = r1.getInt("CREDIT");
				cdata.add(ac);
			}
			System.out.println("Select the courses from below: ");
			System.out.println("Press 0 to go back.");
			System.out.println("List of Enrolled courses: ");
			
			System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId")+"Course Name".format("%-50s", "Course Name")+"Credits".format("%-10s", "Credits")+
					"Faculty".format("%-30s", "Faculty"));
			
			for(int i = 0; i < cdata.size(); i++){
				String is = Integer.toString(i + 1) + ".";
				System.out.println(is.format("%-8s", is)+cdata.get(i).course_id.format("%-15s", cdata.get(i).course_id)+
						cdata.get(i).cname.format("%-50s", cdata.get(i).cname)+Integer.toString(cdata.get(i).credit).format("%-10s",Integer.toString(cdata.get(i).credit))
						+cdata.get(i).fname.format("%-30s", cdata.get(i).fname));
				
			}
			
			PreparedStatement p3 = connect.getConnection().prepareStatement(Queries.get_waitlisted);
			p3.setInt(1, StudentProfile.getInstance().getSid());
			ResultSet r3 = p3.executeQuery();
			ArrayList<AvailableClasses> cdata1 = new ArrayList<AvailableClasses>();
			while(r3.next()){
				AvailableClasses ac = new AvailableClasses();
				
				PreparedStatement p4 = connect.getConnection().prepareStatement(Queries.select_course_name);
				p4.setString(1, r3.getString("COURSE_ID"));
				ResultSet r4 = p4.executeQuery();	
				if(r4.next()){
					ac.cname = r4.getString("COURSE_NAME");
				}
				ac.course_id = r3.getString("COURSE_ID");
				ac.sem = r3.getString("SEMESTER");
				ac.fname = r3.getString("FACULTY");
				ac.wait_number = r3.getInt("WAITLIST_NUMBER");
				ac.dropc = r3.getString("DROP_COURSE");
				ac.credit = r3.getInt("CREDITS");
				cdata1.add(ac);
			}
			if(cdata1.size() > 0)
			System.out.println("List of Waitlisted course: ");
			for(int t = 0; t < cdata1.size(); t++){
				String ts = Integer.toString(t + cdata.size() + 1) + ".";
				System.out.println(ts.format("%-8s", ts)+cdata1.get(t).course_id.format("%-15s", cdata1.get(t).course_id)+
						cdata1.get(t).cname.format("%-50s", cdata1.get(t).cname)+Integer.toString(cdata1.get(t).credit).format("%-10s",Integer.toString(cdata1.get(t).credit))
						+cdata1.get(t).fname.format("%-30s", cdata1.get(t).fname));	
			}
			
			
			while(true){
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 0){
					StudentView.viewenrollCourses(ip);
				}
				else if(choice < 0 && choice > cdata.size()+cdata1.size()){
					System.out.println("Select correct option.");
				}
				else if(choice <= cdata.size()){
					drop_enrolled(cdata.get(choice - 1), ip);
					enroll_waitlisted(cdata.get(choice - 1), ip);
					DropCourse.drop_course(ip);
				}
				else if(choice > cdata.size() && choice <= cdata.size()+cdata1.size()){
					drop_waitlisted(cdata1.get(choice - 1 - cdata.size()), ip);
				}
				//enroll_waitlisted(cdata.get(choice - 1), ip);
				//DropCourse.drop_course(ip);
			}
		}  catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
		
	}
	
	
	// Method to drop the waitlisted course.
	public static void drop_waitlisted(AvailableClasses ac, Scanner ip){
		
		try{
			PreparedStatement pw1 = connect.getConnection().prepareStatement(Queries.drop_waitlist);
			pw1.setInt(1, StudentProfile.getInstance().getSid());
			pw1.setString(2, ac.course_id);
			pw1.setString(3, ac.fname);
			
			pw1.executeUpdate();
			
			PreparedStatement pw2 = connect.getConnection().prepareStatement(Queries.update_waitlist_number);
			pw2.setInt(1, ac.wait_number);
			pw2.setString(2, ac.course_id);
			pw2.executeUpdate();
			
			System.out.println("Waitlisted course: "+ac.course_id+" "+ac.cname+" dropped Successfully!");
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
	
	
	// Method to drop the enrolled course.
	public static void drop_enrolled(AvailableClasses ac, Scanner ip){
		try{
			
			// Execute the query to delete the enrollment.
			PreparedStatement pe1 = connect.getConnection().prepareStatement(Queries.drop_enrolled);
			int ss = StudentProfile.getInstance().getSid();
			pe1.setInt(1, ss);
			pe1.setString(2, ac.course_id);
			pe1.setString(3, ac.fname);
			pe1.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
		
		public static void enroll_waitlisted(AvailableClasses ac, Scanner ip){
			try{
			int ss = StudentProfile.getInstance().getSid();
				
			// Checking for any waitlisted students to be enrolled for the course.
			PreparedStatement pe2 = connect.getConnection().prepareStatement(Queries.get_course_waitlist);
			pe2.setString(1, ac.course_id);
			ResultSet re2 = pe2.executeQuery();
			int start = 0;
			while(re2.next()){
				
				// If there are students in the waitlisted course, check whether the credit limit is satisfied or not for the waitlisted student.
				AvailableClasses ac1 = new AvailableClasses();
				ac1.course_id = re2.getString("COURSE_ID");
				ac1.fname = re2.getString("FACULTY");
				ac1.wait_number = re2.getInt("WAITLIST_NUMBER");
				ac1.dropc = re2.getString("DROP_COURSE");
				ac1.sid = re2.getInt("STUDENT_ID");
				ac1.credit = re2.getInt("CREDITS");
				boolean check_credit_limit = CheckEligibility.check_credit_limit(0, ac1);
				if(check_credit_limit){
					// Enroll the waitlisted student for the course.
					PreparedStatement pe3 = connect.getConnection().prepareStatement(Queries.insert_in_enrollment);
					pe3.setInt(1, ac1.sid);
					pe3.setString(2, ac1.course_id);
					pe3.setString(3, ac1.fname);
					pe3.setString(4, "SPRING");
					pe3.setString(5, "F");
					pe3.setInt(6, ac1.credit);
					pe3.executeQuery();
					
					// Delete from waitlist
					PreparedStatement pe11 = connect.getConnection().prepareStatement(Queries.drop_waitlist);
					pe11.setInt(1, ac1.sid);
					pe11.setString(2, ac1.course_id);
					pe11.setString(3, ac1.fname);
					pe11.executeUpdate();
					
					// Decrement the waitlist number
					PreparedStatement pe1111 = connect.getConnection().prepareStatement(Queries.update_waitlist);
					pe1111.setString(1, ac1.course_id);
					pe1111.setString(2, ac1.fname);
					pe1111.setInt(3, ac1.wait_number);
					pe1111.executeUpdate();
					
					break;
				}
				else{
					// Credit limit is violated.
					// Check if the student has selected any course to drop or not.
					if(ac1.dropc == null){
						/*
						 * Keep the student in the waitlist table only as he/she has not selected any course and currently out of credit limit.
						 */
					}
					else{
						// Checking if the course to be dropped is present in the enrollment table or not for the current student.
						PreparedStatement pe4 = connect.getConnection().prepareStatement(Queries.get_drop_course);
						pe4.setInt(1, ac1.sid);
						pe4.setString(2, ac1.dropc);
						ResultSet re4 = pe4.executeQuery();
						if(re4.next()){
							// Drop the course given by the student and make him enroll in the current course.
							// Execute the query to delete the enrollment.
							PreparedStatement pe5 = connect.getConnection().prepareStatement(Queries.drop_enrolled2);
							pe5.setInt(1, StudentProfile.getInstance().getSid());
							pe5.setString(2, ac1.dropc);
							pe5.execute();
							
							
							// Enrolling the student from waitlist to enrollment table.
							PreparedStatement pe6 = connect.getConnection().prepareStatement(Queries.insert_in_enrollment);
							pe6.setInt(1, ac1.sid);
							pe6.setString(2, ac1.course_id);
							pe6.setString(3, ac1.fname);
							pe6.setString(4, "SPRING");
							pe6.setString(5, "F");
							pe6.setInt(6, ac1.credit);
							pe6.executeQuery();
							
							// Delete from waitlist
							PreparedStatement pe112 = connect.getConnection().prepareStatement(Queries.drop_waitlist);
							pe112.setInt(1, ac1.sid);
							pe112.setString(2, ac1.course_id);
							pe112.setString(3, ac1.fname);
							pe112.executeUpdate();
							
							// Decrement the waitlist number
							// Decrement the waitlist number
							PreparedStatement pe1111 = connect.getConnection().prepareStatement(Queries.update_waitlist);
							pe1111.setString(1, ac1.course_id);
							pe1111.setString(2, ac1.fname);
							pe1111.setInt(3, ac1.wait_number);
							pe1111.executeUpdate();
							
							break;
							
						}
						else{
							/*
							 * if the course to be dropped is not present in the enrollment table for the current student, then we move to the next student.
							 * */
						}
					}
				}
			}
			if(start == 0){
				System.out.println("The course: "+ac.course_id+" "+ac.cname+" is Successfully dropped!");
				DropCourse.drop_course(ip);
			}	
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
}

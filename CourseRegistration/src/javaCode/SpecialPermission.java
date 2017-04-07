package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import dbConnect.connect;

public class SpecialPermission {
	// Method to provide the admin options.
	public static void viewPendingMenu(Scanner ip){
		System.out.println("\n** View Pending/Approved/Rejected Special Requests **");
		System.out.println("Select an option:");
		System.out.println("0. Go back to previous menu.");
		System.out.println("1. View Pending Special Requests.");
		System.out.println("2. View Approved/Rejected Special Requests.");
		
		System.out.print("Your Choice: ");
		int choice = ip.nextInt();
		if(choice == 0){
			Login.admin_homepage(ip);
		}
		else if(choice == 1){
			approveRequests(ip);
		}
		else if(choice == 2){
			viewApprovedRejected(ip);
		}
		else{
			System.out.println("Please select a correct option");
			viewPendingMenu(ip);
		}
	}
	
	// Method to view pending special requests.
	public static void viewApprovedRejected(Scanner ip){
		try{
			PreparedStatement pv1 = connect.getConnection().prepareStatement(Queries.get_approvedreject_requests);
			ResultSet rv1 = pv1.executeQuery();
			ArrayList<AvailableClasses> cdata1 = new ArrayList<AvailableClasses>();
			while(rv1.next()){
				AvailableClasses ac1 = new AvailableClasses();
				
				PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.select_course_name);
				p1.setString(1, rv1.getString("COURSE_ID"));
				ResultSet r1 = p1.executeQuery();	
				if(r1.next()){
					ac1.cname = r1.getString("COURSE_NAME");
				}
				ac1.sem = rv1.getString("SEMESTER");
				ac1.course_id = rv1.getString("COURSE_ID");
				ac1.sid = rv1.getInt("STUDENT_ID");
				ac1.status = rv1.getString("APPROVAL_STATUS");
				ac1.admin = rv1.getString("ADMIN_NAME");
				ac1.ddate = rv1.getString("DATE_OF_APPROVAL");
				
				cdata1.add(ac1);
			}
			System.out.println("\nList of Approved/Rejected Requests: ");
			if(cdata1.size() == 0){
				System.out.println("No Requests at present.");
			}
			else{
				System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Student Id".format("%-13s", "Student Id")+"Course Id".format("%-15s", "CourseId")+
						"Course Name".format("%-50s", "Course Name")+"Semester".format("%-15s", "Semester")+"Status".format("%-20s", "Status")+
						"Approved By".format("%-30s", "Approved By")+"Date Of Approval".format("%-20s", "Date Of Approval"));
			
				for(int i = 0; i < cdata1.size(); i++){
					String is = Integer.toString(i + 1) + ".";
					System.out.println(is.format("%-8s", is)+Integer.toString(cdata1.get(i).sid).format("%-13s", Integer.toString(cdata1.get(i).sid))+
							cdata1.get(i).course_id.format("%-15s", cdata1.get(i).course_id)+cdata1.get(i).cname.format("%-50s", cdata1.get(i).cname)+
							cdata1.get(i).sem.format("%-15s", cdata1.get(i).sem)+cdata1.get(i).status.format("%-20s", cdata1.get(i).status)+
							cdata1.get(i).admin.format("%-30s", cdata1.get(i).admin)
							+cdata1.get(i).ddate.substring(0, 11).format("%-20s", cdata1.get(i).ddate).substring(0, 11));	
				
				}
				while(true){
					System.out.print("\nPress 0 to go back: ");
					int cc = ip.nextInt();
					if(cc == 0){
						viewPendingMenu(ip);
					}
					else{
						System.out.println("Please enter correct option.");
					}
				}
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
	
	// Method to view approved special requests.
	public static void approveRequests(Scanner ip){
		try{
			PreparedStatement pa1 = connect.getConnection().prepareStatement(Queries.get_pending_requests);
			ResultSet ra1 = pa1.executeQuery();
			ArrayList<AvailableClasses> cdata2 = new ArrayList<AvailableClasses>();
			while(ra1.next()){
				AvailableClasses ac2 = new AvailableClasses();
				
				PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.select_course_name);
				p1.setString(1, ra1.getString("COURSE_ID"));
				ResultSet r1 = p1.executeQuery();	
				if(r1.next()){
					ac2.cname = r1.getString("COURSE_NAME");
				}
				ac2.course_id = ra1.getString("COURSE_ID");
				ac2.sid = ra1.getInt("STUDENT_ID");
				ac2.status = ra1.getString("APPROVAL_STATUS");
				ac2.fname = ra1.getString("FACULTY");
				ac2.sem = ra1.getString("SEMESTER");
				ac2.credit = ra1.getInt("CREDITS");
				
				cdata2.add(ac2);
			}
			System.out.println("\nSelect the Special Request to Approve/Reject: ");
			if(cdata2.size() == 0){
				System.out.println("No Pending Requests at present.");
			}
			else{
				System.out.println("List of Pending Requests: ");
				System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Student Id".format("%-13s", "Student Id")+"Course Id".format("%-15s", "CourseId")+
						"Course Name".format("%-50s", "Course Name")+"Credits".format("%-10s", "Credits")+"Semester".format("%-15s", "Semester")+"Status".format("%-20s", "Status"));
			
				for(int i = 0; i < cdata2.size(); i++){
					String is = Integer.toString(i + 1) + ".";
					System.out.println(is.format("%-8s", is)+Integer.toString(cdata2.get(i).sid).format("%-13s", Integer.toString(cdata2.get(i).sid))+
							cdata2.get(i).course_id.format("%-15s", cdata2.get(i).course_id)+cdata2.get(i).cname.format("%-50s", cdata2.get(i).cname)+
							Integer.toString(cdata2.get(i).credit).format("%-10s",Integer.toString(cdata2.get(i).credit))+
							cdata2.get(i).sem.format("%-15s", cdata2.get(i).sem)+cdata2.get(i).status.format("%-20s", cdata2.get(i).status));	
				
				}
			}
			System.out.println("Press 0 to go back.");
			System.out.print("Your Choice: ");
			int choice = ip.nextInt();
			if(choice == 0){
				viewPendingMenu(ip);
			}
			else if(choice > 0 && choice <= cdata2.size()){
				
				System.out.println("Please select the decision for the slected Special request:\n1. Approve\n2. Reject.");
				System.out.println("Press 0 to go back.");
				System.out.print("Your Choice: ");
				int c1 = ip.nextInt();
				if(c1 == 0){
					viewPendingMenu(ip);
				}
				else if(c1 == 1){
					// Approve the request and move the student's request to enrollment.
					PreparedStatement pap1 = connect.getConnection().prepareStatement(Queries.update_approval_request);
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate localDate = LocalDate.now();
					String ddate = (String)dtf.format(localDate);
					pap1.setString(1, "Approved");
					pap1.setString(2, AdminProfile.getInstance().getFirstname() + " "+ AdminProfile.getInstance().getLastname());
					pap1.setString(3, ddate);
					pap1.setInt(4, cdata2.get(choice - 1).sid);
					pap1.setString(5, cdata2.get(choice - 1).course_id);
					pap1.setString(6, cdata2.get(choice - 1).sem);
					pap1.setString(7, cdata2.get(choice - 1).fname);
					pap1.executeUpdate();
					// Enroll the student in the course.
					boolean check_credit = CheckEligibility.check_credit_limit(1, cdata2.get(choice - 1));
					if(!check_credit){
						// Credit limit not met. Not able to enroll.
						viewPendingMenu(ip);
					}
					else{
						// Credit limit is maintained.
						// Checking if the class size is less than the capacity to enroll.
						PreparedStatement pcheck1 = connect.getConnection().prepareStatement(Queries.view_course_offerings1);
						pcheck1.setString(1, cdata2.get(choice - 1).course_id);
						ResultSet rcheck1 = pcheck1.executeQuery();
						if(rcheck1.next()){
							if(rcheck1.getInt("CLASS_SIZE") > rcheck1.getInt("NUMBER_OF_ENROLLED")){
								// Class available to enroll.
								EnrollDropCourses.enroll_class(0, cdata2.get(choice - 1).sid, cdata2.get(choice - 1).course_id,
										cdata2.get(choice - 1).fname, cdata2.get(choice - 1).sem, "F", cdata2.get(choice - 1).credit, ip);
								viewPendingMenu(ip);
							}
							
							// Check if the waitlist is available or not.
							else if(rcheck1.getInt("WAITLIST_SIZE") > rcheck1.getInt("WAITLISTED")){
								EnrollDropCourses.enroll_waitlist(0, cdata2.get(choice - 1).sid, cdata2.get(choice - 1).course_id,
										cdata2.get(choice - 1).fname, cdata2.get(choice - 1).sem, 
										rcheck1.getInt("WAITLISTED") + 1,"",cdata2.get(choice - 1).credit, ip);
							}
							else{
								// No action as waitlist and class size are full.
								viewPendingMenu(ip);
							}
						}
						else{
							// No action as no course found.
							viewPendingMenu(ip);
						}
					}
				}
				else if(c1 == 2){
					// Reject the request and don't move the student's request to enrollment.
					PreparedStatement pre1 = connect.getConnection().prepareStatement(Queries.update_approval_request);
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate localDate = LocalDate.now();
					String ddate = (String)dtf.format(localDate);
					pre1.setString(1, "Rejected");
					pre1.setString(2, AdminProfile.getInstance().getFirstname() +" "+ AdminProfile.getInstance().getLastname());
					pre1.setString(3, ddate);
					pre1.setInt(4, cdata2.get(choice - 1).sid);
					pre1.setString(5, cdata2.get(choice - 1).course_id);
					pre1.setString(6, cdata2.get(choice - 1).sem);
					pre1.setString(7, cdata2.get(choice - 1).fname);
					pre1.executeUpdate();
				}
				else{
					System.out.println("Please select a correct option.");
					approveRequests(ip);
				}
			}
			else{
				System.out.println("Please enter correct option.");
				approveRequests(ip);
			}

		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
}

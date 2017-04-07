package javaCode;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dbConnect.connect;
public class AdminView {

	// Method to enroll students
	public static void enrollStudent(Scanner ip){
		try{
			
			// Object to hold the results of the queries.
			//ResultSet rs;
			
			// Object to hold the query.
			PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.enroll_new_student);
			
			System.out.println("\n**Enroll a student.**");
			System.out.print("Enter StudentId: ");
			int sid = ip.nextInt();
			System.out.print("Enter Student's username: ");
			String username = ip.next();
			System.out.print("Enter Student's First Name: ");
			String firstname = ip.next();
			System.out.print("Enter Student's Last Name: ");
			String lastname = ip.next();
			System.out.print("Enter Student's email address: ");
			String email = ip.next();
			System.out.print("Enter phone number for a student: ");
			String phone = ip.next();
			System.out.print("Enter Department for student: ");
			String dept = ip.next();
			
			
			String level_class, residency_class;
			while(true){
				System.out.println("Enter Student's level(Select an option from below): \n 1. Graduate \n 2. Undergraduate");
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 1){
					level_class = "Graduate";
					break;
				}
				else if(choice == 2){
					level_class = "Undergraduate";
					break;
				}
				else{
					System.out.println("Please select correct option.");
				}
				
			}
			
			while(true){
				System.out.println("Enter Student's Residency status(Select an option from below): \n 1. In-state \n 2. Out-state \n 3. International");
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 1){
					residency_class = "In-State";
					break;
				}
				else if(choice == 2){
					residency_class = "Out-State";
					break;
				}
				else if(choice == 3){
					residency_class = "International";
					break;
				}
				else{
					System.out.println("Please select correct option.");
				}
			}
			
			System.out.print("Enter Student's Date of Birth(YYYY-MM-DD): ");
			String dob = ip.next();
     		System.out.println("Enter Amount owed if any:");
			int amt_owed = ip.nextInt();
			
			pstmt.setInt(1, sid);
			pstmt.setString(2, username);
			pstmt.setString(3, Integer.toString(sid));
			pstmt.setString(4, firstname);
			pstmt.setString(5, lastname);
			pstmt.setString(6, email);
			pstmt.setString(7, phone);
			pstmt.setString(8, dept);
			pstmt.setString(9, level_class);
			pstmt.setString(10, residency_class);
			pstmt.setFloat(11, (float) 0.0);
			pstmt.setString(12, dob);
			// Executing the insertion query.
			pstmt.execute();
			//connect.close(pstmt);
			// Checking if the insertion of new student is successful or not.
			ResultSet rs1;
			PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_login_student);
			pstmt1.setString(1, username);
			pstmt1.setString(2, Integer.toString(sid));
			rs1 = pstmt1.executeQuery();
			if(rs1.next()){
				//System.out.println("\n~~New Student created Successfully~~\n");
				
				connect.close(pstmt1);
				
				// Updating the student's credit limits after successful enrollment.
				ResultSet r22;
				PreparedStatement pstmt3 = connect.getConnection().prepareStatement(Queries.insert_credit_limit);
				pstmt3.setInt(1, sid);
				pstmt3.setInt(2, 0);
				pstmt3.setInt(3, 0);
				pstmt3.setInt(4, 0);
				pstmt3.setInt(5, 0);
				pstmt3.setInt(6, 0);
				pstmt3.executeQuery();
				connect.close(pstmt3);
				PreparedStatement pstmt4 = connect.getConnection().prepareStatement(Queries.select_credits_and_cost);
				pstmt4.setString(1, level_class);
				pstmt4.setString(2, residency_class);
				r22 = pstmt4.executeQuery();
				PreparedStatement pstmt6 = connect.getConnection().prepareStatement(Queries.insert_default_bill);
				pstmt6.setInt(1, sid);
				pstmt6.setInt(2, amt_owed);
				pstmt6.execute();
				if(r22.next()){
					PreparedStatement pstmt5 = connect.getConnection().prepareStatement(Queries.update_credit_limit_and_cost);
					pstmt5.setString(1, r22.getString("min_credits"));
					pstmt5.setString(2, r22.getString("max_credits"));
					pstmt5.setString(3, r22.getString("cost_per_credit"));
					pstmt5.setInt(4, sid);
					pstmt5.executeQuery();
					System.out.println("\n~~New Student created Successfully~~\n");
					connect.close(pstmt3);
					connect.close(pstmt4);
					connect.close(pstmt5);
					Login.admin_homepage(ip);
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
	
	// Method to view student details
	public static void viewStudentDetails(Scanner ip){
		try{
			while(true){
				System.out.println("\n**View Student Details**");
				System.out.print("Enter the Student ID:");
				int sid = ip.nextInt();
				
				// Object to hold the results of the queries.
				ResultSet r;
				
				// Object to hold the query.
				PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.get_student_details);
				pstmt.setInt(1, sid);
				// Execute the query.
				r = pstmt.executeQuery();
				
				if(r.next()){
					System.out.println("First Name: " + r.getString("First_Name"));
					System.out.println("Last Name: " + r.getString("Last_Name"));
					System.out.println("Date of Birth: " + r.getDate("Dateofbirth"));
					System.out.println("Student's level: " + r.getString("level_class"));
					System.out.println("Student's Residency Status: " + r.getString("residency_class"));
					System.out.println("Student's GPA: " + r.getFloat("GPA"));
					System.out.println("Student's phone: " + r.getString("phone"));
					System.out.println("Student's email id: " + r.getString("email"));
					connect.close(pstmt);
					System.out.print("Press 1 to update grades for this student. ");
					System.out.print("Press 0 to go back: ");
					int choice = ip.nextInt();
					if(choice == 0){
						Login.admin_homepage(ip);
					}
					else if(choice==1){
						enterGrades(ip,sid);
					}	
					else{
						System.out.println("Please enter 0 to go back.");
					}
				}
				else{
					System.out.println("Please enter correct Student Id.");
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
	
	// Method to view admin profile
	public static void viewProfile(Scanner ip){
		while(true){
			System.out.println("**Your Profile**");
			System.out.println("First Name: " + AdminProfile.getInstance().getFirstname());
			System.out.println("Last Name: " + AdminProfile.getInstance().getLastname());
			System.out.println("Date of Birth: " + AdminProfile.getInstance().getDob());
			System.out.println("Employee id: " + AdminProfile.getInstance().getEid());
			System.out.print("Press 0 to go back. ");
		
			int n = ip.nextInt();
			if(n == 0){
				Login.admin_homepage(ip);
			}
			else{
				System.out.println("Please enter 0 to go back.");
			}
		}
	}
	
	// Method to view/add courses
	public static void viewaddCourses(Scanner ip){
		while(true){
			System.out.println("\n**View/Add Courses**");
			System.out.println("Select appropriate option:");
			System.out.println("0. Go to previous menu.");
			System.out.println("1. View Courses.");
			System.out.println("2. Add a Course.");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			switch(choice){
			case 0:
				Login.admin_homepage(ip);
				break;
			case 1:
				Courses.viewCourse(ip);
				break;
			case 2:
				Courses.addCourse(ip);
				break;
			default:
				System.out.println("Please select a correct option.");
			}
		}
	}
	
	// Method to view/add Course offering
	public static void viewaddCourseOffering(Scanner ip){
		while(true){
			System.out.println("\n**View/Add Course Offering**");
			System.out.println("Select appropriate option:");
			System.out.println("0. Go to previous menu.");
			System.out.println("1. View Course Offering.");
			System.out.println("2. Add a Course Offering.");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			switch(choice){
			case 0:
				Login.admin_homepage(ip);
				break;
			case 1:
				Courses.viewCourseOffering(ip);
				break;
			case 2:
				Courses.addCourseOffering(ip);
				break;
			default:
				System.out.println("Please select a correct option.");
			}
		}	
	}
	
	public static void enterGrades(Scanner ip,int sid){
		
		try{
			while(true)
			{
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.view_enrolled_courses_currsem);
			p1.setInt(1,sid);
			ResultSet r1 = p1.executeQuery();
			int i = 0;
			List<EnrolledClasses> edata = new ArrayList<EnrolledClasses>();
			while(r1.next()){
				i += 1;
				EnrolledClasses ec = new EnrolledClasses();
				String cid = r1.getString("COURSE_ID");
				ec.lgrade=r1.getString("LETTER_GRADE");
				
				PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.select_course_name);
				PreparedStatement p3 = connect.getConnection().prepareStatement(Queries.select_course_semester);

				p2.setString(1, cid);
				//p3.setString(1, cid);
				p3.setInt(1, sid);


				ResultSet r2 = p2.executeQuery();
				ResultSet r3 = p3.executeQuery();

				if(r2.next()){
					ec.course_name = r2.getString("COURSE_NAME");
				}
				if(r3.next()){
					ec.sem = r3.getString("SEMESTER");
				}
				ec.cid = cid;		
				edata.add(ec);
				
				
				connect.close(p2);
				connect.close(p3);

				
			}
			
			
			if(i == 0){
				System.out.println("No enrolled courses found.");
				Login.admin_homepage(ip);
			}
			System.out.println("Student's currently enrolled courses : ");
			//System.out.println("Press 0 to go back.");

			System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId")+"Course Name".format("%-30s", "Course Name")+"Semester".format("%-30s", "Semester")+"Grade".format("%-30s", "Grade"));
			
			for(int k = 0; k < i; k++){
				String ks = Integer.toString(k + 1) + ".";
				System.out.println(ks.format("%-8s", ks) + edata.get(k).cid.format("%-15s", edata.get(k).cid) + edata.get(k).course_name.format("%-30s", edata.get(k).course_name) + edata.get(k).sem.format("%-30s", edata.get(k).sem)+ edata.get(k).lgrade.format("%-15s", edata.get(k).lgrade));
			}
			System.out.print("Enter index to enter grades (press 0 to go back) : ");
			int choice = ip.nextInt();
			if(choice == 0){
				Login.admin_homepage(ip);
			}
			else if(choice <= (edata.size()+1)){
				PreparedStatement pfetchvarcredits = connect.getConnection().prepareStatement(Queries.view_course);
				PreparedStatement pinsertgrade = connect.getConnection().prepareStatement(Queries.update_grade_enrollment);
				PreparedStatement pinsertgradecredit = connect.getConnection().prepareStatement(Queries.update_grade_credit_enrollment);
				CallableStatement proccall = connect.getConnection().prepareCall(Queries.proc_call_update_grade);
				//System.out.println(edata.get(choice-1).cid);
				pfetchvarcredits.setString(1, edata.get(choice-1).cid);
				ResultSet rvarcredit=pfetchvarcredits.executeQuery();
				String varcredindicator=null;
				
				System.out.println("Enter the letter grade for this course :");
				String grd=ip.next();
				pinsertgrade.setString(1, grd);
				pinsertgrade.setInt(2, sid);
				pinsertgrade.setString(3, edata.get(choice-1).cid);
				pinsertgrade.executeQuery();
				proccall.setInt(1, sid);
				proccall.executeUpdate();

			}
			else{
				System.out.println("Please select correct option.");
			}
			
			//check if course has variable credit
			//if not simply ask for grade
			//else ask for credit hours and grade
		
		connect.close(p1);
			}
	
} catch (SQLException e){
	e.printStackTrace();
}
catch (Exception e){
	System.out.println("Invalid values entered. Please enter correct values.");
	System.out.println(e.getMessage());
}
		
}
	public static void viewEnforceDeadline(Scanner ip){
		System.out.println("Are you sure you want to enforce deadlines ?");
		System.out.println("Press 0 to go back.");
		System.out.println("1. YES");
		System.out.println("2. NO");
		System.out.print("Choice:");
		int choice = ip.nextInt();
		switch(choice){
		case 0:
			Login.admin_homepage(ip);
			break;
		case 1:
			try{
				PreparedStatement p0 = connect.getConnection().prepareStatement(Queries.check_if_deadline_already_enforced);
				ResultSet r = p0.executeQuery();
			  if(r.next()){
				int status = r.getInt("STATUS");
				if (status == 1)
					{System.out.println("Deadline Already Enforced for this Semester");
					 connect.close(p0);
					 Login.admin_homepage(ip);
					}
				
				else if(status == 0){
				PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.set_deadline_enforced_true);
				p1.execute();
				PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.clear_waitlist_table);
				p2.execute();
				PreparedStatement p3 = connect.getConnection().prepareStatement(Queries.drop_enrolled_students_unpaid_fees);
				p3.execute();
				
				connect.close(p1);
				connect.close(p2);
				connect.close(p3);
				System.out.println("Deadline is enforced for Current Semester.");
				Login.admin_homepage(ip);
				
				}
			  }
			} catch (SQLException e){
				e.printStackTrace();
			}
			catch (Exception e){
				System.out.println("Invalid values entered. Please enter correct values.");
				System.out.println(e.getMessage());
			}
		
			
			break;
		case 2:
			System.out.println("Deadline Not Enforced.");
			Login.admin_homepage(ip);
			break;
		default:
			System.out.println("Please select a correct option.");
		}
	}
	
}

package javaCode;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dbConnect.connect;

public class Login {
	
	// Method to verify login and to identify the user as admin or student.
	public static int login_verify(Scanner ip){
		do{
			try{
				// Object to hold the results of the queries.
				ResultSet rs1, rs2;
				
				// Object to hold the query.
				PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_login_admin);
				PreparedStatement pstmt2 = connect.getConnection().prepareStatement(Queries.verify_login_student);
				
				System.out.print("Enter Username: ");
				String user = ip.next();
				System.out.print("Enter Password: ");
				String passwd = ip.next();
				
				// Setting the username and password parameters in the query.
				pstmt1.setString(1, user);
				pstmt1.setString(2, passwd);
				pstmt2.setString(1, user);
				pstmt2.setString(2, passwd);
				
				// Getting the results of the queries.
				rs1 = pstmt1.executeQuery();
				rs2 =  pstmt2.executeQuery();

				// Checking if the user is an admin or a student.
				// If the user is an admin.
				if(rs1.next()){
					System.out.println("Successful Admin Login!");
					// Setting up the session for admin.
					AdminProfile.getInstance().setupProfile(rs1);
					connect.close(pstmt1);
					connect.close(pstmt2);
					return 0;
				}
				// If the user is a student.
				else if(rs2.next()){
					System.out.println("Successful Student Login!");
					//Setting up the session for student.
					StudentProfile.getInstance().setupProfile(rs2);
					connect.close(pstmt1);
					connect.close(pstmt2);
					return 1;
				}
				// If the user has entered invalid credentials.
				else{
					System.out.println("Please enter correct values.");
				}
				
			} catch (SQLException e){
				e.printStackTrace();
			}
			catch (Exception e){
				System.out.println("Invalid values entered. Please enter correct values.");
				System.out.println(e.getMessage());
			}	
		}while(true);
	}
	
	
	// Method to display the initial menu options to the user.
	public static void startPage(Scanner ip) throws NoSuchElementException{
		System.out.println("\n*****Course Registration System*****");

		while (true) {
			System.out.println("1. Admin/Student Login");
			System.out.println("2. Exit");
			System.out.print("Choice : ");
			int choice = ip.nextInt();
			switch (choice){
			case 1:
				int status = login_verify(ip);
				if (status == 0){
					admin_homepage(ip);
				}
				else
					student_homepage(ip);
				break;
				
			// Closing the connection			
			case 2:
				connect.closeConnection();
				connect.closeStatement();
				ip.close();
				System.exit(0);
			
			default: System.out.println("Please enter correct choice.");
				
			}
		}
	}
	
	// Method to display options for an admin after successful login.
	public static void admin_homepage(Scanner ip){
		System.out.println("\n**Hello admin.**");
		
		while(true){
			System.out.println("1. View Profile");
			System.out.println("2. Enroll New Student");
			System.out.println("3. View Student's Detail");
			System.out.println("4. View/Add Courses");
			System.out.println("5. View/Add Course Offering");
			System.out.println("6. View/Approve Special Enrollment Requests");
			System.out.println("7. Enforce Add/Drop Deadline");
			System.out.println("8. Logout");
			System.out.print("Choice: ");
			
			int choice = ip.nextInt();
			switch(choice){
			case 1:
				AdminView.viewProfile(ip);
				break;
			case 2:
				AdminView.enrollStudent(ip);
				break;
			case 3:
				AdminView.viewStudentDetails(ip);
				break;
			case 4:
				AdminView.viewaddCourses(ip);
				break;
			case 5:
				AdminView.viewaddCourseOffering(ip);
				break;
			case 6:
				SpecialPermission.viewPendingMenu(ip);
				break;
			case 7:
				////
				AdminView.viewEnforceDeadline(ip);
				break;
			case 8:
				AdminProfile.deleteInstance();
				startPage(ip);
				break;
			default:
				System.out.println("Please enter correct choice.");
			}
		}
		
	}
	
	// Method to display options for a student after successful login.
	public static void student_homepage(Scanner ip){
		System.out.println("\n**Hello " + StudentProfile.getInstance().getFirstname() + "**");
		while(true){
			try{
				PreparedStatement p0 = connect.getConnection().prepareStatement(Queries.check_if_deadline_already_enforced);
				PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.check_if_below_min_credit_limit);

				ResultSet r0 = p0.executeQuery();
				p1.setInt(1,StudentProfile.getInstance().getSid() );
				ResultSet r1 = p1.executeQuery();
			    if(r0.next() && r1.next()){
				int status = r0.getInt("STATUS");
				int min_credit = r1.getInt("MIN_CREDIT");
				int curr_credit = r1.getInt("CURRENT_CREDIT");
				if (status == 1 && (min_credit > curr_credit))
					{
					 System.out.println();
					 System.out.println("**** ALERT ****");
					 System.out.println("****You do not meet Minimum Credit Requirements for this semester.****");
					 System.out.println();
					 connect.close(p0);
					 connect.close(p1);
					}
				else {
					connect.close(p0);
					connect.close(p1);
				  }
				}
				
			    
			}catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
			System.out.println("1. View/Edit Profile");
			System.out.println("2. View/Enroll/Drop Courses");
			System.out.println("3. View Pending Courses");
			System.out.println("4. View Grades");
			System.out.println("5. View/Pay Bills");
			System.out.println("6. Logout");
			
			System.out.print("Choice: ");
			
			int choice = ip.nextInt();
			switch(choice){
			case 1:
				StudentView.viewProfile(ip);
				break;
			case 2:
				StudentView.viewenrollCourses(ip);
				break;
			case 3:
				StudentView.viewPendingCourses(ip);
				break;
			case 4:
				StudentView.viewGrades(ip);
				break;
			case 5:
				StudentView.viewPayBills(ip);
				break;
			case 6:
				StudentProfile.deleteInstance();
				startPage(ip);
				break;
			default:
				System.out.println("Please enter correct choice.");
			}
		}	
		
		
	}
}

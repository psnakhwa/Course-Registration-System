package javaCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dbConnect.connect;

public class StudentView {
	
	
	// Method to view/edit student profile.
	public static void viewProfile(Scanner ip){
		while(true){
			System.out.println("\n**Your Profile**");
			System.out.println("First Name: " + StudentProfile.getInstance().getFirstname());
			System.out.println("Last Name: " + StudentProfile.getInstance().getLastname());
			System.out.println("Date of Birth: " + StudentProfile.getInstance().getDob());
			System.out.println("Email id: " + StudentProfile.getInstance().getEmail());
			System.out.println("Phone number: " + StudentProfile.getInstance().getPhone());
			System.out.println("Level: " + StudentProfile.getInstance().getLevelclass());
			System.out.println("Status: " + StudentProfile.getInstance().getResidencyclass());
			System.out.println("~~Select an option~~");
			System.out.print("0. Go back to previous menu.\n");
			System.out.println("1. Edit profile.");
			System.out.print("Your choice: ");
			int n = ip.nextInt();
			if(n == 0){
				Login.student_homepage(ip);
			}
			else if(n == 1){
				editProfile(ip);
			}
			else{
				System.out.println("Please enter 0 to go back.");
			}
		}
	}
	
	// Method to edit student profile.
	public static void editProfile(Scanner ip){
		while(true){
			System.out.println("\n**Edit Your Profile**");
			System.out.println("Select appropriate option.");
			System.out.println("0. Go back to previous menu.");
			System.out.println("1. Edit First Name.");
			System.out.println("2. Edit Last Name.");
			System.out.println("3. Edit Phone.");
			System.out.println("4. Edit Email id.");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			switch(choice){
			case 0:
				viewProfile(ip);
				break;
			case 1:
				try{
					System.out.print("Enter new First Name: ");
					String firstname = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_firstname);
					pstmt.setString(1, firstname);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_firstname);
					pstmt1.setString(1, firstname);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("First name has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid first name.");
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
				
			case 2:
				try{
					System.out.print("Enter new Last Name: ");
					String lastname = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_lastname);
					pstmt.setString(1, lastname);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_lastname);
					pstmt1.setString(1, lastname);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("Last name has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid last name.");
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
			
			case 3:
				try{
					System.out.print("Enter new Phone Number: ");
					String phone = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_phone);
					pstmt.setString(1, phone);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_phone);
					pstmt1.setString(1, phone);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("Phone number has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid phone number.");
						System.out.println();
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
			
			case 4:
				try{
					System.out.print("Enter new Email: ");
					String email = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_email);
					pstmt.setString(1, email);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_email);
					pstmt1.setString(1, email);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("Email has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid email.");
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
				
			default:
				System.out.println("Please select correct option.");
			}
		}
	}
	
	// Method to view/enroll courses.
	public static void viewenrollCourses(Scanner ip){
		while(true){
			System.out.println("\n**View/Enroll/Drop Courses**");
			System.out.println("Select an option: ");
			System.out.println("0. To go back to previous Menu");
			System.out.println("1. View / Enroll Courses.");
			System.out.println("2. Drop a Course.");
			System.out.println("3. View my courses.");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			if(choice == 0){
				Login.student_homepage(ip);
			}
			else if(choice == 1){
				try{
//					PreparedStatement pp1 = connect.getConnection().prepareStatement("SELECT * FROM STUDENTBILL WHERE STUDENT_ID = ?");
//					pp1.setInt(1, StudentProfile.getInstance().getSid());
//					ResultSet rspp1 = pp1.executeQuery();
//					
//					if(rspp1.next()){
//						if(rspp1.getInt("TOTAL_AMOUNT") - rspp1.getInt("AMOUNT_PAID") > 0){
//							System.out.println("Bill pending");
//							EnrollDropCourses.enrollCourses(ip);
//						}
//					}
					PreparedStatement p0 = connect.getConnection().prepareStatement(Queries.check_if_deadline_already_enforced);
					ResultSet r = p0.executeQuery();
				    if(r.next()){
					int status = r.getInt("STATUS");
					if (status == 1)
						{System.out.println("Deadline Already Enforced for this Semester, Can't Enroll now.");
						 connect.close(p0);
						 viewenrollCourses(ip);
						}
					else if (status == 0){
						connect.close(p0);
						EnrollDropCourses.enrollCourses(ip);
					}
					
				  }
				}catch (SQLException e){
						e.printStackTrace();
					}
					catch (Exception e){
						System.out.println("Invalid values entered. Please enter correct values.");
						System.out.println(e.getMessage());
					}
				
			}
			else if(choice == 2){
				try{
					
					
					
					PreparedStatement p0 = connect.getConnection().prepareStatement(Queries.check_if_deadline_already_enforced);
					ResultSet r = p0.executeQuery();
				    if(r.next()){
					int status = r.getInt("STATUS");
					if (status == 1)
						{System.out.println("Deadline Already Enforced for this Semester, Can't Drop now.");
						 connect.close(p0);
						 viewenrollCourses(ip);
						}
					else if (status == 0){
						connect.close(p0);
						DropCourse.drop_course(ip);
					}
					
				  }
				}catch (SQLException e){
						e.printStackTrace();
					}
					catch (Exception e){
						System.out.println("Invalid values entered. Please enter correct values.");
						System.out.println(e.getMessage());
					}
			}
			else if(choice == 3){
				EnrollDropCourses.viewMyCourses(ip);
			}
			else{
				System.out.println("Please select correct option.");
			}
		}
	}
	
	// Method to view Pending courses
	public static void viewPendingCourses(Scanner ip){
		while(true){
			System.out.println("\n**View Courses and Status**");
			System.out.print("Press 0 to go back to previous Menu: ");
			try{
				System.out.println("\n** Courses Status **");
				//All the special permission courses
				PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.special_permission_course_status);
				p1.setInt(1, StudentProfile.getInstance().getSid());
				ResultSet r1 = p1.executeQuery();
					
				ArrayList<SpecialPermissionCourses> spdata = new ArrayList<SpecialPermissionCourses>();
				while(r1.next()){
					SpecialPermissionCourses spc = new SpecialPermissionCourses();
				    //Getting course name
					PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.select_course_name);						p2.setString(1, r1.getString("COURSE_ID"));
					ResultSet r2 = p2.executeQuery();	
					if(r2.next()){
						spc.cname = r2.getString("COURSE_NAME");
					}
					spc.course_id = r1.getString("COURSE_ID");
					spc.sem = r1.getString("SEMESTER");
					spc.fname = r1.getString("FACULTY");
					spc.status = r1.getString("APPROVAL_STATUS");
					spdata.add(spc);
					}
				if(spdata.size()>0){
					System.out.println("List of Pending courses: ");
					System.out.println("Course Id".format("%-15s", "CourseId")+"Course Name".format("%-50s", "Course Name")+
							"Faculty".format("%-30s", "Faculty"));
				    
					for(int i = 0; i < spdata.size(); i++){
						if(spdata.get(i).status.compareTo("Pending")==0){
							System.out.println(spdata.get(i).course_id.format("%-15s", spdata.get(i).course_id)+
									spdata.get(i).cname.format("%-50s", spdata.get(i).cname)+spdata.get(i).fname.format("%-30s", spdata.get(i).fname));	
						}	
					}
				}
				if(spdata.size()>0){
					 System.out.println("List of Rejected courses: ");
					 System.out.println("Course Id".format("%-15s", "CourseId")+"Course Name".format("%-50s", "Course Name")+
							 "Faculty".format("%-30s", "Faculty"));
				    
					 for(int i = 0; i < spdata.size(); i++){
						 if(spdata.get(i).status.compareTo("Rejected")==0){
							 System.out.println(spdata.get(i).course_id.format("%-15s", spdata.get(i).course_id)+ spdata.get(i).cname.format("%-50s", spdata.get(i).cname)+spdata.get(i).fname.format("%-30s", spdata.get(i).fname));	
						 }	
					 }
				}
				//All Wait listed courses
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
					ac.wait_number = r3.getInt("WAITLIST_NUMBER");
					cdata1.add(ac);
				}
			if(cdata1.size() > 0)
				System.out.println("List of Waitlisted course: ");
				for(int t = 0; t < cdata1.size(); t++){
					String wn=Integer.toString(cdata1.get(t).wait_number);
					System.out.println(cdata1.get(t).course_id.format("%-15s", cdata1.get(t).course_id)+
							cdata1.get(t).cname.format("%-50s", cdata1.get(t).cname)+(wn.format("%-50s",wn)));	
				}
				
				
					while(true){
						System.out.print("Your choice: ");
						int choice = ip.nextInt();
						if(choice == 0){
							Login.student_homepage(ip);
					}
				}
			}  catch (SQLException e){
				e.printStackTrace();
			}
			catch (Exception e){
				System.out.println("Invalid values entered. Please enter correct values.");
				System.out.println(e.getMessage());
			}
		
		}
			
	}
	
	// Method to display the grades of student.
	public static void viewGrades(Scanner ip){
		while(true){
			System.out.println("\n**View Grades/GPA**");
			System.out.println("Select an appropriate option: ");
			System.out.println("0. Go back to previous Menu.");
			System.out.println("1. Display Letter Grades.");
			System.out.println("2. Display GPA");
			System.out.print("Your Choice: ");
			int choice = ip.nextInt();
			if(choice == 0){
				Login.student_homepage(ip);
			}
			else if(choice == 1){
				
				try{
					
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view_grade);
					pstmt.setInt(1, StudentProfile.getInstance().getSid());
					r = pstmt.executeQuery();
					List<GradeReport> gdata = new ArrayList<GradeReport>();
					int i = 0;
					while(r.next()){
						i += 1;
						GradeReport gr = new GradeReport();
						String cid = r.getString("COURSE_ID");
						
						gr.cid = cid;
						
						PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.select_course_name);
						p2.setString(1, cid);
						ResultSet r2 = p2.executeQuery();
						if(r2.next()){
							gr.course_name = r2.getString("COURSE_NAME");
						}
						
						gr.grade = r.getString("LETTER_GRADE");
						gr.sem = r.getString("SEMESTER");
						
								
						gdata.add(gr);	
						
						connect.close(p2);
						
					}
					
					
					if(i == 0){
						System.out.println("No grades found.");
						Login.student_homepage(ip);
					}
					System.out.println("My Grade Report : ");
					//System.out.println("Press 0 to go back.");

					System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId")+"Course Name".format("%-40s", "Course Name")+"Semester".format("%-20s", "Semester")+"Grade".format("%-10s","Grade"));
					
					for(int k = 0; k < i; k++){
						String ks = Integer.toString(k + 1) + ".";
						System.out.println(ks.format("%-8s", ks) + gdata.get(k).cid.format("%-15s", gdata.get(k).cid) + gdata.get(k).course_name.format("%-40s", gdata.get(k).course_name) + gdata.get(k).sem.format("%-20s", gdata.get(k).sem) + gdata.get(k).grade.format("%-10s", gdata.get(k).grade));
					}
					
				
				connect.close(pstmt);
				//Login.student_homepage(ip);
				viewGrades(ip);
					
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
				
			}
			else if(choice == 2){
				try{
					System.out.println("Your GPA is : ");
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view_gpa);
					pstmt.setInt(1 , StudentProfile.getInstance().getSid());
					r = pstmt.executeQuery();
					if(r.next()){
						System.out.println(r.getString("GPA"));
					}
					connect.close(pstmt);
					//Login.student_homepage(ip);
					viewGrades(ip);
					
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
			}
			else{
				System.out.println("Please select a correct option");
			}
		}
	}
	
	// Method to view/pay Bills.
	public static void viewPayBills(Scanner ip){
		while(true){
			System.out.println("\n**View/Pay Letter Bills**");
			System.out.println("Select an appropriate option: ");
			System.out.println("0. Go back to previous Menu.");
			System.out.println("1. Display Student's Balance.");
			System.out.println("2. Pay Bills.");
			System.out.print("Your Choice: ");
			int choice = ip.nextInt();
			if(choice == 0){
				Login.student_homepage(ip);
			}
			else if(choice == 1){
				//Displaying the student bill
				try{
					System.out.println("Your Total Bill is : ");
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view_total_bill);
					pstmt.setInt(1 , StudentProfile.getInstance().getSid());
					r = pstmt.executeQuery();
					if(r.next()){
						System.out.println(r.getString("TOTAL_AMOUNT-AMOUNT_PAID"));
					}
					connect.close(pstmt);
					viewPayBills(ip);
					
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
				
			}
			else if(choice == 2){
				//method involving bill module
				try{
					System.out.println("Enter the amount you want to pay : ");
					float amt = ip.nextFloat();
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.pay_bill);
					pstmt.setFloat(1 , amt);
					pstmt.setInt(2 , StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					System.out.println("Bill Pay Successful. Amount Paid is "+amt);
					connect.close(pstmt);
					viewPayBills(ip);
					
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
			}
			else{
				System.out.println("Please select a correct option");
			}
		}
	}
}

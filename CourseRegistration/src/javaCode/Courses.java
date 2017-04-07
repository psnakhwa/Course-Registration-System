package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import dbConnect.connect;

public class Courses {
	
	public static void viewCourse(Scanner ip){
        try{

            while(true){
                System.out.println("\n**View Course Details**");
                System.out.println("Enter 0 to go to main menu");
                System.out.print("Enter the Course ID:");
                String cid = ip.next();
           
                
                if(cid.equals(Integer.toString(0)))
                {

                    
                    Login.admin_homepage(ip);

             
                }
                else
                {
                ResultSet r;
                
                
                PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view_course);
                pstmt.setString(1, cid);
                // Execute the query.
                r = pstmt.executeQuery();
                
                if(r.next()){
                    System.out.println("Course name: " + r.getString("course_name"));
                    System.out.println("Department name: " + r.getString("dept_name"));
                    System.out.println("Class level: " + r.getString("level_class"));
                    System.out.println("GPA requirement: " + r.getFloat("gpa_req"));
                    System.out.println("List of prerequisites: ");
                    String prereq=r.getString("pre_req_courses");
                    
                    PreparedStatement pstmt_getprereqs = connect.getConnection().prepareStatement(Queries.get_prereqs);
                    
                    pstmt_getprereqs.setString(1, cid);
                    ResultSet prereqs=pstmt_getprereqs.executeQuery();
                    if(prereq.equals("Yes"))
                    {
                        int i=1;
                        while(prereqs.next())
                        {
                            System.out.printf("Prerequiste %d : %s\n",i,prereqs.getString("prerequisite_id"));
                            i++;
                        }
                        
                    }
                    else
                        System.out.println("Prerequisites : No prerequisites required.");

                    
                    
                    System.out.println("Special approval required: " + r.getString("spcl_approval_req"));
                    System.out.println("Number of credits: " + r.getInt("credits"));
                    connect.close(pstmt);
                    System.out.print("Press 0 to go back: ");
                    int choice = ip.nextInt();
                    if(choice == 0){
                        Login.admin_homepage(ip);
                    }
                    else{
                        System.out.println("Please enter 0 to go back.");
                    }
                }
                else{
                    System.out.println("Please enter correct Course Id.");
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
    
    // Method to add a course.
    public static void addCourse(Scanner ip){
        try{
            
            // Object to hold the results of the queries.
            ResultSet rs;
            
            // Object to hold the query.
            PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.add_new_course);
            
            System.out.println("\n*Add a course.**");
            System.out.print("Enter CourseID: ");
            String cid = ip.next();
            ip.nextLine();
            System.out.print("Enter Course name: ");
            
            String cname = ip.nextLine();
            System.out.print("Enter Department Name: ");
            String dname = ip.next();
            
            String level_class;
            while(true){
                System.out.println("Enter Course level(Select an option from below): \n 1. Graduate \n 2. Undergraduate");
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
            
            
            
            System.out.print("Enter GPA requirement (Enter 0 if no requirement): ");
            Float gpaReq = ip.nextFloat();
            
            System.out.print("Number of prerequisutes : ");
            int prereqcount = ip.nextInt();
            
            String preqreCourses;
            if(prereqcount>0)
            {
                preqreCourses="Yes";
                PreparedStatement pstmt_prereq = connect.getConnection().prepareStatement(Queries.add_new_prereq);
                PreparedStatement pstmt_getgrade = connect.getConnection().prepareStatement(Queries.get_grade);
                ResultSet rgrade;
                
                
              
                for(int i=1;i<=prereqcount;i++)
                {
                    System.out.printf("Enter prerequisite %d CourseID : ",i);
                    String prereq=ip.next();
                    System.out.print("Enter the given prerequisite's letter grade requirement : ");
                    String gradereq=ip.next();
                    
                    pstmt_getgrade.setString(1, gradereq);
                    rgrade=pstmt_getgrade.executeQuery();
                    pstmt_prereq.setString(1,cid);
                    pstmt_prereq.setString(2, prereq);
                    pstmt_prereq.setString(3, gradereq);
                    if(rgrade.next())
                    pstmt_prereq.setFloat(4, rgrade.getFloat("number_grade"));
                    
                    pstmt_prereq.execute();
                    
                    
                }
            }
            else
                preqreCourses="No";
            
      
            
            String specialPermission;
            while(true){
                System.out.println("State if special persmission is required (Select an option from below): \n 1. Yes \n 2. No");
                System.out.print("Your choice: ");
                int choice = ip.nextInt();
                if(choice == 1){
                    specialPermission = "Yes";
                    break;
                }
                else if(choice == 2){
                    specialPermission = "No";
                    break;
                }
                else{
                    System.out.println("Please select correct option.");
                }
            }
            
            int credits = 0;
            String credit = "Yes";
            while(true){
            	System.out.println("Does this course have variable credit (Select an option from below): \n 1. Yes \n 2. No");
            	System.out.print("Your choice: ");
            	int varcredit = ip.nextInt();
            	if(varcredit == 1){
            		System.out.print("Enter minimum credit limit for this course: ");
            		int mincredit = ip.nextInt();
            		System.out.println("Enter maximum credit limit for this course: ");
            		int maxcredit = ip.nextInt();
            		
            		PreparedStatement p = connect.getConnection().prepareStatement(Queries.insert_variable_credit);
            		p.setString(1, cid);
            		p.setInt(2, mincredit);
            		p.setInt(3, maxcredit);
            		p.executeQuery();
            		
            		credit = "Yes";
            		break;
            	}
            	else if(varcredit == 2){
            		System.out.print("Number of credits: ");
            		credits = ip.nextInt();
            		credit = "No";
            		break;
            	}
            	else{
            		System.out.println("Please select a correct option.");
            	}
            }
            
            
            pstmt.setString(1, cid);
            pstmt.setString(2, cname);
            pstmt.setString(3, dname);
            pstmt.setString(4, level_class);
            pstmt.setFloat(9, gpaReq);
            pstmt.setString(5, preqreCourses);
            pstmt.setString(6, specialPermission);
            pstmt.setString(7, credit);
            pstmt.setInt(8, credits);
            
            
            // Executing the insertion query.
            rs = pstmt.executeQuery();

            // Checking if the insertion of new course is successful or not.
            ResultSet rs1;
            PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.view_course);
            pstmt1.setString(1, cid);
            rs1 = pstmt1.executeQuery();
            if(rs1.next()){
                System.out.println("\n~~New Course successfully added!~~\n");
                connect.close(pstmt);
                connect.close(pstmt1);
                AdminView.viewaddCourses(ip);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println("Invalid values entered. Please enter correct values.");
            System.out.println(e.getMessage());
        }    
        
    }
    

	// Method to view a Course Offering.
	public static void viewCourseOffering(Scanner ip){
		System.out.println("\n**View a Course Offering**");
		
		try{
				System.out.println();
				ResultSet r;
				PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view_course_offerings);

				System.out.print("Enter the Course ID:");
				String cid = ip.next().toUpperCase();
				System.out.println();
				pstmt.setString(1, cid);
				// Execute the query.
				r = pstmt.executeQuery();
				
				if (!r.next()) {
					System.out.println("No Course Offerings found.");
				} else {
					
				do{
					System.out.println("Course Id: " + r.getString("COURSE_ID"));
					System.out.println("Faculty Name: " + r.getString("FACULTY_NAME"));
					System.out.println("Semester: " + r.getString("SEMESTER"));
					System.out.println("Days: " + r.getString("DAYS_OF_WEEK"));
					System.out.println("Start Time: " + r.getString("START_TIME").substring(2));
       				System.out.println("End Time: " + r.getString("END_TIME").substring(2));
					System.out.println("Class Size: " + r.getInt("CLASS_SIZE"));
					System.out.println("Number of students enrolled: " + r.getInt("NUMBER_OF_ENROLLED"));
					System.out.println("Waitlist Size: " + r.getInt("WAITLIST_SIZE"));

					System.out.println();
					System.out.println();
				}while(r.next());
			  }
				connect.close(pstmt);
				AdminView.viewaddCourseOffering(ip);
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
		
	}
	
	// Method to add a Course Offering
	public static void addCourseOffering(Scanner ip){
		System.out.println("\n**Add a Course Offering**");
		
		try{
			
			ResultSet rs;
			PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.add_course_offerings);
			
			String cid;
			
			ResultSet rs2;
			PreparedStatement pstmt2 = connect.getConnection().prepareStatement(Queries.verify_course_for_course_offering);
			System.out.print("Enter Course Id: ");
			cid = ip.next().toUpperCase();
			pstmt2.setString(1,cid);
			rs2 = pstmt2.executeQuery();
			if (!rs2.next()){
				System.out.println("\n~~Course ID entered is not valid~~\n");
				connect.close(pstmt2);
				addCourseOffering(ip);
			}
			else {
			connect.close(pstmt2);
			}
			System.out.print("Enter Faculty Name for "+cid+" :");
//			String firstName = ip.nextLine().toUpperCase();
//			String lastName = ip.next().toUpperCase();
//			String facultyName = firstName+" "+lastName;
//			System.out.println();
			ip.nextLine();
			
			String facultyName = ip.nextLine().toUpperCase();
			System.out.print("Enter Semester for "+cid+" :");
			String semester;
			
			while(true){
				System.out.println("Enter Semester(Select from below): \n 1. FALL \n 2. SPRING");
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 1){
					semester = "FALL";
					break;
				}
				else if(choice == 2){
					semester = "SPRING";
					break;
				}
				else{
					System.out.println("Please select correct option.");
				}
				
			}
			
			System.out.print("Enter Days of week for this course comma separated (MON,TUE,WED,THU,FRI) for "+cid+": ");
			String days = ip.next().toUpperCase();
			System.out.print("Enter course offering's start time (HH:MM:SS) for "+cid+": ");
			String start = ip.next();
			String start_time = "0 "+start;
			System.out.print("Enter course offering's end time (HH:MM:SS) for "+cid+": ");
			String end = ip.next();
			String end_time = "0 "+end;
			System.out.print("Enter class size for "+cid+" :");
			int class_size = ip.nextInt();
			System.out.print("Enter waitlist size for "+cid+" :");
			int waitlist_size = ip.nextInt();
			System.out.println("Confirm Adding the Course Offering for "+cid+" : \n 1: YES \n 2: No");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			if (choice == 2){
				AdminView.viewaddCourseOffering(ip);
			}
			else if (choice == 1) {
			
			pstmt.setString(1, cid);
			pstmt.setString(2, facultyName);
			pstmt.setString(3, semester);
			pstmt.setString(4, days);
			pstmt.setString(5, start_time);
			pstmt.setString(6, end_time);
			pstmt.setInt(7, class_size);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, waitlist_size);
			pstmt.setInt(10, 0);
			
			// Executing the insertion query.
			rs = pstmt.executeQuery();
			}
			// Checking if the insertion of new course offering is successful or not.
			ResultSet rs1;
			PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_course_offering);
			pstmt1.setString(1, cid);
			pstmt1.setString(2, facultyName);
			rs1 = pstmt1.executeQuery();
			if(rs1.next()){
				System.out.println("\n~~New Course Offering created Successfully~~\n");
				connect.close(pstmt);
				connect.close(pstmt1);
				AdminView.viewaddCourseOffering(ip);
			}
			else {System.out.println("Error");}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}	
	}
}
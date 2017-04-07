package javaCode;

public class AvailableClasses {
	public String course_id;
	public String cname;
	public String fname;
	public String sem;
	public String days;
	public String start_time;
	public String end_time;
	public int class_size;
	public int num_enrolled;
	public int waitlist_size;
	public int waitlisted;
	public String rawstarttime;
	public String rawendtime;
	public String dropc;
	public int wait_number;
	public int sid;
	public String status;
	public String admin;
	public String ddate;
	public String var_credit;
	public int credit;
	
	public void set_start_time(String stime){
		
		String s12, s3, s4,s;
		rawstarttime=stime;
		s = "";
		rawstarttime=stime;
		s12 = stime.substring(0,2);
		s3 = stime.substring(3,4);
		s4 = stime.substring(4);
		if(s4.equals(":"))
			 s4 = "0";
		if(Integer.parseInt(s12) > 12){
			s = Integer.toString(Integer.parseInt(s12) - 12)+":"+s3+s4+" p.m";
		}
		else if(Integer.parseInt(s12) == 12){
			s = "12"+":"+s3+s4+" p.m";
		}
		else{
			s = s12+":"+s3+s4+" a.m";
		}
		
		this.start_time = s;
	}
	
	public void set_end_time(String etime){
		
		String s12, s3, s4,s;
		rawendtime=etime;
		s = "";
		rawendtime=etime;
		s12 = etime.substring(0,2);
		s3 = etime.substring(3,4);
		s4 = etime.substring(4);
		if(s4.equals(":"))
			 s4 = "0";
		if(Integer.parseInt(s12) > 12){
			s = Integer.toString(Integer.parseInt(s12) - 12)+":"+s3+s4+" p.m";
		}
		else if(Integer.parseInt(s12) == 12){
			s = "12"+":"+s3+s4+" p.m";
		}
		else{
			s = s12+":"+s3+s4+" a.m";
		}
		
		this.end_time = s;
	}
	
	
}

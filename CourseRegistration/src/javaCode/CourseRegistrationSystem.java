package javaCode;

import java.sql.*;
import java.util.*;

import dbConnect.connect;

public class CourseRegistrationSystem {

	public static void main(String[] args) throws SQLException{
		Scanner ip = new Scanner(System.in);
		connect.getConnection();
		Login.startPage(ip);
	}
}
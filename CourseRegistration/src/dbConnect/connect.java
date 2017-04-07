package dbConnect;

import java.sql.*;
import java.util.*;

public class connect {

	static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
	static Connection conn = null;
	static Statement stmt = null;
	
	public static Connection getConnection() {
		if (conn == null) {

			try {
				
				// Loading and registering the driver.
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				Scanner sc = new Scanner(System.in);
				
				// Signing in the oracle database account.
				System.out.println("Enter Database Username - (UnityId)");
				String username = sc.next();
				System.out.println("Enter Database Password - (StudentId)");
				String password = sc.next();
				
				// Establishing a connection to the database.
				conn = DriverManager.getConnection(jdbcURL, username, password);
				
				
				stmt = conn.createStatement();
				// Displaying Success Message
				System.out.println("Connection Successfull\n\n\n\n");

			} catch (Throwable oops) {
				System.out.println(oops.getMessage());
				getConnection();
			}

		}
		return conn;
	}

	public static Statement getStatement() {
		if (stmt == null) {
			getConnection();
		}
		return stmt;
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable whatever) {
			}
		}
	}

	public static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable whatever) {
			}
		}
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable whatever) {
			}
		}
	}

	public static void closeStatement() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Throwable whatever) {
			}
		}
	}
}

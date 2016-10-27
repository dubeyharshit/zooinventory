package zootest.zooinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Class to establish connection with mysql
 */
public class DbConnection {
	private static Connection con = null;
	private final String DB_USER_NAME = "root";
	private final String DB_PASSWORD = "password";
	private final String MYSQL_DATABASE_NAME = "zooinventory";

	protected DbConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:MySQL://localhost:3306/" + MYSQL_DATABASE_NAME, DB_USER_NAME, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static Connection getInstance() {
		if (con == null) {
			new DbConnection();
		}
		return con;
	}
}

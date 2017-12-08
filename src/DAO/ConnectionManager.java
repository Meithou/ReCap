package DAO;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionManager {
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
	Class.forName("com.mysql.jdbc.Driver");
	//load the jdbc driver class
	// should read a conf file and login to the written DB server
	Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/canardtishop","root","root");/* red colored part has to be as per your database*/
	return con;
	}	
}

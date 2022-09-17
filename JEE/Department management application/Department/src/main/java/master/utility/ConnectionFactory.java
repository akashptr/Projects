package master.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class for creating a connection with DB and returning the Connection object.
public class ConnectionFactory {
	public static Connection getConnection()
	{
		Connection conn = null;
		
		// I am using MySql DB, database-name: personaldb, user-name: root, password: root.
		try
		{
			//REGISTER AND LOAD THE JDBC DRIVER
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//ESTABLISH THE CONNECTION
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/personaldb","root","root");
		}
		catch(ClassNotFoundException ce)
		{
			ce.printStackTrace();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		return conn;	// return the connection object.
	}
}

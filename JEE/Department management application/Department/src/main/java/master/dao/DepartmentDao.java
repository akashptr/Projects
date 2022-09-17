package master.dao;

import java.sql.*;
import java.util.ArrayList;

import master.dto.DepartmentDto;
import master.utility.ConnectionFactory;

public class DepartmentDao {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement preStmt = null;

	String insert_sql = "insert into department values(?,?,?,?)"; // Parameterized DML query
	String delete_sql = "delete from department where dept_id=?"; // Parameterized DML query
	String select_sql = "select * from department"; // Non-parameterized DQL query

	public void insertData(DepartmentDto dDto) throws SQLException // Method for inserting data into database
	{

		this.conn = ConnectionFactory.getConnection(); // Establish a connection with DB
		this.preStmt = this.conn.prepareStatement(insert_sql); // Create a PreparedStatement object with parameterized
																// insert query

		this.preStmt.setString(1, dDto.getDept_id()); // Fill the parameters(?) with values
		this.preStmt.setString(2, dDto.getDept_name());
		this.preStmt.setString(3, dDto.getDept_loc());
		this.preStmt.setString(4, dDto.getDept_phone());

		this.preStmt.executeUpdate(); // Execute the query.
		this.conn.close(); // Close the connection
	}

	public void deleteData(DepartmentDto dDto) throws SQLException // Method for deleting data from database
	{
		this.conn = ConnectionFactory.getConnection();
		this.preStmt = this.conn.prepareStatement(delete_sql);
		this.preStmt.setString(1, dDto.getDept_id());
		this.preStmt.executeUpdate();
		this.conn.close();
	}

	public ArrayList<DepartmentDto> getData() // Method for fetching all the data present in the table
	{
		ArrayList<DepartmentDto> arr = new ArrayList<DepartmentDto>();
		try {
			this.conn = ConnectionFactory.getConnection();
			this.stmt = this.conn.createStatement();
			rs = this.stmt.executeQuery(select_sql); // Store the result of the select query into ResultSet object.

			while (rs.next()) // For each row in the ResultSet
			{
				DepartmentDto dDto = new DepartmentDto(); // Create a new dto object.
				dDto.setDept_id(rs.getString(1)); // Fill the dto object's fields with ResultSet's data.
				dDto.setDept_name(rs.getString(2));
				dDto.setDept_loc(rs.getString(3));
				dDto.setDept_phone(rs.getString(4));
				arr.add(dDto); // Add this dto object to the arraylist.
			}
			this.conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return arr; // returning the table as an arraylist.
	}
}

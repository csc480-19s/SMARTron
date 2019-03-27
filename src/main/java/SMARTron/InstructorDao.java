package SMARTron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorDao {

	// Query Strings for the methods
	private static String INSERT_INSTRUCTOR = "insert into instructor (instructor_id, inst_first_name, inst_last_name)"
			+ " values (?, ?, ?)";
	
	private static String DELETE_INSTRUCTOR = "delete from instructor where instructor_id = ?";
	
	private static String SELECT_INSTRUCTOR = "select * from instructor where instructor_id = ?";
	
	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public InstructorDao() 
	{
		
	}
	
	/**
	 * Gets the connection to the database through the Connection Factory
	 * @return
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException
	{
		return ConnectionFactory.getInstance().getConnection();
	}
	
	/**
	 * Adds a new instructor to the database. instId is the parent key
	 * and must be unique
	 * 
	 * @param instId
	 * @param instFirstName
	 * @param instLastName
	 * @throws SQLException
	 */
	public void addInstructor (String instId, String instFirstName, String instLastName) throws SQLException
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(INSERT_INSTRUCTOR);
			ps.setString(1, instId);
			ps.setString(2, instFirstName);
			ps.setString(3, instLastName);
			ps.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			closeConnections();
		}
	}
	
	/**
	 * Deletes an instructor from the database.
	 * 
	 * @param instId
	 * @throws SQLException
	 */
	public void deleteInstructor (String instId) throws SQLException
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(DELETE_INSTRUCTOR);
			ps.setString(1, instId);
			ps.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} 

	}
	
	/**
	 * Returns an instructor and their information from the database
	 * 
	 * @param instId
	 * @throws SQLException
	 */
	public void selectInstructor (String instId) throws SQLException
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(SELECT_INSTRUCTOR);
			ps.setString(1, instId);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} 
		finally {
			closeConnections();
		}
	}
	
	/**
	 * Closes the connections after a transaction has been committed
	 */
	private void closeConnections()
	{
			try {
				if (ps != null) {ps.close();}
				if (rs != null) {rs.close();}
				if (con != null) {con.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
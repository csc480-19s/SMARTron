package SMARTron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDao {

	// Query Strings for the methods
	private static String INSERT_COURSE = "insert into course (course_crn, course_name, section_num,"
			+ " semester, instructor_id) values (?, ?, ?, ?, ?)";
	
	private static String DELETE_COURSE = "delete from course where course_crn = ? and section_num = ? and"
			+ " semester = ?";
	
	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public CourseDao() 
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
	 * Adds a course to the database. CourseCrn is the primary key and 
	 * instId is a foreign key
	 * 
	 * @param courseCrn
	 * @param courseName
	 * @param secNum
	 * @param sem
	 * @param instId
	 */
	public void addCourse (String courseCrn, String courseName, String secNum,
			String sem, String instId)
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(INSERT_COURSE);
			ps.setString(1, courseCrn);
			ps.setString(2, courseName);
			ps.setString(3, secNum);
			ps.setString(4, sem);
			ps.setString(5, instId);
			ps.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			closeConnections();
		}
	}
	
	/**
	 * Deletes a course from the database using the crn, section number,
	 * and semester
	 * 
	 * @param courseCrn
	 * @param secNum
	 * @param sem
	 */
	public void deleteCourse (String courseCrn, String secNum, String sem)
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(DELETE_COURSE);
			ps.setString(1, courseCrn);
			ps.setString(2, secNum);
			ps.setString(3, sem);
			ps.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
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

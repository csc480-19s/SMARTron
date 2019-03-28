package SMARTron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamDao {
	
	// Query Strings for the methods
	private static String INSERT_EXAM = "insert into exam (first_name, last_name, "
			+ "student_id, semester, birth_date, course_crn, instructor_id, exam_id) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		
	private static String DELETE_EXAM = "delete from exam where exam_id = ?";
	
	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public ExamDao() 
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
	 * Adds an exam to the database. InstId is a foreign key so this must be called
	 * after there is a valid instructor in the database. Exam id is the primary key.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param stdtId
	 * @param semester
	 * @param birthDate
	 * @param courseCrn
	 * @param instId
	 * @param examId
	 * @throws SQLException
	 */
	public void addExam(String firstName, String lastName, String stdtId, String semester,
			String birthDate, String courseCrn, String instId, String examId) throws SQLException
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(INSERT_EXAM);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, stdtId);
			ps.setString(4, semester);
			ps.setString(5, birthDate);
			ps.setString(6, courseCrn);
			ps.setString(7, instId);
			ps.setString(8, examId);
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
	 * Deletes an exam from the database using the examId
	 * 
	 * @param examId
	 */
	public void deleteExam (String examId)
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(DELETE_EXAM);
			ps.setString(1, examId);
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerKeyDao {

	// Query Strings for the methods
	private static String INSERT_ANSWER_KEY = "insert into answerkey (exam_id, instructor_id) "
			+ "values (?, ?)";
	
	private static String DELETE_ANSWER_KEY = "delete from answerkey where exam_id = ?";
	
	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/**
	 * Answer Key Object
	 */
	public AnswerKeyDao() 
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
	 * Add an answer key to the database. examId and instId are both foreign keys.
	 * 
	 * This will be updated with the new schema changes that will be out shortly
	 * 
	 * @param examId
	 * @param instId
	 */
	public void addAnswerKey (String examId, String instId)
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(INSERT_ANSWER_KEY);
			ps.setString(1, examId);
			ps.setString(2, instId);
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
	 * Delete an answer key from the database using the examId
	 * 
	 * @param examId
	 */
	public void deleteAnswerKey (String examId)
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(DELETE_ANSWER_KEY);
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

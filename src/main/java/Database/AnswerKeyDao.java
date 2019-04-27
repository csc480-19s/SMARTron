package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerKeyDao {

	// Query Strings for the methods
	private static String INSERT_ANSWER_KEY = "insert into answerkey (exam_id, instructor_id, answers) "
			+ "values (?, ?, ?)";

	private static String UPDATED_ANSWER_KEY = "";

	private static String DELETE_ANSWER_KEY = "delete from answerkey where exam_id = ?";

	private static String SELECT_ANSWER_KEY = "select answers from answerkey where exam_id = ? and instructor_id = ?";

	private static String SELECT_UPDATED_ANSWER_KEY = "select updated_answers from answerkey where exam_id = ? and "
			+ "instructor_id = ?";

	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	List<String> list = new ArrayList<String>();

	/**
	 * Answer Key Object
	 */
	public AnswerKeyDao() {

	}

	/**
	 * Gets the connection to the database through the Connection Factory
	 * 
	 * @return
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception {
		return ConnectionFactory.getInstance().getConnection();
	}

	/**
	 * Add an answer key to the database. examId and instId are both foreign keys.
	 * 
	 * This will be updated with the new schema changes that will be out shortly
	 * 
	 * @param examId
	 * @param instId
	 * @throws Exception
	 */
	public void addAnswerKey(String examId, String instId, String answers) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(INSERT_ANSWER_KEY);
			ps.setString(1, examId);
			ps.setString(2, instId);
			ps.setString(3, answers);
			ps.execute();
		} catch (SQLException e) {
			throw new Exception("Could not successfully add the answer key for the exam with id " + examId);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Adds an updated answer key to the table
	 * 
	 * @param examId
	 * @param instId
	 * @param updatedAnswers
	 * @throws Exception
	 */
	public void addUpdatedAnswerKey(String examId, String updatedAnswers) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(UPDATED_ANSWER_KEY);
			ps.setString(1, updatedAnswers);
			ps.setString(2, examId);
			ps.execute();
		} catch (SQLException e) {
			throw new Exception("Could not successfully update the answer key for the exam with id " + examId);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Delete an answer key from the database using the examId
	 * 
	 * @param examId
	 * @throws Exception
	 */
	public void deleteAnswerKey(String examId) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(DELETE_ANSWER_KEY);
			ps.setString(1, examId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Could not delete the answer key for the exam with id " + examId);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Returns the answer key
	 * 
	 * @param examId
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	public List<String> selectAnswerKey(String examId, String instId) throws Exception {
		list = new ArrayList<String>();
		try {
			con = getConnection();
			ps = con.prepareStatement(SELECT_ANSWER_KEY);
			ps.setString(1, examId);
			ps.setString(2, instId);
			rs = ps.executeQuery();
			while (rs.next()) {
				String answerkeyStr = rs.getString(1);
				if(answerkeyStr !=null && !answerkeyStr.isEmpty()) {
						list.add(answerkeyStr);						
				}
			}
		} catch (SQLException e) {
			throw new Exception("Could not get the answer key for the exam with id " + examId);
		}

		return list;
	}

	/**
	 * Returns the updated answer key
	 * 
	 * @param examId
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	public List<String> selectUpdatedAnswerKey(String examId, String instId) throws Exception {
		list = new ArrayList<String>();
		try {
			con = getConnection();
			ps = con.prepareStatement(SELECT_UPDATED_ANSWER_KEY);
			ps.setString(1, examId);
			ps.setString(2, instId);
			rs = ps.executeQuery();
			while (rs.next()) {
				String answerkeyStr = rs.getString(1);
				if(answerkeyStr !=null && !answerkeyStr.isEmpty()) {
						list.add(answerkeyStr);						
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Could not retrieve the updated answer key for the exam with id " + examId);
		}

		return list;
	}

	/**
	 * Closes the connections after a transaction has been committed
	 * 
	 * @throws Exception
	 */
	private void closeConnections() throws Exception {
		try {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			throw new Exception("Could not close the connections to the database");
		}
	}
}

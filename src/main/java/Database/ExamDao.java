package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

public class ExamDao {

	// Query Strings for the methods
	private static String INSERT_EXAM = "insert into exam (first_name, last_name, "
			+ "student_id, semester, birth_date, course_crn, instructor_id, exam_id, answers) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static String DELETE_EXAM = "delete from exam where exam_id = ?";

	private static String SELECT_EXAM = "select answers from exam where exam_id = ? "
			+ "and instructor_id = ? and student_id = ? and course_crn = ?" + "and semester = ?";
	
	private static String SELECT_STUDENTS = "SELECT first_name, last_name, gender, semester, birth_date, "
			+ "student_id, course_crn, answers FROM exam WHERE instructor_id= ? and exam_id = ?";

	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	BasicDataSource basicDS = DataSource.getInstance().getBasicDataSource();

	List<String> list;
	
	public ExamDao() {

	}

	/**
	 * Gets the connection to the database through the Connection Factory
	 * 
	 * @return
	 * @throws Exception 
	 */
	private Connection getConnection() throws Exception {
		return basicDS.getConnection();
	}

	/**
	 * Adds an exam to the database. InstId is a foreign key so this must be called
	 * after there is a valid instructor in the database. Exam id is the primary
	 * key.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param stdtId
	 * @param semester
	 * @param birthDate
	 * @param courseCrn
	 * @param instId
	 * @param examId
	 * @throws Exception 
	 */
	public void addExam(String firstName, String lastName, String stdtId, String semester, String birthDate,
			String courseCrn, String instId, String examId, String answers) throws Exception {
		try {
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
			ps.setString(9, answers);
			ps.execute();
		} catch (SQLException e) {
                    e.printStackTrace();
			throw new Exception("Could not add the exam with id " +  examId);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Deletes an exam from the database using the examId
	 * 
	 * @param examId
	 * @throws Exception 
	 */
	public void deleteExam(String examId) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(DELETE_EXAM);
			ps.setString(1, examId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Could not delete the exam with id " + examId);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Returns an exam from the database using the unique exam id
	 * 
	 * @param examId
	 * @return
	 * @throws Exception 
	 */
	public List<String> selectExamId(String examId, String instId, String stdntId, String crn, String sem)
			throws Exception {
		list  = new ArrayList<String>();
		try {
			con = getConnection();
			ps = con.prepareStatement(SELECT_EXAM);
			ps.setString(1, examId);
			ps.setString(2, instId);
			ps.setString(3, stdntId);
			ps.setString(4, crn);
			ps.setString(5, sem);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new Exception("Could not retrieve the data for the exam with id " + examId);
		}

		return list;
	}

	/**
	 * Closes the connections after a transaction has been committed
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
	
	/**
	 * Returns a list of student based on the exam id and the instructor id
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<String> selectStudents(String instId, String examId) throws Exception {
		list = new ArrayList<String>();
		try {
			con = getConnection();
			ps = con.prepareStatement(SELECT_STUDENTS);
			ps.setString(1, instId);
			ps.setString(2, examId);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1) + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + ","
						+ rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7) + ","
						+ rs.getString(8).substring(1, rs.getString(8).length() - 1));
			}
		} catch (SQLException e) {
			throw new Exception("Could not retrieve the list of students");
		}
		return list;
	}
}

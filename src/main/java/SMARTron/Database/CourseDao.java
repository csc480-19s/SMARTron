package SMARTron.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

	// Query Strings for the methods
	private static String INSERT_COURSE = "insert into course (course_crn, course_name, section_num,"
			+ " semester, instructor_id) values (?, ?, ?, ?, ?)";

	private static String DELETE_COURSE = "delete from course where course_crn = ? and section_num = ? and"
			+ " semester = ? and instructor_id = ?";

	private static String SELECT_COURSE = "select course_name from course where course_crn = ? and section_num = ? and"
			+ " semester = ? and instructor_id = ?";

	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	List<String> list = new ArrayList<String>();

	public CourseDao() {

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
	 * Adds a course to the database. CourseCrn is the primary key and instId is a
	 * foreign key
	 *
	 * @param courseCrn
	 * @param courseName
	 * @param secNum
	 * @param sem
	 * @param instId
	 * @throws Exception
	 */
	public void addCourse(String courseCrn, String courseName, String secNum, String sem, String instId) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(INSERT_COURSE);
			ps.setString(1, courseCrn);
			ps.setString(2, courseName);
			ps.setString(3, secNum);
			ps.setString(4, sem);
			ps.setString(5, instId);
			ps.execute();
		} catch (SQLException e) {
			throw new Exception("Could not add the course for crn " + courseCrn);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Deletes a course from the database using the crn, section number, and
	 * semester
	 *
	 * @param courseCrn
	 * @param secNum
	 * @param sem
	 * @throws Exception
	 */
	public void deleteCourse(String courseCrn, String secNum, String sem, String instId) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(DELETE_COURSE);
			ps.setString(1, courseCrn);
			ps.setString(2, secNum);
			ps.setString(3, sem);
			ps.setString(4, instId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Could not delete the course for crn " + courseCrn);
		} finally {
			closeConnections();
		}
	}

	public List<String> selectCourse(String crn, String section, String semester, String instId) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(SELECT_COURSE);
			ps.setString(1, crn);
			ps.setString(2, section);
			ps.setString(3, semester);
			ps.setString(4, instId);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new Exception("Could not get the course name for crn " + crn);
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
}
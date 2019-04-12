

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorDao {

	// Query Strings for the methods
	private static String INSERT_INSTRUCTOR = "insert into instructor (instructor_id, inst_first_name, inst_last_name)"
			+ " values (?, ?, ?)";

	private static String DELETE_INSTRUCTOR = "delete from instructor where instructor_id = ?";
	
	private static String SELECT_INSTRUCTOR = "select instructor_id from instructor where inst_first_name = ? "
			+ "and inst_last_name = ?";

	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	List<String> list = new ArrayList<String>();
	
	public InstructorDao() {

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
	 * Adds a new instructor to the database. instId is the parent key and must be
	 * unique
	 * 
	 * @param instId
	 * @param instFirstName
	 * @param instLastName
	 * @throws Exception 
	 */
	public void addInstructor(String instId, String instFirstName, String instLastName) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(INSERT_INSTRUCTOR);
			ps.setString(1, instId);
			ps.setString(2, instFirstName);
			ps.setString(3, instLastName);
			ps.execute();
		} catch (SQLException e) {
                    e.printStackTrace();
			throw new Exception("Could not add the instructor with the id " + instId);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Deletes an instructor from the database.
	 * 
	 * @param instId
	 * @throws Exception 
	 */
	public void deleteInstructor(String instId) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(DELETE_INSTRUCTOR);
			ps.setString(1, instId);
			ps.execute();
		} catch (SQLException e) {
			throw new Exception("Could not delete the instructor with the id " + instId);
		} finally {
			closeConnections();
		}

	}

	/**
	 * Returns an instructor and their information from the database
	 * 
	 * @param instId
	 * @throws Exception 
	 */
	public List<String> selectInstructor(String firstName, String lastName) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(SELECT_INSTRUCTOR);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new Exception("Could not retrieve the data for the instructor");
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
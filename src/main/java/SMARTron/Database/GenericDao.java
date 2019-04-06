package SMARTron.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenericDao {

	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * Generic Dao object
	 */
	public GenericDao() {

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
	 * Inserts into the database using sql code passed in as a string
	 *
	 * @param sql
	 * @throws Exception
	 */
	public void insert(String sql) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			throw new Exception("Could not execute the following sql statement: " + sql);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Deletes from the database using sql code passed in as a string
	 *
	 * @param sql
	 * @throws Exception
	 * @throws SQLException
	 */
	public void delete(String sql) throws Exception {
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Could not execute the following sql statement: " + sql);
		} finally {
			closeConnections();
		}
	}

	/**
	 * Selects from the database using sql code passed in as a string
	 *
	 * @param sql
	 * @throws Exception
	 * @throws SQLException
	 */
	public List<String> select(String sql) throws Exception {
		List<String> list = new ArrayList<String>();
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Could not execute the following sql statement: " + sql);
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

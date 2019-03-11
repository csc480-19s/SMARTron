import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDao {

	// Standard connection properties for the class
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/**
	 * Generic Dao object
	 */
	public GenericDao() 
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
	 * Inserts into the database using sql code passed in as a string
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void insert(String sql) throws SQLException
	{
		try 
		{
			con = getConnection();
			ps = con.prepareStatement(sql);
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
	 * Deletes from the database using sql code passed in as a string
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void delete(String sql)
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql);
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
	 * Selects from the database using sql code passed in as a string
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void select(String sql)
	{
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.executeQuery();
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

package SMARTron.DbConnector.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	// You must put your user and password in here for your local database for this to work
	private String driverClass = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/scantron?useSSL=false&allowPublicKeyRetrieval=true";
	private String user = "root";
	private String password = "root";
	
	private static ConnectionFactory connectionFactory = null;
	
	private ConnectionFactory() 
	{
		try
		{
			Class.forName(driverClass);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the connection to the database
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException
	{
		Connection con = null;
		con = DriverManager.getConnection(url, user, password);
		return con;
	}
	
	/**
	 * Returns the connection to the database if not null. Creates one if it is null
	 * @return
	 */
	public static ConnectionFactory getInstance()
	{
		if (connectionFactory == null)
		{
			connectionFactory = new ConnectionFactory();
		}
		
		return connectionFactory;
	}
}

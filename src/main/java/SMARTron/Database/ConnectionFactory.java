package SMARTron.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	Properties props = new Properties();
	String propsFileName = "db.properties";
	
	// You must put your user and password in here for your local database for this
	private String driverClass = "com.mysql.cj.jdbc.Driver";
	private String url;
	private String user;
	private String password;

	private static ConnectionFactory connectionFactory = null;

	/**
	 *
	 * @throws Exception
	 */
	private ConnectionFactory() throws Exception {
		try {
			Class.forName(driverClass);
		} catch (Exception e) {
			throw new Exception("Could not create a connection for the JDBC driver");
		}
	}

	/**
	 * Gets the connection to the database
	 *
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection con = null;
		loadDbProps();
		con = DriverManager.getConnection(url, user, password);
		return con;
	}

	/**
	 * Returns the connection to the database if not null. Creates one if it is null
	 *
	 * @return
	 * @throws Exception
	 */
	public static ConnectionFactory getInstance() throws Exception {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}

		return connectionFactory;
	}
	
	private void loadDbProps() {
			try {
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				Properties props = new Properties();
				try(InputStream inputStream = loader.getResourceAsStream(propsFileName)) {
				    props.load(inputStream);
					url = props.getProperty("url");
					user = props.getProperty("user");
					password = props.getProperty("password");
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}

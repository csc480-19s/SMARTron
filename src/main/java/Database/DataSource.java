package Database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {

	// Instantiate Class Objects
	private static DataSource dataSource;
	private BasicDataSource basicDataSource = new BasicDataSource();
	Properties dbProperties = new Properties();
	InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
	
	/**
	 * Constructor for the DataSource object
	 * @throws IOException 
	 */
	private DataSource() {
		
		// Load the properties file into the DataSource object
		try {
			dbProperties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		basicDataSource.setDriverClassName(dbProperties.getProperty("db.driverClassName"));
		basicDataSource.setUrl(dbProperties.getProperty("db.url"));
		basicDataSource.setUsername(dbProperties.getProperty("db.userName"));
		basicDataSource.setPassword(dbProperties.getProperty("db.password"));
		
		basicDataSource.setInitialSize(10);
		basicDataSource.setMaxTotal(100);
	}
	
	/**
	 * Returns the DataSource object and instantiates it if null
	 * @return
	 */
	public static DataSource getInstance() {
		if (dataSource == null) {
			dataSource = new DataSource();
		}
		return dataSource;
	}
	
	/**
	 * Returns the BasicDataSource object
	 * @return
	 */
	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}
	
	/**
	 * Sets the BasicDataSource object
	 * @param basicDataSource
	 */
	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}
}

/**
 * 
 */
package tarea15.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 */
public class PoolConexion {
	private static HikariConfig config=new HikariConfig();
	
	private static HikariDataSource data;
	
	static {
		config.setJdbcUrl("jdbc:mysql://localhost:3306/tarea11?useSSL=false&serverTimezone=UTC");
		config.setUsername("root");
		config.setPassword("manager");
		config.addDataSourceProperty("maximumPoolSize", 1);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		data=new HikariDataSource(config);
		
	}
	
	private PoolConexion() {}
	
	public static Connection getConnection() throws SQLException {
		return data.getConnection();
	}
}

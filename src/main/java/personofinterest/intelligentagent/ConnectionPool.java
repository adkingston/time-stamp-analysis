package personofinterest.intelligentagent;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {
	
	private static ConnectionPool dataSource;
	private ComboPooledDataSource comboPooledDataSource;
	

   private ConnectionPool() {
      try {
    	 Properties p = new Properties(System.getProperties());
    	 p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
    	 p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF"); // Off or any other level
    	 System.setProperties(p);
         comboPooledDataSource = new ComboPooledDataSource();
         comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
         comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/intelligentagenttest");
         comboPooledDataSource.setUser("root");
         comboPooledDataSource.setPassword("password");
      } catch (PropertyVetoException ex1) {
         ex1.printStackTrace();
      }
   }

   public static ConnectionPool getInstance() {
      if (dataSource == null)
         dataSource = new ConnectionPool();
      return dataSource;
   }

   public Connection getConnection() {
      Connection con = null;
      try {
         con = comboPooledDataSource.getConnection();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return con;
   }
}
	

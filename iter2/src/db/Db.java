package db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class Db {
	
    public static Connection getConnection() throws SQLException, NamingException{
    	Context ctx = new InitialContext();
    	DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookstore");
		return ds.getConnection();
	}
	
}

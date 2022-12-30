package com.wipro.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtil {
    public static Connection getDBConnection(){
    	Connection connection=null;
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "B2022102210058", "B2022102210058");
		} catch (Exception e) {
			//e.printStackTrace();
		}
        return connection;
    }
}

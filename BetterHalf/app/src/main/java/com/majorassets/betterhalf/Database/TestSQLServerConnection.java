package com.majorassets.betterhalf.Database;

import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class TestSQLServerConnection
{
	public static void connectToDB()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		String connectionSQLServerUrl = "jdbc:sqlserver://localhost:1433;" +
				"databaseName=AdventureWorks2014;integratedSecurity=true;";

		//127.0.0.1
		String connectionjtdsUrl = "jdbc:jtds:sqlserver://10.0.2.2:1433/databasename=AdventureWorks2014;" +
				"instance=SYSTEST_ANDROID;user=sa;password=pat@g0nia1994";

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try{
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connection = DriverManager.getConnection(connectionjtdsUrl);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT TOP 10 FirstName, LastName FROM Person.Person");


		}
		catch (SQLException se)
		{
			Log.e("ERROR", se.getMessage() + se.getErrorCode());
		}
		catch (ClassNotFoundException e) {
	Log.e("ERROR", e.getMessage());
	} catch (Exception e) {
	Log.e("ERROR", e.getMessage());
}
		finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {}

				try{
					statement.close();
				}
				catch(Exception e){}

				try{
					connection.close();
				}
				catch(Exception e){}
			}
		}
	}

}

package application.utils;

import java.sql.*;
public class MyConnection {
	String url = "jdbc:mysql://localhost:3306/eco";

	String login = "root";

	String pwd = "";
	
	Connection cnx;
	
	public MyConnection() {
		try {
		cnx=  DriverManager.getConnection(url, login ,pwd);	
		System.out.println("connexion etablie !");
		} catch (SQLException ex ) {
			System.err.println(ex.getMessage());
		}
	}


}

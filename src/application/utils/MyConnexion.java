package application.utils;

import java.sql.*;


public class MyConnexion {
	String url = "jdbc:mysql://localhost:3306/eco";

	String login = "root";

	String pwd = "";
	
	Connection cnx;
	
	
	public static MyConnexion instance; 
	

	private MyConnexion() {
		try {
		cnx=  DriverManager.getConnection(url, login ,pwd);	
		System.out.println("connexion etablie !");
		} catch (SQLException ex ) {
			System.err.println(ex.getMessage());
		}
	}
	public Connection getCnx() {
		return cnx;
	}
	//class pat SingleTOn
	public static MyConnexion getInsance() {
		if(instance == null ) {
			instance = new MyConnexion();	
		}
		return instance;
	}
	

}
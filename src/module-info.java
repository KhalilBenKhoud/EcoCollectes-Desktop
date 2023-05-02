module ECO_FORUM {
	requires javafx.controls;
	requires javafx.fxml;
	requires mysql.connector.java;
	requires java.sql;
	requires java.desktop;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}

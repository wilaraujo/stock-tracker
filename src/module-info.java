module stocktracker {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	opens gui to javafx.graphics, javafx.fxml;
}

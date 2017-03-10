package stage.login;

import dataManager.DataManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("수강신청 시스템 ver 1.0");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		DataManager.connectDB();
		Application.launch();
	}
}

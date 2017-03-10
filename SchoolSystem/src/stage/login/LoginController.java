package stage.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.Student;
import dataManager.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stage.manager.ManagerController;
import stage.msgBox.MsgBox;
import stage.user.UserController;

public class LoginController implements Initializable{

	@FXML private TextField txtID;
	@FXML private PasswordField txtPW;
	@FXML private Button btnLogin;
	@FXML private Button btnExit;
	
	private void handleBtnLogin(ActionEvent event){
		String inputID = txtID.getText();
		String inputPW = txtPW.getText();
		
		
		Stage curStage = (Stage) btnLogin.getScene().getWindow();
		
		if(inputID == null || inputID.equals("") || inputPW == null || inputPW.equals("")){
			MsgBox.showMsgBox(curStage,"ID,PW를 입력하십시오.");
			return;
		}
		
		if(inputID.equals("manager") && inputPW.equals("1234")){
			try {
				Parent manager = FXMLLoader.load(ManagerController.class.getResource("Manager.fxml"));
				Scene scene = new Scene(manager);
				curStage.setScene(scene);
				curStage.setX(200);
				curStage.setY(100);
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try{
			int convertedID = Integer.parseInt(inputID); //예외처리 필요.
			Student student = DataManager.getStudent(convertedID, inputPW);
			if(student != null){
				Parent user = FXMLLoader.load(UserController.class.getResource("User.fxml"));
				UserController.instance.setStudent(student);
				UserController.instance.init();
				Scene scene = new Scene(user);
				curStage.setScene(scene);
				curStage.setX(200);
				curStage.setY(100);
			} else{
				MsgBox.showMsgBox(curStage,"올바르지 않은 ID,PW 입니다.");
				return;
			}
		} catch(NumberFormatException e){
			MsgBox.showMsgBox(curStage,"올바르지 않은 ID,PW 입니다.");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleBtnExit(ActionEvent event){
		Stage curStage = (Stage) btnExit.getScene().getWindow();
		curStage.close();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction(e-> handleBtnLogin(e));
		btnExit.setOnAction(e-> handleBtnExit(e));
	}
	
}

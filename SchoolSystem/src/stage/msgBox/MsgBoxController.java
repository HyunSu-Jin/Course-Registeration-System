package stage.msgBox;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MsgBoxController implements Initializable {

	public static MsgBoxController instance; //���� ��ü
	
	
	@FXML private Label lblErrMsg; // �����޽������
	@FXML private Button btnOk; // Ȯ�� ��ư
	
	public void handleBtnOk(ActionEvent evnet){
		Stage curStage = (Stage) btnOk.getScene().getWindow();
		curStage.close();
	}
	
	public void writeLblErrMsg(String msg){
		lblErrMsg.setText(msg);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		btnOk.setOnAction(e->handleBtnOk(e));
	}

}

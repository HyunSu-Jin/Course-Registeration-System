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

	public static MsgBoxController instance; //참조 객체
	
	
	@FXML private Label lblErrMsg; // 에러메시지출력
	@FXML private Button btnOk; // 확인 버튼
	
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

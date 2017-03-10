package stage.msgBox;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MsgBox {
	
	public static void showMsgBox(Stage ownerStage,String msg){
		try {
			//�ش� msg�� ���� error stage�� �����ϰ� ������ ���.
			Stage errBox = new Stage(StageStyle.UTILITY);
			errBox.initModality(Modality.WINDOW_MODAL);
			errBox.initOwner(ownerStage);
			
			FXMLLoader loader = new FXMLLoader(MsgBox.class.getResource("MsgBox.fxml"));
			Parent msgBox = loader.load();
			MsgBoxController controller = loader.getController();
			controller.writeLblErrMsg(msg);
			Scene scene = new Scene(msgBox);
			
			errBox.setScene(scene);
			errBox.setTitle("Message");
			errBox.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	
}

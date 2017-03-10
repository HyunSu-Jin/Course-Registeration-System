package stage.manager.addClass;

import java.net.URL;
import java.util.ResourceBundle;

import dataManager.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stage.msgBox.MsgBox;

public class AddClassController implements Initializable{
	@FXML private Button btnCancel;
	@FXML private Button btnMake;
	@FXML private ChoiceBox<String> cb1_day;
	@FXML private ChoiceBox<String> cb1_time;
	@FXML private ChoiceBox<String> cb2_day;
	@FXML private ChoiceBox<String> cb2_time;
	@FXML private ChoiceBox<String> cbDept;
	@FXML private ChoiceBox<String> cbLecturer;
	@FXML private ChoiceBox<String> cbPeriod;
	@FXML private ChoiceBox<String> cbStuYear;
	@FXML private ChoiceBox<String> cbYear;
	@FXML private TextField txtClass_name;
	@FXML private TextField txtClass_no;
	@FXML private TextField txtCourse_id;
	@FXML private TextField txtCredit;
	@FXML private TextField txtPersonMax;
	@FXML private TextField txtRoom; //room_id
	
	private void init(){
		ObservableList<String> cbDayList = FXCollections.observableArrayList("월","화","수","목","금","토","NO");
		cb1_day.setItems(cbDayList);
		cb2_day.setItems(cbDayList);
		ObservableList<String> cbTimeList = FXCollections.observableArrayList("0:00",
				"0:30",
				"1:00",
				"1:30",
				"2:00",
				"2:30",
				"3:00",
				"3:30",
				"4:00",
				"4:30",
				"5:00",
				"5:30",
				"6:00",
				"6:30",
				"7:00",
				"7:30",
				"8:00",
				"8:30",
				"9:00",
				"9:30",
				"10:00",
				"10:30",
				"11:00",
				"11:30",
				"12:00",
				"12:30",
				"13:00",
				"13:30",
				"14:00",
				"14:30",
				"15:00",
				"15:30",
				"16:00",
				"16:30",
				"17:00",
				"17:30",
				"18:00",
				"18:30",
				"19:00",
				"19:30",
				"20:00",
				"20:30",
				"21:00",
				"21:30",
				"22:00",
				"22:30",
				"23:00",
				"23:30"
);
		cb1_time.setItems(cbTimeList);	
		cb2_time.setItems(cbTimeList);
		
		ObservableList<String> cbYearList = FXCollections.observableArrayList("2014","2013","2012","2011");
		cbYear.setItems(cbYearList);
		
		ObservableList<String> cbStuYearList = FXCollections.observableArrayList("4","3","2","1");
		cbStuYear.setItems(cbStuYearList);
		
		ObservableList<String> cbPeriodList = FXCollections.observableArrayList("1학기","2학기");
		cbPeriod.setItems(cbPeriodList);
		cbPeriod.getSelectionModel().select(0);
		ObservableList<String> cbLecturerList = FXCollections.observableArrayList("2001001001",
				"2001001002",
				"2001011003",
				"2001011004",
				"2001021002",
				"2001031001",
				"2001032003",
				"2001032004",
				"2001032005",
				"2001032006",
				"2001032007",
				"2001032008",
				"2001032009",
				"2001032010",
				"2001032011",
				"2001032012",
				"2001032013",
				"2001032014",
				"2001032015",
				"2001032016",
				"2001032017",
				"2001032018",
				"2001032019",
				"2001032020",
				"2001032021",
				"2001032022",
				"2001032023",
				"2001032024",
				"2001032025",
				"2001032026",
				"2001032027",
				"2001032028",
				"2001032029",
				"2001032030",
				"2001032031",
				"2001032032",
				"2001032033",
				"2001032034",
				"2001032035",
				"2001032036",
				"2001032037",
				"2001032038",
				"2001032039",
				"2001032040",
				"2001032041",
				"2001032042",
				"2001032043",
				"2001032044",
				"2001032045",
				"2001032046",
				"2001032047",
				"2001032048",
				"2001032049",
				"2001032050",
				"2001032051",
				"2001032052",
				"2001032053",
				"2001032054",
				"2001032055",
				"2001032056",
				"2001032057",
				"2001032058",
				"2001032059",
				"2001032060",
				"2001032061",
				"2001032062",
				"2001032063",
				"2001032064",
				"2001032065",
				"2001032066",
				"2001032067",
				"2001032068",
				"2001032069",
				"2001032070",
				"2001032071",
				"2001032072",
				"2001032073",
				"2001032074",
				"2001032075",
				"2001032076",
				"2001032077",
				"2001032078",
				"2001032079",
				"2001032080",
				"2001032081",
				"2001032082",
				"2001032083",
				"2001032084",
				"2001032085"
);
		cbLecturer.setItems(cbLecturerList);
		
		ObservableList<String> cbDeptList = FXCollections.observableArrayList("건설환경공학과",
				"소프트웨어전공",
				"컴퓨터전공",
				"전기공학전공",
				"건축공학부",
				"화학공학전공",
				"생명공학전공",
				"유기나노공학과",
				"에너지공학과",
				"기계공학부",
				"미래자동차공학과",
				"원자력공학과",
				"자원환경공학과",
				"산업공학과",
				"생체공학전공",
				"도시공학과",
				"융합전자공학부",
				"신소재공학부",
				"교육학과",
				"국어교육과",
				"영어교육과",
				"응용미술교육과",
				"교육공학과",
				"수학교육과",
				"성악과",
				"작곡과",
				"피아노과",
				"관현악과",
				"국악과",
				"국어국문학과",
				"영어영문학과",
				"독어독문학과",
				"중어중문학과",
				"사학과",
				"철학과",
				"관광학부",
				"수학전공",
				"물리학전공",
				"화학전공",
				"생명과학전공",
				"컴퓨터공학부",
				"정보시스템학과",
				"건축학부",
				"경영학부",
				"파이낸스경영학과",
				"국제학부",
				"경제금융학부",
				"STS(과학기술학)전공",
				"공공수행인문학전공",
				"미디어문화전공",
				"영어커뮤니케이션전공",
				"통상한국어커뮤니케이션전공",
				"글로벌비즈니스문화전공(영어전용) ",
				"중국경제통상전공",
				"정책학과",
				"법학과",
				"의류학과",
				"식품영양학과",
				"실내건축디자인학과",
				"체육학과",
				"스포츠산업학과",
				"연극영화학과",
				"무용학과",
				"간호학전공",
				"간호전공",
				"정치외교학과",
				"사회학과",
				"미디어커뮤니케이션학과",
				"행정학과",
				"한중통번역전공");
		cbDept.setItems(cbDeptList);
	}
	private void handleBtnCalcel(ActionEvent e){
		Stage curStage = (Stage) btnCancel.getScene().getWindow();
		curStage.close();
	}
	private void handleBtnMake(ActionEvent e){
		//예외처리 필요.
		int class_no = Integer.parseInt(txtClass_no.getText());
		String class_name = txtClass_name.getText();
		int year = Integer.parseInt(cbStuYear.getValue());
		int credit = Integer.parseInt(txtCredit.getText());
		int person_maximum = Integer.parseInt(txtPersonMax.getText());
		int opened = Integer.parseInt(cbYear.getValue());
		String course_id = txtCourse_id.getText();
		String dept = cbDept.getValue();
		int lecturer_id = Integer.parseInt(cbLecturer.getValue());
		int room_id = Integer.parseInt(txtRoom.getText());
		int major_id = DataManager.convertDept(dept);
		int class_id = DataManager.insertClass(class_no, class_name, year, credit, person_maximum, opened, course_id, major_id, lecturer_id, room_id);
		
		int period = 1;
		int day1 = DataManager.convertDayToInt(cb1_day.getValue());
		int day2 = DataManager.convertDayToInt(cb2_day.getValue());
		String time1 = cb1_time.getValue();
		String time2 = cb2_time.getValue();
		String start = "1990-01-0" +day1 +"T" + time1 +":00.000Z";
		String end = "1990-01-0" +day2 +"T" + time2 +":00.000Z";
		DataManager.insertTime(period, start, end, class_id);
		Stage curStage = (Stage)btnMake.getScene().getWindow();
		MsgBox.showMsgBox(curStage,"강의가 개설되었습니다.");
		curStage.close();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		btnCancel.setOnAction(e->handleBtnCalcel(e));
		btnMake.setOnAction(e->handleBtnMake(e));
	}

}

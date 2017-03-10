package stage.manager;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.ResourceBundle;

import data.StatisticItem;
import data.Statistic_Temp;
import data.TableItem;
import data.TimeUnit;
import dataManager.DataManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import stage.login.LoginController;
import stage.manager.addClass.AddClassController;
import stage.msgBox.MsgBox;

public class ManagerController implements Initializable {

	public static ManagerController instance;
	
	@FXML private ChoiceBox<String> cbYear;
	@FXML private ChoiceBox<String> cbStuYear;
	@FXML private ChoiceBox<String> cbPeriod;
	@FXML private ChoiceBox<String> cbDept;
	@FXML private Button btnInit;
	@FXML private Button btnSearch;
	@FXML private TableView<TableItem> tableView;
	@FXML private TextField txtClassName;
	@FXML private TextField txtCurseID;
	@FXML private TextField txtLecturer;
	@FXML private Button btnLogout;
	@FXML private Button btnMakeClass;
	@FXML private ChoiceBox<String> sta_cbDept;
	@FXML private TableView<StatisticItem> sta_tableView;
	@FXML private Button sta_btnSearch;
	
	List<TableItem> tableList = new LinkedList<>();
	List<Statistic_Temp> tempList = new LinkedList<>();
	HashMap<String,StatisticItem> statisticList = new HashMap<>();
	List<StatisticItem> staList = new LinkedList<>();
	private void initChoiceBoxes(){
		ObservableList<String> cbYearList = FXCollections.observableArrayList("전체","2014","2013","2012","2011");
		cbYear.setItems(cbYearList);
		cbYear.getSelectionModel().select(0);
		
		ObservableList<String> cbStuYearList = FXCollections.observableArrayList("전체","4","3","2","1");
		cbStuYear.setItems(cbStuYearList);
		cbStuYear.getSelectionModel().select(0);
		
		ObservableList<String> cbPeriodList = FXCollections.observableArrayList("1학기","2학기");
		cbPeriod.setItems(cbPeriodList);
		cbPeriod.getSelectionModel().select(0);
		
		ObservableList<String> cbDeptList = FXCollections.observableArrayList("전체","건설환경공학과",
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
		ObservableList<String> cbDeptList2 = FXCollections.observableArrayList("건설환경공학과",
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
		sta_cbDept.setItems(cbDeptList2);
		cbDept.getSelectionModel().select(0);	
	}
	private void handleBtnSearch(ActionEvent e){
		Runnable runnable = new Runnable(){
		@Override
		public void run() {
			try {
				tableList.clear();
				String opened = cbYear.getValue();
				String year = cbStuYear.getValue();
				String period = "1"; // fixed
				String dept = cbDept.getValue();
				String lecturer_name = txtLecturer.getText();
				String course_id = txtCurseID.getText();
				String class_name = txtClassName.getText();

				
				/* SQL*/
				String sql = "select class.year, class.class_no, class.course_id, class.name AS class_name, lecturer.name AS lecturer_name, class.credit,class.class_id, class.person_max, class.room_id "
						+ "from class, lecturer, major where lecturer.lecturer_id = class.lecturer_id and class.major_id = major.major_id ";
				
				LinkedList<String> psmtFlags = new LinkedList<>();
				int num = 0;
				if(!opened.equals("전체")){
					sql += "and class.opened = ? ";
					psmtFlags.add("opened");
					num++;
				}
				if(!year.equals("전체")){
					sql += "and class.year = ? ";
					psmtFlags.add("year");
					num++;
				}
				if(!dept.equals("전체")){
					sql += "and major.name = ? ";
					psmtFlags.add("major_name");
					num++;
				}
				if(!lecturer_name.equals("")){
					sql += "and lecturer.name like ? ";
					psmtFlags.add("lecturer_name");
					num++;
				}
				if(!course_id.equals("")){
					sql += "and class.course_id = ? ";
					psmtFlags.add("course_id");
					num++;
				}
				if(!class_name.equals("")){
					sql += "and class.name like ? ";
					psmtFlags.add("class_name");
					num++;
				}
				//System.out.println(sql);
				PreparedStatement psmt = DataManager.conn.prepareStatement(sql);
				for(int i = 1 ; i <= num ; i++){
					String col = psmtFlags.removeFirst();
					switch(col){
					case "opened":
						psmt.setInt(i, Integer.parseInt(opened));
						break;
					case "year":
						psmt.setInt(i, Integer.parseInt(year));
						break;
					case "lecturer_name":
						psmt.setString(i,lecturer_name+"%");
						break;
					case "course_id":
						psmt.setString(i,course_id);
						break;
					case "class_name":
						psmt.setString(i,"%"+class_name+"%");
						break;
					case "major_name":
						psmt.setString(i, dept);
						break;
					}
				}
				ResultSet rs= psmt.executeQuery();
				
				while(rs.next()){
					int class_id = rs.getInt("class_id");
					int numOfCurPerson = DataManager.getNumOfPerson(class_id);
					String classPerson = numOfCurPerson +"/"+rs.getInt("person_max"); //수강정원
					
					/* SQL */
					String sql2 = "select time.begin,time.end from time where time.class_id = ? and period = 1";
					PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
					psmt2.setInt(1, class_id);
					ResultSet rs2 = psmt2.executeQuery();
					String classTime; //수업시간
					if(rs2.next()){
						//System.out.println(rs2.getString("begin"));
						//System.out.println(rs2.getString("end"));
						TimeUnit begin = DataManager.getTimeUnit(rs2.getString("begin"));
						TimeUnit end = DataManager.getTimeUnit(rs2.getString("end"));
						classTime = begin.toString() +"~" + end.toString();
						if(begin.equals("NO")){
							classTime = "NO";
						}
					} else{
						classTime = null;
					}
					
					String building_name = DataManager.getBuildingNameByRoomID(rs.getInt("room_id"));
					String classLocation = building_name; //강의실
					TableItem item = new TableItem(class_id,rs.getInt("year"),rs.getInt("class_no"),rs.getString("course_id"),rs.getString("class_name"),rs.getString("lecturer_name"),rs.getInt("credit"),classPerson,classTime,classLocation,null,numOfCurPerson,rs.getInt("person_max"),null);
					tableList.add(item);
				}
				Platform.runLater(()->{
					setTableView(tableList);
					//handleBtnSearch(new ActionEvent());
				});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		};
		Thread thread = new Thread(runnable);
		thread.run();
	}
	private void setStaTableView(List<StatisticItem> list){
		TableColumn tcCourse_id = sta_tableView.getColumns().get(0);
		tcCourse_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));
		tcCourse_id.setStyle("-fx-alignment : CENTER");
		
		TableColumn tcNumOfStu2014 = sta_tableView.getColumns().get(1);
		tcNumOfStu2014.setCellValueFactory(new PropertyValueFactory<>("numOfStu2014"));
		tcNumOfStu2014.setStyle("-fx-alignment : CENTER");
		
		TableColumn tcNumOfStu2013 = sta_tableView.getColumns().get(2);
		tcNumOfStu2013.setCellValueFactory(new PropertyValueFactory<>("numOfStu2013"));
		tcNumOfStu2013.setStyle("-fx-alignment : CENTER");
		
		TableColumn tcNumOfStu2012 = sta_tableView.getColumns().get(3);
		tcNumOfStu2012.setCellValueFactory(new PropertyValueFactory<>("numOfStu2012"));
		tcNumOfStu2012.setStyle("-fx-alignment : CENTER");
		
		TableColumn tcNumOfStu2011 = sta_tableView.getColumns().get(4);
		tcNumOfStu2011.setCellValueFactory(new PropertyValueFactory<>("numOfStu2011"));
		tcNumOfStu2011.setStyle("-fx-alignment : CENTER");
		
		sta_tableView.setItems(FXCollections.observableArrayList(list));
	}
	private void handleBtnStaSearch(ActionEvent e){
		Runnable runnable = new Runnable(){

			@Override
			public void run() {
				try {
					tempList.clear();
					staList.clear();
					statisticList.clear();
					String dept = sta_cbDept.getValue();
					int major_id = DataManager.convertDept(dept);
					
					String sql = "select class_id,name from class where major_id=? and opened = ?";
					
					PreparedStatement psmt = DataManager.conn.prepareStatement(sql);
					psmt.setInt(1, major_id);
					psmt.setInt(2, 2014);
					ResultSet rs = psmt.executeQuery();
					while(rs.next()){
						String sql2 = "select count(*) from class C, credits where C.class_id=? and C.class_id = credits.class_id and opened = ?";
						PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
						psmt2.setInt(1, rs.getInt(1));
						psmt2.setInt(2, 2014);
						ResultSet rs2 = psmt2.executeQuery();
						while(rs2.next()){
							Statistic_Temp item = new Statistic_Temp(rs.getString(2),2014,rs2.getInt(1));
							tempList.add(item);
						}
					}
					
					psmt.setInt(1, major_id);
					psmt.setInt(2, 2014);
					ResultSet rs3 = psmt.executeQuery();
					while(rs3.next()){
						String sql2 = "select count(*) from class C, credits where C.class_id=? and C.class_id = credits.class_id and opened = ?";
						PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
						psmt2.setInt(1, rs3.getInt(1));
						psmt2.setInt(2, 2013);
						ResultSet rs2 = psmt2.executeQuery();
						while(rs2.next()){
							Statistic_Temp item = new Statistic_Temp(rs3.getString(2),2013,rs2.getInt(1));
							tempList.add(item);
						}
					}
					
					psmt.setInt(1, major_id);
					psmt.setInt(2, 2014);
					ResultSet rs4 = psmt.executeQuery();
					while(rs4.next()){
						String sql2 = "select count(*) from class C, credits where C.class_id=? and C.class_id = credits.class_id and opened = ?";
						PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
						psmt2.setInt(1, rs4.getInt(1));
						psmt2.setInt(2, 2012);
						ResultSet rs2 = psmt2.executeQuery();
						while(rs2.next()){
							Statistic_Temp item = new Statistic_Temp(rs4.getString(2),2012,rs2.getInt(1));
							tempList.add(item);
						}
					}
					
					psmt.setInt(1, major_id);
					psmt.setInt(2, 2014);
					ResultSet rs5 = psmt.executeQuery();
					while(rs5.next()){
						String sql2 = "select count(*) from class C, credits where C.class_id=? and C.class_id = credits.class_id and opened = ?";
						PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
						psmt2.setInt(1, rs5.getInt(1));
						psmt2.setInt(2, 2011);
						ResultSet rs2 = psmt2.executeQuery();
						while(rs2.next()){
							Statistic_Temp item = new Statistic_Temp(rs5.getString(2),2011,rs2.getInt(1));
							tempList.add(item);
						}
					}
					
					for(Statistic_Temp ele : tempList){
						String course_id = ele.getCourse_id();
						if(statisticList.get(course_id) == null){
							statisticList.put(course_id, new StatisticItem(course_id));
						}
						
						switch(ele.getYear()){
						case 2014:
							statisticList.get(course_id).setNumOfStu2014(ele.getNumOfPerson());
							break;
						case 2013:
							statisticList.get(course_id).setNumOfStu2013(ele.getNumOfPerson());
							break;
						case 2012:
							statisticList.get(course_id).setNumOfStu2012(ele.getNumOfPerson());
							break;
						case 2011:
							statisticList.get(course_id).setNumOfStu2011(ele.getNumOfPerson());
							break;
						}
					}
//				for(Entry<String, StatisticItem> item : statisticList.entrySet()){
//					StatisticItem dmy = item.getValue();
//					System.out.println(dmy);
//				}
				staList = statisticList.entrySet().stream().map(s-> s.getValue()).collect(Collectors.toList());
				Platform.runLater(()->{
					setStaTableView(staList);
				});
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}};
			
		Thread thread = new Thread(runnable);
		thread.run();
	}
	private void setTableView(List<TableItem> list){
		//1th col
		TableColumn tcYear = tableView.getColumns().get(0);
		tcYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		tcYear.setStyle("-fx-alignment : CENTER");
		//2th col
		TableColumn tcClass_no = tableView.getColumns().get(1);
		tcClass_no.setCellValueFactory(new PropertyValueFactory<>("class_no"));
		tcClass_no.setStyle("-fx-alignment : CENTER");
		//3th col
		TableColumn tcCourse_id = tableView.getColumns().get(2);
		tcCourse_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));
		tcCourse_id.setStyle("-fx-alignment : CENTER");
		//4th col
		TableColumn tcClass_name = tableView.getColumns().get(3);
		tcClass_name.setCellValueFactory(new PropertyValueFactory<>("class_name"));
		tcClass_name.setStyle("-fx-alignment : CENTER");
		//5th col
		TableColumn tcLecturer_name = tableView.getColumns().get(4);
		tcLecturer_name.setCellValueFactory(new PropertyValueFactory<>("lecturer_name"));
		tcLecturer_name.setStyle("-fx-alignment : CENTER");
		//6th col
		TableColumn tcCredit = tableView.getColumns().get(5);
		tcCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
		tcCredit.setStyle("-fx-alignment : CENTER");
		//7th col
		TableColumn tcPerson_maximum = tableView.getColumns().get(6);
		tcPerson_maximum.setCellValueFactory(new PropertyValueFactory<>("person_maximum"));
		tcPerson_maximum.setStyle("-fx-alignment : CENTER");
		//8th col
		TableColumn tcClass_time = tableView.getColumns().get(7);
		tcClass_time.setCellValueFactory(new PropertyValueFactory<>("class_time"));
		tcClass_time.setStyle("-fx-alignment : CENTER");
		//9th col
		TableColumn tcClass_room = tableView.getColumns().get(8);
		tcClass_room.setCellValueFactory(new PropertyValueFactory<>("class_room"));
		tcClass_room.setStyle("-fx-alignment : CENTER");
		//10th col
		TableColumn actionCol = tableView.getColumns().get(9);
        actionCol.setCellValueFactory( new PropertyValueFactory<>( "DUMMY" ) );
        actionCol.setStyle("-fx-alignment : CENTER");
        Callback<TableColumn<TableItem, String>, TableCell<TableItem, String>> cellFactory = //
                new Callback<TableColumn<TableItem, String>, TableCell<TableItem, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<TableItem, String> param )
                    {
                        final TableCell<TableItem, String> cell = new TableCell<TableItem, String>()
                        {

                            final Button btn = new Button( "Do It" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                    {
                                    	TableItem tableItem = getTableView().getItems().get( getIndex() );
                                        int class_id = tableItem.getClass_id();
                                        Runnable runnable = new Runnable(){

											@Override
											public void run() {
												Stage curStage = (Stage)tableView.getScene().getWindow();
												 DataManager.deleteClass(class_id);
												 Platform.runLater(()->{
													 MsgBox.showMsgBox(curStage, "강의가 삭제되었습니다.");
													 handleBtnSearch(new ActionEvent());
												 });
											}
                                        	
                                        };
                                        Thread thread = new Thread(runnable);
                                        thread.run();
                                    } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };
        actionCol.setCellFactory( cellFactory );
        tableView.setItems(FXCollections.observableArrayList(list));
	}
	private void handleBtnInit(ActionEvent e){
		cbYear.getSelectionModel().select(0);
		cbStuYear.getSelectionModel().select(0);
		cbPeriod.getSelectionModel().select(0);
		cbDept.getSelectionModel().select(0);
		txtClassName.clear();
		txtCurseID.clear();
		txtLecturer.clear();
		tableList.clear();
		setTableView(tableList);
	}
	private void handleBtnLogout(ActionEvent e){
		try {
			Parent login = FXMLLoader.load(LoginController.class.getResource("Login.fxml"));
			Scene scene = new Scene(login);
			Stage curStage = (Stage) btnLogout.getScene().getWindow();
			curStage.setScene(scene);
			curStage.setX(500);
			curStage.setY(200);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private void handleBtnMakeClass(ActionEvent e){
		Stage addStage = new Stage(StageStyle.UTILITY);
		addStage.initModality(Modality.WINDOW_MODAL);
		Stage curStage = (Stage) btnMakeClass.getScene().getWindow();
		addStage.initOwner(curStage);
		addStage.setTitle("Add Class");
		Parent addClass;
		try {
			addClass = FXMLLoader.load(AddClassController.class.getResource("AddClass.fxml"));
			Scene scene = new Scene(addClass);
			addStage.setScene(scene);
			addStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		initChoiceBoxes();
		btnSearch.setOnAction(e->handleBtnSearch(e));
		btnInit.setOnAction(e->handleBtnInit(e));
		btnLogout.setOnAction(e-> handleBtnLogout(e));
		btnMakeClass.setOnAction(e->handleBtnMakeClass(e));
		sta_btnSearch.setOnAction(e-> handleBtnStaSearch(e));
	}

}

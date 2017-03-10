package stage.user;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import data.Student;
import data.TableItem;
import data.TimeInterval;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import stage.login.LoginController;
import stage.msgBox.MsgBox;


public class UserController implements Initializable {

	public static UserController instance;
	
	@FXML private ChoiceBox<String> cbYear;
	@FXML private ChoiceBox<String> cbStuYear;
	@FXML private ChoiceBox<String> cbPeriod;
	@FXML private ChoiceBox<String> cbDept;
	@FXML private Button btnInit;
	@FXML private Button btnSearch;
	@FXML private Label lblCurCredit;
	@FXML private Label lblDept;
	@FXML private Label lblID;
	@FXML private Label lblName;
	@FXML private Label lblYear;
	@FXML private TableView<TableItem> tableView;
	@FXML private TextField txtClassName;
	@FXML private TextField txtCurseID;
	@FXML private TextField txtLecturer;
	@FXML private TableView<TableItem> myJoinTable;
	@FXML private Button btnLogout;
	@FXML private Button btnRecovery;
	private Student student;
	
	List<TableItem> tableList = new LinkedList<>();
	List<TimeInterval> timeList = new LinkedList<>();
	List<TableItem> myClassList = new LinkedList<>();
	List<TableItem> backUpList = new LinkedList<>();
	boolean backUpFlag = false;
	private void calculTimeList(){
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				timeList.clear();
				timeList = DataManager.getMyClassTimes(student.getStudent_id());
			}
		};
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
		TableColumn tcRetry = tableView.getColumns().get(9);
		tcRetry.setCellValueFactory(new PropertyValueFactory<>("retry"));
		tcRetry.setStyle("-fx-alignment : CENTER");
		//11th col
		TableColumn actionCol = tableView.getColumns().get(10);
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
												try {
													Stage curStage = (Stage)tableView.getScene().getWindow();
													String sql = "select time.begin,time.end from time where time.class_id = ? and period = 1";
													PreparedStatement psmt = DataManager.conn.prepareStatement(sql);
													psmt.setInt(1, class_id);
													ResultSet rs = psmt.executeQuery();
													if(rs.next()){
														TimeUnit start = DataManager.getTimeUnit(rs.getString("begin"));
														TimeUnit end = DataManager.getTimeUnit(rs.getString("end"));
														if(start.getDay().equals("NO") || DataManager.isOkayToJoin(new TimeInterval(start,end),timeList)){
															if(tableItem.getNumOfCurStu() < tableItem.getNumOfMaxStu()){
																if(DataManager.isAlreadyJoin(student.getStudent_id(), class_id)){
																MsgBox.showMsgBox(curStage,"�ð��� �ߺ��˴ϴ�.");
																return;
																}
																DataManager.insertCredit(student.getStudent_id(), class_id);
																int curCredit = DataManager.getMyCredit(student.getStudent_id());
																calculTimeList();
																Platform.runLater(()->{
																	MsgBox.showMsgBox(curStage,"������û �Ϸ�");
																	lblCurCredit.setText(curCredit + "����");
																	myClassList.clear();
																	setMyJoinTable();
																	//handleBtnSearch(new ActionEvent());
																});
																return;
															} else{ //������û�Ұ�
																MsgBox.showMsgBox(curStage,"���� �ο� �ʰ�");
																return;
															}
														}{//������û�Ұ�
															MsgBox.showMsgBox(curStage,"�ð��� �ߺ��˴ϴ�.");
															return;
														}
													}
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
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
	private void initMyJoinTable(){
		System.out.println("debug");
		//1th col
		TableColumn tcYear = myJoinTable.getColumns().get(0);
		tcYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		tcYear.setStyle("-fx-alignment : CENTER");
		//2th col
		TableColumn tcClass_no = myJoinTable.getColumns().get(1);
		tcClass_no.setCellValueFactory(new PropertyValueFactory<>("class_no"));
		tcClass_no.setStyle("-fx-alignment : CENTER");
		//3th col
		TableColumn tcCourse_id = myJoinTable.getColumns().get(2);
		tcCourse_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));
		tcCourse_id.setStyle("-fx-alignment : CENTER");
		//4th col
		TableColumn tcClass_name = myJoinTable.getColumns().get(3);
		tcClass_name.setCellValueFactory(new PropertyValueFactory<>("class_name"));
		tcClass_name.setStyle("-fx-alignment : CENTER");
		//5th col
		TableColumn tcLecturer_name = myJoinTable.getColumns().get(4);
		tcLecturer_name.setCellValueFactory(new PropertyValueFactory<>("lecturer_name"));
		tcLecturer_name.setStyle("-fx-alignment : CENTER");
		//6th col
		TableColumn tcCredit = myJoinTable.getColumns().get(5);
		tcCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
		tcCredit.setStyle("-fx-alignment : CENTER");
		//7th col
		TableColumn tcPerson_maximum = myJoinTable.getColumns().get(6);
		tcPerson_maximum.setCellValueFactory(new PropertyValueFactory<>("person_maximum"));
		tcPerson_maximum.setStyle("-fx-alignment : CENTER");
		//8th col
		TableColumn tcClass_time = myJoinTable.getColumns().get(7);
		tcClass_time.setCellValueFactory(new PropertyValueFactory<>("class_time"));
		tcClass_time.setStyle("-fx-alignment : CENTER");
		//9th col
		TableColumn tcClass_room = myJoinTable.getColumns().get(8);
		tcClass_room.setCellValueFactory(new PropertyValueFactory<>("class_room"));
		tcClass_room.setStyle("-fx-alignment : CENTER");
		//10th col
		TableColumn tcRetry = myJoinTable.getColumns().get(9);
		tcRetry.setCellValueFactory(new PropertyValueFactory<>("retry"));
		tcRetry.setStyle("-fx-alignment : CENTER");
		//11th col
		TableColumn actionCol = myJoinTable.getColumns().get(10);
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
												Stage curStage = (Stage) myJoinTable.getScene().getWindow();
												DataManager.deleteCredit(student.getStudent_id(), class_id);
												myClassList.clear();
												calculTimeList();
												int curCredit = DataManager.getMyCredit(student.getStudent_id());
												Platform.runLater(()->{
													MsgBox.showMsgBox(curStage,"���� ��ҵǾ����ϴ�.");
													setMyJoinTable();
													lblCurCredit.setText(curCredit + "����");
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
	}
	private void setMyJoinTable(){
		try {
			//System.out.println("debug2");
			String sql = "select C.year, C.class_no, C.course_id, C.name AS class_name, L.name AS lecturer_name, C.credit,C.class_id, C.person_max, C.room_id"
						+" from class C, lecturer L, credits "
						+"where credits.class_id = C.class_id and L.lecturer_id = C.lecturer_id and C.opened = 2014 and credits.student_id = ? and credits.grade is null";
			PreparedStatement psmt = DataManager.conn.prepareStatement(sql);
			psmt.setInt(1,student.getStudent_id());
			ResultSet rs = psmt.executeQuery();
			while(rs.next()){
				int class_id = rs.getInt("class_id");
				int numOfCurPerson = DataManager.getNumOfPerson(class_id);
				String classPerson = numOfCurPerson +"/"+rs.getInt("person_max"); //��������
				
				/* SQL */
				String sql2 = "select time.begin,time.end from time where time.class_id = ? and period = 1";
				PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
				psmt2.setInt(1, class_id);
				ResultSet rs2 = psmt2.executeQuery();
				String classTime; //�����ð�
				if(rs2.next()){
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
				String classLocation = building_name; //���ǽ�
				
				String beforeGrade = DataManager.getBeforeGrade(student.getStudent_id(), class_id);
				if(beforeGrade == null){
					beforeGrade = "N";
				} else{
					beforeGrade = "Y("+beforeGrade+")";
				}
				TableItem item = new TableItem(class_id,rs.getInt("year"),rs.getInt("class_no"),rs.getString("course_id"),rs.getString("class_name"),rs.getString("lecturer_name"),rs.getInt("credit"),classPerson,classTime,classLocation,beforeGrade,numOfCurPerson,rs.getInt("person_max"),beforeGrade);
				myClassList.add(item);
			}
			if(backUpFlag == false){
				backUpList.addAll(myClassList);
				backUpFlag = true;
			}
			Platform.runLater(()->{
				myJoinTable.setItems(FXCollections.observableArrayList(myClassList));
				calculTimeList();
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setStudent(Student student){
		this.student = student;
	}
	public void init(){
		String student_id = String.valueOf(student.getStudent_id());
		lblID.setText(student_id);
		String name = student.getName();
		lblName.setText(name);
		int major_id = student.getMajor_id();
		String dept = DataManager.getMajor(major_id);
		lblDept.setText(dept);
		String year = String.valueOf(student.getYear()) + "�г�";
		lblYear.setText(year);
		//lblMaxCredit.setText("20����");
		int curCredit = DataManager.getMyCredit(student.getStudent_id());
		lblCurCredit.setText(curCredit + "����");
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				timeList = DataManager.getMyClassTimes(student.getStudent_id());
			}
		};
		Thread thread = new Thread(runnable);
		thread.run();
		initMyJoinTable();
		setMyJoinTable();
	}	
	private void initChoiceBoxes(){
		ObservableList<String> cbYearList = FXCollections.observableArrayList("��ü","2014","2013","2012","2011");
		cbYear.setItems(cbYearList);
		cbYear.getSelectionModel().select(0);
		
		ObservableList<String> cbStuYearList = FXCollections.observableArrayList("��ü","4","3","2","1");
		cbStuYear.setItems(cbStuYearList);
		cbStuYear.getSelectionModel().select(0);
		
		ObservableList<String> cbPeriodList = FXCollections.observableArrayList("1�б�","2�б�");
		cbPeriod.setItems(cbPeriodList);
		cbPeriod.getSelectionModel().select(0);
		
		ObservableList<String> cbDeptList = FXCollections.observableArrayList("��ü","�Ǽ�ȯ����а�",
"����Ʈ��������",
"��ǻ������",
"�����������",
"������к�",
"ȭ�а�������",
"�����������",
"���⳪����а�",
"���������а�",
"�����к�",
"�̷��ڵ������а�",
"���ڷ°��а�",
"�ڿ�ȯ����а�",
"������а�",
"��ü��������",
"���ð��а�",
"�������ڰ��к�",
"�ż�����к�",
"�����а�",
"�������",
"�������",
"����̼�������",
"�������а�",
"���б�����",
"���ǰ�",
"�۰��",
"�ǾƳ��",
"�����ǰ�",
"���ǰ�",
"������а�",
"������а�",
"������а�",
"�߾��߹��а�",
"���а�",
"ö�а�",
"�����к�",
"��������",
"����������",
"ȭ������",
"�����������",
"��ǻ�Ͱ��к�",
"�����ý����а�",
"�����к�",
"�濵�к�",
"���̳����濵�а�",
"�����к�",
"���������к�",
"STS(���б����)����",
"���������ι�������",
"�̵�ȭ����",
"����Ŀ�´����̼�����",
"����ѱ���Ŀ�´����̼�����",
"�۷ι�����Ͻ���ȭ����(��������) ",
"�߱������������",
"��å�а�",
"���а�",
"�Ƿ��а�",
"��ǰ�����а�",
"�ǳ�����������а�",
"ü���а�",
"����������а�",
"���ؿ�ȭ�а�",
"�����а�",
"��ȣ������",
"��ȣ����",
"��ġ�ܱ��а�",
"��ȸ�а�",
"�̵��Ŀ�´����̼��а�",
"�����а�",
"�������������");
		cbDept.setItems(cbDeptList);
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
				if(!opened.equals("��ü")){
					sql += "and class.opened = ? ";
					psmtFlags.add("opened");
					num++;
				}
				if(!year.equals("��ü")){
					sql += "and class.year = ? ";
					psmtFlags.add("year");
					num++;
				}
				if(!dept.equals("��ü")){
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
					String classPerson = numOfCurPerson +"/"+rs.getInt("person_max"); //��������
					
					/* SQL */
					String sql2 = "select time.begin,time.end from time where time.class_id = ? and period = 1";
					PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
					psmt2.setInt(1, class_id);
					ResultSet rs2 = psmt2.executeQuery();
					String classTime; //�����ð�
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
					String classLocation = building_name; //���ǽ�
					String beforeGrade = DataManager.getBeforeGrade(student.getStudent_id(),class_id);
					String classRetry; //���������
					if(beforeGrade == null){
						classRetry = "N";
					} else{
						classRetry = "Y("+beforeGrade +")";
					}
					TableItem item = new TableItem(class_id,rs.getInt("year"),rs.getInt("class_no"),rs.getString("course_id"),rs.getString("class_name"),rs.getString("lecturer_name"),rs.getInt("credit"),classPerson,classTime,classLocation,classRetry,numOfCurPerson,rs.getInt("person_max"),beforeGrade);
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
	private void handleBtnRecovery(ActionEvent e){
		Runnable runnable = new Runnable(){

			@Override
			public void run() {
				for(TableItem ele : myClassList){
					DataManager.deleteCreditByBackUP(student.getStudent_id(), ele.getClass_id());
				}
				myClassList.clear();
				for(TableItem ele : backUpList){
					DataManager.insertCreditByBackUP(student.getStudent_id(), ele.getClass_id(),ele.getRetry_credit());
					myClassList.add(ele);
				}
				int curCredit = DataManager.getMyCredit(student.getStudent_id());
				Platform.runLater(()->{
					lblCurCredit.setText(curCredit + "����");
					myJoinTable.setItems(FXCollections.observableArrayList(myClassList));
					calculTimeList();
				});
			}
			
		};
		Thread thread = new Thread(runnable);
		thread.run();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		initChoiceBoxes();
		btnSearch.setOnAction(e->handleBtnSearch(e));
		btnInit.setOnAction(e->handleBtnInit(e));
		btnLogout.setOnAction(e-> handleBtnLogout(e));
		btnRecovery.setOnAction(e-> handleBtnRecovery(e));
	}
}

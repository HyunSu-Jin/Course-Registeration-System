# 수강신청시스템
사용 기술
- Java
- JavaFX(GUI)
- RDBMS(MySQL)

- 시연 영상(https://www.youtube.com/watch?v=wX6m8qUzI6A)
![img3](/img/img3.png)

# UML : Use case diagram
![img1](/img/img1.png)
- 지속적인 transacation이 가능한 인터페이스
- 수강편람기능(강의조회)
- 사용자 로그인
- 수강신청 기능
- 설강 및 폐강

"학생이 강의시간이 겹치는 수업을 수강신청 하거나 동일한 교사가 같은시간에 2개이상의 강의를 가진다. 또는 강의실 한개에 같은시간대에 2개 이상의 수업이 이루어 진다." 이러한 불가능한 상황에 대해서 데이터베이스 디자인을 통해(Primary Key, 제약조건설정)을 통해 신뢰성인은 DB를 구축한다.

## Conceptual database design
![img2](/img/img2.png)

# Search(Dynamic SQL)
GUI Interface의 검색조건이 사용자입력에 따라 결정되어지므로 이에 따른 SQL문은 다음과 같이 Dynamic하게 결정되어야 한다.
<pre><code>
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
				String classPerson = numOfCurPerson +"/"+rs.getInt("person_max");
				
				/* SQL */
				String sql2 = "select time.begin,time.end from time where time.class_id = ? and period = 1";
				PreparedStatement psmt2 = DataManager.conn.prepareStatement(sql2);
				psmt2.setInt(1, class_id);
				ResultSet rs2 = psmt2.executeQuery();
				String classTime; 
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
</code><pre>

# Event Handling
JavaFX는 Button이 클릭되었을때(event)에 따른 function call기반으로 구성되어있다.
click되었을때 검색조건이 리셋되는 버튼
- 소스코드 예시
<pre><code>
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
</code></pre>


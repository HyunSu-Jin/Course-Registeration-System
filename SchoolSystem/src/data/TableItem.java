package data;

public class TableItem{
	int class_id;
	int year; // 학년
	int class_no; // 수업번호
	String course_id; // 학수번호
	String class_name; // 교과목명
	String lecturer_name; //교강사
	int credit; // 학점
	int numOfCurStu;
	int numOfMaxStu;
	String person_maximum; // 수강정원
	String class_time; // 수업시간
	String class_room; //강의실
	String retry; //재수강
	String retry_credit; // 재수강학점 B0.
	public TableItem(int class_id,int year,int class_no,String course_id,String class_name,String lecturer_name,int credit,String person_maximum,String class_time,String class_room,String retry,int cur,int max,String retry_credit){
		this.class_id = class_id;
		this.year = year;
		this.class_no = class_no;
		this.course_id = course_id;
		this.class_name = class_name;
		this.lecturer_name = lecturer_name;
		this.credit = credit;
		this.person_maximum = person_maximum;
		this.class_time = class_time;
		this.class_room = class_room;
		this.retry = retry;
		this.numOfCurStu = cur;
		this.numOfMaxStu = max;
		this.retry_credit = retry_credit;
	}
	
	public String getRetry_credit() {
		return retry_credit;
	}

	public void setRetry_credit(String retry_credit) {
		this.retry_credit = retry_credit;
	}

	public int getNumOfCurStu() {
		return numOfCurStu;
	}

	public void setNumOfCurStu(int numOfCurStu) {
		this.numOfCurStu = numOfCurStu;
	}

	public int getNumOfMaxStu() {
		return numOfMaxStu;
	}

	public void setNumOfMaxStu(int numOfMaxStu) {
		this.numOfMaxStu = numOfMaxStu;
	}

	public int getClass_id() {
		return class_id;
	}

	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getClass_no() {
		return class_no;
	}

	public void setClass_no(int class_no) {
		this.class_no = class_no;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getLecturer_name() {
		return lecturer_name;
	}

	public void setLecturer_name(String lecturer_name) {
		this.lecturer_name = lecturer_name;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getPerson_maximum() {
		return person_maximum;
	}

	public void setPerson_maximum(String person_maximum) {
		this.person_maximum = person_maximum;
	}

	public String getClass_time() {
		return class_time;
	}

	public void setClass_time(String class_time) {
		this.class_time = class_time;
	}

	public String getClass_room() {
		return class_room;
	}

	public void setClass_room(String class_room) {
		this.class_room = class_room;
	}

	public String getRetry() {
		return retry;
	}

	public void setRetry(String retry) {
		this.retry = retry;
	}

	@Override
	public String toString() {
		return year +" " + class_no +" " + course_id + " " + class_name + " " + lecturer_name + " " +credit
				+ person_maximum + " " + class_time + " " +class_room + " " +retry;
				
				}
}
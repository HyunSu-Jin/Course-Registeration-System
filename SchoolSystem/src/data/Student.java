package data;

public class Student {
	private int student_id; //학번
	private String password; // 비밀번호
	private String name; //이름
	private String sex; //male,female 구분
	private int year; //학년
	private int major_id; // 전공 코드
	private int tutor_id; // 교수
	
	public Student(int student_id,String password,String name,String sex,int year,int major_id,int tutor_id){
		this.student_id = student_id;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.year = year;
		this.major_id = major_id;
		this.tutor_id = tutor_id;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getTutor_id() {
		return tutor_id;
	}
	public void setTutor_id(int tutor_id) {
		this.tutor_id = tutor_id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMajor_id() {
		return major_id;
	}
	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}
	
	
}

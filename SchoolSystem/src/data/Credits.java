package data;

public class Credits {
	private int credits_id; // 학번<>수업번호에 따른 credit이 이미 존재하면 재수강인 것으로 판단 할 수 있음.
	private String grade; // 학점
	private int student_id; // 학번
	private int class_id; // 수업번호
	public int getCredits_id() {
		return credits_id;
	}
	public void setCredits_id(int credits_id) {
		this.credits_id = credits_id;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	
	
}

package data;

public class Course { //과목
	private String course_id; // 학수번호, ITE3070
	private String name; //과목 이름
	private int credit; // 해당 과목의 학점
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	
}

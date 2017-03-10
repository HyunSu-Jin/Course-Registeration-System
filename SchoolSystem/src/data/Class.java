package data;

public class Class {
	private int class_id;
	private int class_no; // 10345
	private String name; // 과목이름
	private int year; // 몇 학년 대상 강의
	private int credit; // 학점
	private int person_max; // 수강신청 인원제한
	private int opended; // 설강년도
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public int getClass_no() {
		return class_no;
	}
	public void setClass_no(int class_no) {
		this.class_no = class_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getPerson_max() {
		return person_max;
	}
	public void setPerson_max(int person_max) {
		this.person_max = person_max;
	}
	public int getOpended() {
		return opended;
	}
	public void setOpended(int opended) {
		this.opended = opended;
	}
	
	
}

package data;

public class Statistic_Temp {
	String course_id;
	int year;
	int numOfPerson;
	
	public Statistic_Temp(String course_id,int year,int num){
		this.course_id = course_id;
		this.year = year;
		this.numOfPerson = num;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getNumOfPerson() {
		return numOfPerson;
	}
	public void setNumOfPerson(int numOfPerson) {
		this.numOfPerson = numOfPerson;
	}
	
}

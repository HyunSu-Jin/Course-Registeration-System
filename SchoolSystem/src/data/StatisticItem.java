package data;

public class StatisticItem {
	String course_id;
	int numOfStu2014;
	int numOfStu2013;
	int numOfStu2012;
	int numOfStu2011;
	
	public StatisticItem(String course_id){
		this.course_id = course_id;
		this.numOfStu2011 = 0;
		this.numOfStu2012 = 0;
		this.numOfStu2013 = 0;
		this.numOfStu2014 = 0;
	}
	public StatisticItem(String course_id,int n2014,int n2013,int n2012,int n2011){
		this.course_id = course_id;
		this.numOfStu2011 = n2011;
		this.numOfStu2012 = n2012;
		this.numOfStu2013 = n2013;
		this.numOfStu2014 = n2014;
	}
	
	@Override
	public String toString() {
		return course_id + " " + numOfStu2011 + " " + numOfStu2012 + " " + numOfStu2013 +" " + numOfStu2014;
	}
	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public int getNumOfStu2014() {
		return numOfStu2014;
	}

	public void setNumOfStu2014(int numOfStu2014) {
		this.numOfStu2014 = numOfStu2014;
	}

	public int getNumOfStu2013() {
		return numOfStu2013;
	}

	public void setNumOfStu2013(int numOfStu2013) {
		this.numOfStu2013 = numOfStu2013;
	}

	public int getNumOfStu2012() {
		return numOfStu2012;
	}

	public void setNumOfStu2012(int numOfStu2012) {
		this.numOfStu2012 = numOfStu2012;
	}

	public int getNumOfStu2011() {
		return numOfStu2011;
	}

	public void setNumOfStu2011(int numOfStu2011) {
		this.numOfStu2011 = numOfStu2011;
	}
	@Override
	public boolean equals(Object obj) {
		StatisticItem obj2 = (StatisticItem)obj;
		if(this.course_id.equals(obj2.getCourse_id())){
			return true;
		} else{
			return false;
		}
	}
	
}

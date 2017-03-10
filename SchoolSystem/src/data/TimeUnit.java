package data;

public class TimeUnit {
	String day;
	String time;
	int date;
	int hour;
	int minute;
	public TimeUnit(String day,String time){
		this.day = day;
		this.time = time;
	}
	public TimeUnit(String day,String time,int date,int hour,int minute){
		this.day = day;
		this.time = time;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
	}
	
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String toString(){
		if(day.equals("NO")){
			return "NO";
		}
		return day+"("+time+")";
	}
	public static String covertDay(int num){
		switch(num){
		case 1:
			return "월";
		case 2:
			return "화";
		case 3:
			return "수";
		case 4:
			return "목";
		case 5:
			return "금";
		case 6:
			return "토";
		default:
			return "error";
		}
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	
}

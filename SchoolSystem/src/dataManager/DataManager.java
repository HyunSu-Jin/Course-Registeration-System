package dataManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import data.Student;
import data.TimeInterval;
import data.TimeUnit;

public class DataManager {
	public static Connection conn;
	public static void connectDB(){
        try {  
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SchoolDB",
            		"schooldb", "1234");
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
	}
	public static void disconnectDB(){
		try {
			if(conn != null){
				conn.close();
				System.out.println("연결 종료");
				return;
			}
			System.out.println("연결 종료");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Student getStudent(int student_id,String password){
		try {
			String sql = "select * from student where student_id = ? and password = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, student_id);
			psmt.setString(2, password);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){ // 해당 tuple이 존재함. 
				String name = rs.getString("name");
				String sex = rs.getString("sex");
				int year = rs.getInt("year");
				int major_id = rs.getInt("major_id");
				int tutor_id = rs.getInt("tutor_id");
				Student student = new Student(student_id,password,name,sex,year,major_id,tutor_id);
				return student;
			} else{ //해당 tuple이 존재하지 않음.
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getMajor(int major_id){
		try {
			String sql = "select name from major where major_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, major_id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static int getMyCredit(int student_id){
		try {
			String sql = "select sum(class.credit) from class, credits where credits.student_id = ? and class.class_id = credits.class_id and class.opened = 2014 and credits.grade is null";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, student_id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			} else{
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static int getNumOfPerson(int class_id){
		try {
			String sql = "select count(*) from credits where credits.class_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1,class_id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			} else{
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static TimeUnit getTimeUnit(String str){
		try {
			if(str.equals("NO")){
				return new TimeUnit("NO",null);
			}
			SimpleDateFormat formatter, FORMATTER;
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Date date = formatter.parse(str.substring(0, 24));
			int day = date.getDate();
			String day_str = TimeUnit.covertDay(day);
			int hours = date.getHours();
			int minutes = date.getMinutes();
			String time = hours + ":" +minutes;
			TimeUnit timeUnit = new TimeUnit(day_str,time,day,hours,minutes);
			return timeUnit;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getBuildingName(int building_id){
		try {
			String sql = "select name from building where building.building_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			} else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getBuildingNameByRoomID(int room_id){
		try {
			String sql = "select building.name from building,room where room.room_id = ? and room.building_id = building.building_id";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, room_id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			} else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getBeforeGrade(int student_id,int class_id){
		try {
			String sql = "select credits.grade from credits where credits.student_id = ? and credits.class_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, student_id);
			psmt.setInt(2, class_id);
			ResultSet rs = psmt.executeQuery();
			String grade = null;
			while(rs.next()){
				if(rs.getString(1) != null){
					grade = rs.getString(1);
				}
			}
			return grade;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static List<TimeInterval> getMyClassTimes(int student_id){
		try {
			List<TimeInterval> list = new LinkedList<>();
			String sql = "select class.class_id,begin,end from credits C,class,time where C.class_id = class.class_id and C.class_id = time.class_id and C.student_id = ? and class.opened = 2014 and time.period = 1 and C.grade is null";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, student_id);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()){
				TimeUnit start = DataManager.getTimeUnit(rs.getString("begin"));
				TimeUnit end = DataManager.getTimeUnit(rs.getString("end"));
				TimeInterval timeInterval = new TimeInterval(start,end);
				list.add(timeInterval);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static boolean isOkayToJoin(TimeInterval itv,List<TimeInterval> list){
		LocalDateTime target_b = LocalDateTime.of(2014,1,itv.getStart().getDate(),itv.getStart().getHour(), itv.getStart().getMinute());
		LocalDateTime target_e = LocalDateTime.of(2014,1,itv.getEnd().getDate(),itv.getEnd().getHour(), itv.getEnd().getMinute());
		for(TimeInterval ele : list){
			LocalDateTime before = LocalDateTime.of(2014,1,ele.getStart().getDate(),ele.getStart().getHour(), ele.getStart().getMinute());
			LocalDateTime end = LocalDateTime.of(2014,1,ele.getEnd().getDate(),ele.getEnd().getHour(), ele.getEnd().getMinute());
			if(target_b.isBefore(before) || target_b.equals(before)){
				if(target_e.isAfter(before) || target_e.equals(before)){
					return false;
				}
			} 
			if(target_b.isBefore(end) || target_b.equals(end)){
				if(target_e.isAfter(end) || target_e.equals(end)){
					return false;
				}
			} 
		}
		return true;
	}
	public static void insertCredit(int student_id,int class_id){
		try {
			String sql = "insert into credits(grade,student_id,class_id) values(null,?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, student_id);
			psmt.setInt(2, class_id);
			int row = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isAlreadyJoin(int student_id,int class_id){
		try {
			String sql = "select grade from credits where credits.class_id = ? and credits.student_id = ? and credits.grade is null";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, class_id);
			psmt.setInt(2, student_id);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()){
				String grade = rs.getString(1);
				if(grade == null){
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static void deleteCredit(int student_id,int class_id){
		try {
			String sql = "select credits_id from credits,class C where C.class_id = ? and credits.class_id = C.class_id and credits.student_id = ? and C.opened = 2014 and credits.grade is null";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, class_id);
			psmt.setInt(2, student_id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				int credits_id = rs.getInt(1);
				String sql2 = "delete from credits where credits.credits_id = ?";
				PreparedStatement psmt2 = conn.prepareStatement(sql2);
				psmt2.setInt(1, credits_id);
				int row = psmt2.executeUpdate();
			} else{
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void deleteCreditByBackUP(int student_id,int class_id){
		try {
			String sql = "select credits_id from credits,class C where C.class_id = ? and credits.class_id = C.class_id and credits.student_id = ? and C.opened = 2014";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, class_id);
			psmt.setInt(2, student_id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				int credits_id = rs.getInt(1);
				String sql2 = "delete from credits where credits.credits_id = ?";
				PreparedStatement psmt2 = conn.prepareStatement(sql2);
				psmt2.setInt(1, credits_id);
				int row = psmt2.executeUpdate();
			} else{
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insertCreditByBackUP(int student_id,int class_id,String grade){
		try {
			String sql = "insert into credits(grade,student_id,class_id) values(?,?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, grade);
			psmt.setInt(2, student_id);
			psmt.setInt(3, class_id);
			int row = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int convertDayToInt(String day){
		switch(day){
		case "월":
			return 1;
		case "화":
			return 2;
		case "수":
			return 3;
		case "목":
			return 4;
		case "금":
			return 5;
		case "토":
			return 6;
			default:
				return -1;
		}
	}
	public static void insertTime(int period,String begin,String end,int class_id){
		try {
			String sql = "insert into time(period,begin,end,class_id) values(?,?,?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, period);
			psmt.setString(2, begin);
			psmt.setString(3, end);
			psmt.setInt(4, class_id);
			int row = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int insertClass(int class_no,String name,int year,int credit,int person_max,int opened,String course_id,int major_id,int lecturer_id,int room_id){
		try {
			String sql = "insert into class(class_no,name,year,credit,person_max,opened,course_id,major_id,lecturer_id,room_id) values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, class_no);
			psmt.setString(2, name);
			psmt.setInt(3, year);
			psmt.setInt(4, credit);
			psmt.setInt(5, person_max);
			psmt.setInt(6, opened);
			psmt.setString(7, course_id);
			psmt.setInt(8, major_id);
			psmt.setInt(9, lecturer_id);
			psmt.setInt(10, room_id);
			int row = psmt.executeUpdate();
			String sql2 = "select class_id from class where class_no = ? and year = ?";
			PreparedStatement psmt2 = conn.prepareStatement(sql2);
			psmt2.setInt(1, class_no);
			psmt2.setInt(2, year);
			ResultSet rs= psmt2.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}else{
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static int convertDept(String dept){
		try {
			String sql = "select major_id from major where name = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, dept);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			} else{
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static void deleteClass(int class_id){
		try {
			String sql = "delete from class where class_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, class_id);
			int row = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

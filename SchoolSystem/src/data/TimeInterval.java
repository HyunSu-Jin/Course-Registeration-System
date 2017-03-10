package data;

public class TimeInterval {
	TimeUnit start;
	TimeUnit end;
	public TimeInterval(TimeUnit start,TimeUnit end){
		this.start = start;
		this.end = end;
	}

	public TimeUnit getStart() {
		return start;
	}

	public void setStart(TimeUnit start) {
		this.start = start;
	}

	public TimeUnit getEnd() {
		return end;
	}

	public void setEnd(TimeUnit end) {
		this.end = end;
	}
}

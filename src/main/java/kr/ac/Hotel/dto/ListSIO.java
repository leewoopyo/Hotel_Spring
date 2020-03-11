package kr.ac.Hotel.dto;

public class ListSIO {
	
	String date;
	String dayOfWeek;
	String room1;
	String room2;
	String room3;
	
	public ListSIO() {
		super();
	}

	public ListSIO(String date,String dayOfWeek, String room1, String room2, String room3) {
		super();
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.room1 = room1;
		this.room2 = room2;
		this.room3 = room3;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getRoom1() {
		return room1;
	}

	public void setRoom1(String room1) {
		this.room1 = room1;
	}

	public String getRoom2() {
		return room2;
	}

	public void setRoom2(String room2) {
		this.room2 = room2;
	}

	public String getRoom3() {
		return room3;
	}

	public void setRoom3(String room3) {
		this.room3 = room3;
	}
	
	
	
	

}

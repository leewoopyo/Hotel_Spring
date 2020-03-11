package kr.ac.Hotel.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "resv")
@IdClass(HotelRIO_Idclass.class)
public class HotelRIO {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "name")
	private String name;
	
	@Id
	@Type(type = "date")
	@Column(name = "resv_date",columnDefinition = "DATE")
	private Date resv_date;

	@Id
	@Column(name = "room",columnDefinition = "INT")
	private int room;
	
	@Column(name = "addr")
	private String addr;
	
	@Column(name = "telnum")
	private String telnum;
	
	@Column(name = "in_name")
	private String in_name;
	
	@Type(type="text")
	@Column(name = "comment")
	private String comment;
	
	@Type(type = "date")
	@Column(name = "write_date")
	private Date write_date;
	
	@Column(name = "processing")
	private int processing;

	
	public HotelRIO() {
		super();
	}
	
	
	public HotelRIO(String name, Date resv_date, int room, String addr, String telnum, String in_name, String comment,
			Date write_date, int processing) {
		super();
		this.name = name;
		this.resv_date = resv_date;
		this.room = room;
		this.addr = addr;
		this.telnum = telnum;
		this.in_name = in_name;
		this.comment = comment;
		this.write_date = write_date;
		this.processing = processing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getResv_date() {
		return resv_date;
	}

	public void setResv_date(Date resv_date) {
		this.resv_date = resv_date;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getIn_name() {
		return in_name;
	}

	public void setIn_name(String in_name) {
		this.in_name = in_name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	public int getProcessing() {
		return processing;
	}

	public void setProcessing(int processing) {
		this.processing = processing;
	}
	
	
	
	
	
}

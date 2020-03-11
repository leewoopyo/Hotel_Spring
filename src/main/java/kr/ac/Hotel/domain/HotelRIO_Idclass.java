package kr.ac.Hotel.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class HotelRIO_Idclass implements Serializable {
	
	@Column(name="resv_date")
	private Date resv_date;
	
	@Column(name="room")
	private int room;
	
	public HotelRIO_Idclass() {
		
	}
	
	public HotelRIO_Idclass(Date resv_date, int room) {
		super();
		this.resv_date = resv_date;
		this.room = room;
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
	
	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
	        if (!(obj instanceof HotelRIO_Idclass)) return false;
	        HotelRIO_Idclass that = (HotelRIO_Idclass) obj;
	        return Objects.equals(getResv_date(), that.getResv_date()) &&
	                Objects.equals(getRoom(), that.getRoom());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getResv_date(), getRoom());
	}
	
	

}

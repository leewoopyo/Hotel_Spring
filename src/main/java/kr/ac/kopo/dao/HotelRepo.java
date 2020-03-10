package kr.ac.kopo.dao;

import java.util.Date;
import java.util.List;

import kr.ac.kopo.domain.HotelRIO;

public interface HotelRepo {
	
	HotelRIO selectOne(String resv_date, int room);
	List<HotelRIO> selectAll();	//전체데이터를 list에 담음
	void createOne(HotelRIO hotel);	//DB에 insert하는 함수
	void updateOne(String resv_date,int room,HotelRIO hotel);
	void daleteOne(String resv_date,int room);
	int deleteAll();
	void createDB();
	void dropDB();

}

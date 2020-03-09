package kr.ac.kopo.dao;

import java.util.Date;
import java.util.List;

import kr.ac.kopo.domain.HotelRIO;

public interface HotelRepo {
	
	Long count();
	HotelRIO selectOne(String resv_date, int room);
	List<HotelRIO> selectAll();
	List<HotelRIO> selectAllByPagination(int page, int itemSizePerPage);
	void createOne(HotelRIO hotel);
	void updateOne(String resv_date,int room,HotelRIO hotel);
	void daleteOne(String resv_date,int room);
	int deleteAll();
	void createDB();
	void dropDB();

}

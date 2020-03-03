package kr.ac.kopo.dao;

import java.util.Date;
import java.util.List;

import kr.ac.kopo.domain.HotelRIO;

public interface HotelRepo {
	
	Long count();
	HotelRIO selectOne(Date resv_date, int room);
	List<HotelRIO> selectAll();
	List<HotelRIO> selectAllByPagination(int page, int itemSizePerPage);
	void createOne(HotelRIO hotel);
	void updateOne(HotelRIO hotel);
	void daleteOne(HotelRIO hotel);
	int deleteAll();
	void createDB();
	void dropDB();

}

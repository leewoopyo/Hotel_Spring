package kr.ac.kopo.service;

import java.util.Date;
import java.util.List;

import kr.ac.kopo.domain.HotelRIO;
import kr.ac.kopo.dto.HotelSIO;
import kr.ac.kopo.dto.ListSIO;

public interface HotelService {
	
	public void createDB() throws Exception;	//테이블 생성
	public void dropDB() throws Exception;		//테이블 삭제
	
	//create
	public void insert (HotelSIO hotelSIO) throws Exception;	//DB정보를 토대로 데이터 삽입
	
	//read
	public HotelSIO selectOne(String resv_date,int room) throws Exception;		//해당 예약 정보 하나 출력
	public List<ListSIO> selectAll_status() throws Exception; 		//전체 출력
	public List<ListSIO> admin_selectAll_status() throws Exception; 		//전체 출력(관리자 페이지)
	public List<HotelSIO> check_rooms(String resv_date) throws Exception;	//예약된 방이 있나 체크
	
	//update
	public void update (String resv_date,int room,HotelSIO hotelSIO) throws Exception;	//해당 매개변수 해당 데이터 업데이트
	
	//delete
	public void delete(String resv_date,int room) throws Exception;	//예약일, 방 정보 기반 데이터 삭제 
}

package kr.ac.kopo.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.stereotype.Service;

import kr.ac.kopo.dao.HotelRepo;
import kr.ac.kopo.domain.HotelRIO;
import kr.ac.kopo.dto.HotelSIO;
import kr.ac.kopo.dto.ListSIO;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelRepo repo;

	private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

	@Override
	public void createDB() throws Exception {
		repo.createDB();
	}

	@Override
	public void dropDB() throws Exception {
		repo.dropDB();
		
	}

	@Override
	public void allsetDB() throws Exception {
		// ExamRepo클래스의 insert 메소드를 실행 한다.
		// 매개변수는 ExamRIO형의 데이터로 DB에 들어갈 데이터들이다.
	}

	@Override
	public void insert(HotelSIO hotelSIO) throws Exception {
		// ExamSIO형의 데이터를 매개변수로 하여 데이터를 삽입한다.
		repo.createOne(new HotelRIO(hotelSIO.getName(),hotelSIO.getResv_date(),hotelSIO.getRoom(),hotelSIO.getAddr(),hotelSIO.getTelnum(),hotelSIO.getIn_name(),hotelSIO.getComment(),hotelSIO.getWrite_date(),hotelSIO.getProcessing()));
	}

	@Override
	public HotelSIO selectOne(String resv_date, int room) throws Exception {
		HotelRIO hotel = null;
		try {
			hotel = repo.selectOne(resv_date,room);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 담은 데이터를 다시 ExamSIO에 담는다.
		return new HotelSIO(hotel.getName(),hotel.getResv_date(),hotel.getRoom(),hotel.getAddr(),hotel.getTelnum(),hotel.getIn_name(),hotel.getComment().replaceAll("@@개!행!문!자@@", "\n"),hotel.getWrite_date(),hotel.getProcessing());
	}
	
	public String calc_dayOfWeek(int dayOfWeek_number) {
		String dayOfWeek = null;
		
		if(dayOfWeek_number == 1) {
			dayOfWeek = "월";
		}else if(dayOfWeek_number == 2){
			dayOfWeek = "화";
		}else if(dayOfWeek_number == 3){
			dayOfWeek = "수";
		}else if(dayOfWeek_number == 4){
			dayOfWeek = "목";
		}else if(dayOfWeek_number == 5){
			dayOfWeek = "금";
		}else if(dayOfWeek_number == 6){
			dayOfWeek = "토";
		}else if(dayOfWeek_number == 7){
			dayOfWeek = "일";
		}
		
		return dayOfWeek;
	}

	@Override
	public List<ListSIO> selectAll_status() throws Exception {
		
		LocalDate currentDate = LocalDate.now();
		String resv_date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		int dayOfWeek_number = 0;
		String dayOfWeek = null;
		
		
		
		List<HotelRIO> list = null;
		// genelic이 ExamSIO인 list를 하나 선언한다 .
		List<ListSIO> hotellist = new ArrayList<ListSIO>();
		
		String room1 = "선택가능";
		String room2 = "선택가능";
		String room3 = "선택가능";
		
		try {
			list = repo.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// foreach문은 써서 list갯수 만큼 반복하면서 exams에 담겼던 모든 데이터들을 다시 ExamSIO객체를 생성해서 거기에 담아
		// examScores 리스트에 담는다.
		for (int i = 0; i < 30; i++) {
			resv_date = currentDate.plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			dayOfWeek_number = currentDate.plusDays(i).getDayOfWeek().getValue();
			dayOfWeek = calc_dayOfWeek(dayOfWeek_number);
			for(int j = 0; j < list.size(); j++) {
				if(resv_date.equals(format.format(list.get(j).getResv_date()))) {
					if(list.get(j).getRoom() == 1) {
						room1 = list.get(j).getName().replace(list.get(j).getName().substring(1,2),"*");
						break;
					}else {
						room1 = "선택가능";
					}
				}else {
					room1 = "선택가능";
				}
			}	
			for(int j = 0; j < list.size(); j++) {
				if(resv_date.equals(format.format(list.get(j).getResv_date()))) {
					if(list.get(j).getRoom() == 2) {
						room2 = list.get(j).getName().replace(list.get(j).getName().substring(1,2),"*");
						break;
					}else {
						room2 = "선택가능";
					}
				}else {
					room2 = "선택가능";
				}
			}
			
			for(int j = 0; j < list.size(); j++) {
				if(resv_date.equals(format.format(list.get(j).getResv_date()))) {
					if(list.get(j).getRoom() == 3) {
						room3 = list.get(j).getName().replace(list.get(j).getName().substring(1,2),"*");
						break;
					}else {
						room3 = "선택가능";
					}
				}else {
					room3 = "선택가능";
				}
			}	
				hotellist.add(new ListSIO(resv_date,dayOfWeek,room1,room2,room3));
			}
		
		// 리스트를 리턴한다.
		return hotellist;
	}
	
	@Override
	public List<ListSIO> admin_selectAll_status() throws Exception {
		
		LocalDate currentDate = LocalDate.now();
		String resv_date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		int dayOfWeek_number = 0;
		String dayOfWeek = null;
		
		
		
		List<HotelRIO> list = null;
		// genelic이 ExamSIO인 list를 하나 선언한다 .
		List<ListSIO> hotellist = new ArrayList<ListSIO>();
		
		String room1 = "선택가능";
		String room2 = "선택가능";
		String room3 = "선택가능";
		
		try {
			list = repo.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// foreach문은 써서 list갯수 만큼 반복하면서 exams에 담겼던 모든 데이터들을 다시 ExamSIO객체를 생성해서 거기에 담아
		// examScores 리스트에 담는다.
		for (int i = 0; i < 30; i++) {
			resv_date = currentDate.plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			dayOfWeek_number = currentDate.plusDays(i).getDayOfWeek().getValue();
			dayOfWeek = calc_dayOfWeek(dayOfWeek_number);
			for(int j = 0; j < list.size(); j++) {
				if(resv_date.equals(format.format(list.get(j).getResv_date()))) {
					if(list.get(j).getRoom() == 1) {
						room1 = list.get(j).getName();
						break;
					}else {
						room1 = "선택가능";
					}
				}else {
					room1 = "선택가능";
				}
			}	
			for(int j = 0; j < list.size(); j++) {
				if(resv_date.equals(format.format(list.get(j).getResv_date()))) {
					if(list.get(j).getRoom() == 2) {
						room2 = list.get(j).getName();
						break;
					}else {
						room2 = "선택가능";
					}
				}else {
					room2 = "선택가능";
				}
			}
			
			for(int j = 0; j < list.size(); j++) {
				if(resv_date.equals(format.format(list.get(j).getResv_date()))) {
					if(list.get(j).getRoom() == 3) {
						room3 = list.get(j).getName();
						break;
					}else {
						room3 = "선택가능";
					}
				}else {
					room3 = "선택가능";
				}
			}	
			hotellist.add(new ListSIO(resv_date,dayOfWeek,room1,room2,room3));
		}
		
		// 리스트를 리턴한다.
		return hotellist;
	}

	@Override
	public List<HotelSIO> selectAllByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String resv_date,int room,HotelSIO hotelSIO) throws Exception {
		repo.updateOne(resv_date,room,new HotelRIO(hotelSIO.getName(),hotelSIO.getResv_date(),hotelSIO.getRoom(),hotelSIO.getAddr(),hotelSIO.getTelnum(),hotelSIO.getIn_name(),hotelSIO.getComment(),hotelSIO.getWrite_date(),hotelSIO.getProcessing()));
		
	}

	@Override
	public void delete(String resv_date,int room) throws Exception {
		repo.daleteOne(resv_date, room);
	}


	@Override
	public List<HotelSIO> check_rooms(String resv_date) throws Exception {
		
		List<HotelRIO> all_list = null;
		List<HotelSIO> list = new ArrayList<HotelSIO>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			all_list = repo.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < all_list.size();i ++) {
			if(resv_date.equals(format.format(all_list.get(i).getResv_date()))) {
				list.add(new HotelSIO(all_list.get(i).getName(),all_list.get(i).getResv_date(),all_list.get(i).getRoom(),all_list.get(i).getAddr(),all_list.get(i).getTelnum(),all_list.get(i).getIn_name(),all_list.get(i).getComment(),all_list.get(i).getWrite_date(),all_list.get(i).getProcessing()));							
			}
		}
		return list;
	}
	
	
	
	

}

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
	
	//숫자로 된 요일 값을 문자로 치환하기 위한 변수
	public String calc_dayOfWeek(int dayOfWeek_number) {
		String dayOfWeek = null;
		//localdate에서는 요일이 숫자로 나와서 해당 숫자를 문자로 반환하고 
		//다시 string형으로 반환한다. 
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
		//문자로 치환된 변수를 반환한다.
		return dayOfWeek;
	}
	
	//전체 예약 상황을 표시하는 리스트를 출력하기 위한 메소드
	@Override
	public List<ListSIO> selectAll_status() throws Exception {
		//오늘 날짜 가져옴
		LocalDate currentDate = LocalDate.now();
		String resv_date = null;	//예약일을 담는 string변수
		//date형을 string으로 담기 위한 포맷
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		int dayOfWeek_number = 0;	//숫자로 된 요일을 담는 변수
		String dayOfWeek = null;	//문자로 된 요일을 담는 변수
		
		//레포지토리에서 받을 list생성
		List<HotelRIO> list = null;
		// genelic이 ExamSIO인 list를 하나 선언한다 .
		List<ListSIO> hotellist = new ArrayList<ListSIO>();
		//30일 동안의 방 상태를 저장하기 위한 변수
		String room1 = "선택가능";
		String room2 = "선택가능";
		String room3 = "선택가능";
		
		//list에 DB에서 가져온 전체 데이터를 가져온다.
		try {
			list = repo.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 예약 상황은 30일 동안 출력하기 때문에 30번을 반복하는 반복문 작성
		for (int i = 0; i < 30; i++) {
			//resv_date변수에  오늘부터 시작해서 29일까지 더해서 해당 변수에 담음
			resv_date = currentDate.plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			//각 일자마다 숫자로된 요일을 담는다. 
			dayOfWeek_number = currentDate.plusDays(i).getDayOfWeek().getValue();
			//작성한 함수를 통해 숫자로된 요일을 문자로 치환한다.
			dayOfWeek = calc_dayOfWeek(dayOfWeek_number);
			//list에 담았던 전체 데이터를 서비스에 담는다.
			for(int j = 0; j < list.size(); j++) {
				//list에 저장된 예약일과 위에서 설정한 30일 동안의 날자와 같은 값이 있나 확인(해당 일자에 예약이 있나 확인)
				if(resv_date.equals(format.format(list.get(j).getResv_date()))) {
					//해당 일자에 예약 된 정보가  있으면
					//그 데이터가 어떤 방 정보를 가지고 있는지 확인
					if(list.get(j).getRoom() == 1) {
						//1번 방이라면 해당 데이터의 예약자 이름을 명시해 변수에 저장하고 2번째 글자는 *로 처리
						room1 = list.get(j).getName().replace(list.get(j).getName().substring(1,2),"*");
						//해당 데이터가변수에 저장이 되었음면, 다른 변수에 덮으면 안되기 때문에 break한다.
						break;
					}else {
						//만약 해당하는 방 정보가 없으면 이름 대신 '선택가능' 이라는 글자가 나오도록 한다.
						room1 = "선택가능";
					}
				}else {
					//같은 일자가 없다면 해당 변수는 '선택가능'이라는 글자가 나온다. 
					room1 = "선택가능";
				}
			}
			//해당 for문도 위와 완전이 똑같다. 
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
			//해당 for문도 위와 완전이 똑같다. 
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
				//room1,room2,room3 변수와 날짜, 요일 변수까지 모두 설정이 되었으면 
				//ListSIO형의 클래스 객체를 만들어 해당 변수들의 값을 넣는다.
				//해당 작업을 30일까지 반복한다.
				//ListSIO클래스 예약 상황에 필요한 정보만을 모아둔 클래스이다.
				hotellist.add(new ListSIO(resv_date,dayOfWeek,room1,room2,room3));
			}
		
		// 값이 저장된 리스트를 리턴한다.
		return hotellist;
	}

	@Override
	public void insert(HotelSIO hotelSIO) throws Exception {
		// HotelSIO형의 데이터를 매개변수로 하여 데이터를 삽입한다.
		repo.createOne(new HotelRIO(hotelSIO.getName(),hotelSIO.getResv_date(),hotelSIO.getRoom(),hotelSIO.getAddr(),hotelSIO.getTelnum(),hotelSIO.getIn_name(),hotelSIO.getComment(),hotelSIO.getWrite_date(),hotelSIO.getProcessing()));
	}

	@Override
	public List<HotelSIO> check_rooms(String resv_date) throws Exception {
		
		//전체 리스트를 가져올 list생성
		List<HotelRIO> all_list = null;
		//원하는 방 정보를 담을 list생성 
		List<HotelSIO> list = new ArrayList<HotelSIO>();
		//date형 변수를 비교하기 위해 String형으로 만들기 위한 객체
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//전체 데이터를 가져오고 
		try {
			all_list = repo.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//매개변수로 받은 예약일에 해당하는 데이터만 다시 list에 담는다.
		for(int i = 0; i < all_list.size();i ++) {
			if(resv_date.equals(format.format(all_list.get(i).getResv_date()))) {
				list.add(new HotelSIO(all_list.get(i).getName(),all_list.get(i).getResv_date(),all_list.get(i).getRoom(),all_list.get(i).getAddr(),all_list.get(i).getTelnum(),all_list.get(i).getIn_name(),all_list.get(i).getComment(),all_list.get(i).getWrite_date(),all_list.get(i).getProcessing()));							
			}
		}
		//해당 리스트 반환
		return list;
	}
	
	//selectAll_status()와 완전히 같은 기능이다.
	//단지, name값을 넣을 때 2번째 문자열의 이름을 수정하지 않는다는 것이다.
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
	
	//예약일과 방 정보를 매개변수로 하여 일치하는 데이터를 출력하는 메소드
	@Override
	public HotelSIO selectOne(String resv_date, int room) throws Exception {
		//데이터를 담을 HptelRIO형의 데이터를 생성
		HotelRIO hotel = null;
		//레포지토리에서 함수를 실행해서 해당 데이터를 받음 
		try {
			hotel = repo.selectOne(resv_date,room);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 담은 데이터를 HotelSIO 형태로 반환한다.
		return new HotelSIO(hotel.getName(),hotel.getResv_date(),hotel.getRoom(),hotel.getAddr(),hotel.getTelnum(),hotel.getIn_name(),hotel.getComment().replaceAll("@@개!행!문!자@@", "\n"),hotel.getWrite_date(),hotel.getProcessing());
	}

	//데이터 수정하는 메소드
	@Override
	public void update(String resv_date,int room,HotelSIO hotelSIO) throws Exception {
		//예약일과 방정보를 조건으로 해서 HotelSIO에 담긴 데이터로 수정하는 메소드
		repo.updateOne(resv_date,room,new HotelRIO(hotelSIO.getName(),hotelSIO.getResv_date(),hotelSIO.getRoom(),hotelSIO.getAddr(),hotelSIO.getTelnum(),hotelSIO.getIn_name(),hotelSIO.getComment(),hotelSIO.getWrite_date(),hotelSIO.getProcessing()));
		
	}
	//데이터 삭제하는 메소드
	@Override
	public void delete(String resv_date,int room) throws Exception {
		//예약일과 방정보를 조건으로 해당 데이터 삭제
		repo.daleteOne(resv_date, room);
	}

}

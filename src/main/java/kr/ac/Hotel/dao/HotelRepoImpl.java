package kr.ac.Hotel.dao;

import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.Hotel.domain.HotelRIO;
import kr.ac.Hotel.domain.HotelRIO_Idclass;

@Transactional
@Repository
public class HotelRepoImpl implements HotelRepo {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(HotelRepoImpl.class);
	
	//hibernate 쿼리문을 실행할 sessrion이 필요함
	private Session getSession() {
		logger.info("getSession().start");
		Session ss = null;
		try {
			ss = sessionFactory.getCurrentSession();
			System.out.println("getCurrentSession");
		}catch(org.hibernate.HibernateException he) {
			ss = sessionFactory.openSession();
			System.out.println("openSession");
		}
		return ss;
	}

	//전체 데이터를 리스트에 담아서 반환
	@SuppressWarnings("unchecked")
	@Override
	public List<HotelRIO> selectAll() {
		//전체 데이터 출력하는 hql문 작성 
		String hql = "from HotelRIO";
		//쿼리를 실행하고 그 정보를 Query변수에 담음
		Query query = getSession().createQuery(hql);
		//쿼리 클래스의 list함수를 통해 정보를 list형으로 반환
		return query.list();	
	}
	
	//매개변수로 받은 데이터를 DB에 insert하는 함수
	@Override
	public void createOne(HotelRIO hotel) {
		//해당 데이터를 insert하는 메소드(saveorupdate)
		getSession().saveOrUpdate(hotel);
	}
	
	//예약일과 방 정보를 매개변수로 해당 데이터를 출력
	@Override
	public HotelRIO selectOne(String resv_date, int room) {
		//쿼리문을 담고
		String hql = "From HotelRIO e where e.resv_date = '" + resv_date + "' and e.room = " + room;
		//쿼리문을 실행한다.
		Query query = getSession().createQuery(hql);
		//해당 결과를 HotelRIO 형태로 반환한다.
		return (HotelRIO) query.uniqueResult();
	}
	
	//데이터를 수정하는 메소드
	@Override
	public void updateOne(String resv_date,int room, HotelRIO hotel) {
		//해당 조건에서 수정하는  쿼리문을 작성
		String hql = "update HotelRIO e set "
				+ "e.name = :name, "
				+ "e.resv_date = :resv_date, "
				+ "e.room = :room, "
				+ "e.addr = :addr, "
				+ "e.telnum = :telnum, "
				+ "e.in_name = :in_name, "
				+ "e.comment = :comment, "
				+ "e.write_date = :write_date, "
				+ "e.processing = :processing "
				+ "where e.resv_date = '" + resv_date + "' and e.room = " + room;
		
		//쿼리문의 실행 정보를 담은 query 변수를 만들고
		Query query = getSession().createQuery(hql);
		
		//수정할 값들을 세팅한다.
		query.setParameter("name", hotel.getName());
		query.setParameter("resv_date", hotel.getResv_date());
		query.setParameter("room", hotel.getRoom());
		query.setParameter("addr", hotel.getAddr());
		query.setParameter("telnum", hotel.getTelnum());
		query.setParameter("in_name", hotel.getIn_name());
		query.setParameter("comment", hotel.getComment());
		query.setParameter("write_date", hotel.getWrite_date());
		query.setParameter("processing", hotel.getProcessing());
		
		//쿼리문을 실행한다.
		query.executeUpdate();
	}

	//예약일과 방 정보를 조건으로 데이터를 삭제하는 메소드
	@Override
	public void daleteOne(String resv_date,int room) {
		//쿼리문을 담고
		String hql = "delete from HotelRIO e "
				+ "where e.resv_date = '" + resv_date + "' and e.room = " + room;
		//쿼리문 실행 정보를 담고 있는 query객체를 생성한다
		Query query = getSession().createQuery(hql);
		//쿼리문을 실행
		query.executeUpdate();
	}


	@Override
	public void createDB() {
		Statement stmt;
		try {
			stmt = ((SessionImpl)getSession()).connection().createStatement();
			stmt.execute(
					"create table resv ( "+
					"name varchar(20), "+   //성명	
					"resv_date date not null, "+  //예약일
					"room int not null, "+ //예약방 1:VIP룸 2:일반룸 3:합리적인룸
					"addr varchar(100), "+  //주소
					"telnum varchar(20), "+ //연락처
					"in_name  varchar(20), "+ //입금자명
					"comment  text, "+ //남기실말
					"write_date date, "+// 예약한(이 글을 쓴) 날짜
					"processing int, "+//현재 진행 1:예약완료 2: 입금완료(예약확정) 3: 환불요청 4:...
					"primary key (resv_date,room))"+  // 예약일과 룸을 합쳐서 DB의 키로 사용
					"DEFAULT CHARSET=utf8"); 
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dropDB() {
		Statement stmt;
		try {
			stmt = ((SessionImpl)getSession()).connection().createStatement();
			stmt.execute("drop table resv;");
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}

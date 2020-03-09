package kr.ac.kopo.dao;

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

import kr.ac.kopo.domain.HotelRIO;
import kr.ac.kopo.domain.HotelRIO_Idclass;

@Transactional
@Repository
public class HotelRepoImpl implements HotelRepo {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(HotelRepoImpl.class);

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

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HotelRIO selectOne(String resv_date, int room) {
		String hql = "From HotelRIO e where e.resv_date = '" + resv_date + "' and e.room = " + room;
		Query query = getSession().createQuery(hql);
		return (HotelRIO) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HotelRIO> selectAll() {
		String hql = "from HotelRIO";
		Query query = getSession().createQuery(hql);
		return query.list();	
	}

	@Override
	public List<HotelRIO> selectAllByPagination(int page, int itemSizePerPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOne(HotelRIO hotel) {
		getSession().saveOrUpdate(hotel);
	}

	@Override
	public void updateOne(String resv_date,int room, HotelRIO hotel) {
		/* "update Stock set stockName = :stockName where stockCode = :stockCode" */
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
		
		Query query = getSession().createQuery(hql);
		
		query.setParameter("name", hotel.getName());
		query.setParameter("resv_date", hotel.getResv_date());
		query.setParameter("room", hotel.getRoom());
		query.setParameter("addr", hotel.getAddr());
		query.setParameter("telnum", hotel.getTelnum());
		query.setParameter("in_name", hotel.getIn_name());
		query.setParameter("comment", hotel.getComment());
		query.setParameter("write_date", hotel.getWrite_date());
		query.setParameter("processing", hotel.getProcessing());
		
		query.executeUpdate();
	}

	@Override
	public void daleteOne(String resv_date,int room) {
		String hql = "delete from HotelRIO e "
				+ "where e.resv_date = '" + resv_date + "' and e.room = " + room;
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return 0;
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

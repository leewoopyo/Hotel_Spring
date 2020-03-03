package kr.ac.kopo.dao;

import java.sql.Statement;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.kopo.domain.ExamRIO;


@Transactional
@Repository
public class ExamRepoImpl implements ExamRepo{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ExamRepoImpl.class);

	private Session getSession() {
		logger.info("getSession().start");
		Session ss = null;
		try {
			ss = sessionFactory.getCurrentSession();
			System.out.println("111");
		}catch(org.hibernate.HibernateException he) {
			ss = sessionFactory.openSession();
			System.out.println("222");
		}
		return ss;
	}
	
	@Override
	public Long count() {
		logger.info("count().start");
		String hql = "select count(*) from ExamRIO";
		Query query = getSession().createQuery(hql);
		Long totalCount = (Long) query.uniqueResult();
		return totalCount;
	}

	@Override
	public ExamRIO selectOne(int studentid) {
//		ExamRIO exam = (ExamRIO)getSession().get(ExamRIO.class, id); 
		String hql = "From ExamRIO e where e.studentid = " + studentid;
		Query query = getSession().createQuery(hql);
		return (ExamRIO) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamRIO> selectAll() {
		String hql = "from ExamRIO";
		Query query = getSession().createQuery(hql);
		return query.list();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamRIO> selectAllByPagination(int page, int itemSizePerPage) {
		String hql = "from ExamRIO order by studentid";
		Query query = getSession().createQuery(hql);
		query.setFirstResult((page-1) * itemSizePerPage);
		query.setMaxResults(itemSizePerPage);
		return query.list();
	}

	@Override
	public void createOne(ExamRIO exam) {
		getSession().saveOrUpdate(exam);
		//getSession().flush();
	}

	@Override
	public void updateOne(ExamRIO exam) {
		getSession().saveOrUpdate(exam);
	}

	@Override
	public void daleteOne(ExamRIO exam) {
		getSession().delete(exam);
	}

	@Override
	public int deleteAll() {
		String hql = "delete from ExamRIO";
		Query query = getSession().createQuery(hql);
		return query.executeUpdate();
	}

	@Override
	public void createDB() {
		Statement stmt;
		try {
			stmt = ((SessionImpl)getSession()).connection().createStatement();
			stmt.execute("create table examtable(" +
					"name varchar(20)," +
					"studentid int not null primary key," +
					"kor int," + "eng int," +
					"mat int)" +
					"DEFAULT CHARSET=utf8;");
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
			stmt.execute("drop table examtable;");
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

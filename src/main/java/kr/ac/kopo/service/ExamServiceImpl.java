package kr.ac.kopo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kopo.dao.ExamRepo;
import kr.ac.kopo.domain.ExamRIO;
import kr.ac.kopo.dto.ExamSIO;



@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamRepo repo;

	private static final Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);

	@Override
	public void createDB() throws Exception {
		repo.createDB(); // ExamRepo클래스의 createDB메소드를 실행함(테이블 생성)
	}

	@Override
	public void dropDB() throws Exception {
		repo.dropDB(); // ExamRepo클래스의 dropDB메소드를 실행함(테이블 삭제)
	}

	@Override
	public void allsetDB() throws Exception {
		// ExamRepo클래스의 insert 메소드를 실행 한다.
		// 매개변수는 ExamRIO형의 데이터로 DB에 들어갈 데이터들이다.

		logger.info("allsetDB().start");

		this.insert(new ExamSIO("나연", 209901, 95, 100, 95));
		this.insert(new ExamSIO("정연", 209902, 90, 90, 100));
		this.insert(new ExamSIO("모모", 209903, 85, 80, 95));
		this.insert(new ExamSIO("사나", 209904, 75, 100, 85));
		this.insert(new ExamSIO("지영", 209905, 85, 70, 75));
		this.insert(new ExamSIO("미나", 209906, 95, 80, 95));
		this.insert(new ExamSIO("다현", 209907, 85, 100, 85));
		this.insert(new ExamSIO("채영", 209908, 75, 90, 65));
		this.insert(new ExamSIO("쯔위", 209909, 85, 80, 95));

	}

	@Override
	public void insert(ExamSIO examSIO) throws Exception {
		// ExamSIO형의 데이터를 매개변수로 하여 데이터를 삽입한다.
		repo.createOne(new ExamRIO(examSIO.getName(), examSIO.getStudentid(), examSIO.getKor(), examSIO.getEng(),
				examSIO.getMat()));
	}

	@Override
	public ExamSIO selectOne(int studentid) throws Exception {
		// 학번을 매개변수로 나온 select 결과를 examRIO형으로 리턴해서 받고,
		ExamRIO exam = null;
		try {
			exam = repo.selectOne(studentid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 담은 데이터를 다시 ExamSIO에 담는다.
		return new ExamSIO(exam.getName(), exam.getStudentid(), exam.getKor(), exam.getEng(), exam.getMat());
	}

	@Override
	public List<ExamSIO> selectAll() throws Exception {
		// ExamRepo클래스의 getAllrecords(전체 출력)메소드를 통해 list에 데이터가 전부 담긴다.
		List<ExamRIO> exams = null;
		try {
			exams = repo.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// genelic이 ExamSIO인 list를 하나 선언한다 .
		List<ExamSIO> examScores = new ArrayList<ExamSIO>();
		// foreach문은 써서 list갯수 만큼 반복하면서 exams에 담겼던 모든 데이터들을 다시 ExamSIO객체를 생성해서 거기에 담아
		// examScores 리스트에 담는다.
		for (int i = 0; i < exams.size(); i++) {
			examScores.add(new ExamSIO(exams.get(i).getName(), exams.get(i).getStudentid(), exams.get(i).getKor(),
					exams.get(i).getEng(), exams.get(i).getMat()));
		}
		// 리스트를 리턴한다.
		return examScores;
	}

	@Override
	public List<ExamSIO> selectAllByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int studentid, ExamSIO examSIO) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(ExamSIO examSIO) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(int studentid) throws Exception {
	}

	@Override
	public void delete(ExamSIO examSIO) throws Exception {
		// TODO Auto-generated method stub
	}
}

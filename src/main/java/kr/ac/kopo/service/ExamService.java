package kr.ac.kopo.service;

import java.util.List;

import kr.ac.kopo.dto.ExamSIO;

public interface ExamService {
	
	public void createDB() throws Exception;	//테이블 생성
	public void dropDB() throws Exception;		//테이블 삭제
	public void allsetDB() throws Exception;	//데이터 삽입

	public void insert (ExamSIO examSIO) throws Exception;	//DB정보를 토대로 데이터 삽입
	
	//read
	public ExamSIO selectOne(int studentid) throws Exception;		//해당 학번 하나 출력
	public List<ExamSIO> selectAll() throws Exception; 		//전체 출력
	public List<ExamSIO> selectAllByName(String name) throws Exception;//이름 기반 출력
	
	//update
	public void update (int studentid, ExamSIO examSIO) throws Exception;	//해당 매개변수 해당 데이터 업데이트
	public void update (ExamSIO examSIO) throws Exception;	//전체 업데이트 
	
	//delete
	public void delete(int studentid) throws Exception;	//학번 기반 데이터 삭제 
	public void delete(ExamSIO examSIO) throws Exception;	//매개변수 기반 데이터 삭제
}

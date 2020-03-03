package kr.ac.kopo.dao;

import java.util.List;

import kr.ac.kopo.domain.ExamRIO;

public interface ExamRepo {
	Long count();
	ExamRIO selectOne(int studentid);
	List<ExamRIO> selectAll();
	List<ExamRIO> selectAllByPagination(int page, int itemSizePerPage);
	void createOne(ExamRIO exam);
	void updateOne(ExamRIO exam);
	void daleteOne(ExamRIO exam);
	int deleteAll();
	void createDB();
	void dropDB();
}

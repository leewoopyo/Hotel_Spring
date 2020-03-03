package kr.ac.kopo.dto;

public class ExamSIO {
	
	//다룰 데이터 선언
	private String name;
	private int studentid;
	private int kor;
	private int eng;
	private int mat;
	
	//constructor 선언 빈 생성자와 데이터가 같이 들어있는 생성자를 같이 만들어 준다.
	public ExamSIO() {
		super();
	}
	public ExamSIO(String name, int studentid, int kor, int eng, int mat) {
		super();
		this.name = name;
		this.studentid = studentid;
		this.kor = kor;
		this.eng = eng;
		this.mat = mat;
	}
	
	//getter, setter 메소드 생성 : 데이터를 불러오고 적용할 때 사용한다. 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMat() {
		return mat;
	}
	public void setMat(int mat) {
		this.mat = mat;
	}

}

package kr.ac.kopo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//@Entity
@Table(name="examtable")
public class ExamRIO {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="studentid")
	private int studentid;
	
	@Column(name="name")
	private String name;
	
	
	@Column(name="kor")
	private int kor;
	
	@Column(name="eng")
	private int eng;
	
	@Column(name="mat")
	private int mat;
	
	//constructor 선언 빈 생성자와 데이터가 같이 들어있는 생성자를 같이 만들어 준다.
	public ExamRIO() {
		super();
	}
	public ExamRIO(String name, int studentid, int kor, int eng, int mat) {
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

package kr.ac.kopo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.kopo.service.ExamService;
import kr.ac.kopo.service.HotelService;


/**
 * Handles requests for the application home page.
 */


//해당 클래스가 controller임을 표시함
@Controller
public class HomeController {
	//service를 autowired 해서 Controller, Service, Repository, JdbcTemplate를 묶음
//	@Autowired
//	private ExamService service;
	
	@Autowired
	private HotelService service;
	
	//로그 객체를 선언
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//해당경로(기본 디렉토리)로 URL이동하면 index로 감
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		logger.info("index.jsp start");

		return "index";
	}
	
	//URL에 menu를 더 적으면 해당 파일을 찾아감
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu() {
		logger.info("menu.jsp start");
		
		return "menu";
	}
	//URL에 home를 더 적으면 해당 파일을 찾아감
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		logger.info("menu.jsp start");
		
		return "home";
	}
	
//	//allsetDB으로 갈 controller 메소드
//	//URL에 allsetDB를 치면 
//	@RequestMapping(value = "/allsetDB", method = RequestMethod.GET)
//	public String allsetDB(Model model) {
//		//로그 표시하고 
//		logger.info("allsetDB.jsp start");
//		//작업 메시지 표시할 변수 선언
//		String ret=null;
//		try {
//			//위에서 선언한 service에서 allsetDB메소드를 선언한다.
//			service.allsetDB();
//			//성공하면 위 메시지 를 담고
//			ret = "DB allset 성공";
//		}catch(Exception e) {
//			e.printStackTrace();
//			//위에서 예외처리가 났다면 아래 문구를 넣는다.
//			ret = "DB allset 실패" + e;
//		}
//		//각 들어간 문구를 model에 담고
//		model.addAttribute("msg",ret);
//		//allsetDB.jsp에  보낸다. 
//		return "allsetDB";
//	}
//	
//	//allviewDB으로 갈 controller 메소드
//	//URL에 allviewDB를 치면 
//	@RequestMapping(value = "/allviewDB", method = RequestMethod.GET)
//	public String allviewDB(Model model) {
//		//로그 표시하고
//		logger.info("allviewDB.jsp start");
//		
//		try {
//			//service에서 selectAll한 결과를 list라는 이름으로 model에 담고
//			model.addAttribute("list",service.selectAll());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		//allviewDB.jsp로 간다.
//		return "allviewDB";
//	}
	
	//createDB으로 갈 controller 메소드
	//URL에 createDB를 치면 
	@RequestMapping(value = "/createDB", method = RequestMethod.GET)
	public String createDB(Model model) {
		//로그 문구가 뜨고
		logger.info("createDB.jsp start");
		//메시지를 담을 변수 선언
		String ret = null;
		try {
			//service객체의 createDB메소드를 불러온다.
			service.createDB();
			//결과 메시지를 변수에 담는다.
			ret = "DB create 성공";
		}catch(Exception e) {
			e.printStackTrace();
			//예외처리 날 시 메시지를 변수에 담는다.
			ret = "DB create 실패" + e;
		}
		//model에 해당 메시지를 담고
		model.addAttribute("msg",ret);
		//createDB.jsp에 보낸다.
		return "createDB";
	}
	
	//dropDB으로 갈 controller 메소드
	//URL에 dropDB를 치면
	@RequestMapping(value = "/dropDB", method = RequestMethod.GET)
	public String dropDB(Model model) {
		//로그 문구가 뜨고
		logger.info("dropDB.jsp start");
		//메시지를 담을 변수 선언
		String ret = null;
		try {
			//service객체의 dropDB메소드를 불러온다.
			service.dropDB();
			//아래 메시지를 변수에 담는다.
			ret = "DB drop 성공";
		}catch(Exception e) {
			e.printStackTrace();
			//예외처리 시 아래 메시지를 변수에 담는다.
			ret = "DB drop 실패" + e;
		}
		//메시지를 model에 담고
		model.addAttribute("msg",ret);
		//dropDB.jsp에 간다.
		return "dropDB";
	}
//	
//	//oneviewDB으로 갈 controller 메소드
//	//URL에 oneviewDB를 치고, 뒤에 다시 /하고 파라메터 이름을 적는다.
//	@RequestMapping(value = "/oneviewDB/{studentid}", method = RequestMethod.GET)
//	//Pathvariable 어노테이션으로 studentid 값을 studentid라는 이름의 매개변수로 만든다.
//	public String oneviewDB(@PathVariable("studentid") int studentid, Model model) {
//		//로그 출력하고
//		logger.info("oneviewDB.jsp start studentid = " + studentid);
//		
//		try {
//			//model에 list라는 이름으로 service의 selectOne 메소드의 출력 결과를 담고
//			model.addAttribute("list",service.selectOne(studentid));
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		//oneviewDB.jsp 로 이동한다.
//		return "oneviewDB";
//	}
//	
//	//fullcalendar 테스트
//
//	//해당경로(기본 디렉토리)로 URL이동하면 index로 감
//	@RequestMapping(value = "/fullcalendar_test/test1", method = RequestMethod.GET)
//	public String fullcalendar() {
//		logger.info("test1.jsp start");
//
//		return "fullcalendar_test/test1";
//	}
//	//해당경로(기본 디렉토리)로 URL이동하면 index로 감
//	@RequestMapping(value = "/fullcalendar_test/test2", method = RequestMethod.GET)
//	public String fullcalendar2() {
//		logger.info("test2.jsp start");
//		
//		return "fullcalendar_test/test2";
//	}
//	@RequestMapping(value = "/fullcalendar_test/test3", method = RequestMethod.GET)
//	public String fullcalendar3() {
//		logger.info("test3.jsp start");
//		
//		return "fullcalendar_test/test3";
//	}
	
	
	
}

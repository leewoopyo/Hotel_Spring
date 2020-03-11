package kr.ac.Hotel;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.Session;

import kr.ac.Hotel.domain.HotelRIO;
import kr.ac.Hotel.dto.HotelSIO;
import kr.ac.Hotel.dto.ListSIO;
import kr.ac.Hotel.service.HotelService;


/**
 * Handles requests for the application home page.
 */

//해당 클래스가 controller임을 표시함
@Controller
public class HomeController {
	
	//service를 autowired 해서 Controller, Service, Repository를 묶음
	@Autowired
	private HotelService service;
	
	//로그 객체를 선언
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//해당경로(기본 디렉토리)로 URL이동하면 index로 감
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("index.jsp start");
		//메인화면 구성에 예약 상황이 나오게 하기 위해서 {type} 이란 이름의 모델을 만들어 
		//type에 allviewDB 라는 값을 넣는다 그러면 index에서 해당 값을 읽어서 allviewDB를 
		//불러온다
		model.addAttribute("type","allviewDB");
		//
		return "index";
	}
	
	//index에서 {type}값을 확인 후 값이 allviewDB이면 해당 컨트롤러 메소드로 이동된다.
	@RequestMapping(value = "/allviewDB", method = RequestMethod.GET)
	public String allviewDB(Model model) {
		logger.info("allviewDB.jsp start");
		
		try {
			//service에서 selectAll한 결과를 list라는 이름으로 model에 담고
			model.addAttribute("list",service.selectAll_status());
		}catch(Exception e) {
			e.printStackTrace();
		}
		//allviewDB.jsp를 불러온다
		return "allviewDB";
	}
	
	//allviewDB에서 선택 가능 링크 클릭하면 해당 컨트롤러 이동
	//get방식으로 선택한 부분의 예약일(resv_date)과 방정보(room)을 파라미터 값으로 보냄
	@RequestMapping(value = "/go_insertDB", method = RequestMethod.GET)
	public String go_insertDB(Model model,String resv_date,int room) {
		logger.info("go_insertDB start");
		//모델에 값들을 담음
		model.addAttribute("resv_date", resv_date);		//예약일
		model.addAttribute("room", room);	//방 정보 
		model.addAttribute("type","insertDB");	//index에서 출력하고자 하는 페이지
		//해당 모델을 담고 index로 이동한다.
		return "index";
	}
	
	//index에서 insertDB페이지로 이동하능 URL 
	@RequestMapping(value = "/insertDB", method = RequestMethod.GET)
	public String insertDB(Model model,String resv_date,int room) {
		logger.info("insertDB.jsp start");
		
		//해당 페이지로 보낼 예약일와 방 정보를 모델에 담고
		model.addAttribute("resv_date", resv_date);
		model.addAttribute("room", room);
		//insertDB로 보냄 
		return "insertDB";
	}
	
	//ajax통신을 위한 메소드 @ResponsBody 어노테이션을 사용한다.
		//파라미터 값으로 날짜가 들어 왔을 때 
		@ResponseBody
		@RequestMapping(value = "/date_check", method = RequestMethod.GET)
		public Map<String, Object> date_check(@RequestParam("resv_date") String resv_date) {
			logger.info("date_check ajax start");
			
			//map객체를 통해서 ajax를 통해 response할 데이터를 json형식으로 보낼 수 있다.
			Map<String, Object> map = new HashMap<String, Object>();
			//list객체를 만든다.
			List<HotelSIO> list = new ArrayList<HotelSIO>();
			//localdate 객체로 오늘 날짜를 가져온다.
			LocalDate currentDate = LocalDate.now();
			//메시지 변수를 선언한다.
			String message = "";
			
			//service를 통해서 매개변수로 예약일을 넣고 
			//해당하는 방 정보들을 list에 담는다.
			try {
				list = service.check_rooms(resv_date);			
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			//list를 반복하면서 
			for(int i = 0; i < list.size();i++) {
				//System.out.println(list.get(i).getRoom());
				//해당 방 정보가 1,2,3 인지 판단해서
				//데이터가 있다면 각 맵의 key값에 맞에 1,2,3을 넣어준다.
				if(list.get(i).getRoom() == 1) {
					map.put("deluxe", 1);
				}else if(list.get(i).getRoom() == 2) {
					map.put("suite", 2);
				}else if(list.get(i).getRoom() == 3) {
					map.put("royal", 3);
				}
			}
			
			//그리고, 예약 상황을 30일이라서 억지로 해당 일자 바깥으로 예약을 하려 할 시 나오는 문구를 설정하기 위해서 
			//받은 파라미터 값이 오늘부터 30일까지의 날짜에 해당하지 않으면 예약 불가능 문구가 나오도록 변수 지정한다. 
			for(int i = 0; i < 30; i++){
				if(resv_date.equals(currentDate.plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))){
					message = "";
					break;
				}else{
					message = "예약 가능 일자를 벗어났습니다.";
				}
			}
			//그리고 그 메시지를 다시 map에 저장한다.
			map.put("message", message);
			//그리고 map 객체를 다시 반환하면 요청한 페이지에서 json형태로 데이터를 받을 수 있다.
			return map;
		}
	
	//insertDB.jsp에서 예약하기를 누르면 해당 컨트롤러로 이동한다.
	@RequestMapping(value = "/insert_logic", method = RequestMethod.POST)
	public String insert_logic(Model model,String name, @DateTimeFormat(pattern="yyyy-MM-dd") Date resv_date,int room,String addr,String telnum,String in_name,String comment,@DateTimeFormat(pattern="yyyy-MM-dd") Date write_date,int processing) throws UnsupportedEncodingException {
		logger.info("insert_logic start");
		
		//comment 요소의 줄바꿈(개행문자)을 제거하면서 DB에 넣는다.
		//제거하는 이유는 DB조회 시 데이터가 지저분 했기에 제거하였다. 
		//제거에는 2가지 방식이 있는데, 줄바꿈을 가진 시스템 요소를 제거하는 방법과
		//정규식을 이용하여 제거하는 방법 2가지가 있다.
		//String modify_comment = comment.replaceAll(System.getProperty("line.separator"),"");
		String modify_comment = comment.replaceAll("(\r|\n|\r\n|\n\r)","@@개!행!문!자@@");
		
		//HotelSIO객체를 하나 만들고 post로 보낸 파라미터 값을 매개로 해당 객체를 인스턴스 화 한다.
		HotelSIO hotel = new HotelSIO(name,resv_date,room,addr,telnum,in_name,modify_comment,write_date,processing);
		
		//service의 insert하는 함수를 요청한다(매개변수를 넣어 해당 데이터를 insert한다.)
		try {
			//위에서 선언한 service에서 allsetDB메소드를 선언한다.
			service.insert(hotel);
			//model.addAttribute("list",service.selectAll_status());
			//성공하면 위 메시지 를 담고
			System.out.print("DB insert 성공");
		}catch(Exception e) {
			e.printStackTrace();
			//위에서 예외처리가 났다면 아래 문구를 넣는다.
			System.out.print("DB insert 실패" + e);
		}
		//그리고 model에 allviewDB 값을 담아서 예약 상황 표시 부분으로 넘어 갈 수 있도록 한다.
		model.addAttribute("type","allviewDB");
		//그리고 index로 돌아간다.
		return "index";
	}
	
	
	//로그인 하기 위해서 login 버튼 클릭시 해당 메소드로 이동한다.
	//java클래스에서 session을 생성하기 위해 httpsession이라는 클래스를 가져온다.
	//httpsession클래스를 httpservletrequest클래스를 통해 생성 가능하다.
	@RequestMapping(value = "/go_login", method = RequestMethod.GET)
	public String go_login(Model model,HttpServletRequest request) {
		logger.info("go_login");
		//controller에서 session 객체를 만들기 위해서 HttpSession이라는 클래스를 가져온다. 
		HttpSession session = request.getSession();
		String loginOK = null; 		//login_ok라는 session 값을 String으로 담기 위한 변수
		loginOK = (String)session.getAttribute("login_ok");		//login_ok세션의 값을 가져와서 String형 변수loginOK에 담는다.
		
		//만약 loginOK가 yes가 아니면 로그인 페이지로 넘어가야하니까 
		//index에서 로그인 화면으로 이동하기 위해 필요한 모델을 담고 index로 이동한다.
		if(loginOK == null) {
			model.addAttribute("type","login");
			//model.addAttribute("jump","admin_allviewDB");
			return "index";
		}
		//만약 loginOK가 yes가 아니면 로그인 페이지로 넘어가야하니까 
		//index에서 로그인 화면으로 이동하기 위해 필요한 모델을 담고 index로 이동한다.
		if(!loginOK.equals("yes")) {
			model.addAttribute("type","login");
			//model.addAttribute("jump","admin_allviewDB");
			return "index";
		}
		//위의 경우가 아니면 현제 로그인 상태라는 의미이기 때문에 로그인 했을 때의 페이지로 이동하도록 한다.
		model.addAttribute("type","allviewDB");
		return "index";
	}
	
	//로그인 하는 페이지가 표시되는 controller
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		logger.info("login.jsp start");
		//login 페이지를 출력한다.
		return "login";
	}
	
	//비동기 데이터 요청(ajax)으로 
	//id값과 password값을 주었을 때 
	//해당 아이디와 비밀번호가 일치하는지 확인하고 확인 여부를 메시지로 반환함
	@ResponseBody
	@RequestMapping(value = "/login_check", method = RequestMethod.GET)
	public Map<String, Object> login_check(@RequestParam("id") String id,@RequestParam("password") String password) {
		logger.info("login_check start");
		//json 형태로 넣기 위해서 map객체를 선언 
		Map<String, Object> map = new HashMap<String, Object>();
		//메시지를 담을 변수를 선언
		String message = "";
		
		//id가 admin인지, 그리고 password가 admin인지 확인한다.(현제는 DB가 아닌 관리자 계정만 가지고 있다고 가정)
		if(id.replaceAll(" ","").equals("admin")&& password.replaceAll(" ","").equals("admin")){
			//일치하면 아이디에 아무것도 안담고
			message = "";
		}else{
			//일치하지 않으면 해당 문구를 변수에 담는다. 
			message = "아이디 혹은 비밀번호가 일치하지 않습니다.";
		}
		//map에 message를 담는다.
		map.put("message", message);
		//그리고 map을 반환한다.
		return map;
	}
	
	//아이디 비밀번호가 일치 했을 때 오는 컨트롤러
	@RequestMapping(value = "/loginck", method = RequestMethod.POST)
	public String loginck(Model model,String id, String password, String jump,HttpServletRequest request) {
		logger.info("loginck start");
		//세션을 가져온다.
		HttpSession session = request.getSession();
		//세션에 로그인 상태를 저장하는 attibute를 설정
		session.setAttribute("login_ok", "yes");
		//세션에 아이디 값을 저장하는 attibute를 설정
		session.setAttribute("login_id", id);
		//모델에 표시 할 페이지를 담는다.
		model.addAttribute("type","allviewDB");
		//index로 돌아간다.
		return "index";
	}
	
	//logout버튼 클릭 시 오는 컨트롤러
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model,HttpServletRequest request) {
		logger.info("logout start");
		//세션을 가져온다.
		HttpSession session = request.getSession();
		//해당 세션을 삭제한다. 그러면 세션안에 저장 돼 있던 세션값을이 다 사라진다.
		session.invalidate();
		//그리고 돌아갈 페이지를 설정하고 
		model.addAttribute("type","allviewDB");
		//index로 이동한다.
		return "index";
	}
	
	//index에 있는 관리자 페이지 링크를 클릭하면 해당 URL로 이동
	@RequestMapping(value = "/go_admin_allviewDB", method = RequestMethod.GET)
	public String go_admin_allviewDB(Model model) {
		logger.info("go_admin_allviewDB start");
		//모델에 admin_allveiwDB값을 담아서 해당 페이지로 이동 할 수 있게 한다. 
		model.addAttribute("type","admin_allviewDB");
		//index로 돌아간다.
		return "index";
	}
	
	//실제 index에 표시될 admin_allviewDB페이지
	@RequestMapping(value = "/admin_allviewDB", method = RequestMethod.GET)
	public String admin_allviewDB(Model model) {
		logger.info("admin_allviewDB.jsp start");
		try {
			//service에서 selectAll한 결과를 list라는 이름으로 model에 담고
			model.addAttribute("admin_list",service.admin_selectAll_status());
		}catch(Exception e) {
			e.printStackTrace();
		}
		//해당 페이지를 불러온다.
		return "admin/admin_allviewDB";
	}
	
	//URL을 받아서 index에 모델값을 보내줘서 index에서 해당 URL로 이동할 수 있게 함
	@RequestMapping(value = "/go_selectoneDB", method = RequestMethod.GET)
	public String go_selectoneDB(Model model,String resv_date,int room) {
		logger.info("go_selectoneDB start");
		//index에 보내기 전에 모델에 예약일, 방정보, 화면에 출력할 페이지 정보를 담는다.
		model.addAttribute("type","admin_selectoneDB");
		model.addAttribute("resv_date", resv_date);
		model.addAttribute("room", room);
		//index페이지로 간다.
		return "index";
	}
	
	//실제 index에 표시될 selectoneDB페이지
	@RequestMapping(value = "/admin_selectoneDB", method = RequestMethod.GET)
	public String admin_selectoneDB(Model model,String resv_date,int room) {
		logger.info("admin_selectoneDB.jsp start");
		//HotelSIO 형태로 데이터를 담을 변수를 하나 생성한다.
		HotelSIO resv_data = new HotelSIO();
		
		//함수를 통해 예약일과 방 정보를 매개변수로 해서 해당하는 데이터를 반환 받는다.
		try {
			resv_data = service.selectOne(resv_date,room); 
			System.out.println(resv_data.getName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		//해당 데이터를 model에 담는다.
		model.addAttribute("resv_data", resv_data);
		//해당 페이지를 출력한다.
		return "admin/admin_selectoneDB";
	}
	

	//admin_selectoneDB페이지에서 '수정하기' 누르면 해당 컨트롤러로 이동한다.
	@RequestMapping(value = "/admin_updateDB", method = RequestMethod.POST)
	public String admin_updateDB(Model model,String param_resv_date,int param_room,String name, @DateTimeFormat(pattern="yyyy-MM-dd") Date resv_date,int room,String addr,String telnum,String in_name,String comment,@DateTimeFormat(pattern="yyyy-MM-dd") Date write_date,int processing) throws UnsupportedEncodingException {
		logger.info("admin_updateDB start");
		//post로 받은 파라메터 값을 넣어 HotelSIO 형의 객체를 만든다.
		HotelSIO hotel = new HotelSIO(name,resv_date,room,addr,telnum,in_name,comment,write_date,processing);
		
		try {
			//수정 전 예약일과 방 정보의 파라미터를 update에 넣고,수정할 데이터도 매개변수로 넣는다.
			service.update(param_resv_date,param_room,hotel);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//이동할 페이지를 모델에 담고 
		model.addAttribute("type","admin_allviewDB");
		//index로 이동한다.
		return "index";
	}
	
	//admin_selectoneDB페이지에서 '삭제하기' 누르면 해당 컨트롤러로 이동한다.
	@RequestMapping(value = "/admin_deleteDB", method = RequestMethod.POST)
	public String admin_deleteDB(Model model,String param_resv_date,int param_room) {
		logger.info("admin_deleteDB start");
		
		try {
			//예약일과 방 정보를 담아서 delete메소드를 실행한다.
			service.delete(param_resv_date,param_room);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//이동할 페이지를 모델에 담고 
		model.addAttribute("type","admin_allviewDB");
		//index로 이동한다.
		return "index";
	}
	
	
	
//	//createDB으로 갈 controller 메소드
//	//URL에 createDB를 치면 
//	@RequestMapping(value = "/createDB", method = RequestMethod.GET)
//	public String createDB(Model model) {
//		//로그 문구가 뜨고
//		logger.info("createDB.jsp start");
//		//메시지를 담을 변수 선언
//		String ret = null;
//		try {
//			//service객체의 createDB메소드를 불러온다.
//			service.createDB();
//			//결과 메시지를 변수에 담는다.
//			ret = "DB create 성공";
//		}catch(Exception e) {
//			e.printStackTrace();
//			//예외처리 날 시 메시지를 변수에 담는다.
//			ret = "DB create 실패" + e;
//		}
//		//model에 해당 메시지를 담고
//		model.addAttribute("msg",ret);
//		//createDB.jsp에 보낸다.
//		return "createDB";
//	}
//	
//	//dropDB으로 갈 controller 메소드
//	//URL에 dropDB를 치면
//	@RequestMapping(value = "/dropDB", method = RequestMethod.GET)
//	public String dropDB(Model model) {
//		//로그 문구가 뜨고
//		logger.info("dropDB.jsp start");
//		//메시지를 담을 변수 선언
//		String ret = null;
//		try {
//			//service객체의 dropDB메소드를 불러온다.
//			service.dropDB();
//			//아래 메시지를 변수에 담는다.
//			ret = "DB drop 성공";
//		}catch(Exception e) {
//			e.printStackTrace();
//			//예외처리 시 아래 메시지를 변수에 담는다.
//			ret = "DB drop 실패" + e;
//		}
//		//메시지를 model에 담고
//		model.addAttribute("msg",ret);
//		//dropDB.jsp에 간다.
//		return "dropDB";
//	}


	
	
	
}

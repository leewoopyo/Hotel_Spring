<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- index.css파일을 불러오기 css파일, js파일, 이미지등의 파일은 스프링에서는 resources폴더가 기본 경로로 지정이 되어있다. -->
<!-- 불러오는건 기본으로 contextpath경로를 가져오고, 그 뒤에 resources와 해당 파일을 경로를 지정하여 가져온다. -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index.css">

</head>
<body style="margin:0px; width :100%; height:100%;">
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
<!-- 헤더부분  -->
<!-- 최상단 로고와 로그인 로그아웃 관리자 페이지가 나타나는 부분이다. 각 글자들을 해당하는 링크가 있다.  -->
<header class="banner" style="display: flex;">
	<div id="logo_div" ><a id="logo" href="${pageContext.request.contextPath}/">joa hotel</a></div>
	<div id="admin_div" ><a id="admin" href="${pageContext.request.contextPath}/go_admin_allviewDB">관리자페이지</a></div>	
	<div id="login_div"><a id="login_or_logout" href="#" onclick="login_or_logout()">login</a></div>
</header>

<!-- 헤더 밑 이미지와 그림이 들어있는 영역(섹션)이다. -->
<!-- 이지미 파일을 가져오기 위해서 contextpath경로를 가져오고 resources폴더와  -->
<section>
	<div style="width: 100%; height:350px; overflow: hidden;">
		<h3 class="resv">Reservarion</h3>
		<img src='${pageContext.request.contextPath}/resources/images/index/banner.png' style="width:100%;" >
	</div>
</section>

<!-- 실제 내용이 나오는 부분이다.  -->
<!-- 프레임과 같은 기능을 하도록 하기 위해서 div영역에 각 페이지를 출력하게 하는 작업을 함  -->
<!-- 프레임을 사용하지 않은 이유는, 출력할 각 컨텐츠의 내용이 크기가 달라서 그 크기를 조정하기 힘들어서 이런 방법을 취함 -->
<section>
	<div id="content"></div>
</section>

<!-- jquery와 지정한 js파일을 불러온다. -->
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>

<script type="text/javascript">
//ready로 인해 위 페이지들이 전부 출력된 후 아래 function이 동작한다.
	$( document ).ready(function() {		
		//내용이 출력되는 부분이다. 프레임처럼 특정 영역에 내용을 출력하기 위해서 jquery의 .load()의 기능을 히용하였다.
		//controller에서 type이란 모델에 각 가고자 하는 문구를 집어넣는 것으로 각 링크를 구분하였다. 
		if('${type}' == 'allviewDB'){
			$("#content").load("./allviewDB"); 	//전체 예약 상황 출력(해당 페이지가 메인페이지이다.) 
		}else if('${type}' == 'insertDB'){
			$("#content").load("./insertDB?resv_date=${resv_date}&room=${room}");	//입력 폼 페이지 출력 
		}else if('${type}' == 'login'){
			$("#content").load("./login"); 	//로그인 페이지 출력 
		}else if('${type}' == 'admin_allviewDB'){
			$("#content").load("./admin_allviewDB"); //관리자 로그인 상황일 때 나오는 전체 예약상황 페이지 출력 
		}else if('${type}' == 'admin_selectoneDB'){
			$("#content").load("./admin_selectoneDB?resv_date=${resv_date}&room=${room}"); 	//관리자가 원하는 예약 상황을 보고자 할때 출력되는 페이지
		}

 		//console.log('${sessionScope}');
		//console.log('${sessionScope.login_id}'); 

		let login_or_logout = document.getElementById("login_or_logout");		// 로그인 로그아웃 버튼이 있는 태그
		let admin = document.getElementById("admin");		//관리자 페이지로 가는 태그 

		//만약 로그인 이 되 있는 상태이고, sesstion에 저장되 있는 id가 admin이면 
		if('${sessionScope.login_ok}' == 'yes'){
			if('${sessionScope.login_id}' == 'admin'){
				//관리자 페이지 링크를 보여주고
				admin.style.display = "block";
			}else{
				//아니면 보여주지 않는다. 
				admin.style.display = "none";
			}
				//로그인 상태지만 관리자 로그인이 아니면 로그인, 로그아웃 보여주는 링크는 logout으로 표시한다.
				login_or_logout.innerHTML = "logout";
		}else{
			//만약 로그인 상태가 아니면 관리자 링크도 표시 안하고, 로그인 링크를 보여주도록 한다.
			admin.style.display = "none";
			login_or_logout.innerHTML = "login";
		}
	});
</script>
</body>
</html>
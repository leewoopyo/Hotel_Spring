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

<!-- 헤더부분  -->
<!-- 최상단 로고와 로그인 로그아웃 관리자 페이지가 나타나는 부분이다. 각 글자들을 해당하는 링크가 있다.  -->
<header class="banner" style="display: flex;">
	<div id="logo_div" ><a id="logo" href="/kopo/">joa hotel</a></div>
	<div id="admin_div" ><a id="admin" href="/kopo/go_admin_allviewDB">관리자페이지</a></div>	
	<div id="login_div"><a id="login_or_logout" href="#" onclick="login_or_logout()">login</a></div>
</header>

<!-- 헤더 밑 이미지와 그림이 들어있는 영역(섹션)이다. -->
<section>
	<div style="width: 100%; height:350px; overflow: hidden;">
		<h3 class="resv">Reservarion</h3>
		<img src='${pageContext.request.contextPath}/resources/images/index/banner.png' style="width:100%;" >
	</div>
</section>

<section>
	<div id="content"></div>
</section>

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
<script type="text/javascript">
	$( document ).ready(function() {
		if('${type}' == 'allviewDB'){
			$("#content").load("./allviewDB"); 
		}else if('${type}' == 'insertDB'){
			$("#content").load("./insertDB?resv_date=${resv_date}&room=${room}"); 
		}else if('${type}' == 'login'){
			$("#content").load("./login"); 
		}else if('${type}' == 'admin_allviewDB'){
			$("#content").load("./admin_allviewDB"); 
		}else if('${type}' == 'admin_selectoneDB'){
			$("#content").load("./admin_selectoneDB?resv_date=${resv_date}&room=${room}"); 
		}

		console.log('${sessionScope}');
		console.log('${sessionScope.login_id}');

		let login_or_logout = document.getElementById("login_or_logout");
		let admin = document.getElementById("admin");
		if('${sessionScope.login_ok}' == 'yes'){
			if('${sessionScope.login_id}' == 'admin'){
				admin.style.display = "block";
			}else{
				admin.style.display = "none";
			}
				login_or_logout.innerHTML = "logout";
		}else{
			admin.style.display = "none";
			login_or_logout.innerHTML = "login";
		}
	});



</script>
</body>
</html>
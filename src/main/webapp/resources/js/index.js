var contextPath = document.getElementById("contextPath").value;
//로그인 글씨 로그아웃 글씨에 따라서 링크가 달라지는 기능

function login_or_logout(){
	let login_or_logout = document.getElementById("login_or_logout");
	if(login_or_logout.innerHTML == 'login'){
		location.href= contextPath + "/go_login";
	}else if(login_or_logout.innerHTML == 'logout'){
		location.href= contextPath + "/logout";
	}
}
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.sql.*, java.io.*"%>
<%@ page import="kr.ac.kopo.service.*, kr.ac.kopo.dto.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login.css">
</head>
<body>

<section style="width: 40%; margin: auto; margin-bottom: 100px;">

	<h2>로그인 페이지</h2>
	<hr style="border-width: 2px; border-color: black;">
	<form id="post_param" action="./loginck" method="post">
		<div>
			<p>아이디</p>
			<input name="id" id="id" type="text">
		</div>
		
		<div>
			<p>비밀번호</p>
			<input name="password" id="password" type="password">
		</div>
		
		<div>
			<input type="button" id="submit_btn" value="로그인 " onclick="send_param()">
		</div>
		<div id="message" style="display:none; color:red; font-size:20px; margin-top:20px;"></div>
		<input type="hidden" name="jump" value="${admin_allviewDB}">
	</form>
</section>

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 왼쪽 프레임의 메뉴바가 나오는 부분의 내용물, target을 main으로 적어서  -->
<!-- 다른 프레임에 링크된 내용이 나오도록 하였다. -->
<a href="./createDB" target="main">테이블 생성</a><br>
<a href="./allsetDB" target="main">데이터 입력</a><br>
<a href="./dropDB" target="main">테이블 삭제</a><br>
<a href="./allviewDB" target="main">데이터 보기</a><br>

</body>
</html>
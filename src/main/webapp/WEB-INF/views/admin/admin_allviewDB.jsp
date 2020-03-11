<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import ="java.sql.*, javax.sql.*, java.io.*" %>
   <%@ page import ="kr.ac.Hotel.service.*,kr.ac.Hotel.dto.*" %>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/allviewDB.css">
</head>
<body>
	
<section style="text-align: center; margin-bottom: 100px;">
	<h3 style="font-family: sans-serif;font-size: xx-large; margin: 15px;">관리자 페이지 (전체 예약 현황)</h3>
	<hr style="border-width: 2px; border-color: black;">
		<!-- 전체 리스트가 출력되는 테이블 생성   -->
		<table id="status" cellspacing=1 border=2px>
			<tr>
				<td width=50 style="background-color: antiquewhite;"><p align=center>예약 일자</p></td>
				<td width=50 style="background-color: antiquewhite;"><p align=center>Deluxe Room</p></td>
				<td width=50 style="background-color: antiquewhite;"><p align=center>Suite Room</p></td>
				<td width=50 style="background-color: antiquewhite;"><p align=center>Royal Room</p></td>
			</tr>
			<!-- JSTL의 choose태그 로 조건문 실행 -->
			<!-- 만약 list가 비어있다면 -->
			<!-- 코드를 출력한다 -->
			<!-- 그렇지 않다면 foreach문으로 list를 출력한다.  -->
			<!-- list에 담긴 room1,room2,room3 의 정보를 출력한다. -->
			<!-- 데이터 조건에 따라서 링크를 걸지 안걸지를 정한다. -->
			<!-- 데이터가 '선택가능' 이라는 데이터 이면 예약 화면으로 넘어가는 링크를 건다. -->
			<!-- '선택가능'이 아니면 클릭 시 해당 데이터를 조회하는 페이지로 넘어간다. -->
			<c:choose>
				<c:when test="${empty admin_list}">
					<tr>
						<td colspan=3>
							<spring:message code="common.listEmpty"/>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${admin_list}" var="e" begin="0" end="30" step="1" varStatus="status">
						<tr> 
							<td width=50><p name="date" align=center>${status.index+1}.${e.date}(${e.dayOfWeek})</p></td>
							<c:choose>
								<c:when test="${e.room1 eq '선택가능'}">
									<td width=50><p align=center><a href="./go_insertDB?resv_date=${e.date}&room=1">${e.room1}</a></p></td>
								</c:when>
								<c:otherwise>
									<td width=50><p align=center><a class="reserved" href="./go_selectoneDB?resv_date=${e.date}&room=1">${e.room1}</a></p></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${e.room2 eq '선택가능'}">
									<td width=50><p align=center><a href="./go_insertDB?resv_date=${e.date}&room=2">${e.room2}</a></p></td>
								</c:when>
								<c:otherwise>
									<td width=50><p align=center><a class="reserved" href="./go_selectoneDB?resv_date=${e.date}&room=2">${e.room2}</a></p></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${e.room3 eq '선택가능'}">
									<td width=50><p align=center><a href="./go_insertDB?resv_date=${e.date}&room=3">${e.room3}</a></p></td>
								</c:when>
								<c:otherwise>
									<td width=50><p align=center><a class="reserved" href="./go_selectoneDB?resv_date=${e.date}&room=3">${e.room3}</a></p></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
</section>

<script src="${pageContext.request.contextPath}/resources/js/allviewDB.js"></script>
</body>
</html>
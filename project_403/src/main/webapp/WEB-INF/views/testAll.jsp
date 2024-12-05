<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
testAll 페이지
<p><a href="../">Home</a>
<div>
	<!-- <c:forEach items="${List}" var="test">
		<div style="border: 1px solid black;">
			<h3>${test.test_num}</h3>
			<p>${test.mem_id}
			<p>${test.test_time}
			<p>${test.test_date}
			<p>${test.test_name}
			<p><a href="testUpdate?Num=${test.test_num}">수정</a>
			<p><a href="testDelete?Num=${test.test_num}">삭제</a>
		</div>
	</c:forEach> -->
-------------------------
	<c:forEach items="${boardlist}" var="test">
		<div style="border: 1px solid black;">
			<h3>${test.test_num}</h3>
			<p>${test.mem_id}
			<p>${test.test_time}
			<p>${test.test_date}
			<p>${test.test_name}
			<p><a href="testUpdate?Num=${test.test_num}">수정</a>
			<p><a href="testDelete?Num=${test.test_num}">삭제</a>
		</div>
	</c:forEach>

	<div align="center">
		<c:set var="pageNum" value="${pageNum}" />
		<c:forEach var="i" begin="1" end="${total_page}">
			<a href="testAll?pageNum=${i}">
				<c:choose>
					<c:when test="${pageNum==i}">
						<font color='4C5317'><b> [${i}]</b></font>
					</c:when>
					<c:otherwise>
						<font color='4C5317'> [${i}]</font>
					</c:otherwise>
				</c:choose>
			</a>
		</c:forEach>
	</div>
</div>
</body>
</html>
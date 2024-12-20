<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
testStart 페이지
<p><a href="testAll">전체보기</a>
<div>
	시험 번호: ${test.test_num}
</div>
<div>
	시험 제목: ${test.test_name}
</div>
<div>
	과목명: ${test.sub_name}
</div>
<div>
	총 문제 갯수: ${fn:length(test.serial)}개
</div>
<div>
	문제: ${allQuestion[0].question_content}
</div>
<div>
<!-- 
	<c:set var="allQuestion" value="${allQuestion}" />
	<c:forEach var="i" begin="0" end="${fn:length(allQuestion[0].question_ans)}">
		보기: ${allQuestion[0][i].question_ans}
	</c:forEach>
	 -->
</div>
<div>
	<input type="button" value="정답확인">
</div>
</body>
</html>
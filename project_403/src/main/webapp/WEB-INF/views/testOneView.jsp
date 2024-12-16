<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
testOneView 페이지
<p><a href="testAll">전체보기</a>
<p><a href="">시험시작하기</a>

<div>
	작성자ID: ${test.mem_id}
</div>
<div>
	시험 제목: ${test.test_name}
</div>
<div>
	과목명: ${test.sub_name}
</div>
<div>
	챕터명: ${test.sub_chap}
</div>
<div>
	<c:forEach items="${allQuestion}" var="all" >
		<p>------
		<div>${all.question_content}</div>
		<div>${all.question_ans}</div>
		<p>------
	</c:forEach>
</div>
<div>
	조회수: ${test.test_hit}
</div>
</body>
</html>
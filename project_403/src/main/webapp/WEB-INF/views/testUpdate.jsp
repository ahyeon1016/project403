<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
testUpdate 페이지
<p><a href="testAll">Home</a>

<form:form modelAttribute="UpdateTest" action="./testUpdate">
	<fieldset>
		<div>
			시험 번호: <input name="test_num" value="${test.test_num}" readonly="readonly" />
		</div>		
		<div>			
			작성자ID: <input name="mem_id" value="${test.mem_id}" readonly="readonly" />
		</div>
		<div>
			시험 제목: <form:input path="test_name" value="${test.test_name}" />
		</div>
		<div>
			시험 비밀번호: <form:input path="test_pw" value="${test.test_pw}" />
		</div>
		<div>
			공개 또는 비공개: <form:input path="test_openYN" value="${test.test_openYN}" />
		</div>
		<div>
			과목명: <form:input path="sub_name" value="${test.sub_name}" />
		</div>
		<div>
			챕터명: <form:input path="sub_chap" value="${test.sub_chap}" />
		</div>
		<div>
			<input type="submit" value="전송">
		</div>
	</fieldset>
</form:form>
</body>
</html>
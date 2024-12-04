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
			<form:input path="test_num" value="${test.test_num}" type="hidden" />
			작성자ID: <input name="mem_id" value="${test.mem_id}" readonly="readonly" />
		</div>
		<div>
			시험시간: <form:input path="test_time" value="${test.test_time}" />
		</div>
		<div>
			시험날짜: <form:input path="test_date" value="${test.test_date}" />
		</div>
		<div>
			시험이름: <form:input path="test_name" value="${test.test_name}" />
		</div>
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="전송">
		</div>
	</fieldset>
</form:form>
</body>
</html>
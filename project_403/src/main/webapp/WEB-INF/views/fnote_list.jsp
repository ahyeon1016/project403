<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.spring.domain.Test" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
*{
	list-style:none;
}
button{
	cursor:pointer;
}

</style>
</head>
<body>
<% ArrayList<Test> test_list=(ArrayList<Test>)request.getAttribute("testlist"); %>

	<ul>
		<%
			if(test_list.size()!=0){
		for(int i=0;i<test_list.size();i++){ %>
		<li><form action="mynote?testnum=<%=test_list.get(i).getTest_num()%>" method="post"><button type="submit"><%=test_list.get(i).getTest_name() %></button></form></li>
		<%}}else{ %>
		<li>시험 기록이 존재하지 않습니다.</li>
		<%} %>
	</ul>


</body>
</html>
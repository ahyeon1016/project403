<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.spring.domain.Member" %>


<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<style>
#mem_form{
display:flex;
}
.mem_page{
cursor:pointer;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<div>
		<div>
			<h1>회원 조회</h1>
			<table border=1>
			<tr>
				<td>회원 아이디</td>
				<td>회원 닉네임</td>
				<td>회원 포인트</td>
				<td>회원 등급포인트</td>
				<td>회원 이메일</td>
				<td>회원 마지막 접속일</td>	
				<td>회원 고유 번호</td>
				<td>회원 이메일 등록 여부</td>		
			</tr>
		
		<%
		ArrayList<Member> al=(ArrayList<Member>)request.getAttribute("al");
		int mem_num=(int)request.getAttribute("mem_num");
		for(int i=0;i<al.size();i++){
			Member member=(Member)al.get(i);
		%>
			<tr>
				<td><%=member.getMem_id() %></td>
				<td><%=member.getMem_nickName() %></td>
				<td><%=member.getMem_point() %></td>
				<td><%=member.getMem_exp() %></td>
				<td><%=member.getMem_email() %></td>
				<td><%=member.getMem_date() %></td>
				<td><%=member.getMem_serial() %></td>
				<td><%=member.isMem_confirmed() %></td>
			</tr>
			<%} %>
			</table>
			<div id="mem_form">
			<form action="./admin?page=1" method="post">
				<input type="submit" value="[1]" class="mem_page">
					<%for(int j=1;mem_num/10>=j;){ j++;%>
			</form>
			<form action="./admin?page=<%=j %>" method="post">
				<input type="submit" value="[<%=j %>]" class="mem_page">
			</form>
					<%} %>
			</div>
			
			
			
			
			
			
					
		</div>
		<a href="./">홈</a>
	
	</div>

</body>
</html>
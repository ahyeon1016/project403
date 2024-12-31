<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.QnA" %>
<%@ page import="java.util.ArrayList" %>
<%
	ArrayList<QnA> rootAll = (ArrayList<QnA>) request.getAttribute("rootAll");
	int index = (int)request.getAttribute("index");
	int maxPage = (int)request.getAttribute("maxPage");
	int totalPage = (int)request.getAttribute("totalPage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	<p><%=member.getMem_nickName()%>
	HELLO COMMENT ROOT ALL
	<hr>
	<%
	for(int i=index; i<maxPage; i++){
		QnA qna = rootAll.get(i);
	%>
		<span>작성자 : <%=qna.getMem_nickName()%> | </span>
		<span>질문 문제 고유 넘버 : <%=qna.getQuestion_serial()%> | </span>
		<span>번호 <%=i+1%> </span>
		<br>
		<p>작성 날짜 : 
		<span>
		<%= (qna.getComment_date().getYear() + 1900)+"-" 
		    +String.format("%02d", qna.getComment_date().getMonth() + 1)+"-"
		    +String.format("%02d", qna.getComment_date().getDate()) %>
		</span>
		<h3>제목 : <%=qna.getComment_title()%></h3>
		<p>조회수 : <%=qna.getComment_hit()%>
		<span>좋아요 : <%=qna.getComment_totalGood()%></span>
		<span>싫어요 : <%=qna.getComment_totalBad()%></span>
		<br>
		<a href="commentRootOne?
				comment_root=<%=qna.getComment_root()%>
				">확인하기</a>
		<hr>
	<%
	}
	%>
	<%
	for(int i=1; i<totalPage+1; i++){
	%>
		<a href="commentRootAll?page=<%=i%>"><%=i%></a>
	<%
	}
	%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.QnA" %>
<%@ page import="java.util.ArrayList" %>
<%
	ArrayList<QnA> rootAll = (ArrayList<QnA>) request.getAttribute("rootAll");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO COMMENT ROOT ALL
	<hr>
	<%
	for(QnA qna : rootAll){
	%>
		<span>작성자 : <%=qna.getMem_id()%> | </span>
		<span>질문 문제 고유 넘버 : <%=qna.getQuestion_serial()%> | </span>
		<span>번호 <%=qna.getComment_root()%> </span>
		<br>
		<p>작성 날짜 : <%=qna.getComment_date()%>
		<h3>제목 : <%=qna.getComment_title()%></h3>
		<p>조회수 : <%=qna.getComment_hit()%>
		<span>추천수 : <%=qna.getComment_good()%></span>
		<br>
		<a href="commentRootOne?
				comment_root=<%=qna.getComment_root()%>&
				comment_hit=<%=qna.getComment_hit()%>
				">확인하기</a>
		<hr>
	<%
	}
	%>
</body>
</html>
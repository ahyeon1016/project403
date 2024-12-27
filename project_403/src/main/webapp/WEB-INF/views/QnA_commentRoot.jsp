<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
	body{
		width : 70%;
		margin : 0 auto;
		padding : 100px;
		border : 1px solid black;
		
	}
</style>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	<div style="display: none" id=isClicked_btn>${isClicked_btn}</div>
	<div style="display: none" id="nickName">${member.getMem_nickName()}</div>
	
	<p>${member.getMem_id()}
	HELLO COMMENT ROOT VIEW
	<div>
		<h2>${qna.getComment_title()}</h2>
		<h4>${qna.getQuestion_serial()}</h4>
		<p id="root">${qna.getComment_root()}</p>
		<p>작성자 : ${qna.getMem_nickName()}
		<p id="comment_date">${qna.getComment_date()}</p>
	</div>
	<hr>
	<div>
		<p>${qna.getComment_content()}
		<br>
		<br>
		<p>조회수 ${qna.getComment_hit()}  |  
		<button id="goodBtn" style="background-color: ${goodColor}" onclick="goodUp(${isGood_btn}, ${qna.getComment_num()})">좋아요</button>
		<span id="good">${qna.getComment_totalGood()}</span> | 
		<button id="badBtn" style="background-color: ${badColor}" onclick="badUp(${isBad_btn}, ${qna.getComment_num()})">싫어요</button>
		<span id="bad">${qna.getComment_totalBad()}</span> | 
	</div>
	<hr>
	<h3>댓글</h3>
	<div>
		<textarea id="comment_input"rows="10" cols="60" placeholder="내용을 입력해 주세요."></textarea>
		<br>
		<button id="comment_input_btn" onclick="comment_submit(${qna.getComment_root()}, '${qna.getQuestion_serial()}')">입력</button>
	</div>
	<hr>
	<ul id="comment">
	</ul>
	
	<script src="/project_403/resources/js/commentRoot.js"></script>
</body>
</html>
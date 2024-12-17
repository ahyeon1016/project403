<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO COMMENT ROOT VIEW
	<br>
	<h2>${qna.getComment_title()}</h3>
	<h4>${qna.getQuestion_serial()}</h4>
	<p>${qna.getComment_date()}
	<hr>
	<p>${qna.getComment_content()}
	<br>
	<br>
	<p>조회수 ${qna.getComment_hit()}  |  
	<button onclick="goodUp(${qna.getComment_good()})">
		<p>추천</p>
		(<span id="good">${qna.getComment_good()}</span>)
	</button> 
	<hr>
	<h3>댓글</h3>
	<p>
	<script>
		let good = document.querySelector("#good");
		function goodUp(count){
			console.log(good.textContent);
			good.textContent = parseInt(good.textContent)+1;
		}
	</script>
</body>
</html>
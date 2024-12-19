<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	HELLO COMMENT ROOT VIEW
	<br>
	<h2>${qna.getComment_title()}</h2>
	<h4>${qna.getQuestion_serial()}</h4>
	<p>게시글 번호 : ${qna.getComment_root()}
	<p>작성자 : ${qna.getMem_id()}
	<p>${qna.getComment_date()}
	<hr>
	<p>${qna.getComment_content()}
	<br>
	<br>
	<p>조회수 ${qna.getComment_hit()}  |  
	<button onclick="goodUp()">
		<p>추천</p>
		(<span id="good">${qna.getComment_good()}</span>)
	</button> 
	<hr>
	<h3>댓글</h3>
	<textarea id="comment_input"rows="10" cols="60" placeholder="내용을 입력해 주세요."></textarea>
	<br>
	<button onclick="comment_submit(${qna.getComment_root()}, '유저', '${qna.getQuestion_serial()}')">입력</button>
	<hr>
	<ul id="comment">
	</ul>
	<script>
		let good = document.querySelector("#good");
		function goodUp(){
			good.textContent = parseInt(good.textContent)+1;
			console.log(good.textContent);
			/*$.ajax({
				url : "",
				type : "get",
				contentType : "application/json",
				data : ({
					"count" : parseInt(good.textContent)+1
				}),
				success : function(data){
					good.textContent = data.count;
				},
				error : function(data){
					alert("조회수 증가 실패");
				}
			});*/
		}
		
		let comment_input = document.querySelector("#comment_input");
		let comment = document.querySelector("#comment");
		comment.addEventListener("load", comment_load());
		function comment_load(){
			console.log("페이지가 로드 되었습니다.");
			$.ajax({
				url : "",
				type : "get",
				contentType : "application/json",
				data : ({
					
				}),
				success : function(){
					
				},
				error : function(){
					
				}
			});
		}
		/* 댓글 추가 */
		function comment_submit(root, id, q_serial){
			console.log(comment_input.value);
			console.log(root+"|"+id);
			comment.innerHTML += 
				"<li>"+
					"작성자 : "+id+"<br>"+
					comment_input.value+
				"</li>";
			$.ajax({
				url : "addCommentParent",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"mem_id":id,
					"question_serial":q_serial,
					"comment_root":root,
					"comment_content":comment_input.value
				}),
				success : function(data){
					console.log("성공");
					alert(data.success);
				},
				error : function(data){
					console.log("실패");
				}
			});
		}
	</script>
</body>
</html>
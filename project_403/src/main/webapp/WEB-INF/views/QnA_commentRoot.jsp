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
	<p id="root">${qna.getComment_root()}</p>
	<p>작성자 : ${qna.getMem_id()}
	<p id="comment_date">${qna.getComment_date()}</p>
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
		let root = document.querySelector("#root");
		let comment_date = document.querySelector("#comment_date");
		let comment_input = document.querySelector("#comment_input");
		let comment = document.querySelector("#comment");
		comment.addEventListener("load", comment_load());
		function comment_load(){
			let comment_root = parseInt(root.textContent);
			console.log("페이지가 로드 되었습니다.");
			let comment_date_format = new Date(comment_date.innerText.trim().replace(' ', 'T')).toLocaleString("ko-KR", {
				timeZone: "Asia/Seoul"
		    });
			comment_date.innerText = comment_date_format;
			console.log(comment_date_format);
			$.ajax({
				url : "getCommentParent",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"comment_root" : comment_root
				}),
				success : function(data){
					console.log("성공");
					console.log(data.list);
					let list = data.list;
					for(let i=0; i<list.length; i++){
						let date = new Date(list[i].comment_date);
						let formattedDate = date.toLocaleString("ko-KR", {
						    timeZone: "Asia/Seoul"
						});
						comment.innerHTML += 
							"<li>"+
								"작성자 : "+list[i].mem_id+" | 작성시간 : "+formattedDate+"<br>"+
								list[i].comment_content+
							"</li><button>댓글쓰기</button><br><br>";
					}
				},
				error : function(data){
					console.log("실패");
				}
			});
		}
		
		/* 댓글 추가 */
		function comment_submit(root, id, q_serial){	
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
					let date_format = new Date(data.time.replace(' ', 'T')).toLocaleString("ko-KR", {
						timeZone: "Asia/Seoul"
				    });
					if(data.success){
						alert("댓글 작성에 성공했습니다.");
						comment.innerHTML += 
							"<li>"+
								"작성자 : "+id+" | 작성시간 : "+date_format+"<br>"+
								comment_input.value+
							"</li><button>댓글쓰기</button><br><br>";
					} else{
						alert("댓글 작성에 실패했습니다.");
					}
				},
				error : function(data){
					console.log("실패");
				}
			});
		}
	</script>
</body>
</html>
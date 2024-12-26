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
	<br>
	<h2>${qna.getComment_title()}</h2>
	<h4>${qna.getQuestion_serial()}</h4>
	<p id="root">${qna.getComment_root()}</p>
	<p>작성자 : ${qna.getMem_nickName()}
	<p id="comment_date">${qna.getComment_date()}</p>
	<hr>
	<p>${qna.getComment_content()}
	<br>
	<br>
	<p>조회수 ${qna.getComment_hit()}  |  
	<button id="goodBtn" style="background-color: ${goodColor}" onclick="goodUp(${isGood_btn}, ${qna.getComment_num()})">좋아요</button>
	<span id="good">${qna.getComment_totalGood()}</span> | 
	<button id="badBtn" style="background-color: ${goodColor}" onclick="badUp(${isGood_btn}, ${qna.getComment_num()})">싫어요</button>
	<span id="bad">${qna.getComment_totalGood()}</span> | 
	<hr>
	<h3>댓글</h3>
	<textarea id="comment_input"rows="10" cols="60" placeholder="내용을 입력해 주세요."></textarea>
	<br>
	<button onclick="comment_submit(${qna.getComment_root()}, '${qna.getQuestion_serial()}')">입력</button>
	<hr>
	<ul id="comment">
	</ul>
	<script>
		let good = document.querySelector("#good");
		let goodBtn = document.querySelector("#goodBtn");
		let bad = document.querySelector("#bad");
		let badBtn = document.querySelector("#badBtn");
		let nickName = document.querySelector("#nickName").textContent;
		let root = document.querySelector("#root");
		let comment_date = document.querySelector("#comment_date");
		let comment_input = document.querySelector("#comment_input");
		let comment = document.querySelector("#comment");
		comment.addEventListener("load", comment_load());
		
		/*좋아요 기능*/
		function goodUp(isClicked, qnaNum){
			console.log(goodBtn.style.backgroundColor);
			console.log(qnaNum);
			/* 버튼의 파라미터와 색상 변경 */
			console.log(isClicked);
			if(isClicked){
				goodBtn.setAttribute("onclick", "goodUp(false, "+qnaNum+")");
				goodBtn.style.backgroundColor='gray';
			}else{
				goodBtn.setAttribute("onclick", "goodUp(true, "+qnaNum+")");
				goodBtn.style.backgroundColor='white';
			}	
			
			/* comment_good true/false 설정 */
			$.ajax({
				url : "../favorite/good",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"isClicked" : isClicked,
					"qnaNum" : qnaNum
				}),
				success : function(data){
					console.log("성공"+data.isClicked);
					if(data.isClicked){
						good.textContent = parseInt(good.textContent)+1;
					} else{
						good.textContent = parseInt(good.textContent)-1;	
					}
				},
				error : function(data){
					console.log("실패");
				}
			});
		}
		
		/*싫어요 기능*/
		function badUp(isClicked, qnaNum){
			console.log(badBtn.style.backgroundColor);
			console.log(qnaNum);
			/* 버튼의 파라미터와 색상 변경 */
			console.log(isClicked);
			if(isClicked){
				badBtn.setAttribute("onclick", "badUp(false, "+qnaNum+")");
				badBtn.style.backgroundColor='gray';
			}else{
				badBtn.setAttribute("onclick", "badUp(true, "+qnaNum+")");
				badBtn.style.backgroundColor='white';
			}	
			
			/* comment_bad true/false 설정 */
			$.ajax({
				url : "../favorite/bad",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"isClicked" : isClicked,
					"qnaNum" : qnaNum
				}),
				success : function(data){
					console.log("성공"+data.isClicked);
					if(data.isClicked){
						bad.textContent = parseInt(bad.textContent)+1;
					} else{
						bad.textContent = parseInt(bad.textContent)-1;	
					}
				},
				error : function(data){
					console.log("실패");
				}
			});
		}
		
		/*View 로딩 시에 댓글을 가져오는 기능*/
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
						if(list[i].comment_content=="pDel"){
							comment.innerHTML += 
								"<li>"+
									"삭제된 댓글 입니다."+
									"<ul></ul>"+
								"</li>";
						}else if(list[i].comment_content=="cDel"){
							comment.lastElementChild.lastElementChild.innerHTML +=
								"<li>삭제된 댓글 입니다.</li><br>"
						}else if(list[i].comment_child==0){
							comment.innerHTML += 
								"<li>"+
									"<p>작성자 : "+list[i].mem_nickName+" | 작성시간 : "+formattedDate+"<br>"+
									list[i].comment_content+"</p>"+
									"<textarea></textarea>"+
									"<button onclick='child_input(this, `"+
										list[i].question_serial+"`, "+list[i].comment_root+","+
										list[i].comment_parent+
									")'>댓글쓰기</button>"+
									"<button onclick='deleteParent(this, "+
										list[i].comment_root+", "+list[i].comment_parent+", `"+
										list[i].question_serial+
									"`)'>삭제하기</button>"+
									"<ul></ul>"+
								"</li>";
						}else{
							comment.lastElementChild.lastElementChild.innerHTML += 
								"<li>"+
									"<p>작성자 : "+list[i].mem_nickName+" | 작성시간 : "+formattedDate+"<br>"+
									list[i].comment_content+"</p>"+
									"<button onclick='deleteChild(this, "+
									list[i].comment_root+", "+list[i].comment_parent+", `"+
									list[i].question_serial+"`, "+list[i].comment_child+
									")'>삭제하기</button>"+
								"</li>"+
								"<br>";
						}
					}
				},
				error : function(data){
					console.log("실패");
				}
			});
		}
		
		/* 댓글 추가 */
		function comment_submit(root, q_serial){	
			$.ajax({
				url : "addCommentParent",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
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
						console.log(data);
						alert("댓글 작성에 성공했습니다.");
						comment.innerHTML += 
							"<li>"+
								"<p>작성자 : "+nickName+" | 작성시간 : "+date_format+"<br>"+
								comment_input.value+"</p>"+
								"<textarea></textarea>"+
								"<button onclick='child_input(this, `"+
									data.question_serial+"`,"+data.comment_root+","+
									data.comment_parent+
								")'>댓글쓰기</button>"+
								"<button onclick='deleteParent(this, "+
								data.comment_root+", "+data.comment_parent+", `"+
								data.question_serial+
								"`)'>삭제하기</button>"+
								"<ul></ul>"+
							"</li>";
					} else{
						alert("댓글 작성에 실패했습니다.");
					}
				},
				error : function(data){
					console.log("실패");
				}
			});
		}
		
		/*대댓글 추가*/
		function child_input(element, q_serial, root, parent){
			
			let child = element.previousElementSibling.value;
			console.log(element.parentElement);
			$.ajax({
				url: "addCommentChild",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					"comment_content" : child,
					"question_serial" : q_serial,
					"comment_root" : root,
					"comment_parent" : parent
				}),
				success: function(data){
					let date_format = new Date(data.time.replace(' ', 'T')).toLocaleString("ko-KR", {
						timeZone: "Asia/Seoul"
				    });
					console.log("성공~")
					console.log(data);
					element.parentElement.lastElementChild.innerHTML+=
						"<li>"+
							"<p>작성자 : "+nickName+" | 작성시간 : "+date_format+"</p>"+
							child+
							"<button onclick='deleteChild(this, "+
							data.comment_root+", "+data.comment_parent+", `"+
							data.question_serial+"`, "+data.comment_child+
							")'>삭제하기</button>"+
						"</li><br>";
				},
				error: function(data){
					console.log("실패");
				}
			});
		}
		
		/*댓글 삭제*/
		function deleteParent(element, root, parent, q_serial){
			console.log("comment_parent 제거버튼");
			console.log(root);
			console.log(parent);
			console.log(q_serial);
			$.ajax({
				url: "removeCommentParent",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					"comment_root" : root,
					"comment_parent" : parent,
					"question_serial" : q_serial,
				}),
				success: function(data){
					for(let i=0; i<3; i++){
						console.log(element.parentElement.children[0]);
						element.parentElement.removeChild(element.parentElement.children[0]);
					}
				
					let newChild = document.createElement('li');
			        newChild.innerHTML = '삭제된 댓글 입니다.';
			  
					element.parentElement.replaceChild(newChild ,element.parentElement.children[0]);
				},
				error: function(data){
					console.log("실패");
				}
			});
		}
		
		/*대댓글 삭제*/
		function deleteChild(element, root, parent, q_serial, child){
			console.log("comment_parent 제거버튼");
			console.log(root);
			console.log(parent);
			console.log(q_serial);
			console.log(child);
			$.ajax({
				url: "removeCommentChild",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					"comment_root" : root,
					"comment_parent" : parent,
					"question_serial" : q_serial,
					"comment_child" : child
				}),
				success: function(data){
					element.parentElement.innerHTML="<li>삭제된 댓글 입니다.</li>"
				},
				error: function(data){
					console.log("실패");	
				}
			});
		}
	</script>
</body>
</html>
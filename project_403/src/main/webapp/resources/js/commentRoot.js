let good = document.querySelector("#good");
let goodBtn = document.querySelector("#goodBtn");
let bad = document.querySelector("#bad");
let badBtn = document.querySelector("#badBtn");
let nickName = document.querySelector("#nickName").textContent;
let root = document.querySelector("#root");
let comment_date = document.querySelector("#comment_date");
let comment_input = document.querySelector("#comment_input");
let comment_input_btn = document.querySelector("#comment_input_btn");
let comment = document.querySelector("#comment");
let comment_count = document.querySelector("#comment-count").textContent;
let count=0;

/*이벤트*/
comment.addEventListener("load", comment_load());

/*좋아요 기능*/
function goodUp(isClicked, qnaNum){
	console.log(goodBtn.style.backgroundColor);
	console.log(qnaNum);
	/* 사용자의 로그인 여부 확인 */
	if(nickName==null||nickName==""){
		alert("로그인 후 사용가능한 기능입니다..");
		return;
	}
	/* isClicked의 값에 따라 버튼의 파라미터와 색상 변경 */
	console.log(isClicked);
	if(isClicked){
		goodBtn.setAttribute("onclick", "goodUp(false, "+qnaNum+")");
		goodBtn.style.backgroundColor='#FA5858';
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
	/* 사용자의 로그인 여부 확인 */
	if(nickName==null||nickName==""){
		alert("로그인 후 사용가능한 기능입니다..");
		return;
	}
	/* isClicked의 값에 따라 버튼의 파라미터와 색상 변경 */
	console.log(isClicked);
	if(isClicked){
		badBtn.setAttribute("onclick", "badUp(false, "+qnaNum+")");
		badBtn.style.backgroundColor='#58ACFA';
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

/*View 로딩 시에 댓글과 대댓글을 가져오는 기능*/
function comment_load(){			
	/* 정수 변환 */
	let comment_root = parseInt(root.textContent);
	console.log("페이지가 로드 되었습니다.");
	/* 날짜 데이터 가공 후 추가 */
	let comment_date_format = new Date(comment_date.innerText.trim().replace(' ', 'T')).toLocaleString("ko-KR", {
		timeZone: "Asia/Seoul"
    });
	comment_date.innerText = comment_date_format;
	console.log(comment_date_format);
	
	/* 댓글과 대댓글을 가져온다. */
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
				/* 날짜 데이터 가공 */
				let formattedDate = date.toLocaleString("ko-KR", {
				    timeZone: "Asia/Seoul"
				});
				if(list[i].comment_content=="pDel"){
					/* 삭제된 댓글인 경우 */
					comment.innerHTML +=
						"<hr>"+ 
						"<li>"+
							"삭제된 댓글 입니다.<br>"+
							"<ul></ul>"+
						"</li>";
				}else if(list[i].comment_content=="cDel"){
					/* 삭제된 대댓글인 경우 */
					comment.lastElementChild.lastElementChild.innerHTML +=
						"<hr><li>삭제된 댓글 입니다.</li>"
				}else if(list[i].comment_child==0){
					/* 댓글인 경우 */
					count=count+1;
					comment.innerHTML +=
						"<hr>"+ 
						"<li>"+
							"<p class='commentPa'>작성자 : "+list[i].mem_nickName+" | 작성시간 : "+formattedDate+"<br><br>"+
							list[i].comment_content+"</p>"+
							"<textarea></textarea><br>"+
							"<button class='addBtn' onclick='child_input(this, `"+
								list[i].question_serial+"`, "+list[i].comment_root+","+
								list[i].comment_parent+", `"+list[i].mem_id+
							"`)'>댓글쓰기</button>"+
							"<button class='delBtn' onclick='deleteParent(this, "+
								list[i].comment_root+", "+list[i].comment_parent+", `"+
								list[i].question_serial+
							"`)'>삭제하기</button><br>"+
							"<ul></ul>"+
						"</li>";
				}else{
					/* 대댓글인 경우 */
					count=count+1;
					comment.lastElementChild.lastElementChild.innerHTML += 
						"<hr>"+
						"<li>"+
							"<p>작성자 : "+list[i].mem_nickName+" | 작성시간 : "+formattedDate+"<br><br>"+
							list[i].comment_content+"</p>"+
							"<button class='delBtn' onclick='deleteChild(this, "+
							list[i].comment_root+", "+list[i].comment_parent+", `"+
							list[i].question_serial+"`, "+list[i].comment_child+
							")'>삭제하기</button>"+
						"</li>";
				}
			}
			/*사용자가 로그인 했는지 확인 후 버튼과 입력필드를 비활성화 하는 유효성 검사*/
			let commentButton = document.querySelectorAll("button");
			let commentField = document.querySelectorAll("textarea"); 

			if(nickName==null||nickName==""){
				for(let i=0; i<commentButton.length; i++){
					commentButton[i].setAttribute("disabled", true);			
				}
				for(let i=0; i<commentField.length; i++){
					commentField[i].setAttribute("disabled", true);			
				}
				
			}
			
			/* 댓글의 갯수를 카운트하는 함수 호출 */
			countUpdate(count);
			count=0;
		},
		error : function(data){
			console.log("실패");
		}
	});
}

/* 댓글 추가 */
function comment_submit(root, q_serial,	 mem_id){		
	/* 유효성 검사 */
	if(comment_input.value==null||comment_input.value.trim()==""){
		alert("공백은 입력 불가능 합니다.");
		return;
	}
	
	$.ajax({
		url : "addCommentParent",
		type : "POST",
		contentType : "application/json",
		data : JSON.stringify({
			"question_serial":q_serial,
			"comment_root":root,
			"comment_content":comment_input.value,
			"root_mem_id":mem_id
		}),
		success : function(data){
			console.log("성공");
			count=count+1;
			/* 날짜 데이터 가공 */
			let date_format = new Date(data.time.replace(' ', 'T')).toLocaleString("ko-KR", {
				timeZone: "Asia/Seoul"
		    });
			if(data.success){
				console.log(data);
				alert("댓글 작성에 성공했습니다.");
				/* 가져온 데이터 HTML의 요소에 추가 */
				comment.innerHTML += 
					"<hr>"+
					"<li>"+
						"<p>작성자 : "+nickName+" | 작성시간 : "+date_format+"<br><br>"+
						comment_input.value+"</p>"+
						"<textarea></textarea><br>"+
						"<button class='addBtn' onclick='child_input(this, `"+
							data.question_serial+"`,"+data.comment_root+","+
							data.comment_parent+", `"+mem_id+
						"`)'>댓글쓰기</button>"+
						"<button class='delBtn' onclick='deleteParent(this, "+
						data.comment_root+", "+data.comment_parent+", `"+
						data.question_serial+
						"`)'>삭제하기</button><br>"+
						"<ul></ul>"+
					"</li>"+
					"<br>";
			} else{
				alert("댓글 작성에 실패했습니다.");
			}
			
			/* 댓글의 갯수를 카운트하는 함수 호출 */
			countUpdate(count);
			count=0;
		},
		error : function(data){
			console.log("실패");
		}
	});
}

/*대댓글 추가*/
function child_input(element, q_serial, root, parent, mem_id){
	console.log(element);
	/* element의 형제의 형제의 값 */
	let child = element.previousElementSibling.previousElementSibling.value;
	console.log(child);
	/* 유효성 검사 */
	if(child==null||child.trim()==""){
		alert("공백은 입력 불가능 합니다.");
		return;
	} 
	
	console.log(element.parentElement);
	$.ajax({
		url: "addCommentChild",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			"comment_content" : child,
			"question_serial" : q_serial,
			"comment_root" : root,
			"comment_parent" : parent,
			"parent_mem_id" : mem_id
		}),
		success: function(data){
			count=count+1;
			/* 날짜 가공 */
			let date_format = new Date(data.time.replace(' ', 'T')).toLocaleString("ko-KR", {
				timeZone: "Asia/Seoul"
		    });
			alert("대댓글 작성에 성공했습니다.");
			console.log(data);
			/* 가져온 데이터 HTML의 요소에 추가 */
			element.parentElement.lastElementChild.innerHTML+=
				"<hr>"+
				"<li>"+
					"<p>작성자 : "+nickName+" | 작성시간 : "+date_format+"<br><br>"+
					child+"</p>"+
					"<button class='delBtn' onclick='deleteChild(this, "+
					data.comment_root+", "+data.comment_parent+", `"+
					data.question_serial+"`, "+data.comment_child+
					")'>삭제하기</button>"+
				"</li>"+
				"<br>";
			
			/* 댓글의 갯수를 카운트하는 함수 호출 */
			countUpdate(count);
			count=0;
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
	/* 삭제시 확인 요청 */
	if(!confirm("정말 삭제 하시겠습니까?")){
		return;
	}
	
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
			if(data.success){
				count=count-1;
				/* 기존 요소 제거 */
				for(let i=0; i<4; i++){
					console.log(element.parentElement.children[0]);
					element.parentElement.removeChild(element.parentElement.children[0]);
				}
				
				/* 대체 텍스트 생성 */
				let newChild = document.createElement('li');
		        newChild.innerHTML = '삭제된 댓글 입니다.';
		  		
				/* 대체 텍스트로 변경 */
				element.parentElement.replaceChild(newChild ,element.parentElement.children[0]);
			}else {
				alert("작성자만 삭제 가능합니다.");
			}
			
			/* 댓글의 갯수를 카운트하는 함수 호출 */
			countUpdate(count);
			count=0;
		},
		error: function(data){
			console.log("실패");
		}
	});
}

/*대댓글 삭제*/
function deleteChild(element, root, parent, q_serial, child){
	/* 삭제시 확인 요청 */
	if(!confirm("정말 삭제 하시겠습니까?")){
		return;
	}
	
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
			/* 작성자가 삭제할 수 있도록 유효성 검사 */
			if(data.success){
				count=count-1;
				element.parentElement.innerHTML="<li>삭제된 댓글 입니다.</li>"
			}else {
				alert("작성자만 삭제 가능합니다.");
			}
			
			/* 댓글의 갯수를 카운트하는 함수 호출 */
			countUpdate(count);
			count=0;
		},
		error: function(data){
			console.log("실패");	
		}
	});
}

/* 댓글의 갯수를 카운트하는 함수 */
function countUpdate(count){
	comment_count = parseInt(comment_count)+count;
	document.querySelector("#comment-count").textContent = comment_count;
	
}
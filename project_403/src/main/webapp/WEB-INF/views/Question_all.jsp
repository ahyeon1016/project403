<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.spring.domain.Question"%>
<%@ page import="com.spring.domain.Subject"%>
<%
	ArrayList<Question> question_all = (ArrayList<Question>) request.getAttribute("question_all");
	ArrayList<Subject> sub_all_name = (ArrayList<Subject>) request.getAttribute("sub_all_name");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	HELLO QUESTION ALL PAGE
	<select name="name_select" id="name_selector">
		<option selected>선택</option>
		<%
		for(Subject sub:sub_all_name){	
		%>
		<option><%=sub.getSub_name()%></option>
		<%
		}
		%>
	</select>
	<select name="chap_select" id="chap_selector"></select> | 
	전체 <input type="radio" name="id" value="ALL" class="question_id" checked>
	객관식<input type="radio" name="id" value="MCQ" class="question_id">
	주관식<input type="radio" name="id" value="SAQ" class="question_id">
	코딩<input type="radio" name="id" value="CP" class="question_id">
	<div id="question_container">
		
	</div>
	

	<div>
		
	</div>
	<script>
		let sub_name = document.querySelector("#name_selector");
		let sub_chap = document.querySelector("#chap_selector");
		sub_name.addEventListener("change", nameSearch);
		function nameSearch(){
			let sub_name_value = sub_name.value;
			console.log(sub_name_value);
			$.ajax({
				url : "Q_subNameByChap",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"sub_name" : sub_name_value
				}),
				success : function(data){
					let list = data.list;
					sub_chap.replaceChildren();
					for(let i=0; i<list.length; i++){
						let option = document.createElement("option");
						console.log(list[i]);
						option.text = list[i];
				        sub_chap.appendChild(option);
					}
				},
				error : function(){
					alert("데이터를 받아오지 못했습니다.");
				}
			});
		}
		
		
		let question_container = document.querySelector("#question_container");
		sub_chap.addEventListener("change", chapSearch);
		function chapSearch(){
			let	name_value = sub_name.value;
			let chap_value = sub_chap.value;
			console.log(name_value+"|"+chap_value);
			$.ajax({
				url : "Q_search",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"name" : name_value,
					"chap" : chap_value
				}),
				success : function(data){
					console.log("성공");
					console.log(data.question);
					question_container.replaceChildren();
					let question = data.question;
					for(let i=0; i<question.length; i++){
						let div = document.createElement("div")
						div.classList.add(question[i].question_id, "question_div");
						div.innerHTML = 
							"<p>"+question[i].question_id+"</p>"+
							"<p>"+question[i].sub_code_sum+"</p>"+
							"<p>"+question[i].question_serial+"</p>"+
							"<a href='Q_read"+question[i].question_id+"/"+question[i].question_serial+"'>문제 보기</a>"+
							"<hr>";
							
						question_container.append(div);
						console.log(div);
					}
				},
				error : function(){
					console.log("실패");
				}
			});
		}
		
		let question_id = document.querySelectorAll(".question_id");
		for(let i=0; i<question_id.length; i++){
			question_id[i].addEventListener("click", change_id);
		}
		function change_id(){
			let id = event.target.value;
			let question_div_all = document.querySelectorAll(".question_div");
			console.log(id);
			console.log("챕터에 존재하는 문제의 갯수 : "+question_div_all.length);
			if(id=="ALL"){
				console.log("all");
				for(let i=0; i<question_div_all.length; i++){
					question_div_all[i].style.display = "block";
				}
			}else if(id!="ALL"){
				let question_div = document.querySelectorAll("."+id); 
				console.log("문제 유형과 일치하는 문제의 갯수 : "+question_div.length);
				for(let i=0; i<question_div_all.length; i++){
					question_div_all[i].style.display = "none";
				}
				for(let i=0; i<question_div.length; i++){
					question_div[i].style.display="block";
				}
			}
		}
	</script>
</body>
</html>
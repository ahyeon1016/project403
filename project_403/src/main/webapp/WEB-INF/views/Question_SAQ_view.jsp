<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Question" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO MCQ VIEW
	<p> 문제 고유 번호 : ${question.question_serial}
	<p> 과목 : ${question.sub_code_sum}
	<p>
	<p> 문제풀이 시도 횟수 합계 : ${question.question_count}<span id="plus"></span>
	<p><img src="/project_403/resources/images/${question.question_img_name}">
	<p> 문제 : ${question.question_content}</p>
	<p> <input type="text" id="ans_input">
	<p><button onclick="grading('${question.question_ans}', '${question.question_serial}', ${question.question_count})">정답 확인</button></p>
	<!-- 질문기능 -->
	<p><a href="/project_403/QnA/addCommentRoot?question_serial=${question.question_serial}">질문하기</a>
	<script>
		let index = 0;
		function grading(ans, question_serial, question_count){
			let ans_input = document.querySelector("#ans_input");
			let plus = document.querySelector("#plus");
			let isInput = false;	
			
			if(ans_input.value!=null && ans_input.value.trim()!=""){
				isInput=true;
				if(ans_input.value==ans){
					index++;
					alert(index+"회만에 정답!");
					window.location.href=
							"../Q_plusCount?serial="+question_serial+
							"&count="+question_count+
							"&plus="+index;
				} else{
					index++;
					alert("땡!");
				}
			} 
			plus.textContent = "+"+index;
			if(isInput!=true){
				alert("입력하고 눌러주세요.");
			}
		}
	</script>
</body>
</html>
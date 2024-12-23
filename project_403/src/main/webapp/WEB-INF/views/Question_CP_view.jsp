<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Question" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	HELLO CP VIEW
	<p> 작성자 : ${question.getMem_nickName()}
	<p> 문제 고유 번호 : ${question.question_serial}
	<p> 과목 : ${question.sub_code_sum}
	<p>
	<p> 문제풀이 시도 횟수 합계 : ${question.question_count}<span id="plus"></span>
	<p><img src="/project_403/resources/images/${question.question_img_name}">
	<p> 문제 : ${ans[0]}</p>
	<p><textarea rows="15" cols="70" id="ans_input">${ans[1]}</textarea>
	<p><textarea rows="10" cols="70" id="ans_error" readonly></textarea>
	<p><button onclick="grading('${question.question_ans}', '${question.question_serial}', ${question.question_count})">정답 확인</button></p>
	<!-- 질문기능 -->
	<p><a href="/project_403/QnA/addCommentRoot?question_serial=${question.question_serial}">질문하기</a>
	<script>
		let index = 0;
		function grading(ans, question_serial, question_count){
			let ans_input = document.querySelector("#ans_input");
			let ans_error = document.querySelector("#ans_error");
			let plus = document.querySelector("#plus");
			ans_error.text= null;
			$.ajax({
				url : "../Compile",
				type : "POST",
<<<<<<< HEAD
				contentType : "application/json;charset=UTF-8",
=======
				contentType : "application/json",
>>>>>>> origin/shh
				data : JSON.stringify({"ans_input" : ans_input.value}),
				success : function(data){
					if(data.success){
						console.log(data.output+"|"+ans);
						if(data.output==ans){
							alert(index+"회만에 정답!");
							window.location.href=
									"../Q_plusCount?serial="+question_serial+
									"&count="+question_count+
									"&plus="+index;
						} else{
							alert("땡!");
						}
					} else{
						alert("오류 발생!!!");
						console.log(data.output+"\n"+data.errorCode);
						ans_error.textContent = data.output;
					}
				},
				error : function(data){
					console.log("오류");
				}
			});
			index++;
			plus.textContent = "+"+index;
		}
	</script>
</body>
</html>
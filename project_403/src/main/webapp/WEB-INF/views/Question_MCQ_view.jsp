<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Question" %>
<% 
	String[] ans = (String[]) request.getAttribute("ans");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	HELLO MCQ VIEW
	<p> 작성자 : ${question.getMem_nickName()}
	<p> 문제 고유 번호 : ${question.question_serial}
	<p> 과목 : ${question.sub_code_sum}
	<p>
	<p> 문제풀이 시도 횟수 합계 : ${question.question_count}<span id="plus"></span>
	<p><img src="/project_403/resources/images/${question.question_img_name}">
	<p> 문제 : ${question.question_content}</p>
	<%
	for(int i=0; i<ans.length-1; i++){
	%>	
		| <input type="radio" name="option" value="<%=i+1%>" class="option"> <%=ans[i]%>
	<%
	}
	%>
	<p><button onclick="grading(<%=ans[4]%>, '${question.question_serial}', ${question.question_count})">정답 확인</button></p>
	<!-- 질문기능 -->
	<p><a href="/project_403/QnA/addCommentRoot?question_serial=${question.question_serial}">질문하기</a>
	<script>
		let index = 0;
		function grading(ans, question_serial, question_count){
			let option = document.querySelectorAll(".option");
			let plus = document.querySelector("#plus");
			let isChecked = false;
			for(let i=0; i<option.length; i++){	
				if(option[i].checked){
					isChecked=true;
					if(option[i].value==ans){
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
			}
			plus.textContent = "+"+index;
			if(isChecked!=true){
				alert("선택하고 눌러주세요.");
			}
		}
	</script>
</body>
</html>
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
	HELLO MCQ VIEW
	<p> 문제 고유 번호 : ${question.question_serial}
	<p> 과목 : ${question.sub_code_sum}
	<p>
	<p> 문제풀이 시도 횟수 합계 : ${question.question_count}+<span id="plus"></span>
	<p><img src="/project_403/resources/images/${question.question_img_name}">
	<p> 문제 : ${question.question_content}</p>
	<%
	for(int i=0; i<ans.length-1; i++){
	%>	
		| <input type="radio" name="option" value="<%=i+1%>" class="option"> <%=ans[i]%>
	<%
	}
	%>
	
	<%
		Question q = (Question)request.getAttribute("question");
	%>
	
	<p><button onclick="grading(<%=ans[4]%>, '<%=q.getQuestion_serial()%>')">정답 확인</button></p>
	<script>
		let count = 0;
		function grading(ans, question_serial){
			let option = document.querySelectorAll(".option");
			let plus = document.querySelector("#plus");
			let isChecked = false;
			for(let i=0; i<option.length; i++){	
				if(option[i].checked){
					isChecked=true;
					if(option[i].value==ans){
						count++;
						alert(count+"회만에 정답!");
						console.log(count);
						window.location.href="../Q?question_serial="+question_serial+"&plus="+count;
					} else{
						count++;
						alert("땡!");
					}
				} 
			}
			plus.textContent = count;
			if(isChecked!=true){
				alert("선택하고 눌러주세요.");
			}
		}
	</script>
</body>
</html>
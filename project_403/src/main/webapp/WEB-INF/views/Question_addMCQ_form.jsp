<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.spring.domain.Subject"%>
<%
	ArrayList<Subject> sub_all = (ArrayList<Subject>) request.getAttribute("sub_all");
	ArrayList<Subject> sub_all_name = (ArrayList<Subject>) request.getAttribute("sub_all_name");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<style>
	.question_main {
		width: 100%;
		border: 1px solid red;
		display: flex;
		justify-content: center;
	}
	
	.question_aside {
		width: 15%;	
		margin: 0px;	
		border-right: 1px solid;
 			
	}
	
	.question_aside ul{
		width: 100%;
		margin: 50% 0%;
		padding: 0px;
		border: 1px solid;
		
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}
	
	.question_aside li{
		width: 80%;
		margin: 20px 0px;
		border: 1px solid white;
		background-color: white; 
		text-align: center;
		transition: 1s;	
	}
	
	.question_aside a{
		color: black;
		font-size: 20px;
		text-decoration: none;
		transition: 1s;	
	}
	
	.question_aside li:hover{
		background-color: black; 
		transition: 1s;
	}
	
	.question_aside li:hover a{
		 color: white;
		 text-decoration: none;
		 transition: 1s;
	}
	
	.question_form {
		width: 85%;
		padding: 20px 30px;
	}
	
	.question_subject_chap{
		border: 1px solid;
	}
	
	.question_subject_chap select {
		width: 10%;
		margin: 0px 5px;
	}
	
	.question_content{
		border: 1px solid;
		border-radius : 10px; 
		margin: 10px 0px;
		padding: 20px 80px;
	}
	
	.question_form>input {
		width: 30%;
		border: 1px solid;
		color: black ;
		background-color: white;
		margin: 2% 35%;
	}
	
	.question_choice input{
		width: 500px;
		border: 0px solid;
		border-bottom: 1px dotted; 
    	box-shadow: 2px -2px 5px rgba(0, 0, 0, 0.1);

	}
	
	.question_ans input{
	
	}
	
</style>

<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	
	<div class="question_main">
		<div class="question_aside">
			<%@include file="/WEB-INF/views/Question_asidebar.jsp" %>
		</div>
		<form:form class="question_form" modelAttribute="question" action="Q_addMCQ" method="post" enctype="multipart/form-data" onsubmit="return validateMCQForm(event)">
			
			<!-- 과목 선택 영역 -->
			<div class="question_subject_chap">
				<span>과목명: </span>
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
				<select name="chap_select" id="chap_selector"></select>
	
				<label for="MCQ" onclick="addQuestion('MCQ')">객관식</label>
				<form:radiobutton path="question_id" value="MCQ" checked="checked" id="MCQ"/> | 
				<label for="SAQ" onclick="addQuestion('SAQ')">주관식</label>
				<form:radiobutton path="question_id" value="SAQ" id="SAQ"/> | 
				<label for="CP" onclick="addQuestion('CP')">코딩</label>
				<form:radiobutton path="question_id" value="CP" id="CP"/>
			</div>				
			
			<!-- 문제 입력 필드 -->
			<div class="question_content">
				<p>문제 내용<p> 
				<form:textarea path="question_content" style="width:100%; height:100px; resize: none;" placeholder="문제의 내용을 입력해 주세요."/>
				<div class="question_choice">
					<p> 1 : <form:input path="question_ans" placeholder="선택지를 입력해 주세요."/>
					<p> 2 : <form:input path="question_ans" placeholder="선택지를 입력해 주세요."/>
					<p> 3 : <form:input path="question_ans" placeholder="선택지를 입력해 주세요."/>
					<p> 4 : <form:input path="question_ans" placeholder="선택지를 입력해 주세요."/>
				</div>
				<hr>
				<div class="question_ans">
					<p>정답<p>	
					<label for="MCQ_1">1번</label>
					<form:radiobutton path="question_ans" value="1" id="MCQ_1"/> | 
					
					<label for="MCQ_2">2번</label>
					<form:radiobutton path="question_ans" value="2" id="MCQ_2"/> | 
					
					<label for="MCQ_3">3번</label>
					<form:radiobutton path="question_ans" value="3" id="MCQ_3"/> | 
					
					<label for="MCQ_4">4번</label>
					<form:radiobutton path="question_ans" value="4" id="MCQ_4"/>	
				</div>
				<hr>
				<div>
					<p>문제 난이도</p>
						<label for="level_1">1단계</label>
						<form:radiobutton path="question_level" value="1" id="level_1"/> |
						
						<label for="level_2">2단계</label>
						<form:radiobutton path="question_level" value="2" id="level_2"/> |
						
						<label for="level_3">3단계</label>
						<form:radiobutton path="question_level" value="3" id="level_3"/> |
						
						<label for="level_4">4단계</label>
						<form:radiobutton path="question_level" value="4" id="level_4"/> |
						
						<label for="level_5">5단계</label>
						<form:radiobutton path="question_level" value="5" id="level_5"/>
				</div>	
				<hr>
				<p>문제 이미지 <form:input type="file" path="question_img" id="imageInput"/>
			</div>
	
			<input type="submit" value="전송">
		</form:form>
	</div>
	<%@include file="/WEB-INF/views/footer.jsp" %>
	<script src="/project_403/resources/js/questionAdd.js"></script>
</body>
</html>
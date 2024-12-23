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
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	HELLO QUESTION addCP FORM PAGE
	<p>${member.getMem_serial()}
	<form:form modelAttribute="question" action="Q_addCP" method="post" enctype="multipart/form-data">
		<p> 과목명 
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
		<p> 문제분류 	<form:radiobutton path="question_id" value="MCQ" disabled="true"/>
						<label for="MCQ">객관식</label>
					<form:radiobutton path="question_id" value="SAQ" disabled="true"/>
						<label for="SAQ">주관식</label>
					<form:radiobutton path="question_id" value="CP" checked="checked"/>
						<label for="CP">코딩</label>
		<p> 문제 내용 <textarea name="question_content_text" rows="10" cols="70"></textarea>
		<p> 코드 내용 <form:textarea path="question_content" rows="20" cols="70"/>
		<p> 정답<form:input path="question_ans"/>
		<p> 문제 이미지 <form:input type="file" path="question_img"/>
		<p> 문제풀이 추가 카운트 <form:input path="question_plus"/>
		<p> 문제풀이 횟수 <form:input path="question_count"/>

		<p> <input type="submit" value="전송">
	</form:form>
	<script>
		let sub_name = document.querySelector("#name_selector");
		let sub_chap = document.querySelector("#chap_selector");
		sub_name.addEventListener("change", check);
		function check(){
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
						let option = document.createElement('option');
						console.log(list[i]);
						option.text = list[i];
				        sub_chap.appendChild(option);
					}
				},
				error : function(){
					arlet("데이터를 받아오지 못했습니다.");
				}
			});
		}
	</script>
</body>
</html>
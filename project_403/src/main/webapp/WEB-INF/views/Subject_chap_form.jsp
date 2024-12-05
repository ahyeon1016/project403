<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<body>
	HELLO SUBJECT FORM
	<form:form modelAttribute="subject" method="POST" action="sub_form">
		<!-- 추후에 ajax로 검색 가능하게 처리할 것임. -->
		<p>과목 이름 : <form:input path="sub_name" id="sub_name"/>
		<p>과목 챕터 : <form:input path="sub_chap" id="sub_chap"/> 
		<span style="display: none;" id="duplication">중복된 챕터입니다.</span>
		<p><input type="submit" value="전송" id="sub_submit">
	</form:form>
	
	
	<script>
		var	sub_chap = document.querySelector("#sub_chap");
		var	duplication = document.querySelector("#duplication");
		var	submit = document.querySelector("#sub_submit");
		sub_chap.addEventListener("input", chap);
		function chap(){
			var sub_chap_value = sub_chap.value;
			var sub_name_value = document.querySelector("#sub_name").value;
			console.log(sub_name_value);
			$.ajax({
				url : "subChapCheck",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"input_chap":sub_chap_value, 
					"input_name":sub_name_value
					}),
				success : function(data){
					if(data.check=="true"){
						console.log("true");
						duplication.style.display = "inline";
						submit.setAttribute("disabled", true);
					} else{
						console.log("false");
						duplication.style.display = "none";
						submit.removeAttribute("disabled");
					}
				},
				error : function(errorThrown){
					//alert("실패");
				}
			}); 
		}
	</script>
</body>
</html>
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
	HELLO SUBJECT CHAP FORM
	<form:form modelAttribute="subject" method="POST" action="sub_chap_form">
		<!-- 추후에 ajax로 검색 가능하게 처리할 것임. -->
		<p>과목 이름 : <form:input path="sub_name" id="c_sub_name" required/>
		<span style="display: none;" id="c1_duplication">존재하지 않는 과목입니다.</span>
		<p>과목 챕터 : <form:input path="sub_chap" id="c_sub_chap" required/> 
		<span style="display: none;" id="c2_duplication">중복된 챕터입니다.</span>
		<p><input type="submit" value="전송" id="c_sub_submit">
	</form:form>
		
	<script>
		var	c_sub_chap = document.querySelector("#c_sub_chap");
		var c_sub_name = document.querySelector("#c_sub_name");
		var	c1_duplication = document.querySelector("#c1_duplication");
		var	c2_duplication = document.querySelector("#c2_duplication");
		var	c_submit = document.querySelector("#c_sub_submit");
		c_sub_chap.addEventListener("input", c_chap);
		function c_chap(){
			var c_sub_chap_value = c_sub_chap.value;
			var c_sub_name_value = c_sub_name.value;
			console.log(c_sub_name_value);
			$.ajax({
				url : "subChapCheck",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"input_chap":c_sub_chap_value, 
					"input_name":c_sub_name_value
					}),
				success : function(data){
					if(data.check=="true"){
						console.log("true");
						c2_duplication.style.display = "inline";
						c_submit.setAttribute("disabled", true);
					} else{
						console.log("false");
						c2_duplication.style.display = "none";
						c_submit.removeAttribute("disabled");
					}
				},
				error : function(errorThrown){
					//alert("실패");
				}
			}); 
		}
		
		
		c_sub_name.addEventListener("input", c_name);
		function c_name(){
			var c_sub_name_value = c_sub_name.value;
			console.log(c_sub_name_value);
			$.ajax({
				url : "subNameCheck",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({"input_name":c_sub_name_value}),
				success : function(data){
					if(data.check=="true"){
						console.log("true");
						c1_duplication.style.display = "none";
						c_submit.removeAttribute("disabled");
						c_sub_chap.removeAttribute("disabled");
					} else{
						console.log("false");
						c1_duplication.style.display = "inline";
						c_submit.setAttribute("disabled", true);
						c_sub_chap.setAttribute("disabled", true);
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
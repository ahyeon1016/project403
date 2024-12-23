<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	HELLO SUBJECT NAME FORM
	<p>${member.getMem_id()}
	<form method="POST" action="sub_name_form">
		<p>과목 이름 : <input type="text" name="sub_name" id="n_sub_name" required/>
		<span style="display: none;" id="n_duplication">중복된 과목입니다.</span>
		<p><input type="submit" value="전송" id="n_sub_submit">
	</form>
	<script>
		var n_sub_name = document.querySelector("#n_sub_name");
		var	n_duplication = document.querySelector("#n_duplication");
		var	n_submit = document.querySelector("#n_sub_submit");
		n_sub_name.addEventListener("input", name);
		function name(){
			var n_sub_name_value = n_sub_name.value;
			console.log(n_sub_name_value);
			$.ajax({
				url : "subNameCheck",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({"input_name":n_sub_name_value}),
				success : function(data){
					if(data.check=="true"){
						console.log("true");
						n_duplication.style.display = "inline";
						n_submit.setAttribute("disabled", true);
					} else{
						console.log("false");
						n_duplication.style.display = "none";
						n_submit.removeAttribute("disabled");
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
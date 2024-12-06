<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<body>
	HELLO SUBJECT UPDATE NAME
	<form action="updateSubName" method="POST">
		<p>과목 : <input type="text" name="sub_name" value="${sub_name}" readonly>
		<p>기존과목 : <input type="text" name="new_sub_name" id="u_sub_name">
		<span style="display: none;" id="u_duplication">중복된 과목입니다.</span>
		<p><input type="submit" value="수정" id="u_sub_submit">
	</form>
	
	<script>
		var u_sub_name = document.querySelector("#u_sub_name");
		var	u_duplication = document.querySelector("#u_duplication");
		var	u_submit = document.querySelector("#u_sub_submit");
		u_sub_name.addEventListener("input", u_name);
		function u_name(){
			var u_sub_name_value = u_sub_name.value;
			console.log(u_sub_name_value);
			$.ajax({
				url : "subNameCheck",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({"input_name":u_sub_name_value}),
				success : function(data){
					if(data.check=="true"){
						console.log("true");
						u_duplication.style.display = "inline";
						u_submit.setAttribute("disabled", true);
					} else{
						console.log("false");
						u_duplication.style.display = "none";
						u_submit.removeAttribute("disabled");
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
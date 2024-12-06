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
	<form action="updateSubChap" method="POST">
		<h4>과목 : <input type="text" name="sub_name" value="${sub_name}" readonly id="u2_sub_name"></h4>
		<p>기존챕터 : <input type="text" value="${sub_chap}" name="old_sub_chap" readonly>
		<p>수정챕터 : <input type="text" name="new_sub_chap" id="u2_sub_chap" required>
		<span style="display: none;" id="u2_duplication">중복된 챕터입니다.</span>
		<p><input type="submit" value="수정" id="u2_sub_submit">
	</form>
	
	<script>
		var	u2_sub_chap = document.querySelector("#u2_sub_chap");
		var u2_sub_name = document.querySelector("#u2_sub_name");
		var	u2_duplication = document.querySelector("#u2_duplication");
		var	u2_submit = document.querySelector("#u2_sub_submit");
		u2_sub_chap.addEventListener("input", u2_chap);
		function u2_chap(){
			var u2_sub_chap_value = u2_sub_chap.value;
			var u2_sub_name_value = u2_sub_name.value;
			console.log(u2_sub_name_value);
			$.ajax({
				url : "subChapCheck",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"input_chap":u2_sub_chap_value, 
					"input_name":u2_sub_name_value
					}),
				success : function(data){
					if(data.check=="true"){
						console.log("true");
						u2_duplication.style.display = "inline";
						u2_submit.setAttribute("disabled", true);
					} else{
						console.log("false");
						u2_duplication.style.display = "none";
						u2_submit.removeAttribute("disabled");
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
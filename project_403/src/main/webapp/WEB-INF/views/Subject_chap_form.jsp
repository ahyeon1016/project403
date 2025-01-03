<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챕터 등록</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; }
        .form-container { max-width: 500px; margin: 0 auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .form-group { margin: 15px 0; }
        .form-group input[type="text"] { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .error-message { color: #dc3545; display: none; margin-top: 5px; }
        input[type="submit"] { background: #007bff; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
        input[type="submit"]:disabled { background: #ccc; }
    </style>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
    <div class="form-container">
        <h2>챕터 등록</h2>
        <form:form modelAttribute="subject" method="POST" action="sub_chap_form">
            <div class="form-group">
                <label>과목명</label>
                <form:input path="sub_name" id="c_sub_name" required="true"/>
                <span class="error-message" id="c1_duplication">존재하지 않는 과목입니다.</span>
            </div>
            <div class="form-group">
                <label>챕터명</label>
                <form:input path="sub_chap" id="c_sub_chap" required="true"/>
                <span class="error-message" id="c2_duplication">중복된 챕터입니다.</span>
            </div>
            <input type="submit" value="등록" id="c_sub_submit">
        </form:form>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
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
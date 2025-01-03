<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>과목명 수정</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .form-container { max-width: 500px; margin: 0 auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .form-group { margin: 15px 0; }
        input[type="text"] { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .error-message { color: #dc3545; display: none; margin-top: 5px; }
        input[type="submit"] { background: #007bff; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
        input[type="submit"]:disabled { background: #ccc; }
    </style>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
    <div class="form-container">
        <h2>과목명 수정</h2>
        <form action="updateSubName" method="POST">
            <div class="form-group">
                <label>현재 과목명</label>
                <input type="text" name="old_sub_name" value="${sub_name}" readonly>
            </div>
            <div class="form-group">
                <label>새 과목명</label>
                <input type="text" name="new_sub_name" id="u_sub_name" required>
                <span class="error-message" id="u_duplication">중복된 과목입니다.</span>
            </div>
            <input type="submit" value="수정" id="u_sub_submit">
        </form>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
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
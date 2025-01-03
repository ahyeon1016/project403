<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>과목명 등록</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
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
        <h2>과목명 등록</h2>
        <p>사용자: ${member.getMem_id()}</p>
        
        <form method="POST" action="sub_name_form">
            <div class="form-group">
                <label>과목명</label>
                <input type="text" name="sub_name" id="n_sub_name" required>
                <span class="error-message" id="n_duplication">중복된 과목입니다.</span>
            </div>
            <input type="submit" value="등록" id="n_sub_submit">
        </form>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
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
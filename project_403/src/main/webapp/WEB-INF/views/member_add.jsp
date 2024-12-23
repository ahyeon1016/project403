<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <%-- 아작스 사용을 위한 코드 --%>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<style>
html, body {
    height: 100%;
    margin: 0;
    font-family: Arial, sans-serif; /* 세련된 폰트 설정 */
    background: #f0f0f0; /* 밝은 배경색 설정 */
}

body {
    display: flex;
    justify-content: center;
    align-items: center;
}

.container {
    border: 1px solid #ccc;
    background: #fff;
    border-radius: 10px; /* 모서리를 둥글게 설정 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
    display: flex;
    flex-direction: column;
    text-align: center;
    width: 80%; /* 너비 증가 */
    max-width: 600px; /* 최대 너비 설정 */
    padding: 40px; /* 여백 추가 */
}

input {
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    border: 1px solid #ccc;
    border-radius: 5px;
}

button {
    width: 100%;
    padding: 10px;
    background: #007BFF;
    border: none;
    border-radius: 5px;
    color: white;
    font-size: 16px;
    cursor: pointer;
    transition: background 0.3s ease;
}

button:hover {
    background: #0056b3;
}

a {
    color: #007BFF;
    text-decoration: none;
    margin: 10px 0;
    display: block;
}

a:hover {
    text-decoration: underline;
}
</style>
</head>
<body>

		<form:form modelAttribute="member" action="./login" method="post">
		    <div class="container">
		        <h2>회원가입</h2>
		        <label for="mem_id">아이디</label>
		        <input id="mem_id" maxlength="15" autocomplete="off"/>
		        <button type="button" id="check_id">중복 검사</button>
		        <span> *아이디는 최소 3자, 최대 15자입니다.</span><br>
		
		        <label for="mem_pw">비밀번호</label>
		        <input id="mem_pw" type="password" maxlength="15" required/>
		        <span> *3~15자 이내의 값을 입력해주세요.</span><br>
		
		        <label for="mem_pw_submit">비밀번호 확인</label>
		        <input id="mem_pw_submit" type="password" maxlength="15" required/>
		        <span id="conf"></span><br>
		
		        <label for="mem_nickName">닉네임</label>
		        <input id="mem_nickName" maxlength="10"/><br>
				<span> *닉네임은 특수문자를 포함하지 않아야합니다.</span>
				<br>
		        <label for="mem_email">이메일</label>
		        <input type="email" id="mem_email" autocomplete="off" required/>
		        <span> *아이디@naver.com의 형식으로 써주세요.</span><br>
		
		        <button type="submit" id="sub" disabled>회원 가입하기</button>
		        <h5>아이디 중복검사 후 활성화됩니다.</h5>
		
		        <a href="./">뒤로가기</a>
		    </div>
		</form:form>
	<script>
	// 비밀번호 확인
	document.querySelector("#mem_pw_submit").addEventListener("keyup", passck);
	
	function passck(){
	    let pass = document.querySelector("#mem_pw").value;
	    let passub = document.querySelector("#mem_pw_submit").value;
	    
	    if(pass === passub){
	        document.querySelector("#conf").innerText = "일치합니다.";
	    } else {
	        document.querySelector("#conf").innerText = "불일치합니다.";
	    }
	}
	
	// 아이디 중복검사 실행
	document.querySelector("#check_id").addEventListener("click", memck);
	document.querySelector("#sub").addEventListener("click", finalck);
	
	function memck(){
	    console.log("memck함수 실행");
	    let mem_id = document.querySelector("#mem_id").value;
	    let sub = document.querySelector("#sub");
	    $.ajax({
	        url: "check",
	        type: "post",
	        data: JSON.stringify({"mem_id": mem_id}),
	        contentType: "application/json; charset=UTF-8",
	        success: function(data){
	            alert(data.key);
	            if(data.isavail !== false){
	                sub.removeAttribute("disabled");
	            } else {
	                sub.setAttribute("disabled", "disabled");
	            }
	        },
	        error: function(errorThrown){
	            alert("fail");
	        }
	    });
	}
	
	function finalck(event){
	    event.preventDefault();
	    console.log("finalck함수 실행");
	    let mem_id = document.querySelector("#mem_id").value;
	    let mem_pw = document.querySelector("#mem_pw").value;
	    let mem_pwsub = document.querySelector("#mem_pw_submit").value;
	    let mem_nick = document.querySelector("#mem_nickName").value;
	    let mem_email = document.querySelector("#mem_email").value;
	    $.ajax({
	        url: "finalck",
	        type: "post",
	        data: JSON.stringify({"mem_id": mem_id, "mem_pw": mem_pw, "mem_nick": mem_nick, "mem_email": mem_email, "mem_pw_sub": mem_pwsub}),
	        contentType: "application/json; charset=UTF-8",
	        success: function(data){
	            alert(data.key);
	            console.log(data.key);
	            if(data.key === "회원가입 성공!"){
	                window.location.href = "./";
	            }
	        },
	        error: function(errorThrown){
	            alert("fail");
	        }
	    });
	}
	</script>
</body>
</html>

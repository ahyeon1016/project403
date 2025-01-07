<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
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
    justify-content: center;
    align-items: center;
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
    margin: 10px;
    display: inline-block;
}

a:hover {
    text-decoration: underline;
}

img {
    margin: 10px;
}

.link-container {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
</head>
<body>
	<form:form modelAttribute="member" action="./login" method="post">
	    <div class="container">
	        <h2>로그인</h2>
	        <label for="mem_id">아이디</label>
	        <form:input path="mem_id" id="mem_id" autocomplete="off" /><br>
	        <label for="mem_pw">비밀번호</label>
	        <form:input path="mem_pw" id="mem_pw" type="password" required="true" /><br>
	        <button type="submit">로그인</button>
	        <div class="link-container">
	            <a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=dYy4lyrqfeCoc7Q3NRsE&state=STATE_STRING&redirect_uri=http://localhost:8080/member/login/naver">
	                <img src="/resources/images/btnG_축약형.png" width="80px" height="30px"/>
	            </a>

	            <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=17731f8bc4cf09ad45f06addfd541982&redirect_uri=http://wjdwoals222.cafe24.com/member/login/kakao">
	                <img src="/resources/images/kakao_login_small.png" width="80px" height="30px"/>
	            </a>
	        </div>
	        <a href="/project_403">뒤로가기</a>
	    </div>
	</form:form>
	
</body>
</html>

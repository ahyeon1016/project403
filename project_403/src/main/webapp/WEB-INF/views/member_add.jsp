<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <%--아작스 사용을 위한 코오드 --%>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
</head>
<body>
<div>
<form method="get">
아이디<input id="mem_id"  maxlength="15"/><button type="button" id="check_id">중복 검사</button><br>
비밀번호<input id="mem_pw" type="password" maxlength="15" required/> *3~15자 이내의 값을 입력해주세요.  <br>
닉네임<input id="mem_nickName" maxlength="10"/><br>
이메일<input type="email" id="mem_email" required/> *아이디@naver.com의 형식으로 써주세요. <br>
<button type="submit" id="sub" disabled>회원 가입하기</button> <h5>아이디 중복검사 후 활성화됩니다.</h5>
</form>
</div>


<script>
//ajax로 아이디 중복검사를 실행하는 함수 만들기
document.querySelector("#check_id").addEventListener("click",memck);
document.querySelector("#sub").addEventListener("click",finalck);
//아이디 중복검사 실행
function memck(){
	console.log("memck함수 실행");
	let mem_id=document.querySelector("#mem_id").value;
	let sub=document.querySelector("#sub");
	$.ajax({
		url:"check",
		type:"post",
		data:JSON.stringify({"mem_id":mem_id}),
		contentType:"application/json; charset=UTF-8",
		success:function(data){
			alert(data.key);
			if(data.isavail!=false){
				sub.removeAttribute("disabled");
			}else sub.Attribute="disabled";
		},
		error:function(errorThrown){
			alert("fail");
		}
	});
	
}
//회원가입 버튼을 누를시 중복검사하는 함수 실행
function finalck(){
	console.log("finalck함수 실행");
	let mem_id=document.querySelector("#mem_id").value;
	let sub=document.querySelector("#sub");
	let mem_pw=document.querySelector("#mem_pw").value;
	let mem_nick=document.querySelector("#mem_nickName").value;
	let mem_email=document.querySelector("#mem_email").value;
	$.ajax({
		url:"finalck",
		type:"post",
		data:JSON.stringify({"mem_id":mem_id,"mem_pw":mem_pw,"mem_nick":mem_nick,"mem_email":mem_email}),
		contentType:"application/json; charset=UTF-8",
		success:function(data){
			alert(data.key);
			console.log(data.key);
			if(data.key=="회원가입 성공!"){
			window.location.href="/project_403";
			}
		},
		error:function(errorThrown){
			alert("fail");
		}
	});
	
	
}




</script>
</body>
</html>
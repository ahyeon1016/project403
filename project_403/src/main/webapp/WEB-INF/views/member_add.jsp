<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <%--아작스 사용을 위한 코오드 --%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post">
아이디<input id="mem_id"/><button type="button" id="check_id">중복 검사</button><br>
비밀번호<input id="mem_pw" type="password" minlength="3" maxlength="15" placeholder="3~15자 사이의 값이어야 합니다."/>  <br>
닉네임<input id="mem_nickName"/><br>
<button type="submit" id="sub" disabled>회원 가입하기</button> <h5>아이디 중복검사 후 활성화됩니다.</h5>
</form>


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
function finalck(event){
	console.log("finalck함수 실행");
	let mem_id=document.querySelector("#mem_id").value;
	let sub=document.querySelector("#sub");
	let mem_pw=document.querySelector("#mem_pw").value;
	let mem_nick=document.querySelector("#mem_nickName").value;
	$.ajax({
		url:"finalck",
		type:"post",
		data:JSON.stringify({"mem_id":mem_id,"mem_pw":mem_pw,"mem_nick":mem_nick}),
		contentType:"application/json; charset=UTF-8",
		success:function(data){
			alert(data.key);
			window.location.href="./";
			
		},
		error:function(errorThrown){
			alert("fail");
		}
	});
	
	
}




</script>
</body>
</html>
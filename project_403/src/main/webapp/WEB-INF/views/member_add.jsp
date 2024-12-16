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
<form>
	<table>
		<tr>
			<td>아이디</td><td><input id="mem_id"  maxlength="15"/><button type="button" id="check_id" >중복 검사</button> <span> *아이디는 최소3자,최대 15자입니다.</span></td>
		</tr>
		<tr>
			<td>비밀번호</td><td><input id="mem_pw" type="password" maxlength="15" required/> *3~15자 이내의 값을 입력해주세요.  </td>
		</tr>
		<tr>
			<td>비밀번호 확인</td><td><input id="mem_pw_submit" type="password" maxlength="15" required/> <span id="conf"></span> </td>
		</tr>
		<tr>
			<td>닉네임</td><td><input id="mem_nickName" maxlength="10" id="mem_nick"/></td>
		</tr>
		<tr>
			<td>이메일</td><td><input type="email" id="mem_email" required/> *아이디@naver.com의 형식으로 써주세요. </td>
		</tr>
		<tr>
			<td><button type="submit" id="sub" disabled>회원 가입하기</button> <h5>아이디 중복검사 후 활성화됩니다.</h5></td>
		</tr>	
	</table>
	<a href="./">뒤로가기</a>
</form>
</div>


<script>
//비밀번호 확인

document.querySelector("#mem_pw_submit").addEventListener("keyup",passck);

function passck(){
	let pass=document.querySelector("#mem_pw").value;
	let passub=document.querySelector("#mem_pw_submit").value;
	
	if(pass==passub){
		document.querySelector("#conf").innerText="일치합니다.";
	}else{
		document.querySelector("#conf").innerText="불일치합니다.";
	}
	
	
}


//ajax로 중복검사를 실행하는 함수 만들기
document.querySelector("#check_id").addEventListener("click",memck);
document.querySelector("#sub").addEventListener("click",finalck);
//아이디 중복검사 실행
function memck(){
	console.log("memck함수 실행");
	let mem_id=document.querySelector("#mem_id").value;
	let mem_nick=document.querySelector("#mem_nick").value;
	let sub=document.querySelector("#sub");
	$.ajax({
		url:"check",
		type:"post",
		data:JSON.stringify({"mem_id":mem_id,"mem_nick":mem_nick}),
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
	event.preventDefault();
	console.log("finalck함수 실행");
	let mem_id=document.querySelector("#mem_id").value;
	let sub=document.querySelector("#sub");
	let mem_pw=document.querySelector("#mem_pw").value;
	let mem_pwsub=document.querySelector("#mem_pw_submit").value;
	let mem_nick=document.querySelector("#mem_nickName").value;
	let mem_email=document.querySelector("#mem_email").value;
	$.ajax({
		url:"finalck",
		type:"post",
		data:JSON.stringify({"mem_id":mem_id,"mem_pw":mem_pw,"mem_nick":mem_nick,"mem_email":mem_email,"mem_pw_sub":mem_pwsub}),
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
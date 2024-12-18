<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <%--아작스 사용을 위한 코오드 --%>
<title>Insert title here</title>
</head>
<body>
<form action="update?mem_id=${member.getMem_id()}" method="post">
<table>
<tr>
<td>아이디:</td><td id="id">${member.getMem_id()}</td>
</tr>
<tr>
<td>닉네임:</td><td>${member.getMem_nickName()}</td>
</tr>
<tr>
<td>이메일:</td><td id="email">${member.getMem_email()}</td>
</tr>
<tr>
<td>이메일 인증여부:</td><td id="isconfirmed">${member.isMem_confirmed()}</td><td id="conf"><button type="button" id="mailbut">이메일 인증하기</button></td>
</tr>
<tr>
<td>포인트:</td><td>${member.getMem_point()}</td>
</tr>
<tr>
<td>경험치:</td><td>${member.getMem_exp()}</td>
</tr>
<tr>
<td>마지막 접속일:</td><td>${member.getMem_date()}</td>
</tr>

</table>
<button type="submit" >정보수정</button>
</form>
<a href="./">뒤로가기</a>
<script>
	let mail=document.querySelector("#email");
	let conf=document.querySelector("#conf");
	let isconf=document.querySelector("#isconfirmed");
	if(isconf.innerText=="true"){
		document.querySelector("#mailbut").style.display="none";
	}
	document.querySelector("#mailbut").addEventListener("click",confirmm);
	function confirmm(){
		let user_mail=document.querySelector("#email").innerText;
		let user_id=document.querySelector("#id").innerText;
		$.ajax({
			url:"email",
			type:"post",
			data:JSON.stringify({"user_mail":user_mail,"user_id":user_id}),
			contentType:"application/json; charset=UTF-8",
			success:function(data){
				alert("메일 전송 완료!");
			},
			error:function(errorThrown){
				alert("fail");
			}
		});
		
		
		
	}
	
</script>

</body>
</html>
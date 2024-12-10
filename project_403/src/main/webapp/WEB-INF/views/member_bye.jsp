<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 페이지</title>
</head>
<body>
<form action="delete_bye" method="post" id="fom">
<p>${mem_id}님 정말로 탈퇴하시겠습니까?</p>
<p>탈퇴를 하시려면 버튼을 눌러주세요</p>
<input type="submit" value="탈퇴하기" id="but">
<a href="/project_403">취소하기</a>
</form>


<script>
	document.querySelector("#but").addEventListener("click",del);
	let form=document.querySelector("#fom");
	function del(event){
	event.preventDefault();
	let bye=window.confirm("정말로 탈퇴하시겠습니까?")
	if(bye){
		alert("탈퇴되었습니다.")
		form.submit();	
	}
	}


</script>
</body>
</html>
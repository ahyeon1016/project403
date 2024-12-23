<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.spring.domain.Member"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>닉네임 변경 페이지</title>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	
	<form action="nick/change?mem_id=<%=member.getMem_id()%>"method="post">
	
		닉네임 :<input type="text" value="<%=member.getMem_nickName()%>" maxlength="10" name="nick" pattern="^[가-힣a-zA-Z0-9]+$" required> 변경할 닉네임을 써주세요 <br> 
		<button type="submit" id="sub">변경하기</button> 
		<a href="/project_403/member">취소하기</a>
	</form>
<script>
	document.querySelector("#sub").addEventListener("click",function(){alert("변경되었습니다!");})

</script>
</body>
</html>
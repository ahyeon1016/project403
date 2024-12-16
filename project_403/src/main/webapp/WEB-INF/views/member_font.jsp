<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<%@ page import="com.spring.domain.Member_Item" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>닉네임 색상 변경 페이지</title>
<style>
	.text-primary{
	color:blue;
	}
	.text-success{
	color:green;
	}
	.text-danger{
	color:red;
	}
	.text-warning{
	color:orange;
	}
</style>
</head>
<body>
	<%
		Member member=(Member)request.getAttribute("member");
		Member_Item mi=(Member_Item)request.getAttribute("member_item");
	%>
	<span id="nick_name" class="<%=mi.getMem_color()%>"><%=member.getMem_nickName() %></span>
	
	<form action="./font/change?mem_id=<%=member.getMem_id()%>" method="post" id="form">
		<select id="colors" name="color">
			<option value="text-danger">빨간색</option>
			<option value="text-primary">파란색</option>
			<option value="text-warning">주황색</option>
			<option value="text-success">초록색</option>
		</select>
		<br>
		<button type="submit" id="sub">변경하기</button>
	</form>
		<button id="back" onclick="history.back();">뒤로가기</button>
	
	<script>
		let colors=document.querySelector("#colors");
		colors.addEventListener("change",color_change);
		let nick=document.querySelector("#nick_name");
		function color_change(){
			let color=colors.value;
			nick.className="";
			nick.classList.add(color);
		}
		document.querySelector("#sub").addEventListener("click",submit);
		function submit(e){
			e.preventDefault();
			if(confirm("변경하시겠습니까?")){
				alert("닉네임 색상이 변경되었습니다.");
				document.querySelector("#form").submit();
			}
			
		}
		
		
	</script>
</body>
</html>
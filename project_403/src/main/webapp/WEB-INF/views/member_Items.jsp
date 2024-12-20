<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member_Item" %>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	Member_Item mi=(Member_Item)request.getAttribute("mem_item");
	Member member=(Member)session.getAttribute("member");
%>

<div>
		<h1><b id="mem_id"><%=member.getMem_nickName()%></b>님의 아이템</h1>
	<ul>
		<li>닉네임 변경권:<%if(mi.isMem_itemA()){%>보유 <form action="item/nick?mem_id=<%=mi.getMem_id()%>" method="post" id="nick"><button type="button" id="a_use">사용하기</button><%}else{ %>없음<%} %></form></li>
		<li>닉네임 색상 변경권:<%if(mi.isMem_itemB()){%>보유 <form action="item/font?mem_id=<%=mi.getMem_id()%>" method="post" id="font"><button type="button" id="b_use">사용하기</button><%}else{ %>없음<%} %></form></li> 
		<li>현재 폰트색상:<b><%=mi.getMem_color() %></b></li>
	</ul>

</div>


<script>
	
	let mem_id="<%=mi.getMem_id()%>";
	console.log(mem_id);
	let a_button=document.querySelector("#a_use");
	if(a_button){
	a_button.addEventListener("click",use_a);
	function use_a(){
			if(confirm("사용하시겠습니까?")){
			document.querySelector("#nick").submit();
			}
		}
	}
	let b_button=document.querySelector("#b_use");
	if(b_button){
		b_button.addEventListener("click",use_b);
		function use_b(){
			if(confirm("사용하시겠습니까?")){
				document.querySelector("#font").submit();
			}
		}
	}
	
</script>
</body>
</html>
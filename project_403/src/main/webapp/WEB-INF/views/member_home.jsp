<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.spring.domain.Member"
%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>홈</title>
   <style>
   *{
   	list-style: none;
   	text-decoration:none;
   }
   
   </style>
</head>
<body>
	
   <div class="container">
   		<h1>&laquo; HELLO MEMBER HOME!!! &raquo; </h1>
   	
   		<a href="/project_403/member/alarm">되냐</a>
   	
       <%
       		Member member=(Member)session.getAttribute("member");
       	if(member==null){ %>
           <form action="/project_403/member/add" method="get">
               <button type="submit">회원가입</button>
           </form>
           <form action="/project_403/member/login" method="get">
               <button type="submit">로그인</button>
           </form>
       <%}else {

    	   ArrayList<String> arr=member.getAlarm_list(); %>
           <form action="/project_403/member/mypage" method="post">
               <button type="submit">마이페이지</button>
           </form>
           <%if(member.getMem_alarm()!=null){ %>
           	<ul>
           		<%for(int i=0;i<arr.size();i++){ %>
           			<li><a href="/project_403/member/alarm/delete?index=<%=i%>" class="delbut"><%=arr.get(i) %></a></li>
           		<%} %>
           	</ul>
           	
           	
           <%} %>
          <%if(member.isMem_admin()==true){ %>
          <form action="/project_403/member/admin?page=1" method="post">
   		   	<button type="submit">관리자페이지</button>
          </form> 
       <%}
     } %>
   </div>
   <script>
   let but=document.querySelectorAll(".delbut");
   for(let i=0;i<but.length;i++){
	   but[i].addEventListener("click",del);
   }
   function del(event){
	  this.style.color="gray";
   }
  
   </script>
</body>
</html>
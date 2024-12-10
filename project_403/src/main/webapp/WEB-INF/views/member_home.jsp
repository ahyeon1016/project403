<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.spring.domain.Member"
%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>홈</title>
   
</head>
<body>
   <div class="container">
   		
       <%
       		Member member=(Member)session.getAttribute("member");
       	if(member==null){ %>
           <form action="/project_403/member/add" method="get">
               <button type="submit">회원가입</button>
           </form>
           <form action="/project_403/member/login" method="get">
               <button type="submit">로그인</button>
           </form>
       <%}else { %>
           <form action="/project_403/member/mypage" method="post">
               <button type="submit">마이페이지</button>
           </form>
          <%if(member.isMem_admin()==true){ %>
          <form action="" method="post">
   		   	<button type="submit">관리자페이지</button>
          </form> 
       <%}
     } %>
   </div>
   
</body>
</html>
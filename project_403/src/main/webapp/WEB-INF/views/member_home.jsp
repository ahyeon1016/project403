<!-- nav.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="com.spring.domain.Member"
%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
    * {
        list-style: none;
        text-decoration: none;
    }
    .nav {
    	margin-top:10px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
        background-color: black;
        padding: 10px;
    }
    .nav a{
        color: white;
        background: none;
        border: none;
        padding: 10px;
        cursor: pointer;
        font-size: 16px;
    }
    .nav a:hover{
        text-decoration: underline;
    }
    .nav #left-a{
    	position:absolute;
    	left:30px;
    
    }
    .logo {
        margin-right: auto;
        display: flex;
        align-items: center;
    }
  
    </style>
</head>
<body>
    <div class="nav">
        <a href="/project_403/" class="logo" id="left-a">
            RALLYPOLLY
        </a>
        <%
            Member member = (Member) session.getAttribute("member");
            if (member == null) {
        %>
            <a href="/project_403/member/add">회원가입</a>
            <a href="/project_403/member/login">로그인</a>
        <%
            } else {
               
        %>
            <a href="/project_403/member/mypage">마이페이지</a>
            <% if (member.isMem_admin()) { %>
                <a href="/project_403/member/admin?page=1">관리자페이지</a>
            <% } %>
        <% } %>
    </div>
    <script>
    let but = document.querySelectorAll(".delbut");
    for (let i = 0; i < but.length; i++) {
        but[i].addEventListener("click", del);
    }
    function del(event) {
        this.style.color = "gray";
    }
    </script>
</body>
</html>

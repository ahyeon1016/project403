<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="com.spring.domain.Member"
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>홈</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
        }
        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .container {
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="navbar">
            <%
                Member member = (Member) session.getAttribute("member");
                if (member == null) { %>
                    <a href="/project_403/member/add">회원가입</a>
                    <a href="/project_403/member/login">로그인</a>
                <% } else { %>
                    <a href="/project_403/member/mypage">마이페이지</a>
                    <% if (member.isMem_admin()) { %>
                        <a href="/project_403/member/admin?page=1">관리자페이지</a>
                    <% } %>
                <% }
            %>
        </div>
    </div>
</body>
</html>

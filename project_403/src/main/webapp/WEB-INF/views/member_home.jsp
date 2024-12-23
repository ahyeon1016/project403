<!-- nav.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="com.spring.domain.Member"
%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Navigation</title>
    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
    
    	
        .navbar-dark .navbar-nav .nav-link {
            color: rgba(255,255,255,.75);
        }
        .navbar-dark .navbar-nav .nav-link:hover {
            color: rgba(255,255,255,1);
        }
        #left-a {
            color: white;
            font-weight: bold;
            text-decoration: none;
        }
        #left-a:hover {
            color: white;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a href="/project_403/" class="navbar-brand" id="left-a">RALLYPOLLY</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <%
                        Member member = (Member) session.getAttribute("member");
                        if (member == null) {
                    %>
                        <li class="nav-item">
                            <a class="nav-link" href="/project_403/member/add">회원가입</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/project_403/member/login">로그인</a>
                        </li>
                    <%
                        } else {
                    %>
                        <li class="nav-item">
                            <a class="nav-link" href="/project_403/member/mypage">마이페이지</a>
                        </li>
                        <% if (member.isMem_admin()) { %>
                            <li class="nav-item">
                                <form action="/project_403/member/admin?page=1" method="post" class="form-inline">
                                    <button type="submit" class="nav-link btn btn-link">관리자페이지</button>
                                </form>
                            </li>
                        <% } %>
                        <li class="nav-item">
                            <a class="nav-link" href="/project_403/member/logout">로그아웃</a>
                        </li>
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

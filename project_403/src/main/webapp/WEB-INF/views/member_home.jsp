<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="com.spring.domain.Member"
%>
<%@ page import="java.util.*" %>
<%@ page import="com.spring.domain.Member_Item" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Navigation</title>
    <!-- 폰트 -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,700&display=swap">
  	<!-- 부트스트랩 적용 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
    
    	* {
    	 font-family: 'Open Sans', sans-serif;
    	 }
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
        .nav-item{
        	margin:10px 0;
        	align-content: center;
        	padding-right:10px;
        }
        
        .nav-item form{
        	margin:10px 10px;
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
            <div class="collapse navbar-collapse justify-content-around" id="navbarNav">
                <ul class="navbar-nav">
                	<li class="nav-item">
                		<a class="nav-link" href="/project_403/"></a>
                	</li>
                	<li class="nav-item">
                		<a class="nav-link" href="/project_403/Q/main">문제 작성하기</a>
                	</li>
                	<li class="nav-item">
                		<a class="nav-link" href="/project_403/test/testAdd">시험지 추가하기</a>
                	</li>
                	<li class="nav-item">
                		<a class="nav-link" href="/project_403/test/testAll">시험지 보기</a>
                	</li>
                	<li class="nav-item">
                		<a class="nav-link" href="/project_403/QnA/main">질문 게시판</a>
                	</li>
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
                        	Member_Item mi=(Member_Item)session.getAttribute("member_item");
                    %>
                        <li class="nav-item">
                            <a class="nav-link" href="/project_403/member/mypage">마이페이지</a>
                        </li>
                        <% if (member.isMem_admin()) { %>
                            <li class="nav-item">
                                <form action="/project_403/member/admin?page=1" method="post" class="form-inline">
                                    <button type="submit" class="nav-link btn btn-link">모든 회원 조회</button>
                                </form>
                            </li>
                            <li class="nav-item">
                            	<a href="/project_403/sub" class="nav-link">Subject</a>
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

    
</body>
</html>

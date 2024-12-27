<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member"%>
<%@ page import="java.util.*"%>
<%@ page import="com.spring.domain.Member_Item"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Navigation</title>
    <!-- 폰트 -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,700&display=swap">
    <!-- 부트스트랩 적용 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Font Awesome 적용 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
    * {
        font-family: 'Open Sans', sans-serif;
        list-style: none;
        text-decoration: none;
    }
    .navbar {
       height: 70px !important;
   	   overflow: visible !important; /* 잘리는 문제 방지 */
       align-items: center !important;
       z-index: 1000; /* 적당히 높은 값 설정 */
       margin-bottom:0;
        
    }
    .navbar-brand {
        height: 100%; /* 부모 요소의 높이에 맞춤 */
        display: flex;
        align-items: center; /* 수직 정렬 */
    }
    .navbar-nav .nav-link {
        height: 100%; /* 부모 요소의 높이에 맞춤 */
        display: flex;
        align-items: center !important;
        color: rgba(255, 255, 255, .75);
    }
    .navbar-nav .nav-link:hover {
        color: rgba(255, 255, 255, 1);
    }
    #dropdown {
        cursor: pointer;
    }
    .nav-item {
        margin: 10px 0;
        align-content: center;
        padding-right: 10px;
    }
    .nav-item form {
        margin: 10px 10px;
    }
    #down_menu {
        position: absolute;
        right: 380px;
        top: 60px;
        display: none;
        background-color: #333; /* 어두운 배경색 */ 
        color: white; /* 텍스트 색상 */ 
        border: 1px solid gray; /* 테두리 색상 */ 
        border-radius: 10px; /* 테두리 둥글게 */ 
        padding: 10px; /* 내부 여백 */ 
        z-index: 2;
        overflow: hidden; /* overflow 속성 추가 */
    }
    #down_menu > img {
        border-radius: 50px;
    }
    #down_menu > ul > li > a {
        display: inline-block;
        padding: 10px 15px;
        color: white;
        background-color: #555; /* 버튼 배경색 */
        border-radius: 5px;
        text-align: center;
        text-decoration: none;
        margin-right: 10px; /* 버튼 간 간격 */
        transition: background-color 0.3s;
    }
    #down_menu > ul > li > a:hover {
        background-color: #777; /* 호버 시 배경색 */
    }
    .fa-caret-down {
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
                            String profile = member.getMem_profile_name();
                            Member_Item mi = (Member_Item) session.getAttribute("member_item");
                    %>
                        <li class="nav-item">
                            <span class="nav-link" id="dropdown"><%= member.getMem_nickName() %><i class="fa fa-caret-down"></i></span>
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
                </ul>
                <div id="down_menu">
                    <% if (profile != null && profile.startsWith("http")) { %>
                        <img src="<%= profile %>" width="200px" height="200px" alt="Profile Image">
                    <% } else { %>
                        <img src="/project_403/resources/images/<%= profile %>" width="100" height="100" alt="Profile Image">
                    <% } %>
                    <ul>
                        <li>
                            <span><b class="<%= mi.getMem_color() %>"><%= member.getMem_nickName() %></b>님, 환영합니다!</span>
                        </li>
                        <li>
                            <span>경험치: <%= member.getMem_exp() %></span>
                        </li>
                        <li>
                            <span>포인트: <%= member.getMem_point() %></span>
                        </li>
                        <li>
                            <a class="nav-link" href="/project_403/member/mypage">마이페이지</a>
                            <a class="nav-link" href="/project_403/member/logout">로그아웃</a>
                        </li>
                    </ul>
                </div>
                <%
                        }
                    %>
            </div>
        </div>
    </nav>
<script>
    let dropdown = document.querySelector("#dropdown");
    let menu = document.querySelector("#down_menu");
    dropdown.addEventListener("click", downmenu);
    function downmenu() {
        if (menu.style.display != "flex") {
            menu.style.display = "flex";
        } else {
            menu.style.display = "none";
        }
    }
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member"%>
<%@ page import="java.util.*"%>
<%@ page import="com.spring.domain.Member_Item" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Navigation</title>
    <!-- 폰트 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&family=Orbit&display=swap" rel="stylesheet">   
    <!-- 부트스트랩 적용 -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
	<!-- Font Awesome 적용 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="https://kit.fontawesome.com/0ae4c75b50.js" crossorigin="anonymous"></script>

	<style>
    * {
        font-family: 'Gowun Dodum', sans-serif;
        list-style: none;
        text-decoration: none;
        box-sizing:border-box;
    }
    .navbar {
        height: 70px !important;
        overflow: visible !important;
        align-items: center !important;
        background-color: #213555;
        z-index: 1000;
        margin-bottom: 0;
    }
    .navbar-brand {
        height: 100%;
        display: flex;
        align-items: center;
        color: #F5EFE7; 
        font-weight: bold;
        transition:1s;
    }
    .navbar-brand:hover {
        color: #005f99; /* 호버 시 더 깊은 블루 */
    }
    .navbar-nav .nav-link {
        height: 100%;
        display: flex;
        align-items: center !important;
        color: #F5EFE7; 
        transition: color 0.3s ease;
    }
    .navbar-nav .nav-link:hover {
        color: lightblue; /* 호버 효과 */
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
        background-color: #3E5879; /* 밝은 블루 */
        color: #F5EFE7;
        border: 1px solid #87cefa;
        border-radius: 10px;
        padding: 10px;
        z-index: 2;
    }
    #down_menu > img {
        border-radius: 50px;
    }
    #down_menu > ul > li > a {
        display: inline-block;
        padding: 10px 15px;
        color: #003f66;
        background-color: #d1ecf1; /* 부드러운 블루 */
        border-radius: 5px;
        text-align: center;
        text-decoration: none;
        margin-right: 10px;
        transition: background-color 0.3s;
    }
    #down_menu > ul > li > a:hover {
        background-color: #c4e3ed; /* 더 밝은 블루 */
    }
    .fa-caret-down {
        color: lightblue;
    }
    .fa-solid {
        color: #005f99;
        cursor: pointer;
        font-size: medium;
    }

    /* 알림 관련 CSS */
    #alarm_bell {
        position: relative;
        font-size: large;
        color: #005f99;
        transition: transform 0.3s, color 0.3s;
    }

    #alarm_bell:hover {
        transform: scale(1.2);
        color: lightblue;
    }

    #alarm_list {
        position: absolute;
        top: 100%;
        right: 300px;
        display: none;
        background-color: #3E5879;
        color: #D8C4B6;
        border: 1px solid #87cefa;
        border-radius: 10px;
        padding: 15px;
        z-index: 2;
        width: 300px;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    }

    #alarm_list ul {
        padding: 0;
        margin: 0;
        list-style-type: none;
    }

    #alarm_list ul li {
        margin-bottom: 10px;
    }

    #alarm_list ul li a {
        display: inline-block;
        padding: 8px 12px;
        color: #003f66;
        background-color: #d1ecf1;
        border-radius: 5px;
        text-align: center;
        text-decoration: none;
        margin-right: 10px;
        transition: background-color 0.3s;
    }

    #alarm_list ul li a:hover {
        background-color: #c4e3ed;
        color: #005f99;
    }

    #alarm_list p {
        margin: 0;
        padding: 10px;
        color: #666;
        font-style: italic;
        text-align: center;
    }
</style>

</head>
<body>
    <nav class="navbar navbar-expand-lg bg-blue">
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
                        <a class="nav-link" href="/project_403/Q/Q_all">문제 작성하기</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/project_403/test/testAdd">시험지 추가하기</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/project_403/test/testAll">시험지 보기</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/project_403/QnA/commentRootAll?page=1">질문 게시판</a>
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
                            <span class="nav-link <%=mi.getMem_color() %>" id="dropdown"><b><%= member.getMem_nickName() %></b><i class="fa fa-caret-down"></i></span>
                        </li>
                        <li class="nav-item" id="alarm_container">
                            <i class="fa-solid fa-bell" id="alarm_bell"></i>
                            <div id="alarm_list">
                                <ul>
                                    <% if (member.getMem_alarm() != null) {
                                        String alarm = member.getMem_alarm();
                                        String[] alarms = alarm.split(",");
                                        if (!(alarm.equals(alarms[0]))) {
                                            for (int i = 1; i < alarms.length; i++) { %>
                                                <hr>
                                                <li>
                                                    <a href="/project_403/member/alarm/delete?index=<%= i %>"><%= alarms[i] %></a>
                                                </li>
                                                <hr>
                                            <% }
                                        } else { %>
                                            <li>
                                                <p>알림이 없습니다.</p>
                                            </li>
                                        <% }
                                    } %>
                                </ul>
                            </div>
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
                            <p id="mem_serial"><%=member.getMem_serial()%></p>
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
    let alarm_list = document.querySelector("#alarm_list");
    dropdown.addEventListener("click", downmenu);
    function downmenu() {
        alarm_list.style.display = "none";
        if (menu.style.display != "flex") {
            menu.style.display = "flex";
        } else {
            menu.style.display = "none";
        }
    }

    let alarm = document.querySelector("#alarm_bell");
    alarm.addEventListener("click", alarm_menu);
    function alarm_menu() {
        menu.style.display = "none";
        if (alarm_list.style.display != "block") {
            alarm_list.style.display = "block";
        } else {
            alarm_list.style.display = "none";
        }
    }
</script>
</body>
</html>

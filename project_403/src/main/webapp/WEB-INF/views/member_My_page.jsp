<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member"%>
<%@ page import="com.spring.domain.Member_Item" %>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>MyPage</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa; /* 밝은 회색 배경 */
            color: #343a40; /* 어두운 텍스트 */
        }
        nav, footer {
            background-color: #213555; /* 네비게이션과 푸터 색상 유지 */
        }
        nav a, footer a {
            color: #ffffff !important; /* 링크 텍스트 흰색 */
        }
        .mypage-container {
            margin-top: 20px;
            display: flex;
            gap: 20px;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .welcome-message img {
            border-radius: 50%;
        }
        .inner-card {
            padding: 20px;
            background-color: #ffffff; /* 카드 배경 */
        }
        .btn-custom {
            width: 200px;
            margin:50px 0;
           
        }
        .btn-primary {
            background-color: #007bff; /* 기본 블루 */
            border-color: #007bff;
        }
        .btn-danger {
            background-color: #dc3545; /* 기본 레드 */
            border-color: #dc3545;
        }
        .btn-warning {
            color: #212529;
            background-color: #ffc107; /* 기본 옐로우 */
            border-color: #ffc107;
        }
        .member_inf{
        	border:1px solid gray;
        	border-radius:20px;
        	text-align:center;
      	}
      	
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/member_home.jsp" %>
<% if (member != null) { 
    Member_Item mi = (Member_Item) session.getAttribute("member_item"); 
    String profile = member.getMem_profile_name();
%>
<div class="container mypage-container">
    <!-- Left Navigation Section -->
    <div class="card p-3" style="background-color: #213555; color: white;">
        <h5 class="text-center">Navigation</h5>
        <div class="d-flex flex-column align-items-center">
            <!-- Update Info -->
            <form action="/project_403/member/update?mem_id=<%= member.getMem_id() %>" method="post">
                <button type="submit" class="btn btn-primary btn-custom "><i class="fas fa-user-edit"></i> 정보 수정</button>
            </form>
            <!-- Items -->
            <form action="/project_403/member/item?mem_id=<%= member.getMem_id() %>" method="post">
                <button type="submit" class="btn btn-primary btn-custom "><i class="fas fa-box"></i> 아이템</button>
            </form>
            <!-- Notes -->
            <form action="/project_403/fnote/notelist?mem_id=<%= member.getMem_id() %>" method="post">
                <button type="submit" class="btn btn-primary btn-custom "><i class="fas fa-sticky-note"></i> 정리노트</button>
            </form>
            <!-- Delete Account -->
            <form action="/project_403/member/delete?mem_id=<%= member.getMem_id() %>" method="post">
                <button type="submit" class="btn btn-danger btn-custom"><i class="fas fa-user-minus"></i> 회원 탈퇴</button>
            </form>
        </div>
    </div>

    <!-- Right Info Section -->
    <div class="card p-3 flex-fill">
        <!-- Welcome Section -->
        <div class="welcome-message inner-card text-center">
            <% if (profile != null && profile.startsWith("http")) { %>
                <img src="<%= profile %>" width="150" height="150" alt="Profile Image">
            <% } else { %>
                <img src="/project_403/resources/images/<%= profile %>" width="150" height="150" alt="Profile Image">
            <% } %>
            <h4><b class="<%= mi.getMem_color() %>"><%= member.getMem_nickName() %></b>님, 환영합니다!</h4>
        </div>
        <!-- Member Info Section -->
        <div class="member_inf inner-card mt-4">
            <h5>현재 정보</h5>
            <% if (!(member.getMem_id().startsWith("kakao_") || member.getMem_id().startsWith("naver_"))) { %>
                <p>아이디: <span id="id"><%= member.getMem_id() %></span></p>
            <% } %>
            <p>닉네임: <span class="<%= mi.getMem_color() %>"><%= member.getMem_nickName() %></span></p>
            <p>경험치: <%= member.getMem_exp() %></p>
            <p>포인트: <%= member.getMem_point() %></p>
            <p>마지막 접속일: <%= member.getMem_date() %></p>
            <p>이메일: <span id="email"><%= member.getMem_email() %></span></p>
            <p>이메일 인증여부: 
                <% if (!member.isMem_confirmed()) { %>
                    X <button type="button" class="btn btn-warning btn-sm" id="mailbut">이메일 인증하기</button>
                <% } else { %>
                    인증 완료
                <% } %>
            </p>
                <a href="/project_403/member/alarm" class="btn btn-outline-primary btn-sm mt-2">알림생성</a>
            
        </div>
    </div>
</div>
<% } else { %>
<div class="text-center mt-5">
    <h3>로그인 후에 이용해주세요!</h3>
</div>
<% } %>
<%@ include file="/WEB-INF/views/footer.jsp" %>

<script>
    document.querySelector("#mailbut").addEventListener("click", () => {
        const user_mail = document.querySelector("#email").innerText;
        const user_id = document.querySelector("#id").innerText;
        $.ajax({
            url: "email",
            type: "post",
            data: JSON.stringify({ "user_mail": user_mail, "user_id": user_id }),
            contentType: "application/json; charset=UTF-8",
            success: () => alert("메일 전송 완료!"),
            error: () => alert("메일 전송 실패!"),
        });
    });
</script>
</body>
</html>

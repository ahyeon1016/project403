<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member"%>
<%@ page import="com.spring.domain.Member_Item" %>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>MyPage</title>
    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script> <%--아작스 사용을 위한 코드 --%>
    
    <style>
        .mypage-container {
            height: 800px;
            display: flex;
            justify-content: space-around;
        }

        .card {
            border-radius: 20px;
        }

        .welcome-message {
            box-shadow: 1px 1px 1px gray;
            height: 300px;
            width: 500px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: center;
        }

        .btn-custom {
            width: 200px;
            margin: 65px ;
        }

        .right-card {
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: center;
        }

        .inner-card {
            box-shadow: 1px 1px 1px 1px gray;
            background-color: #f5f5f5;
            border: 1px solid gray;
            width: 700px;
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <%
    	
        if (member != null) {
        	Member_Item mi=(Member_Item)session.getAttribute("member_item");
            String profile = member.getMem_profile_name();
    %>
    <div class="container mypage-container">
        <!-- Navigation Box -->
        <div class="card mt-4">
            <div class="navigation-buttons d-flex flex-column align-items-center">
                <!-- 정보 수정 -->
                <form action="/project_403/member/update?mem_id=<%= member.getMem_id() %>" method="post">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-user-edit"></i> 정보 수정
                    </button>
                </form>

                <!-- 아이템 -->
                <form action="/project_403/member/item?mem_id=${member.getMem_id()}" method="post">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-box"></i> 아이템
                    </button>
                </form>

                <!-- 정리노트 -->
                <form action="/project_403/fnote/notelist?mem_id=${member.getMem_id()}" method="post">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-sticky-note"></i> 정리노트
                    </button>
                </form>

                <!-- 회원 탈퇴 -->
                <form action="/project_403/member/delete?mem_id=${member.getMem_id()}" method="post">
                    <button type="submit" class="btn btn-danger btn-custom">
                        <i class="fas fa-user-minus"></i> 회원 탈퇴
                    </button>
                </form>
            </div>
        </div>
		
        <!-- Welcome Message -->
        <div class="card right-card">
            <div class="welcome-message inner-card">
                <% if (profile != null && profile.startsWith("http")) { %>
                    <img src="<%= profile %>" width="200px" height="200px" alt="Profile Image">
                <% } else { %>
                    <img src="/project_403/resources/images/<%= profile %>" width="100" height="100" alt="Profile Image">
                <% } %>
                <h2><b class="<%=mi.getMem_color()%>"><%= member.getMem_nickName() %></b>님, 환영합니다!</h2>
            </div>
            <div class="member_inf inner-card p-3">
                <h3>현재 정보</h3>
                <% if (!(member.getMem_id().startsWith("kakao_") || member.getMem_id().startsWith("naver_"))) { %>
                    <p>아이디 : <span id="id"><%= member.getMem_id() %></span></p>
                <% } %>
                <p>닉네임 : <span class="<%=mi.getMem_color()%>"><%= member.getMem_nickName() %></span></p>
                <p>경험치 : <%= member.getMem_exp() %></p>
                <p>포인트 : <%= member.getMem_point() %></p>
                <p>마지막 접속일 : <%= member.getMem_date() %></p>
                <p>이메일 : <span id="email"><%= member.getMem_email() %></span></p>
                <p>이메일 인증여부 : <% if (member.isMem_confirmed() != true) { %>X
                    <button type="button" class="btn btn-warning" id="mailbut">이메일 인증하기</button>
                <% } else { %>인증 완료<% } %></p>
                <a href="/project_403/member/alarm">알림생성</a>
            </div>
        </div>
    </div>
    <% } else { %>
        <h1>로그인 후에 이용해주세요!</h1>
    <% } %>
    <%@ include file="/WEB-INF/views/footer.jsp" %>

    <script>
        document.querySelector("#mailbut").addEventListener("click", confirmm);
        function confirmm() {
            let user_mail = document.querySelector("#email").innerText;
            let user_id = document.querySelector("#id").innerText;
            $.ajax({
                url: "email",
                type: "post",
                data: JSON.stringify({ "user_mail": user_mail, "user_id": user_id }),
                contentType: "application/json; charset=UTF-8",
                success: function(data) {
                    alert("메일 전송 완료!");
                },
                error: function(errorThrown) {
                    alert("fail");
                }
            });
        }
        
    </script>
</body>
</html>

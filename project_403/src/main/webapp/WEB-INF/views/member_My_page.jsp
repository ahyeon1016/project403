<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.spring.domain.Member" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>MyPage</title>
    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .welcome-message {
            text-align: center;
            margin: 30px 0;
        }
        .welcome-message img {
            border-radius: 50%;
            margin-bottom: 15px;
        }
        .navigation-box {
            display: flex;
            flex-direction: column;
            align-items: center; /* 버튼을 가운데로 정렬 */
            padding: 20px;
            background: #f8f9fa;
            border: 1px solid #ccc;
            border-radius: 10px;
            margin-top: 20px;
        }
        .navigation-box form, .navigation-box a {
            width: 80%; /* 버튼 너비 조정 */
            margin-bottom: 15px;
        }
        .btn-custom {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 5px 10px; /* 버튼 크기 조정 */
            border-radius: 5px;
            font-size: 14px; /* 글자 크기 조정 */
            cursor: pointer;
            transition: background 0.3s ease;
            text-align: center;
            width: 100%;
        }
        .btn-primary, .btn-danger, .btn-outline-secondary, .btn-outline-info {
            margin-bottom: 10px;
        }
        .welcome-message i {
            font-size: 24px; /* 아이콘 크기 조정 */
            width: 30px; /* 너비 조정 */
            height: 30px; /* 높이 조정 */
        }
    </style>
</head>
<body>

    <%@include file="/WEB-INF/views/member_home.jsp" %>

    <% 
        String profile = member.getMem_profile_name();
    %>
    <div class="container">
        <div class="mypage-container">
            <div class="welcome-message">
                <h2>
                    <i class="fas fa-user-circle"></i>
                <% if (profile != null && profile.startsWith("http")) { %>
                    <img src="<%= profile %>" width="100px" height="100px">
                <% } else { %>
                    <img src="/project_403/resources/images/<%= profile %>" width="100px" height="100px">
                <% } %>
                <%= member.getMem_nickName() %>님 환영합니다
                </h2>
            </div>

            <div class="navigation-box">
                <form action="/project_403/member/me?mem_id=${member.getMem_id()}" method="post">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-user-edit me-2"></i>정보 조회
                    </button>
                </form>

                <form action="/project_403/member/item?mem_id=${member.getMem_id()}" method="post">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-user-edit me-2"></i>아이템
                    </button>
                </form>

                <form action="/project_403/fnote/notelist?mem_id=${member.getMem_id()}" method="post">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-user-edit me-2"></i>정리노트
                    </button>
                </form>

                <form action="/project_403/member/delete?mem_id=${member.getMem_id()}" method="post">
                    <button type="submit" class="btn btn-danger btn-custom">
                        <i class="fas fa-user-minus me-2"></i>회원 탈퇴
                    </button>
                </form>

                <a href="/project_403/member/logout" class="btn btn-outline-secondary btn-custom">
                    <i class="fas fa-sign-out-alt me-2"></i>로그아웃
                </a>
                <a href="/project_403/member" class="btn btn-outline-info btn-custom">
                    <i class="fas fa-home me-2"></i>홈으로
                </a>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/footer.jsp" %>    

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

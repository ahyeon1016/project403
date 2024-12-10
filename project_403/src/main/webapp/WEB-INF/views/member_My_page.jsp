<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>MyPage</title>
 
</head>
<body>
    <div class="container">
        <div class="mypage-container">
            <div class="welcome-message">
                <h2>
                    <i class="fas fa-user-circle me-2"></i>
                    ${member.getMem_nickName()}님 환영합니다
                </h2>
            </div>

            <div class="d-flex flex-column align-items-center">
                <form action="member?mem_id=${member.getMem_id()}" method="post" class="mb-3">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-user-edit me-2"></i>정보 조회
                    </button>
                </form>

                <form action="delete?mem_id=${member.getMem_id()}" method="post" class="mb-3">
                    <button type="submit" class="btn btn-danger btn-custom">
                        <i class="fas fa-user-minus me-2"></i>회원 탈퇴
                    </button>
                </form>

                <div class="d-flex justify-content-center">
                    <a href="logout" class="btn btn-outline-secondary btn-custom me-2">
                        <i class="fas fa-sign-out-alt me-2"></i>로그아웃
                    </a><br>
                    <a href="/project_403/" class="btn btn-outline-info btn-custom">
                        <i class="fas fa-home me-2"></i>홈으로
                    </a>
                </div>
            </div>
        </div>
    </div>

    
</body>
</html>
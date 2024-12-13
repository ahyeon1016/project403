<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>MyPage</title>
 	<style>
 		img{
 		border-radius:100px;
 		
 		}
 	
 	</style>
</head>
<body>
	<% Member member=(Member)session.getAttribute("member");
		String profile=member.getMem_profile_name();
	%>
    <div class="container">
        <div class="mypage-container">
            <div class="welcome-message">
                <h2>
                     <i class="fas fa-user-circle me-2"></i>
                <% if (profile != null && profile.startsWith("http")) { %>
                    <img src="<%= profile %>" width="100px" height="100px">
                <% } else { %>
                    <img src="/project_403/resources/images/<%= profile %>" width="100px" height="100px">
                <% } %>
                ${member.getMem_nickName()}님 환영합니다
                </h2>
           </div>

            <div class="d-flex flex-column align-items-center">
                <form action="/project_403/member/me?mem_id=${member.getMem_id()}" method="post" class="mb-3">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="fas fa-user-edit me-2"></i>정보 조회
                    </button>
                </form>

                <form action="/project_403/member/delete?mem_id=${member.getMem_id()}" method="post" class="mb-3">
                    <button type="submit" class="btn btn-danger btn-custom">
                        <i class="fas fa-user-minus me-2"></i>회원 탈퇴
                    </button>
                </form>

                <div class="d-flex justify-content-center">
                    <a href="/project_403/member/logout" class="btn btn-outline-secondary btn-custom me-2">
                        <i class="fas fa-sign-out-alt me-2"></i>로그아웃
                    </a><br>
                    <a href="/project_403/member" class="btn btn-outline-info btn-custom">
                        <i class="fas fa-home me-2"></i>홈으로
                    </a>
                </div>
            </div>
        </div>
    </div>

    
</body>
</html>
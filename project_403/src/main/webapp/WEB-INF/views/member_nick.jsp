<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>닉네임 변경 페이지</title>
    
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <div class="container form-container my-5">
        <h2 class="text-center">닉네임 변경</h2>
        
        <form action="nick/change?mem_id=<%= member.getMem_id() %>" method="post" id="form">
            <div class="form-group">
                <label for="nick">닉네임 <span class="text-danger">*</span></label>
                <input type="text" class="form-control" id="nick" name="nick" value="<%= member.getMem_nickName() %>" maxlength="10" pattern="^[가-힣a-zA-Z0-9]+$" required>
                <small class="form-text text-muted">변경할 닉네임을 써주세요.</small>
            </div>
            <button type="submit" class="btn btn-primary btn-custom">변경하기</button>
            <a href="/member/mypage" class="btn btn-secondary btn-custom">취소하기</a>
        </form>
    </div>

    <script>
        document.querySelector("#form").addEventListener("submit", function() {
            alert("변경되었습니다!");
        });
    </script>
    <%@include file="/WEB-INF/views/footer.jsp" %>
    
</body>
</html>

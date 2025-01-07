<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 실패</title>

    <style>
        .fail_container {
        	width: 50%;
            margin: 50px auto;
        }
        .alert {
            margin-bottom: 20px;
        }
        .btn {
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp" %>

    <div class="fail_container text-center">
        <div class="alert alert-danger" role="alert">
            <h4 class="alert-heading">로그인에 실패하셨습니다.</h4>
            <p>계정을 다시 확인해 주세요.</p>
            <hr>
            <a href="/" class="btn btn-primary">뒤로가기</a>
        </div>
    </div>

    <%@include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>

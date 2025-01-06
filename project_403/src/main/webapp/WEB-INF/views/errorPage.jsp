<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>에러 페이지</title>

    <style>
        .error_container {
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

    <div class="error_container text-center">
        <div class="alert alert-danger" role="alert">
            <h4 class="alert-heading">에러가 발생했습니다.</h4>
            <p>홈으로 이동해주세요.</p>
            <hr>
            <a href="/project_403/" class="btn btn-primary">홈으로 이동</a>
        </div>
    </div>

    <%@include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
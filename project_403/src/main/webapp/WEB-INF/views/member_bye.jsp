<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>탈퇴 페이지</title>
    <!-- Bootstrap CSS CDN -->
    <style>
        .container {
            margin-top: 50px;
        }
        .button-group {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px; /* 버튼 간의 간격 */
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <div class="container text-center">
        <form action="delete_bye" method="post" id="form">
            <div class="alert alert-warning" role="alert">
                <p><strong>${mem_id}님</strong>, 정말로 탈퇴하시겠습니까?</p>
                <p>탈퇴를 하시려면 버튼을 눌러주세요.</p>
            </div>
            <div class="button-group">
                <button type="submit" class="btn btn-danger" id="but">탈퇴하기</button>
                <a href="/project_403/member/mypage" class="btn btn-secondary">취소하기</a>
            </div>
        </form>
    </div>

    <script>
        document.querySelector("#but").addEventListener("click", del);
        let form = document.querySelector("#form");
        function del(event) {
            event.preventDefault();
            let bye = window.confirm("정말로 탈퇴하시겠습니까?");
            if (bye) {
                alert("탈퇴되었습니다.");
                form.submit();
            }
        }
    </script>
</body>
</html>

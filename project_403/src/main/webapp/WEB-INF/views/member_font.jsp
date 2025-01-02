<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member"%>
<%@ page import="com.spring.domain.Member_Item"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>닉네임 색상 변경 페이지</title>
    <style>
        .container {
            margin-top: 50px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .form-container h2 {
            margin-bottom: 20px;
        }
        .form-group label {
            font-weight: bold;
        }
        .btn-custom {
            padding: 10px 20px;
            margin-top: 10px;
        }
        #nick_name {
            display: block;
            margin: 20px 0;
            font-size: 1.5em;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <%
        Member_Item mi = (Member_Item) request.getAttribute("member_item");
    %>

    <div class="container form-container my-5">
        <h2 class="text-center">닉네임 색상 변경</h2>

        <span id="nick_name" class="<%= mi.getMem_color() %>"><%= member.getMem_nickName() %></span>
        
        <form action="./font/change?mem_id=<%= member.getMem_id() %>" method="post" id="form" class="mt-4">
            <div class="form-group">
                <label for="colors">색상 선택 <span class="text-danger">*</span></label>
                <select id="colors" name="color" class="form-control" required>
                    <option value="text-danger">빨간색</option>
                    <option value="text-primary">파란색</option>
                    <option value="text-warning">주황색</option>
                    <option value="text-success">초록색</option>
                </select>
                <p>*재접속시 변경됩니다!</p>
            </div>
            <button type="submit" id="sub" class="btn btn-primary btn-custom">변경하기</button>
            <button type="button" id="back" onclick="history.back();" class="btn btn-secondary btn-custom">뒤로가기</button>
        </form>
    </div>

    <script>
        let colors = document.querySelector("#colors");
        colors.addEventListener("change", color_change);
        let nick = document.querySelector("#nick_name");
        function color_change() {
            let color = colors.value;
            nick.className = "";
            nick.classList.add(color);
        }
        document.querySelector("#sub").addEventListener("click", submit);
        function submit(e) {
            e.preventDefault();
            if (confirm("변경하시겠습니까?")) {
                alert("닉네임 색상이 변경되었습니다.");
                document.querySelector("#form").submit();
            }
        }
    </script>
    <%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>

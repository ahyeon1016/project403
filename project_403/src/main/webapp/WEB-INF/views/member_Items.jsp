<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member_Item"%>
<%@ page import="com.spring.domain.Member"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이템 관리</title>
    <style>
    .list-group-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: relative;
        padding: 10px 15px; /* 여백을 설정 */
    }

    .list_span {
        flex: 1; /* 가변적인 공간 차지 */
        max-width: 250px; /* 길이 제한 */
        word-break: break-word; /* 긴 텍스트 줄바꿈 처리 */
    }

    .badge {
        margin-left: 10px; /* 배지와 다른 요소 간격 */
        display: inline-flex; /* 텍스트 및 정렬 개선 */
        justify-content: center;
        align-items: center;
        padding: 5px 10px; /* 배지 크기 조정 */
        font-size: 14px; /* 텍스트 크기 조정 */
        height: auto; /* 높이 자동 조정 */
    }

    form {
        margin: 0;
        display: flex;
        align-items: center; /* 버튼이 배지와 같은 라인에 정렬 */
    }

    button {
        margin-left: 10px; /* 버튼과 배지 사이 간격 */
        padding: 5px 10px; /* 버튼 크기 조정 */
    }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <%
       
        if (member != null) {
            Member_Item mi = (Member_Item) request.getAttribute("mem_item");
            String mem_color = mi.getMem_color();
    %>

    <div class="container my-5">
        <div class="text-center mb-4">
            <h1><b id="mem_id" class="<%= mem_color %>"><%= member.getMem_nickName() %></b>님의 아이템</h1>
        </div>
        <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <span class="list_span">닉네임 변경권:</span>
                <% if (mi.isMem_itemA()) { %>
                    <span class="badge badge-success">보유</span>
                    <form action="item/nick?mem_id=<%= mi.getMem_id() %>" method="post" id="nick" class="ml-3">
                        <button type="button" class="btn btn-primary btn-sm" id="a_use">사용하기</button>
                    </form>
                <% } else { %>
                    <span class="badge badge-secondary">없음</span>
                    <form action="item/purchase_nick?mem_id=<%=mi.getMem_id() %>" method="post" id="purchase_nick">
                        <button type="button" class="btn btn-primary btn-sm" id="nick_buy">구매하기</button>
                    </form>
                <% } %>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <span class="list_span">닉네임 색상 변경권:</span>
                <% if (mi.isMem_itemB()) { %>
                    <span class="badge badge-success">보유</span>
                    <form action="item/font?mem_id=<%= mi.getMem_id() %>" method="post" id="font" class="ml-3">
                        <button type="button" class="btn btn-primary btn-sm" id="b_use">사용하기</button>
                    </form>
                <% } else { %>
                    <span class="badge badge-secondary">없음</span>
                    <form action="item/purchase_font?mem_id=<%=mi.getMem_id()%>" method="post" id="purchase_font">
                        <button type="button" class="btn btn-primary btn-sm" id="color_buy">구매하기</button>
                    </form>
                <% } %>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
                현재 폰트색상:
                <% if (mem_color != null) { %>
                    <% if (mem_color.equals("text-danger")) { %><b class="text-danger">빨간색</b><% } %>
                    <% if (mem_color.equals("text-primary")) { %><b class="text-primary">파란색</b><% } %>
                    <% if (mem_color.equals("text-success")) { %><b class="text-success">초록색</b><% } %>
                    <% if (mem_color.equals("text-warning")) { %><b class="text-warning">주황색</b><% } %>
                <% } %>
            </li>
        </ul>
    </div>

    <script>
        //닉네임 변경
        let mem_id = "<%= mi.getMem_id() %>";
        console.log(mem_id);
        let a_button = document.querySelector("#a_use");
        if (a_button) {
            a_button.addEventListener("click", use_a);
            function use_a() {
                if (confirm("닉네임을 변경하시겠습니까?")) {
                    document.querySelector("#nick").submit();
                }
            }
        }
        //닉네임 색상 변경
        let b_button = document.querySelector("#b_use");
        if (b_button) {
            b_button.addEventListener("click", use_b);
            function use_b() {
                if (confirm("닉네임 색상을 변경하시겠습니까?")) {
                    document.querySelector("#font").submit();
                }
            }
        }
		//닉변권 구매
        let nick_buy = document.querySelector("#nick_buy");
        if (nick_buy) {
            nick_buy.addEventListener("click", nick_button);
            function nick_button(event) {
                event.preventDefault();
                if (confirm("구매시 50포인트가 소모됩니다.")) {
                    if (<%= member.getMem_point() %> >= 100) {
                        alert("구매되었습니다.");
                        document.querySelector("#purchase_nick").submit();
                    } else {
                        alert("포인트가 부족합니다!");
                    }
                }
            }
        }
		//닉네임 색상 변경권 구매
        let color_buy = document.querySelector("#color_buy");
        if (color_buy) {
            color_buy.addEventListener("click", color_button);
            function color_button(event) {
                event.preventDefault();
                if (confirm("구매시 100포인트가 소모됩니다.")) {
                    if (<%= member.getMem_point() %> >= 100) {
                        alert("구매되었습니다.");
                        document.querySelector("#purchase_font").submit();
                    } else {
                        alert("포인트가 부족합니다!");
                    }
                }
            }
        }
    </script>
    <% } else { %>
        <h1>로그인해주세요!</h1>
    <% } %>
    <%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>

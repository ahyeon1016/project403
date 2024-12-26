<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member_Item"%>
<%@ page import="com.spring.domain.Member"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이템 관리</title>
    </head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>
	
	
    <%
    	if(member!=null){
        Member_Item mi = (Member_Item)request.getAttribute("mem_item");
        String mem_color = mi.getMem_color();
    %>

    <div class="container my-5">
        <div class="text-center mb-4">
            <h1><b id="mem_id" class="<%= mem_color %>"><%= member.getMem_nickName() %></b>님의 아이템</h1>
        </div>
        <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center">
                닉네임 변경권:
                <% if (mi.isMem_itemA()) { %>
                    <span class="badge badge-success">보유</span>
                    <form action="item/nick?mem_id=<%= mi.getMem_id() %>" method="post" id="nick" class="ml-3">
                        <button type="button" class="btn btn-primary btn-sm" id="a_use">사용하기</button>
                    </form>
                <% } else { %>
                    <span class="badge badge-secondary">없음</span>
                <% } %>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
                닉네임 색상 변경권:
                <% if (mi.isMem_itemB()) { %>
                    <span class="badge badge-success">보유</span>
                    <form action="item/font?mem_id=<%= mi.getMem_id() %>" method="post" id="font" class="ml-3">
                        <button type="button" class="btn btn-primary btn-sm" id="b_use">사용하기</button>
                    </form>
                <% } else { %>
                    <span class="badge badge-secondary">없음</span>
                <% } %>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
                현재 폰트색상:
                <% if (mem_color.equals("text-danger")) { %><b class="text-danger">빨간색</b><% } %>
                <% if (mem_color.equals("text-primary")) { %><b class="text-primary">파란색</b><% } %>
                <% if (mem_color.equals("text-success")) { %><b class="text-success">초록색</b><% } %>
                <% if (mem_color.equals("text-warning")) { %><b class="text-warning">주황색</b><% } %>
            </li>
        </ul>
    </div>

    <script>
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
        let b_button = document.querySelector("#b_use");
        if (b_button) {
            b_button.addEventListener("click", use_b);
            function use_b() {
                if (confirm("닉네임 색상을 변경하시겠습니까?")) {
                    document.querySelector("#font").submit();
                }
            }
        }
    </script>
    <%}else{ %>
    	<h1>로그인해주세요!</h1>
    <%} %>
    <%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>

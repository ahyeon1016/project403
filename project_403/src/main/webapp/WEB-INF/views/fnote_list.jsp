<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.spring.domain.Test"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>시험 기록</title>
    <!-- Bootstrap CSS CDN -->
    <style>
        .container {
            margin-top: 50px;
        }
        .test-list {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <% ArrayList<Test> test_list = (ArrayList<Test>) request.getAttribute("testlist"); %>

    <div class="container">
        <h2 class="text-center">시험 기록</h2>
        <ul class="list-group test-list">
            <%
                if (test_list != null && test_list.size() != 0) {
                    for (int i = 0; i < test_list.size(); i++) {
            %>
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <form action="mynote?testnum=<%= test_list.get(i).getTest_num() %>" method="post">
                    <button type="submit" class="btn btn-link"><%= test_list.get(i).getTest_name() %></button>
                </form>
            </li>
            <%
                    }
                } else {
            %>
            <li class="list-group-item text-center">시험 기록이 존재하지 않습니다.</li>
            <%
                }
            %>
        </ul>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.spring.domain.Test"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>시험 기록</title>
    <style>
        .container_test {
            padding-top: 50px;
            text-align: center; /* 텍스트 가운데 정렬 */
        }
        .test-list {
            padding-top: 20px;
        }
        .list-group-item {
            display: flex;
            justify-content: center; /* 리스트 아이템 가운데 정렬 */
            align-items: center;
        }
        .btn-link {
            font-size: 1.2em;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <% ArrayList<Test> test_list = (ArrayList<Test>) request.getAttribute("testlist"); %>

    <div class="container_test">
        <h2 class="text-center">시험 기록</h2>
        <ul class="list-group test-list">
            <%
                if (test_list != null && test_list.size() != 0) {
                    for (int i = 0; i < test_list.size(); i++) {
            %>
            <li class="list-group-item">
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
    <%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>

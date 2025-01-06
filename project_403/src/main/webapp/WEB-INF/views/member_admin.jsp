<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="com.spring.domain.Member"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 조회</title>
    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
       
        .table th, .table td {
            text-align: center;
            vertical-align: middle;
            white-space: nowrap; /* 텍스트 줄바꿈 방지 */
        }
        .table td {
            max-width: 250px; /* 최대 너비를 지정 */
            overflow: hidden;
            text-overflow: ellipsis; /* 긴 텍스트 말줄임 표시 */
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination li {
            margin: 0 5px;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <%
        if (member != null && member.isMem_admin()) {
    %>
    <div class="container">
        <h1 class="text-center">회원 조회</h1>
        <table class="table table-striped">
            <thead class="thead">
                <tr>
                    <th>회원 아이디</th>
                    <th>회원 닉네임</th>
                    <th>회원 포인트</th>
                    <th>회원 등급포인트</th>
                    <th>회원 이메일</th>
                    <th>회원 마지막 접속일</th>
                    <th>회원 고유 번호</th>
                    <th>회원 이메일 등록 여부</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Member> al = (ArrayList<Member>) request.getAttribute("al");
                    int mem_num = (int) request.getAttribute("mem_num");
                    for (int i = 0; i < al.size(); i++) {
                        Member members = (Member) al.get(i);
                %>
                <tr>
                    <td><%= members.getMem_id() %></td>
                    <td><%= members.getMem_nickName() %></td>
                    <td><%= members.getMem_point() %></td>
                    <td><%= members.getMem_exp() %></td>
                    <td><%= members.getMem_email() %></td>
                    <td><%= members.getMem_date() %></td>
                    <td><%= members.getMem_serial() %></td>
                    <td><%= members.isMem_confirmed() ? "예" : "아니오" %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <%
                    int totalPages = (mem_num / 10) + 1;
                    for (int j = 1; j <= totalPages; j++) {
                %>
                <li class="page-item">
                    <form action="./admin?page=<%= j %>" method="post">
                        <button type="submit" class="btn btn-link page-link"><%= j %></button>
                    </form>
                </li>
                <% } %>
            </ul>
        </nav>
        <a href="../" class="btn btn-primary">홈</a>
    </div>
    <% } %>
   <%@include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>

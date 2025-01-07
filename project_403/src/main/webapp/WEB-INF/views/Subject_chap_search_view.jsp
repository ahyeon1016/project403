<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Subject" %>
<%
    Subject sub_chap = (Subject) request.getAttribute("subByChap");
    Subject inputSub = (Subject) request.getAttribute("inputSub");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챕터 검색</title>
    <style>
        .chap-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .chap-title {
            color: #2c3e50;
            font-size: 24px;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #3498db;
        }

        .chap-info {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #e1e1e1;
            border-radius: 4px;
            background-color: #f8f9fa;
        }

        .chap-subtitle {
            color: #2c3e50;
            font-size: 18px;
            margin-bottom: 15px;
        }

        .chap-item {
            margin-bottom: 10px;
            padding: 5px 0;
            border-bottom: 1px solid #eee;
        }

        .chap-label {
            font-weight: bold;
            color: #34495e;
            margin-right: 10px;
        }

        .chap-value {
            color: #2c3e50;
        }

        .chap-empty {
            text-align: center;
            padding: 20px;
            color: #7f8c8d;
            background-color: #f8f9fa;
            border-radius: 4px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
    <div class="chap-container">
        <h1 class="chap-title">챕터 검색 결과</h1>
        <% if(sub_chap.getSub_name() != null) { %>
            <div class="chap-info">
                <h3 class="chap-subtitle">과목 정보</h3>
                <div class="chap-item">
                    <span class="chap-label">과목 이름:</span>
                    <span class="chap-value"><%=sub_chap.getSub_name()%></span>
                </div>
                <% if(sub_chap.getSub_chap() != null) { %>
                    <div class="chap-item">
                        <span class="chap-label">과목 코드:</span>
                        <span class="chap-value"><%=sub_chap.getSub_name_code()%></span>
                    </div>
                    <div class="chap-item">
                        <span class="chap-label">챕터 이름:</span>
                        <span class="chap-value"><%=sub_chap.getSub_chap()%></span>
                    </div>
                    <div class="chap-item">
                        <span class="chap-label">챕터 코드:</span>
                        <span class="chap-value"><%=sub_chap.getSub_chap_code()%></span>
                    </div>
                    <div class="chap-item">
                        <span class="chap-label">과목 번호:</span>
                        <span class="chap-value"><%=sub_chap.getSub_num()%></span>
                    </div>
                <% } else { %>
                    <div class="chap-empty">
                        <h3><%=inputSub.getSub_chap()%> 챕터가 존재하지 않습니다.</h3>
                    </div>
                <% } %>
            </div>
        <% } else { %>
            <div class="chap-empty">
                <h3><%=inputSub.getSub_name()%> 과목이 존재하지 않습니다.</h3>
            </div>
        <% } %>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
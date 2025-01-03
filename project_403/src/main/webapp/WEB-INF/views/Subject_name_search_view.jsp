<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.spring.domain.Subject" %>
<%
    ArrayList<Subject> sub_name_arr = (ArrayList<Subject>) request.getAttribute("sub_name_arr");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>과목명 검색 결과</title>
    <style>
        .search-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .search-title {
            color: #2c3e50;
            font-size: 24px;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #3498db;
        }

        .search-result {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #e1e1e1;
            border-radius: 4px;
            background-color: #f8f9fa;
        }

        .search-subtitle {
            color: #2c3e50;
            font-size: 18px;
            margin-bottom: 15px;
        }

        .search-item {
            margin-bottom: 10px;
            padding: 5px 0;
            border-bottom: 1px solid #eee;
        }

        .search-label {
            font-weight: bold;
            color: #34495e;
            margin-right: 10px;
        }

        .search-value {
            color: #2c3e50;
        }

        .search-empty {
            text-align: center;
            padding: 20px;
            color: #7f8c8d;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
    </style>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
    <div class="search-container">
        <h1 class="search-title">과목명 검색 결과</h1>
        <% if(sub_name_arr.size() != 0) {
            for(Subject sub : sub_name_arr) { %>
                <div class="search-result">
                    <h3 class="search-subtitle">과목 정보</h3>
                    <div class="search-item">
                        <span class="search-label">과목 이름:</span>
                        <span class="search-value"><%=sub.getSub_name()%></span>
                    </div>
                    <div class="search-item">
                        <span class="search-label">과목 코드:</span>
                        <span class="search-value"><%=sub.getSub_name_code()%></span>
                    </div>
                    <div class="search-item">
                        <span class="search-label">챕터 이름:</span>
                        <span class="search-value"><%=sub.getSub_chap()%></span>
                    </div>
                    <div class="search-item">
                        <span class="search-label">챕터 코드:</span>
                        <span class="search-value"><%=sub.getSub_chap_code()%></span>
                    </div>
                    <div class="search-item">
                        <span class="search-label">과목 번호:</span>
                        <span class="search-value"><%=sub.getSub_num()%></span>
                    </div>
                </div>
        <%  }
        } else { %>
            <div class="search-empty">
                <p>Subject에 데이터가 없습니다.</p>
            </div>
        <% } %>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
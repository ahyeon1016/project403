<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.spring.domain.Question"%>
<%@ page import="com.spring.domain.Subject"%>
<%
    ArrayList<Subject> sub_all_name = (ArrayList<Subject>) request.getAttribute("sub_all_name");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문제 목록</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>

        body {
            background-color: #f8f9fa;
            color: #333;
        }

        .main-container {
            display: flex;
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
            gap: 2rem;
        }

        .question_aside {
            width: 250px;
        }

        .question_aside ul {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            position: sticky;
            top: 2rem;
        }

        .question_aside li {
            list-style: none;
            margin: 0.75rem 0;
        }

        .question_aside a {
            display: block;
            padding: 0.75rem 1rem;
            color: #333;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.2s ease;
        }

        .question_aside a:hover {
            background-color: #f0f0f0;
            color: #2563eb;
        }

        .sidebar-menu {
            list-style: none;
        }

        .sidebar-menu a {
            display: block;
            padding: 0.75rem 1rem;
            color: #495057;
            text-decoration: none;
            border-radius: 4px;
            margin-bottom: 0.5rem;
        }

        .sidebar-menu a:hover {
            background-color: #f8f9fa;
            color: #228be6;
        }

        .content {
            flex: 1;
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            padding: 2rem;
        }

        .filter-section {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 2rem;
        }

        .filter-row {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-bottom: 1rem;
        }

        .filter-row select {
            padding: 0.5rem 1rem;
            border: 1px solid #dee2e6;
            border-radius: 4px;
            background: white;
            min-width: 120px;
        }

        .radio-group {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .radio-group label {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: #495057;
            cursor: pointer;
        }

        .question-item {
            padding: 1.5rem;
            border-bottom: 1px solid #dee2e6;
        }

        .question-item:last-child {
            border-bottom: none;
        }

        .question-meta {
            margin-bottom: 1rem;
            color: #868e96;
            font-size: 0.9rem;
        }

        .question-actions {
            margin-top: 1rem;
        }

        .question-actions a {
            color: #228be6;
            text-decoration: none;
            margin-right: 1rem;
        }

        .question-actions a:hover {
            text-decoration: underline;
        }

        #question_paging {
            display: flex;
            justify-content: center;
            gap: 0.5rem;
            margin-top: 2rem;
        }

        .page_btn {
            padding: 0.5rem 1rem;
            border: 1px solid #dee2e6;
            background: white;
            border-radius: 4px;
            cursor: pointer;
            color: #495057;
        }

        .page_btn:hover {
            background-color: #228be6;
            color: white;
            border-color: #228be6;
        }

        .checkbox-wrapper {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .checkbox-wrapper input[type="checkbox"] {
            cursor: pointer;
        }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp" %>
    
    <div class="main-container">
    	<!-- 사이드 바 -->
        <div class="question_aside">
            <%@include file="/WEB-INF/views/Question_asidebar.jsp" %>
        </div>

        <div class="content">
        	<!-- 문제 선택 항목 -->
            <div class="filter-section">
                <div class="filter-row">
                    <select name="name_select" id="name_selector">
                        <option selected>선택</option>
                        <% for(Subject sub:sub_all_name) { %>
                            <option><%=sub.getSub_name()%></option>
                        <% } %>
                    </select>
                    
                    <select name="chap_select" id="chap_selector">
                        <option>선택</option>
                    </select>
                </div>

                <div class="filter-row">
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="id" value="ALL" class="question_id" checked disabled="true">
                            전체
                        </label>
                        <label>
                            <input type="radio" name="id" value="MCQ" class="question_id" disabled="true">
                            객관식
                        </label>
                        <label>
                            <input type="radio" name="id" value="SAQ" class="question_id" disabled="true">
                            주관식
                        </label>
                        <label>
                            <input type="radio" name="id" value="CP" class="question_id" disabled="true">
                            코딩
                        </label>
                    </div>

                    <% if(member!=null) { %>
                    <div class="checkbox-wrapper">
                        <label>
                            <input type="checkbox" name="name" value="<%=member.getMem_serial()%>" id="myQuestion">
                            자기가 낸 문제 찾기
                        </label>
                    </div>
                    <% } %>
                </div>
            </div>

			<!-- 문제 출력 영역 -->
            <div id="question_container">
                <div class="question-item">과목과 챕터를 선택해 주세요</div>
            </div>
			
			<!-- 페이징 처리 -->
            <div id="question_paging"></div>
        </div>
    </div>
	
	<%@include file="/WEB-INF/views/footer.jsp" %>
    <script src="/project_403/resources/js/questionAll.js"></script>
</body>
</html>
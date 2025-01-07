<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.spring.domain.Subject"%>
<%
	ArrayList<Subject> sub_all = (ArrayList<Subject>) request.getAttribute("sub_all");
	ArrayList<Subject> sub_all_name = (ArrayList<Subject>) request.getAttribute("sub_all_name");

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문제 등록</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #f5f5f5;
            color: #333;
        }

        .question_main {
            width: 100%;
            display: flex;
            justify-content: center;
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

        .question_form {
            flex: 1;
            max-width: 800px;
            background: white;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .question_subject_chap {
            display: flex;
            gap: 1rem;
            align-items: center;
            margin-bottom: 2rem;
            padding: 1rem;
            background-color: #f8f9fa;
            border-radius: 8px;
        }

        select {
            padding: 0.5rem;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            font-size: 0.95rem;
            background-color: white;
        }

        .question_content {
            background: white;
            padding: 1.5rem;
            border-radius: 8px;
            border: 1px solid #e2e8f0;
        }

        .question_content h5 {
            margin-bottom: 1rem;
            color: #1e40af;
            font-size: 1.1rem;
        }

        textarea {
            width: 100%;
            padding: 1rem;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            resize: vertical;
            min-height: 120px;
            margin-bottom: 1.5rem;
        }

        .question_choice p {
            display: flex;
            align-items: center;
            margin: 1rem 0;
        }

        .question_choice input {
            flex: 1;
            padding: 0.75rem;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            margin-left: 1rem;
            transition: border-color 0.2s ease;
        }

        .question_choice input:focus {
            outline: none;
            border-color: #2563eb;
        }

        .question_ans {
            margin: 1.5rem 0;
        }

        .radio-group {
            display: flex;
            gap: 1.5rem;
            margin: 1rem 0;
        }

        .radio-group label {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            cursor: pointer;
        }

        input[type="radio"] {
            cursor: pointer;
        }

        input[type="file"] {
            padding: 0.75rem;
            background: #f8f9fa;
            border-radius: 6px;
            width: 100%;
        }

        .question_form > input[type="submit"] {
            width: 100%;
            padding: 0.875rem;
            background-color: #2563eb;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.2s ease;
            margin-top: 2rem;
        }

        .question_form > input[type="submit"]:hover {
            background-color: #1d4ed8;
        }

        hr {
            margin: 1.5rem 0;
            border: none;
            border-top: 1px solid #e2e8f0;
        }

        .section-title {
            font-weight: 600;
            color: #1e40af;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp" %>
    
    <div class="question_main">
    	<!-- 사이드 바 -->
        <div class="question_aside">
            <%@include file="/WEB-INF/views/Question_asidebar.jsp" %>
        </div>
		
		<!-- 스프링 폼태그 -->
        <form:form class="question_form" modelAttribute="question" action="Q_addMCQ" method="post" enctype="multipart/form-data" onsubmit="return validateMCQForm(event)">
            <div class="question_subject_chap">
            	<!-- 과목과 챕터 선택 -->
                <span>과목명:</span>
                <select name="name_select" id="name_selector">
                    <option selected>선택</option>
                    <% for(Subject sub:sub_all_name) { %>
                        <option><%=sub.getSub_name()%></option>
                    <% } %>
                </select>
                <select name="chap_select" id="chap_selector"></select>
				
				<!-- 문제 유형 선택 -->
                <div class="radio-group">
                    <label for="MCQ">
                        <form:radiobutton path="question_id" value="MCQ" checked="checked" id="MCQ" onclick="addQuestion('MCQ')"/>
                        객관식
                    </label>
                    <label for="SAQ">
                        <form:radiobutton path="question_id" value="SAQ" id="SAQ" onclick="addQuestion('SAQ')"/>
                        주관식
                    </label>
                    <label for="CP">
                        <form:radiobutton path="question_id" value="CP" id="CP" onclick="addQuestion('CP')"/>
                        코딩
                    </label>
                </div>
            </div>
			
			<!-- 문제 본문 -->
            <div class="question_content">
            	<!-- 문제 내용 -->
                <h5>문제 내용</h5>
                <form:textarea name="question_content" path="question_content" placeholder="문제의 내용을 입력해 주세요."/>
				
				<!-- 선택지 -->
                <div class="question_choice">
                    <p>1 : <form:input name="question_ans" path="question_ans" placeholder="선택지를 입력해 주세요."/></p>
                    <p>2 : <form:input name="question_ans" path="question_ans" placeholder="선택지를 입력해 주세요."/></p>
                    <p>3 : <form:input name="question_ans" path="question_ans" placeholder="선택지를 입력해 주세요."/></p>
                    <p>4 : <form:input name="question_ans" path="question_ans" placeholder="선택지를 입력해 주세요."/></p>
                </div>

                <hr>
				<!-- 정답 -->
                <div class="question_ans">
                    <p class="section-title">정답</p>
                    <div class="radio-group">
                        <label for="MCQ_1">
                            <form:radiobutton name="question_ans" path="question_ans" value="1" id="MCQ_1"/>
                            1번
                        </label>
                        <label for="MCQ_2">
                            <form:radiobutton name="question_ans" path="question_ans" value="2" id="MCQ_2"/>
                            2번
                        </label>
                        <label for="MCQ_3">
                            <form:radiobutton name="question_ans" path="question_ans" value="3" id="MCQ_3"/>
                            3번
                        </label>
                        <label for="MCQ_4">
                            <form:radiobutton name="question_ans" path="question_ans" value="4" id="MCQ_4"/>
                            4번
                        </label>
                    </div>
                </div>

                <hr>
				<!-- 난이도 -->
                <div>
                    <p class="section-title">문제 난이도</p>
                    <div class="radio-group">
                        <label for="level_1">
                            <form:radiobutton name="question_level" path="question_level" value="1" id="level_1"/>
                            1단계
                        </label>
                        <label for="level_2">
                            <form:radiobutton name="question_level" path="question_level" value="2" id="level_2"/>
                            2단계
                        </label>
                        <label for="level_3">
                            <form:radiobutton name="question_level" path="question_level" value="3" id="level_3"/>
                            3단계
                        </label>
                        <label for="level_4">
                            <form:radiobutton name="question_level" path="question_level" value="4" id="level_4"/>
                            4단계
                        </label>
                        <label for="level_5">
                            <form:radiobutton name="question_level" path="question_level" value="5" id="level_5"/>
                            5단계
                        </label>
                    </div>
                </div>

                <hr>
				<!-- 이미지 -->
                <p class="section-title">문제 이미지</p>
                <form:input type="file" path="question_img" id="imageInput"/>
            </div>
            <input type="submit" value="문제 등록">
        </form:form>
    </div>

    <%@include file="/WEB-INF/views/footer.jsp" %>
    <script src="/resources/js/questionAdd.js"></script>
</body>
</html>
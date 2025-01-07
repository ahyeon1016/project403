<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.spring.domain.Question"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문제 수정</title>
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

        .question_content {
            background: white;
            padding: 1.5rem;
            border-radius: 8px;
            border: 1px solid #e2e8f0;
        }

        .question_info {
            display: flex;
            gap: 1rem;
            margin-bottom: 1.5rem;
        }

        .question_info input {
            padding: 0.5rem;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            background-color: #f8f9fa;
        }

        textarea, .content-input {
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

        .section-title {
            font-weight: 600;
            color: #1e40af;
            margin-bottom: 1rem;
        }

        hr {
            margin: 1.5rem 0;
            border: none;
            border-top: 1px solid #e2e8f0;
        }

        .question_image {
            margin: 1.5rem 0;
        }

        .question_image img {
            max-width: 100%;
            border-radius: 8px;
            margin: 1rem 0;
        }

        input[type="file"] {
            padding: 0.75rem;
            background: #f8f9fa;
            border-radius: 6px;
            width: 100%;
        }

        input[type="submit"] {
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

        input[type="submit"]:hover {
            background-color: #1d4ed8;
        }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp" %>
    
    <div class="question_main">
    	<!-- 사이드바 -->
        <div class="question_aside">
            <%@include file="/WEB-INF/views/Question_asidebar.jsp" %>
        </div>
		
		<!-- 스프링 폼태그 -->
        <form:form class="question_form" modelAttribute="question" action="../Q_updateMCQ" method="post" enctype="multipart/form-data" onsubmit="return validateMCQForm(event)">
            <div class="question_content">
            	<!-- 과목, 챕터, 문제 유형 -->
                <div class="question_info">
                    <form:hidden path="mem_serial"/>
                    <p>과목 코드: <form:input path="sub_code_sum" readonly="true"/></p>
                    <p>문제 번호: <form:input path="question_serial" readonly="true"/></p>
                </div>

                <div class="radio-group">
                    <label>
                        문제분류: 객관식
                        <form:radiobutton path="question_id" value="MCQ" checked="checked"/>
                    </label>
                </div>

                <hr>
				<!-- 문제 내용 -->
                <h5 class="section-title">문제 내용</h5>
                <form:textarea name="question_content" path="question_content" class="content-input"/>
				
				<!-- 선택지 -->
                <div class="question_choice">
                    <p>1 : <form:input name="question_ans" path="question_ans" value="${ans[0]}"/></p>
                    <p>2 : <form:input name="question_ans" path="question_ans" value="${ans[1]}"/></p>
                    <p>3 : <form:input name="question_ans" path="question_ans" value="${ans[2]}"/></p>
                    <p>4 : <form:input name="question_ans" path="question_ans" value="${ans[3]}"/></p>
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
                <div class="question_image">
                    <p class="section-title">현재 문제 이미지</p>
                    <img src="/resources/images/${question.getQuestion_img_name()}" alt="문제 이미지">
                    
                    <p class="section-title">새 문제 이미지 업로드</p>
                    <form:input id="imageInput" type="file" path="question_img"/>
                </div>

                <div style="margin-top: 1.5rem;">
                    <p class="section-title">문제풀이 횟수</p>
                    <form:input path="question_count" disabled="true" style="background-color: #f8f9fa; padding: 0.5rem; border-radius: 6px; border: 1px solid #e2e8f0;"/>
                </div>
            </div>

            <input type="submit" value="문제 수정">
        </form:form>
    </div>

    <%@include file="/WEB-INF/views/footer.jsp" %>
    <script src="/resources/js/questionUpdate.js"></script>
</body>
</html>		
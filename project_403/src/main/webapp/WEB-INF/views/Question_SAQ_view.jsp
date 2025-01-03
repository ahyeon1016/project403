<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Question" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주관식 문제 풀이</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.5;
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

        .content {
            flex: 1;
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            padding: 2rem;
        }

        .question-meta {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 2rem;
        }

        .question-meta p {
            margin-bottom: 0.5rem;
            color: #495057;
        }

        .question-meta p:last-child {
            margin-bottom: 0;
        }

        .question-image {
            margin: 2rem 0;
            text-align: center;
        }

        .question-image img {
            max-width: 100%;
            height: auto;
            border-radius: 4px;
        }

        .question-content {
            margin-bottom: 2rem;
        }

        .question-content p {
            font-size: 1.1rem;
            margin-bottom: 1.5rem;
        }

        .answer-input {
            width: 100%;
            padding: 0.75rem;
            border: 2px solid #e9ecef;
            border-radius: 4px;
            font-size: 1rem;
            margin-bottom: 1rem;
        }

        .action-buttons {
            margin-top: 2rem;
            display: flex;
            gap: 1rem;
        }

        button {
            padding: 0.75rem 1.5rem;
            background-color: #228be6;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.2s;
        }

        button:hover {
            background-color: #1c7ed6;
        }

        .question-link {
            display: inline-block;
            padding: 0.75rem 1.5rem;
            background-color: #f8f9fa;
            color: #228be6;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.2s;
        }

        .question-link:hover {
            background-color: #e9ecef;
        }

        #plus {
            font-weight: bold;
            color: #228be6;
        }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp" %>
    
    <div class="main-container">
        <div class="question_aside">
            <%@include file="/WEB-INF/views/Question_asidebar.jsp" %>
        </div>

        <div class="content">
            <div class="question-meta">
                <p>문제 번호: ${question.question_serial}</p>
                <p>문제 난이도: ${question.question_level} 단계</p>
                <p>작성자: ${question.getMem_nickName()}</p>
                <p>과목: ${question.sub_code_sum}</p>
                <p>문제풀이 시도 횟수 합계: ${question.question_count}<span id="plus"></span></p>
            </div>

            <% if(request.getAttribute("question") != null) { 
                Question question = (Question)request.getAttribute("question");
                if(question.getQuestion_img_name() != null && !question.getQuestion_img_name().isEmpty()) { %>
                <div class="question-image">
                    <img src="/project_403/resources/images/${question.question_img_name}">
                </div>
            <% }} %>

            <div class="question-content">
                <p>${question.question_content}</p>
                
                <input type="text" id="ans_input" class="answer-input" placeholder="답을 입력하세요">
            </div>

            <div class="action-buttons">
                <button onclick="grading('${question.question_ans}', '${question.question_serial}', ${question.question_count}, ${question.question_level})">
                    정답 확인
                </button>
                <a href="/project_403/QnA/addCommentRoot?question_serial=${question.question_serial}" class="question-link">
                    질문하기
                </a>
            </div>
        </div>
    </div>

    <script>
        let index = 0;
        function grading(ans, question_serial, question_count, question_level){
            let ans_input = document.querySelector("#ans_input");
            let plus = document.querySelector("#plus");
            let isInput = false;	
            
            if(ans_input.value!=null && ans_input.value.trim()!=""){
                isInput=true;
                if(ans_input.value==ans){
                    index++;
                    alert(index+"회만에 정답!");
                    window.location.href=
                        "../Q_plusCount?serial="+question_serial+
                        "&count="+question_count+
                        "&plus="+index+
                        "&level="+question_level;
                } else{
                    index++;
                    alert("땡!");
                }
            } 
            plus.textContent = "+"+index;
            if(isInput!=true){
                alert("입력하고 눌러주세요.");
            }
        }
    </script>
</body>
</html>
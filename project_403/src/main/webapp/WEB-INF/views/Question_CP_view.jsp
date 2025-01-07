<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Question" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>코딩 문제 풀이</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
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

        .code-input {
            width: 100%;
            padding: 1rem;
            color: #d4d4d4;
            background-color: #1e1e1e;
            border: 2px solid #e9ecef;
            border-radius: 4px;
            font-family: 'Consolas', monospace;
            font-size: 0.9rem;
            margin-bottom: 1rem;
            resize: vertical;
        }

        .error-output {
            width: 100%;
            padding: 1rem;
            background-color: #fff5f5;
            border: 2px solid #ffe3e3;
            border-radius: 4px;
            font-family: monospace;
            font-size: 0.9rem;
            color: #e03131;
            margin-bottom: 1rem;
            resize: none;
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
    	<!-- 사이드바 -->
        <div class="question_aside">
            <%@include file="/WEB-INF/views/Question_asidebar.jsp" %>
        </div>

        <div class="content">
        	<!-- 문제 정보 -->
            <div class="question-meta">
                <p>문제 번호: ${question.question_serial}</p>
                <p>문제 난이도: ${question.question_level} 단계</p>
                <p>작성자: ${question.getMem_nickName()}</p>
                <p>과목: ${question.sub_code_sum}</p>
                <p>문제풀이 시도 횟수 합계: ${question.question_count}<span id="plus"></span></p>
            </div>
			
			<!-- 이미지 존재 여부 확인 -->
            <% if(request.getAttribute("question") != null) { 
                Question question = (Question)request.getAttribute("question");
                if(question.getQuestion_img_name() != null && !question.getQuestion_img_name().isEmpty()) { %>
                <div class="question-image">
                    <img src="/resources/images/${question.question_img_name}">
                </div>
            <% }} %>
			
			<!-- 문제 내용 -->
            <div class="question-content">
                <p>${ans[0]}</p>
                
                <textarea id="ans_input" class="code-input" rows="15">${ans[1]}</textarea>
                <textarea id="ans_error" class="error-output" rows="10" readonly></textarea>
            </div>
			
			<!-- 버튼 영역 -->
            <div class="action-buttons">
                <button onclick="grading('${question.question_ans}', '${question.question_serial}', ${question.question_count}, ${question.question_level})">
                    정답 확인
                </button>
                <a href="/QnA/addCommentRoot?question_serial=${question.question_serial}" class="question-link">
                    질문하기
                </a>
            </div>
        </div>
    </div>
    
	<script>
		let index = 0;
		/* 문제풀이 횟수를 추가하는 함수 */
		function grading(ans, question_serial, question_count, question_level){
			let ans_input = document.querySelector("#ans_input");
			let ans_error = document.querySelector("#ans_error");
			let plus = document.querySelector("#plus");
			ans_error.text= null;
			$.ajax({
				url : "../Compile",
				type : "POST",
				contentType : "application/json;charset=UTF-8",
				data : JSON.stringify({"ans_input" : ans_input.value}),
				success : function(data){
					if(data.success){
						console.log(data.output+"|"+ans);
						if(data.output==ans){
							index++;
							alert(index+"회만에 정답!");
							/* 정답을 맞추면 누적된 횟수를 가지고 이동 */
							window.location.href=
								"../Q_plusCount?serial="+question_serial+
								"&count="+question_count+
								"&plus="+index+
								"&level="+question_level;
						} else{
							index++;
							alert("땡!");
						}
					} else{
						alert("오류 발생!!!");
						console.log(data.output+"\n"+data.errorCode);
						ans_error.textContent = data.output;
					}
				},
				error : function(data){
					console.log("오류");
				}
			});
			/* 문제풀이 횟수 갱신 */
			plus.textContent = "+"+index;
		}
	</script>
</body>
</html>
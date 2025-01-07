<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Q&A 상세보기</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
            color: #333;
        }

        .main-container {
        	background-color: #FAFAFA;
            max-width: 800px;
            margin: 0 auto;
            padding: 2rem;
        }

        .content {
        	background-color: white;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            padding: 2rem;
        }

        .post-header {
            margin-bottom: 2rem;
            border-bottom: 1px solid lightgray; 
        }

        .post-title {
            font-size: 1.75rem;
            font-weight: 600;
            color: #333;
            margin-bottom: 0.5rem;
        }

        .post-meta {
            color: #868e96;
            font-size: 0.9rem;
            margin-bottom: 5px;
        }

        .post-content {
            margin: 2rem 0;
            line-height: 1.6;
        }

        .post-stats {
            display: flex;
            gap: 1rem;
            align-items: center;
            margin: 1.5rem 0;
        }


        button {
            padding: 5px 10px;
            border: 1px solid #dee2e6;
            background: #FAFAFA;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }

        .reaction-button {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            padding: 0.5rem 1rem;
            border: 1px solid #dee2e6;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }
        
        #goodBtn:hover {
            background-color: #F78181 !important;
        }
	
     	#badBtn:hover {
            background-color: #81BEF7 !important;
        }
		
		#comment_input_btn:hover {
            background-color: #228be6;
            color: white;
        }
        
		.addBtn:hover {
            background-color: #228be6;
            color: white;
        }

        .delBtn:hover {
            background-color: #FA5858;
            color: white;
        }
		
        .comment-section {
            margin-top: 3rem;
        }

        .comment-header {
            font-size: 1.25rem;
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .comment-input {
            width: 100%;
            margin-bottom: 2rem;
        }

        .comment-input textarea {
            width: 100%;
            height: 140px;
            padding: 1rem;
            border: 1px solid #dee2e6;
            border-radius: 10px;
            resize: vertical;
            min-height: 100px;
        }

        .comment-list {
			margin: 0 auto;        	
            padding: 20px;
            border-radius: 10px;
            border: 1px solid lightgray;
            list-style: none;
        }

        .comment-meta {
            font-size: 0.9rem;
            color: #868e96;
            margin-bottom: 0.5rem;
        }

        .hidden {
            display: none;
        }

        .reaction-count {
            font-weight: 500;
            color: #495057;
        }
        
        .comment-section textarea{
        	resize: none;
        }
        
        #comment>li {
        	border-radius: 5px;
        	box-shadow: 0 0px 2px rgba(0,0,0,0.3); 
        	margin: 5px;
        	padding: 30px;
        }
        
        #comment>ul{
        	border-left: 1px solid;
        }
        
        #comment textarea{
        	width: 100%;
        	height: 120px;
        	border: 1px solid #dee2e6;
            border-radius: 4px;
        }
		        
		hr {
		    background-color: gray;
		}
        
        #comment_hit{
        	color: black;
        	margin-left: 580px;
        	padding: 2px 15px;
        	border-radius: 5px;
        	box-shadow: 0 0px 2px rgba(0,0,0,0.5);
        }
        
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp"%>
    <!-- 숨겨진 요소 -->
    <div class="hidden" id="isClicked_btn">${isClicked_btn}</div>
    <div class="hidden" id="nickName">${member.getMem_nickName()}</div>
    
    <!-- 메인 컨테이너 -->
    <div class="main-container">
        <div class="content">
        	<!-- 질문글 상단 -->
            <div class="post-header">
                <h1 class="post-title">${qna.getComment_title()}</h1>
                <div class="post-meta">
                    <span>작성자: ${qna.getMem_nickName()}</span> |
                    <span>문제 번호: ${qna.getQuestion_serial()}</span> |
                    <span id="root" class="hidden">${qna.getComment_root()}</span>
                    <span id="comment_date">${qna.getComment_date()}</span>
                    <span id="comment_hit">조회수 ${qna.getComment_hit()}</span>
                </div>
            </div>
            
            <!-- 질문글 메인 내용 -->
            <div class="post-content">
            	<p><a href="/Q/${question_url}">문제 보기</a></p>
                <p>${qna.getComment_content()}</p>
            </div>
			
			<!-- 좋아요 싫어요 버튼 -->
            <div class="post-stats">
                <button id="goodBtn" class="reaction-button" 
                        style="background-color: ${goodColor}" 
                        onclick="goodUp(${isGood_btn}, ${qna.getComment_num()})">
                    	좋아요 <span id="good" class="reaction-count">${qna.getComment_totalGood()}</span>
                </button>
                <button id="badBtn" class="reaction-button"
                        style="background-color: ${badColor}" 
                        onclick="badUp(${isBad_btn}, ${qna.getComment_num()})">
						싫어요 <span id="bad" class="reaction-count">${qna.getComment_totalBad()}</span>
                </button>
            </div>
			
			<!-- 댓글 영역 -->
            <div class="comment-section">
                <h5 class="comment-header">전체 댓글 (<span id="comment-count">0</span>개)</h5>
                <div class="comment-input">
                    <textarea id="comment_input" placeholder="내용을 입력해 주세요."></textarea>
                    <button id="comment_input_btn" 
                            onclick="comment_submit(${qna.getComment_root()}, '${qna.getQuestion_serial()}', '${qna.getMem_id()}')">
                        	댓글 작성
                    </button>
                </div>
                <ul id="comment" class="comment-list">
                </ul>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/views/footer.jsp"%>
    <script src="/resources/js/commentRoot.js"></script>
</body>
</html>
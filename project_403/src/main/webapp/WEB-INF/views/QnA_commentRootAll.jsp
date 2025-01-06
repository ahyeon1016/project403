<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.QnA"%>
<%@ page import="java.util.ArrayList"%>
<%
    ArrayList<QnA> rootAll = (ArrayList<QnA>) request.getAttribute("rootAll");
    int index = (int)request.getAttribute("index");
    int maxPage = (int)request.getAttribute("maxPage");
    int totalPage = (int)request.getAttribute("totalPage");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Q&A 목록</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
        }

        .main-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .content {
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            padding: 2rem;
        }

        .qna-item {
            padding: 1.5rem;
            border-bottom: 1px solid #dee2e6;
        }

        .qna-item:last-child {
            border-bottom: none;
        }

        .qna-meta {
            display: flex;
            gap: 1rem;
            color: #868e96;
            font-size: 0.9rem;
            margin-bottom: 0.5rem;
        }

        .qna-title {
            font-size: 1.25rem;
            font-weight: 600;
            margin: 1rem 0;
            color: #333;
        }

        .qna-stats {
            display: flex;
            gap: 1.5rem;
            margin: 1rem 0;
            color: #495057;
        }

        .qna-action {
            display: inline-block;
            padding: 0.5rem 1rem;
            background-color: #228be6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.2s;
        }

        .qna-action:hover {
            background-color: #1c7ed6;
        }

        .pagination {
            display: flex;
            justify-content: center;
            gap: 0.5rem;
            margin-top: 2rem;
        }

        .pagination a {
            padding: 0.5rem 1rem;
            border: 1px solid #dee2e6;
            background: white;
            border-radius: 4px;
            text-decoration: none;
            color: #495057;
            transition: all 0.2s;
        }

        .pagination a:hover {
            background-color: #228be6;
            color: white;
            border-color: #228be6;
        }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp"%>
    
    <div class="main-container">
        <div class="content">
            <% for(int i=index; i<maxPage; i++) {
                QnA qna = rootAll.get(i);
            %>
            <div class="qna-item">
                <div class="qna-meta">
                    <span>작성자: <%=qna.getMem_nickName()%></span>
                    <span>문제 번호: <%=qna.getQuestion_serial()%></span>
                    <span>게시글 번호: <%=rootAll.size()-i%></span>
                </div>
                
                <div class="qna-title"><%=qna.getComment_title()%></div>
                
                <div class="qna-meta">
                    <span>작성일: <%= (qna.getComment_date().getYear() + 1900)+"-"
                        +String.format("%02d", qna.getComment_date().getMonth() + 1)+"-"
                        +String.format("%02d", qna.getComment_date().getDate()) %></span>
                </div>
                
                <div class="qna-stats">
                    <span>조회수: <%=qna.getComment_hit()%></span>
                    <span>좋아요: <%=qna.getComment_totalGood()%></span>
                    <span>싫어요: <%=qna.getComment_totalBad()%></span>
                </div>
                
                <a href="commentRootOne?comment_root=<%=qna.getComment_root()%>" class="qna-action">
                    자세히 보기
                </a>
            </div>
            <% } %>

            <div class="pagination">
                <% for(int i=1; i<totalPage+1; i++) { %>
                    <a href="commentRootAll?page=<%=i%>"><%=i%></a>
                <% } %>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>
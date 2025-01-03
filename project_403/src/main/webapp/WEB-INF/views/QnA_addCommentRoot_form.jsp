<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f9f9f9;
        margin: 0;
        padding: 0;
    }

    form {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background: #ffffff;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    form p {
        margin: 10px 0;
        font-size: 16px;
        color: #333;
    }

    form input[type="text"], 
    form textarea {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        font-size: 14px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    form input[type="text"]:read-only {
        background-color: #f5f5f5;
        color: #666;
    }

    form textarea {
        height: 100px;
    }

    form input[type="submit"] {
        display: inline-block;
        background: #4caf50;
        color: #fff;
        border: none;
        border-radius: 4px;
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
        transition: background 0.3s ease;
    }

    form input[type="submit"]:hover {
        background: #45a049;
    }

    form:after {
        content: "";
        display: table;
        clear: both;
    }
</style>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	<form:form modelAttribute="qna" action="addCommentRoot" method="post">
		<p> 문제 번호 : <form:input path="question_serial" readonly="true"/> 
		<p> 제목 : <form:input path="comment_title"/>
		<p> 내용 : <form:textarea style="resize: none;" path="comment_content" placeholder="질문을 작성해 주세요."/>
		<p><input type="submit" value="작성"/>
	</form:form>
	<%@include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
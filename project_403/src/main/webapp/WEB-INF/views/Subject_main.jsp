<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>과목 관리 시스템</title>
    <style>
		body { 
		    margin: 20px; 
		    background-color: #f5f5f5; 
		} 
		
		.main-container { 
		    max-width: 800px; 
		    margin: 0 auto; 
		    padding: 20px; 
		    background: white; 
		    border-radius: 8px; 
		    box-shadow: 0 2px 4px rgba(0,0,0,0.1); 
		} 
		
		.menu-item { 
		    margin: 15px 0; 
		} 
		
		.menu-item a { 
		    color: #007bff; 
		    text-decoration: none; 
		} 
		
		.menu-item a:hover { 
		    text-decoration: underline; 
		} 
		
		.search-form { 
		    margin-top: 20px; 
		    padding: 15px; 
		    border: 1px solid #ddd; 
		    border-radius: 4px; 
		} 
		
		input[type="text"] { 
		    padding: 8px; 
		    width: 100%; 
		    margin: 5px 0; 
		} 
		
		input[type="submit"] { 
		    background: #007bff; 
		    color: white; 
		    border: none; 
		    padding: 10px 20px; 
		    border-radius: 4px; 
		    cursor: pointer; 
		}

    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp" %>
    <div class="main-container">
        <h2>과목 관리 시스템</h2>
        <p>사용자: ${member.getMem_id()}</p>
        
        <div class="menu-item"><a href="sub/sub_name_form">과목명 등록</a></div>
        <div class="menu-item"><a href="sub/sub_chap_form">챕터 등록</a></div>
        <div class="menu-item"><a href="sub/sub_all">과목 목록</a></div>
        
        <div class="search-form"> 
            <form:form action="sub/getSubByName" method="get">
                <h3>과목명으로 검색</h3>
                <input type="text" name="sub_name" placeholder="과목명 입력">
                <input type="submit" value="검색">
            </form:form>
        </div>
        
        <div class="search-form">
            <form:form modelAttribute="subject" action="sub/getSubByChap" method="get">
                <h3>챕터로 검색</h3>
                <form:input path="sub_name" placeholder="과목명"/>
                <form:input path="sub_chap" placeholder="챕터명"/>
                <input type="submit" value="검색">
            </form:form>
        </div>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
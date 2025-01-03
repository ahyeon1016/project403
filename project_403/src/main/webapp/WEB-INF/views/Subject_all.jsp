<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.spring.domain.Subject" %>
<%
	ArrayList<Subject> sub_all = (ArrayList<Subject>) request.getAttribute("sub_all");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>과목 목록</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .list-container { max-width: 800px; margin: 0 auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .subject-item { border: 1px solid #ddd; margin: 10px 0; padding: 15px; border-radius: 4px; }
        .subject-item h3 { margin: 0 0 10px 0; }
        .action-links a { color: #007bff; text-decoration: none; margin-right: 10px; }
        .action-links a:hover { text-decoration: underline; }
        .empty-message { text-align: center; color: #666; }
    </style>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
    <div class="list-container">
        <h2>과목 목록</h2>
        <% if(sub_all.size()!=0) {
            for(Subject sub : sub_all) { %>
                <div class="subject-item">
                    <h3><%=sub.getSub_name()%></h3>
                    <p>과목 코드: <%=sub.getSub_name_code()%></p>
                    <p>챕터: <%=sub.getSub_chap()%></p>
                    <p>챕터 코드: <%=sub.getSub_chap_code()%></p>
                    <p>과목 번호: <%=sub.getSub_num()%></p>
                    <div class="action-links">
                        <a href="updateSubName?sub_name=<%=sub.getSub_name()%>">과목명 수정</a>
                        <a href="deleteSubName?sub_name=<%=sub.getSub_name()%>">과목 삭제</a>
                        <a href="updateSubChap?sub_name=<%=sub.getSub_name()%>&sub_chap=<%=sub.getSub_chap()%>">챕터 수정</a>
                        <a href="deleteSubChap?sub_name=<%=sub.getSub_name()%>&sub_chap=<%=sub.getSub_chap()%>">챕터 삭제</a>
                    </div>
                </div>
            <% }
        } else { %>
            <p class="empty-message">등록된 과목이 없습니다.</p>
        <% } %>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
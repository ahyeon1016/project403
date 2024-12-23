<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.spring.domain.Question" %> 
<%@ page import="com.spring.domain.Fnote" %>   
<!DOCTYPE html>
<html>
<head>
<style>
	* {
 	   list-style: none;
	  }
	.ans {
 		visibility: hidden; 
 		}
	td:hover .ans{
	visibility:visible;
	}
	
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <%--아작스 사용을 위한 코오드 --%>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>

	<%if(member==null){ %>
		<h1>로그인해주세요!</h1>
	<%} %>
    <% if (request.getAttribute("questionlist") != null) {
        ArrayList<Question> question_list = (ArrayList<Question>) request.getAttribute("questionlist");
        Fnote fnote=(Fnote)session.getAttribute("fnote");
    %>
	<form action="delete" method="post" id="fom">
    <% if (question_list.size() != 0) { %>
		<table border=1>
			<tr>
			<td>문제</td>
			<td>정답</td>
			<td>비고</td>
			<td>노트</td>
			</tr>
            <% for (int i = 0; i < question_list.size(); i++) {
                Question question = question_list.get(i);
                String imgName = question.getQuestion_img_name();
                String content = question.getQuestion_content();
                String answer = question.getQuestion_ans();
            %>
                <tr>
                <td><%= content %></td>
                <td><span class="ans"><%= answer %></span></td> 
                <td><%if(question.getQuestion_id().equals("CP")){%>코딩문제 입니다.<%} %></td>
                <td><textarea class="note_area" cols="30" rows="10"><%if(fnote.getNote()!=null){%><%=fnote.getNote()[i]%><%} %></textarea></td>
                </tr>
                
            <% } %>
        </table>
        	<button id="note_pre">저장하기</button>
            <button id="note_delete" type="submit">노트 삭제</button> <a href="../">홈</a>
        </form>
    <% } else { %>
        <h1>자료를 읽어올 수 없습니다.</h1>
    <% } %>
    <% } else { %>
        <h1>자료를 읽어올 수 없습니다.</h1>
    <% } %>
    
    <script>
    	let up_but=document.querySelector("#note_pre");
    	let delete_but=document.querySelector("#note_delete");
    	up_but.addEventListener("click",note_pre_up);
    	delete_but.addEventListener("click",del);
	    	function note_pre_up(e){
	    		e.preventDefault();
	    		let areas=document.querySelectorAll(".note_area");
	    		let notes="";
	    		for(let j=0;j<areas.length;j++){
	    			notes+=areas[j].value+",";
	    			console.log(notes);
	    		}
	    		$.ajax({
	    			url:"./update",
	    			type:"post",
	    			data:JSON.stringify({"notes":notes}),
	    			contentType:"application/json;charset=UTF-8",
	    			success:function(data){
	    				alert("저장 완료");
	    			},
	    			error:function(errorThrown)
	    			{alert("실패")}
	    			
	    		});
	    	}
	    	
	    	function del(e){
	    		e.preventDefault();	
	    		if(confirm("정말 삭제하시겠습니까?")){
	    			document.querySelector("#fom").submit();
	    			alert("삭제되었습니다.");
	    		}else{
	    			alert("취소되었습니다.");
	    		}
	    		
	    	}
    </script>
</body>
</html>

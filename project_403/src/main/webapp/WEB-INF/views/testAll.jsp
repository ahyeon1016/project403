<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<body>
testAll 페이지
<p><a href="../">Home</a>
<div>
	<!-- 시험 목록 보여주기(All) -->
	<!-- <c:forEach items="${List}" var="test">
		<div style="border: 1px solid black;">
			<h3>${test.test_num}</h3>
			<p>${test.mem_id}
			<p>${test.test_time}
			<p>${test.test_date}
			<p>${test.test_name}
			<p><a href="testUpdate?Num=${test.test_num}">수정</a>
			<p><a href="testDelete?Num=${test.test_num}">삭제</a>
		</div>
	</c:forEach> -->
	
	<!-- 검색 -->
	<div>				
		<select name="items" class="txt">
			<option value="title">제목 검색</option>
			<option value="subject">과목 검색</option>
			<option value="name">글쓴이에서</option>
		</select> <input name="text" type="text" /> <input type="submit" id="btnAdd" value="검색 " />				
	</div>

	<!-- 시험 목록 보여주기(게시판 형식) -->
	<div>
		<p><h3>전체글 총 ${total_record}건</h3>
	</div>
	<c:forEach items="${boardList}" var="test" varStatus="status">
		<div style="border: 1px solid black;">
			<h3>번호: ${test.test_num}</h3>
			<!-- <p>제목: <a href="testOneView?Num=${test.test_num}" onclick="testPopup()">${test.test_name}</a> -->
			<p>제목: <button onclick="testPopup('${test.test_num}')">${test.test_name}</button> 
			<p>작성자: ${test.mem_id}
			<p>비밀번호 입력: ${test.test_pw}
			<p>조회수: ${test.test_hit}
			<p>교과명: ${test.sub_name}
			<p>챕터명: ${test.sub_chap}
			<p><a href="testUpdate?Num=${test.test_num}">수정</a>
			<p><a href="testDelete?Num=${test.test_num}">삭제</a>
		</div>
	</c:forEach>

	<!-- 페이지 이동 -->
	<div align="center">
		<c:set var="pageNum" value="${pageNum}" />
		<c:forEach var="i" begin="1" end="${total_page}">
			<a href="testAll?pageNum=${i}">
				<c:choose>
					<c:when test="${pageNum==i}">
						<font color='4C5317'><b> [${i}]</b></font>
					</c:when>
					<c:otherwise>
						<font color='4C5317'> [${i}]</font>
					</c:otherwise>
				</c:choose>
			</a>
		</c:forEach>
	</div>
	
	
</div>
</body>
<script type="text/javascript">
function testPopup(test_num) {
	let inputValue = prompt("비밀번호를 입력하세요.");
	if(inputValue !== null) {
		ajaxTest(inputValue, test_num);
	}
}

function ajaxTest(inputValue, test_num) {
	let param = {
			password : inputValue,
			test_num : test_num
	};
	
	$.ajax({
		type: "POST", <!-- post 방식으로 전송 / -->
		url: "testValue", <!-- 전송할 서블릿을 지정 -->
		data: param, <!-- 서버로 매개변수와 값을 설정 -->
		dataType : 'json',
        <!-- 전송과 응답이 성공했을 경우의 작업을 설정 -->
		success: function (data) {
			if(data.success) {
				window.location.href = "testOneView?Num=" + test_num; <!-- 서버 응답 메시지를 <div> 엘리먼트에 표시 -->
			} else {
				alert("비밀번호가 틀렸습니다.");
			}
		},
        <!-- 작업 중 오류가 발생했을 경우에 수행할 작업을 설정 -->
		error: function (data) {
			alert("에러가 발생했습니다.");
		},
	});
}
</script>
</html>
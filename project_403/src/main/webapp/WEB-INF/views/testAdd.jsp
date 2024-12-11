<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<meta charset="UTF-8">
	<title>시험 제출 공간</title>
</head>

<body>
testAdd 페이지
<p><a href="../">Home</a>
<form:form modelAttribute="NewTest" action="./testAdd">
	<fieldset>
		<div>
			작성자ID: <form:input path="mem_id" />
		</div>		
		<div>
			시험 제목: <form:input path="test_name" />
		</div>
		<div>
			시험 비밀번호: <form:input path="test_pw" />
		</div>
		<div>
			<!-- 공개 또는 비공개: <form:input path="test_openYN" /> -->
			공개 / 비공개: 
				<form:radiobutton path="test_openYN" value="Y" checked="checked" />Y
				<form:radiobutton path="test_openYN" value="N" />N
		</div>
		<div>
			과목명: 
				<form:select path="sub_name" id="subjectSelect">
					<form:option value="" selected="true">카테고리를 선택해주세요.</form:option>
					<c:forEach items="${subList}" var="sub">
	        			<form:option value="${sub.sub_name}">${sub.sub_name}</form:option>					
					</c:forEach>
				</form:select>
		</div>
		<div>
			챕터명: 
				<form:select path="sub_chap" id="chapSelect">
					<form:option value="" selected="true">카테고리를 먼저 선택해주세요.</form:option>
					<!-- <c:forEach items="${chapList}" var="chap">
	        			<form:option value="${chap.sub_chap}">${chap.sub_chap}</form:option>					
					</c:forEach> -->
				</form:select>
		</div>		
		<div>
			<input type="submit" value="작성하기">
		</div>
	</fieldset>
</form:form>

<div>
	기존 문제 불러오기:
	<div id="qnaSelect"></div>
</div>
</body>

<script type="text/javascript">
$("#subjectSelect").on("change", function() {

	let chapSelect = $("#chapSelect")
	chapSelect.empty();
	let chapterHtml = "";
	let param = {sub_name : $(this).val()};
	
	if ($(this).val() != "") {
		$.ajax({
			type: "POST", 
			url: "subValue", 
			data: param, 
			dataType : "json",
	        <!-- 전송과 응답이 성공했을 경우의 작업을 설정 -->
			success: function(data) {
				chapterHtml += "<option selected value=''>ALL</option>";				
				for(let i = 0; i < data.chapList.length; i++) {
					chapterHtml += "<option>" + data.chapList[i].sub_chap +"</option>";
				}
				chapSelect.append(chapterHtml);
			},
	        <!-- 작업 중 오류가 발생했을 경우에 수행할 작업을 설정 -->
			error: function (data) {
				alert("에러가 발생했습니다.");
			}
		});
	} else {
		chapterHtml += "<option selected value=''>카테고리를 먼저 선택해주세요.</option>";
		chapSelect.append(chapterHtml);
	}
});

$("#chapSelect").on("change", function() {
	
	let qnaSelect = $("#qnaSelect");
	qnaSelect.empty();
	let qnaHtml = "";

	let param = {
			sub_name : $("#subjectSelect").val(),
			sub_chap : $(this).val()
	};
	
	$.ajax({
		type: "POST", 
		url: "qnaSelectRead", 
		data: param, 
		dataType : 'json',
		success: function (data) {
			for(let i = 0; i < data.qnaList.length; i++) {
				qnaHtml += "<div style='border: 1px solid black;'>" + data.qnaList[i].question_content + "</div><div>" + data.qnaList[i].question_ans + "</div>";
			}
			qnaSelect.append(qnaHtml);
		},
		error: function (data) {
			alert("실패");
		},		
	});
});
</script>
</html>

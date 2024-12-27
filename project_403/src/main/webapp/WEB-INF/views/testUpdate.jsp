<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<!-- 드래그 앤 드랍 라이브러리 -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js"></script>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<link rel="icon" href="data:;base64,iVBORw0KGgo=">
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<style>
	.container {
		display: flex;
		justify-content: space-around;
	}
	
	.test {
		width: 30%;
		border: 1px solid black;
	}
	
	.input_wrap {
		width:100%;
		height: 500px;
		border: 1px solid black;
	}
	
	.input_list {
		width:100%;
		height: 100px;
		background-color: yellow;
		border: 1px solid black;
	}
	
	/* 문제 자동 카운터 */
	.list {
		counter-reset: numbering;
	}

	.list .item:before{
	    counter-increment: numbering; 
	    content: counter(numbering) "번";
	    margin-right: 10px;
	}
</style>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

testUpdate 페이지
<p><a href="testAll">Home</a>
<div class="container">
	<form:form modelAttribute="UpdateTest" action="./testUpdate" class="test">
		<fieldset>
			<div>
				시험 번호: <input name="test_num" value="${test.test_num}" readonly="readonly" />
			</div>		
			<div>			
				작성자ID: <input name="mem_id" value="${test.mem_id}" readonly="readonly" />
			</div>
			<div>
				시험 제목: <form:input path="test_name" value="${test.test_name}" />
			</div>
			<div>
				시험 비밀번호: <form:input path="test_pw" value="${test.test_pw}" />
			</div>
			<div>
				공개 / 비공개: 
					<form:radiobutton path="test_openYN" value="Y" checked="checked" />Y
					<form:radiobutton path="test_openYN" value="N" />N
			</div>
			<div>
				과목명: 
					<form:select path="sub_name" id="subjectSelect">
						<form:option value="" selected="true" disabled="true" hidden="true">카테고리를 선택해주세요.</form:option>
						<c:forEach items="${subList}" var="sub">
		        			<form:option value="${sub.sub_name}">${sub.sub_name}</form:option>					
						</c:forEach>
					</form:select>
			</div>
			<div id="chapSelect">			 
				챕터명: <input type="checkbox" class="chapSelect" value="All" />All
			</div>
			<input type="button" id="questionSelect" value="등록문제보기">
			<p>총 문제 갯수: ${fn:length(test.serial)}개 <br>
			시험지 작성 공간
			<div id="input_wrap" class="input_wrap list">
				<c:forEach items="${allQuestion}" var="allQuestion">
					<div class='input_list item' draggable='true'>
						<input type="text" name="serial[]" value="${allQuestion.question_serial}"><br>
						${allQuestion.question_content}<br>
						<c:set var="splitData" value="${fn:split(allQuestion.question_ans, '|★|')}" />        
				            1번: ${splitData[0]},
				            2번: ${splitData[1]}, 
				            3번: ${splitData[2]}, 
				            4번: ${splitData[3]}
				            <br>
				            정답: ${splitData[4]}번
					</div>
				</c:forEach>
			</div>		
			<div>
				<input type="submit" value="수정하기">
			</div>
		</fieldset>
	</form:form>
	
	<div class="test">
		기존 문제 불러오기
		<div id="qnaSelect" class="input_wrap">
		
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
// 과목 선택시 챕터 체크박스 생성
$("#subjectSelect").on("change", function() {
	let chapSelect = $("#chapSelect");
	chapSelect.empty();
	let chapterHtml = "";
	
	let param = {sub_name : $(this).val()};
	
	if ($(this).val() != "") {
		$.ajax({
			type: "POST", 
			url: "subValue", 
			data: param,
			dataType : "json",
			success: function(data) {
				chapterHtml += "챕터명: <input type='checkbox' class='chapSelect' value='All' />All";
				for(let i = 0; i < data.chapList.length; i++) {
					chapterHtml += "<input type='checkbox' class='chapSelect' value='" + data.chapList[i].sub_chap + "' />" + data.chapList[i].sub_chap;
				}
				chapSelect.append(chapterHtml);
			},
			error: function (data) {
				alert("에러가 발생했습니다.");
			}
		});
	} else {
		chapterHtml += "<input type='checkbox' class='chapSelect' value='All' />All";
		chapSelect.append(chapterHtml);
	}
});

// 등록문제보기 클릭시 저장되어있는 문제 불러오기
$(document).on('click', '#questionSelect', function() {	
	// List에 Map담아서 ajax로 넘기기
	let chapSelectList = [];
	for (let i = 0; i < $(".chapSelect:checked").length; i++) {
		if ($($('.chapSelect:checked')[i]).val() != "All") {			
			let chapSelectMap = {name : $($('.chapSelect:checked')[i]).val()};
			
			chapSelectList.push(chapSelectMap);
		}
	}
	
	let selectedSubject = $("#subjectSelect").val();
	let testNum = $('input[name=test_num]').val();
	
	let param = {
			"paramList" : JSON.stringify(chapSelectList), 
			"selectedSubject" : selectedSubject,
			"test" : testNum
	};
	
	let qnaSelect = $("#qnaSelect");
	qnaSelect.empty();
	let qnaHtml = "";
	
	$.ajax({
		type: "POST", 
		url: "qnaSelectRead", 
		data: param,
		dataType : "json",
		success: function (data) {
			for(let i = 0; i < data.qnaList.length; i++) {
				if(i % 2 == 0) {
					for(let j = 0; j < data.qnaList[i].length; j++) {
						qnaHtml += "<div class='input_list item' draggable='true'>";
						//qnaHtml += "<input type='hidden' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>";
						qnaHtml += "<input type='text' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>";
						qnaHtml += "<br>문제: " + data.qnaList[i][j].question_content + "<br>";
					    let answers = data.qnaList[i+1][j];
					    let formattedAnswers = answers.slice(0, -1).map(function(answer, index) {
					        return (index + 1) + "번: " + answer;
					    }).join(", ");
					    
					    qnaHtml += "보기: " + formattedAnswers;
					    qnaHtml += "<br> 정답: " + answers[answers.length - 1] + "번";
					    qnaHtml += "</div>";
					}
				}
			}
			qnaSelect.append(qnaHtml);
		},
		error: function (data) {
			alert("실패");
		},
	});
});

//All 체크시 전체 선택
$(document).on('change', '.chapSelect[value="All"]', function() {
	if ($(this).is(':checked')) {
		$('.chapSelect:not([value="All"])').prop('checked', true);
	} else {
		$('.chapSelect:not([value="All"])').prop('checked', false);
	}
});

// All 체크시 전체 해제
$(document).on('change', '.chapSelect:not([value="All"])', function() {
	if ($(this).is(':checked')) {
		$('.chapSelect[value="All"]').prop('checked', $('.chapSelect:not([value="All"])').length === $('.chapSelect:not([value="All"]):checked').length);
	} else {
		$('.chapSelect[value="All"]').prop('checked', false);
	}
});

// 드래그 앤 드랍 라이브러리
const columns = document.querySelectorAll(".input_wrap");
columns.forEach((column) => {
	new Sortable(column, {
		group: "shared",
		animation: 150,
		ghostClass: "blue-background-class"
	});
});
</script>
</html>
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
	* {
	    margin: 0;
	    padding: 0;
	    box-sizing: border-box;
	    list-style: none;
	    text-decoration: none;
	}
	
	.container-test {
	    width: 90%;
	    max-width: 1400px;
	    margin: 2rem auto;
	    display: flex;
	    gap: 2rem;
	    padding: 1.5rem;
	    background-color: rgb(52, 58, 64);
	    border-radius: 8px;
	    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	    position: relative;
	}
	
	.test {
	    padding: 1.5rem;
	    background: white;
	    border-radius: 6px;
	    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12);
	}
	
	/* 왼쪽 폼 영역 */
	.test:first-child {
	    width: 45%;
	}
	
	/* 오른쪽 문제 불러오기 영역 */
	.test:last-child {
	    width: 35%;
	    position: fixed;
	    right: 15%;
	    top: 125px;
	    max-height: calc(100vh - 200px);
	    overflow-y: auto;
	}
	
	.input_wrap {
	    width: 100%;
	    height: 1000px;
	    padding: 1rem;
	    background: #ffffff;
	    border: 1px solid #e0e0e0;
	    border-radius: 6px;
	    overflow-y: auto;
	    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.05);
	}
	
	.input_list {
	    width: 100%;
	    margin: 2rem 0;
	    padding: 1rem;
	    background-color: #f8f9fa;
	    border: 1px solid #e9ecef;
	    border-radius: 4px;
	    cursor: move;
	}
	
	.input_list:hover {
	    background-color: #e9ecef;
	}
	
	/* 드래그 중인 항목 스타일 */
	.input_list.sortable-chosen {
	    background-color: #e7f5ff;
	    opacity: 0.8;
	}
	
	/* 드래그 목적지 표시 */
	.input_list.sortable-ghost {
	    background-color: #e7f5ff;
	    border: 2px dashed #4dabf7;
	}
	
	/* 문제 자동 카운터 */
	.list {
	    counter-reset: numbering;
	}
	
	.list .item:before {
	    counter-increment: numbering;
	    content: counter(numbering) "번 문제";
	    margin-right: 10px;
	}
	
	/* 폼 입력 필드 스타일링 */
	.test input[type="text"],
	.test input[type="password"],
	.test select {
	    width: 100%;
	    padding: 0.5rem;
	    margin: 0.5rem 0;
	    border: 1px solid #dee2e6;
	    border-radius: 4px;
	}
	
	/* 라디오 버튼 그룹 스타일링 */
	.test div:has(input[type="radio"]) {
	    margin: 1rem 0;
	    display: flex;
	    gap: 1rem;
	    align-items: center;
	}
	
	/* 제출 버튼 스타일링 */
	.test input[type="submit"],
	.test input[type="button"] {
	    padding: 0.5rem 1rem;
	    background-color: #339af0;
	    color: white;
	    border: none;
	    border-radius: 4px;
	    cursor: pointer;
	    transition: background-color 0.2s;
	}
	
	.test input[type="submit"]:hover,
	.test input[type="button"]:hover {
	    background-color: #228be6;
	}
	
	/* 과목 선택시 생성되는 챕터 체크박스 */
	.chapSelect {
		margin: 0 5px;
	}
</style>
<body>

<%@include file="/WEB-INF/views/member_home.jsp" %>

<div class="container-test">
	<form:form modelAttribute="UpdateTest" action="./testUpdate" class="test">
		<fieldset>
			<div>
				시험 번호: <form:input path="test_num" value="${test.test_num}" readonly="true" />
			</div>		
			<div>			
				작성자: <form:input path="mem_id" value="${test.mem_nickName}" readonly="true" />
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
			<div id="input_wrap" class="input_wrap list serialBox">
				<!-- 시험지 작성 공간 -->
				<c:forEach items="${allQuestion}" var="allQuestion">
					<c:choose>
						<c:when test="${allQuestion.question_id eq 'MCQ'}">
							<div class='input_list item' draggable='true'>
								<input type="hidden" class="serial" name="serial[]" value="${allQuestion.question_serial}">
								<div>문제: ${allQuestion.question_content}</div>
								<c:set var="splitData" value="${fn:split(allQuestion.question_ans, '|★|')}" />        
						            보기: 
						            1번: ${splitData[0]},
						            2번: ${splitData[1]}, 
						            3번: ${splitData[2]}, 
						            4번: ${splitData[3]}
						            <br>
						            정답: ${splitData[4]}번
							</div>
						</c:when>
						<c:when test="${allQuestion.question_id eq 'SAQ'}">
							<div class='input_list item' draggable='true'>
								<input type="hidden" class="serial" name="serial[]" value="${allQuestion.question_serial}">
								<div>문제: ${allQuestion.question_content}</div>
								<div>정답: ${allQuestion.question_ans}</div>
							</div>
						</c:when>
						<c:when test="${allQuestion.question_id eq 'CP'}">
							<div class='input_list item' draggable='true'>
								<input type="hidden" class="serial" name="serial[]" value="${allQuestion.question_serial}">
								<c:set var="splitData" value="${fn:split(allQuestion.question_content, '|★|')}" />
								<div>문제: ${splitData[0]}</div>
								<div><textarea class='answerCheckText' rows='10' cols='50' readonly> 보기: ${splitData[1]}</textarea></div>
								<div>정답: ${allQuestion.question_ans}</div>
							</div>
						</c:when>
						<c:otherwise>
							<div>문제 출력 오류 발생. 관리자에게 문의바랍니다.</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>		
			<div>
				<input type="submit" value="수정하기">
			</div>
		</fieldset>
	</form:form>
	
	<div class="test">
		<p><a href="../Q/main" onclick="window.open(this.href, '_blank', 'width=1000px, height=600px'); return false;">문제추가하기</a>
		<p>기존 문제 불러오기
		<div id="qnaSelect" class="input_wrap list">
			<!-- 문제 불러오기 공간 -->
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
	
	let existingSerials = [];
	let serials = $(".serialBox .serial");
	if(serials != null) {
	    for(let i = 0; i < serials.length; i++) {
	    	let serialMap = {serial : $(serials[i]).val()};
	    	
	        existingSerials.push(serialMap);
	    };
	}
	
	let selectedSubject = $("#subjectSelect").val();
	let testNum = $('input[name=test_num]').val();
	
	let param = {
			"paramList" : JSON.stringify(chapSelectList), 
			"selectedSubject" : selectedSubject,
			"test" : testNum,
			"existingSerials": JSON.stringify(existingSerials)
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
						if(data.qnaList[i][j].question_id === "MCQ") { 
							qnaHtml += "<div class='input_list item' draggable='true'>";
							qnaHtml += "<input type='hidden' class='serial' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>";
							/* qnaHtml += "<input type='text' class='serial' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>"; */
							qnaHtml += "<div>문제: " + data.qnaList[i][j].question_content + "</div>";
						    let answers = data.qnaList[i+1][j];
						    let formattedAnswers = answers.slice(0, -1).map(function(answer, index) {
						        return (index + 1) + "번: " + answer;
						    }).join(", ");
						    qnaHtml += "보기: " + formattedAnswers;
						    qnaHtml += "<div>정답: " + answers[answers.length - 1] + "번</div>";
						    qnaHtml += "</div>";
						} else if(data.qnaList[i][j].question_id === "SAQ") {
							qnaHtml += "<div class='input_list item' draggable='true'>";
							qnaHtml += "<input type='hidden' class='serial' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>";
							/* qnaHtml += "<input type='text' class='serial' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>"; */
							qnaHtml += "<div>문제: " + data.qnaList[i][j].question_content + "</div>";
						    qnaHtml += "<div>정답: " + data.qnaList[i][j].question_ans + "</div>";
						    qnaHtml += "</div>";
						} else if(data.qnaList[i][j].question_id === "CP") {
							qnaHtml += "<div class='input_list item' draggable='true'>";
							qnaHtml += "<input type='hidden' class='serial' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>";
							/* qnaHtml += "<input type='text' class='serial' name='serial[]' value='" + data.qnaList[i][j].question_serial + "'>"; */
							/* qnaHtml += "<div>문제: " + data.qnaList[i][j].question_content + "</div>"; */
							content = data.qnaList[i][j].question_content.split("|★|");
							let formattedContent = content.map(function(content, index) {
								if(index == 0) {
							        return "<div>문제: " + content + "</div>";
								} else {
									return "<p class='answerCheckBox'><textarea class='answerCheckText' rows='10' cols='50' readonly>" + content + "</textarea>";
								}
						    }).join(" ");
							qnaHtml += formattedContent;
						    qnaHtml += "<div>정답: " + data.qnaList[i][j].question_ans + "</div>";
						    qnaHtml += "</div>";
						}
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
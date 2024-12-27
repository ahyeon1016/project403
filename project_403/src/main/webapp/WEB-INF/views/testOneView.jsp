<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

testOneView 페이지
<p><a href="testAll">전체보기</a>
<!-- <p><a href="testStart?Num=${test.test_num}">시험시작하기</a> -->
<br>
<button id="testStart" value="${test.test_num}">시험시작하기</button>
<button id="testEnd" value="${test.test_num}">시험종료하기</button>
<div>
	시험번호: ${test.test_num}
</div>
<div>
	작성자ID: ${test.mem_id}
</div>
<div>
	시험 제목: ${test.test_name}
</div>
<div>
	과목명: ${test.sub_name}
</div>
<div>
	총 문제 갯수: ${fn:length(test.serial)}개
</div>
<div id="questionView">
	시험문제 출력 영역
</div>
<div>
	조회수: ${test.test_hit}
</div>
</body>
<script type="text/javascript">

let dataAllQuestion = [];
let count = 0;

// 시험시작하기 버튼을 눌렀을때 실행되는 함수
$(document).on("click", "#testStart", function() {	
	let questionView = $("#questionView");
	questionView.empty();
	let questionHtml = "";
	let answers = [];
	let param = {test_num : $(this).val()};
	
	$.ajax({
		type: "POST",
		url: "callQuestion", 
		data: param,
		dataType : "json",
		success: function(data) {			
			for(let i = 0; i < data.allQuestion.length; i++) {
				dataAllQuestion = data.allQuestion;
				questionHtml += "<div id='" + data.allQuestion[i].question_serial + "' class='questionSerial'><br>";
				questionHtml += data.allQuestion[i].question_serial + "<br>";
				questionHtml += i+1 + "번 문제 <br>";
				questionHtml += data.allQuestion[i].question_content + "<br>";
				answers = data.allQuestion[i].question_ans.split("|★|");
				let formattedAnswers = answers.slice(0, -1).map(function(answer, index) {
			        return "<input type='radio' name='answer' value='" + (index + 1) + "'>" + answer;
			    }).join(" ");
			    questionHtml += formattedAnswers + "<br>";
			    questionHtml += "<button class='answerCheck' value='" + answers.slice(-1) + "'>정답확인</button><br><br><br><br>";
			    questionHtml += "</div>";
			}
			questionView.append(questionHtml);
			
			for(let i = 0; i < data.allQuestion.length; i++) {
				if(count === i) {
					$("#" + data.allQuestion[i].question_serial).show();
				} else {
					$("#" + data.allQuestion[i].question_serial).hide();
				}
			}
		},
		error: function (data) {
			alert("에러");
		}
	});
});

// 입력된값이 정답인지 확인하는 함수
$(document).on("click", ".answerCheck", function() {
	let answerCheck = $(this).val();
	let answer = $('input[name=answer]:checked').val();
	
	for (let i = 0; i < dataAllQuestion.length; i++) {
		if (dataAllQuestion[i].question_serial == $(this).parents('.questionSerial').attr('id')) {
			dataAllQuestion[i].question_count += 1;
		}
	}
	
	if(answerCheck === answer) {
		alert("정답");
		count += 1;
		$(".questionSerial").hide();
		if(count < dataAllQuestion.length) {
			$("#" + dataAllQuestion[count].question_serial).show();
		} else {
			alert("종료");
		}
	} else {
		count = 0;
		alert("오답");
		$('.questionSerial').hide();
        $("#" + dataAllQuestion[0].question_serial).show();
	}
});

$(document).on("click", "#testEnd", function() {
	alert("시험이 종료됩니다");
	window.location.href = "http://localhost:8080/project_403/test/testAll";
});
</script>
</html>
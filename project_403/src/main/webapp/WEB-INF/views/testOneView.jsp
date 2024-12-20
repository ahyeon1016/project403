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
testOneView 페이지
<p><a href="testAll">전체보기</a>
<!-- <p><a href="testStart?Num=${test.test_num}">시험시작하기</a> -->
<br>
<button id="testStart" value="${test.test_num}">시험시작하기</button>
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

아래에 시험문제 출력 영역
<div id="questionView">
	<c:forEach items="${allQuestion}" var="all" >
		<p>------
		<div>${all.question_content}</div>
		<div>${all.question_ans}</div>
		<p>------
	</c:forEach>
</div>
<div>
	조회수: ${test.test_hit}
</div>
</body>
<script type="text/javascript">
let count = 0;

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
				questionHtml += data.allQuestion[i].question_serial + "<br>";
				questionHtml += i+1 + "번 문제 <br>";
				questionHtml += data.allQuestion[i].question_content + "<br>";
				answers = data.allQuestion[i].question_ans.split("|★|");
				let formattedAnswers = answers.slice(0, -1).map(function(answer, index) {
			        return "<input type='radio' name='answer' value='" + answer + "'>" + answer;			        
			    }).join(" ");
			    questionHtml += formattedAnswers + "<br>";
			    questionHtml += "<button id='answerCheck' value='" + answers.slice(-1) + "'>정답확인</button><br><br><br><br>";
			}
				questionView.append(questionHtml);
		},
		error: function (data) {
			alert("에러");
		}
	});
});

$(document).on("click", "#answerCheck", function() {
	let answerCheck = $("#answerCheck").val();
	let answer = $('input[name=answer]:checked').val();
	if(answerCheck===answer) {
		alert("정답");
	} else {
		alert("오답");
	}
});
</script>
</html>
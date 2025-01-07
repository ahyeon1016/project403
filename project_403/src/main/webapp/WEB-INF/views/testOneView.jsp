<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<meta charset="UTF-8">
	<title>시험지 단일 보기 페이지</title>
</head>
<style>
	* {
	  margin: 0;
	  padding: 0;
	  box-sizing: border-box;
	  list-style: none;
	  text-decoration: none;
	}
	
	/* 컨테이너 스타일링 */
	.container-testOne {
	    max-width: 1000px;
	    margin: 0 auto;
	    padding: 20px;
	    background-color: #fff;
	    border-radius: 10px;
	    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}
	
	.container-testOne > div {
	    margin: 10px 0;
	    padding: 10px 0;
	    border-bottom: 1px solid #eee;
	}
	
	/* 버튼 스타일링 */
	button {
	    padding: 10px 20px;
	    margin: 10px 5px;
	    border: none;
	    border-radius: 10px;
	    cursor: pointer;
	    font-weight: 600;
	    transition: all 0.3s ease;
	}
	
	#testStart {
	    background-color: #28a745;
	    color: white;
	}
	
	#testStart:hover {
	    background-color: #218838;
	}
	
	#testEnd {
	    background-color: #dc3545;
	    color: white;
	}
	
	#testEnd:hover {
	    background-color: #c82333;
	}
	
	/* 문제 영역 스타일링 */
	#questionView {
	    margin: 20px 0;
	    padding: 20px;
	    background-color: rgb(241, 239, 232);
	    border-radius: 8px;
	    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
	}
	
	.questionSerial {
	    padding: 20px;
	    margin: 10px 0;
	    border: 1px solid #e0e0e0;
	    border-radius: 8px;
	    background-color: #fafafa;
	}
	
	.questionSerial > div {
	    margin: 10px 0;
	    padding: 10px 0;
	    border-bottom: 1px solid #eee;
	}
	
	/* 라디오 버튼 스타일링 */
	input[type="radio"] {
	    margin: 10px 5px;
	    cursor: pointer;
	    transform: scale(1.2);
	}
	
	/* 텍스트 입력 필드 스타일링 */
	input[type="text"] {
	    width: 100%;
	    padding: 8px;
	    margin: 10px 0;
	    border: 1px solid #ced4da;
	    border-radius: 4px;
	}
	
	/* 텍스트 입력 필드 스타일링 */
	input[type="text"], textarea {
	    width: 100%;
	    padding: 0.8rem;
	    margin: 0.8rem 0;
	    border: 2px solid #e0e0e0;
	    border-radius: 6px;
	    font-size: 0.95rem;
	    transition: border-color 0.2s ease;
	}
	
	input[type="text"]:focus, textarea:focus {
	    outline: none;
	    border-color: #4CAF50;
	}
	
	/* 정답 확인 버튼 스타일링 */
	.answerCheck, .answerCheckCP {
	    background-color: #007bff;
	    color: white;
	}
	
	.answerCheck:hover, .answerCheckCP:hover {
	    background-color: #0056b3;
	}
	
	/* 코드 문제 텍스트영역 스타일링 */
	.answerCheckText {
	    font-family: 'Consolas', monospace;
	    background-color: #f8f9fa;
	    resize: vertical;
	    min-height: 200px;
	}
</style>
<body>
<%@include file="/WEB-INF/views/member_home.jsp" %>

<div class="container-testOne">
	<button id="testStart" value="${test.test_num}">시험시작하기</button>
	<button id="testEnd" value="${test.test_num}">시험종료하기</button>
	<div>
		시험번호: ${test.test_num}
	</div>
	<div>
		작성자: ${test.mem_nickName}
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
		<!-- 시험문제 출력 영역 -->
	</div>
	<div>
		조회수: ${test.test_hit}
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp" %>
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
			dataAllQuestion = data.allQuestion;
			
			resetQuestion();
		},
		error: function (data) {
			alert("에러");
		}
	});
});

// 문제 생성 이벤트
function resetQuestion() {
	let questionView = $("#questionView");
	questionView.empty();
	let questionHtml = "";
	
	for(let i = 0; i < dataAllQuestion.length; i++) {
		// 객관식
		if(dataAllQuestion[i].question_id === "MCQ") { 
			questionHtml += "<div id='" + dataAllQuestion[i].question_serial + "' class='questionSerial'>";
			/* questionHtml += dataAllQuestion[i].question_serial + "<br>"; */
			questionHtml += "<div>문제 풀이 횟수: " + dataAllQuestion[i].question_count + " 회</div>";
			questionHtml += "<div>" + (i+1) + "번 문제</div>";
			questionHtml += "<div>" + dataAllQuestion[i].question_content + "</div>";
			answers = dataAllQuestion[i].question_ans.split("|★|");
			let formattedAnswers = answers.slice(0, -1).map(function(answer, index) {
		        return "<input type='radio' name='" + dataAllQuestion[i].question_serial + "' value='" + (index + 1) + "'>" + answer + "<br>";
		    }).join(" ");
		    questionHtml += "<div>" + formattedAnswers + "</div>"; 
		    questionHtml += "<button class='answerCheck' value='" + answers.slice(-1) + "'>정답확인</button><br><br><br><br>";
		    questionHtml += "</div>";
		// 주관식
		} else if(dataAllQuestion[i].question_id === "SAQ") {
			questionHtml += "<div id='" + dataAllQuestion[i].question_serial + "' class='questionSerial'>";
			/* questionHtml += dataAllQuestion[i].question_serial + "<br>"; */
			questionHtml += "<div>문제 풀이 횟수: " + dataAllQuestion[i].question_count + " 회</div>";
			questionHtml += "<div>" + (i+1) + "번 문제</div>";
			questionHtml += "<div>" + dataAllQuestion[i].question_content + "</div>";
			questionHtml += "<input type='text' name='" + dataAllQuestion[i].question_serial + "'>";
			questionHtml += "<button class='answerCheck' value='" + dataAllQuestion[i].question_ans + "'>정답확인</button><br><br><br><br>";
			questionHtml += "</div>";
		// 코드 문제	
		} else if(dataAllQuestion[i].question_id === "CP") {
			questionHtml += "<div id='" + dataAllQuestion[i].question_serial + "' class='questionSerial'>";
			/* questionHtml += dataAllQuestion[i].question_serial + "<br>"; */
			questionHtml += "<div>문제 풀이 횟수: " + dataAllQuestion[i].question_count + " 회</div>";
			questionHtml += "<div>" + (i+1) + "번 문제</div>";
			content = dataAllQuestion[i].question_content.split("|★|");
			let formattedContent = content.map(function(content, index) {
				if(index == 0) {
			        return "<div>" + content + "</div>";
				} else {
					return "<p class='answerCheckBox'><textarea class='answerCheckText' rows='15' cols='70' id='" + dataAllQuestion[i].question_serial + "'>" + content + "</textarea>";
				}
		    }).join(" ");
			questionHtml += formattedContent + "<br>";
			questionHtml += "<button class='answerCheckCP' value='" + dataAllQuestion[i].question_ans + "'>정답확인</button><br><br><br><br>";
			questionHtml += "</div>";
		}
		
	}
	questionView.append(questionHtml);
	
	showQuestion(0);
}

// 특정 문제를 보여주는 함수
function showQuestion(index) {
    $(".questionSerial").hide();
    if (index < dataAllQuestion.length) {
        $("#" + dataAllQuestion[index].question_serial).show();
    }
}

//입력된값이 정답인지 확인하는 함수: MCQ, SAQ
$(document).on("click", ".answerCheck", function() {
    let currentSerial = $(this).parents('.questionSerial').attr('id');
    let answerCheck = $(this).val();
    let answer;
    
    // 현재 문제의 타입에 따라 답안 가져오기
    if ($("input[name='" + currentSerial + "']:checked").length > 0) {
        answer = $("input[name='" + currentSerial + "']:checked").val();
    } else {
        answer = $("input[name='" + currentSerial + "']").val();
    }
    
    // 시도 횟수 증가
    for (let i = 0; i < dataAllQuestion.length; i++) {
        if (dataAllQuestion[i].question_serial == currentSerial) {
            dataAllQuestion[i].question_count += 1;
        }
    }
    
 	// 남은 문제 숫자
    let remainingQuestions = dataAllQuestion.length - (count + 1);
    
    // 정답 or 오답 체크
    if(answerCheck === answer) {
    	count += 1;
        if(count < dataAllQuestion.length) {
	        if (confirm("정답! 다음 문제로 이동 하시겠습니까? \n남은 문제: " + remainingQuestions + "개") == true){
		        showQuestion(count);
	        }
        } else {
        	alert("정답입니다. \n시험이 종료되었습니다. 수고하셨습니다.");
            window.location.reload();
        }
    } else {
        alert("오답입니다. 1번 문제로 돌아갑니다.");
        count = 0;
		
		resetQuestion();
		
        showQuestion(0);
    }
});

//입력된값이 정답인지 확인하는 함수: CP
$(document).on("click", ".answerCheckCP", function() {
	let currentSerial = $(this).parents(".questionSerial").attr("id");
	let answerCP = $(this).parents('.answerCheckBox').find('.answerCheckText').val();
	let answerCheckCP = $(this).val();
	
	// 시도 횟수 증가
    for (let i = 0; i < dataAllQuestion.length; i++) {
        if (dataAllQuestion[i].question_serial == currentSerial) {
            dataAllQuestion[i].question_count += 1;
        }
    }
	
 	// 남은 문제 숫자
    let remainingQuestions = dataAllQuestion.length - (count + 1);
	
	// 코드 문제 컴파일 정답확인 AJAX
	$.ajax({
		url : "../Q/Compile",
		type : "POST",
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify({"ans_input" : answerCP}),
		success : function(data){
			if(data.output === answerCheckCP) {
				count += 1;
		        if(count < dataAllQuestion.length) {
			        if (confirm("정답! 다음 문제로 이동 하시겠습니까? \n남은 문제: " + remainingQuestions + "개") == true){
				        showQuestion(count);
			        }
		        } else {
		        	alert("정답입니다. \n시험이 종료되었습니다. 수고하셨습니다.");
		            window.location.reload();
		        }
		    } else {
		        alert("오답. 1번 문제로 돌아갑니다.");
		        count = 0;
				
				resetQuestion();
				
		        showQuestion(0);
		    }
		},
		error : function(data){
			console.log("오류");
		}
	});
});

// 시험 종료 버튼 이벤트
$(document).on("click", "#testEnd", function() {
	alert("시험이 종료됩니다");
	window.location.href = "testAll";
});
</script>
</html>
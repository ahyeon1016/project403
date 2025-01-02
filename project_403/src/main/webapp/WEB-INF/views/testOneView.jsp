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
	
	/* 버튼 스타일링 */
	button {
	    padding: 10px 20px;
	    margin: 10px 5px;
	    border: none;
	    border-radius: 5px;
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
	    background-color: #fff;
	    border-radius: 8px;
	    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
	}
	
	.questionSerial {
	    padding: 20px;
	    margin: 10px 0;
	    border: 1px solid #dee2e6;
	    border-radius: 8px;
	}
	
	/* 라디오 버튼 스타일링 */
	input[type="radio"] {
	    margin: 10px 5px;
	    cursor: pointer;
	}
	
	/* 텍스트 입력 필드 스타일링 */
	input[type="text"] {
	    width: 100%;
	    padding: 8px;
	    margin: 10px 0;
	    border: 1px solid #ced4da;
	    border-radius: 4px;
	}
	
	/* 정답 확인 버튼 스타일링 */
	.answerCheck {
	    background-color: #007bff;
	    color: white;
	}
	
	.answerCheck:hover {
	    background-color: #0056b3;
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
			for(let i = 0; i < data.allQuestion.length; i++) {
				if(data.allQuestion[i].question_id === "MCQ") { 
					dataAllQuestion = data.allQuestion;
					questionHtml += "<div id='" + data.allQuestion[i].question_serial + "' class='questionSerial'><br>";
					questionHtml += data.allQuestion[i].question_serial + "<br>";
					questionHtml += i+1 + "번 문제 <br>";
					questionHtml += data.allQuestion[i].question_content + "<br>";
					answers = data.allQuestion[i].question_ans.split("|★|");
					let formattedAnswers = answers.slice(0, -1).map(function(answer, index) {
				        return "<input type='radio' name='" + data.allQuestion[i].question_serial + "' value='" + (index + 1) + "'>" + answer;
				    }).join(" ");
				    questionHtml += formattedAnswers + "<br>";
				    questionHtml += "<button class='answerCheck' value='" + answers.slice(-1) + "'>정답확인</button><br><br><br><br>";
				    questionHtml += "</div>";
				} else if(data.allQuestion[i].question_id === "SAQ") {
					dataAllQuestion = data.allQuestion;
					questionHtml += "<div id='" + data.allQuestion[i].question_serial + "' class='questionSerial'><br>";
					questionHtml += data.allQuestion[i].question_serial + "<br>";
					questionHtml += i+1 + "번 문제 <br>";
					questionHtml += data.allQuestion[i].question_content + "<br>";
					questionHtml += "<input type='text' name='" + data.allQuestion[i].question_serial + "'>";
					questionHtml += "<button class='answerCheck' value='" + data.allQuestion[i].question_ans + "'>정답확인</button><br><br><br><br>";
					questionHtml += "</div>";
				} else if(data.allQuestion[i].question_id === "CP") {
					dataAllQuestion = data.allQuestion;
					questionHtml += "<div id='" + data.allQuestion[i].question_serial + "' class='questionSerial'><br>";
					questionHtml += data.allQuestion[i].question_serial + "<br>";
					questionHtml += i+1 + "번 문제 <br>";
					content = data.allQuestion[i].question_content.split("|★|");
					let formattedContent = content.map(function(content, index) {
						if(index == 0) {
					        return content;
						} else {
							return "<p><textarea rows='15' cols='70' id='answerCP'>" + content + "</textarea>";
						}
				    });
					questionHtml += formattedContent + "<br>";
					questionHtml += "<button class='answerCheckCP' value='" + data.allQuestion[i].question_ans + "'>정답확인</button><br><br><br><br>";
					questionHtml += "</div>";
				}
				
			}
			questionView.append(questionHtml);
			
			showQuestion(0);
			
			// 정답 오답에 따라 다음문제보기 처음으로돌아가기
			/* for(let i = 0; i < data.allQuestion.length; i++) {
				if(count === i) {
					$("#" + data.allQuestion[i].question_serial).show();
				} else {
					$("#" + data.allQuestion[i].question_serial).hide();
				}
			} */
		},
		error: function (data) {
			alert("에러");
		}
	});
});

//특정 문제를 보여주는 함수
function showQuestion(index) {
    $('.questionSerial').hide();
    if (index < dataAllQuestion.length) {
        $("#" + dataAllQuestion[index].question_serial).show();
    }
}

// 입력된값이 정답인지 확인하는 함수
/* $(document).on("click", ".answerCheck", function() {
	let answerCheck = $(this).val();
	let answer = null;
	if($("input[name=answer]:checked").length > 0) {
		answer = $("input[name=answer]:checked").val();
	} else {
		answer = $("input[name=answer]").val();
	}
	
	for (let i = 0; i < dataAllQuestion.length; i++) {
		if (dataAllQuestion[i].question_serial == $(this).parents('.questionSerial').attr('id')) {
			dataAllQuestion[i].question_count += 1;
		}
	}
	
	if(answerCheck === answer) {
		alert("정답");
		count += 1;
		$(".questionSerial").hide();
		$('input[type="radio"]').prop('checked', false);
		$('input[type="text"]').val('');
		if(count < dataAllQuestion.length) {
			$("#" + dataAllQuestion[count].question_serial).show();
		} else {
			alert("종료");
		}
	} else {
		count = 0;
		alert("오답");
		$('.questionSerial').hide();
		$('input[type="radio"]').prop('checked', false);
		$('input[type="text"]').val('');
        $("#" + dataAllQuestion[0].question_serial).show();
	}
}); */

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
    
    // 정답 or 오답 체크
    if(answerCheck === answer) {
        alert("정답");
        count += 1;
        if(count < dataAllQuestion.length) {
            showQuestion(count);
        } else {
            alert("종료");
            window.location.reload();
        }
    } else {
        alert("오답");
        count = 0;
        $('input[type="radio"]').prop('checked', false);
		$('input[type="text"]').val('');
        showQuestion(0);
    }
});

//입력된값이 정답인지 확인하는 함수: CP
$(document).on("click", ".answerCheckCP", function() {
	let currentSerial = $(this).parents('.questionSerial').attr('id');
	let answerCP = $("#answerCP").val();
	let answerCheckCP = $(this).val();
	
	// 시도 횟수 증가
    for (let i = 0; i < dataAllQuestion.length; i++) {
        if (dataAllQuestion[i].question_serial == currentSerial) {
            dataAllQuestion[i].question_count += 1;
        }
    }
	
	$.ajax({
		url : "../Q/Compile",
		type : "POST",
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify({"ans_input" : answerCP}),
		success : function(data){
			if(data.output === answerCheckCP) {
		        alert("정답");
		        count += 1;
		        if(count < dataAllQuestion.length) {
		            showQuestion(count);
		        } else {
		            alert("종료");
		            window.location.reload();
		        }
		    } else {
		        alert("오답");
		        count = 0;
		        $('input[type="radio"]').prop('checked', false);
				$('input[type="text"]').val('');
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
	window.location.href = "http://localhost:8080/project_403/test/testAll";
});
</script>
</html>
let sub_name = document.querySelector("#name_selector");
let sub_chap = document.querySelector("#chap_selector");
sub_name.addEventListener("change", check);

/* 문제를 만드는 페이지로 이동, 파라미터 값을 통해 다른 페이지로 이동 */
function addQuestion(type){
	window.location.href="Q_add"+type;
}

/* 과목 목록을 가져온다. */
function check(){
	let sub_name_value = sub_name.value;
	console.log(sub_name_value);
	$.ajax({
		url : "Q_subNameByChap",
		type : "POST",
		contentType : "application/json",
		data : JSON.stringify({
			"sub_name" : sub_name_value
		}),
		success : function(data){
			let list = data.list;
			/* 자식요소 초기화 */
			sub_chap.replaceChildren();
			/* option 태그 안에 가져온 데이터를 넣어 select 태그의 자식요소로 삽입 */
			for(let i=0; i<list.length; i++){
				let option = document.createElement('option');
				console.log(list[i]);
				option.text = list[i];
		        sub_chap.appendChild(option);
			}
		},
		error : function(){
			arlet("데이터를 받아오지 못했습니다.");
		}
	});
}

/* 객관식 문제 생성시 유효성 검사 */
function validateMCQForm(event) {
    const nameSelect = document.getElementById("name_selector");
    const questionContent = document.querySelector('textarea[name="question_content"]');
    const answerInputs = document.querySelectorAll('input[name="question_ans"]');
    const selectedAnswers = document.querySelectorAll('input[name="question_ans"]:checked');
	const questionLevel = document.querySelectorAll('input[name="question_level"]:checked');
	const imageInput = document.getElementById("imageInput");
    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
	
	/* 과목명 선택 검사 */
    if (nameSelect.value === "선택") {
        alert("과목명을 선택해야 합니다.");
        event.preventDefault();
        return false;
    }
	
	/* 문제 내용 입력 검사 */
    if (questionContent.value.trim() === "") {
        alert("문제 내용을 입력해야 합니다.");
        event.preventDefault();
        return false;
    }
	
	/* 선택지 입력 검사 */
    let hasAnswer = false;
    answerInputs.forEach(input => {
        if (input.value.trim() === "") {
            alert("모든 선택지를 입력해야 합니다.");
            event.preventDefault();
            return false;
        }
        if (input.value.trim() !== "") {
            hasAnswer = true;
        }
    });
	
    if (!hasAnswer) {
		alert("최소 하나의 선택지를 입력해야 합니다.");
        event.preventDefault();
        return false;
    }
	
	/* 정답 선택 검사 */
    if (selectedAnswers.length === 0) {
        alert("정답을 선택해야 합니다.");
        event.preventDefault();
        return false;
    }
	
	// 이미지 파일 확장자 검사
    if (imageInput.files.length > 0) {
        const filePath = imageInput.value;
        if (!allowedExtensions.exec(filePath)) {
            alert("유효한 이미지 파일만 업로드할 수 있습니다. (jpg, jpeg, png)");
            imageInput.value = ''; // 입력값 초기화
            event.preventDefault();
            return false;
        }
    }
	
	/* 난이도 설정 검사 */
	if(questionLevel.length===0){
		alert("난이도를 선택해야 합니다.");
        event.preventDefault();
        return false;
	}

}

/* 주관식 문제 생성시 유효성 검사 */
function validateSAQForm(event) {
    const nameSelect = document.getElementById("name_selector");
    const questionContent = document.querySelector('textarea[name="question_content"]');
    const answerInput = document.querySelector('input[name="question_ans"]'); // 주관식 답안
    const questionLevel = document.querySelectorAll('input[name="question_level"]:checked');
    const imageInput = document.getElementById("imageInput"); // 이미지 입력
    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
	
    // 과목명 검사
    if (nameSelect.value === "선택") {
        alert("과목명을 선택해야 합니다.");
        event.preventDefault();
        return false;
    }

    // 문제 내용 검사
    if (questionContent.value.trim() === "") {
        alert("문제 내용을 입력해야 합니다.");
        event.preventDefault();
        return false;
    }

    // 정답 입력 검사 (주관식 문제에서 정답은 하나의 입력으로 처리)
    if (answerInput.value.trim() === "") {
        alert("정답을 입력해야 합니다.");
        event.preventDefault();
        return false;
    }

    // 이미지 파일 확장자 검사
    if (imageInput.files.length > 0) {
        const filePath = imageInput.value;
        if (!allowedExtensions.exec(filePath)) {
            alert("유효한 이미지 파일만 업로드할 수 있습니다. (jpg, jpeg, png)");
            imageInput.value = ''; // 입력값 초기화
            event.preventDefault();
            return false;
        }
    }

    // 문제 난이도 검사
    if (questionLevel.length === 0) {
        alert("난이도를 선택해야 합니다.");
        event.preventDefault();
        return false;
    }

    return true; // 모든 유효성 검사 통과
}

/* 코딩 문제 생성시 유효성 검사 */
function validateCPForm(event) {
    const nameSelect = document.getElementById("name_selector");
    const questionContent = document.querySelector('textarea[name="question_content_text"]');
    const codeContent = document.querySelector('textarea[name="question_content"]');
    const answerInput = document.querySelector('input[name="question_ans"]'); // 정답 입력
    const questionLevel = document.querySelectorAll('input[name="question_level"]:checked');
    const imageInput = document.getElementById("imageInput"); // 이미지 입력
    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

    // 과목명 검사
    if (nameSelect.value === "선택") {
        alert("과목명을 선택해야 합니다.");
        event.preventDefault();
        return false;
    }

    // 문제 내용 검사
    if (questionContent.value.trim() === "") {
        alert("문제 내용을 입력해야 합니다.");
        event.preventDefault();
        return false;
    }

    // 코드 내용 검사
    if (codeContent.value.trim() === "") {
        alert("코드 내용을 입력해야 합니다.");
        event.preventDefault();
        return false;
    }

    // 정답 입력 검사
    if (answerInput.value.trim() === "") {
        alert("정답을 입력해야 합니다.");
        event.preventDefault();
        return false;
    }

    // 이미지 파일 확장자 검사
    if (imageInput.files.length > 0) {
        const filePath = imageInput.value;
        if (!allowedExtensions.exec(filePath)) {
            alert("유효한 이미지 파일만 업로드할 수 있습니다. (jpg, jpeg, png)");
            imageInput.value = ''; // 입력값 초기화
            event.preventDefault();
            return false;
        }
    }

    // 문제 난이도 검사
    if (questionLevel.length === 0) {
        alert("난이도를 선택해야 합니다.");
        event.preventDefault();
        return false;
    }
	
    return true; // 모든 유효성 검사 통과
}
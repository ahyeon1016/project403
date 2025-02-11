function validateMCQForm(event) {
    const questionContent = document.querySelector('textarea[name="question_content"]');
    const answerInputs = document.querySelectorAll('input[name="question_ans"]');
    const selectedAnswers = document.querySelectorAll('input[name="question_ans"]:checked');
	const questionLevel = document.querySelectorAll('input[name="question_level"]:checked');
	const imageInput = document.getElementById("imageInput");
    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

    if (questionContent.value.trim() === "") {
        alert("문제 내용을 입력해야 합니다.");
        event.preventDefault();
        return false;
    }
	//
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

	if(questionLevel.length===0){
		alert("난이도를 선택해야 합니다.");
        event.preventDefault();
        return false;
	}

}

function validateSAQForm(event) {
    const questionContent = document.querySelector('textarea[name="question_content"]');
    const answerInput = document.querySelector('input[name="question_ans"]'); // 주관식 답안
    const questionLevel = document.querySelectorAll('input[name="question_level"]:checked');
    const imageInput = document.getElementById("imageInput"); // 이미지 입력
    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

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

function validateCPForm(event) {
    const questionContent = document.querySelector('textarea[name="question_content_text"]');
    const codeContent = document.querySelector('textarea[name="question_content"]');
    const answerInput = document.querySelector('input[name="question_ans"]'); // 정답 입력
    const questionLevel = document.querySelectorAll('input[name="question_level"]:checked');
    const imageInput = document.getElementById("imageInput"); // 이미지 입력
    const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

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

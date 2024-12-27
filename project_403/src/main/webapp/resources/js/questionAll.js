let sub_name = document.querySelector("#name_selector");
let sub_chap = document.querySelector("#chap_selector");
let question_container = document.querySelector("#question_container");

/* 이벤트 */
sub_chap.addEventListener("change", chapSearch);
sub_name.addEventListener("change", nameSearch);

// 과목 선택 시 챕터을 로드하는 AJAX 요청
function nameSearch() {
    let sub_name_value = sub_name.value;
    console.log("선택된 과목:", sub_name_value);
    
    $.ajax({
        url: "Q_subNameByChap",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ "sub_name": sub_name_value }),
        success: function(data) {
            let list = data.list;
            sub_chap.innerHTML = ""; 
            let choice = document.createElement("option");
            choice.text = "선택";
            sub_chap.appendChild(choice);
            list.forEach(item => {
                let option = document.createElement("option");
                option.text = item; 
                sub_chap.appendChild(option);
            });
        },
        error: function() {
            alert("데이터를 받아오지 못했습니다.");
        }
    });
}

// 챕터 선택 시 문제를 로드하는 AJAX 요청
function chapSearch() {
    let name_value = sub_name.value;
    let chap_value = sub_chap.value;
    console.log("선택된 과목과 챕터:", name_value, chap_value);
    
    $.ajax({
        url: "Q_search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ "name": name_value, "chap": chap_value }),
        success: function(data) {
            console.log("문제 검색 성공");
            question_container.innerHTML = ""; 
            let questions = data.question; 
            questions.forEach(question => {
                let div = document.createElement("div");
                div.classList.add(question.question_id, "question_div");
                div.innerHTML = `
                    <p>작성자명: ${question.mem_nickName}</p>
                    <p>문제 난이도: ${question.question_level}</p>
                    <p>${question.question_id}</p>
                    <p>${question.sub_code_sum}</p>
                    <p>${question.question_serial}</p>
                    <a href='Q_read${question.question_id}/${question.question_serial}'>문제 보기</a> | 
                    <a href='Q_update${question.question_id}/${question.question_serial}'>문제 수정하기</a> | 
                    <a href='Q_delete/${question.question_serial}'>문제 삭제하기</a>
                    <hr>`;
                question_container.appendChild(div);
            });
        },
        error: function() {
            console.log("문제 검색 실패");
        }
    });
}

// 문제 유형에 따른 필터링
let question_id = document.querySelectorAll(".question_id");
question_id.forEach(qId => {
    qId.addEventListener("click", change_id);
});

function change_id() {
    let id = this.value; 
    let question_div_all = document.querySelectorAll(".question_div");
    console.log("문제유형:", id);
    
    if (id === "ALL") {
        question_div_all.forEach(div => div.style.display = "block");
    } else {
        question_div_all.forEach(div => div.style.display = "none");
        let question_div = document.querySelectorAll(`.${id}`);
        question_div.forEach(div => div.style.display = "block");
    }
}

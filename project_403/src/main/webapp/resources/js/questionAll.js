let sub_name = document.querySelector("#name_selector");
let sub_chap = document.querySelector("#chap_selector");
let question_container = document.querySelector("#question_container");
let myQuestion = document.querySelector("#myQuestion");
let question_id = document.querySelectorAll(".question_id");

for(let i=0; i<question_id.length; i++){
	question_id[i].addEventListener("change", chapSearch);
}
sub_name.addEventListener("change", nameSearch);
sub_chap.addEventListener("change", chapSearch);
myQuestion.addEventListener("change", resetSub);

function nameSearch(){
	let sub_name_value = sub_name.value;
	question_id[0].checked = true;
	question_id[0].value = "ALL";
	for(let i=0; i<question_id.length; i++){
		question_id[i].setAttribute("disabled", true);
	}
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
			sub_chap.replaceChildren();
			let choice = document.createElement("option");
			choice.text = "선택";
			sub_chap.appendChild(choice);
			for(let i=0; i<list.length; i++){
				let option = document.createElement("option");
				console.log(list[i]);
				option.text = list[i];
		        sub_chap.appendChild(option);
			}
		},
		error : function(){
			alert("데이터를 받아오지 못했습니다.");
		}
	});
}

function chapSearch(){
	let	name_value = sub_name.value;
	let chap_value = sub_chap.value;
	let page_div = document.querySelector("#question_paging");
	let page = document.querySelector(".page_btn");
	let id = '';
	for(let i=0; i<question_id.length; i++){
		if(question_id[i].checked){
			id=question_id[i].value;
		}
	}
	console.log(id);
	
	/* 비활성화 해제 */
	if(!(name_value=="선택"||chap_value=="선택")){
		for(let i=0; i<question_id.length; i++){
			question_id[i].disabled=false;	
		}
	}
	
	
	/*처음으로 함수를 호출하면 page 변수에는 아무 값도 없기 때문에 값을 지정해줌*/
	if(page == null) {
	    console.log(page);
	    page = 1;
	}else if (event.target.classList.contains("page_btn")) { // 페이지 버튼인지 확인
	    console.log(page);
	    page = parseInt(event.target.textContent); // 페이지 버튼의 텍스트를 숫자로 변환
	    page_div.replaceChildren();
	}else {
	    console.log(page);
	    page = 1; // 챕터나 문제 유형 버튼을 선택했을 때
	    page_div.replaceChildren();
	}
	console.log(page); 
	console.log(name_value+"|"+chap_value);
	$.ajax({
		url : "Q_search",
		type : "POST",
		contentType : "application/json",
		data : JSON.stringify({
			"name" : name_value,
			"chap" : chap_value,
			"myQuestion" : myQuestion.checked,
			"page" : page,
			"id" : id
		}),
		success : function(data){
			console.log("성공");
			console.log("최대 페이지의 갯수: "+data.totalPage);
			console.log("최대 인덱스의 개수: "+data.maxIndex);
			console.log("최소 인덱스의 개수: "+data.minIndex);
			let totalPage = data.totalPage;
			let maxIndex = data.maxIndex;
			let minIndex = data.minIndex;
			/*모든 자식 요소를 제거한다.*/ 
			question_container.replaceChildren();
			let question = data.question;
			if(question.length!=0){
				console.log("문제의 갯수: "+data.question.length);
				/* 가져온 question을 view에 뿌리는 작업 */
				for(let i=minIndex; i<maxIndex; i++){
					let div = document.createElement("div");
					div.classList.add(question[i].question_id, "question_div");
					div.innerHTML = 
						"<p>작성자명 : "+question[i].mem_nickName+"</p>"+
						"<p>문제 난이도 : "+question[i].question_level+"단계</p>"+
						"<p>"+question[i].question_id+"</p>"+
						"<a href='Q_read"+question[i].question_id+
						"/"+question[i].question_serial+"'>문제 보기</a> | "+
						"<a href='Q_update"+question[i].question_id+
						"/"+question[i].question_serial+"'>문제 수정 하기</a> | "+
						"<a href='Q_delete"+"/"+question[i].question_serial+"'>문제 삭제 하기</a>"+
						"<hr>";
						
					question_container.append(div);
					console.log(div);
				}
				
				for(let i=0; i<totalPage; i++){
					let btn = document.createElement("button");
					btn.classList.add("page_btn");
					btn.textContent=i+1;
					btn.addEventListener("click", chapSearch);
									
					page_div.append(btn);
				}
			}else if(name_value=="선택"||chap_value=="선택"){
				let div = document.createElement("div");
				div.textContent="과목과 챕터를 선택 해주세요.";
				question_container.append(div);
			}else{
				let div = document.createElement("div");
				div.textContent="과목과 챕터, 문제유형에 해당하는 문제가 없습니다.";
				question_container.append(div);
			}
		},
		error : function(){
			console.log("실패");
		}
	});
}

function resetSub(){
	sub_name.value = "선택";
	sub_chap.value = "선택";
	
	question_id[0].checked = true;
	event.target.value = "ALL";
	for(let i=0; i<question_id.length; i++){
		question_id[i].setAttribute("disabled", true);
	}
	
	nameSearch();
	chapSearch();
}
let sub_name = document.querySelector("#name_selector");
let sub_chap = document.querySelector("#chap_selector");
let question_container = document.querySelector("#question_container");
let myQuestion = document.querySelector("#myQuestion");
let question_id = document.querySelectorAll(".question_id");

for(let i=0; i<question_id.length; i++){
	question_id[i].addEventListener("change", change_id);
}
sub_name.addEventListener("change", nameSearch);
sub_chap.addEventListener("change", chapSearch);
myQuestion.addEventListener("change", resetSub);

function nameSearch(){
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
	
	/*처음으로 함수를 호출하면 page 변수에는 아무 값도 없기 때문에 값을 지정해줌*/
	if(page==null){
		page=1;
	}else if(isNaN(event.target.textContent)){	/*페이징 버튼이 아닌 챕터를 선택 했을 때 실행*/
		page=1;
		page_div.replaceChildren();
	}else {
		/*페이징 버튼의 텍스트를 page변수에 대입*/
		page = parseInt(event.target.textContent);
		console.log(page);
		page_div.replaceChildren();
	}
	
	console.log(name_value+"|"+chap_value);
	$.ajax({
		url : "Q_search",
		type : "POST",
		contentType : "application/json",
		data : JSON.stringify({
			"name" : name_value,
			"chap" : chap_value,
			"myQuestion" : myQuestion.checked,
			"page" : page
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
			console.log("문제의 갯수: "+data.question.length);
			/* 가져온 question을 view에 뿌리는 작업 */
			for(let i=minIndex; i<maxIndex; i++){
				let div = document.createElement("div")
				div.classList.add(question[i].question_id, "question_div");
				div.innerHTML = 
					"<p>작성자명 : "+question[i].mem_nickName+"</p>"+
					"<p>문제 난이도 : "+question[i].question_level+"</p>"+
					"<p>"+question[i].question_id+"</p>"+
					"<p>"+question[i].sub_code_sum+"</p>"+
					"<p>"+question[i].question_serial+"</p>"+
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
			
		},
		error : function(){
			console.log("실패");
		}
	});
}



function change_id(){
	let id = event.target.value;
	let question_div_all = document.querySelectorAll(".question_div");
	console.log(id);
	console.log("챕터에 존재하는 문제의 갯수 : "+question_div_all.length);
	if(id=="ALL"){
		console.log("all");
		for(let i=0; i<question_div_all.length; i++){
			question_div_all[i].style.display = "block";
		}
	}else if(id!="ALL"){
		let question_div = document.querySelectorAll("."+id); 
		console.log("문제 유형과 일치하는 문제의 갯수 : "+question_div.length);
		for(let i=0; i<question_div_all.length; i++){
			question_div_all[i].style.display = "none";
		}
		for(let i=0; i<question_div.length; i++){
			question_div[i].style.display="block";	
		}
	}
}

function resetSub(){
	sub_name.value = "선택";
	sub_chap.value = "선택";
	
	nameSearch();
	chapSearch();
	
	question_id[0].checked = true;
	event.target.value = "ALL";
	change_id();
	
}
let sub_name = document.querySelector("#name_selector");
let sub_chap = document.querySelector("#chap_selector");
let myQuestion = document.querySelector("#myQuestion");
sub_name.addEventListener("change", nameSearch);
myQuestion.addEventListener("change", selectMyQuestion);
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


let question_container = document.querySelector("#question_container");
sub_chap.addEventListener("change", chapSearch);
function chapSearch(){
	let	name_value = sub_name.value;
	let chap_value = sub_chap.value;
	console.log(name_value+"|"+chap_value);
	$.ajax({
		url : "Q_search",
		type : "POST",
		contentType : "application/json",
		data : JSON.stringify({
			"name" : name_value,
			"chap" : chap_value
		}),
		success : function(data){
			console.log("성공");
			console.log(data.question);
			question_container.replaceChildren();
			let question = data.question;
			for(let i=0; i<question.length; i++){
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
		},
		error : function(){
			console.log("실패");
		}
	});
}

let question_id = document.querySelectorAll(".question_id");
for(let i=0; i<question_id.length; i++){
	question_id[i].addEventListener("click", change_id);
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

function selectMyQuestion(){
	let serial = event.target.value;
	console.log(serial);
}

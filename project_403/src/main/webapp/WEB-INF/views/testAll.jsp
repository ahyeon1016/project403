<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<link rel="icon" href="data:;base64,iVBORw0KGgo=">
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
	
	.container-testAll {
		width: 90%;
		margin: 0 auto;
	}
	
	.txt {
	    padding: 8px;
	    border: 1px solid #ddd;
	    border-radius: 4px;
	    margin-right: 10px;
	}
	
	input[type="text"] {
	    padding: 8px;
	    border: 1px solid #ddd;
	    border-radius: 4px;
	    width: 200px;
	}
	
	#btnAdd {
	    padding: 8px 16px;
	    background-color: #4C5317;
	    color: white;
	    border: none;
	    border-radius: 4px;
	    cursor: pointer;
	    transition: background-color 0.3s;
	}
	
	#btnAdd:hover {
	    background-color: #5c6420;
	}
	
	/* Test list styles */
	h3 {
	    color: #4C5317;
	    margin: 10px 0;
	}
	
	div[style*="border: 1px solid black"] {
	    background-color: white;
	    border: 1px solid #ddd !important;
	    border-radius: 8px;
	    padding: 20px;
	    margin-bottom: 20px;
	    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
	    transition: transform 0.2s;
	}
	
	div[style*="border: 1px solid black"]:hover {
	    transform: translateY(-2px);
	}
	
	/* Test item details */
	p {
	    margin: 8px 0;
	    color: #333;
	}
	
	button {
	    background: none;
	    border: none;
	    color: #2b5bae;
	    cursor: pointer;
	    font-size: 1em;
	    padding: 0;
	    text-decoration: underline;
	}
	
	button:hover {
	    color: #1a3c7d;
	}
	
	/* Links */
	a {
	    color: #4C5317;
	    text-decoration: none;
	    margin-right: 15px;
	}
	
	a:hover {
	    text-decoration: underline;
	}
	
	/* Pagination */
	div[align="center"] {
	    margin-top: 30px;
	    padding: 20px 0;
	}
	
	div[align="center"] a {
	    padding: 5px 10px;
	    margin: 0 3px;
	}
	
	div[align="center"] font {
	    text-decoration: none;
	}
	
	/* Modify and Delete links */
	a[href*="testUpdate"], a[href*="testDelete"] {
	    display: inline-block;
	    padding: 5px 10px;
	    background-color: #f0f0f0;
	    border-radius: 4px;
	    margin-top: 10px;
	}
	
	a[href*="testDelete"] {
	    color: #dc3545;
	}
	
	a[href*="testUpdate"]:hover, a[href*="testDelete"]:hover {
	    background-color: #e0e0e0;
	    text-decoration: none;
	}
	
	/* Total records display */
	h3:first-of-type {
	    background-color: #4C5317;
	    color: white;
	    padding: 10px 15px;
	    border-radius: 4px;
	    display: inline-block;
	}
</style>
<body>
<%@include file="/WEB-INF/views/member_home.jsp" %>

<div class="container-testAll">
	<!-- 시험 목록 보여주기(게시판 형식) -->
	<div>
		<p><h3>전체글 총 ${total_record}건</h3>
	</div>
	
	<!-- 검색 -->
	<div>				
		<select name="items" class="txt">
			<option value="title">제목</option>
			<option value="subject">교과목</option>
			<option value="name">작성자</option>
		</select> <input name="text" type="text" /> <input type="submit" id="btnAdd" value="검색 " />				
	</div>

	<c:forEach items="${boardList}" var="test" varStatus="status">
		<div style="border: 1px solid black;">
			<h3>번호: ${test.test_num}</h3>
			<!-- <p>제목: <a href="testOneView?Num=${test.test_num}" onclick="testPopup()">${test.test_name}</a> -->
			<p>제목: <button onclick="testPopup('${test.test_num}')">${test.test_name}</button> 
			<p>작성자: ${test.mem_id}
			<p>비밀번호 입력: ${test.test_pw}
			<p>조회수: ${test.test_hit}
			<p>교과명: ${test.sub_name}
			<p>총 문제 갯수: ${fn:length(test.serial)}개
			<p><a href="testUpdate?Num=${test.test_num}">수정</a>
			<p><a href="testDelete?Num=${test.test_num}">삭제</a>
		</div>
	</c:forEach>

	<!-- 페이지 이동 -->
	<div align="center">
		<c:set var="pageNum" value="${pageNum}" />
		<c:forEach var="i" begin="1" end="${total_page}">
			<a href="testAll?pageNum=${i}">
				<c:choose>
					<c:when test="${pageNum==i}">
						<font color='4C5317'><b> [${i}]</b></font>
					</c:when>
					<c:otherwise>
						<font color='4C5317'> [${i}]</font>
					</c:otherwise>
				</c:choose>
			</a>
		</c:forEach>
	</div>	
</div>
</body>
<script type="text/javascript">
function testPopup(test_num) {
	let inputValue = prompt("비밀번호를 입력하세요.");
	if(inputValue !== null) {
		ajaxTest(inputValue, test_num);
	}
}

function ajaxTest(inputValue, test_num) {
	let param = {
			password : inputValue,
			test_num : test_num
	};
	
	$.ajax({
		type: "POST", 
		url: "testValue", 
		data: param, 
		dataType : 'json',
		success: function (data) {
			if(data.success) {
				window.location.href = "testOneView?Num=" + test_num; 
			} else {
				alert("비밀번호가 틀렸습니다.");
			}
		},
		error: function (data) {
			alert("에러가 발생했습니다.");
		},
	});
}

//HTML의 검색 부분에 form 태그 추가 및 id 부여
$(document).ready(function() {
    // 검색 버튼 클릭 이벤트
    $('#btnAdd').click(function(e) {
        e.preventDefault();
        performSearch();
    });

    // 엔터키 입력 이벤트
    $('input[name="text"]').keypress(function(e) {
        if (e.which === 13) {
            e.preventDefault();
            performSearch();
        }
    });

    function performSearch() {
        let searchType = $('.txt').val();
        let searchText = $('input[name="text"]').val();
        let param = {
        		searchType: searchType,
                searchText: searchText
        };

        // 검색어가 비어있는 경우 처리
        if (searchText.trim() === '') {
            alert('검색어를 입력해주세요.');
            return;
        }

        // AJAX를 통한 검색 요청
        $.ajax({
            type: 'POST',
            url: 'search', 
            data: param,
            dataType: 'json',
            success: function(data) {
                if (response.success) {
                    // 검색 결과를 화면에 표시
                    updateSearchResults(response.data);
                } else {
                    alert('검색 중 오류가 발생했습니다.');
                }
            },
            error: function() {
                alert('서버 통신 중 오류가 발생했습니다.');
            }
        });
    }

    function updateSearchResults(results) {
        // 기존 목록 비우기
        $('.container-testAll > div[style*="border"]').remove();

        // 검색 결과가 없는 경우
        if (results.length === 0) {
            $('.container-testAll').append('<div class="no-results">검색 결과가 없습니다.</div>');
            return;
        }

        // 검색 결과 표시
        results.forEach(function(test) {
            let resultHtml = `
                <div style="border: 1px solid black;">
                    <h3>번호: ${test.test_num}</h3>
                    <p>제목: <button onclick="testPopup('${test.test_num}')">${test.test_name}</button></p>
                    <p>작성자: ${test.mem_id}</p>
                    <p>비밀번호 입력: ${test.test_pw}</p>
                    <p>조회수: ${test.test_hit}</p>
                    <p>교과명: ${test.sub_name}</p>
                    <p>총 문제 갯수: ${test.serial.length}개</p>
                    <p><a href="testUpdate?Num=${test.test_num}">수정</a></p>
                    <p><a href="testDelete?Num=${test.test_num}">삭제</a></p>
                </div>
            `;
            $('.container-testAll').append(resultHtml);
        });

        // 검색 결과 수 업데이트
        $('h3:contains("전체글")').text(`전체글 총 ${results.length}건`);
    }
});
</script>
</html>
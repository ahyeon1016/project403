<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script> <%-- 아작스 사용을 위한 코드 --%>
    
    <style>
    
    
    	 .form-container {
	        max-width: 400px;
	        margin: 50px auto;
	        padding: 20px;
	        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	        border-radius: 10px;
	        text-align: center;
	    }
	    .form-control {
	        max-width: 300px;
	        margin: 0 auto;
	    }
	    .btn-custom {
	        width: 10%; /* 버튼 너비 조정 */
	        margin-top: 10px;
	    }
        .required {
            color: red;
            margin-left: 5px;
        }
        
        
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/member_home.jsp" %>

    <div class="container form-container">
        <h2 class="text-center">회원 정보 수정</h2>
        
        <form:form modelAttribute="member" action="update/sequence" method="post" id="form" enctype="multipart/form-data">
            <div class="form-group">
                <label for="mem_id">아이디 <span class="required">*</span></label>
                <form:input path="mem_id" type="text" value="${member.getMem_id()}" readonly="true" id="mem_id" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="pw">비밀번호 <span class="required">*</span></label>
                <form:input path="mem_pw" type="password" minlength="3" maxlength="15" required="true" id="pw" class="form-control"/>
                <small class="form-text text-muted">비밀번호는 3~15자 이내여야 합니다.</small>
            </div>
            <div class="form-group">
                <label for="mem_nickName">닉네임 <span class="required">*</span></label>
                <form:input path="mem_nickName" type="text" maxlength="10" value="${member.getMem_nickName()}" readonly="true" class="form-control"/>
                <small class="form-text text-muted">닉네임 변경권을 쓸 경우 바꿀 수 있습니다.</small>
            </div>
            <div class="form-group">
                <label for="email">이메일 <span class="required">*</span></label>
                <form:input path="mem_email" type="email" value="${member.getMem_email()}" id="email" required="true" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="input_file">프로필 사진</label>
                <form:input path="mem_profile" id="input_file" type="file" accept="image/*" class="form-control"/>
            </div>
            <form:button type="submit" id="sub" class="btn btn-primary btn-custom">수정하기</form:button>
        </form:form>
    </div>

    <script>
        //폼에 입력된 값 유효성 검사
        let sub = document.querySelector("#sub");
        sub.addEventListener("click", alertmessage);
        const emailPattern = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
        
        function alertmessage(event) {
            let email = document.querySelector("#email").value;
            let pw = document.querySelector("#pw").value;
            let file = document.querySelector("#input_file").files[0];
            console.log(pw.length);
            console.log(emailPattern.test(email));
            event.preventDefault();
            if ((pw.length >= 3 && pw.length <= 15 && emailPattern.test(email)) || 
                ((mem_id.value.startsWith("kakao_") || mem_id.value.startsWith("naver_")))) {
                alarm();
                document.querySelector("#form").submit();
            } else {
                alert("형식에 맞게 입력해주세요.");
            }
        }
        
        function alarm() {
            alert("수정되었습니다.다시 로그인 하십시오.");
        }

        // 카카오나 네이버로 로그인 했을 시 비밀번호 설정 못하게 하기
        let mem_id = document.querySelector("#mem_id");
        let mem_pw = document.querySelector("#pw");
        let pw_filed = document.querySelector("#pw_filed");
        if ((mem_id.value.startsWith("kakao_") || mem_id.value.startsWith("naver_")) && pw.value === "") {
            pw_filed.style.display = "none";
        }

        // 올린 파일 유효성 검사
        let file = document.querySelector("#input_file");
        file.addEventListener("change", extention);

        function extention() {
            let imgs = file.files[0];
            if (imgs.type != "image/jpeg" && imgs.type != "image/png") {
                alert("jpg, png 파일만 업로드 가능합니다.");
                file.value = "";
            }
        }
    </script>
</body>
</html>

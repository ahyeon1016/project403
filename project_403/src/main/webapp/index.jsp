<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>랠리폴리</title>
    <!-- fontawesome -->
    <script src="https://kit.fontawesome.com/0ae4c75b50.js" crossorigin="anonymous"></script>
    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            line-height: 1.6;
            color: #fff;
            font-family: Arial, sans-serif;
        }

        .about {
            height: 1000px;
            background-image: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url("/project_403/resources/images/high-angle-girl-learning-math-school.jpg");
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
        }

        .about h1 {
            font-size: 2.5rem;
            font-weight: 900;
            color: #fff;
            line-height: 1.4;
            max-width: 800px;
            background: rgba(0, 0, 0, 0.6);
        }

        .how {
            min-height: 700px;
            background-image: url("/project_403/resources/images/4119036.jpg");
            background-repeat: no-repeat;
            background-size: 100%;
            background-position: center;
            padding: 50px 0;
        }

       .how > h1 {
			    text-align: center;
			    margin-bottom: 50px;
			    font-size: 3rem;
			    font-weight: 900;
			    color: #d4f7d4; /* 연한 녹색 */
			    padding: 10px;
			    border-radius: 10px;
			    text-shadow: 2px 2px 6px rgba(34, 139, 34, 0.7); /* 어두운 녹색 그림자 */
			    font-family: 'Montserrat', sans-serif;
			    -webkit-text-stroke: 2.5px #006400; /* 어두운 녹색 테두리 */
		}


        .box {
            border: 1px solid rgba(255, 255, 255, 0.6);
            border-radius: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 250px;
            height: 350px;
            background: rgba(0, 0, 0, 0.7);
            color: #fff;
            padding: 20px;
        }

        .box:hover {
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }

        .fa-solid {
            font-size: 2rem;
            color: #ffdd57; /* 노란색 강조 */
            margin: 0 10px;
        }

        @media (max-width: 768px) {
            .about h1 {
                font-size: 1.8rem;
                padding: 15px;
            }
        }

        @media (max-width: 480px) {
            .about h1 {
                font-size: 1.5rem;
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/views/member_home.jsp" %>
    <!-- 메인박스 -->
    <div class="about d-flex align-items-center justify-content-center text-center">
        <h1 class="p-4 rounded">창의적인 학습 플랫폼<br> 학습의 새로운 기준, 랠리폴리.</h1>
    </div>

    <!-- 두번째 박스 -->
    <div class="how d-flex flex-column justify-content-center">
        <h1 class="text-center mb-4">사이트 이용 순서</h1>
        <div class="boxes d-flex justify-content-center align-items-center gap-4">
            <div class="box d-flex flex-column align-items-center justify-content-center text-center">
                <h3 class="text-warning mb-3">Step1.</h3>
                <h5>문제 작성!</h5>
                <p>문제 작성페이지에서 <br> 과목과 챕터를 선택해 <br> 문제를 작성합니다.</p>
            </div>
            <i class="fa-solid fa-angle-right"></i>
            <div class="box d-flex flex-column align-items-center justify-content-center text-center">
                <h3 class="text-warning mb-3">Step2</h3>
                <h5>시험지 작성!</h5>
                <p>등록된 문제를 시험지에<br>추가해 시험지를 <br>작성합니다.</p>
            </div>
            <i class="fa-solid fa-angle-right"></i>
            <div class="box d-flex flex-column align-items-center justify-content-center text-center">
                <h3 class="text-warning mb-3">Step3</h3>
                <h5>시험 시작!!</h5>
            </div>
        </div>
    </div>
    
   <%@include file="/WEB-INF/views/footer.jsp" %>
    
    
</body>
</html>

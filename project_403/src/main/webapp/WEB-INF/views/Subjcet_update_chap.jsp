<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챕터 수정</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        .update-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .update-title {
            color: #2c3e50;
            font-size: 24px;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #3498db;
        }

        .update-form-group {
            margin-bottom: 15px;
        }

        .update-label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #2c3e50;
        }

        .update-input {
            width: 100%;
            padding: 8px;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
            font-size: 14px;
        }

        .update-input:read-only {
            background-color: #f5f6fa;
        }

        .update-input:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
        }

        .update-error {
            color: #e74c3c;
            font-size: 12px;
            margin-top: 5px;
        }

        .update-button {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            width: 100%;
        }

        .update-button:hover {
            background-color: #2980b9;
        }

        .update-button:disabled {
            background-color: #bdc3c7;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
    <div class="update-container">
        <h1 class="update-title">챕터 수정</h1>
        <form action="updateSubChap" method="POST">
        	<!-- 과목 -->
            <div class="update-form-group">
                <label class="update-label">과목</label>
                <input type="text" name="sub_name" value="${sub_name}" readonly id="u2_sub_name" class="update-input">
            </div>
            <!-- 기존 챕터 -->
            <div class="update-form-group">
                <label class="update-label">기존 챕터</label>
                <input type="text" value="${sub_chap}" name="old_sub_chap" readonly class="update-input">
            </div>
            <!-- 수정 챕터 -->
            <div class="update-form-group">
                <label class="update-label">수정 챕터</label>
                <input type="text" name="new_sub_chap" id="u2_sub_chap" required class="update-input">
                <span id="u2_duplication" class="update-error" style="display: none;">중복된 챕터입니다.</span>
            </div>
            <button type="submit" id="u2_sub_submit" class="update-button">수정</button>
        </form>
    </div>
    <%@include file="/WEB-INF/views/footer.jsp" %>
    <script>
        var u2_sub_chap = document.querySelector("#u2_sub_chap");
        var u2_sub_name = document.querySelector("#u2_sub_name");
        var u2_duplication = document.querySelector("#u2_duplication");
        var u2_sub_submit = document.querySelector("#u2_sub_submit");
	
        /* 이벤트 */
        u2_sub_chap.addEventListener("input", u2_chap);

        function u2_chap() {
            var u2_sub_chap_value = u2_sub_chap.value;
            var u2_sub_name_value = u2_sub_name.value;
			
            /* 입력한 데이터가 존재하는지 확인하고 존재한다면 전송버튼 숨김 처리 */
            $.ajax({
                url: "subChapCheck",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    "input_chap": u2_sub_chap_value,
                    "input_name": u2_sub_name_value
                }),
                success: function(data) {
                    if(data.check == "true") {
                        u2_duplication.style.display = "block";
                        u2_sub_submit.disabled = true;
                    } else {
                        u2_duplication.style.display = "none";
                        u2_sub_submit.disabled = false;
                    }
                }
            });
        }
    </script>
</body>
</html>
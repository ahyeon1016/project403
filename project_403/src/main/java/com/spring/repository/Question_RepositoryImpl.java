package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.spring.domain.Question;

@Repository
public class Question_RepositoryImpl implements Question_Repository{

	//DB에 객관식 문제 저장하는 함수 (CREATE)
	@Override
	public void addMCQ(Question question) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리전송
			//question_serial 변수 생성
			String question_serial = question.getSub_code_sum()+"_"+setQuestionNum();
			question.setQuestion_serial(question_serial);
			String SQL = "INSERT INTO Question VALUES(NULL, ?, ?, ?, 0, 0, ?, NULL, ?, 'MCQ')";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, question.getQuestion_content());
			pstmt.setString(2, question.getQuestion_ans());
			pstmt.setString(3, question.getQuestion_img_name());
			pstmt.setString(4, question.getSub_code_sum());
			pstmt.setString(5, question_serial);
			//pstmt.setString(6, question.getQuestion_id());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	
	//DB의 question_num 컬럼의 최대값을 리턴하는 함수 | 사용한 함수 : addMCQ()
	private String setQuestionNum() {
		int questionNum = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "SELECT MAX(question_num) FROM Question";
			pstmt = conn.prepareStatement(SQL);
			
			rs = pstmt.executeQuery();
			//ResultSet에 담아서 조건문으로 question_num 가져오기
			if(rs.next()) {
				questionNum=rs.getInt(1)+1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | setQuestionNum함수의 리턴값 : "+questionNum);
		return String.valueOf(questionNum);
	}
}

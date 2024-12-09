package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.spring.domain.Question;

@Repository
public class Question_RepositoryImpl implements Question_Repository{

	//DB에 객관식 문제 저장 (CREATE)
	@Override
	public void addMCQ(Question question) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리전송
			String SQL = "INSERT INTO Question VALUES(NULL, ?, ?, ?, 0, 0, ?, NULL, NULL, ?)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, question.getQuestion_content());
			pstmt.setString(2, question.getQuestion_ans());
			pstmt.setString(3, question.getQuestion_img_name());
			pstmt.setString(4, question.getSub_code_sum());
			pstmt.setString(5, question.getQuestion_id());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
}

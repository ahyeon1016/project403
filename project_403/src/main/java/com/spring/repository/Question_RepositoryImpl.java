package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.spring.domain.Question;

@Repository
public class Question_RepositoryImpl implements Question_Repository{

	@Override
	//INSERT
	public void Question_Insert_DB(Question question) {
		//DB연결
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println("리파지토리 | DB연결");
			conn = DBConnection.dbconn();
			
			System.out.println("리파지토리 | 쿼리전송");
			//쿼리 전송
			//AUTO_INCREMENT로 작성된 컬럼은 null값 고정
			String sql = "INSERT INTO Question VALUES(null, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, question.getQuestion_inner());
			pstmt.setString(2, question.getQuestion_ans());
			pstmt.setString(3, question.getQuestion_img_name());
			pstmt.setInt(4, question.getQuestion_plus());
			pstmt.setInt(5, question.getQuestion_count());
			//pstmt.setString(6, question.getQuestion_chap());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			//객체 close
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}
}

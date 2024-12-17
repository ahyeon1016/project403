package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.spring.domain.QnA;

@Repository
public class QnA_RepositoryImpl implements QnA_Repository{

	//CommentRoot를 DB에 추가 (Create)
	@Override
	public void addCommentRoot(QnA qna) {
		System.out.println("리파지토리 | addCommentRoot() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(Read) - comment_root 값 설정
			String SQL_SELECT = "SELECT MAX(comment_root) FROM QnA";
			pstmt = conn.prepareStatement(SQL_SELECT);
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				qna.setComment_root(1);
			}else {
				qna.setComment_root(rs.getInt(1)+1);
			}
			System.out.println("리파지토리 | SELECT 성공 comment_root 값 설정 완료");
			//쿼리전송(Create)
			String SQL_INSERT = "INSERT INTO QnA VALUES(NULL, ?, ?, ?, 0, 0, ?, ?, ?, 0, 0)";
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, "NULL");
			pstmt.setString(2, qna.getQuestion_serial());
			pstmt.setInt(3, qna.getComment_root());
			pstmt.setString(4, qna.getComment_title());
			pstmt.setString(5, qna.getComment_content());
			pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			
			pstmt.executeUpdate();
			System.out.println("리파지토리 | INSERT 성공");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}

}

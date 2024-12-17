package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

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
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(Create)
			qna.setComment_root(getCommentDepth("comment_root"));
			String SQL = "INSERT INTO QnA VALUES(NULL, ?, ?, ?, 0, 0, ?, ?, ?, 0, 0)";
			pstmt = conn.prepareStatement(SQL);
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
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	
	//DB에서 comment_parent와 comment_child가 0인 comment_root 컬럼을 모두 가져온다. (Read) 
	@Override
	public ArrayList<QnA> getCommentRootAll() {
		System.out.println("리파지토리 | getCommentRootAll() 도착");
		ArrayList<QnA> rootAll = new ArrayList<QnA>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			String SQL = "SELECT * FROM QnA WHERE comment_parent=0 AND comment_child=0";
			pstmt = conn.prepareStatement(SQL);
			
			rs =  pstmt.executeQuery();
			//ResultSet에 담긴 데이터를 ArrayList에 추가
			while(rs.next()) {
				QnA qna = new QnA();
				qna.setComment_num(rs.getInt(1));
				qna.setMem_id(rs.getString(2));
				qna.setQuestion_serial(rs.getString(3));
				qna.setComment_root(rs.getInt(4));
				qna.setComment_parent(rs.getInt(5));
				qna.setComment_child(rs.getInt(6));
				qna.setComment_title(rs.getString(7));
				qna.setComment_content(rs.getString(8));
				qna.setComment_date(rs.getTimestamp(9));
				qna.setComment_hit(rs.getInt(10));
				qna.setComment_good(rs.getInt(11));
				
				rootAll.add(qna);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | getCommentRootAll() rootAll의 크기 : "+rootAll.size());
		return rootAll;
	}

	//
	@Override
	public QnA getCommentRootOne(int comment_root, int comment_hit) {
		System.out.println("리파지토리 | getCommentRootOne() 도착");
		QnA qna = new QnA();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			commentHitUp(comment_root, comment_hit);
			String SQL = "SELECT * FROM QnA WHERE comment_root=? AND comment_parent=0 AND comment_child=0";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, comment_root);
			
			rs =  pstmt.executeQuery();
			//ResultSet에 담긴 데이터를 DTO에 추가
			if(rs.next()) {
				qna.setComment_num(rs.getInt(1));
				qna.setMem_id(rs.getString(2));
				qna.setQuestion_serial(rs.getString(3));
				qna.setComment_root(rs.getInt(4));
				qna.setComment_parent(rs.getInt(5));
				qna.setComment_child(rs.getInt(6));
				qna.setComment_title(rs.getString(7));
				qna.setComment_content(rs.getString(8));
				qna.setComment_date(rs.getTimestamp(9));
				qna.setComment_hit(rs.getInt(10));
				qna.setComment_good(rs.getInt(11));
			}
			System.out.println("리파지토리 | getCommentRootAll() 처리 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return qna;
	}

	//comment_root, comment_parent, comment_child의 최대값을 구하는 함수
	private int getCommentDepth(String name) {
		System.out.println("리파지토리 | getCommentDepth() 도착 "+name+"의 값 설정 시작");
		int depth = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();;
			//쿼리전송
			String SQL = "SELECT MAX("+name+") FROM QnA";
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				depth = 1;
			}else {
				depth = rs.getInt(1)+1;
			}
			System.out.println("리파지토리 | SELECT 성공 "+name+"의 값을 "+depth+"으로 설정 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return depth;
	}
	
	//comment_hit(조회수) 추가 함수
	private void commentHitUp(int comment_root, int comment_hit) {
		System.out.println("리파지토리 | commentHitUp() 도착");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			String SQL = "UPDATE QnA SET comment_hit=? WHERE comment_root=? AND comment_parent=0 AND comment_child=0";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, comment_hit+1);
			pstmt.setInt(2, comment_root);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | commentHitUp() 추가 완료");
	}
}

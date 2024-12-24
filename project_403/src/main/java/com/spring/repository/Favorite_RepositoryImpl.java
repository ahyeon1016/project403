package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class Favorite_RepositoryImpl implements Favorite_Repository{
	
	//좋아요 버튼을 활성화(true)시키는 함수로 사용자가 처음으로 좋아요를 눌렀다면 DB에 추가한다.(Create, Update)
	@Override
	public void goodEnable(String mem_id, int qnaNum) {
		System.out.println("리파지토리 | goodEnable()도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(존재 여부에 따라 다른 쿼리문 전송)
			if(hasFeedBack("comment_good", mem_id, qnaNum)) {
				System.out.println("리파지토리 | goodEnable() 좋아요 활성화");
				String SQL = 
						"UPDATE Favorite "
						+ "SET comment_good=1 "
						+ "WHERE mem_id=? AND comment_num=?";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, mem_id);
				pstmt.setInt(2, qnaNum);
				
				pstmt.executeUpdate();
			} else {
				System.out.println("리파지토리 | goodEnable() 좋아요를 처음 누른 사용자");
				String SQL = 
						"INSERT INTO Favorite(mem_id, comment_num, comment_good) "
						+ "VALUES(?, ?, 1)";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, mem_id);
				pstmt.setInt(2, qnaNum);
				
				pstmt.executeUpdate();
			}
			System.out.println("리파지토리 | goodEnable()완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	//좋아요 버튼을 비활성화(false)시키는 함수로 DB의 값을 수정한다.(Update)
	@Override
	public void goodDisable(String mem_id, int qnaNum) {
		System.out.println("리파지토리 | goodDisable()도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			System.out.println("리파지토리 | goodDisable() 좋아요 비활성화");
			String SQL = 
					"UPDATE Favorite "
					+ "SET comment_good=0 "
					+ "WHERE mem_id=? AND comment_num=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, qnaNum);
			
			pstmt.executeUpdate();

			System.out.println("리파지토리 | goodDisable()완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	//활성화된 좋아요의 갯수를 가져오는 함수(Read)
	@Override
	public int getTotalGood(int qnaNum) {
		System.out.println("리파지토리 | getTotalGood()도착");
		int totalGood = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			String SQL = 
					"SELECT COUNT(comment_good) FROM Favorite "
					+ "WHERE comment_num=? AND comment_good=1";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, qnaNum);
			System.out.println(pstmt);
			//COUNT값을 totalGood에 대입한다.
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalGood = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | getTotalGood()완료 totalGood값 리턴 : "+totalGood);
		
		return totalGood;
	}

	//DB에 파라미터로 받은 값이 포함된 row가 있는지 확인하고 Boolean값을 반환하는 함수
	private Boolean hasFeedBack(String type, String mem_id, int qnaNum) {
		System.out.println("리파지토리 | hasFeedBack()도착");
		
		Boolean hasFeedBack = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			String SQL = 
					"SELECT "+type+" FROM Favorite WHERE mem_id=? AND comment_num=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, qnaNum);
			
			rs = pstmt.executeQuery();
			
			//존재한다면 true 없다면 false
			if(!rs.next()) {
				hasFeedBack=false;
			}else {
				hasFeedBack=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | hasFeedBack()완료 Boolean값 리턴 : "+hasFeedBack);
		return hasFeedBack;
	}
}

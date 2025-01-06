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
			//쿼리전송(DB상 comment_good의 존재 여부에 따라 다른 쿼리문 전송)
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
			//쿼리전송 질문글에 포함된 comment_good의 값이 1인 데이터의 갯수를 파악하는 쿼리문
			String SQL = 
					"SELECT COUNT(comment_good) FROM Favorite "
					+ "WHERE comment_num=? AND comment_good=1";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, qnaNum);

			//COUNT의 값을 totalGood에 대입한다.
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

	//사용자가 해당 질문에 좋아요를 눌렀는지 확인하는 함수 (Read)
	@Override
	public Boolean isGoodClicked(String user_id, int qnaNum) {
		System.out.println("리파지토리 | isGoodClicked()도착");
		Boolean isClicked_btn = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			String SQL = 
					"SELECT comment_good "
					+ "FROM Favorite "
					+ "WHERE mem_id=? AND comment_num=? AND comment_good=1";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			pstmt.setInt(2, qnaNum);
			
			rs = pstmt.executeQuery();
			//ResultSet에 담긴 데이터에 따른 isClicked의 값을 설정
			if(!rs.next()) {
				isClicked_btn = false;
			}else {
				isClicked_btn = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | isGoodClicked()완료 isClicked_btn값 리턴 : "+isClicked_btn);
		
		return isClicked_btn;
	}

	//싫어요 버튼을 활성화(true)시키는 함수로 사용자가 처음으로 싫어요를 눌렀다면 DB에 추가한다.(Create, Update)
	@Override
	public void badEnable(String mem_id, int qnaNum) {
		System.out.println("리파지토리 | badEnable()도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(DB상 comment_bad의 존재 여부에 따라 다른 쿼리문 전송)
			if(hasFeedBack("comment_bad", mem_id, qnaNum)) {
				System.out.println("리파지토리 | badEnable() 좋아요 활성화");
				String SQL = 
						"UPDATE Favorite "
						+ "SET comment_bad=1 "
						+ "WHERE mem_id=? AND comment_num=?";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, mem_id);
				pstmt.setInt(2, qnaNum);
				
				pstmt.executeUpdate();
			} else {
				System.out.println("리파지토리 | badEnable() 좋아요를 처음 누른 사용자");
				String SQL = 
						"INSERT INTO Favorite(mem_id, comment_num, comment_bad) "
						+ "VALUES(?, ?, 1)";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, mem_id);
				pstmt.setInt(2, qnaNum);
				
				pstmt.executeUpdate();
			}
			System.out.println("리파지토리 | badEnable()완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}

	//싫어요 버튼을 비활성화(false)시키는 함수로 DB의 값을 수정한다.(Update)
	@Override
	public void badDisable(String mem_id, int qnaNum) {
		System.out.println("리파지토리 | badDisable()도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			System.out.println("리파지토리 | badDisable() 좋아요 비활성화");
			String SQL = 
					"UPDATE Favorite "
					+ "SET comment_bad=0 "
					+ "WHERE mem_id=? AND comment_num=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, qnaNum);
			
			pstmt.executeUpdate();

			System.out.println("리파지토리 | badDisable()완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}

	//활성화된 싫어요의 갯수를 가져오는 함수(Read)
	@Override
	public int getTotalBad(int qnaNum) {
		System.out.println("리파지토리 | getTotalBad()도착");
		int totalBad = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송 질문글에서 comment_bad의 값이 1인 데이터의 갯수를 가져오는 쿼리문 
			String SQL = 
					"SELECT COUNT(comment_bad) FROM Favorite "
					+ "WHERE comment_num=? AND comment_bad=1";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, qnaNum);

			//COUNT()의 값을 totalBad에 대입한다.
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalBad = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | getTotalBad()완료 totalBad값 리턴 : "+totalBad);
		
		return totalBad;
	}

	//사용자가 해당 질문에 좋아요를 눌렀는지 확인하는 함수 (Read)
	@Override
	public Boolean isBadClicked(String user_id, int qnaNum) {
		System.out.println("리파지토리 | isBadClicked()도착");
		Boolean isClicked_btn = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			String SQL = 
					"SELECT comment_bad "
					+ "FROM Favorite "
					+ "WHERE mem_id=? AND comment_num=? AND comment_bad=1";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			pstmt.setInt(2, qnaNum);
			
			rs = pstmt.executeQuery();
			//ResultSet에 담긴 데이터에 따른 isClicked의 값을 설정
			if(!rs.next()) {
				isClicked_btn = false;
			}else {
				isClicked_btn = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | isBadClicked()완료 isClicked_btn값 리턴 : "+isClicked_btn);
		
		return isClicked_btn;
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
			//쿼리전송 | SELECT할 데이터는 동적 처리가 불가능하기 때문에 변수를 집어넣었음.
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

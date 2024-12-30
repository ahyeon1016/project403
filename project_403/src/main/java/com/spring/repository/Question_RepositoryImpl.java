package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.spring.domain.Question;

@Repository
public class Question_RepositoryImpl implements Question_Repository{

	//DB에 객관식 문제 저장하는 함수 (CREATE)
	@Override
	public void addMCQ(Question question, int mem_serial) {
		System.out.println("리파지토리 | addMCQ() 도착");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			//question_serial 변수 생성
			String question_serial = question.getSub_code_sum()+"_"+addQuestionNum();
			question.setQuestion_serial(question_serial);
			String SQL = "INSERT INTO Question VALUES(NULL, ?, ?, ?, ?, 0, ?, ?, ?, 'MCQ', true)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, question.getQuestion_content());
			pstmt.setString(2, question.getQuestion_ans());
			pstmt.setString(3, question.getQuestion_img_name());
			pstmt.setInt(4, question.getQuestion_level());
			pstmt.setString(5, question.getSub_code_sum());
			pstmt.setInt(6, mem_serial);
			pstmt.setString(7, question_serial);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	//DB에 주관식 문제를 저장하는 함수 (CREATE)
	@Override
	public void addSAQ(Question question, int mem_serial) {
		System.out.println("리파지토리 | addSAQ() 도착");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			//question_serial 변수 생성
			String question_serial = question.getSub_code_sum()+"_"+addQuestionNum();
			question.setQuestion_serial(question_serial);
			String SQL = "INSERT INTO Question VALUES(NULL, ?, ?, ?, ?, 0, ?, ?, ?, 'SAQ', true)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, question.getQuestion_content());
			pstmt.setString(2, question.getQuestion_ans());
			pstmt.setString(3, question.getQuestion_img_name());
			pstmt.setInt(4, question.getQuestion_level());
			pstmt.setString(5, question.getSub_code_sum());
			pstmt.setInt(6, mem_serial);
			pstmt.setString(7, question_serial);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}

	//DB에 코딩 문제를 저장하는 함수 (CREATE)
	@Override
	public void addCP(Question question, int mem_serial) {
		System.out.println("리파지토리 | addCP() 도착");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송
			//question_serial 변수 생성
			String question_serial = question.getSub_code_sum()+"_"+addQuestionNum();
			question.setQuestion_serial(question_serial);
			String SQL = "INSERT INTO Question VALUES(NULL, ?, ?, ?, ?, 0, ?, ?, ?, 'CP', true)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, question.getQuestion_content());
			pstmt.setString(2, question.getQuestion_ans());
			pstmt.setString(3, question.getQuestion_img_name());
			pstmt.setInt(4, question.getQuestion_level());
			pstmt.setString(5, question.getSub_code_sum());
			pstmt.setInt(6, mem_serial);
			pstmt.setString(7, question_serial);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}

	//Question 테이블의 모든 DTO를 ArrayList에 담아 리턴(Read) 
	@Override
	public ArrayList<Question> getQuestionAll() {
		System.out.println("리파지토리 | getQuestionAll() 호출");
		ArrayList<Question> question_all = new ArrayList<Question>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리 전송
			String SQL = "SELECT * FROM Question WHERE question_visible=1 ORDER BY sub_code_sum ASC";
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			//ResultSet를 통해 DTO를 ArrayList에 담는 작업
			while(rs.next()) {
				Question question = new Question();
				question.setQuestion_content(rs.getString(2));
				question.setQuestion_ans(rs.getString(3));
				question.setQuestion_img_name(rs.getString(4));
				question.setQuestion_level(rs.getInt(5));
				question.setQuestion_count(6);
				question.setSub_code_sum(rs.getString(7));
				question.setMem_serial(rs.getInt(8));
				question.setQuestion_serial(rs.getString(9));
				question.setQuestion_id(rs.getString(10));
				
				question_all.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
				
		System.out.println("리파지토리 | getQuestionAll() ArrayList 사이즈 : "+question_all.size());
		return question_all;
	}

	//Question 테이블에서 sub_code와 일치하는 DTO를 찾아 ArrayList에 담고 반환(Read)
	@Override
	public ArrayList<Question> getQuestionsBySubCode(String sub_code, String id) {
		System.out.println("리파지토리 | getQuestionsBySubCode() 도착");
		ArrayList<Question> question_list = new ArrayList<Question>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리 전송 | id의 값에 따라 다른 쿼리문을 전송
			String SQL = "";
			if(id.equals("ALL")) {
				System.out.println("리파지토리 | getQuestionsBySubCode() ALL");
				SQL = 
					"SELECT * FROM Question "
					+ "WHERE BINARY sub_code_sum=? AND question_visible=1 "
					+ "ORDER BY sub_code_sum ASC";
				
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, sub_code);

			}else {
				System.out.println("리파지토리 | getQuestionsBySubCode() "+id);
				SQL = 
					"SELECT * FROM Question "
					+ "WHERE BINARY sub_code_sum=? AND question_visible=1 "
					+ "AND question_id=? "
					+ "ORDER BY sub_code_sum ASC";
				
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, sub_code);
				pstmt.setString(2, id);
			}
			
			rs = pstmt.executeQuery();
			//ResultSet를 통해 DTO를 ArrayList에 담는 작업
			while(rs.next()) {
				Question question = new Question();
				question.setQuestion_content(rs.getString(2));
				question.setQuestion_ans(rs.getString(3));
				question.setQuestion_img_name(rs.getString(4));
				question.setQuestion_level(rs.getInt(5));
				question.setQuestion_count(rs.getInt(6));
				question.setSub_code_sum(rs.getString(7));
				question.setMem_serial(rs.getInt(8));
				question.setQuestion_serial(rs.getString(9));
				question.setQuestion_id(rs.getString(10));
				
				question_list.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | question_list 사이즈 : "+question_list.size());
		return question_list;
	}
	
	//Question 테이블에서 question_serial과 일치하는 DTO를 반환(Read)
	@Override
	public Question getQuestionBySerial(String question_serial) {
		System.out.println("리파지토리 | getQuestionBySerial() 도착");
		Question question = new Question();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리 전송
			String SQL = "SELECT * FROM Question WHERE BINARY question_serial=? AND question_visible=1";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, question_serial);
			rs = pstmt.executeQuery();
			//ResultSet를 통해 DTO에 데이터를 담는 작업
			if(rs.next()) {
				question.setQuestion_content(rs.getString(2));
				question.setQuestion_ans(rs.getString(3));
				question.setQuestion_img_name(rs.getString(4));
				question.setQuestion_level(rs.getInt(5));
				question.setQuestion_count(rs.getInt(6));
				question.setSub_code_sum(rs.getString(7));
				question.setMem_serial(rs.getInt(8));
				question.setQuestion_serial(rs.getString(9));
				question.setQuestion_id(rs.getString(10));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | getQuestionBySerial() question을 리턴하기 전에 값을 확인 sub_code_sum : "+question.getSub_code_sum());
		return question;
	}

	//Question 테이블에서 question_serial 과 일치하는 DTO의 question_count 수정(Update)
	@Override
	public void updateQuestionCount(String question_serial, int question_count) {
		System.out.println("리파지토리 | updateQuestionCount() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리 전송
			String SQL = "UPDATE Question SET question_count=? WHERE question_serial=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, question_count);
			pstmt.setString(2, question_serial);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}

	//Question 테이블에서 question_serial과 일치하는 DTO를 수정 (Update)
	@Override
	public void updateQuestion(Question question) {
		System.out.println("리파지토리 | updateQuestion() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리 전송
			String question_serial = question.getSub_code_sum()+"_"+addQuestionNum(); 
			String SQL = "INSERT INTO Question VALUES(NULL, ?, ?, ?, 0, ?, ?, NULL, ?, ?, true)";
			pstmt = conn.prepareStatement(SQL);
			System.out.println(question.getQuestion_content());
			pstmt.setString(1, question.getQuestion_content());
			pstmt.setString(2, question.getQuestion_ans());
			pstmt.setString(3, question.getQuestion_img_name());
			pstmt.setInt(4, question.getQuestion_count());
			pstmt.setString(5, question.getSub_code_sum());
			pstmt.setString(6, question_serial);
			pstmt.setString(7, question.getQuestion_id());
			
			pstmt.executeUpdate();
			System.out.println("리파지토리 | Question 수정완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	//question_serial과 일치하는 Question DTO의 question_visible 값을 false로 변경하는 함수
	public void visibleQuestion(String question_serial) {
		System.out.println("리파지토리 | visibleQuestion() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리 전송
			String SQL = "UPDATE Question SET question_visible=0 WHERE question_serial=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, question_serial);
		
			pstmt.executeUpdate();
			System.out.println("리파지토리 | Question DTO 숨김 처리 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	//Question 테이블에서 사용자의 serial과 sub_code가 일치하는 DTO를 찾아 ArrayList에 담고 반환(Read)
	@Override
	public ArrayList<Question> getMyQuestionsBySubCode(String sub_code, int mem_serial, String id) {
		System.out.println("리파지토리 | getMyQuestionsBySubCode() 도착");
		ArrayList<Question> question_list = new ArrayList<Question>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리 전송 | id의 값에 따라 다른 쿼리문을 전송
			String SQL = "";
			if(id.equals("ALL")) {
				System.out.println("리파지토리 | getMyQuestionsBySubCode() ALL");
				SQL = 
					"SELECT * FROM Question "
					+ "WHERE BINARY sub_code_sum=? AND mem_serial=? AND question_visible=1 "
					+ "ORDER BY sub_code_sum ASC";
				
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, sub_code);
				pstmt.setInt(2, mem_serial);
				
			} else {
				System.out.println("리파지토리 | getMyQuestionsBySubCode() "+id);
				SQL = 
					"SELECT * FROM Question "
					+ "WHERE BINARY sub_code_sum=? AND mem_serial=? AND question_visible=1 "
					+ "AND question_id=? "
					+ "ORDER BY sub_code_sum ASC";
				
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, sub_code);
				pstmt.setInt(2, mem_serial);
				pstmt.setString(3, id);
			}
			
			rs = pstmt.executeQuery();
			//ResultSet를 통해 DTO를 ArrayList에 담는 작업
			while(rs.next()) {
				Question question = new Question();
				question.setQuestion_content(rs.getString(2));
				question.setQuestion_ans(rs.getString(3));
				question.setQuestion_img_name(rs.getString(4));
				question.setQuestion_level(rs.getInt(5));
				question.setQuestion_count(rs.getInt(6));
				question.setSub_code_sum(rs.getString(7));
				question.setMem_serial(rs.getInt(8));
				question.setQuestion_serial(rs.getString(9));
				question.setQuestion_id(rs.getString(10));
				
				question_list.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | question_list 사이즈 : "+question_list.size());
		return question_list;
	}

	//DB의 question_num 컬럼의 최대값을 리턴하는 함수 | 사용한 함수 : addMCQ()
	private String addQuestionNum() {
		System.out.println("리파지토리 | addQuestionNum()도착");
		int questionNum = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
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
		System.out.println("리파지토리 | addQuestionNum함수의 리턴값 : "+questionNum);
		return String.valueOf(questionNum);
	}

}

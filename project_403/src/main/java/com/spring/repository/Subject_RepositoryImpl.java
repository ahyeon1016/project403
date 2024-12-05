package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.spring.domain.Subject;

@Repository
public class Subject_RepositoryImpl implements Subject_Repository{
	//Subject 추가(Create)
	@Override
	public void addSub(Subject subject) {
		System.out.println("리파지토리 | addSub() 도착");

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "INSERT INTO Subject(sub_code , sub_name, sub_chap) VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(SQL);
			//sub_code의 값을 지정하는 함수를 호출 후에 리턴 받은 값을 삽입.
			pstmt.setInt(1, subCodeValue(subject));
			pstmt.setString(2, subject.getSub_name());
			pstmt.setString(3, subject.getSub_chap());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 닫기
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
		
	//Subject 테이블의 모든 데이터를 ArrayList에 담에 리턴하는 함수(Read)
	@Override
	public ArrayList<Subject> getAllSub() {
		ArrayList<Subject> sub_all = new ArrayList<Subject>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "SELECT * FROM Subject ORDER BY sub_name ASC";
			pstmt = conn.prepareStatement(SQL);
			//ResultSet에 데이터를 담아 처리
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Subject sub = new Subject();
				
				sub.setSub_code(rs.getInt(1));
				sub.setSub_name(rs.getString(2));
				sub.setSub_chap(rs.getString(3));
				sub.setSub_num(rs.getInt(4));
				
				sub_all.add(sub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 닫기
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | sub_all의 크기 : "+sub_all.size());
		return sub_all;
	}

	//Subject 테이블에서 특정 sub_name을 가진 DTO를 모두 ArrayList에 담아 리턴하는 함수(Read)
	@Override
	public ArrayList<Subject> getSubByName(String sub_name) {
		System.out.println("리파지토리 | getSubByName() 호출");
		ArrayList<Subject> sub_name_arr = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "SELECT * FROM Subject WHERE BINARY sub_name=? ORDER BY sub_code ASC";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, sub_name);
			//ResultSet에 데이터를 담아 처리
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Subject sub = new Subject();
				
				sub.setSub_code(rs.getInt(1));
				sub.setSub_name(rs.getString(2));
				sub.setSub_chap(rs.getString(3));
				sub.setSub_num(rs.getInt(4));
				
				sub_name_arr.add(sub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 닫기
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | "+sub_name+"와(과) 일치한 DTO를 담은 sub_name_arr의 크기 : "+sub_name_arr.size());
		return sub_name_arr;
	}

	//Subject 테이블에서 특정 sub_name과 sub_chap을 가진 DTO를 리턴하는 함수(Read)
	@Override
	public Subject getSubByChap(Subject subject) {
		System.out.println("리파지토리 | getSubByChap() 도착");
		Subject subByChap = new Subject();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "SELECT * FROM Subject WHERE BINARY sub_name=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, subject.getSub_name());
			//ResultSet에 데이터를 담아 처리
			rs = pstmt.executeQuery();
			String chap = subChapValue(subject);
			while(rs.next()) {
				subByChap.setSub_code(rs.getInt(1));
				subByChap.setSub_name(rs.getString(2));
				subByChap.setSub_chap(chap);
				subByChap.setSub_num(rs.getInt(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 닫기
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | sub_name : "+subByChap.getSub_name());
		System.out.println("리파지토리 | sub_chap : "+subByChap.getSub_chap());
		System.out.println("리파지토리 | sub_code : "+subByChap.getSub_code());
		return subByChap;
	}

	//Subject 작성 폼에서 입력된 값이 DB에 존재하는지 확인하는 함수
	@Override
	public HashMap<String, Object> subChapCheck(HashMap<String, Object> map) {
		HashMap<String, Object> sum = new HashMap<String, Object>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "SELECT * FROM Subject WHERE BINARY sub_name=? AND sub_chap=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, (String)map.get("input_name"));
			pstmt.setString(2, (String)map.get("input_chap"));
			//ResultSet에 데이터를 담아 처리
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sum.put("check", "true");
			}else if(!rs.next()){
				sum.put("check", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 닫기
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return sum;
	}

	//Subject 테이블의 sub_name과 sub_chap의 유효성 검사를 위해 
	//sub_name에 일치하는 sub_chap이 테이블에 없다면 null을 반환하는 함수
	//이 함수를 사용한 함수 : getSubByChap()
	private String subChapValue(Subject subject) {
		String chap = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "SELECT * FROM Subject WHERE BINARY sub_name=? AND sub_chap=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, subject.getSub_name());
			pstmt.setString(2, subject.getSub_chap());
			//ResultSet에 데이터를 담아 처리
			rs = pstmt.executeQuery();
			if(rs.next()) {
				chap = rs.getString(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 닫기
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return chap;
	}

	//sub_code 변수 값 설정 함수 | 이 함수를 사용한 함수 : addSub()
	private int subCodeValue(Subject subject) {
		System.out.println("리파지토리 | subCodeValue() 호출");
		int value = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.dbconn();
			//쿼리 전송
			String SQL = "SELECT MAX(sub_code) FROM Subject WHERE sub_name=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, subject.getSub_name());
			System.out.println("쿼리 전송");
			rs = pstmt.executeQuery();
			
			//sub_code 컬럼에 값이 없다면 값을 1로 지정하고, 값이 있다면 마지막 행의 값+1을 값으로 지정
			if( !(rs.next()) ) {
				System.out.println("sub_name컬럼에 해당하는 sub_code가 존재하지 않음");
				value = 1;
			}else {
				System.out.println("sub_name컬럼에 해당하는 sub_code가 존재함");
				do{
					value = (rs.getInt(1))+1;
					System.out.println("value = "+value);
				}while(rs.next());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 닫기
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return value;
	}
	
}
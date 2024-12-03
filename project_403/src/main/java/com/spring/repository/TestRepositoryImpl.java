package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.domain.Test;

@Repository
public class TestRepositoryImpl implements TestRepository {
	
	//전역변수
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	// Read All
	@Override
	public List<Test> getAllTestList() {
		List<Test> listOfTests = new ArrayList<Test>();
		
		try {
			// 데이터 베이스 연결객체 확보 
			conn = DBConnection.getConnection();
			// SQL쿼리 전송
			String sql = "SELECT * FROM Test";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Test test = new Test();
				
				test.setTest_num(rs.getInt(1));
				test.setMem_id(rs.getString(2));
				test.setTest_date(rs.getString(3));
				test.setTest_time(rs.getString(4));
				test.setTest_name(rs.getString(5));
				
				listOfTests.add(test);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return listOfTests;
	}

	@Override
	public void setNewTest(Test test) {
		
		try {
			//데이터 베이스 연결객체 확보 
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "INSERT INTO Test(mem_id, test_time, test_date, test_name) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,test.getMem_id());
			pstmt.setString(2,test.getTest_time());
			pstmt.setString(3,test.getTest_date());
			pstmt.setString(4,test.getTest_name());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setDeleteTest(Integer test_num) {
		
		try {
			//데이터 베이스 연결객체 확보 
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "DELETE FROM Test WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,test_num);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}

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
		
	// 페이징 없이 All
//	@Override
//	public List<Test> getAllTestList() {
//		
//		List<Test> listOfTests = new ArrayList<Test>();
//		
//		try {
//			// 데이터 베이스 연결객체 확보 
//			conn = DBConnection.getConnection();
//			// SQL쿼리 전송
//			String sql = "SELECT * FROM Test";
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				Test test = new Test();
//				
//				test.setTest_num(rs.getInt(1));
//				test.setMem_id(rs.getString(2));
//				test.setTest_date(rs.getString(3));
//				test.setTest_time(rs.getString(4));
//				test.setTest_name(rs.getString(5));
//				
//				listOfTests.add(test);
//			}			
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//		    try {
//		    	if (rs != null) {
//		    		rs.close();
//		        }
//		        if (pstmt != null) {
//		            pstmt.close();
//		        }
//		        if (conn != null) {
//		            conn.close();
//		        }
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		    }
//		}
//		
//		return listOfTests;
//	}

	// 페이징 처리 Read All
	@Override
	public List<Test> getBoardList(Integer pageNum, int limit) {
		
//		int total_record = getListCount();
	    int start = (pageNum - 1) * limit;
//	    int index = start + 1;
	    
	    List<Test> list = new ArrayList<Test>();	    
	    
	    try {
	    	conn = DBConnection.getConnection();
	    	
	    	String sql = "SELECT * FROM Test WHERE test_openYN = 'Y' ORDER BY test_num DESC LIMIT ? OFFSET ?";		    	
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, limit);
	        pstmt.setInt(2, start);
	        rs = pstmt.executeQuery();
	        
            while (rs.next()) {
                Test test = new Test();
                test.setTest_num(rs.getInt(1));
                test.setMem_id(rs.getString(2));
                test.setTest_name(rs.getString(3));
                test.setTest_pw(rs.getString(4));
                test.setTest_openYN(rs.getString(5));
                test.setSub_name(rs.getString(6));
                test.setTest_hit(rs.getInt(7));
                list.add(test);
                
//                if (index < (start + limit) && index <= total_record) {
//					index++;
//                } else {
//					break;
//                }
	        }
	    } catch(Exception e) {
			e.printStackTrace();
		} finally {
		    try {
		    	if (rs != null) {
		    		rs.close();
		        }
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		return list;
	}
	
	// Test 테이블 전체 글 갯수 계산
	@Override
	public int getListCount() {
		
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) FROM Test WHERE test_openYN = 'Y'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		    try {
		    	if (rs != null) {
		    		rs.close();
		        }
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}			
		
		return count;
	}
	
	// Create
	@Override
	public void setNewTest(Test test) {
		
		try {
			//데이터 베이스 연결객체 확보 getOneTestList
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "INSERT INTO Test(mem_id, test_name, test_pw, test_openYN, sub_name) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,test.getMem_id());
			pstmt.setString(2,test.getTest_name());
			pstmt.setString(3,test.getTest_pw());
			pstmt.setString(4,test.getTest_openYN());
			pstmt.setString(5,test.getSub_name());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try {
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}

	// Delete
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
		} finally {
		    try {
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}

	// DB에서 test_num에 해당하는 행 가져오기
	@Override
	public Test getTestByNum(Integer test_num) {
		
		Test test = new Test();
		
		try {
			//데이터 베이스 연결객체 확보 
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "SELECT * FROM Test WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,test_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				test.setTest_num(rs.getInt(1));
				test.setMem_id(rs.getString(2));
				test.setTest_name(rs.getString(3));
				test.setTest_pw(rs.getString(4));
				test.setTest_openYN(rs.getString(5));
				test.setSub_name(rs.getString(6));
				test.setTest_hit(rs.getInt(7));
			}	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		    try {
		    	if (rs != null) {
		    		rs.close();
		        }
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		return test;
	}

	// Update
	@Override
	public void setUpdateTest(Test test) {
		
		try {
			//데이터 베이스 연결객체 확보 
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "UPDATE Test SET test_name=?, test_pw=?, test_openYN=?, sub_name=? WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, test.getTest_name());
			pstmt.setString(2, test.getTest_pw());
			pstmt.setString(3, test.getTest_openYN());
			pstmt.setString(4, test.getSub_name());
			pstmt.setInt(5, test.getTest_num());
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// Test 1개 상세보기
	@Override
	public Test getOneTestList(Integer test_num) {
		
		Test test = new Test();
		
		try {
			//데이터 베이스 연결객체 확보 
			conn = DBConnection.getConnection();
			//SQL쿼리 전송: 조회수 증가
			String sql1 = "UPDATE Test SET test_hit = test_hit+1 WHERE test_num=?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1,test_num);
			pstmt.executeUpdate();
			//SQL쿼리 전송: test_num에 해당하는 상세 내용 가져오기
			String sql = "SELECT * FROM Test WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,test_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				test.setTest_num(rs.getInt(1));
				test.setMem_id(rs.getString(2));
				test.setTest_name(rs.getString(3));
				test.setTest_pw(rs.getString(4));
				test.setTest_openYN(rs.getString(5));
				test.setSub_name(rs.getString(6));
				test.setTest_hit(rs.getInt(7));
			}	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		    try {
		    	if (rs != null) {
		    		rs.close();
		        }
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		return test;
	}

	@Override
	public Test getTestValue(Integer test_num) {
		
		Test test = new Test();
		
		try {
			//데이터 베이스 연결객체 확보 
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "SELECT * FROM Test WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,test_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				test.setTest_num(rs.getInt(1));
				test.setMem_id(rs.getString(2));
				test.setTest_name(rs.getString(3));
				test.setTest_pw(rs.getString(4));
				test.setTest_openYN(rs.getString(5));
				test.setSub_name(rs.getString(6));
				test.setTest_hit(rs.getInt(7));
			}	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		    try {
		    	if (rs != null) {
		    		rs.close();
		        }
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		return test;
	}

	

	
	
	
	
}

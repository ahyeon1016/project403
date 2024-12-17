package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.domain.Test;

@Repository
public class TestRepositoryImpl implements TestRepository {
	
	//전역변수
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 페이징 처리 Read All
	@Override
	public List<Test> getBoardList(Integer pageNum, int limit) {
		
//		int total_record = getListCount();
	    int start = (pageNum - 1) * limit;
//	    int index = start + 1;
	    
	    List<Test> list = new ArrayList<Test>();	    
	    
	    try {
	    	conn = DBConnection.getConnection();
	    	//SQL쿼리 전송
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
                test.setSub_chap(rs.getString(7));
                test.setTest_hit(rs.getInt(8));
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
			//SQL쿼리 전송
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
		
		String[] serial = test.getSerial();
		String serials = serial[0];
		for(int i =1; i<serial.length;i++) {
			serials = serials+","+serial[i];
		}
		
		try {
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "INSERT INTO Test(mem_id, test_name, test_pw, test_openYN, sub_name, sub_chap, serial) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,test.getMem_id());
			pstmt.setString(2,test.getTest_name());
			pstmt.setString(3,test.getTest_pw());
			pstmt.setString(4,test.getTest_openYN());
			pstmt.setString(5,test.getSub_name());
			pstmt.setString(6,test.getSub_chap());
			pstmt.setString(7,serials);
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
				test.setSub_chap(rs.getString(7));
				test.setTest_hit(rs.getInt(8));
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
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "UPDATE Test SET test_name=?, test_pw=?, test_openYN=?, sub_name=?, sub_chap=? WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, test.getTest_name());
			pstmt.setString(2, test.getTest_pw());
			pstmt.setString(3, test.getTest_openYN());
			pstmt.setString(4, test.getSub_name());
			pstmt.setString(5, test.getSub_chap());
			pstmt.setInt(6, test.getTest_num());
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
			conn = DBConnection.getConnection();
			//SQL쿼리 전송: 조회수 증가
			String sql1 = "UPDATE Test SET test_hit = test_hit+1 WHERE test_num=?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, test_num);
			pstmt.executeUpdate();
			//SQL쿼리 전송: test_num에 해당하는 상세 내용 가져오기
			String sql = "SELECT * FROM Test WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, test_num);
			rs = pstmt.executeQuery();			
			
			if(rs.next()) {		
				String[] serial_split = rs.getString(9).split(",");
				test.setTest_num(rs.getInt(1));
				test.setMem_id(rs.getString(2));
				test.setTest_name(rs.getString(3));
				test.setTest_pw(rs.getString(4));
				test.setTest_openYN(rs.getString(5));
				test.setSub_name(rs.getString(6));
				test.setSub_chap(rs.getString(7));
				test.setTest_hit(rs.getInt(8));
				test.setSerial(serial_split);
				test.setVisible(rs.getBoolean(10));
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

	// test_num에 해당하는 행 값을 가져오기
	@Override
	public Test getTestValue(Integer test_num) {
		
		Test test = new Test();
		
		try {
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
			String sql = "SELECT * FROM Test WHERE test_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, test_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				test.setTest_num(rs.getInt(1));
				test.setMem_id(rs.getString(2));
				test.setTest_name(rs.getString(3));
				test.setTest_pw(rs.getString(4));
				test.setTest_openYN(rs.getString(5));
				test.setSub_name(rs.getString(6));
				test.setSub_chap(rs.getString(7));
				test.setTest_hit(rs.getInt(8));
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

	// Subject 테이블 sub_name 칼럼값 가져오기(중복제외)
	@Override
	public List<Subject> getSubList() {
		
		List<Subject> list = new ArrayList<Subject>();
	    
	    try {
	    	conn = DBConnection.getConnection();
	    	//SQL쿼리 전송
	    	String sql = "SELECT DISTINCT sub_name FROM Subject";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
            while (rs.next()) {
            	Subject subject = new Subject();
            	subject.setSub_name(rs.getString(1));
                list.add(subject);
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

	// Subject 테이블 sub_name(과목)으로 해당하는 sub_chap(챕터) 값 가져오기(중복제외)
	@Override
	public List<Subject> subValue(String sub_name) {
		
		List<Subject> list = new ArrayList<Subject>();
		
		try {
	    	conn = DBConnection.getConnection();
	    	//SQL쿼리 전송
	    	String sql = "SELECT DISTINCT sub_chap FROM Subject WHERE sub_name=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, sub_name);
	        rs = pstmt.executeQuery();
	        
            while (rs.next()) {
            	Subject subject = new Subject();
            	subject.setSub_chap(rs.getString(1));
                list.add(subject);
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

	// Question 테이블 sub_code_sum(과목챕터코드)=subCodeSum 일치하는 모든 값 가져오기
	@Override
	public List<Question> qnaSelectValue(String subCodeSum) {
		
		List<Question> list = new ArrayList<Question>();
		
		try {
	    	conn = DBConnection.getConnection();
	    	//SQL쿼리 전송
	    	String sql = "SELECT * FROM Question WHERE sub_code_sum=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, subCodeSum);
	        rs = pstmt.executeQuery();
	        
            while(rs.next()) {
            	Question question = new Question();
            	question.setQuestion_num(rs.getInt(1));
            	question.setQuestion_content(rs.getString(2));
            	question.setQuestion_ans(rs.getString(3));
            	question.setQuestion_img_name(rs.getString(4));
            	question.setQuestion_plus(rs.getInt(5));
            	question.setQuestion_count(rs.getInt(6));
            	question.setSub_code_sum(rs.getString(7));
            	question.setMem_serial(rs.getInt(8));
            	question.setQuestion_serial(rs.getString(9));
            	question.setQuestion_id(rs.getString(10));
            	
                list.add(question);
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

	// sub_code_sum(과목탭터코드) 값에 해당하는 question_ans(정답) 값을 Question 테이블에서 가져오기
	@Override
	public List<String> ansSelectValue(String subCodeSum) {
		
		List<String> list = new ArrayList<String>();
		
		try {
			conn = DBConnection.getConnection();
			//SQL쿼리 전송
	    	String sql = "SELECT question_ans FROM Question WHERE sub_code_sum=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, subCodeSum);
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	        	String ans = rs.getString(1);
	        	String[] ansList = ans.split("\\|★\\|");
	        	
	        	list =  Arrays.asList(ansList);
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

	// serial(과목챕터코드) 해당하는 문제를 Question 테이블에서 가져오기
	@Override
	public List<Question> getQuestion(Test test) {
		
		List<Question> list = new ArrayList<Question>();
		
		String[] serial = test.getSerial();
//		String serials = serial[0];
//		for(int i =1; i<serial.length; i++) {
//			serials = serials+","+serial[i];
//		}
//		
//		String[] serial_split = serials.split(",");
		
		try {
			conn = DBConnection.getConnection();
			for(int i = 0; i < serial.length; i++) {
				//SQL쿼리 전송
				String sql = "SELECT * FROM Question WHERE question_serial=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, serial[i]);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Question question = new Question();
		        	question.setQuestion_num(rs.getInt(1));
		        	question.setQuestion_content(rs.getString(2));
		        	question.setQuestion_ans(rs.getString(3));
		        	question.setQuestion_img_name(rs.getString(4));
		        	question.setQuestion_plus(rs.getInt(5));
		        	question.setQuestion_count(rs.getInt(6));
		        	question.setSub_code_sum(rs.getString(7));
		        	question.setMem_serial(rs.getInt(8));
		        	question.setQuestion_serial(rs.getString(9));
		        	question.setQuestion_id(rs.getString(10));
		        	
		            list.add(question);
				}
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

	
	
	
	
	
}

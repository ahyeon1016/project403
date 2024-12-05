package page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.spring.domain.Test;
import com.spring.repository.DBConnection;

public class Repository {
	//전역변수
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		public int getListCount() {
			int count = 0;
			try {
				conn = DBConnection.getConnection();
				String sql = "SELECT COUNT(*) FROM Test";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if (rs.next()) 
					count = rs.getInt(1);
				
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
		
		public ArrayList<Test> getBoardList(int page, int limit) {
		    int total_record = getListCount(); // 9
		    int start = (page - 1) * limit;
		    int index = start + 1;
		    
		    ArrayList<Test> list = new ArrayList<Test>();	    
		    
		    try {
		    	conn = DBConnection.getConnection();
		    	
		    	String sql = "SELECT * FROM Test ORDER BY test_num DESC LIMIT ? OFFSET ?";		    	
		        pstmt = conn.prepareStatement(sql); 		        
		        pstmt.setInt(1, limit);
		        pstmt.setInt(2, start);		        
		        rs = pstmt.executeQuery();
		        
	            while (rs.next()) {
	                Test test = new Test();
	                test.setTest_num(rs.getInt(1));
	                test.setMem_id(rs.getString(2));
	                test.setTest_date(rs.getString(3));
	                test.setTest_time(rs.getString(4));
	                test.setTest_name(rs.getString(5));
	                list.add(test);
	                
	                if (index < (start + limit) && index <= total_record) {
						index++;
	                } else {
						break;
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

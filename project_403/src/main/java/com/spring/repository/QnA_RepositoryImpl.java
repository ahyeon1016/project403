package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.spring.domain.QnA;

@Repository
public class QnA_RepositoryImpl implements QnA_Repository{

	//CommentRoot를 DB에 추가 (Create)
	@Override
	public void addCommentRoot(QnA qna, String mem_id) {
		System.out.println("리파지토리 | addCommentRoot() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송 
			qna.setComment_root(getCommentDepth("comment_root"));
			String SQL = "INSERT INTO QnA VALUES(NULL, ?, ?, ?, 0, 0, ?, ?, ?, 0)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, qna.getQuestion_serial());
			pstmt.setInt(3, qna.getComment_root());
			pstmt.setString(4, qna.getComment_title());
			pstmt.setString(5, qna.getComment_content());
			pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));  //현재시간을 넣는다.
			
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
			String SQL = 
					"SELECT * FROM QnA "
					+ "WHERE comment_parent=0 AND comment_child=0 "
					+ "ORDER BY comment_num DESC";
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

	//comment_root와 일치하고 comment_parent와 comment_child가 0인 컬럼을 가져오는 함수 (Read)
	@Override
	public QnA getCommentRootOne(int comment_root) {
		System.out.println("리파지토리 | getCommentRootOne() 도착");
		QnA qna = new QnA();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//페이지가 로드되었을 때 조회수를 상승시키는 함수로 이동 
			commentHitUp(comment_root);
			//쿼리전송
			String SQL = 
					"SELECT * FROM QnA "
					+ "WHERE comment_root=? AND comment_parent=0 AND comment_child=0";
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
	
	//View 페이지에서 작성된 댓글을 비동기 POST 요청으로 받아 데이터를 DB에 넣는 함수(Read, Create)
	//comment_parent를 DB에 추가하는 함수 (Create) 
	@Override
	public HashMap<String, Object> addCommentParent(HashMap<String, Object> map) {
		System.out.println("리파지토리 | addCommentParent() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(Read) 질문글을 남기면 comment_parent의 값은 0으로 들어가기 때문에 최대값에서 +1을 한 결과를 가져온다. 
			String SQL_SELECT ="SELECT MAX(comment_parent)+1 FROM QnA WHERE comment_root=?";
			pstmt = conn.prepareStatement(SQL_SELECT);
			pstmt.setInt(1, (Integer) map.get("comment_root"));
			
			rs = pstmt.executeQuery();
			//comment_parent 변수 설정
			int parent = 0;
			if(rs.next()) {
				parent = rs.getInt(1);
			}
			
			//쿼리전송(Create)
			Timestamp stamp = new Timestamp(System.currentTimeMillis());
			String SQL_INSERT = "INSERT INTO QnA VALUES(NULL, ?, ?, ?, ?, 0, NULL, ?, ?, 0)";
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, (String) map.get("mem_id"));
			pstmt.setString(2, (String) map.get("question_serial"));
			pstmt.setInt(3, (Integer) map.get("comment_root"));
			pstmt.setInt(4, parent);
			pstmt.setString(5, (String) map.get("comment_content"));
			pstmt.setTimestamp(6, stamp);	//현재 시간
			
			pstmt.executeUpdate();
			
			System.out.println("리파지토리 | addCommentParent() comment_parent 추가 완료");
			map.put("time", stamp.toString());
			map.put("comment_parent", parent);
			map.put("success", true);			//성공 여부를 확인하여 JavaScript에서 조건식으로 사용하기 위함
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return map;
	}
	
	//DB에서 comment_root에 해당하는 모든 댓글과 대댓글의 데이터를 가지고 돌아가는 함수 (Read)
	//comment_root의 comment_parent, comment_child들을 ArrayList에 담고 맵에 넣어 리턴하는 함수(Read)
	@Override
	public HashMap<String, ArrayList<QnA>> getCommentParent(int comment_root) {
		System.out.println("리파지토리 | addCommentParent() 도착");
		HashMap<String, ArrayList<QnA>> returnMap = new HashMap<String, ArrayList<QnA>>();
		ArrayList<QnA> list = new ArrayList<QnA>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(Read)  comment_root에 해당하고 comment_parent의 값이 0인 모든 데이터를 가져오는 쿼리문 
			String SQL =
					"SELECT * FROM QnA "
					+ "WHERE comment_root=? AND NOT comment_parent=0 "
					+ "ORDER BY comment_parent  ASC";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, comment_root);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QnA qna = new QnA();
				qna.setComment_num(rs.getInt(1));
				qna.setMem_id(rs.getString(2));
				qna.setQuestion_serial(rs.getString(3));
				qna.setComment_root(rs.getInt(4));
				qna.setComment_parent(rs.getInt(5));
				qna.setComment_child(rs.getInt(6));
				qna.setComment_content(rs.getString(8));
				qna.setComment_date(rs.getTimestamp(9));
				list.add(qna);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println("리파지토리 | getCommentParent() list의 크기 : "+list.size());
		returnMap.put("list", list);
		return returnMap;
	}
	
	//View 페이지에서 작성된 대댓글을 비동기 POST 요청으로 받아 데이터를 DB에 넣는 함수(Read, Create)
	//commit_child를 DB에 추가하는 함수 (Create)
	@Override
	public HashMap<String, Object> addCommentChild(HashMap<String, Object> map) {
		System.out.println("리파지토리 | addCommentChild() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(Read) 질문글이나 댓글을 남기면 comment_child의 값은 0으로 들어가기 때문에 최대값에서 +1을 한 결과를 가져온다. 
			String SQL_SELECT =
					"SELECT MAX(comment_child)+1 "
					+ "FROM QnA "
					+ "WHERE comment_root=? AND comment_parent=?";
			pstmt = conn.prepareStatement(SQL_SELECT);
			pstmt.setInt(1, (Integer) map.get("comment_root"));
			pstmt.setInt(2, (Integer) map.get("comment_parent"));
			
			rs = pstmt.executeQuery();
			//comment_child값 설정
			int child = 0;
			if(rs.next()) {
				child = rs.getInt(1);
			}
			System.out.println("리파지토리 | addCommentChild() Read 완료");
			
			//쿼리전송(Create)
			Timestamp stamp = new Timestamp(System.currentTimeMillis());
			String SQL_INSERT = "INSERT INTO QnA VALUES(NULL, ?, ?, ?, ?, ?, NULL, ?, ?, 0)";
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, (String) map.get("mem_id"));
			pstmt.setString(2, (String) map.get("question_serial"));
			pstmt.setInt(3, (Integer) map.get("comment_root"));
			pstmt.setInt(4, (Integer) map.get("comment_parent"));
			pstmt.setInt(5, child);
			pstmt.setString(6, (String) map.get("comment_content"));
			pstmt.setTimestamp(7, stamp);		//현재 시간
			
			pstmt.executeUpdate();
			
			System.out.println("리파지토리 | addCommentChild() comment_child 추가 완료");
			map.put("time", stamp.toString());
			map.put("comment_child", child);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return map;
	}
	
	//사용자가 댓글의 삭제를 요청했을 때 DB에서 제거되는 것이 아닌 comment_content의 데이터를 변경하기 위함(Update)
	//DB에서 comment_parent의 content 값을 변경하므로써 지우는 함수(Update)
	@Override
	public HashMap<String, Object> removeCommentParent(HashMap<String, Object> map) {
		System.out.println("리파지토리 | removeCommentParent() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			System.out.println(map.get("mem_id"));
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(Read) Update 전에 해당 데이터가 DB에 존재하는지 먼저 확인한다.
			String SQL_SELECT = 
					"SELECT * "
					+ "FROM QnA "
					+ "WHERE mem_id=? AND question_serial=? AND comment_root=? "
					+ "AND comment_parent=? AND comment_child=0";
			pstmt = conn.prepareStatement(SQL_SELECT);
			pstmt.setString(1, (String)map.get("mem_id"));
			pstmt.setString(2, (String)map.get("question_serial"));
			pstmt.setInt(3, (Integer)map.get("comment_root"));
			pstmt.setInt(4, (Integer)map.get("comment_parent"));
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//쿼리전송(Update) 기존 댓글의 내용인 comment_content의 데이터를 pDel로 수정한다.
				String SQL_UPDATE =
						"UPDATE QnA "
						+ "SET comment_content='pDel' "
						+ "WHERE mem_id=? AND question_serial=? AND comment_root=? "
						+ "AND comment_parent=? AND comment_child=0";
				pstmt = conn.prepareStatement(SQL_UPDATE);
				pstmt.setString(1, (String)map.get("mem_id"));
				pstmt.setString(2, (String)map.get("question_serial"));
				pstmt.setInt(3, (Integer)map.get("comment_root"));
				pstmt.setInt(4, (Integer)map.get("comment_parent"));
				
				pstmt.executeUpdate();
				System.out.println("리파지토리 | removeCommentParent() UPDATE 성공");
				map.put("success", true);
			}else {
				System.out.println("리파지토리 | removeCommentParent() UPDATE 실패 일치하지 않음.");
				map.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return map;
	}
	
	//사용자가 대댓글의 삭제를 요청했을 때 DB에서 제거되는 것이 아닌 comment_content의 데이터를 변경하기 위함(Update)
	//DB에서 comment_child의 content 값을 변경하므로써 지우는 함수(Update)
	@Override
	public HashMap<String, Object> removeCommentChild(HashMap<String, Object> map) {
		System.out.println("리파지토리 | removeCommentChild() 도착");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송(Read)  Update 전에 해당 데이터가 DB에 존재하는지 먼저 확인한다.
			String SQL_SELECT = 
					"SELECT * "
					+ "FROM QnA "
					+ "WHERE mem_id=? AND question_serial=? AND comment_root=? "
					+ "AND comment_parent=? AND comment_child=?";
			pstmt = conn.prepareStatement(SQL_SELECT);
			pstmt.setString(1, (String)map.get("mem_id"));
			pstmt.setString(2, (String)map.get("question_serial"));
			pstmt.setInt(3, (Integer)map.get("comment_root"));
			pstmt.setInt(4, (Integer)map.get("comment_parent"));
			pstmt.setInt(5, (Integer)map.get("comment_child"));
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//쿼리전송(Update) 기존 대댓글의 내용인 comment_content의 데이터를 cDel로 수정한다.
				String SQL_UPDATE = "UPDATE QnA "
						+ "SET comment_content='cDel' "
						+ "WHERE mem_id=? AND question_serial=? AND comment_root=? "
						+ "AND comment_parent=? AND comment_child=?";
				pstmt = conn.prepareStatement(SQL_UPDATE);
				pstmt.setString(1, (String)map.get("mem_id"));
				pstmt.setString(2, (String)map.get("question_serial"));
				pstmt.setInt(3, (Integer)map.get("comment_root"));
				pstmt.setInt(4, (Integer)map.get("comment_parent"));
				pstmt.setInt(5, (Integer)map.get("comment_child"));
	
				pstmt.executeUpdate();
				System.out.println("리파지토리 | removeCommentChild() UPDATE 성공");
				map.put("success", true);
			}else {
				System.out.println("리파지토리 | removeCommentChild() UPDATE 실패 일치하지 않음.");
				map.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return map;
		
	}
	
	//comment_root의 값을 설정하는 함수
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
			//쿼리전송	                  변수처리
			String SQL = "SELECT MAX("+name+") FROM QnA";
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				//최초 작성시
				depth = 1;
			}else {
				//데이터가 존재한다면
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

	//comment_hit(조회수) 추가 함수 (Update)
	private void commentHitUp(int comment_root) {
		System.out.println("리파지토리 | commentHitUp() 도착");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//DB연결
			conn = DBConnection.getConnection();
			//쿼리전송 comment_hit 값에 +1을 한다.
			String SQL = 
					"UPDATE QnA "
					+ "SET comment_hit=comment_hit+1 "
					+ "WHERE comment_root=? AND comment_parent=0 AND comment_child=0";
			pstmt = conn.prepareStatement(SQL);
			//pstmt.setInt(1, comment_hit+1);
			pstmt.setInt(1, comment_root);
			
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

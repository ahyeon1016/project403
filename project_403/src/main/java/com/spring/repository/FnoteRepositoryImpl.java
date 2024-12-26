package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.mysql.cj.protocol.Resultset;
import com.spring.domain.Fnote;

@Repository
public class FnoteRepositoryImpl implements FnoteRepository {

	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	//테스트 시작시 함께 실행되야함.
	//fnote가 생성되는 기능
	@Override
	public void note_create(String mem_id,int test_num) {
		try {
			conn=DBConnection.getConnection();
			String sql="insert into fnote values(?,?,null)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, test_num);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {e.printStackTrace();}
		
	}
	
	
	//본인 아이디로 작성되어있는 모든 노트 조회
	@Override
	public ArrayList<Fnote> note_mine(String mem_id) {
		ArrayList<Fnote> arr=new ArrayList<Fnote>();
		try {
		conn=DBConnection.getConnection();
		String sql="select * from fnote where mem_id=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, mem_id);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			Fnote fnote=new Fnote();
			fnote.setMem_id(rs.getString("mem_id"));
			fnote.setTest_num(rs.getInt("test_num"));
			String note=rs.getString("note");
			if(note!=null) {
			fnote.setNote(note.split(","));
			}
			else {fnote.setNote(null);}
			arr.add(fnote);
		}
		rs.close();
		pstmt.close();
		conn.close();
		}catch(Exception e) {e.printStackTrace();}
		return arr;
	}
	
	//테스트 번호와 맞는 노트를 조회
	@Override
	public Fnote note_by_testnum(int test_num) {
		Fnote fnote=new Fnote();
		try {
		conn=DBConnection.getConnection();
		String sql="select * from Fnote where test_num=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, test_num);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			fnote.setMem_id(rs.getString("mem_id"));
			fnote.setTest_num(rs.getInt("test_num"));
			String[] note=rs.getString("note").split(",");
			fnote.setNote(note);
		}
		rs.close();
		pstmt.close();
		conn.close();
		}catch(Exception e) {e.printStackTrace();}
		return fnote;
	}
	
	//테스트번호와 mem_id가 맞는 로우에 note 쓰기
	@Override
	public void note_update(String mem_id,int test_num,String note) {
		try {
		conn=DBConnection.getConnection();
		String sql="update fnote set note=? where mem_id=? and test_num=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, note);
		pstmt.setString(2, mem_id);
		pstmt.setInt(3, test_num);
		pstmt.executeUpdate();
		}catch(Exception e) {e.printStackTrace();}
	}
	
	//테스트번호와 mem_id가 맞는 row 삭제
	@Override
	public void note_delete(String mem_id,int test_num) {
		try {
			conn=DBConnection.getConnection();
			String sql="delete from fnote where mem_id=? and test_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, test_num);
			pstmt.executeUpdate();
		}catch(Exception e) {e.printStackTrace();}
	}

	//회원 탈퇴시 모든 노트 삭제!
	@Override
	public void all_note_delete(String mem_id) {
		try {
			conn=DBConnection.getConnection();
			String sql="delete from fnote where mem_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.executeUpdate();
		}catch(Exception e) {e.printStackTrace();}
		
	}
	
	
}

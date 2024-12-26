package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.spring.domain.Member;
import com.spring.domain.Member_Item;
@Repository
public class MemberItemRepositoryImpl implements MemberItemRepository{

	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	
	//insert 쿼리문:회원가입시 함께 실행됨.
	public void addMemItem(Member member) {
		try{
			System.out.println("아이템 추가요~~~~~~~~~~"+member.getMem_id());
			conn=DBConnection.getConnection();
			String sql="insert into member_item(mem_id) values(?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,member.getMem_id() );
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch(Exception e) {e.printStackTrace();}
	}

	//멤버 아이디로 아이템 목록 조회
	@Override
	public Member_Item mem_item_info(String mem_id) {
		Member_Item mi=new Member_Item();
		try {
			conn=DBConnection.getConnection();
			String sql="select * from Member_Item where mem_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mi.setMem_color(rs.getString("mem_color"));
				mi.setMem_id(mem_id);
				mi.setMem_itemA(rs.getBoolean("mem_itemA"));
				mi.setMem_itemB(rs.getBoolean("mem_itemB"));
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {e.printStackTrace();}
		
		return mi;
	}
	//닉네임 변경시 회원의 아이템 A정보 수정
	@Override
	public void nick_change(String mem_id) {
		try{
			conn=DBConnection.getConnection();
			String sql="update Member_Item set mem_itemA=false where mem_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {e.printStackTrace();}
	}
	//닉네임 색상 변경
	@Override
	public void color_change(Member_Item mi) {
		try {
		conn=DBConnection.getConnection();
		String sql="update Member_Item set mem_itemB=false,mem_color=? where mem_id=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, mi.getMem_color());
		pstmt.setString(2, mi.getMem_id());
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		}catch(Exception e) {e.printStackTrace();}
	}
	//회원탈퇴시 member_item테이블에서도 삭제!
	public void item_bye(Member member) {
		try {
			conn=DBConnection.getConnection();
			String sql="delete from Member_Item where mem_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, member.getMem_id());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch(Exception e) {e.printStackTrace();}
	}
	
}

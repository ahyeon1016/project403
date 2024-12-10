package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.spring.domain.Member;
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
	
}

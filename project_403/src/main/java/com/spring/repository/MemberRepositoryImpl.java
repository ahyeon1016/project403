package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.spring.domain.Member;
@Repository
public class MemberRepositoryImpl implements MemberRepository {
	
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	//insert 쿼리문 
	public void addMember(Member member) {
		System.out.println("회원가입 ");
		try {
		conn=DBConnection.getConnection();
		String sql="insert into member(mem_id,mem_pw,mem_nickName) values (?,?,?)";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, member.getMem_id());
		pstmt.setString(2, member.getMem_pw());
		pstmt.setString(3, member.getMem_nickName());
		pstmt.executeUpdate();
		
		}catch (Exception e) {e.printStackTrace();}
	}
	//read one 쿼리문
	@Override
	public Member getMyPage(String mem_id) {
		System.out.println("셀렉"+mem_id);
		Member member=new Member();
		
		try {
			conn=DBConnection.getConnection();
			String sql="SELECT * FROM Member WHERE mem_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
			System.out.println("호호이");
			member.setMem_id(rs.getString(1));
			member.setMem_pw(rs.getString(2));
			member.setMem_nickName(rs.getString(3));
			member.setMem_point(rs.getInt(4));
			member.setMem_exp(rs.getInt(5));
			}else {
				System.out.println("그딴거 없는뎅?");
				return null;}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return member;
	}
	
}

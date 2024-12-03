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
		
		pstmt.close();
		conn.close();
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
			pstmt.close();
			conn.close();
			}else {
				System.out.println("그딴거 없는뎅?");
				pstmt.close();
				conn.close();
				return null;}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return member;
	}
	
	//로그인 기능
	@Override
	public Member member_login(Member member) {
		
		try{conn=DBConnection.getConnection();
		String sql="select mem_id,mem_nickName from member where mem_id=? and mem_pw=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, member.getMem_id());
		pstmt.setString(2, member.getMem_pw());
		rs=pstmt.executeQuery();
		if(rs.next()) {
			member.setMem_id(rs.getString(1));
			member.setMem_nickName(rs.getString(2));
			
		}else {
			return null;
		}
		pstmt.close();
		conn.close();
		}catch(Exception e) {e.printStackTrace();}
		return member;
	}
		
}

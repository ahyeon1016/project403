package com.spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.random.RandomGenerator;

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
		String sql="insert into member(mem_id,mem_pw,mem_nickName,mem_email,mem_serial) values (?,?,?,?,?)";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, member.getMem_id());
		pstmt.setString(2, member.getMem_pw());
		pstmt.setString(3, member.getMem_nickName());
		pstmt.setString(4, member.getMem_email());
		double random=Math.random()*900000;
		int ran=(int)random;
		pstmt.setString(5,String.valueOf(ran));
		System.out.println(ran);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		}catch (Exception e) {e.printStackTrace();}
	}
	//read one 쿼리문
	@Override
	public Member getMyInfo(String mem_id) {
		System.out.println("셀렉"+mem_id);
		Member member=new Member();
		
		try {
			conn=DBConnection.getConnection();
			String sql="SELECT * FROM Member WHERE mem_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
			System.out.println("member에 select한 데이터 넣기");
			member.setMem_id(rs.getString(1));
			member.setMem_pw(rs.getString(2));
			member.setMem_nickName(rs.getString(3));
			member.setMem_point(rs.getInt(4));
			member.setMem_exp(rs.getInt(5));
			member.setMem_admin(rs.getBoolean(6));
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
		
		try{
		conn=DBConnection.getConnection();
		String sql="select * from member where mem_id=? and mem_pw=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, member.getMem_id());
		pstmt.setString(2, member.getMem_pw());
		rs=pstmt.executeQuery();
		if(rs.next()) {
			member.setMem_id(rs.getString("mem_id"));
			member.setMem_nickName(rs.getString("mem_nickName"));
			
		}else {
			return null;
		}
		pstmt.close();
		conn.close();
		}catch(Exception e) {e.printStackTrace();}
		return member;
	}
	//정보 수정 기능
	@Override
	public void member_update(Member member) {
		try{
		conn=DBConnection.getConnection();
		String sql="update Member set mem_pw=?,mem_nickName=? where mem_id=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, member.getMem_pw());
		pstmt.setString(2, member.getMem_nickName());
		pstmt.setString(3, member.getMem_id());
		pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//회원 탈퇴 기능
	@Override
	public void member_delete(Member member) {
		try {
			conn=DBConnection.getConnection();
			String sql="delete from Member where mem_id=? and mem_pw=?";
			pstmt=conn.prepareStatement(sql);
			System.out.println(member.getMem_id());
			System.out.println(member.getMem_pw());
			pstmt.setString(1, member.getMem_id());
			pstmt.setString(2, member.getMem_pw());
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
		
}

package com.spring.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.springframework.stereotype.Repository;

import com.spring.domain.Member;
@Repository
public class MemberRepositoryImpl implements MemberRepository {
	
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	//회원가입
	public void addMember(Member member) {
		System.out.println("회원가입 ");
		try {
		conn=DBConnection.getConnection();
		String sql="insert into member(mem_id,mem_pw,mem_nickName,mem_email,mem_date,mem_profile_name) values (?,?,?,?,?,?)";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, member.getMem_id());
		if(member.getMem_pw()!=null) {
		pstmt.setString(2, member.getMem_pw());}
		else {pstmt.setString(2, null);}
		pstmt.setString(3, member.getMem_nickName());
		if(member.getMem_email()!=null) {
		pstmt.setString(4, member.getMem_email());}
		else {pstmt.setString(4,null);}
		pstmt.setDate(5, new Date(System.currentTimeMillis()));
		if(member.getMem_profile_name()!=null) {
			pstmt.setString(6, null);
		}else{pstmt.setString(6, member.getMem_profile_name());
		}
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		}catch (Exception e) {e.printStackTrace();}
	}
	
	//정보 조회
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
			member.setMem_email(rs.getString("mem_email"));
			member.setMem_confirmed(rs.getBoolean("mem_confirmed"));
			member.setMem_serial(rs.getInt("mem_serial"));
			member.setMem_profile_name(rs.getString("mem_profile_name"));
			rs.close();
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
	
	//모든 회원 정보 조회(관리자 페이지)
	@Override
	public ArrayList<Member> read_all_Member(int limit_num) {
		ArrayList<Member> al=new ArrayList<Member>();
		try {
		conn=DBConnection.getConnection();
		String sql2="select * from Member order by mem_serial asc limit ?,10";
		pstmt=conn.prepareStatement(sql2);
		pstmt.setInt(1, limit_num);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			Member member=new Member();
			member.setMem_id(rs.getString("mem_id"));
			member.setMem_nickName(rs.getString("mem_nickName"));
			member.setMem_point(rs.getInt("mem_point"));
			member.setMem_exp(rs.getInt("mem_exp"));
			member.setMem_email(rs.getString("mem_email"));
			member.setMem_date(rs.getDate("mem_date"));
			member.setMem_serial(rs.getInt("mem_serial"));
			al.add(member);
		}
		
		}catch(Exception e) {e.printStackTrace();}
		return al;
	}
	
	//가입한 회원의 숫자를 조회
	@Override
	public int mem_num() {
		int num=0;
		try {
			conn=DBConnection.getConnection();
			String sql="select count(*) from member";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				num=rs.getInt("count(*)");
			}
		}catch (Exception e) {e.printStackTrace();}
		return num;
	}
	
	//관리자 페이지에서 멤버 검색
//	@Override
//	public ArrayList<Member> search_admin(String search_data) {
//		ArrayList<Member> al=new ArrayList<Member>();
//		try {
//			conn=DBConnection.getConnection();
//			String sql="select * from Member where mem_id like ?";
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, "%"+search_data+"%");
//			rs=pstmt.executeQuery();
//			
//			while(rs.next()) {
//				Member mb=new Member();
//				mb.setMem_id(rs.getString("mem_id"));
//				mb.setMem_nickName(rs.getString("mem_nickName"));
//				mb.setMem_point(rs.getInt("mem_point"));
//				mb.setMem_exp(rs.getInt("mem_exp"));
//				mb.setMem_email(rs.getString("mem_email"));
//				mb.setMem_date(rs.getDate("mem_date"));
//				mb.setMem_serial(rs.getInt("mem_serial"));
//				al.add(mb);
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//		return al;
//	}
	
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
			member.setMem_email(rs.getString("mem_email"));
			member.setMem_serial(rs.getInt("mem_serial"));
			member.setMem_admin(rs.getBoolean("mem_admin"));
			System.out.println(rs.getBoolean("mem_admin")+"admin인가용??????");
			member.setMem_nickName(rs.getString("mem_nickName"));
			member.setMem_point(rs.getInt("mem_point"));
			member.setMem_exp(rs.getInt("mem_exp"));
			member.setMem_confirmed(rs.getBoolean("mem_confirmed"));
			member.setMem_profile_name(rs.getString("mem_profile_name"));
			member_log_date(member);
		}else {
			return null;
		}
		rs.close();
		pstmt.close();
		conn.close();
		}catch(Exception e) {e.printStackTrace();}
		return member;
	}
	

	

	//로그인 할때 마지막 접속날짜 업데이트
	public void member_log_date(Member member) {
		try{
		conn=DBConnection.getConnection();
		String sql="update Member set mem_date=? where mem_id=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setDate(1, new Date(System.currentTimeMillis()));
		pstmt.setString(2, member.getMem_id());
		pstmt.executeUpdate();
		}catch (Exception e) {e.printStackTrace();}
	}
	
	
	//카카오 로그인시 데이터 조회
	public boolean kakao_info(Member member) {
		boolean is_client=false;
		try {
		conn=DBConnection.getConnection();
		String sql="select * from Member where mem_id=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, member.getMem_id());
		rs=pstmt.executeQuery();
		if(rs.next()) {
			is_client=true;
			member_log_date(member);
		}
		}
		catch(Exception e) {e.printStackTrace();}
		return is_client;
	}
	
	
	//네이버 로그인시 데이터 조회
	public boolean naver_info(Member member) {
		boolean is_client=false;
		try {
			conn=DBConnection.getConnection();
			String sql="select * from Member where mem_id=? and mem_email=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, member.getMem_id());
			pstmt.setString(2, member.getMem_email());
			rs=pstmt.executeQuery();
				if(rs.next()) {
					member_log_date(member);
					is_client=true;
				}
			rs.close();
			pstmt.close();
			conn.close();
			}catch(Exception e) {e.printStackTrace();}
		
		return is_client;
		}
	

	//멤버의 이메일로 시리얼을 조회
	@Override
	public int mem_serial(String user_mail,String user_id) {
		int mem_serial=0;
		try {
		conn=DBConnection.getConnection();
		String sql="select mem_serial from Member where mem_email=? and mem_id=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, user_mail);
		pstmt.setString(2, user_id);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			mem_serial=rs.getInt("mem_serial");
			System.out.println(mem_serial);
		}
		}catch(Exception e) {e.printStackTrace();}
		return mem_serial;
	}
	
	@Override
	public void mem_confirm(int mem_serial) {
		try {
			conn=DBConnection.getConnection();
			String sql="update Member set mem_confirmed=true where mem_serial=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, mem_serial);
			pstmt.executeUpdate();
		}catch (Exception e) {e.printStackTrace();}
		
	}

	//정보 수정 기능
	@Override
	public void member_update(Member member) {
		try{
		conn=DBConnection.getConnection();
		System.out.println(member.getMem_id());
		System.out.println(member.getMem_pw());
		System.out.println(member.getMem_email());
		System.out.println(member.getMem_nickName());
		System.out.println(member.getMem_profile_name());
		String sql="update Member set mem_pw=?,mem_nickName=?,mem_email=?,mem_profile_name=? where mem_id=?";
		pstmt=conn.prepareStatement(sql);
		if(member.getMem_pw()==null) {
			pstmt.setString(1, null);
		}
		else {	pstmt.setString(1, member.getMem_pw());}
		pstmt.setString(2, member.getMem_nickName());
		if(member.getMem_email()==null) {
			pstmt.setString(3, null);
		}
		else {	pstmt.setString(3, member.getMem_email());}
		System.out.println(member.getMem_profile_name());
		if(member.getMem_profile_name()==null) {
			pstmt.setString(4, null);
		}
		else{ pstmt.setString(4, member.getMem_profile_name());}
		pstmt.setString(5, member.getMem_id());
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
			String sql="delete from Member where mem_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, member.getMem_id());
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
		
}

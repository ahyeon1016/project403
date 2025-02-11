package com.spring.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.spring.domain.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
   
   Connection conn=null;
   PreparedStatement pstmt=null;
   ResultSet rs=null;
   
   //회원 id를 통한 정보 조회
      @Override
      public Member getMyInfo(String mem_id) {
         System.out.println("셀렉"+mem_id);
         Member member=new Member();
         ArrayList<String> arr=new ArrayList<String>();

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
            member.setMem_date(rs.getDate("mem_date"));
            member.setMem_alarm(rs.getString("mem_alarm"));
            if(rs.getString("mem_alarm")!=null) {
               String alm=rs.getString("mem_alarm");
               String[] almlist=alm.split(",");
               for(int i=0;i<almlist.length;i++) {
                  arr.add(almlist[i]);
               }
            member.setAlarm_list(arr);
            }
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
      
   
   
      //회원가입
      public void addMember(Member member) {
         System.out.println("회원가입 ");
         try {
         conn=DBConnection.getConnection();
         String sql="INSERT INTO Member(mem_id,mem_pw,mem_nickName,mem_email,mem_date,mem_profile_name) VALUES (?,?,?,?,?,?)";
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
   
      //로그인 기능
      @Override
      public Member member_login(Member member) {
         ArrayList<String> arr=new ArrayList<String>();
         try{
         conn=DBConnection.getConnection();
         String sql="SELECT * FROM Member WHERE mem_id=? AND mem_pw=?";
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
            member.setMem_date(rs.getDate("mem_date"));
            member.setMem_alarm(rs.getString("mem_alarm"));
            if(rs.getString("mem_alarm")!=null) {
               String alm=rs.getString("mem_alarm");
               String[] almlist=alm.split(",");
               for(int i=0;i<almlist.length;i++) {
                  arr.add(almlist[i]);
               }
            member.setAlarm_list(arr);
            }
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
         String sql="UPDATE Member SET mem_date=? WHERE mem_id=?";
         pstmt=conn.prepareStatement(sql);
         pstmt.setDate(1, new Date(System.currentTimeMillis()));
         pstmt.setString(2, member.getMem_id());
         pstmt.executeUpdate();
         pstmt.close();
         conn.close();
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
         String sql="UPDATE Member SET mem_pw=?,mem_nickName=?,mem_email=?,mem_profile_name=? WHERE mem_id=?";
         pstmt=conn.prepareStatement(sql);
         if(member.getMem_pw()==null) {
            pstmt.setString(1, null);
         }
         else {   pstmt.setString(1, member.getMem_pw());}
         pstmt.setString(2, member.getMem_nickName());
         if(member.getMem_email()==null) {
            pstmt.setString(3, null);
         }
         else {   pstmt.setString(3, member.getMem_email());}
         System.out.println(member.getMem_profile_name());
         if(member.getMem_profile_name()==null) {
            pstmt.setString(4, null);
         }
         else{ pstmt.setString(4, member.getMem_profile_name());}
         pstmt.setString(5, member.getMem_id());
         pstmt.executeUpdate();
         pstmt.close();
         conn.close();
         }
         catch (Exception e) {
            e.printStackTrace();
         }
         
      }
      
      //회원 탈퇴 기능
      @Override
      public void member_delete(Member member) {
         try {
            conn=DBConnection.getConnection();
            String sql="DELETE FROM Member WHERE mem_id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, member.getMem_id());
            pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
         }catch(Exception e) {
            e.printStackTrace();
         }
         
      }
      
      //네이버 로그인시 데이터 조회
      public boolean naver_info(Member member) {
         boolean is_client=false;
         try {
            conn=DBConnection.getConnection();
            String sql="SELECT * FROM Member WHERE mem_id=? AND mem_email=?";
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
      
      
      //카카오 로그인시 데이터 조회
      public boolean kakao_info(Member member) {
         boolean is_client=false;
         try {
         conn=DBConnection.getConnection();
         String sql="SELECT * FROM Member WHERE mem_id=?";
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, member.getMem_id());
         rs=pstmt.executeQuery();
         if(rs.next()) {
            is_client=true;
            member_log_date(member);
         }
         rs.close();
         pstmt.close();
         conn.close();
         }
         catch(Exception e) {e.printStackTrace();}
         return is_client;
      }
      
      
      
      //모든 회원 정보 조회(관리자 페이지)
      @Override
      public ArrayList<Member> read_all_Member(int limit_num) {
         ArrayList<Member> al=new ArrayList<Member>();
         try {
         conn=DBConnection.getConnection();
         String sql2="SELECT * FROM Member ORDER BY mem_serial ASC LIMIT ?,10";
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
         rs.close();
         pstmt.close();
         conn.close();
         }catch(Exception e) {e.printStackTrace();}
         return al;
      }
   
      //가입한 회원의 숫자를 조회
      @Override
      public int mem_num() {
         int num=0;
         try {
            conn=DBConnection.getConnection();
            String sql="SELECT COUNT(*) FROM Member";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            if(rs.next()) {
               num=rs.getInt("count(*)");
            }
            rs.close();
            pstmt.close();
            conn.close();
         }catch (Exception e) {e.printStackTrace();}
         return num;
      }
      
   

      //멤버의 이메일로 시리얼을 조회
      @Override
      public int mem_serial(String user_mail,String user_id) {
         int mem_serial=0;
         try {
         conn=DBConnection.getConnection();
         String sql="SELECT mem_serial FORM Member WHERE mem_email=? AND mem_id=?";
         pstmt=conn.prepareStatement(sql);
         pstmt.setString(1, user_mail);
         pstmt.setString(2, user_id);
         rs=pstmt.executeQuery();
         if(rs.next()) {
            mem_serial=rs.getInt("mem_serial");
            System.out.println(mem_serial);
         }
         rs.close();
         pstmt.close();
         conn.close();
         }catch(Exception e) {e.printStackTrace();}
         return mem_serial;
      }

      //이메일 인증 기능
      @Override
      public void mem_confirm(int mem_serial) {
         try {
            conn=DBConnection.getConnection();
            String sql="UPDATE Member SET mem_confirmed=true WHERE mem_serial=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, mem_serial);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
         }catch (Exception e) {e.printStackTrace();}
         
      }
      //닉네임 변경!
      @Override
      public void mem_nickname_change(Member member) {
         try{
            conn=DBConnection.getConnection();
            String sql="UPDATE Member SET mem_nickName=? WHERE mem_id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, member.getMem_nickName());
            pstmt.setString(2, member.getMem_id());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
         }catch(Exception e) {e.printStackTrace();}
         
      } 
      
        //알림 생성
         public void mem_alarm_add(String mem_id,String comment_id, int root) {
            Member member=getMyInfo(mem_id);
            try {
               conn=DBConnection.getConnection();
               String sql="UPDATE Member set mem_alarm=? WHERE mem_id=?";
               pstmt=conn.prepareStatement(sql);
               pstmt.setString(1, member.getMem_alarm()+","+root+"번글에서 "+comment_id+"님이 "+mem_id+"님의 글에 댓글을 달았습니다.");
               pstmt.setString(2, mem_id);
               pstmt.executeUpdate();
            }catch(Exception e) {e.printStackTrace();}
            
         }
      
      //알림 업데이트
      public void mem_alarm_update(Member member) {
         
         try {
            conn=DBConnection.getConnection();
            String sql="UPDATE Member SET mem_alarm=? WHERE mem_id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, member.getMem_alarm());
            pstmt.setString(2, member.getMem_id());
            pstmt.executeUpdate();
         }catch(Exception e) {e.printStackTrace();}
      
      }
      
      
      //Question DTO에서 mem_serial을 가져와 DB에서 mem_nickName을 가지고 리턴하는 함수
      @Override
      public String getNickNameBySerial(int serial) {
         System.out.println("==========================================");
         System.out.println("리파지토리 | getNickNameBySerial() 도착");
         String nickName="";
         try {
            //DB연결
            conn = DBConnection.getConnection();
            
            //쿼리 전송
            String SQL = "SELECT mem_nickName FROM Member WHERE mem_serial=?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, serial);
            
            rs = pstmt.executeQuery();
            //ResultSet에 결과를 담아 문자열에 값 저장
            if(rs.next()) {
               nickName = rs.getString("mem_nickName");
            }
            System.out.println("리파지토리 | getNickNameBySerial() nickName 추출 성공");
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         return nickName;
      }

   
      //문제를 풀 시 포인트,경험치 추가
      public void member_lvup(int point,int exp,String mem_id) {
         try {
         conn=DBConnection.getConnection();
         String sql="UPDATE Member SET mem_point=mem_point+?,mem_exp=mem_exp+? WHERE mem_id=?";
         pstmt=conn.prepareStatement(sql);
         pstmt.setInt(1, point);
         pstmt.setInt(2, exp);
         pstmt.setString(3, mem_id);
         pstmt.executeUpdate();
         pstmt.close();
         conn.close();
         }catch(Exception e) {e.printStackTrace();}
      }

      @Override
      //아이템 구매시 포인트 소모
      public void item_buy(int point,String mem_id) {
         try {
            conn=DBConnection.getConnection();
            String sql="UPDATE Member SET mem_point=mem_point-? WHERE mem_id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,point);
            pstmt.setString(2, mem_id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
         }catch(Exception e) {e.printStackTrace();}
         
      }
}

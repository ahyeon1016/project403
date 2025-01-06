package com.spring.repository;

import java.util.ArrayList;

import com.spring.domain.Fnote;

public interface FnoteRepository {
	void note_create(String mem_id,int test_num);
	ArrayList<Fnote> note_mine(String mem_id);
	Fnote note_by_testnum(int test_num);
	boolean is_my_note(int test_num,String mem_id);
	void note_update(String mem_id,int test_num,String note);
	void note_delete(String mem_id,int test_num);
	void all_note_delete(String mem_id);

}

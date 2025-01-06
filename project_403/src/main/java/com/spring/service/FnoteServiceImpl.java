package com.spring.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Fnote;
import com.spring.repository.FnoteRepository;

@Service
public class FnoteServiceImpl implements FnoteService {

	@Autowired
	private FnoteRepository fnoteRepository;
	
	
	@Override
	public void all_note_delete(String mem_id) {
		fnoteRepository.all_note_delete(mem_id);
	}


	@Override
	public void note_delete(String mem_id, int test_num) {
		fnoteRepository.note_delete(mem_id,test_num);
	}


	@Override
	public void note_update(String mem_id, int test_num, String note) {
		fnoteRepository.note_update(mem_id,test_num,note);
	}


	@Override
	public Fnote note_by_testnum(int test_num) {
		return fnoteRepository.note_by_testnum(test_num);
	}


	@Override
	public boolean is_my_note(int test_num, String mem_id) {
		return fnoteRepository.is_my_note(test_num, mem_id);
	}


	@Override
	public void note_create(String mem_id, int test_num) {
		fnoteRepository.note_create(mem_id,test_num);
	}


	@Override
	public ArrayList<Fnote> note_mine(String mem_id) {
		return fnoteRepository.note_mine(mem_id);
	}

}

package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Member;
import com.spring.domain.Member_Item;
import com.spring.repository.MemberItemRepository;
@Service
public class MemberItemServiceImpl implements MemberItemService {
	
	@Autowired
	MemberItemRepository memberItemRepository;
	
	@Override
	public void nick_color_buy(String mem_id) {
		memberItemRepository.nick_color_buy(mem_id);
	}

	@Override
	public void nick_change_buy(String mem_id) {
		memberItemRepository.nick_change_buy(mem_id);
	}

	@Override
	public void item_bye(Member member) {
		memberItemRepository.item_bye(member);
	}

	@Override
	public void color_change(Member_Item mi) {
		memberItemRepository.color_change(mi);
		
	}

	@Override
	public void nick_change(String mem_id) {
		memberItemRepository.nick_change(mem_id);
	}

	@Override
	public void addMemItem(Member member) {
		memberItemRepository.addMemItem(member);;
		
	}

	@Override
	public Member_Item mem_item_info(String mem_id) {
		return memberItemRepository.mem_item_info(mem_id);
	}

}

package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Member;
import com.spring.repository.MemberItemRepository;
@Service
public class MemberItemServiceImpl implements MemberItemService {
	
	@Autowired
	MemberItemRepository memberItemRepository;
	
	@Override
	public void addMemItem(Member member) {
		memberItemRepository.addMemItem(member);;
		
	}

}

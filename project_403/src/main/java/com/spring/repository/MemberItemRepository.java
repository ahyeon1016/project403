package com.spring.repository;

import com.spring.domain.Member;
import com.spring.domain.Member_Item;

public interface MemberItemRepository {
	void addMemItem(Member member);
	Member_Item mem_item_info(String mem_id);
	void nick_change(String mem_id);
	void color_change(Member_Item mi);
	void item_bye(Member member);
	void nick_change_buy(String mem_id);
	void nick_color_buy(String mem_id);
}

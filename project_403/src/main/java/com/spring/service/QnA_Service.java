package com.spring.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.domain.QnA;

public interface QnA_Service {
	void addCommentRoot(QnA qna, String mem_id);
	ArrayList<QnA> getCommentRootAll();
	QnA getCommentRootOne(int comment_root);
	HashMap<String, Object> addCommentParent(HashMap<String, Object> map);
	HashMap<String, ArrayList<QnA>> getCommentParent(int comment_root);
	HashMap<String, Object> addCommentChild(HashMap<String, Object> map);
	void removeCommentParent(HashMap<String, Object> map);
	void removeCommentChild(HashMap<String, Object> map);
}

package com.spring.repository;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.domain.QnA;

public interface QnA_Repository {
	void addCommentRoot(QnA qna);
	ArrayList<QnA> getCommentRootAll();
	QnA getCommentRootOne(int comment_root);
	HashMap<String, Object> addCommentParent(HashMap<String, Object> map);
	HashMap<String, ArrayList<QnA>> getCommentParent(int comment_root);
}

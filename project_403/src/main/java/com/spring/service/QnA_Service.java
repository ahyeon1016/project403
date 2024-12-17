package com.spring.service;

import java.util.ArrayList;

import com.spring.domain.QnA;

public interface QnA_Service {
	void addCommentRoot(QnA qna);
	ArrayList<QnA> getCommentRootAll();
	QnA getCommentRootOne(int comment_root);
}

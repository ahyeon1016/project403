package com.spring.repository;

import java.util.ArrayList;

import com.spring.domain.QnA;

public interface QnA_Repository {
	void addCommentRoot(QnA qna);
	ArrayList<QnA> getCommentRootAll();
}
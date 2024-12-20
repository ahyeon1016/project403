package com.spring.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.QnA;
import com.spring.repository.QnA_Repository;

@Service
public class QnA_ServiceImpl implements QnA_Service{

	@Autowired
	QnA_Repository qnaRepository;

	@Override
	public void addCommentRoot(QnA qna) {
		System.out.println("서비스 | addCommentRoot() 호출");
		qnaRepository.addCommentRoot(qna);
	}

	@Override
	public ArrayList<QnA> getCommentRootAll() {
		System.out.println("서비스 | getCommentRootAll() 호출");
		return qnaRepository.getCommentRootAll();	
	}

	@Override
	public QnA getCommentRootOne(int comment_root) {
		System.out.println("서비스 | getCommentRootOne() 호출");
		return qnaRepository.getCommentRootOne(comment_root);	
	}

	@Override
	public HashMap<String, Object> addCommentParent(HashMap<String, Object> map) {
		System.out.println("서비스 | addCommentParent() 호출");
		return qnaRepository.addCommentParent(map);
	}

	@Override
	public HashMap<String, ArrayList<QnA>> getCommentParent(int comment_root) {
		System.out.println("서비스 | getCommentParent() 호출");
		return qnaRepository.getCommentParent(comment_root);
	}

	@Override
	public HashMap<String, Object> addCommentChild(HashMap<String, Object> map) {
		System.out.println("서비스 | addCommentChild() 호출");
		return qnaRepository.addCommentChild(map);
	}
	
	
}

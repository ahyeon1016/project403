package com.spring.service;

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
	
	
}

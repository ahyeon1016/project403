package com.spring.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.repository.Question_Repository;

@Service
public class Question_ServiceImpl implements Question_Service{
	
	@Autowired
	Question_Repository questionRepository;
	
	
	@Override
	public void addMCQ(Question question, int mem_serial) {
		System.out.println("서비스 | addMCQ() 호출");
		questionRepository.addMCQ(question, mem_serial);
	}

	@Override
	public void addSAQ(Question question, int mem_serial) {
		System.out.println("서비스 | addSAQ() 호출");
		questionRepository.addSAQ(question, mem_serial);
	}

	@Override
	public void addCP(Question question, int mem_serial) {
		System.out.println("서비스 | addCP() 호출");
		questionRepository.addCP(question, mem_serial);
	}

	@Override
	public ArrayList<Question> getQuestionAll() {
		System.out.println("서비스 | getQuestionAll() 호출");
		return questionRepository.getQuestionAll();
	}

	@Override
	public ArrayList<Question> getQuestionsBySubCode(String sub_code, String id) {
		System.out.println("서비스 | getQuestionsBySubCode() 호출");
		return questionRepository.getQuestionsBySubCode(sub_code, id);
	}

	@Override
	public Question getQuestionBySerial(String question_serial) {
		System.out.println("서비스 | getQuestionBySerial() 호출");
		return questionRepository.getQuestionBySerial(question_serial);
	}

	@Override
	public void updateQuestionCount(String question_serial, int question_count) {
		System.out.println("서비스 | updateQuestionCount() 호출");
		questionRepository.updateQuestionCount(question_serial, question_count);
	}

	@Override
	public void updateQuestion(Question question) {
		System.out.println("서비스 | updateQuestion() 호출");
		questionRepository.updateQuestion(question);
	}

	@Override
	public void visibleQuestion(String question_serial) {
		System.out.println("서비스 | visibleQuestion() 호출");
		questionRepository.visibleQuestion(question_serial);
	}

	
	//이미지 파일을 처리하는 함수로 다른 컨트롤러에서도 사용되는 경우가 많을 것이라 판단해서 서비스 계층에 올려둠.
	@Override
	public void img_file_processing(Question question, HttpServletRequest request) {
		System.out.println("서비스 | img_file_processing() 도착");
		//파일 저장 경로
		String path = request.getServletContext().getRealPath("/resources/images");
		System.out.println("서비스 | 이미지파일 저장 경로 : "+path);
		//파일 이름 만들기
		//파일의 확장자를 분리하는 작업
		MultipartFile multi = question.getQuestion_img();
		String file_name = multi.getOriginalFilename();
		String[] file_names = file_name.split("\\.");
		String extension = file_names[file_names.length-1];
		//파일의 이름을 현재시간으로 사용하기 위한 작업
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
		//결합 : RLPL209912311030123.확장자
		String img_name = "RLPL"+sdf.format(time)+"."+extension;
		System.out.println("저장되는 파일 : "+img_name);
		//DTO에 SET
		question.setQuestion_img_name(img_name);
		
		//빈 파일 생성
		File file = new File(path, img_name); 
	
		//빈 파일에 작성
		try {
			multi.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Question> getMyQuestionsBySubCode(String sub_code, int mem_serial, String id) {
		System.out.println("서비스 | getMyQuestionsBySubCode() 호출");
		return questionRepository.getMyQuestionsBySubCode(sub_code, mem_serial, id);
	}

}

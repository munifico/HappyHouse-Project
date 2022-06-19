package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.QnADto;
import com.ssafy.util.PageNavigation;

public interface QnAService {
	int registerQnA(QnADto qnaDto) throws SQLException;
	List<QnADto> listQnA(Map<String, String> map) throws SQLException;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	QnADto getQnAbyNo(int qnaNo) throws SQLException;
	int updateQnA(QnADto qnaDto) throws SQLException;
	int deleteQnAbyNo(int qnaNo) throws SQLException;
}

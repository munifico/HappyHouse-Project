package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.QnADto;

@Mapper
public interface QnAMapper {
	int registerQnA(QnADto qnaDto) throws SQLException;
	List<QnADto> listQnA(Map<String, Object> map) throws SQLException;
	int getTotalQnACount(Map<String, String> map) throws SQLException;
	QnADto getQnAbyNo(int qnaNo) throws SQLException;
	int updateQnA(QnADto qnaDto) throws SQLException;
	int deleteQnAbyNo(int qnaNo) throws SQLException;
}

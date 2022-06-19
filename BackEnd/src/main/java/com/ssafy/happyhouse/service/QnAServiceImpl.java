package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.QnADto;
import com.ssafy.happyhouse.model.mapper.QnAMapper;
import com.ssafy.util.PageNavigation;

@Service
public class QnAServiceImpl implements QnAService{
	@Autowired
	private QnAMapper qnaMapper;
	
	@Override
	public int registerQnA(QnADto qnaDto) throws SQLException {
		return qnaMapper.registerQnA(qnaDto);
	}

	@Override
	public List<QnADto> listQnA(Map<String, String> map) throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");

		param.put("key", key == null ? "all" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pg") == null ? "1" : map.get("pg"));
//		int sizePerPage = Integer.parseInt(map.get("spp"));
//		int start = (currentPage - 1) * sizePerPage;
//		param.put("start", start);
//		param.put("spp", sizePerPage);
		return qnaMapper.listQnA(param);
	}

	@Override
	public QnADto getQnAbyNo(int qnaNo) throws SQLException {
		return qnaMapper.getQnAbyNo(qnaNo);
	}

	@Override
	public int updateQnA(QnADto qnaDto) throws SQLException {
		return qnaMapper.updateQnA(qnaDto);
	}
	
	@Override
	public int deleteQnAbyNo(int qnaNo) throws SQLException {
		return qnaMapper.deleteQnAbyNo(qnaNo);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		
		int naviSize = 10;
		int currentPage = map.get("pg")==null? 1:Integer.parseInt(map.get("pg"));
		int sizePerPage = Integer.parseInt(map.get("spp"));
		
		pageNavigation.setCurrentPage(currentPage);
		System.out.println(pageNavigation.getCurrentPage());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = qnaMapper.getTotalQnACount(map);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		
		return pageNavigation;
	}

}

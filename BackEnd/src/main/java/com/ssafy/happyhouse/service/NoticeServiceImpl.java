package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	
    @Autowired
	private NoticeMapper noticeMapper;

    @Override
	public List<NoticeDto> retrieveNotice() {
		return noticeMapper.selectNotice();
	}
    
  	@Override
	public boolean writeNotice(NoticeDto notice) {
		return noticeMapper.insertNotice(notice) == 1;
	}

	@Override
	public NoticeDto detailNotice(int articleno) {
		return noticeMapper.selectNoticeByNo(articleno);
	}

	@Override
	@Transactional
	public boolean updateNotice(NoticeDto notice) {
		return noticeMapper.updateNotice(notice) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNotice(int articleno) {
		return noticeMapper.deleteNotice(articleno) == 1;
	}
}
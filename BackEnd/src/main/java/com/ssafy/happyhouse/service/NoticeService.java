package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.model.NoticeDto;

public interface NoticeService {
	public List<NoticeDto> retrieveNotice();
	public NoticeDto detailNotice(int articleno);
	public boolean writeNotice(NoticeDto notice);
	public boolean updateNotice(NoticeDto notice);
	public boolean deleteNotice(int articleno);
}

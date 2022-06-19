package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public MemberDto findPassWord(MemberDto memberDto) throws Exception;
	public int registerMember(MemberDto memberDto) throws Exception;
	public int updateMember(MemberDto memberDto) throws Exception;
	public int deleteMember(String id) throws Exception;
	

	
}

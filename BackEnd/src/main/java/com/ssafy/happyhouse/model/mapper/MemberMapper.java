package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;

import com.ssafy.happyhouse.model.MemberDto;


public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public MemberDto findPassWord(MemberDto memberDto) throws Exception;
	public int registerMember(MemberDto memberDto) throws SQLException;
	public int updateMember(MemberDto memberDto) throws Exception;
	public int deleteMember(String id) throws Exception;

	
}
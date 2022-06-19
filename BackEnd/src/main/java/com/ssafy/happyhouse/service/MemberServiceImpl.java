package com.ssafy.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return memberMapper.login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return memberMapper.userInfo(userid);
	}
	
	
	@Override
	public int registerMember(MemberDto memberDto) throws Exception {
		return memberMapper.registerMember(memberDto);
	}

	@Override
	public int updateMember(MemberDto memberDto) throws Exception {
		return memberMapper.updateMember(memberDto);
	}

	@Override
	public int deleteMember(String id) throws Exception {
		return memberMapper.deleteMember(id);
	}

	@Override
	public MemberDto findPassWord(MemberDto memberDto) throws Exception {
		return memberMapper.findPassWord(memberDto);
	}

	

}

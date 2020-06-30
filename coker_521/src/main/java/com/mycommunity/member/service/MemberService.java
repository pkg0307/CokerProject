package com.mycommunity.member.service;

import java.util.List;
import java.util.Map;

import com.mycommunity.member.vo.MemberVO;

public interface MemberService {
	public MemberVO login(Map loginMap) throws Exception;
	public void addMember(MemberVO memberVO) throws Exception;
	public int overlapEmail(MemberVO memberVO) throws Exception;
	public int overlapNickName(MemberVO memberVO) throws Exception;
	public void modifyInfo(MemberVO memberVO) throws Exception;
	public MemberVO myInfo(String userEmail) throws Exception;
	public void deleteMember(MemberVO memberVO) throws Exception;
	public String passChk(MemberVO memberVO) throws Exception;
	public int getPoint(String nickname) throws Exception;
	public int getLvl(String nickname) throws Exception;
	public String getImgName(String nickname) throws Exception;
	public int setLvl(MemberVO memberVO) throws Exception;
	public void addProfileImg(Map imageMap) throws Exception;
	
	public String getNoticeTitle() throws Exception;
	//회원계정 찾기
	public List<String> searchUserEmail(Map<String, Object> searchUserInfo) throws Exception;
	//계정 비번 재설정
	public int resetPw(Map<String, Object> resetPwInfo)throws Exception;
	
}

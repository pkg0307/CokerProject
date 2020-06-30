package com.mycommunity.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycommunity.member.vo.MemberVO;

public interface MemberDAO {
	public MemberVO login(Map loginMap) throws DataAccessException;
	public void createMember(MemberVO memberVO) throws DataAccessException;
	public int overlapEmail(MemberVO memberVO) throws Exception;
	public int overlapNickName(MemberVO memberVO) throws Exception;
	public void updateMyInfo(MemberVO memberVO) throws Exception;
	public MemberVO selectMyInfo(String userEmail) throws DataAccessException;
	public void deleteMyInfo(MemberVO memberVO) throws Exception;
	public String passChk(MemberVO memberVO) throws Exception;
	public int getPoint(String nickname) throws Exception;
	public int getLvl(String nickname) throws Exception;
	public String getImgName(String nickname) throws Exception;
	public int setLvlUp(MemberVO memberVO) throws Exception;
	public void addProfileImage(Map imageMap);
	public String getNoticeTitle() throws DataAccessException;
	public List<String> searchUserEmail(Map<String, Object> searchUserInfo)throws Exception;
	public int resetPw(Map<String, Object> resetPwInfo)throws Exception;
	
}

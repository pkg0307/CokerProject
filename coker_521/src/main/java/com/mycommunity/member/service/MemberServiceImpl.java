package com.mycommunity.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycommunity.member.dao.MemberDAO;
import com.mycommunity.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation=Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public MemberVO login(Map loginMap) throws Exception {
		return memberDAO.login(loginMap);
	}

	@Override
	public void addMember(MemberVO memberVO) throws Exception {
		memberDAO.createMember(memberVO);
		
	}

	@Override
	public int overlapEmail(MemberVO memberVO) throws Exception {
		int result = memberDAO.overlapEmail(memberVO);
		return result;
	}

	@Override
	public int overlapNickName(MemberVO memberVO) throws Exception {
		int result =  memberDAO.overlapNickName(memberVO);
		return result;
	}

	@Override
	public void modifyInfo(MemberVO memberVO) throws Exception {
		memberDAO.updateMyInfo(memberVO);
		
	}
	
	@Override
	public MemberVO myInfo(String userEmail) throws Exception {
		return memberDAO.selectMyInfo(userEmail);
	}

	@Override
	public void deleteMember(MemberVO memberVO) throws Exception {
		memberDAO.deleteMyInfo(memberVO);
		
	}

	@Override
	public String passChk(MemberVO memberVO) throws Exception {
		String userPw = memberDAO.passChk(memberVO);
		return userPw;
	}

	@Override
	public int getPoint(String nickname) throws Exception {
		return memberDAO.getPoint(nickname);
	}
	
	
	@Override
	public int getLvl(String nickname) throws Exception {
		return memberDAO.getLvl(nickname);
	}
	
	@Override
	public String getImgName(String nickname) throws Exception {
		return memberDAO.getImgName(nickname);
	}
	
	@Override
	public void addProfileImg(Map imageMap) throws Exception {
		memberDAO.addProfileImage(imageMap);
	}

	@Override
	public int setLvl(MemberVO memberVO) throws Exception {
		int result = memberDAO.setLvlUp(memberVO);
		return result;
	}

	
	@Override
	public String getNoticeTitle() throws Exception {
		String noticeTitle = null;
		noticeTitle = memberDAO.getNoticeTitle();
		return noticeTitle;
	}

	//계정찾기
	@Override
	public List<String> searchUserEmail(Map<String, Object> searchUserInfo) throws Exception {
		List<String> userEmailList = new ArrayList<String>(); 
		userEmailList = memberDAO.searchUserEmail(searchUserInfo);
		return userEmailList;
		
	}
	
	//비번 재설정
	@Override
	public int resetPw(Map<String, Object> resetPwInfo) throws Exception {
		int result = memberDAO.resetPw(resetPwInfo);
		return result;
	}
}

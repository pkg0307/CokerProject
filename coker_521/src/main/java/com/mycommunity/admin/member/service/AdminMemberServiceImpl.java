package com.mycommunity.admin.member.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mycommunity.admin.member.dao.AdminMemberDAO;
import com.mycommunity.member.vo.MemberVO;


@Service("adminMemberService")
public class AdminMemberServiceImpl implements AdminMemberService{
	@Autowired
	private AdminMemberDAO adminMemberDao;

	@Override
	public MemberVO memberDetail(String userEmail) throws Exception {
		return adminMemberDao.memberDetail(userEmail);
	}

	@Override
	public void modifyMemberInfo(MemberVO memberVO) throws Exception {
		adminMemberDao.modifyMemberInfo(memberVO);
	}
	
	@Override
	public int overlapNickName(MemberVO memberVO) throws Exception {
		int result =  adminMemberDao.overlapNickName(memberVO);
		return result;
	}

	@Override
	public List<MemberVO> listMembers(Map<String, Object> searchMemberInfo) throws DataAccessException {
		List<MemberVO> memberList = null;
		memberList = adminMemberDao.selectMembers(searchMemberInfo);
		return memberList;
	}
	
	//Admin 비밀번호 체크
	@Override
	public boolean checkAdmin(HashMap adminMap) throws Exception {
		return adminMemberDao.checkAdmin(adminMap);
	}
	
	//Admin 계정으로 회원삭제
	@Override
	public void deleteMember(String userEmail) throws Exception {
		adminMemberDao.deleteMember(userEmail);
		
	}
	
	//계정상태변경
	@Override
	public void enableMember(Map<String, String> enableInfoMap) throws Exception {
		adminMemberDao.enableMember(enableInfoMap);
		
	}
	
	//계정상태확인
	@Override
	public String checkEnableMember(String userEmail) throws Exception {
		return adminMemberDao.checkEnableMember(userEmail);
	}
	
	//차단계정리스트 불러오기
	@Override
	public ArrayList<MemberVO> checkDisMemberList() throws Exception {
		ArrayList<MemberVO> disMemberList = null;
		disMemberList = adminMemberDao.checkDisMemberList();
		return disMemberList;
	}
	
	//Oracle 시간(sysdate) 확인
	@Override
	public Date checkServerDate() throws Exception {
		Date serverDate = adminMemberDao.checkServerDate();
		return serverDate;
	}
	
	//@Scheduler와 연결됨, 차단계정 자동해제
	@Override
	public void autoEnableMember(String disUserEmail) throws Exception {
		adminMemberDao.autoEnableMember(disUserEmail);
	}

	@Override
	public int setMemberTotalCount(Map<String, Object>searchMemberInfo) throws Exception {
		return adminMemberDao.setMemberTotalCount(searchMemberInfo);
	}

	//비밀번호 재설정시 회원 이메일 인증
	@Override
	public String resetPwCheckUserEmail(String checkUserEmail) throws Exception {
		String userEmail = adminMemberDao.checkPwUserEmail(checkUserEmail);
		return userEmail;
	}

	@Override
	public String getNickname(String userEmail) throws Exception {
		String nickname  = adminMemberDao.getNickname(userEmail);
		return nickname;
	}

	

}

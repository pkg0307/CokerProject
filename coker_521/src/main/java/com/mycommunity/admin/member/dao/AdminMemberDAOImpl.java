package com.mycommunity.admin.member.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycommunity.member.vo.MemberVO;

@Repository("adminMemberDao")
public class AdminMemberDAOImpl implements AdminMemberDAO{
	@Autowired 
	private SqlSession sqlSession;
	
	//계정상세정보불러오기
	@Override
	public MemberVO memberDetail(String userEmail) throws DataAccessException {
		MemberVO memberBean = (MemberVO)sqlSession.selectOne("mapper.admin.member.memberDetail", userEmail);
		return memberBean;
	}
	
	//계정정보 수정
	@Override
	public void modifyMemberInfo(MemberVO memberVO) throws DataAccessException {
		sqlSession.update("mapper.admin.member.modifyMemberInfo", memberVO);
	}
	
	//중복 닉네임 검사
	@Override
	public int overlapNickName(MemberVO memberVO) throws Exception {
		int result = sqlSession.selectOne("mapper.admin.member.overlapNickName", memberVO);
		return result;
	}

	
	//모든 계정목록불러오기
	@Override
	public List<MemberVO> selectMembers(Map<String, Object> searchMemberInfo) throws DataAccessException {
		List<MemberVO> memberList = null;
		memberList = sqlSession.selectList("mapper.admin.member.selectMemberList", searchMemberInfo);
		return memberList;
	}
	
	//Admin 비밀번호 체크
	@Override
	public boolean checkAdmin(HashMap adminMap) throws DataAccessException {
		boolean result = false;
		int count = sqlSession.selectOne("mapper.admin.member.checkAdmin", adminMap);
		if(count == 1) {
			result = true;
		}
		return result;
	}
	
	//Admin 계정으로 회원삭제
	@Override
	public void deleteMember(String userEmail) throws DataAccessException {
		sqlSession.delete("mapper.admin.member.deleteMember", userEmail);
	}
	
	//계정상태변경
	@Override
	public void enableMember(Map<String, String> enableInfoMap) throws DataAccessException {
		sqlSession.update("mapper.admin.member.enableMember", enableInfoMap);
	}

	//계정상태확인
	@Override
	public String checkEnableMember(String userEmail) throws DataAccessException {
		String result = sqlSession.selectOne("mapper.admin.member.checkEnableMember", userEmail);
		return result;
	}
	
	//차단계정 목록 불러오기
	@Override
	public ArrayList<MemberVO> checkDisMemberList() throws DataAccessException {
		ArrayList<MemberVO> checkDisMemberList = null;
		checkDisMemberList = (ArrayList)sqlSession.selectList("mapper.admin.member.checkDisMemberList");
		return checkDisMemberList;	
	}

	//Oracle내 당일날짜 확인
	@Override
	public Date checkServerDate() throws DataAccessException {
		Date serverDate = sqlSession.selectOne("mapper.admin.member.checkServerDate");
		return serverDate;
	}
	
	//계정차단 자동해제
	@Override
	public void autoEnableMember(String disUserEmail) throws DataAccessException {
		sqlSession.update("mapper.admin.member.autoEnableMember", disUserEmail);
		
	}

	@Override
	public int setMemberTotalCount(Map<String, Object>searchMemberInfo) throws DataAccessException {
		return sqlSession.selectOne("mapper.admin.member.setMemberListTotalCount", searchMemberInfo);
	}
	
	//비밀번호 재설정시 회원 이메일 인증
	@Override
	public String checkPwUserEmail(String checkUserEmail) throws DataAccessException {
		String userEmail = sqlSession.selectOne("mapper.admin.member.checkPwUserEmail", checkUserEmail);
		return userEmail;
	}

	@Override
	public String getNickname(String userEmail) throws DataAccessException {
		String nickname = sqlSession.selectOne("mapper.admin.member.getNickname", userEmail);
		return nickname;
	}

}

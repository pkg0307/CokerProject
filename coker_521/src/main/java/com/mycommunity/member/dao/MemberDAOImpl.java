package com.mycommunity.member.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycommunity.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public MemberVO login(Map loginMap) throws DataAccessException {
		MemberVO member = (MemberVO)sqlSession.selectOne("mapper.member.login", loginMap);
		return member;
	}

	@Override
	public void createMember(MemberVO memberVO) throws DataAccessException {
		sqlSession.insert("mapper.member.createMember", memberVO);
		
	}

	@Override
	public int overlapEmail(MemberVO memberVO) throws Exception {
		int result = sqlSession.selectOne("mapper.member.overlapEmail", memberVO);
		return result;
	}

	@Override
	public int overlapNickName(MemberVO memberVO) throws Exception {
		int result = sqlSession.selectOne("mapper.member.overlapNickName", memberVO);
		return result;
	}

	@Override
	public void updateMyInfo(MemberVO memberVO) throws Exception {
		sqlSession.update("mapper.member.updateMyInfo", memberVO);
		
	}
	
	@Override
	public MemberVO selectMyInfo(String userEmail) throws DataAccessException {
		MemberVO memberVO = (MemberVO)sqlSession.selectOne("mapper.mypage.selectMyInfo", userEmail);
		return memberVO;
	}

	@Override
	public void deleteMyInfo(MemberVO memberVO) throws Exception {
		sqlSession.delete("mapper.member.deleteMyInfo", memberVO);
		
	}

	@Override
	public String passChk(MemberVO memberVO) throws Exception {
		String userPw = sqlSession.selectOne("mapper.member.passChk", memberVO);
		return userPw;
	}

	@Override
	public int getPoint(String nickname) throws Exception {
		return sqlSession.selectOne("mapper.member.getPoint", nickname);
	}
	
	
	@Override
	public int getLvl(String nickname) throws Exception {
		return sqlSession.selectOne("mapper.member.getLvl", nickname);
	}
	
	@Override
	public String getImgName(String nickname) throws Exception {
		return sqlSession.selectOne("mapper.member.getImgName", nickname);
	}
	
	@Override
	public void addProfileImage(Map imageMap) {
		sqlSession.update("mapper.member.addProfileImg", imageMap);
		
	}

	@Override
	public int setLvlUp(MemberVO memberVO) throws Exception {
		return sqlSession.update("mapper.member.lvlUp", memberVO);
	}
	
	@Override
	public String getNoticeTitle() throws DataAccessException {
		String noticeTitle = null;
		noticeTitle = sqlSession.selectOne("mapper.member.getNoticeTitle");
		return noticeTitle;
	}

	//회원계정 찾기
	@Override
	public List<String> searchUserEmail(Map<String, Object> searchUserInfo) throws Exception {
		List<String> userEmailList = new ArrayList<String>();
		userEmailList = sqlSession.selectList("mapper.member.searchUserEmail", searchUserInfo);
		return userEmailList;
	}
	
	//회원 비번 재설정
	@Override
	public int resetPw(Map<String, Object> resetPwInfo) throws Exception {
		int result = sqlSession.update("mapper.member.resetPw", resetPwInfo);
		return result;
	}

}

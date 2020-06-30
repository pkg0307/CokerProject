package com.mycommunity.admin.member.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycommunity.member.vo.MemberVO;

public interface AdminMemberDAO {
	public MemberVO memberDetail(String userEmail) throws DataAccessException;
	public void modifyMemberInfo(MemberVO memberVO) throws DataAccessException;
	public List<MemberVO> selectMembers(Map<String, Object> searchMemberInfo)throws DataAccessException;
	public boolean checkAdmin(HashMap adminMap)throws DataAccessException;
	public void deleteMember(String userEmail) throws DataAccessException;
	public void enableMember(Map<String, String> enableInfoMap) throws DataAccessException;
	public String checkEnableMember(String userEmail) throws DataAccessException;
	public ArrayList<MemberVO> checkDisMemberList() throws DataAccessException;
	public Date checkServerDate()throws DataAccessException;
	public void autoEnableMember(String disUserEmail) throws DataAccessException;
	public int setMemberTotalCount(Map<String, Object>searchMemberInfo)throws DataAccessException;
	//비밀번호 재설정 시 회원 이메일 인증
	public String checkPwUserEmail(String checkUserEmail) throws DataAccessException;
	public int overlapNickName(MemberVO memberVO) throws Exception;
	public String getNickname(String userEmail)throws DataAccessException;
}

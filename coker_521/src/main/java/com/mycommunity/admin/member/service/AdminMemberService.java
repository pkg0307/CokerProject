package com.mycommunity.admin.member.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycommunity.member.vo.MemberVO;

public interface AdminMemberService {
		public MemberVO memberDetail(String userEmail) throws Exception;
		public void modifyMemberInfo(MemberVO memberVO) throws Exception;
		public List<MemberVO> listMembers(Map<String, Object> searchMemberInfo) throws DataAccessException;
		public boolean checkAdmin(HashMap adminMap) throws Exception;
		public void deleteMember(String userEmail) throws Exception;
		public void enableMember(Map<String, String> enableInfoMap) throws Exception;
		public String checkEnableMember(String userEmail) throws Exception;
		public ArrayList<MemberVO> checkDisMemberList() throws Exception;
		public Date checkServerDate() throws Exception;
		public void autoEnableMember(String disUserEmail) throws Exception;
		public int setMemberTotalCount(Map<String, Object>searchMemberInfo) throws Exception;
		//비밀번호 재설정시 회원 이메일 인증		
		public String resetPwCheckUserEmail(String checkUserEmail) throws Exception;
		public int overlapNickName(MemberVO memberVO) throws Exception;
		public String getNickname(String userEmail)throws Exception;
}

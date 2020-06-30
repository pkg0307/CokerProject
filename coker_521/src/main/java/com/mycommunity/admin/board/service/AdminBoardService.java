package com.mycommunity.admin.board.service;

import java.util.List;
import java.util.Map;

import com.mycommunity.admin.board.vo.AdminBoardVO;
import com.mycommunity.member.vo.MemberVO;

public interface AdminBoardService {

	public List<MemberVO> getRankedMemberList(Map<String, Object> searchRankedMemberInfo) throws Exception;

	public int setArticleTotalCount(Map totalMap)throws Exception;

	public List<AdminBoardVO> selectAllArticles(Map<String, Object> search)throws Exception;
	
}

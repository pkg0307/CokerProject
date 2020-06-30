package com.mycommunity.admin.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycommunity.admin.board.vo.AdminBoardVO;
import com.mycommunity.member.vo.MemberVO;

public interface AdminBoardDAO {

	public List<MemberVO> getRankedMemberList(Map<String, Object> searchRankedMemberInfo) throws DataAccessException;

	public int setArticleTotalCount(Map totalMap)throws DataAccessException;

	public List<AdminBoardVO> selectAllArticles(Map<String, Object> search)throws DataAccessException;
}

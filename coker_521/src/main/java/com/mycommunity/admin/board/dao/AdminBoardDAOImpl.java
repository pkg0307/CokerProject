package com.mycommunity.admin.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycommunity.admin.board.vo.AdminBoardVO;
import com.mycommunity.member.vo.MemberVO;

@Repository("adminBoardDao")
public class AdminBoardDAOImpl implements AdminBoardDAO{
	  
	  @Autowired 
	  private SqlSession sqlSession;
	  
	  @Override
		public List<MemberVO> getRankedMemberList(Map<String, Object> searchRankedMemberInfo) throws DataAccessException {
			List<MemberVO> rankedMemberList = null;
			rankedMemberList = sqlSession.selectList("mapper.admin.board.getRankedMemberList", searchRankedMemberInfo);
			
			return rankedMemberList;
		}

	@Override
	public int setArticleTotalCount(Map totalMap) throws DataAccessException {
		
		return sqlSession.selectOne("mapper.admin.board.setArticleTotalCount", totalMap);
	}

	@Override
	public List<AdminBoardVO> selectAllArticles(Map<String, Object> search) throws DataAccessException {
		
		return sqlSession.selectList("mapper.admin.board.selectAllArticles", search);
	}
	 
}

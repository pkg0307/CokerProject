package com.mycommunity.admin.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycommunity.admin.board.dao.AdminBoardDAO;
import com.mycommunity.admin.board.vo.AdminBoardVO;
import com.mycommunity.member.vo.MemberVO;

@Service("adminBoardService")
public class AdminBoardServiceImpl implements AdminBoardService{
	@Autowired
	AdminBoardDAO adminBoardDao;
	
	@Override
	public List<MemberVO> getRankedMemberList(Map<String, Object> searchRankedMemberInfo) throws Exception {
		List<MemberVO> rankedMemberList = null;
		rankedMemberList = adminBoardDao.getRankedMemberList(searchRankedMemberInfo);
		return rankedMemberList;
	}

	@Override
	public int setArticleTotalCount(Map totalMap) throws Exception {
		return adminBoardDao.setArticleTotalCount(totalMap); 
	}

	@Override
	public List<AdminBoardVO> selectAllArticles(Map<String, Object> search) throws Exception {
		// TODO Auto-generated method stub
		return adminBoardDao.selectAllArticles(search);
	}

	
}	

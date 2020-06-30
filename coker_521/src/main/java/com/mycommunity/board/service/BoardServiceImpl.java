package com.mycommunity.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycommunity.board.dao.BoardDAO;
import com.mycommunity.board.vo.BoardVO;
import com.mycommunity.board.vo.ReplyVO;

@Service("boardService")
@Transactional(propagation=Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDAO boardDAO;

	/*
	@Override
	public List listArticles(String tableName) throws Exception {
		List<BoardVO> articlesList = boardDAO.selectAllArticles(tableName);
		return articlesList;
	}*/
	
	
	
	@Override
	public List bestListArticles(String tableName) throws Exception {
		List<BoardVO> articlesList = boardDAO.selectAllBestArticles(tableName);
		return articlesList;
	}



	@Override
	public Map viewArticle(String tableName, int boardNO) throws Exception {
		Map articleMap = new HashMap();
		BoardVO boardVO = boardDAO.selectArticle(tableName, boardNO);
		articleMap.put("article", boardVO);
		return articleMap;
	}

	@Override
	public List getReplies(String tableName) throws Exception {
		List<ReplyVO> repliesList = boardDAO.selectAllReplies(tableName);
		return repliesList;
	}
	
	@Override
	public List getReplies(String tableName, int boardNO) throws Exception {
		List<ReplyVO> repliesList = boardDAO.selectAllReplies(tableName, boardNO);
		return repliesList;
	}

	@Override
	public void addArticle(Map articleMap) throws Exception {
		boardDAO.addArticle(articleMap);
	}

	@Override
	public void removeArticle(String boardTableName, String replyTableName, int boardNO) {
		boardDAO.removeArticle(boardTableName, replyTableName, boardNO);
	}

	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.modArticle(articleMap);
	}

	@Override
	public void addReply(Map replyMap) throws Exception {
		boardDAO.addReply(replyMap);
	}

	@Override
	public void modifyReply(Map replyMap) throws Exception {
		boardDAO.modifyReply(replyMap);;
	}

	@Override
	public void deleteReply(Map replyMap) throws Exception {
		boardDAO.deleteReply(replyMap);
	}

	@Override
	public void recommend(Map recommendMap) throws Exception {
		boardDAO.recommend(recommendMap);
	}

	@Override
	public boolean recommender(Map recommenderMap) throws Exception {
		return boardDAO.recommender(recommenderMap);
	}
	
	@Override
	public void setArticleComplete(Map boardMap) throws Exception {
		boardDAO.setArticleComplete(boardMap); 
	}
	
	@Override
	public void setArticleComplete(Map boardMap, Map replyMap) throws Exception {
		boardDAO.setArticleComplete(boardMap, replyMap); 
	}

	//2020.03.31 pageMaker
	@Override
	public int setArticleTotalCount(Map totalMap) throws Exception {
		return boardDAO.setArticleTotalCount(totalMap);
	}

	@Override
	public List<BoardVO> selectAllArticles(Map<String, Object> search) throws Exception {
		return boardDAO.selectAllArticles(search);
	}

	@Override
	public void upPoint(String nickname) throws Exception {
		boardDAO.upPoint(nickname);
	}



	@Override
	public Map selectArticlesForMain() throws Exception {
		return boardDAO.selectArticlesForMain();
	}



	@Override
	public void reportBoard(Map reportMap) throws Exception {
		boardDAO.reportBoard(reportMap);
	}



	@Override
	public void reportReply(Map reportMap) throws Exception {
		boardDAO.reportReply(reportMap);
	}	
	
	
}

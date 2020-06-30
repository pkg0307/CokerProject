package com.mycommunity.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycommunity.board.vo.BoardVO;
import com.mycommunity.board.vo.ReplyVO;

public interface BoardDAO {
	//public List<BoardVO> selectAllArticles(String tableName) throws DataAccessException;
	public BoardVO selectArticle(String tableName, int boardNO) throws DataAccessException;
	
	public List<ReplyVO> selectAllReplies(String tableName) throws DataAccessException;
	public List<ReplyVO> selectAllReplies(String tableName, int boardNO) throws DataAccessException;
	
	public void addArticle(Map articleMap) throws DataAccessException;
	public void removeArticle(String boardTableName, String replyTableName, int boardNO) throws DataAccessException;
	public void modArticle(Map articleMap) throws DataAccessException;
	
	//��� �߰�
	public void addReply(Map replyMap) throws Exception;
	//��� ����
	public void modifyReply(Map replyMap) throws Exception;
	//��� ����
	public void deleteReply(Map replyMap) throws Exception;
	public void recommend(Map recommendMap) throws Exception;
	public List<BoardVO> selectAllBestArticles(String tableName) throws Exception;
	public void setArticleComplete(Map boardMap) throws Exception;
	public void setArticleComplete(Map boardMap, Map replyMap) throws Exception;

	//2020.03.31 Article total count
	public int setArticleTotalCount(Map totalMap) throws Exception;
	//2020.03.31 selectAllArticles renew
	public List<BoardVO> selectAllArticles(Map<String, Object> search) throws Exception;

	public boolean recommender(Map recommenderMap) throws Exception;

	public void upPoint(String nickname) throws Exception;

	public Map selectArticlesForMain() throws Exception;

	public void reportBoard(Map reportMap) throws Exception;
	public void reportReply(Map reportMap) throws Exception;
}

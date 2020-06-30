package com.mycommunity.board.service;

import java.util.List;
import java.util.Map;

import com.mycommunity.board.vo.BoardVO;

public interface BoardService {
	//public List listArticles(String tableName) throws Exception;

	public Map viewArticle(String tableName, int boardNO) throws Exception;

	public List getReplies(String tableName) throws Exception;
	public List getReplies(String tableName, int boardNO) throws Exception;

	public void addArticle(Map articleMap) throws Exception;

	public void removeArticle(String boardTableName, String replyTableName, int boardNO);

	public void modArticle(Map articleMap) throws Exception;
	
	//��� �߰�
	public void addReply(Map replyMap) throws Exception;
	//��� ����
	public void modifyReply(Map replyMap) throws Exception;
	//��� ����
	public void deleteReply(Map replyMap) throws Exception;

	public void recommend(Map recommendMap) throws Exception;
	public boolean recommender(Map recommenderMap) throws Exception;
	
	public List bestListArticles(String tableName) throws Exception;

	public void setArticleComplete(Map boardMap) throws Exception;
	public void setArticleComplete(Map boardMap, Map replyMap) throws Exception;
	
	//2020.03.31 Article total count
	public int setArticleTotalCount(Map totalMap) throws Exception;
	//2020.03.31 selectAllArticles renew
	public List<BoardVO> selectAllArticles(Map<String, Object> search) throws Exception;

	public void upPoint(String nickname) throws Exception;

	public Map selectArticlesForMain() throws Exception;

	public void reportBoard(Map reportMap) throws Exception;
	public void reportReply(Map reportMap) throws Exception;

	
}

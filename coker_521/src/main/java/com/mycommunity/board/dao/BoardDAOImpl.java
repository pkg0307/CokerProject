package com.mycommunity.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycommunity.board.vo.BoardVO;
import com.mycommunity.board.vo.ReplyVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO{
	@Autowired
	private SqlSession sqlSession;
	
	Map map;
	
	/*
	@Override
	public List<BoardVO> selectAllArticles(String tableName) throws DataAccessException {
		map = new HashMap();
		map.put("tableName", tableName);
		List<BoardVO> articlesList = sqlSession.selectList("mapper.board.selectAllArticles", map);
		return articlesList;
	}
	*/
	
	
	
	@Override
	public List<BoardVO> selectAllBestArticles(String tableName) throws Exception {
		map = new HashMap();
		map.put("tableName", tableName);
		List<BoardVO> articlesList = sqlSession.selectList("mapper.board.selectAllBestArticles", map);
		return articlesList;
	}



	public BoardVO selectArticle(String tableName, int boardNO) throws DataAccessException{
		map = new HashMap();
		map.put("tableName", tableName);
		map.put("boardNO", boardNO);
		return sqlSession.selectOne("mapper.board.selectArticle", map);
	}

	@Override
	public List<ReplyVO> selectAllReplies(String tableName) throws DataAccessException {
		map = new HashMap();
		map.put("tableName", tableName);
		return sqlSession.selectList("mapper.board.selectAllReplies", map);
	}
	
	@Override
	public List<ReplyVO> selectAllReplies(String tableName, int boardNO) throws DataAccessException {
		map = new HashMap();
		map.put("tableName", tableName);
		map.put("boardNO", boardNO);
		List repliesList =  sqlSession.selectList("mapper.board.selectAllReplies", map);
		for(int i=0; i<repliesList.size(); i++) {
			ReplyVO replyVO = (ReplyVO)repliesList.get(i);
			String nickname = replyVO.getWriter();
			String imgName = sqlSession.selectOne("mapper.board.getImgName",nickname);
			replyVO.setImgName(imgName);
		}
		return repliesList;
	}

	@Override
	public void addArticle(Map articleMap) throws DataAccessException {
		sqlSession.insert("mapper.board.addArticle", articleMap);
	}

	@Override
	public void removeArticle(String boardTableName, String replyTableName, int boardNO) throws DataAccessException {
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		map1.put("boardTableName", boardTableName);
		map2.put("replyTableName", replyTableName);
		map1.put("boardNO", boardNO);
		map2.put("boardNO", boardNO);
		sqlSession.delete("mapper.board.removeReply",map2);
		sqlSession.delete("mapper.board.removeArticle",map1);
	}

	@Override
	public void modArticle(Map articleMap) throws DataAccessException {
		System.out.println(articleMap);
		sqlSession.update("mapper.board.modArticle", articleMap);
	}

	@Override
	public void addReply(Map replyMap) throws Exception {
		sqlSession.insert("mapper.board.addReply", replyMap);
	}

	@Override
	public void modifyReply(Map replyMap) throws Exception {
		sqlSession.update("mapper.board.modifyReply", replyMap);
	}

	@Override
	public void deleteReply(Map replyMap) throws Exception {
		sqlSession.delete("mapper.board.deleteReply", replyMap);
	}

	@Override
	public void recommend(Map recommendMap) throws Exception {
		sqlSession.update("mapper.board.recommend", recommendMap);
	}

	@Override
	public boolean recommender(Map recommenderMap) throws Exception {
		if(sqlSession.selectOne("mapper.board.isRecommended",recommenderMap).equals("yes")) {
			return false;
		}
		sqlSession.insert("mapper.board.recommender", recommenderMap);
		return true;
	}
	@Override
	public void setArticleComplete(Map boardMap) throws Exception {
		sqlSession.update("mapper.board.setArticleComplete",boardMap);
	}
	
	@Override
	public void setArticleComplete(Map boardMap, Map replyMap) throws Exception {
		sqlSession.update("mapper.board.setArticleComplete",boardMap);
		sqlSession.update("mapper.board.setReplyComplete",replyMap);
	}
	
	@Override
	public int setArticleTotalCount(Map totalMap) throws Exception {
		return sqlSession.selectOne("mapper.board.setArticleTotalCount", totalMap);
	}
	
	//2020.03.31
	@Override
	public List<BoardVO> selectAllArticles(Map<String, Object> search) throws Exception {
		return sqlSession.selectList("mapper.board.selectAllArticles", search);
	}

	@Override
	public void upPoint(String nickname) throws Exception {
		sqlSession.update("mapper.board.upPoint", nickname);
	}

	@Override
	public Map selectArticlesForMain() throws Exception {
		Map articlesMap = new HashMap();
		
		articlesMap.put("javaList", sqlSession.selectList("mapper.board.selectArticlesForMain", "c_board_java_share_board"));
		articlesMap.put("cList", sqlSession.selectList("mapper.board.selectArticlesForMain", "c_board_c_share_board"));
		articlesMap.put("cplusList", sqlSession.selectList("mapper.board.selectArticlesForMain", "c_board_cplus_share_board"));
		articlesMap.put("pythonList", sqlSession.selectList("mapper.board.selectArticlesForMain", "c_board_python_share_board"));
		articlesMap.put("noticeList", sqlSession.selectList("mapper.board.selectArticlesForMain", "c_board_notice_board"));
		articlesMap.put("freetalkList", sqlSession.selectList("mapper.board.selectArticlesForMainPlusTopic", "c_board_freetalk_board"));
		articlesMap.put("qnaList", sqlSession.selectList("mapper.board.selectArticlesForMainPlusProgress", "c_board_qna_board"));
		articlesMap.put("recruitList", sqlSession.selectList("mapper.board.selectArticlesForMainPlusProgress", "c_board_recruit_board"));
		articlesMap.put("searchList", sqlSession.selectList("mapper.board.selectArticlesForMainPlusProgress", "c_board_search_board"));
		
		return articlesMap;
	}

	@Override
	public void reportBoard(Map reportMap) throws Exception {
		sqlSession.update("mapper.board.reportBoard", reportMap);
	}

	@Override
	public void reportReply(Map reportMap) throws Exception {
		sqlSession.update("mapper.board.reportReply", reportMap);
	}	
}

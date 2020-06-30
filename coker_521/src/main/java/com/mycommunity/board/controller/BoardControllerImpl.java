package com.mycommunity.board.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycommunity.board.service.BoardService;
import com.mycommunity.board.vo.BoardVO;
import com.mycommunity.board.vo.ReplyVO;
import com.mycommunity.common.annotation.LoginRequired;
import com.mycommunity.common.vo.PageCriteria;
import com.mycommunity.common.vo.PageMaker;
import com.mycommunity.member.vo.MemberVO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController{
	
	@Autowired
	BoardService boardService;
	@Autowired
	BoardVO boardVO;
	
	@Override
	@RequestMapping(value="/board/viewBoard.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(@RequestParam("category") String category, HttpServletRequest request, HttpServletResponse response,
			Integer page, Integer perPageNum, String searchType, String keyword) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		Map totalMap = new HashMap();
		Map<String, Object> search = new HashMap<String, Object>();
		//java, c�� �Խ���
		String title = getBoardName(category);
		
		String tableName = getBoardTableName(category);
		totalMap.put("tableName", tableName);
		totalMap.put("searchType", searchType);
		
		List wordsList = new ArrayList();
		if(keyword != null && keyword != "") {
			String words[] = keyword.split(" ");
			for(String word : words) {
				word.trim();
				wordsList.add(word);
			}
			totalMap.put("wordsList", wordsList);
			search.put("wordsList", wordsList);
		}
		
		page = (page == null)?1:page;
		perPageNum = (perPageNum == null)?10:perPageNum;
		
		PageCriteria cri = new PageCriteria();
		cri.setPage(page);
		cri.setPerPageNum(perPageNum);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setCategory(category);
		
		int totalCount = boardService.setArticleTotalCount(totalMap);
		pageMaker.setTotalCount(totalCount);
		
		System.out.println("pageMaker Data check : " + pageMaker.toString());
		System.out.println("pageCriteria Data check : " + cri.toString());
		
		search.put("tableName", tableName);
		//rowStart, rowEnd Setting & page, perPageNum setting
		search.put("cri", cri);
		search.put("searchType", searchType);
		
		//List articlesList = boardService.listArticles(tableName);
		List<BoardVO> articlesList = boardService.selectAllArticles(search);
		
		if(category.contains("share")) {
			List bestArticlesList = boardService.bestListArticles(tableName);
			mav.addObject("bestArticlesList",bestArticlesList);
		}
		
		mav.addObject("articlesList", articlesList);
		mav.addObject("boardName",category);
		mav.addObject("title", title);
		
		//page, perPageNum
		mav.addObject("pageMaker", pageMaker);
		return mav;
	}
	
	private String getBoardName(String category) {
		String[] splitedName = null;
		if(category != null && category.length() != 0) {
			splitedName = category.split("_");
			if(splitedName.length==2) {
				return splitedName[0].toUpperCase()+" 자료공유 게시판";
			} else {
				if(splitedName[0].equals("freetalk")){
					return "자유게시판";
				} else if(splitedName[0].equals("notice")){
					return "공지사항";
				} else if(splitedName[0].equals("recruit")) {
					return "구인 게시판";
				} else if(splitedName[0].equals("search")) {
					return "구직 게시판";
				} else if(splitedName[0].equals("qna")) {
					return "Q&A 게시판";
				} else if(splitedName[0].equals("service")) {
					return "고객센터";
				}
			}
		}
		return "오류";
	}
	
	@Override
	@RequestMapping(value="/board/viewArticle.do", method=RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam String category, @RequestParam int boardNO, @RequestParam(required = false, defaultValue = "0") Integer isAdmin,  HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		
		String tableName = getBoardTableName(category);
		Map articleMap = boardService.viewArticle(tableName, boardNO);
		mav.addObject("articleMap",articleMap);
		
		String tableName2 = getReplyTableName(category);
		List repliesList = boardService.getReplies(tableName2, boardNO);
		mav.addObject("repliesList",repliesList);
		mav.addObject("category",category);
		mav.addObject("isAdmin", isAdmin);

		return mav;
	}
	@LoginRequired
	@RequestMapping(value="/board/articleForm.do",method={RequestMethod.GET, RequestMethod.POST})
	private ModelAndView form(@RequestParam String category, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("category", category);
		return mav;
	}
	
	@Override
	@LoginRequired
	@RequestMapping(value="/board/removeArticle.do", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity removeArticle(String category, int boardNO, Integer isAdmin, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;chareset=utf-8");
		String message = null;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		
		String boardTableName = getBoardTableName(category);
		String replyTableName = getReplyTableName(category);
		try {
			boardService.removeArticle(boardTableName, replyTableName, boardNO);
			if(isAdmin == null || isAdmin == 0) {				
				message = "<script>alert('글을 삭제했습니다.'); location.href='"+request.getContextPath()+"/board/viewBoard.do?category="+category+"';</script>";
			}else {
				message = "<script>alert('글을 삭제했습니다.'); location.href='"+request.getContextPath()+"/admin/board/reportedBoardList.do';</script>";
			}
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch(Exception e) {
			if(isAdmin == null || isAdmin == 0) {				
				message = "<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/viewBoard.do?category="+category+"';</script>";
			} else {
				message = "<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/admin/board/reportedBoardList.do';</script>";
			}
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	@LoginRequired
	@RequestMapping(value = "/board/modForm.do", method =  RequestMethod.GET)
	private ModelAndView form(String category, int boardNO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("category", category);
		mav.addObject("boardNO", boardNO);
		
		String tableName = getBoardTableName(category);
		Map articleMap = boardService.viewArticle(tableName, boardNO);
		mav.addObject("articleMap",articleMap);
		return mav;
	}
	@Override
	@LoginRequired
	@RequestMapping(value = "/board/modArticle.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity modArticle(String category, int boardNO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map articleMap = new HashMap();
		String tableName = getBoardTableName(category);
		articleMap.put("tableName", tableName);
		
		articleMap.put("boardNO", boardNO);
		
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = request.getParameter(name);
			articleMap.put(name, value); //Ÿ��Ʋ �� ����Ʈ �߰�
		}
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try{
			boardService.modArticle(articleMap);
			message ="<script>alert('글을 수정했습니다.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
		} catch(Exception e) {
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/modForm.do?category="+category+"&boardNO="+boardNO+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	@Override
	@LoginRequired
	@RequestMapping(value="/board/addArticle.do", method=RequestMethod.POST)
	public ResponseEntity addArticle(String category, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map articleMap = new HashMap();
		String tableName = getBoardTableName(category);
		articleMap.put("tableName", tableName); //�ʿ� ī�װ� �߰�
		
		String seqName = getBoardSeqName(category);
		articleMap.put("seqName", seqName);
		
		if(category.equals("qna") || category.equals("recruit") || category.equals("search")) {
			int progress=0;
			articleMap.put("progress", progress);
		} else if(category.contains("share")) {
			int likes=0;
			articleMap.put("likes", likes);
		} else if(category.equals("service")) {
			int parentNo=0;
			articleMap.put("parentNo", parentNo);
		}
		if(!category.equals("recruit") && !category.equals("search") && !category.equals("notice")) {
			int reports=0;
			articleMap.put("reports",reports);
		}
		
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = request.getParameter(name);
			if(name.equals("files") || name.equals("category")) {
				continue;
			}
			articleMap.put(name, value); //Ÿ��Ʋ �� ����Ʈ �߰�	
		}
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		String nickname = memberVO.getNickname();
		articleMap.put("nickname", nickname); //�г��� �߰�
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try{
			boardService.addArticle(articleMap);
			message ="<script>alert('글을 추가했습니다.'); location.href='"+request.getContextPath()+"/board/viewBoard.do?category="+category+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
		} catch(Exception e) {
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/articleForm.do?category="+category+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	/*
	 * 2020. 03. 25 Reply
	 */
	@Override
	@LoginRequired
	@RequestMapping(value="/board/addReply.do", method=RequestMethod.POST)
	public ResponseEntity addReply(ReplyVO reply, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String category = request.getParameter("category");

		String tableName = getReplyTableName(category);
		String seqName = getReplySeqName(category);
		
		Map replyMap = new HashMap();
		replyMap.put("tableName", tableName);
		replyMap.put("seqName", seqName);
		replyMap.put("reply", reply);
		if(category.contains("qna")) {
			int progress = 0;
			replyMap.put("progress", progress);
		}
		int reports=0;
		replyMap.put("reports",reports);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try {
			boardService.addReply(replyMap);
			message ="<script>alert('댓글을 추가했습니다.');";
		} catch (Exception e) {
			e.printStackTrace();
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.');";
		}
		message += "location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+reply.getBoardNO()+"';</script>";
		resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);	
		return resEnt;
	}
	
	
	//2020.03.25
	//¥��� ®�µ� modify �ϱ����� textarea�� ���� �־����� ��.,��....
	@LoginRequired
	@RequestMapping(value="/board/modifyReply.do", method=RequestMethod.POST)
	public ResponseEntity modifyReply(ReplyVO reply, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String category = request.getParameter("category");
		String tableName = getReplyTableName(category);
		
		Map replyMap = new HashMap();
		replyMap.put("tableName", tableName);
		replyMap.put("reply", reply);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		
		try {
			boardService.modifyReply(replyMap);
			message ="<script>alert('댓글을 수정했습니다.');";
		} catch (Exception e) {
			e.printStackTrace();
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.');";
		}
		message += "location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+reply.getBoardNO()+"';</script>";
		resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);	
		
		return resEnt;
	}
	
	//2020.03.25
	//replyDelete
	@LoginRequired
	@RequestMapping(value="/board/deleteReply.do", method=RequestMethod.POST)
	public ResponseEntity deleteReply(ReplyVO reply, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String category = request.getParameter("category");
		String tableName = getReplyTableName(category);
		
		Map replyMap = new HashMap();
		replyMap.put("tableName", tableName);
		replyMap.put("reply", reply);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		
		try {
			boardService.deleteReply(replyMap);
			message ="<script>alert('댓글을 삭제했습니다.');";
		} catch (Exception e) {
			e.printStackTrace();
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.');";
		}
		message += "location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+reply.getBoardNO()+"';</script>";
		resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);	
		
		return resEnt;
	}
	@LoginRequired
	@RequestMapping("/board/recommend.do")
	public ResponseEntity recommend(String category, int boardNO, String nickname, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tableName = getBoardTableName(category);
		Map recommendMap = new HashMap();
		recommendMap.put("tableName", tableName);
		recommendMap.put("boardNO", boardNO);
		
		tableName = getLikesTableName(category);
		Map recommenderMap = new HashMap();
		recommenderMap.put("tableName", tableName);
		recommenderMap.put("boardNO", boardNO);
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		String userNickname = memberVO.getNickname();
		recommenderMap.put("nickname", userNickname);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		if(boardService.recommender(recommenderMap)) {
			try{
				boardService.recommend(recommendMap);
				boardService.upPoint(nickname);
				message ="<script>alert('해당 글을 추천했습니다.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
				resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
			} catch(Exception e) {
				message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
				resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
				e.printStackTrace();
			}
		} else {
			message ="<script>alert('이미 추천하셨습니다.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
		}
		return resEnt;
	}
	@LoginRequired
	@RequestMapping("/board/setFinalReply.do")
	public ResponseEntity setFinalReply(String category, int boardNO, int replyNO, String replyNickname, String boardNickname, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String boardTableName = getBoardTableName(category);
		String replyTableName = getReplyTableName(category);

		Map boardMap = new HashMap();
		Map replyMap = new HashMap();
		
		boardMap.put("tableName", boardTableName);
		boardMap.put("boardNO", boardNO);
		
		replyMap.put("tableName", replyTableName);
		replyMap.put("boardNO", boardNO);
		replyMap.put("replyNO", replyNO);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try{
			boardService.setArticleComplete(boardMap, replyMap);
			boardService.upPoint(replyNickname);
			boardService.upPoint(boardNickname);
			message ="<script>alert('해당 답변을 채택했습니다.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
		} catch(Exception e) {
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	@LoginRequired
	@RequestMapping("/board/setFinalBoard.do")
	public ResponseEntity setFinalBoard(String category, int boardNO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String boardTableName = getBoardTableName(category);
		
		Map boardMap = new HashMap();
		
		boardMap.put("tableName", boardTableName);
		boardMap.put("boardNO", boardNO);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try{
			boardService.setArticleComplete(boardMap);
			message ="<script>alert('구인/구직을 완료했습니다.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
		} catch(Exception e) {
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardNO+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	
	
	@Override
	@LoginRequired
	@RequestMapping("/board/reportBoard.do")
	public ResponseEntity reportBoard(String category, int boardno, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map reportMap = new HashMap();
		
		String tableName = getBoardTableName(category);
		reportMap.put("tableName", tableName);
		reportMap.put("boardno", boardno);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try{
			boardService.reportBoard(reportMap);
			message ="<script>alert('해당 글을 신고하였습니다.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardno+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
		} catch(Exception e) {
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardno+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}

	@Override
	@LoginRequired
	@RequestMapping("/board/reportReply.do")
	public ResponseEntity reportReply(String category, int replyno, int boardno, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map reportMap = new HashMap();
		
		String tableName = getReplyTableName(category);
		reportMap.put("tableName", tableName);
		reportMap.put("replyno", replyno);
		reportMap.put("boardno", boardno);
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try{
			boardService.reportReply(reportMap);
			message ="<script>alert('해당 댓글을 신고하였습니다.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardno+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
		} catch(Exception e) {
			message ="<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='"+request.getContextPath()+"/board/viewArticle.do?category="+category+"&boardNO="+boardno+"';</script>";
			resEnt = new ResponseEntity(message, responseHeaders,  HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}

	private String getLikesTableName(String category) {
		return "c_board_"+category+"_likes";
	}
	private String getBoardSeqName(String category) {
		return "c_board_"+category+"_board_seq";
	}
	private String getReplySeqName(String category) {
		return "c_board_"+category+"_reply_seq";
	}	
	private String getBoardTableName(String category) {
		return "c_board_"+category+"_board";
	}
	private String getReplyTableName(String category) {
		return "c_board_"+category+"_reply";
	}
}

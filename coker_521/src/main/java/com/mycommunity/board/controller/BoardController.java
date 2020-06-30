package com.mycommunity.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycommunity.board.vo.ReplyVO;

public interface BoardController {
	public ModelAndView listArticles(@RequestParam("category") String category, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") Integer page, @RequestParam("perPageNum") Integer perPageNum,
			@RequestParam(required=false, defaultValue="title") String searchType, @RequestParam(required=false) String keyword) throws Exception;
	public ModelAndView viewArticle(@RequestParam("category") String category, @RequestParam("boardNO") int boardNO,  @RequestParam(required = false, defaultValue = "0") Integer isAdmin, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ResponseEntity removeArticle(@RequestParam("category") String category, @RequestParam("boardNO") int boardNO,  @RequestParam(required=false, defaultValue="0") Integer isAdmin, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity modArticle(@RequestParam("category") String category, @RequestParam("boardNO") int boardNO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity addArticle(@RequestParam("category") String category, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//2020.03.25 ���ð���
	//��� �߰�
	public ResponseEntity addReply(@ModelAttribute ReplyVO replyVo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	//��� ����
	public ResponseEntity deleteReply(@ModelAttribute ReplyVO reply, HttpServletRequest request, HttpServletResponse response) throws Exception;
	//��� ����
	public ResponseEntity modifyReply(@ModelAttribute ReplyVO reply, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ResponseEntity reportBoard(@RequestParam String category, @RequestParam int boardno, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity reportReply(@RequestParam String category, @RequestParam int replyno, @RequestParam int boardno, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}

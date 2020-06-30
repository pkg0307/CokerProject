package com.mycommunity.admin.board.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface AdminBoardController {
	public ModelAndView rankedMember(@RequestParam Map<String, Object> searchRankedMemberInfo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response, Integer page, Integer perPageNum, String searchType, String keyword) throws Exception;
}

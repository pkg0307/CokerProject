package com.mycommunity.admin.board.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycommunity.admin.board.service.AdminBoardService;
import com.mycommunity.admin.board.vo.AdminBoardVO;
import com.mycommunity.common.annotation.AdminOnly;
import com.mycommunity.common.base.BaseController;
import com.mycommunity.common.vo.PageCriteria;
import com.mycommunity.common.vo.PageMaker;
import com.mycommunity.member.vo.MemberVO;



@Controller("adminBoardController")
@RequestMapping(value="/admin/board")
public class AdminBoardControllerImpl extends BaseController implements AdminBoardController {
	@Autowired
	AdminBoardService adminBoardService;
	@Autowired
	AdminBoardVO adminBoardVO;
	
	//회원 순위
	@Override
	@RequestMapping(value="/rankedMember.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView rankedMember(@RequestParam Map<String, Object> searchRankedMemberInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		List<MemberVO> rankedMemberList = adminBoardService.getRankedMemberList(searchRankedMemberInfo);
		mav.setViewName(viewName);
		mav.addObject("rankedMemberList", rankedMemberList);
		for(MemberVO memberVO : rankedMemberList) {
			System.out.println(memberVO.getNickname());
		}
		return mav;
	}
	
	//신고된 게시글관리
	@Override
	@AdminOnly
	@RequestMapping(value="/reportedBoardList.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response,
			Integer page, Integer perPageNum, String searchType, String keyword) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		Map totalMap = new HashMap();
		Map<String, Object> search = new HashMap<String, Object>();
		//java, c�� �Խ���
		
		totalMap.put("searchType", searchType);
		System.out.println(searchType);
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
		
		List tableList = new ArrayList();
		tableList.add("c_share");
		tableList.add("cplus_share");
		tableList.add("java_share");
		tableList.add("python_share");
		tableList.add("freetalk");
		tableList.add("qna");
		totalMap.put("tableList", tableList);
		search.put("tableList", tableList);
		
		page = (page == null)?1:page;
		perPageNum = (perPageNum == null)?10:perPageNum;
		
		PageCriteria cri = new PageCriteria();
		cri.setPage(page);
		cri.setPerPageNum(perPageNum);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		int totalCount = adminBoardService.setArticleTotalCount(totalMap);
		pageMaker.setTotalCount(totalCount);
		
		System.out.println("pageMaker Data check : " + pageMaker.toString());
		System.out.println("pageCriteria Data check : " + cri.toString());
		
		//rowStart, rowEnd Setting & page, perPageNum setting
		search.put("cri", cri);
		search.put("searchType", searchType);
		
		//List articlesList = boardService.listArticles(tableName);
		List<AdminBoardVO> articlesList = adminBoardService.selectAllArticles(search);
		
		mav.addObject("articlesList", articlesList);
		for(AdminBoardVO adminBO : articlesList) {
			System.out.println(adminBO.getTitle());
		}
		//page, perPageNum
		mav.addObject("pageMaker", pageMaker);
		return mav;
	}
	
}

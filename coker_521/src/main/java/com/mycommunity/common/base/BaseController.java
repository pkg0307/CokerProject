package com.mycommunity.common.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycommunity.member.vo.MemberVO;

public abstract class BaseController {
	@RequestMapping(value={"/*/*.do", "/*/*/*.do"}, method={RequestMethod.POST,RequestMethod.GET})
	protected  ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("memberVO", new MemberVO());
		return mav;
	}

	public ModelAndView listArticles(String category, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}

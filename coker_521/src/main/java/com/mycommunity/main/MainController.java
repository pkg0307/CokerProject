package com.mycommunity.main;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycommunity.board.service.BoardService;
import com.mycommunity.common.base.BaseController;

@Controller("mainController")
public class MainController extends BaseController{
	@Autowired
	BoardService boardService;

	@RequestMapping(value= "/main/main.do", method=RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		String viewName = (String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		Map articlesMap = boardService.selectArticlesForMain();
		mav.addObject("articlesMap", articlesMap);
		return mav;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "forward:/main/main.do";
    }
}

package com.mycommunity.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mycommunity.common.annotation.AdminOnly;
import com.mycommunity.common.annotation.LoginRequired;
import com.mycommunity.common.exception.AuthenticationException;
import com.mycommunity.common.exception.AuthorizationException;
import com.mycommunity.member.service.MemberService;
import com.mycommunity.member.vo.MemberVO;

public class ViewNameInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if(handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
			MemberVO memberVO = (MemberVO)request.getSession().getAttribute("memberInfo");
			if (hm.hasMethodAnnotation(LoginRequired.class) && memberVO == null) {
                throw new AuthenticationException();
            }
			if(hm.hasMethodAnnotation(AdminOnly.class)) {
				if(memberVO == null) {
					throw new AuthenticationException();
				} else if(!memberVO.getNickname().equals("admin")) {
					throw new AuthorizationException();
				}
			}
			
			try {
				String viewName = getViewName(request);
				request.setAttribute("viewName", viewName);
				
				HttpSession session = request.getSession();
				Boolean isLogOn = (Boolean)session.getAttribute("isLogOn");
				if((isLogOn != null && isLogOn == true) && (memberVO != null)) {
						String nickname = memberVO.getNickname();
						int point = memberService.getPoint(nickname);
						memberVO.setPoint(point);
						int lvlUp = memberService.setLvl(memberVO);
						memberVO.setLvl(lvlUp);
						int lvl = memberService.getLvl(nickname);
						memberVO.setLvl(lvl);
						String imgName = memberService.getImgName(nickname);
						memberVO.setImgName(imgName);
						session.setAttribute("memberInfo", memberVO);	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;			
	}

	public String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String fileName = uri.substring(begin, end);
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/",1), fileName.length());
		}
		return fileName;
	}
}

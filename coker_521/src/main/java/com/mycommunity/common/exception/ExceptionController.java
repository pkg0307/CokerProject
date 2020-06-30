package com.mycommunity.common.exception;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(AuthenticationException.class)
	public ModelAndView authException(HttpServletRequest request, Exception e) {
		ModelAndView mv = new ModelAndView();
		
		String prePage = request.getHeader("Referer");
		
		String message="로그인이 필요합니다.";
		mv.addObject("message", message);
		mv.setViewName("/member/loginForm");
		mv.addObject("prePage",prePage);
		System.out.println(prePage);
		return mv;
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity authorizationException(HttpServletRequest request, Exception e) {
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		message = "<script>alert('관리자 권한 계정만 접근할 수 있습니다.');location.href='"+request.getContextPath()+"/main/main.do';</script>";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
}

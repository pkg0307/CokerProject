package com.mycommunity.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycommunity.member.vo.MemberVO;

public interface MemberController {
	public ModelAndView login(@RequestParam Map<String, String> loginMap,String prePage, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public String memberInsert(@Valid @ModelAttribute MemberVO memberVO, BindingResult bindResult, MultipartHttpServletRequest request, HttpServletResponse response)throws Exception; 
	public ResponseEntity addMember(@ModelAttribute MemberVO memberVO, MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception ;
	public ResponseEntity modifyInfo(MemberVO member, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public int overlapEmail(MemberVO memberVO, HttpServletRequest requset, HttpServletResponse response) throws Exception;
	public int overlapNickName(MemberVO memberVO, HttpServletRequest requset, HttpServletResponse response) throws Exception;
	public ModelAndView myPageMain(@RequestParam(required = false, value="message") String message, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView myInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ResponseEntity deleteMember(MemberVO memberVO, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public int passChk(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity uploadImage(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView searchUserEmail(@RequestParam Map<String, Object> searchUserInfo, HttpServletRequest request, HttpServletResponse response)throws Exception;
	public ModelAndView searchUserPw(@RequestParam (value="userEmail", required=false) String userEmail, @RequestParam (value="message", required=false) String message, HttpServletRequest request, HttpServletResponse response)throws Exception;
	public ResponseEntity resetPw(@ModelAttribute MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)throws Exception;
	public String modifyPw(@Valid @ModelAttribute MemberVO memberVO, BindingResult bindResult, HttpServletRequest request, HttpServletResponse response)throws Exception;
	
	public ModelAndView csCenterMain(HttpServletRequest request, HttpServletResponse response)throws Exception;

}

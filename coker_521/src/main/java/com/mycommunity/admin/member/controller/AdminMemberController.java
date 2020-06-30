package com.mycommunity.admin.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycommunity.member.vo.MemberVO;

public interface AdminMemberController {
	public ModelAndView memberDetail(HttpServletRequest request, HttpServletResponse response)throws Exception;
	public ModelAndView modifyMemberInfo(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)throws Exception;
	public ResponseEntity deleteMember(@RequestParam String userEmail, @RequestParam String nickName, @RequestParam String adminId, @RequestParam String adminPw, HttpServletRequest request, HttpServletResponse response)throws Exception;
	public ModelAndView listMembers(@RequestParam Map<String, Object> searchMemberInfo, Integer page, Integer perPageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity enableMember(@RequestParam Map<String, String> enableInfoMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	//계정 비밀번호 재설정 시 회원메일 인증
	public String resetPwCheckEmail(@RequestParam("checkUserEmail") String checkUserEmail, Model model, RedirectAttributes redAttr, HttpServletRequest request, HttpServletResponse response) throws Exception;
	//계정 비번 재설정 시 인증코드메일 발송
	public String authEmail(@RequestParam("userEmail") String userEmail, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception;
	//계정 비번 재설정 시 발송 인증코드와 회원입력 인증코드 비교 확인
	public String authCodeCompare(@ModelAttribute MemberVO memberVO, Model model,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public String overlapNickName(MemberVO memberVO, @RequestParam("origin_nickname") String originNickname, Model model, HttpServletRequest request, HttpServletResponse response)throws Exception;
}

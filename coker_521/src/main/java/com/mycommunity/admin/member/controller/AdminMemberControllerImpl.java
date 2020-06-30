package com.mycommunity.admin.member.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycommunity.admin.member.service.AdminMemberService;
import com.mycommunity.common.annotation.AdminOnly;
import com.mycommunity.common.base.BaseController;
import com.mycommunity.common.mailService.MailService;
import com.mycommunity.common.vo.PageCriteria;
import com.mycommunity.common.vo.PageMaker;
import com.mycommunity.member.vo.MemberVO;

@Controller("adminMemberController")
@RequestMapping(value="/admin/member")
public class AdminMemberControllerImpl extends BaseController implements AdminMemberController{
	private static final String ARTICLE_IMAGE_REPO = "D:\\profile_img";
	
	@Autowired AdminMemberService adminMemberService;
	@Autowired MailService mailService;
	@Inject PasswordEncoder passwordEncoder;
	@Autowired MemberVO memberVO;
	
	//회원정보 상세보기
	@Override
	@AdminOnly
	@RequestMapping(value="/memberDetail.do", method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView memberDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		String userEmail = request.getParameter("userEmail");
		MemberVO member_info = adminMemberService.memberDetail(userEmail);
		mav.addObject("member_info", member_info);
		return mav;
	} 
	//회원목록 전체보기
	@Override
	@AdminOnly
	@RequestMapping(value= "/listMembers.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listMembers(@RequestParam Map<String, Object> searchMemberInfo, Integer page, Integer perPageNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("검색 카테고리 : " + searchMemberInfo.get("searchCategory"));
		System.out.println("검색어 : " + searchMemberInfo.get("searchWord"));
		String viewName = (String)request.getAttribute("viewName");
				
		page = (page == null)?1:page;
		perPageNum = (perPageNum == null)?10:perPageNum;
		
		PageCriteria cri = new PageCriteria();
		cri.setPage(page);
		cri.setPerPageNum(perPageNum);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		int totalCount = adminMemberService.setMemberTotalCount(searchMemberInfo);
		pageMaker.setTotalCount(totalCount);
		
		searchMemberInfo.put("cri", cri);
		List<MemberVO> memberList = adminMemberService.listMembers(searchMemberInfo);
		for(MemberVO memberVO : memberList) {
			System.out.println("검색한 회원 : " + memberVO.getUserEmail());
		}
		if(memberList.isEmpty()) {
			System.out.println("검색결과 가져오기 실패");
		} else {
			System.out.println("검색결과 가져오기 성공");
		}
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("memberList", memberList);
		mav.addObject("pageMaker", pageMaker);
		return mav;
	}
	
	//회원정보 수정하기
	@Override
	@AdminOnly
	@RequestMapping(value="/modifyMemberInfo.do", method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView modifyMemberInfo(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("수정할 계정 : " + memberVO.getUserEmail());
		String userEmail = memberVO.getUserEmail();
		ModelAndView mav = new ModelAndView();
		String message = null;
		String originNickname = adminMemberService.getNickname(userEmail);
		System.out.println("오리지널 닉네임 : " + originNickname);
		System.out.println("수정할 닉네임 : " + memberVO.getNickname());
		boolean isMoved = true;
		try {
			//닉네임 수정했을 경우 프로필 사진 저장 디렉터리명 변경
			if(!originNickname.equals(memberVO.getNickname())) {
				File file = new File(ARTICLE_IMAGE_REPO + "\\" + originNickname + "\\");
				File fileToMove = new File(ARTICLE_IMAGE_REPO + "\\" + memberVO.getNickname() + "\\");
				isMoved = file.renameTo(fileToMove);
				System.out.println("프로필사진 저장 디렉터리 변경 여부  : " + isMoved);
			}
			adminMemberService.modifyMemberInfo(memberVO);
			System.out.println("계정정보 수정 완료");
			message = "회원 정보를 관리자 권한으로 수정하였습니다.";
		}catch (Exception e) {
			e.printStackTrace();
			message = "오류가 발생하였습니다. 시스템 정보를 확인해주세요.";
		}
		
		mav.addObject("message", message);
		mav.setViewName("forward:/admin/member/memberDetail.do");
		return mav;
	}
	
	//관리자권한으로 회원정보 수정시 중복 닉네임 확인
	@Override
	@RequestMapping(value = "/overlapNickName.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String overlapNickName(MemberVO memberVO, @RequestParam("origin_nickname") String originNickname, Model model, HttpServletRequest request, HttpServletResponse response)throws Exception {
		String modNickname = memberVO.getNickname();
		System.out.println("본래 닉네임 : " + originNickname);
		System.out.println("바꿀 닉네임 : " + modNickname);
		int result = 0;
		if(!modNickname.equals(originNickname)) {			
			result = adminMemberService.overlapNickName(memberVO);
		}
		String message = null;
		String moveTo = null;
		System.out.println("닉네임 중복체크");
		System.out.println("닉네임 중복 체크 결과 : " + result);
		if(result != 0) {
			//중복된 닉네임일 경우 message내용을 jsp에서 alert로 표시 회원정보보기로 이동 
			message = "사용할 수 없는 닉네임 입니다.";
			moveTo =  "/admin/member/memberDetail";
			model.addAttribute("member_info", memberVO);
			model.addAttribute("message", message);
		} else {
			//중복되지 않은 닉네임이 확인되면 회원정보 수정으로 이동
			model.addAttribute(memberVO);
			moveTo =  "forward:/admin/member/modifyMemberInfo.do";
		}
		return moveTo;
	}
	
	//관리자(admin) 계정으로 회원 삭제
	@Override
	@AdminOnly
	@RequestMapping(value="/deleteMember.do", method= {RequestMethod.POST, RequestMethod.GET})
	//삭제할 회원 아이디와 삭제하는 admin의 비밀번호를 RequestParam 애너테이션으로 받아옴
	public ResponseEntity deleteMember(@RequestParam("userEmail") String userEmail, @RequestParam("nickname") String nickname, @RequestParam("adminId") String adminId, @RequestParam("adminPw") String adminPw, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		System.out.println("삭제할 계정  : " + userEmail);
		System.out.println("관리자 계정 : " + adminId);
		System.out.println("관리자 암호확인 : " + adminPw);
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		//Admin 비밀번호를 checkPw를 통해 확인, 비밀번호 일치시 true값 반환
		boolean adminCheckResult = checkAdmin(adminId, adminPw, request);
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		if(adminCheckResult) {
			try {
				message = "<script>";
				message += "alert('관리자 비밀번호가 확인되었습니다.');";
				//프로필 사진 디덱터리 삭제 메소드 호출
				boolean delYn = deleteDirectory(new File(ARTICLE_IMAGE_REPO + "\\" + nickname + "\\"));
				if(delYn) {
					System.out.println("관리자 알림 : 프로필 사진 디렉터리 삭제 완료" );
				} else {
					System.out.println("관리자 알림 : 프로필 사진 디렉터리 삭제 실패, 디렉터리를 확인해주세요." );
				}
				//삭제할 회원의 Email을 deleteMeber로 보냄
				adminMemberService.deleteMember(userEmail);
				message +=" alert('선택하신 계정을 삭제하였습니다.');";
				//삭제 성공시 회원전체목록 불러오기
				message += " location.href='"+request.getContextPath()+"/admin/member/listMembers.do';";
				message += " </script>";
			}catch (Exception e) {
				message = "<script>";
				message += "alert('알 수 없는 오류입니다.');";
				message += "</script>";
				e.printStackTrace();
			}
		}else {
			try {
				message = "<script>";
				message += "alert('관리자 비밀번호를 잘못 입력하셨습니다. 비밀번호를 다시 확인해주세요.');";
				//비밀번호가 틀렸을시 회워전체목록으로 이동
				message += " location.href='"+request.getContextPath()+"/admin/member/listMembers.do';";
				message += "</script>";
			} catch (Exception e) {
				message = "<script>";
				message += "alert('알 수 없는 오류입니다.');";
				message += "</script>";
				e.printStackTrace();
			}
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
		
	} 
	//회원삭제시 프로필 사진 디렉터리 삭제 메소드
	private boolean deleteDirectory(File deleteDir) throws Exception {
		System.out.println("프로필 디렉터리 삭제 메소드 시작");
		System.out.println("받아온 디렉터리 경로: " + deleteDir);
		//디렉터리 경로 존재 확인
		if(!deleteDir.exists()) {
			return false;
		}
		
		//삭제할 디렉터리 경로의 하위 파일 불러오기
		File[] files = deleteDir.listFiles();
		
		
		for (File file : files) {
			if(file.isDirectory()) { //불러온 하위 파일이 디렉터리인 경우 재귀 함수로 deleteDirectory 메소드 호출, 하위 디렉터리의 하위 파일을 다시 확인
				System.out.println("재귀함수 시작");
				deleteDirectory(file);
			} else {
				System.out.println("파일 삭제 시작"); //불러온 하위 파일이 일반 파일인 경우 삭제
				file.delete();
			}
		}
		if(deleteDir.delete()) { //모든 하위 파일 삭제 후 본 디렉터리 삭제
			System.out.println("디렉터리 삭제 성공");
			return true;
		} else {
			System.out.println("디렉터리 삭제 실패");
			return false;
		}
	}
	
	//계정상태변경(활성화/비활성화)
	@Override
	@AdminOnly
	@RequestMapping(value = "/enableMember.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity enableMember(@RequestParam Map<String, String> enableInfoMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		 System.out.println("상태변경 계정 : " + enableInfoMap.get("userEmail"));
		 System.out.println("계정 상태코드 : " + enableInfoMap.get("enable"));
		 System.out.println("상태변경 사유 : " + enableInfoMap.get("disable_reason"));
		 System.out.println("상태변경 계정 비활성화 기간 : " + enableInfoMap.get("disable_term") + "일");
		 System.out.println("관리자 ID : " + enableInfoMap.get("adminId"));
		 System.out.println("관리자 PW : " + enableInfoMap.get("certPw"));
		 
		String adminId = enableInfoMap.get("adminId");
		String adminPw = enableInfoMap.get("certPw");
		String userEmail = enableInfoMap.get("userEmail");
		
		System.out.println(enableInfoMap);
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		//계정상태를 변경하려는 관리자와 ID와 비밀번호 일치 확인
		boolean adminCheckResult = checkAdmin(adminId, adminPw, request);
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		if (adminCheckResult) {
			//계정상태변경
			adminMemberService.enableMember(enableInfoMap);
			//변경된 계정의 상태를 확인(받는 값 : 활성화 - '1', 비활성화 - '0') 
			String checkEnable = adminMemberService.checkEnableMember(userEmail);
			if (checkEnable.equals("0")) {
				try {
					message = "<script>";
					message += "alert('계정을 비활성화 하였습니다.');";
					message += " location.href='" + request.getContextPath() + "/admin/member/memberDetail.do?userEmail="+ userEmail+"';";
					message += " </script>";
				} catch (Exception e) {
					message = "<script>";
					message += "alert('알 수 없는 오류가 발생하였습니다.');";
					message += " location.href='" + request.getContextPath() + "/admin/member/listMembers.do';";
					message += " </script>";
					e.printStackTrace();
				}

			} else {
				try {
					message = "<script>";
					message += "alert('계정을 활성화 하였습니다.');";
					message += " location.href='" + request.getContextPath() + "/admin/member/memberDetail.do?userEmail="+ userEmail+"';";
					message += " </script>";
				} catch (Exception e) {
					message = "<script>";
					message += "alert('알 수 없는 오류가 발생하였습니다.');";
					message += " location.href='" + request.getContextPath() + "/admin/member/listMembers.do';";
					message += " </script>";
					e.printStackTrace();
				}
			}
		} else {
			try {
				message = "<script>";
				message += "alert('관리자 비밀번호가 잘못입력되었습니다.');";
				message += " location.href='" + request.getContextPath() + "/admin/member/memberDetail.do?userEmail="+ userEmail+"';";
				message += "</script>";
			} catch (Exception e) {
				message = "<script>";
				message += "alert('알수없는 오류 입니다.');";
				message += " location.href='" + request.getContextPath() + "/admin/member/listMembers.do';";
				message += "</script>";
				e.printStackTrace();
			}
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	//관리자 계정과 비밀번호 일치여부 확인(계정삭제와 계정상태변경에서 사용)
	private boolean checkAdmin(String adminId, String adminPw, HttpServletRequest request) throws Exception {
		HashMap<String, String> adminMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		System.out.println(memberVO.getUserPw());
		String encAdminPw = memberVO.getUserPw();
		if(passwordEncoder.matches(adminPw,encAdminPw)) {
        adminMap.put("adminId", adminId);
        adminMap.put("adminPw", encAdminPw);
		}
		boolean checkAdminResult = adminMemberService.checkAdmin(adminMap);
		
		return checkAdminResult;
		
	}
	
	//계정상태변경 설정
	@AdminOnly
	@RequestMapping(value = "/enableMemberForm.do", method={RequestMethod.GET, RequestMethod.POST})
	private ModelAndView enableMemberForm(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("상태변경할 계정 ID : " + memberVO.getUserEmail());
		System.out.println("상태변경할 계정 닉네임 : " + memberVO.getNickname());
		System.out.println("현재 계정상태 : " + memberVO.getEnable());
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("member_info", memberVO);
		
		return mav;
	}

	//비밀번호 재설정시 회원 이메일 인증
	@Override
	@RequestMapping(value= "/resetPwCheckEmail.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String resetPwCheckEmail(@RequestParam("checkUserEmail") String checkUserEmail, Model model, RedirectAttributes redAttr, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("비밀번호 재설정 요청 이메일 : " + checkUserEmail);
		String userEmail = adminMemberService.resetPwCheckUserEmail(checkUserEmail);
		String message = null;
		System.out.println(checkUserEmail + " : " + userEmail);
		if(checkUserEmail.equals(userEmail)) {
			message = "코커 커뮤니티에 가입하신 이메일이 확인되었습니다.";
			redAttr.addAttribute("userEmail", userEmail);
		} else {
			message = "해당 이메일로 가입된 계정이 존재하지 않습니다.";
		}
		System.out.println("결과 : " + message );
		redAttr.addAttribute("message", message);
		
		return "redirect:/member/resetUserPwForm.do";
	}
	
	//비밀번호 재설정시 인증코드 발송
   @Override
   @RequestMapping(value = "/authEmail.do", method={RequestMethod.GET, RequestMethod.POST})
   public String authEmail(@RequestParam("userEmail") String userEmail, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  int ran = new Random().nextInt(900000) + 100000;//인증코드 생성
      String authCode = String.valueOf(ran);
      System.out.println(userEmail);
      System.out.println(ran);
      String message = null;
      String subject = "코커 계정 비밀번호 재설정 인증 코드 안내입니다.";
      StringBuilder sb = new StringBuilder();
         
       sb.append("<html>"); 
       sb.append("<body>"); 
       sb.append("<div style='width:60%; text-align:center;'>"); 
       sb.append("<div style='background-color:black; color:white;'>");
       sb.append("<h2>코커</h2>"); 
       sb.append("</div>"); 
       sb.append("<div>");
       sb.append("<p>귀하의 인증의 코드는 <span style='color:red;'>" + authCode + "</span> 입니다.</p>");
       sb.append("<p>감사합니다.</p>"); sb.append("</div>");
       sb.append("</div>");        
       sb.append("</body></html>"); 
      //인증코드메일 전송
      boolean sendResult = mailService.send(subject, sb.toString(), userEmail, null);
      if(sendResult) {
         message = "메일 발송에 성공하였습니다. 이메일함을 확인해주세요.";
         model.addAttribute("message", message);
         model.addAttribute("userEmail", userEmail);
         model.addAttribute("authCode", authCode);         
      } else {
         message = "메일 발송에 실패하였습니다. 다시 시도해주세요.";
         model.addAttribute("message", message);         
      }
      return "/member/resetUserPwForm";
   }

   //비밀번호 재설정 시 메일로 발송된 인증코드와 회원이 입력한 인증코드 비교
   @Override
   @RequestMapping(value="/authCodeCompare.do", method= {RequestMethod.GET, RequestMethod.POST})
   public String authCodeCompare(@ModelAttribute MemberVO memberVO, Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
      System.out.println("메소드실행");
      System.out.println(memberVO.getUserEmail());
      Boolean result = true;
      String message = null;
      String originalCode = memberVO.getAuthCode();
      String insertCode = memberVO.getInsertCode();
      String userEmail = memberVO.getUserEmail();
      model.addAttribute("userEmail", userEmail);
      if(originalCode.equals(insertCode)) {
         message = "인증에 성공하였습니다.";         
         model.addAttribute("message", message);
         model.addAttribute("codeCompareResult", result);
      }else {
         result = false;
         message = "인증번호를 잘못 입력하셨습니다.";   
         if(originalCode.equals(memberVO.getAuthCode())) {
            model.addAttribute("authCode", originalCode);
         }
         model.addAttribute("message", message);
         model.addAttribute("codeCompareResult", result);
      }
      
      return "/member/resetUserPwForm";
   }
	
}



package com.mycommunity.member.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycommunity.common.base.BaseController;
import com.mycommunity.member.service.MemberService;
import com.mycommunity.member.vo.MemberVO;

@Controller("memberController")
@RequestMapping(value = "/member")
public class MemberControllerImpl extends BaseController implements MemberController {
	private static final String ARTICLE_IMAGE_REPO = "D:\\profile_img";
	@Autowired
	MemberService memberService;
	@Autowired
	MemberVO memberVO;
	// 페이스북 로그인 잠정 보류로 인하여 주석처리함
	/*
	 * @Autowired FacebookConnectionFactory connectionFactory;
	 * 
	 * @Autowired OAuth2Parameters oAuth2Parameters;
	 */
	@Inject
	PasswordEncoder passwordEncoder;

	// 로그인
	@Override
	@RequestMapping(value = "/login.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView login(@RequestParam Map<String, String> loginMap, String prePage, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		memberVO = memberService.login(loginMap);
		if (memberVO == null) {
			String message = "해당 이메일이 존재하지 않습니다.";
			mav.addObject("message", message);
			mav.setViewName("/member/loginForm");
		} else {
			String userPw = memberVO.getUserPw();
			String rawPw = loginMap.get("userPw");
			System.out.println("회원비밀번호 : " + userPw);
			System.out.println("입력비밀번호 : " + rawPw);
			System.out.println("매치 결과 : "+ passwordEncoder.matches(rawPw, userPw));
			if (passwordEncoder.matches(rawPw, userPw)) {
				if (memberVO.getEnable().equals("1")) {
					HttpSession session = request.getSession();
					session = request.getSession();
					session.setAttribute("isLogOn", true);
					session.setAttribute("memberInfo", memberVO);
					String action = (String) session.getAttribute("action");
					if (action != null && action.equals("/member/memberForm.do")) {
						mav.setViewName("forward:" + action);
					} else if (prePage != null) {
						mav.setViewName("redirect:" + prePage);
					} else {
						mav.setViewName("redirect:/main/main.do");
					}
				} else {
					mav.addObject("disableMemberInfo", memberVO);
					mav.setViewName("/member/disableMemberForm");
				}
			} else {
				String message = "비밀번호가 틀립니다.";
				mav.addObject("message", message);
				mav.setViewName("/member/loginForm");
			}
		}
		return mav;
	}

	// 회원가입 시 입력값 유효성 검사
	@Override
	@RequestMapping(value = "/memberInsert.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String memberInsert(@Valid @ModelAttribute MemberVO memberVO, BindingResult bindResult,
			MultipartHttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//유효성 검사 실패시 에러확인
		List<ObjectError> errorList = bindResult.getAllErrors();
		for (ObjectError errors : errorList) {
			System.out.println("에러 :" + errors);
		}
		if (bindResult.hasErrors()) {
			return "/member/memberForm";
		}
		System.out.println("유효성검사 통과");
		return "forward:/member/addMember.do";
	}

	// 로그아웃
	@Override
	@RequestMapping(value = "/logout.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		mav.setViewName("redirect:/main/main.do");
		return mav;
	}

	// 회원가입
	@Override
	@RequestMapping(value = "/addMember.do", method = RequestMethod.POST)
	public ResponseEntity addMember(@ModelAttribute MemberVO memberVO, MultipartHttpServletRequest multipartRequest,
	         HttpServletResponse response) throws Exception {
	      response.setContentType("text/html;charset=utf-8");
	      multipartRequest.setCharacterEncoding("utf-8");
	      int result1 = memberService.overlapEmail(memberVO);
	      int result2 = memberService.overlapNickName(memberVO);

	      String message = null;
	      ResponseEntity resEntity = null;
	      HttpHeaders responseHeaders = new HttpHeaders();
	      responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	      //중복되지 않은 이메일과 닉네임을 썼을 경우에만 회원가입 로직 진입
	      if(result1 == 0 && result2 == 0) {
	         try {
	            // 비밀번호 암호화
	            String encPassword = passwordEncoder.encode(memberVO.getUserPw());
	            memberVO.setUserPw(encPassword);
	            memberService.addMember(memberVO);
	            //회원가입 로직을 수행한 후에 프로필사진 디렉토리 생성
	            File outputFile = new File(ARTICLE_IMAGE_REPO + "\\" + memberVO.getNickname() + "\\" + "newbie.jpeg");
	            if (!outputFile.exists()) { // 경로상에 파일이 존재하지 않을 경우
	               outputFile.getParentFile().mkdirs();
	            }
	            URL url = null;
	            BufferedImage bi = null;

	            try {
	               url = new URL("https://data.ac-illust.com/data/thumbnails/8f/8f4dcc02c8e3df06b6d6ac51c2e375bd_t.jpeg");
	               bi = ImageIO.read(url);
	               ImageIO.write(bi, "jpeg", outputFile);
	               bi.flush();
	            } catch (Exception e) {
	               e.printStackTrace();
	            }
	            message = "<script>";
	            message += "alert('환영합니다. 회원가입이 완료 되었습니다.\\n가입축하 10포인트 지급! 로그인해주세요.');";
	            message += "location.href='" + multipartRequest.getContextPath() + "/main/main.do';";
	            message += "</script>";
	         } catch (Exception e) {
	               message = "<script>";
	               message += "alert('알수 없는 오류가 발생했습니다. 다시 시도해 주세요.');";
	               message += "location.href='" + multipartRequest.getContextPath() + "/member/memberForm.do';";
	               message += "</script>";
	               // e.printStackTrace();
	         }

	      }else if(result1 != 0) {//중복된 이메일로 회원가입 버튼을 눌렀을 경우
	         message = "<script>";
	         message += "alert('사용할 수 없는 이메일입니다. 다시 시도해 주세요.');";
	         message += "location.href='" + multipartRequest.getContextPath() + "/member/memberForm.do';";
	         message += "</script>";
	      }else if(result2 != 0) {//중복된 닉네임으로 회원가입 버튼을 눌렀을 경우
	         message = "<script>";
	         message += "alert('사용할 수 없는 닉네임입니다. 다시 시도해 주세요.');";
	         message += "location.href='" + multipartRequest.getContextPath() + "/member/memberForm.do';";
	         message += "</script>";
	      }else if(memberVO.getUserEmail().isEmpty() || memberVO.getUserPw().isEmpty()
	               || memberVO.getNickname().isEmpty() || memberVO.getUserName().isEmpty()
	               || memberVO.getTel().isEmpty()) {
	         message = "<script>";
	         message += "alert('오류가 발생했습니다. 다시 시도해 주세요. 같은 오류가 반복된다면 관리자에게 문의해주세요.');";
	         message += "location.href='" + multipartRequest.getContextPath() + "/member/memberForm.do';";
	         message += "</script>";
	      }
	      
	      resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
	      return resEntity;
	   }

	// email 중복 체크
	@ResponseBody
	@RequestMapping(value = "/overlapEmail.do", method = { RequestMethod.POST, RequestMethod.GET })
	public int overlapEmail(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int result = memberService.overlapEmail(memberVO);
		System.out.println(memberVO.getUserEmail());
		System.out.println("이메일 중복체크");
		return result;
	}

	// 닉네임 중복 체크
	@ResponseBody
	@RequestMapping(value = "/overlapNickName.do", method = { RequestMethod.POST, RequestMethod.GET })
	public int overlapNickName(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int result = memberService.overlapNickName(memberVO);
		System.out.println("닉네임 중복체크");
		return result;
	}
	
	// 회원정보 수정시 입력값 유효성 검사
		@Override
		@RequestMapping(value = "/validModifyPw.do", method = { RequestMethod.POST, RequestMethod.GET })
		public String modifyPw(@Valid @ModelAttribute MemberVO memberVO, BindingResult bindResult, 
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<ObjectError> errorList = bindResult.getAllErrors();
			for (ObjectError errors : errorList) {
				System.out.println("에러 :" + errors);
			}
			if (bindResult.hasErrors()) {
				return "/member/modifyPw";
			}
			System.out.println("유효성검사 통과");
			return "forward:/member/modifyInfo.do";
		}

	// 회원 정보 수정
	@Override
	@ResponseBody
	@RequestMapping(value = "/modifyInfo.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity modifyInfo(MemberVO memberVO, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		boolean isMoved = true;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");
		//프로필사진 저장 디렉토리명 새로운 닉네임으로 변경
		if(!memberInfo.getNickname().equals(memberVO.getNickname())) {
			File file = new File(ARTICLE_IMAGE_REPO + "\\" + memberInfo.getNickname() + "\\");
			File fileToMove = new File(ARTICLE_IMAGE_REPO + "\\" + memberVO.getNickname() + "\\");
			isMoved = file.renameTo(fileToMove);
			System.out.println(isMoved);
		}
		if(isMoved) {
			try {
				//비밀번호 변경을 하지 않을 경우 재암호화 방지
				if(!memberInfo.getUserPw().equals(memberVO.getUserPw())) {					
					String encPassword = passwordEncoder.encode(memberVO.getUserPw());
					memberVO.setUserPw(encPassword);
				}
				memberService.modifyInfo(memberVO);
				System.out.println("세션 pw : " + memberInfo.getUserPw());
				System.out.println("유저 pw : " + memberVO.getUserPw());
				System.out.println("세션 ni : " + memberInfo.getNickname());
				System.out.println("유저 ni : " + memberVO.getNickname());
				System.out.println("세션 te : " + memberInfo.getTel());
				System.out.println("유저 te : " + memberVO.getTel());
				session.setAttribute("memberInfo", memberVO);
				message = "<script>";
				message += "alert('정보가 수정되었습니다.');";
				message += "location.href='" + request.getContextPath() + "/member/myPageMain.do';";
				message += "</script>";
			} catch (Exception e) {
				message = "<script>";
				message += "alert('오류가 발생했습니다. 다시 시도해 주세요.');";
				message += "location.href='" + request.getContextPath() + "/member/myInfo.do';";
				message += "</script>";
				e.printStackTrace();
			}
		} else {
			//변경한 닉네임과 같은 이름의 프로필 사진 디렉토리가 있을 경우
			message = "<script>";
			message += "alert('닉네임 수정 오류가 발생했습니다. 다시 시도해 주세요. 같은 오류가 반복 된다면 관리자에게 문의해주세요.');";
			message += "location.href='" + request.getContextPath() + "/member/myInfo.do';";
			message += "</script>";
		}
		
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	// 회원 정보 메인 페이지
	@Override
	@RequestMapping(value = "/myPageMain.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView myPageMain(@RequestParam(required = false, value = "message") String message,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session = request.getSession();

		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		memberVO = (MemberVO) session.getAttribute("memberInfo");

		mav.addObject("message", message);

		return mav;
	}

	// 정보 수정 페이지
	 @Override 
	 @RequestMapping(value="/myInfo.do", method = {RequestMethod.POST,RequestMethod.GET}) public ModelAndView myInfo(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		 String viewName =(String)request.getAttribute("viewName"); 
		 ModelAndView mav = new ModelAndView(viewName); 
		 
		 return mav; 
	}
	 
	// 회원 탈퇴
	@Override
	@ResponseBody
	@RequestMapping(value = "/deleteMember.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity deleteMember(MemberVO memberVO, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String userPw = memberService.passChk(memberVO);
		String rawPw = memberVO.getUserPw();
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");
		if (passwordEncoder.matches(rawPw, userPw)) {
			//프로필 사진 디덱터리 삭제 메소드 호출
			boolean delYn = deleteDirectory(new File(ARTICLE_IMAGE_REPO + "\\" + memberInfo.getNickname() + "\\"));
			if(delYn) {
				System.out.println("관리자 알림 : 프로필 사진 디렉터리 삭제 완료" );
			} else {
				System.out.println("관리자 알림 : 프로필 사진 디렉터리 삭제 실패, 디렉터리를 확인해주세요." );
			}
			memberService.deleteMember(memberInfo);
			session.invalidate();
			message = "<script>";
			message += "alert('탈퇴 되었습니다.');";
			message += "location.href='" + request.getContextPath() + "/main/main.do';";
			message += "</script>";
		} else {
			message = "<script>";
			message += "alert('오류가 발생했습니다. 비밀번호를 확인하여주세요.');";
			message += "location.href='" + request.getContextPath() + "/member/myPageMain.do';";
			message += "</script>";
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;

	}
	//회원탈퇴시 프로필 사진 디렉터리 삭제 메소드
	private boolean deleteDirectory(File deleteDir) throws Exception {
		System.out.println("프로필 디렉터리 삭제 메소드 시작");
		System.out.println("받아온 디렉터리 : " + deleteDir);
		//디렉터리 경로 존재 확인
		if(!deleteDir.exists()) {
			return false;
		}
		//삭제할 디렉터리 경로의 하위 파일 불러오기
		File[] files = deleteDir.listFiles();
		
		for (File file : files) {
			if(file.isDirectory()) {
				System.out.println("재귀함수 시작");//불러온 하위 파일이 디렉터리인 경우 재귀 함수로 deleteDirectory 메소드 호출, 하위 디렉터리의 하위 파일을 다시 확인
				deleteDirectory(file);
			} else {
				System.out.println("파일 삭제 시작");//불러온 하위 파일이 일반 파일인 경우 삭제
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

	// 회원 탈퇴 비밀번호 확인
	@Override
	@ResponseBody
	@RequestMapping(value = "/passChk.do", method = { RequestMethod.POST, RequestMethod.GET })
	public int passChk(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(memberVO.getUserEmail()+", "+memberVO.getUserPw());
		
		String userPw = memberService.passChk(memberVO);
		String rawPw = memberVO.getUserPw();
		int result=0;
		if(passwordEncoder.matches(rawPw, userPw)) {
			result=1;
		}
		return result;
	}

	// 프로필 사진 저장
	@Override
	@RequestMapping(value = "/uploadImage.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity uploadImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		String imgFileName = null;

		Map imageMap = new HashMap();
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String nickname = memberVO.getNickname();
		imageMap.put("nickname", nickname);

		imgFileName = upload(multipartRequest);
		String prfImgName = getPrfImgName(imgFileName, nickname);
		imageMap.put("imgName", prfImgName);

		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try {
			memberService.addProfileImg(imageMap);
			if (imgFileName != null && imgFileName != "") {
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imgFileName);
				// File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+nickname);
				File destFile = new File(ARTICLE_IMAGE_REPO + "\\" + nickname + "\\" + prfImgName);
				if (!destFile.exists()) {
					destFile.getParentFile().mkdirs();
				}
				// FileUtils.moveFileToDirectory(srcFile, destDir,true); 경로로 이동
				File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + nickname);
				FileUtils.forceDelete(oldFile);
				FileUtils.moveFile(srcFile, destFile);
			}

			memberVO.setImgName(prfImgName);
			session.setAttribute("memberInfo", memberVO);

			message = "<script>alert('프로필 사진을 설정하였습니다.'); location.href='" + multipartRequest.getContextPath()
					+ "/member/myPageMain.do';</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			message = "<script>alert('오류가 발생했습니다. 다시 시도하세요.'); location.href='" + multipartRequest.getContextPath()
					+ "/member/myPageMain.do';</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}

	private String getPrfImgName(String imgFileName, String nickname) {
		String prfImgName = null;
		int pathPoint = imgFileName.lastIndexOf(".");
		String filePoint = imgFileName.substring(pathPoint + 1, imgFileName.length());
		String fileType = filePoint.toLowerCase();
		return "profileImage_" + nickname + "." + fileType;
	}

	// 이미지 업로드 메소드
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
		String imageFileName = null;
		Iterator<String> fileNames = multipartRequest.getFileNames();

		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName = mFile.getOriginalFilename();
			File file = new File(ARTICLE_IMAGE_REPO + "\\" + fileName);
			File tempFile = new File(ARTICLE_IMAGE_REPO + "\\temp");
			if (mFile.getSize() != 0) { // File Null Check
				if (!file.exists()) { // 경로상에 파일이 존재하지 않을 경우
					if (file.getParentFile().mkdirs()) { // 경로에 해당하는 디렉터리 생성
						file.createNewFile();
					}
				}
				if (!tempFile.exists()) {
					tempFile.mkdirs();
				}
				mFile.transferTo(new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName)); // 임시로 저장된
																										// multipartFile을
																										// 실제 파일로 전송
			}
		}
		return imageFileName;
	}

	// 고객센터
	@Override
	@RequestMapping(value = "/csCenterMain.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView csCenterMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		String noticeTitle = memberService.getNoticeTitle();
		System.out.println(noticeTitle);
		mav.addObject("noticeTitle", noticeTitle);
		return mav;
	}

	@Override
	@RequestMapping(value = "/searchUserEmail.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView searchUserEmail(@RequestParam Map<String, Object> searchUserInfo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("찾는 계정에 등록된 본명 : " + searchUserInfo.get("userName"));
		System.out.println("찾는 계정에 등록된 휴대전화 번호 : " + searchUserInfo.get("tel"));
		List<String> userEmailList = memberService.searchUserEmail(searchUserInfo);
		String message = null;
		for(int i = 0; i < userEmailList.size(); i++) {
			System.out.println("찾은계정 : " + userEmailList.get(i));			
		}
		if (userEmailList == null || userEmailList.isEmpty()) {
			message = "입력한 정보와 일치하는 계정이 없습니다. 다시 입력해주세요.";
			mav.addObject("message", message);
		}
		mav.addObject("userEmailList", userEmailList);
		mav.setViewName("forward:/member/searchUserEmailForm.do");

		return mav;
	}

	// 회원 비번 재설정 창 보기
	@Override
	@RequestMapping(value = "/resetUserPwForm.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView searchUserPw(@RequestParam(value = "userEmail", required = false) String userEmail,
			@RequestParam(value = "message", required = false) String message, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		System.out.println("비밀번호 재설정 시작");
		System.out.println("계정명 : " + userEmail);
		mav.addObject("userEmail", userEmail);
		mav.addObject("message", message);
		mav.setViewName(viewName);

		return mav;
	}

	// 고객센터 비밀번호 재설정시 유효성 검사
	@RequestMapping(value = "/validResetPw.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String validResetPw(@Valid @ModelAttribute MemberVO memberVO, BindingResult bindResult, Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean result = true;
		String userEmail = memberVO.getUserEmail();
		List<ObjectError> errorList = bindResult.getAllErrors();
		for (ObjectError errors : errorList) {
			System.out.println("에러 :" + errors);
		}
		if (bindResult.hasErrors()) {
			model.addAttribute("userEmail", userEmail);
			model.addAttribute("codeCompareResult", result);
			return "/member/resetUserPwForm";
		}
		System.out.println("유효성검사 통과");
		return "forward:/member/resetPw.do";
	}

	// 회원 비번 재설정
	@Override
	@RequestMapping(value = "/resetPw.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity resetPw(@ModelAttribute MemberVO memberVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String userEmail = memberVO.getUserEmail();
		String newPw = memberVO.getUserPw();
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "text/html; charset=utf-8");

		Map<String, Object> resetPwInfo = new HashMap<String, Object>();
		System.out.println("비밀번호 재설정 계정명 : " + userEmail);
		String encNewPw = passwordEncoder.encode(newPw);
		resetPwInfo.put("userEmail", userEmail);
		resetPwInfo.put("newPw", encNewPw);
		int result = memberService.resetPw(resetPwInfo);
		if (result == 1) {
			System.out.println(result);
			try {
				message = "<script>";
				message += "alert('계정 비밀번호 재설정이 완료되었습니다.');";
				message += "location.href='" + request.getContextPath() + "/member/loginForm.do';";
				message += "</script>";
			} catch (Exception e) {
				message = "<script>";
				message += "alert('알 수 없는 오류입니다..');";
				message += "location.href='" + request.getContextPath() + "/member/resetUserPwForm.do';";
				message += "</script>";
				e.printStackTrace();
			}
		} else {
			try {
				message = "<script>";
				message += "alert('계정 비밀번호 재설정이 실패하였습니다. 다시 시도해주세요.');";
				message += "location.href='" + request.getContextPath() + "/member/resetUserPwForm.do';";
				message += "</script>";
			} catch (Exception e) {
				message = "<script>";
				message += "alert('알 수 없는 오류입니다..');";
				message += "location.href='" + request.getContextPath() + "/member/resetUserPwForm.do';";
				message += "</script>";
				e.printStackTrace();
			}
		}
		resEntity = new ResponseEntity(message, resHeaders, HttpStatus.OK);
		return resEntity;

	}

	// 페이스북(소셜)로그인 잠정 보류로 추석처리함
	/*
	 * //페이스북 로그인 관련
	 * 
	 * @RequestMapping(value="/loginForm.do", method = {RequestMethod.POST,
	 * RequestMethod.GET}) public String faceboookLogin(Model model,
	 * HttpServletRequest request, HttpServletResponse response) throws Exception {
	 * 
	 * OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
	 * String facebook_url =
	 * oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE,
	 * oAuth2Parameters);
	 * 
	 * model.addAttribute("facebook_url", facebook_url);
	 * System.out.println("/facebook" + facebook_url);
	 * 
	 * return "/member/loginForm"; }
	 * 
	 * //페이스북로그인 콜백
	 * 
	 * @RequestMapping(value = "/facebookSignInCallback", method = {
	 * RequestMethod.GET, RequestMethod.POST }) public String
	 * facebookSignInCallback(@RequestParam String code) throws Exception {
	 * 
	 * try { String redirectUri = oAuth2Parameters.getRedirectUri();
	 * System.out.println("Redirect URI : " + redirectUri);
	 * System.out.println("Code : " + code);
	 * 
	 * OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
	 * AccessGrant accessGrant = oauthOperations.exchangeForAccess(code,
	 * redirectUri, null); String accessToken = accessGrant.getAccessToken();
	 * System.out.println("AccessToken: " + accessToken); Long expireTime =
	 * accessGrant.getExpireTime();
	 * 
	 * 
	 * if (expireTime != null && expireTime < System.currentTimeMillis()) {
	 * accessToken = accessGrant.getRefreshToken(); };
	 * 
	 * 
	 * Connection<Facebook> connection =
	 * connectionFactory.createConnection(accessGrant); Facebook facebook =
	 * connection == null ? new FacebookTemplate(accessToken) : connection.getApi();
	 * UserOperations userOperations = facebook.userOperations();
	 * 
	 * try
	 * 
	 * { String [] fields = { "id", "email", "name"}; User userProfile =
	 * facebook.fetchObject("me", User.class, fields); System.out.println("유저이메일 : "
	 * + userProfile.getEmail()); System.out.println("유저 id : " +
	 * userProfile.getId()); System.out.println("유저 name : " +
	 * userProfile.getName());
	 * 
	 * } catch (MissingAuthorizationException e) { e.printStackTrace(); }
	 * 
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * } return "redirect:/main/main.do";
	 * 
	 * }
	 */
}

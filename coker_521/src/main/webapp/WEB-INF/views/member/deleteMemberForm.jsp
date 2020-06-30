<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath }"  />
	
<section class="resetUserPwForm-section">
	<form action="${contextPath }/member/deleteMember.do" method="POST">
		<h2 class="sec-tit">회원 탈퇴</h2>
		
		<hr class="divider divider1">
		<p>회원 탈퇴 시 계정 정보 및 레벨과 포인트가 삭제됩니다.<p>
		
		<div class="reset-div">
			<h3 class="form-tit">이메일</h3>
			<input type="text" name="userEmail" id="del_userEmail" value="${memberInfo.userEmail }" disabled />
	    
			<h3 class="form-tit">비밀번호 입력</h3>
			<input type="password" name="userPw" id="del_userPw" placeholder="Password" required="required"/>
			<input type="hidden" name="userEmail" id="del_userEmail" value="${memberInfo.userEmail }" />
			<button class="chk-btn" id="del_chkPw">비밀번호 확인</button>
		</div>
		
		<hr class="divider divider2">
		<input type="submit" class="btn" id="submit-btn" value="탈퇴하기" disabled="disabled">
	</form>
</section>
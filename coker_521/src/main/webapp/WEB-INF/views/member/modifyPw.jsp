<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<script type="text/javascript">
function pwCheck(form) {
	newPw = form.userPw.value;
	compare_newPw = form.compare_userPw.value;
	
	if(newPw != compare_newPw) {
		alert ("새로운 비밀번호가 일치하지 않습니다.");
		return false
	}
}
</script>

<section class="resetUserPwForm-section">
	<f:form action="${contextPath }/member/validModifyPw.do" method="POST" onSubmit="return pwCheck(this)" modelAttribute="memberVO">
		<h2 class="sec-tit">비밀번호 수정</h2>
		
		<hr class="divider divider1">
        
        <div class="reset-div">
     		<h3 class="form-tit">새로운 비밀번호</h3>
     		<input type="password" class="mod_userPw" id="mod_userPw" name="userPw" placeholder="8자 이상, 숫자/대문자/소문자/특수문자 포함"/><br>
     		
     		<h3 class="form-tit">비밀번호 확인</h3>
     		<input type="password" class="mod_userPw" id="mod_compare_userPw" name="compare_userPw" size="20" placeholder="New password 확인"/><br>
       		<p class="alert alert-success" id="mod_alert-success">비밀번호가 일치합니다.</p> 
			<p class="alert alert-danger" id="mod_alert-danger">* 비밀번호가 일치하지 않습니다.</p>
			<f:errors path="userPw" class="error"/>
        </div>
        
		<input type="hidden" name="userEmail" value="${memberInfo.userEmail }"/>
		<input type="hidden" name="userName" value="${memberInfo.userName }" />       	
		<input type="hidden" name="nickname" value="${memberInfo.nickname }" />       	
		<input type="hidden" name="tel" value="${memberInfo.tel }" />       	
		
		<hr class="divider divider2">
		
        <div>
			<input type="submit" class="btn" value="확인">
			<button type="button" class="btn" onclick="location.href='${contextPath }/member/myInfo.do' ">취소</button>
        </div>
	</f:form>
</section>
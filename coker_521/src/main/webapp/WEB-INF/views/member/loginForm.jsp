<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:if test='${not empty message }'>
<script>
window.onload=function()
{
  result();
}

function result(){
	alert("${message}");
}
</script>
</c:if>

<section class="login-section">
    <form action="${contextPath }/member/login.do" method="post">
    	<c:if test="${not empty prePage }">
			<input type="hidden" name="prePage" value="${prePage }">
     	</c:if>
     	<hr class="m-divider">
        <h2 class="sec-tit">계정 로그인</h2>
		<hr class="m-divider">
		<div class="content-row-1">
			<input type="text" placeholder="Email" name="userEmail" required="required">
			<input type="password" placeholder="Password"  name="userPw" required="required">
			<button type="submit">로그인</button>
		</div>
        
        <%-- <a href="${facebook_url }"><button type="button" class="btn btn-primary btn-block" style="background-color:skyblue; color:white;"> <i class="fa fa-facebook" aria-hidden="true"></i>FaceBook으로 로그인</button></a> --%>
        <div class="content-row-2">
			<span class="text-left"><a href="${contextPath}/member/memberForm.do">회원가입</a></span>
			 | <a href="${contextPath }/member/searchUserEmailForm.do" >이메일 찾기</a>
			 | <a href="${contextPath }/member/resetUserPwForm.do">비밀번호 찾기</a>
		</div>
    </form>
</section>
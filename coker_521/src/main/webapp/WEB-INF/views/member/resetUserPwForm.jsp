<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  

<c:if test="${codeCompareResult == true }">
	<script>
		$(document).ready(function(){
			$("#authEmail").css("display", "none");
		})
	</script>
</c:if>
<script type="text/javascript">
	window.onload=function(){
		function resetPwValidate(form) {
			pw1 = form.userPw.value;
			pw2 = form.comparePw.value;
	
			if (pw1 != pw2) {
			alert ("비밀번호가 일치하지 않습니다.");
			return false;
			}
			else return true;
			}	
	}
</script>
<c:if test="${not empty message }">
	<script>
		window.onload=function(){
			checkResult();
		}
		function checkResult(){
			alert("${message}");
		}
	</script>
</c:if>
<section class="resetUserPwForm-section">
	<h2 class="sec-tit">계정 비밀번호 찾기</h2>
	
	<hr class="divider divider1">
		
	<div class="content-row-1">
		<c:choose>
			<c:when test="${userEmail == null}">
				<form method="post">
					<div>
						<div>
							<h2 class="form-tit">코딩커뮤니티 계정 이메일 주소</h2>
							<input class="form-control" name="checkUserEmail" type="text" placeholder="Email" value="${userEmail }">
							<p class="info-p">* 코딩커뮤니티 계정 이메일 주소를 입력해주세요.</p>
						</div>
						<hr class="divider divider2">
						<input type="submit" class="btn" value="확인" onclick="javascript: form.action='${contextPath}/admin/member/resetPwCheckEmail.do';">
					</div>
				</form>
			</c:when>
			<c:otherwise>
				<div id="authEmail">
					<form method="post">
						<h3 class="form-tit">비밀번호 찾기</h3>
						<input name="authEmail" type="hidden" value="${userEmail }">계정 이메일 주소로 인증 (<b>${userEmail}</b>)
						<hr class="divider divider2">
						
						<input class="form-control" id="txtUserEmail" name="userEmail" type="hidden" placeholder="Email" value="${userEmail }" readonly>
						
						<div class="cert-div">
							<c:if test="${empty authCode }">
								<input class="btn" id="btn_mailAuth" type="submit" value="인증메일 발송" onclick="javascript: form.action='${contextPath}/admin/member/authEmail.do';">
							</c:if>
							<c:if test="${not empty authCode }">
								<input id="authCode" name="authCode" type="hidden" value="${authCode }">
								<input class="cert-input" id="userInsertCode" name="insertCode" type="text" placeholder="인증번호">
								<input class="btn" id="btnInsertCode" type="submit" value="확인" onclick="javascript: form.action='${contextPath}/admin/member/authCodeCompare.do';">
							</c:if>
						</div>
					</form>
				</div>
				<c:if test="${codeCompareResult == true }">
					<div id="resetPw">
					<f:form method="post" onsubmit="return resetPwValidate(this);" modelAttribute="memberVO">
						<p class="email-p"><b>${userEmail }</b></p>
						<p>계정의 새로운 비밀번호를 설정합니다.</p>

						<div class="reset-div">
							<input type="hidden" name="userEmail" value="${userEmail }">
							
							<h3 class="form-tit">새로운 비밀번호</h3>
							<f:password path="userPw" class="newPw" id="newPw" name="userPw" placeholder="새로운 비밀번호 입력" required="required" />
							
							<h3 class="form-tit">비밀번호 확인</h3>
							<input class="newPw" id="comparePw" name="comparePw" type="password" placeholder="입력 비밀번호 확인" required>
						</div>
						<p class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</p> 
						<p class="alert alert-danger" id="alert-danger">* 비밀번호가 일치하지 않습니다.</p>
						<f:errors path="userPw" class="error" />
						
						<hr class="divider divider2">
						
						<div>
							<input type="submit" class="btn" id="newPwConfirm" value="확인" onclick="javascript: form.action='${contextPath}/member/validResetPw.do';">
						</div>
					</f:form>
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</section>


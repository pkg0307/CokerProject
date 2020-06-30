<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<script type="text/javascript">
function memValidate(form) {
	pw1 = form.userPw.value;
	pw2 = form.compare_userPw.value;

	if (pw1 != pw2) {
	alert ("비밀번호가 일치하지 않습니다.");
	return false;
	}
	else return true;
	}
</script>

<section class="memberForm-section">
	<f:form onsubmit="return memValidate(this);" action="${contextPath }/member/memberInsert.do" encType="multipart/form-data" method="POST" modelAttribute="memberVO">
		<hr class="m-divider">
		<h2 class="sec-tit">회원 가입</h2>
		<hr class="m-divider">
		
		<div class="pre-info">
			모든 정보를 정확히 기입해 주세요.<br>
			계정을 잃어버렸을시 고객님의 소중한 정보를 찾는데 필요합니다.<br>
			회원 가입시 고객님의 프로필 사진은 기본이미지가 사용됩니다.<br>
			프로필 사진 수정은 '마이페이지'에서 수정할 수 있습니다.
		</div>
		
		<div class="content-row-1">
			<p>이메일</p>
			<f:input path="userEmail" name="userEmail" id="add_userEmail" placeholder="email@example.com" required="required"/>
			<button id="add_chkEmail">이메일 중복 확인</button>
			<f:errors path="userEmail" class="error-p"/>

			<p>비밀번호</p>
			<f:password path="userPw" class="add_userPw" id="add_userPw" name="userPw" placeholder="8자 이상, 숫자/대문자/소문자/특수문자 포함" required="required"/>
			<f:errors path="userPw" class="error-p"/>

			<p>비밀번호 확인</p>
			<input type="password" class="add_userPw" id="add_compare_userPw" name="compare_userPw" placeholder="Password 확인" />
			<p id="add_alert-success" class="success-p">비밀번호가 일치합니다.</p> 
			<p id="add_alert-danger" class="error-p">비밀번호가 일치하지 않습니다.</p>

			<p>닉네임</p>
			<f:input path="nickname" name="nickname" id="add_nickname" placeholder="NickName" required="required"/>
			<button id="add_chkNick">닉네임 중복 확인</button>
			<f:errors path="nickname" class="error-p"/>

			<p>이름</p>
			<f:input path="userName" name="userName" placeholder="Name" required="required"/>
			<f:errors path="userName" class="error-p"/>

			<p>휴대폰</p>
			<f:input path="tel" name="tel" placeholder="' - ' 제외하고 입력" required="required"/>
			<f:errors path="tel" class="error-p"/>
		</div>
		
		<div class="content-row-2">
			<input type="submit" class="btn" id="add_member" value="회원 가입">
			<input type="reset" class="btn" value="다시입력">
		</div>
	</f:form>
</section>
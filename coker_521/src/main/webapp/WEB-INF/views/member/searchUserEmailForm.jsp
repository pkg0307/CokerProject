<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  

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

<section class="searchUserEmailForm-section">
	<h2 class="sec-tit">코딩커뮤니티 계정찾기</h2>
	<h2>[본인 이름/전화번호로 찾기]</h2>
	
	<hr class="divider divider1">
	
	<div class="content-row-1">
		<c:choose>
			<c:when test="${userEmailList.isEmpty() || userEmailList == null }">
				<form method="post" class="searchUserEmail">
					<div>
						<h3 class="form-tit">본인 이름</h3>
						<input class="form-control" name="userName" type="text" placeholder="본인 이름">
						<p class="info-p">* 코딩커뮤니티 가입시 입력한 본인 이름을 입력해주세요.</p>
					</div>
					<div>
						<h3 class="form-tit">전화번호</h3>
						<input class="form-control" name="tel" type="text" placeholder="휴대전화 번호">
						<p class="info-p">* 코딩커뮤니티 가입시 입력한 휴대전화 번호를 입력해주세요.</p>
					</div>
					<hr class="divider divider2">
					<div>
						<input class="btn" type="submit" value="코딩커뮤니티 계정찾기" onclick="javascript: form.action='${contextPath}/member/searchUserEmail.do';">
					</div>
				</form>
			</c:when>
			<c:otherwise>
				<div>
					<p>입력하신 정보와 일치하는 계정입니다.</p>
					<br>
					<table>
						<c:forEach var="userEmail" items="${userEmailList }" >
							<tr>
								<td><b>◎ ${userEmail }</b></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<hr class="divider divider2">
				<div>
					<button type="button" onClick="location.href='${contextPath }/member/loginForm.do'">로그인</button>
					<button type="button" onClick="location.href='${contextPath}/member/resetUserPwForm.do'">비밀번호 재설정</button>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</section>




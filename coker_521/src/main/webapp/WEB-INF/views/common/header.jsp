<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<script type="text/javascript"
	src="${contextPath }/resources/js/header.js"></script>
<c:if test='${not empty message }'>
	<script>
		window.onload = function() {
			result();
		}

		function result() {
			alert("계정 정보를 확인해 주세요.");
		}
	</script>
</c:if>

<h1 class="logo"><a href="${contextPath}/main/main.do">COKER</a></h1>
<nav>
	<ul class="gnb">
		<li><a href="${contextPath }/admin/board/rankedMember.do">회원순위</a></li>
		<li><a href="${contextPath }/board/viewBoard.do?category=notice">공지사항</a></li>
		<li><a href="${contextPath }/member/csCenterMain.do">고객센터</a></li>
		<c:if test="${isLogOn == true and memberInfo.nickname == 'admin' }">
			<li><a href="${contextPath }/admin/member/listMembers.do">회원 관리</a></li>
			<li><a href="${contextPath }/admin/board/reportedBoardList.do">신고글 관리</a></li>
		</c:if>
	<c:choose>
		<c:when test="${isLogOn==true and not empty memberInfo }">
			<li><a href="${contextPath }/member/myPageMain.do">마이페이지</a></li>
			<li><a href="${contextPath }/member/logout.do">로그아웃</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${contextPath }/member/loginForm.do">로그인</a></li>
			<li><a href="${contextPath}/member/memberForm.do">회원가입	</a></li>
		</c:otherwise>
	</c:choose>
	</ul>
</nav>
<span class="menu-toggle-btn">
	<span></span>
	<span></span>
	<span></span>
</span>
				
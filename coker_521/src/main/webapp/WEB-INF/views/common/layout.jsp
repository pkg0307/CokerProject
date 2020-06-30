<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="article" value="${articleMap.article }"></c:set>
<%
 	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title><tiles:insertAttribute name="title" /></title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
	<!-- 이미지 link 예시 -- <link rel="icon" href="${contextPath }/resources/image/icon/newbie.jpeg"> -->
	
	<!-- JS 및 CSS -->
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="${contextPath }/resources/js/summernote/summernote-lite.js"></script>
	<script src="${contextPath }/resources/js/summernote/lang/summernote-ko-KR.js"></script>
	<script>var contextPath = "${pageContext.request.contextPath}";</script>
	<script type="text/javascript" src="${contextPath }/resources/js/coker.min.js"></script>
	
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.13.0/css/all.css">
	<link rel="stylesheet" href="${contextPath }/resources/css/summernote/summernote-lite.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/reset.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/default.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/board.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/member.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/admin.css">
</head>
<body>
	<div class="wrap">
		<header class="header cfixed">
			<div class="container">
				<tiles:insertAttribute name="header" />
			</div>
		</header>
		<section class="content">
			<div class="container content-container">
				<tiles:insertAttribute name="body" />
			</div>
		</section>
		<footer class="footer">
			<div class="container">
				<tiles:insertAttribute name="footer" />
			</div>
		</footer>
	</div>
</body>
</html>
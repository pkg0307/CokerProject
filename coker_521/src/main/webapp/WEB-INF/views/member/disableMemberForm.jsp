<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<section class="disableMemberForm-section">
	<i style="color:red;" class="fas fa-exclamation-circle fa-5x"></i>
	<h2 class="sec-tit">회원님의 계정은 코딩 커뮤니티 이용이 제한되었습니다.</h2>
	
	<p>
		코딩 커뮤니티 이용약관 및 운영정책에 따라<br>
		아래 기간동안 코딩 커뮤니티 이용이 불가능 합니다.
	</p>
	
	<div class="table-div">
		<table>
			<colgroup>
				<col width="25%">
				<col width="75%">
			</colgroup>
			<tr>
				<td>계정명</td>
				<td>${disableMemberInfo.userEmail }</td>
			</tr>
			<tr>
				<td>제한날짜</td>
				<td>${disableMemberInfo.disable_date }</td>
			</tr>
			<tr>
				<td>해제날짜</td>
				<c:choose>
					<c:when test="${disableMemberInfo.enable_date == null }">
						<td>무기한<br>(※궁금하신 사항은 관리자에게 문의해주세요.)</td>
					</c:when>
					<c:otherwise>
						<td>${disableMemberInfo.enable_date }</td>	
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td>제한사유</td>
				<td>${disableMemberInfo.disable_reason }</td>
			</tr>
			<tr>
				<td>제한내용</td>
				<td>코딩커뮤니티 게시판 사용 및 기능 사용 차단</td>
			</tr>
		</table>
	</div>
</section>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "contextPath" value="${pageContext.request.contextPath }"></c:set>
<c:set var = "category" value="${searchRankedMemberInfo.searchCategory }"></c:set>
<c:set var = "word" value="${searchRankedMemberInfo.searchWord }"></c:set>
<% 
	request.setCharacterEncoding("utf-8");
%>

<section class="adminBoard-section">
	<h2 class="sec-tit">코딩커뮤니티 회원 순위보기</h2>
	
	<div class="search-div"> 
		<form class="form-box" name="frmSearch" method="post" action="${contextPath }/admin/board/rankedMember.do" id="searchFrm">
			<select name="searchCategory" id="searshing1">
				<option value="userEmail">이메일</option>
				<option value="nickname">닉네임</option>
			</select>
		
			<input type="text" name="searchWord">
			<input type="submit" class="btn" value="검색">
		</form>
	</div>
	
	<div class="table-div">
		<c:choose>
			<c:when test="${rankedMemberList.isEmpty() }">
				<div>
					<h1>회원 검색 결과가 없습니다.</h1>
				</div>
			</c:when>
			<c:otherwise>
				<table>
					<colgroup>
						<col width="10%">
						<col width="35%">
						<col width="25%">
						<col width="10%">
						<col width="20%">
					</colgroup>
					<thead>
						<tr>
							<th>순위</th>
							<th>이메일</th>
							<th>닉네임</th>
							<th>레벨</th>
							<th>포인트</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="rankedMember" items="${rankedMemberList}" begin="0" end="9">
						<tr>
							<td>${rankedMember.rank}</td>
							<td>${rankedMember.userEmail}</td>
							<td>${rankedMember.nickname}</td>
							<td>${rankedMember.lvl}</td>
							<td>${rankedMember.totalPoint}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</section>

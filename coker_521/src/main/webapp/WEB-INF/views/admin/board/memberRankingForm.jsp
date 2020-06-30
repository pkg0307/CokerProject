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
	<h2 class="sec-tit">�ڵ�Ŀ�´�Ƽ ȸ�� ��������</h2>
	
	<div class="search-div"> 
		<form class="form-box" name="frmSearch" method="post" action="${contextPath }/admin/board/rankedMember.do" id="searchFrm">
			<select name="searchCategory" id="searshing1">
				<option value="userEmail">�̸���</option>
				<option value="nickname">�г���</option>
			</select>
		
			<input type="text" name="searchWord">
			<input type="submit" class="btn" value="�˻�">
		</form>
	</div>
	
	<div class="table-div">
		<c:choose>
			<c:when test="${rankedMemberList.isEmpty() }">
				<div>
					<h1>ȸ�� �˻� ����� �����ϴ�.</h1>
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
							<th>����</th>
							<th>�̸���</th>
							<th>�г���</th>
							<th>����</th>
							<th>����Ʈ</th>
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

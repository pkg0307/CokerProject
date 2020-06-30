<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="num" value="${pageMaker.cri.page*pageMaker.cri.perPageNum - pageMaker.cri.perPageNum + 1}"></c:set>
<% 
   request.setCharacterEncoding("utf-8");
%>

<section class="adminBoard-section fixed-section">
	<h2 class="sec-tit">회원관리</h2>

	<div class="search-div">
		<form class="form-box" name="frmSearch" action="${contextPath }/admin/member/listMembers.do" id="searchFrm">
			<select name="searchCategory" id="searshing1">
				<option value="userEmail">이메일</option>
				<option value="userName">이름</option>
				<option value="nickname">닉네임</option>
			</select>
			
			<input type="text" name="searchWord">
			<input type="submit" class="btn" value="검색">
		</form>
	</div>
	
	<div class="table-div">
		<c:choose>
			<c:when test="${memberList.isEmpty() }">
				<div>
					<h1>회원 검색 결과가 없습니다.</h1>
				</div>
			</c:when>
			<c:otherwise>
				<table class="resized-table">
					<colgroup>
						<col width="5%">
						<col width="15%">
						<col width="13%">
						<col width="10%">
						<col width="13%">
						<col width="5%">
						<col width="5%">
						<col width="10%">
						<col width="7%">
						<col width="10%">
						<col width="7%">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>이메일</th>
							<th>닉네임</th>
							<th>이름</th>
							<th>전화번호</th>
							<th>레벨</th>
							<th>포인트</th>
							<th>총 포인트</th>
							<th>계정상태</th>
							<th>차단해제일</th>
							<th>상세보기</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="member" items="${memberList }">
							<c:choose>
								<c:when test="${member.enable == 0 and member.enable_date == null}">
									<tr class="eternalBan-tr">
								</c:when>
								<c:when test="${member.enable == 0 and member.enable_date != null}">
									<tr class="tempBan-tr">
								</c:when>
								<c:otherwise>
									<tr>
								</c:otherwise>
							</c:choose>
								<td>${num }</td>
								<c:set var="num" value="${num+1 }"></c:set>
								<td>${member.userEmail }</td>
								<td>${member.nickname }</td>
								<td>${member.userName }</td>
								<td>${member.tel }</td>
								<td>${member.lvl }</td>
								<td>${member.point}</td>
								<td>${member.totalPoint }</td>
								<c:choose>
									<c:when test="${member.enable == 1 }">
										<td>사용</td>
									</c:when>
									<c:otherwise>
										<td>차단</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${member.enable == 0 and member.enable_date == null}">
										<td>영구정지</td>
									</c:when>
									<c:when test="${member.enable == 0 and member.enable_date != null}">
										<td>${member.enable_date }</td>
									</c:when>
									<c:otherwise>
										<td></td>
									</c:otherwise>
								</c:choose>
								
								<td>
									<a href="${contextPath }/admin/member/memberDetail.do?userEmail=${member.userEmail}">
										&lt;보기&gt;
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="pageNum-div">
		<!-- PageMaker 2020.03.31 -->
		<!-- http://localhost:8090/project01/board/viewBoard.do?category=java_share&page=1&perPageNum=10 -->
		<ul class="pagination">
			<c:if test="${pageMaker.prev}">
				<li style="display: inline-block">
					<a href="listMembers.do${pageMaker.makeMaker(pageMaker.startPage - 1)}">이전</a>
				</li>
			</c:if>
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
				<li style="display: inline-block"<c:out value="${pageMaker.cri.page == idx ? 'class=info' : ''}" />>
					<a href="listMembers.do${pageMaker.makeMaker(idx)}">${idx}</a>
				</li>
			</c:forEach>
			<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
				<li style="display: inline-block">
					<a href="listMembers.do${pageMaker.makeMaker(pageMaker.endPage + 1)}">다음</a>
				</li>
			</c:if>
		</ul>
	</div>
</section>
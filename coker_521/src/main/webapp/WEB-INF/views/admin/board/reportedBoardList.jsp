<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "contextPath" value="${pageContext.request.contextPath }"></c:set>
<c:set var="num" value="${pageMaker.totalCount - pageMaker.cri.page*pageMaker.cri.perPageNum + pageMaker.cri.perPageNum }"></c:set>
<% 
	request.setCharacterEncoding("utf-8");
%>

<section class="adminBoard-section">
	<h2 class="sec-tit">신고된 게시글 관리</h2>

	<div class="search-div">
		<div class="form-box">
			<select name="searchType" id="searchType">
				<option value="title">제목</option>
				<option value="content">본문</option>
				<option value="writer">작성자</option>
			</select>
	
			<input type="text" name="keyword" id="keyword">
			<input type="submit" id="report_btnSearch" class="btn" value="검색">
		</div>
	</div>

	<div class="table-div">
		<table>
			<colgroup>
				<col width="10%">
				<col width="30%">
				<col width="15%">
				<col width="17%">
				<col width="13%">
			</colgroup>
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>신고횟수</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${articlesList == null }">
						<tr align="center">
							<td colspan="4">등록된 글이 없습니다</td>
						</tr>
					</c:when>
					<c:when test="${articlesList != null }">
						<c:forEach var="article" items="${articlesList }" varStatus="articleNum">
							<tr>
								<td>${num }</td>
								<c:set var="num" value="${num-1 }"></c:set>
								<td class="left-td">
									<a href="${contextPath }/board/viewArticle.do?category=${article.tableName}&boardNO=${article.boardNO}&isAdmin=1">
										${article.title }
									</a>
								</td>
								<td>${article.writer }</td>
								<td>${article.regDate }</td>
								<td>${article.reports }</td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
	</div>
		
	<div class="pageNum-div">
		<!-- PageMaker 2020.03.31 -->
		<!-- http://localhost:8090/project01/board/viewBoard.do?category=java_share&page=1&perPageNum=10 -->
		<ul class="pagination">
			<c:if test="${pageMaker.prev}">
				<li style="display: inline-block">
					<a href="reportedBoardList.do${pageMaker.makeMaker(pageMaker.startPage - 1)}">이전</a>
				</li>
			</c:if>
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
				<li style="display: inline-block"<c:out value="${pageMaker.cri.page == idx ? 'class=info' : ''}" />>
					<a href="reportedBoardList.do${pageMaker.makeMaker(idx)}">${idx}</a>
				</li>
			</c:forEach>
			<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
				<li style="display: inline-block">
					<a href="reportedBoardList.do${pageMaker.makeMaker(pageMaker.endPage + 1)}">다음</a>
				</li>
			</c:if>
		</ul>
	</div>
</section>
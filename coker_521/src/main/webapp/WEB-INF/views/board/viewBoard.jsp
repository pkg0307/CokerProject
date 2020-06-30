<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<c:set var="num" value="${pageMaker.totalCount - pageMaker.cri.page*pageMaker.cri.perPageNum + pageMaker.cri.perPageNum }"></c:set>
<%
	request.setCharacterEncoding("utf-8");
%>

<script>
	$(document).on('click', '#btnSearch', function(e){
		e.preventDefault();
		
		var url = "/Project01/board/viewBoard.do";
		url = url+"?category=${boardName }";
		url = url+"&searchType="+$('#searchType').val();
		url = url+"&keyword="+$('#keyword').val();
		location.href=url;
	})
	function fn_articleForm(isLogOn, boardName, nickname, articleForm,
			loginForm) {
		if (isLogOn != '' && isLogOn != 'false') {
			if (boardName == 'notice' && nickname != 'admin') {
				alert("관리자만 글쓰기가 가능합니다.");
			} else {
				location.href = articleForm;
			}
		} else {
			alert("로그인이 필요합니다");
			location.href = loginForm;
		}
	};
</script>

<section class="board-section">
	<h2 class="sec-tit">${title }</h2>
	<div class="content-row-1">
		<table class="pc-table">
			<colgroup>
	    		<col width="8%"/>
	    		<col width="60%"/>
	    		<col width="16%"/>
	    		<col width="16%"/>
	  		</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>닉네임</th>
					<th>날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${bestArticlesList.size() != 0 }">
					<c:forEach var="article" items="${bestArticlesList }">
						<tr class="best-tr">
							<td><span class="best-span">인기</span></td>
							<td>
								<a href="${contextPath}/board/viewArticle.do?category=${boardName }&boardNO=${article.boardNO}">
							 		<span class="likes-span"><i class="fas fa-thumbs-up"></i>${article.likes } </span>
							 		${article.title }
							 	</a>
							 </td>
							<td>${article.writer }</td>
							<td>${article.regDate }</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:choose>
					<c:when test="${articlesList.size() == 0 || articlesList == null}">
						<tr>
							<td colspan="4">등록된 글이 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="article" items="${articlesList }">
							<tr>
								<td>${num }</td>
								<c:set var="num" value="${num-1 }"></c:set>
								<td>
									<a href="${contextPath }/board/viewArticle.do?category=${boardName}&boardNO=${article.boardNO}">
										<c:if test="${article.likes  != 0}">
							 				<span class="likes-span"><i class="fas fa-thumbs-up"></i>${article.likes } </span>
							 			</c:if>
							 			<c:if test="${article.progress == 1 }">
								 			<span class="progress-span">완료 </span>
							 			</c:if>
							 			<span class="topic-span">${article.topic }</span> ${article.title } 
									</a>
								</td>
								<td>${article.writer }</td>
								<td>${article.regDate }</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<table class="mobile-table">
			<colgroup>
	    		<col width="60%"/>
	    		<col width="10%"/>
	    		<col width="30%"/>
	  		</colgroup>
			<tbody>
				<c:if test="${bestArticlesList.size() != 0 }">
					<c:forEach var="article" items="${bestArticlesList }">
						<tr class="best-tr">
							<td colspan="3"><a href="${contextPath}/board/viewArticle.do?category=${boardName }&boardNO=${article.boardNO}">&nbsp;<span class="best-span">인기</span> ${article.title }</a></td>
						</tr>
						<tr class="best-tr">
							<td>&nbsp;<i class="fas fa-user"></i> ${article.writer }</td>
							<td><i class="fas fa-thumbs-up"></i> ${article.likes }</td>
							<td><i class="fas fa-calendar-alt"></i> ${article.regDate }</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:choose>
					<c:when test="${articlesList.size() == 0 || articlesList == null}">
						<tr>
							<td colspan="4">등록된 글이 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="article" items="${articlesList }">
							<tr>
								<td colspan="3">
									<a href="${contextPath }/board/viewArticle.do?category=${boardName}&boardNO=${article.boardNO}">
										<c:if test="${article.progress == 1 }">
								 			<span class="progress-span">완료 </span>
							 			</c:if>
							 			<span class="topic-span">${article.topic }</span> ${article.title }
									</a>
								</td>
							</tr>
							<tr>
								<td>&nbsp;<i class="fas fa-user"></i> ${article.writer }</td>
								<td>
									<c:if test="${fn:contains(boardName, 'share')}">
										<i class="fas fa-thumbs-up"></i> ${article.likes }
									</c:if>
								</td>
								<td><i class="fas fa-calendar-alt"></i> ${article.regDate }</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="content-row-2">
		<div class="search-div">
			<select name="searchType">
				<option value="title">제목</option>
				<option value="content">본문</option>
				<option value="writer">작성자</option>
			</select>
			<input type="text" name="keyword" placeholder=" 검색할 내용">
			<input type="submit" class="btn" value="검색">
		</div>
		<div>
			<a id="addBtn" href="javascript:fn_articleForm('${isLogOn}','${boardName }','${memberInfo.nickname }','${contextPath }/board/articleForm.do?category=${boardName }','${contextPath }/member/loginForm.do')">
				<button>글쓰기</button>
			</a>
		</div>
	</div>
	<!-- PageMaker 2020.03.31 -->
	<!-- http://localhost:8090/project01/board/viewBoard.do?category=java_share&page=1&perPageNum=10 -->
	<div class="content-row-3">
		<ul>
			<c:if test="${pageMaker.prev}">
				<li style="display:inline-block"><a href="viewBoard.do${pageMaker.makeMaker(pageMaker.startPage - 1)}">이전</a></li>
			</c:if>
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
				<li style="display:inline-block" <c:out value="${pageMaker.cri.page == idx ? 'class=info' : ''}" />><a href="viewBoard.do${pageMaker.makeMaker(idx)}">${idx}</a></li>
			</c:forEach>
			<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
				<li style="display:inline-block"><a href="viewBoard.do${pageMaker.makeMaker(pageMaker.endPage + 1)}">다음</a></li>
			</c:if>
		</ul>
	</div>
</section>
		
		
		
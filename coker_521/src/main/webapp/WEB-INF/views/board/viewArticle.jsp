<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@	taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<c:set var="article" value="${articleMap.article }"></c:set>
<c:set var="content" value="${article.content }"></c:set>
<%
	request.setCharacterEncoding("utf-8");
%>

<script>
$(document).ready(function(){
	$("#boardDeleteCtr").click(function(){
		  if(confirm("정말로 해당 글을 삭제하시겠습니까?")){
			location.href='${contextPath}/board/removeArticle.do?category=${category}&boardNO=${article.boardNO}';
		  }
	 });
	
	$("#recommend-btn").click(function(){
		if(confirm("해당 글을 추천하시겠습니까?")){
			if('${memberInfo.nickname}' == '${article.writer}'){
				alert('본인이 게시한 글은 추천할 수 없습니다');
			} else {
				var formObj = $(this).parent();
				formObj.attr("method", "post");
				formObj.attr("action", "${contextPath}/board/recommend.do");
				formObj.submit();  
			}
		}
	});
});
</script>

<section class="article-section">
	<div class="content-row-1">
		<table class="pc-table">
			<colgroup>
	    		<col width="63%"/>
	    		<col width="15%"/>
	    		<col width="22%"/>
	  		</colgroup>
			<thead>
				<tr>
					<th><span class="title-span">제목 | </span>${article.title }</th>
					<td><b>작성자 |</b> ${article.writer }</td>
					<td><b>작성일 |</b> <fmt:formatDate pattern="yy.MM.dd HH:mm" value="${article.regDate }"/></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="3">${content }</td>
				</tr>
			</tbody>
		</table>
		<table class="mobile-table">
			<colgroup>
	    		<col width="50%"/>
	    		<col width="50%"/>
	  		</colgroup>
			<thead>
				<tr>
					<th colspan="2"><span class="title-span">제목 | </span>${article.title }</th>
				</tr>
				<tr>
					<td><b>작성자</b> : ${article.writer }</td>
					<td><b>작성일</b> : <fmt:formatDate pattern="yy.MM.dd HH:mm" value="${article.regDate }"/></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="2">${content }</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="content-row-2">
		<form>
			<input type="hidden" name="nickname" value="${article.writer }">
			<input type="hidden" name="boardNO" value="${article.boardNO}">
			<input type="hidden" name="category" value="${category}">
			<c:if test="${fn:contains(category, 'share')}">
				<button type="button" id="recommend-btn"><i class="fas fa-thumbs-up"></i>! ${article.likes }</button>
			</c:if>
		</form>	
	</div>
	<hr class="divider">
	<div class="content-row-3">
		<!-- 글 작성자일 때 수정/삭제/답변채택/구인구직완료하기-->
		<c:if test="${memberInfo.nickname == article.writer }">
			<c:if test="${article.progress != 1 }">
				<input type="button" value="수정" class="btn" onclick="location.href='${contextPath}/board/modForm.do?category=${category}&boardNO=${article.boardNO}'">	
			</c:if>
			<input type="button" value="삭제" class="btn" id="boardDeleteCtr">
			<c:if test="${article.progress == 0 && category == 'qna'}">
				<input type="button" value="채택" class="btn" id="showSetFinalReplyBtn">
			</c:if>
			<c:if test="${article.progress == 0 && (category == 'recruit' || category == 'search')}">
				<form class="viewArticle_form" method="post" action="${contextPath }/board/setFinalBoard.do">
					<input type="hidden" name="boardNO" value="${article.boardNO }">
					<input type="hidden" name="category" value="${category}">
					<input type="button" class="btn setFinalBoardBtn" value="구인/구직 완료">
				</form>
			</c:if>
		</c:if>
		<!-- 관리자일 때 삭제하기 -->
		<c:if test = "${memberInfo.nickname == 'admin' }">
			<input type="button" value="관리자 권한 삭제" class="btn" onclick="location.href='${contextPath}/board/removeArticle.do?category=${category}&boardNO=${article.boardNO}&isAdmin=${isAdmin }'">
		</c:if>
		<!-- 신고하기 -->
		<c:if test="${category != 'recruit' && category != 'search' && category != 'notice'}">
			<form class="viewArticle_form" method="post" action="${contextPath }/board/reportBoard.do">
				<input type="hidden" name="category" value="${category }">
				<input type="hidden" name="boardno" value="${article.boardNO }">
				<input type="button" value="신고하기" class="btn" id="reportBtn1">
			</form>
		</c:if>
		<!-- 목록보기 -->
		<input type="button" value="목록보기" class="btn" onclick="location.href='${contextPath}/board/viewBoard.do?category=${category}'">
	</div>
	<!-- ================================================================================== -->
	<!-- ======================================위는 테이블===================================== -->
	<!-- ======================================아래는 댓글===================================== -->
	<!-- ================================================================================== -->
	<div class="content-row-4">
		<table>
			<colgroup>
				<col width="80px">
				<col width="100%">
			</colgroup>
			<thead>
				<tr>
					<td colspan="2">&emsp;댓글 ${repliesList.size()}</td>
				</tr>
			</thead>
			<c:choose>
				<c:when test="${repliesList.size() == 0 }">
					<tr class="nothing-tr">
						<td colspan="2">첫 댓글의 주인공이 되어주세요!</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tbody>
						<c:forEach var="reply" items="${repliesList }">
							<tr class="title-tr">
								<td rowspan="3">
									<img src="${contextPath }/download.do?nickname=${reply.writer}&imgName=${reply.imgName}" id="profileImg">
								</td>
								<td><b>${reply.writer}</b> <span class="date-span"><fmt:formatDate pattern="yy.MM.dd HH:mm" value="${reply.regDate }"/></span></td>
							</tr>
							<tr class="content-tr">
								<td>
									<c:choose>
										<c:when test="${reply.reports >= 5 }">
											<span style="color:red;">신고된 댓글로 블라인드 처리되었습니다. (누적 5회)</span>
										</c:when>
										<c:otherwise>
											<c:if test="${reply.progress == 1 }">
												<i style="color:green; float: right;" class="far fa-check-square fa-3x"></i>
											</c:if>
											<pre class="article-pre">${reply.content}</pre>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="function-tr">
								<td>
									<c:if  test="${memberInfo.nickname == reply.writer && reply.reports < 5 }">
										<button class="replyModifyOnOff">수정하기</button>
										<form class="viewArticle_form">
											<input type="hidden" name="boardNO" value="${article.boardNO}">
											<input type="hidden" name="category" value="${category}">
											<input type="hidden" name="replyNO" value="${reply.replyNO}">
											<input type="button" class="btn" id="replyDeleteCtr" value="삭제하기">
										</form>
									</c:if>
									<c:if test="${article.writer != reply.writer }">
										<form>
											<input type="hidden" name="boardNO" value="${article.boardNO }">
											<input type="hidden" name="category" value="${category}">
											<input type="hidden" name="replyNO" value="${reply.replyNO }">
											<input type="hidden" name="replyNickname" value="${reply.writer }">
											<input type="hidden" name="boardNickname" value="${article.writer }">
											<input type="button" class="btn setFinalReplyBtn" style="display: none;" value="채택하기">
										</form>
									</c:if>
									<form class="viewArticle_form" method="post" action="${contextPath }/board/reportReply.do">
										<input type="hidden" name="category" value="${category }">
										<input type="hidden" name="replyno" value="${reply.replyNO }">
										<input type="hidden" name="boardno" value="${article.boardNO }">
										<input type="button" value="신고하기 " class="btn reportBtn2">
									</form>
									<form style="display:none;" class="replyModifyForm">
										<input type="hidden" name="boardNO" value="${article.boardNO}">
										<input type="hidden" name="category" value="${category}">
										<input type="hidden" name="replyNO" value="${reply.replyNO}">
										<textarea name="content" rows="4">${reply.content }</textarea>
										<input type="button" class="btn replyModifyCtr" value="수정반영">
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	<div class="content-row-5">
		<form name="replyForm" method="post" action="${contextPath}/board/addReply.do">
			<input type="hidden" name="boardNO" value="${article.boardNO}">
			<input type="hidden" name="category" value="${category}">
			<input type="hidden" name="writer" value="${memberInfo.nickname }">
			<textarea name="content" id="replyContent" rows="6"></textarea>
			<input type="button" class="btn" id="replyBtn" value="댓글작성">
		</form>
	</div>	
</section>
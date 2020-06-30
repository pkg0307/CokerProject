<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%	request.setCharacterEncoding("utf-8"); %>

<section class="alter-article-section">
	<form name="articleForm" method="post" action="${contextPath }/board/addArticle.do">
		<input type="hidden" name="category" value="${category }">
		<div class="content-row-1">
			<c:if test="${fn:contains(category, 'freetalk')}">
				<select name="topic" class="form-control">
					<option value="">잡담</option>
					<option value="<span class='classification'>유머</span>">유머</option>
					<option value="<span class='classification'>견적</span>">견적</option>
				</select>
			</c:if>
			<input type="text" name="title" placeholder="제목을 입력해주세요.">
		</div>
		<div class="content-row-2">
			<textarea name="content" id="summernote"></textarea>
		</div>
		<div class="content-row-3">
			<input type="submit" value="글쓰기" id="article_btn">
			<input type="button" value="목록보기" onclick="location.href='${contextPath}/board/viewBoard.do?category=${category}'">
		</div>
	</form>
	<script>
		$('#summernote').summernote({
	    	height: 300,
	        minHeight: null,
	        maxHeight: null,
			focus: true,
	        lang: 'ko-KR'
	    });
	</script>
</section>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "contextPath" value="${pageContext.request.contextPath }"></c:set>
<c:set var="num" value="${pageMaker.totalCount - pageMaker.cri.page*pageMaker.cri.perPageNum + pageMaker.cri.perPageNum }"></c:set>
<% 
	request.setCharacterEncoding("utf-8");
%>

<section class="adminBoard-section">
	<h2 class="sec-tit">�Ű�� �Խñ� ����</h2>

	<div class="search-div">
		<div class="form-box">
			<select name="searchType" id="searchType">
				<option value="title">����</option>
				<option value="content">����</option>
				<option value="writer">�ۼ���</option>
			</select>
	
			<input type="text" name="keyword" id="keyword">
			<input type="submit" id="report_btnSearch" class="btn" value="�˻�">
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
					<th>�۹�ȣ</th>
					<th>����</th>
					<th>�ۼ���</th>
					<th>�ۼ���</th>
					<th>�Ű�Ƚ��</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${articlesList == null }">
						<tr align="center">
							<td colspan="4">��ϵ� ���� �����ϴ�</td>
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
					<a href="reportedBoardList.do${pageMaker.makeMaker(pageMaker.startPage - 1)}">����</a>
				</li>
			</c:if>
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
				<li style="display: inline-block"<c:out value="${pageMaker.cri.page == idx ? 'class=info' : ''}" />>
					<a href="reportedBoardList.do${pageMaker.makeMaker(idx)}">${idx}</a>
				</li>
			</c:forEach>
			<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
				<li style="display: inline-block">
					<a href="reportedBoardList.do${pageMaker.makeMaker(pageMaker.endPage + 1)}">����</a>
				</li>
			</c:if>
		</ul>
	</div>
</section>
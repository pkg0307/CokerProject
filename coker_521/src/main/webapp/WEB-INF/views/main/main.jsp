<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="javaList" value="${articlesMap.javaList }" />
<c:set var="cList" value="${articlesMap.cList }" />
<c:set var="cplusList" value="${articlesMap.cplusList }" />
<c:set var="pythonList" value="${articlesMap.pythonList }" />
<c:set var="noticeList" value="${articlesMap.noticeList }" />
<c:set var="freetalkList" value="${articlesMap.freetalkList }" />
<c:set var="recruitList" value="${articlesMap.recruitList }" />
<c:set var="searchList" value="${articlesMap.searchList }" />
<c:set var="qnaList" value="${articlesMap.qnaList }" />
<%
	request.setCharacterEncoding("UTF-8");
%>

<section class="main-section">
	<div class="cfixed">
		<table> <!-- 자바 게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=java_share">JAVA</a>
					</th>
					<th>
						<a class="more" href="${contextPath }/board/viewBoard.do?category=java_share">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${javaList }">
					<tr>
						<td>
							<a href="${contextPath}/board/viewArticle.do?category=java_share&boardNO=${article.boardNO}">${article.title }</a>
						</td>
						<td>${article.regDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table> <!-- 파이썬 게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=python_share">PYTHON</a>
					</th>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=python_share">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${pythonList }">
					<tr>
						<td>
							<a href="${contextPath}/board/viewArticle.do?category=python_share&boardNO=${article.boardNO}">${article.title }</a>
						</td>
						<td>${article.regDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table> <!-- C언어 게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=c_share">C</a>
					</th>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=c_share">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${cList }">
					<tr>
						<td>
							<a href="${contextPath}/board/viewArticle.do?category=c_share&boardNO=${article.boardNO}">${article.title }</a>
						</td>
						<td>${article.regDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table> <!-- C++언어 게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=cplus_share">C++</a>
					</th>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=cplus_share">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${cplusList }">
					<tr>
						<td>
							<a href="${contextPath}/board/viewArticle.do?category=cplus_share&boardNO=${article.boardNO}">${article.title }</a>
						</td>
						<td>${article.regDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<hr class="m-divider">
	<div class="cfixed">
		<table> <!-- QnA 게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=qna">QnA</a>
					</th>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=qna">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${qnaList }">
					<tr>
						<td>
							<a href="${contextPath}/board/viewArticle.do?category=qna&boardNO=${article.boardNO}">
								<c:if test="${article.progress == 1 }">
									<span class="progress-span">완료 </span>
								</c:if>
								${article.title }
							</a>
						</td>
						<td>${article.regDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table> <!-- 자유게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=freetalk">자유</a>
					</th>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=freetalk">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${freetalkList }">
					<tr>
						<td>
							<a href="${contextPath}/board/viewArticle.do?category=freetalk&boardNO=${article.boardNO}"><span class="topic-span">${article.topic }</span> ${article.title }</a>
						</td>
						<td>${article.regDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table> <!-- 구인 게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=recruit">구인</a>
					</th>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=recruit">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${recruitList }">
				<tr>
					<td>
						<a href="${contextPath}/board/viewArticle.do?category=recruit&boardNO=${article.boardNO}">
							<c:if test="${article.progress == 1 }">
								<span class="progress-span">완료 </span>
							</c:if>
							${article.title }
						</a>
					</td>
					<td>${article.regDate }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<table> <!-- 구직 게시판 -->
			<thead>
				<tr>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=search">구직</a>
					</th>
					<th>
						<a href="${contextPath }/board/viewBoard.do?category=search">더보기</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${searchList }">
				<tr>
					<td>
						<a href="${contextPath}/board/viewArticle.do?category=search&boardNO=${article.boardNO}">
							<c:if test="${article.progress == 1 }">
								<span class="progress-span">완료 </span>
							</c:if>
							${article.title }
						</a>
					</td>
					<td>${article.regDate }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>




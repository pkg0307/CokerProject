<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<section class="myPageMain-section">
    <form action="${contextPath }/member/login.do" method="post">
        <hr class="m-divider">
        <h2 class="sec-tit">마이페이지</h2> 
        <hr class="m-divider">
        
        <div class="content-row-1">
	        <table>
	        	<colgroup>
	        		<col width="25%">
	        		<col width="75%">
	        	</colgroup>
	        	<tr>
	        		<th>프로필사진</th>
	        		<td>
	        			<img class="profile-img" src="${contextPath }/download.do?nickname=${memberInfo.nickname}&imgName=${memberInfo.imgName}">
	        		</td>
	        	</tr>
	        	<tr>
	        		<th>이메일</th>
	        		<td>
	        			<input type="text" name="userEmail" value="${memberInfo.userEmail }" readOnly="readOnly"/>
	        		</td>
	        	</tr>
	        	<tr>
	        		<th>닉네임</th>
	        		<td>
	        			<input type="text" name="nickname" value="${memberInfo.nickname }" readOnly="readOnly"/>		
	        		</td>
	        	</tr>
	        	<tr>
	        		<th>레벨</th>
	        		<td>
	        			<input type="text" name="lvl" value="${memberInfo.lvl }" readOnly="readOnly"/>
	        		</td>
	        	</tr>
	        	<tr>
	        		<th>포인트</th>
	        		<td>
	        			<input type="text" name="point" value="${memberInfo.point }" readOnly="readOnly"/>
	        		</td>
	        	</tr>
	        	<tr>
	        		<th>이름</th>
	        		<td>
	        			<input type="text" name="userName" value="${memberInfo.userName }" readOnly="readOnly"/>
	        		</td>	
	        	</tr>
	        	<tr>
	        		<th>휴대폰</th>
	        		<td>
	        			<input type="text" name="tel" value="${memberInfo.tel}" readOnly="readOnly"/>
	        		</td>
	        	</tr>
	        </table>
        </div>

        <div class="content-row-2">
        	<button type="button" onclick="location.href='${contextPath }/member/uploadImageForm.do' " class="btn">프로필 사진 수정</button>
            <button type="button" onclick="location.href='${contextPath }/member/myInfo.do' " class="btn">정보 수정</button>
            <button type="button" onclick="location.href='${contextPath }/member/deleteInfoForm.do' "class="btn">회원 탈퇴</button>
        </div>
    </form>   
</section>
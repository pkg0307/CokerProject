<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "contextPath" value="${pageContext.request.contextPath }"></c:set>
<% 
	request.setCharacterEncoding("utf-8");
%>
<!-- 회원정보 수정후 AdminMemberController에서 보내주는 Message alert로 출력-->
<c:if test="${not empty message }">
<script>
window.onload=function(){
	modiResult();
}
function modiResult(){
	alert("${message}");
};
</script>
</c:if>

<section class="myPageMain-section memberDetail-section">
	<form name="frm_memberInfo" method="post" id="frm_memberInfo" >
		<input type="hidden" name="adminId" value="${sessionScope.memberInfo.userEmail}">
		
		<hr class="m-divider">
		<h2 class="sec-tit">[ ${member_info.nickname } ]님의 상세정보</h2>
		<hr class="m-divider">
	
		<div class="content-row-1 memberDetail-div">
			<table>
				<colgroup>
					<col width="25%">
					<col width="75%">
				</colgroup>
				<tr>
					<th>프로필사진</th>
					<td>
						<img class="profile-img" alt="${member_info.nickname }님의 프로필 사진" src="${contextPath }/download.do?nickname=${memberInfo.nickname}&imgName=${memberInfo.imgName}">
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<input name="userEmail" value="${member_info.userEmail }" readonly="readonly" style="background:lightgray;">
					</td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td>
						<input name="nickname" type="text" value="${member_info.nickname }">
						<input type="hidden" name="origin_nickname" id="origin_nickname" value="${member_info.nickname }" />
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input name="userPw" type="password" value="${member_info.userPw }" readonly="readonly" style="background:lightgray;">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input name="userName" type="text" value="${member_info.userName }">
					</td>
				</tr>
				<tr>
					<th>휴대폰</th>
					<td>
						<input name="tel" type="text" value="${member_info.tel }">
					</td>
				</tr>
				<tr>
					<th>계정상태</th>
					<td>
						<c:choose>
							<c:when test="${member_info.enable == '1'}">
								<input type="text" value="활성화" readonly="readonly" style="background:lightgray;"/>
								<input name="enable" type="hidden" value="0">
							</c:when>
							<c:otherwise>
								<input type="text" value="비활성화" readonly="readonly" style="background:lightgray;"/>
								<input name="enable" type="hidden" value="1">
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="content-row-2">
			<c:choose>
				<c:when test="${isLogOn==true and member_info.enable == '0'}">
					<input id="enable_btn_confirm" class="btn" type="button" value="계정활성화">
				</c:when>
				<c:otherwise>
					<input id="enable_btn_confirm" class="btn" type="button" value="계정비활성화">
				</c:otherwise>
			</c:choose>
			<input id="btn_update" type="button"
				class="btn" value="수정하기"
				onClick="javascript: form.action='${contextPath }/admin/member/overlapNickName.do';submit();">
			<input id="detail_btn_cancel" type="button"
				class="btn" value="취소">
			<input id="detail_btn_delete" type="button"
				class="btn" value="삭제">
		</div>
		
		<div id="enable_checkBox" class="enable_checkBox">
			<div class="enable_check_pw">
				<span class="enable_close">&times;</span>
				<c:choose>
					<c:when test="${member_info.enable == '1'}">
						<h3>계정차단 사유</h3>
						
						<textarea class="reason-textarea" name="disable_reason" rows="10" placeholder="계정차단 사유를 입력해주세요."></textarea>
						
						<p class="content-p">
							위와 같은 사유로 ${member_info.userEmail} 계정을 <br>
							<select id="disable_term" name="disable_term">
								<option value="7">7일</option>
								<option value="14">14일</option>
								<option value="30">30일</option>
								<option value="9999">무기한</option>
							</select>동안 차단합니다.
						<p>
					</c:when>
					<c:otherwise>
						<h3>[ ${member_info.userEmail} ] 계정을 활성화합니다.</h3>
					</c:otherwise>
				</c:choose>
				
				<hr class="divider">
				
				<h3>관리자 암호 확인</h3>
				<div class="pw-div">
					<input type="password" name="certPw" onkeypress="javascript: if(event.keyCode==13){form.action='${contextPath}/admin/member/enableMember.do';submit();}">
					<input id="btn_enable_confirm" type="button" class="btn"
						name="btn_enable_confirm" value="확인"
						onClick="javascript: form.action='${contextPath}/admin/member/enableMember.do';submit();">
				</div>
			</div>
		</div>
		
		<div id="detail_checkBox" class="detail_checkBox">
			<div class="detail_check_pw">
				<span class="detail_close">&times;</span>
				<h2>관리자 암호 확인</h2>
				
				<div class="pw-div">
					<input type="password" name="adminPw" id="adminPw" onkeypress="javascript: if(event.keyCode==13){form.action='${contextPath}/admin/member/deleteMember.do';submit();}">
					<input id="btn_delete_confirm" type="button" name="btn_delete_confirm" class="btn"
						value="확인" onClick="javascript: form.action='${contextPath}/admin/member/deleteMember.do';submit();">
				</div>
			</div>
		</div>
	</form>
</section>
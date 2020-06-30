<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "contextPath" value="${pageContext.request.contextPath }"></c:set>
<% 
	request.setCharacterEncoding("utf-8");
%>
<div class="login-form"
	style="margin: auto; width: 350px; height: 810px;">
	<hr>
	<h3>관리자 권한으로 계정 상태변경을 진행합니다.</h3>
	<hr>
	<p><b>계정정보</b></p>
	<form>
		<div id="detail_table">
			<div class="text-center">
				<p><b>프로필 사진</b></p>
				<img width="200px" alt="${member_info.nickname }님의 프로필 사진"
					src="${contextPath }/download.do?nickname=${memberInfo.nickname}&imgName=${memberInfo.imgName}">
			</div><br>
			<b>이메일</b>
			<div>
				<input class="form-control" name="userEmail" size="20"
					value="${member_info.userEmail }"> <input name="userEmail"
					type="hidden" value="${member_info.userEmail }">
			</div>
			<b>닉네임</b>
			<div>
				<input class="form-control" name="nickname" type="text" size="20"
					value="${member_info.nickname }">
			</div>
			<b>이름</b>
			<div>
				<input class="form-control" name="userName" type="text" size="20"
					value="${member_info.userName }">
			</div>
			<b>휴대폰 번호</b>
			<div>
				<input class="form-control" name="tel" type="text" size="20"
					value="${member_info.tel }">
			</div>
			<b>계정 상태</b>
			<div>
				<c:choose>
					<c:when test="${member_info.enable == '1'}">
						<input type="text" class="form-control" value="활성화" />
						<input name="enable" type="hidden" value="0">
					</c:when>
					<c:otherwise>
						<input type="text" class="form-control" value="비활성화" />
						<input name="enable" type="hidden" value="1">
					</c:otherwise>
				</c:choose>
			</div>
			<br>
			<div>
				<input id="enable_btn_confirm" class="btn btn-warning btn-block"
					type="button" value="계정상태 변경"> <input
					id="enable_btn_cancel" class="btn btn-primary btn-block"
					type="button" value="취소">
			</div>
			<div id="enable_checkBox" class="enable_checkBox">
				<div class="enable_check_pw">
					<header class="check_header">
						<span class="enable_close">&times;</span>
					</header>
					<div class="check_content">
						<c:choose>
							<c:when test="${member_info.enable == '1'}">
								<div>
									<p>계정차단 사유</p>
									<p>
										<textarea name="disable_reason" cols="40" rows="10"
											placeholder="계정차단 사유를 입력해주세요..."></textarea>
									</p>
								</div>
								<div>
									위와 같은 사유로 ${member_info.userEmail} 계정을 <select
										id="disable_term" name="disable_term">
										<option value="7">7일</option>
										<option value="14">14일</option>
										<option value="30">30일</option>
										<option value="9999">무기한</option>
									</select> 동안 차단합니다.
								</div>
							</c:when>
							<c:otherwise>
								<div>${member_info.userEmail}계정을 활성화합니다.</div>
							</c:otherwise>
						</c:choose>
						<label style="font-size: 0.8rem"><b>관리자 암호 확인</b></label> <input
							type="password" name="adminPw"> <input type="hidden"
							name="adminId" value="${sessionScope.memberInfo.userEmail}">
						<input id="btn_enable_confirm" type="submit"
							name="btn_enable_confirm" value="확인"
							onClick="javascript: form.action='${contextPath}/admin/member/enableMember.do';">
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
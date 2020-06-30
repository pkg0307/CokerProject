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
	<h3>������ �������� ���� ���º����� �����մϴ�.</h3>
	<hr>
	<p><b>��������</b></p>
	<form>
		<div id="detail_table">
			<div class="text-center">
				<p><b>������ ����</b></p>
				<img width="200px" alt="${member_info.nickname }���� ������ ����"
					src="${contextPath }/download.do?nickname=${memberInfo.nickname}&imgName=${memberInfo.imgName}">
			</div><br>
			<b>�̸���</b>
			<div>
				<input class="form-control" name="userEmail" size="20"
					value="${member_info.userEmail }"> <input name="userEmail"
					type="hidden" value="${member_info.userEmail }">
			</div>
			<b>�г���</b>
			<div>
				<input class="form-control" name="nickname" type="text" size="20"
					value="${member_info.nickname }">
			</div>
			<b>�̸�</b>
			<div>
				<input class="form-control" name="userName" type="text" size="20"
					value="${member_info.userName }">
			</div>
			<b>�޴��� ��ȣ</b>
			<div>
				<input class="form-control" name="tel" type="text" size="20"
					value="${member_info.tel }">
			</div>
			<b>���� ����</b>
			<div>
				<c:choose>
					<c:when test="${member_info.enable == '1'}">
						<input type="text" class="form-control" value="Ȱ��ȭ" />
						<input name="enable" type="hidden" value="0">
					</c:when>
					<c:otherwise>
						<input type="text" class="form-control" value="��Ȱ��ȭ" />
						<input name="enable" type="hidden" value="1">
					</c:otherwise>
				</c:choose>
			</div>
			<br>
			<div>
				<input id="enable_btn_confirm" class="btn btn-warning btn-block"
					type="button" value="�������� ����"> <input
					id="enable_btn_cancel" class="btn btn-primary btn-block"
					type="button" value="���">
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
									<p>�������� ����</p>
									<p>
										<textarea name="disable_reason" cols="40" rows="10"
											placeholder="�������� ������ �Է����ּ���..."></textarea>
									</p>
								</div>
								<div>
									���� ���� ������ ${member_info.userEmail} ������ <select
										id="disable_term" name="disable_term">
										<option value="7">7��</option>
										<option value="14">14��</option>
										<option value="30">30��</option>
										<option value="9999">������</option>
									</select> ���� �����մϴ�.
								</div>
							</c:when>
							<c:otherwise>
								<div>${member_info.userEmail}������ Ȱ��ȭ�մϴ�.</div>
							</c:otherwise>
						</c:choose>
						<label style="font-size: 0.8rem"><b>������ ��ȣ Ȯ��</b></label> <input
							type="password" name="adminPw"> <input type="hidden"
							name="adminId" value="${sessionScope.memberInfo.userEmail}">
						<input id="btn_enable_confirm" type="submit"
							name="btn_enable_confirm" value="Ȯ��"
							onClick="javascript: form.action='${contextPath}/admin/member/enableMember.do';">
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
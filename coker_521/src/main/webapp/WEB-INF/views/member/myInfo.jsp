<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<script type="text/javascript">
function checkNickname(){
	var result = true;
	if($("#mod_nickname").val()==""){
		alert("닉네임을 입력해주세요.");
		$("#mod_nickname").focus();
		result = false;
	}
	if($("#mod_nickname").val() != $("#origin_nickname").val()){
		$.ajax({
			url : contextPath+"/member/overlapNickName.do",
			type : "POST",
			async:false,
			dataType : "json",
			data : {nickname:$(mod_nickname).val()},
			success: function(data){	
				if(data==0){
					result = true;
				}else if(data!=0){
					alert("사용할 수 없는 닉네임입니다.");
					result = false;
				}
			},
		    error:function(data){
		       alert("에러가 발생했습니다.");
		       result = false;
		    },
		    complete:function(data){
		    	
		    }
		});	
	}
	return result;
};

</script>

<section class="memberForm-section">
	<form action="${contextPath }/member/modifyInfo.do" method="POST" onSubmit="return checkNickname()">
		<hr class="m-divider">
		<h2 class="sec-tit">회원 정보 수정</h2>
		<hr class="m-divider">
		
        <div class="content-row-1">
        	<p>이메일</p>
        	<input type="text" name="userEmail"  id="mod_userEmail" value="${memberInfo.userEmail }" disabled />
			<input type="hidden" name="userEmail" id="mod_userEmail" value="${memberInfo.userEmail }" />
			
           	<p>닉네임</p>
        	<input type="text" name="nickname" id="mod_nickname" value="${memberInfo.nickname }" required="required"/>
        	<input type="hidden" id="origin_nickname" value="${memberInfo.nickname }" />
        	<button id="mod_chkNick">닉네임 중복 확인</button>
        
        	<p>비밀번호</p>
     		<input type="password" name="userPw" id="mod_userPw"  value="${memberInfo.userPw }" disabled/>
     		<input type="hidden" name="userPw" id="mod_userPw" value="${memberInfo.userPw }" />
        	<button type="button" onclick="location.href='${contextPath }/member/modifyPw.do'">비밀번호 수정</button>
        
        	<p>이름</p>
			<input type="text" name="userName" value="${memberInfo.userName }" disabled/>
			<input type="hidden" name="userName" id="mod_userName" value="${memberInfo.userName }" />       	
        
        	<p>휴대폰 번호</p>
			<input type="text" name="tel" id="mod_tel" value="${memberInfo.tel }" required="required"/>
        </div>
        
        <div class="pre-info">
			모든 정보를 정확하게 기입해 주세요.<br>
			계정 분실 시 사용될 수 있습니다.<br>
		</div>
		
		<div class="content-row-2">
			<input type="submit" class="btn" value="확인">
			<button type="button" onclick="location.href='${contextPath }/member/myPageMain.do'">취소</button>
        </div>
	</form>
</section>
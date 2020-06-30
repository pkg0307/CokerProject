<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"	isELIgnored="false"
	%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  

<section class="csCenterMain-section">
	<h2 class="sec-tit">고객센터</h2>
	
	<hr class="divider">
	<div class="content-row-1">
		<h2 class="con-tit">FAQ - 유용한 도움말</h2>
		<table>
			<tbody>
				<tr>
					<td class="faq_title1"><span class="faq_btn">Q. 코딩커뮤니티를 가입하면 어떤 서비스를 이용할 수 있나요?</span></td>
				</tr>
				<tr>
					<td class="faq_answer 1">
						코딩커뮤니티를 가입하시면 혼자 코딩 공부를 하면서 궁금했던 것들을 질문하시거나 비슷한 개린이들 혹은 주니어, 시니어 개발자들과
						지식을 공유할 수도 있고, 프로젝트에 참여할 개발자들을 구하실 수도 있습니다.
					</td>
				</tr>
				<tr>
					<td class="faq_title2"><span class="faq_btn">Q. 코딩커뮤니티는 어떻게 가입할 수 있나요?</span></td>
				</tr>
				<tr>
					<td class="faq_answer 2">
						자주 사용하던 이메일 주소를 인증한 후 코딩커뮤니티 계정으로 이용해보세요.
					</td>
				</tr>
				<tr>
					<td class="faq_title3"><span class="faq_btn">Q. 코딩커뮤니티 가입을 했는데 로그인이 안됩니다.</span></td>
				</tr>
				<tr>
					<td class="faq_answer 3">
						계정을 가입하신 후 계정(이메일 또는 전화번호)과 비밀번호를 정확하게 입력하셨음에도 로그인이 되지 않는 경우 계정이 이용정지된 상태일 수 있습니다.<br>
						계정(이메일 또는 전화번호)을 기재하여 고객센터로 문의주시면 계정이 이용제재 상태인지 확인 후 답변 드리겠습니다.
					</td>
				</tr>
				<tr>
					<td class="faq_title4"><span class="faq_btn">Q. 코딩 커뮤니티 계정이 기억나지 않아요.</span></td>
				</tr>
				<tr>
					<td class="faq_answer 4">
						코딩 커뮤니티 웹사이트에서 계정을 찾을 수 있습니다.<br>
						<b>- [코딩 커뮤니티 웹사이트 로그인창 -> <a href="${contextPath }/member/searchUserEmailForm.do" >이메일 찾기</a>]</b><br><br>
						
						코딩커뮤니티 계정은 아래 방법으로 찾을 수 있습니다.<br>
						코딩커뮤니티 가입시 설정해둔 본인 이름과 전화번호를 입력하여 코딩커뮤니티 계정을 찾을 수 있습니다.<br>
						회원님의 정보 보호를 위해 이메일 주소 일부를 가린채로 계정을 보여 드리고 있습니다.
					</td>
				</tr>
				<tr>
					<td class="faq_title5"><span class="faq_btn">Q. 코딩 커뮤니티 계정의 비밀번호가 기억나지 않아요.</span></td>
				</tr>
				<tr>
					<td class="faq_answer 5">
						코딩 커뮤니티 웹사이트에서 계정 비밀번호를 재설정 할 수 있습니다.<br>
						<b>- [코딩 커뮤니티 웹사이트 로그인창 -> <a href="${contextPath }/member/resetUserPwForm.do">비밀번호 재설정</a>]</b><br><br>
						
						계정을 안전하게 보호하기 위해, 비밀번호 재설정 시 카카오계정에 등록되었던 이메일 또는 휴대전화 및 사용자 확인이 필요합니다.<br>
						사용자 확인 과정을 통과하지 못하면 비밀번호를 재설정 할 수 없습니다.<br>
						<br>
						<span class="alert">
						[ ! ] 안전한 비밀번호를 설정해 주세요.<br>
						- 모두가 알고있는 고유명사나 회원님의 생년월일, 전화번호 등을 포함하여 설정하면 타인이 회원님의 비밀번호를 추측할 가능성이 높아집니다.<br>
						- 비밀번호는 특수문자, 문자, 숫자를 조합하여 과거에 사용하지 않았던 비밀번호로 설정해 주세요.<br>
						</span>
					</td>
				</tr>
				<tr>
					<td class="faq_title6"><span class="faq_btn">Q. 인증메일을 확인할 수 없어요.</span></td>
				</tr>
				<tr>
					<td class="faq_answer 6">
						계정 아이디로 설정한 이메일을 아래 사유로 확인할 수 없나요?<br>
						- 이메일 주소를 잘못된 아이디나 도메인명으로 입력하여 계정을 만드셨나요?<br>
						<span style="color:transparent;">- </span>(@daum.com 또는 daum.met 등)<br> 
						- 실제 사용하지 않는 임의의 이메일 주소로 계정을 만드셨나요?<br>
						- 메일 서비스 종료 및 탈퇴로 인해 계정으로 등록한 메일을 더 이상 사용할 수 없나요?<br><br>
						
						위의 경우 계정으로 사용중인 이메일을, 사용 가능한 이메일로 변경하여 인증 받을 수 있습니다.<br>
						혹시 ‘이메일 주소 변경하기’ 링크가 보이지 않거나, 비밀번호도 잊어서 로그인 하실 수 없으신가요?<br>
						이 경우 고객센터로 문의해주시면, 몇 가지 정보 확인 후 정상적인 이메일로 커뮤니티서비스를 이용하실 수 있도록 도움 드리겠습니다.
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<hr class="divider">
	
	<div class="content-row-3">
		<h2 class="con-tit">개발동기</h2>
		<p>
			&#09;코딩을 독학으로 공부할 때 구글링을 하다 보면 영어로 된 사이트나 문서들이 대부분입니다.
			<br>다른 대형 커뮤니티 사이트 처럼 코딩에 관련된 대형 커뮤니티 사이트가 있으면 좋을것 같아 개발해 보았습니다. 
			<br><br><b>COKER(코딩 커뮤니티)</b>는 프로그래밍 언어와 관련된 정보나 노하우 등을 공유하고, 
			<br>유저들 간의 질문과 답변을 통해 구글링의 번거로움 및 언어장벽 문제를 해결할 수 있도록 돕는 커뮤니티 사이트입니다. 
			<br><br>그 외에도 구인·구직이나 자유게시판 등 커뮤니티에 최적화된 서비스들이 준비되어 있습니다. 
			<br><br>당신도 참여해보세요!
		</p>
		<div>&nbsp;</div>
			<h2 class="con-tit">개발자</h2>
		<p>
			권용길 | 박경근 | 신동혁 | 임채헌(<a href="https://blog.naver.com/co0717gjs" target="_blank">개인 블로그 방문</a>)
		</p>
	</div>
	
	<hr class="divider">
	
	<div class="content-row-4">
			<h2 class="con-tit">문의하기</h2>
		<p>
			Email : <a href="mailto:cokerhelp@gmail.com">cokerhelp@gmail.com</a>
		</p>
	</div>
</section>
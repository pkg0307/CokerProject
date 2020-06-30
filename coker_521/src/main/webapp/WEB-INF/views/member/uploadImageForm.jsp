<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false" %>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> 
<%	request.setCharacterEncoding("UTF-8"); %> 
<script>
	function fileCheck(input){
		var maxSize = 5*1024*1024;
		var fileSize = 0;
		
		var browser=navigator.appName;
		
		// 익스플로러일 경우
		if (browser=="Microsoft Internet Explorer")
		{
			var oas = new ActiveXObject("Scripting.FileSystemObject");
			fileSize = oas.getFile( input.value ).size;
		} 
		// 익스플로러가 아닐경우 
		else { 
			fileSize = input.files[0].size;
		}

        if(fileSize > maxSize)
        {
            alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다.");
            input.value='';
			return false;
		}
        
        pathPoint = input.value.lastIndexOf('.');
		filePoint = input.value.substring(pathPoint+1,input.length);
		fileType = filePoint.toLowerCase();
		if(fileType=='jpg'||fileType=='jpeg'||fileType=='png'||fileType=='gif'||fileType=='bmp'){
			readURL(input);
		} else{
			alert('이미지 파일만 선택할 수 있습니다.');
			input.value='';
			return false;
		}
	}

	function readURL(input){
		if(input.files && input.files[0]){
			var reader = new FileReader();
			reader.onload = function(e){
				$('#preview').attr('src',e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	function fileValueCheck(obj){
		var file = document.getElementById("imgFileName").value;
		if(!file){
			alert("새 프로필 사진을 첨부해주세요.");
			return false;
		} else{
			obj.submit();
		}
	}
</script>

<section class="uploadImageForm-section">
	<form action="${contextPath}/member/uploadImage.do" method="post" enctype="multipart/form-data">
		<hr class="m-divider">
		<h2 class="sec-tit">프로필 이미지 수정</h2>
		<hr class="m-divider">
	
		<hr class="divider">
		<p class="content-p">프로필 사진에 사용할 수 있는 파일은 5MB 이내의 (jpg,jpeg,png,gif) 확장자만 가능합니다.</p>
		<hr class="divider">
		
		<div class="content-row-1">
			<b>사용할 이미지</b>
			<label class="btn" style="">사진 추가<input type="file" style="display:none;" id="imgFileName" name="imgFileName" onchange="fileCheck(this)" accept="image/*"></label>
		</div>
		<img class="preview" id="preview" src="${contextPath }/download.do?nickname=${memberInfo.nickname}&imgName=${memberInfo.imgName}"/>
		
		<div class="content-row-2">
			<input type="button" class="btn" value="이미지 등록하기" onclick="fileValueCheck(this.form)">
			<input type="button" class="btn" value="취소" onclick="location.href='${contextPath }/member/myPageMain.do' ">
		</div>
	</form>
</section>














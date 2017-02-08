<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/css.jsp" %>
<script>
$(document).ready(function(){
	$("#btnSave").click(function(){
		var writer 	= $("#writer").val();
		var subject = $("#subject").val();
		var content = $("#content").val();
		var passwd	= $("#passwd").val();
		if(writer==""){
			alert("작성자를 입력하세요.");
			$("#writer").focus();
			return; //return을 안해주면 다음 코드가 실행됨
		}
		if(subject==""){
			alert("제목을 입력하세요.");
			$("#subject").focus();
			return;
		}
		if(passwd==""){
			alert("비밀번호를 입력하세요.");
			$("#passwd").focus();
			return;
		}
		document.form1.submit();
	});
});
</script>
</head>
<body>
<h2>글쓰기</h2>
<hr size="5" color="#48586d">

<!--
	* 파일 업로드
	-. form태그의 enctype속성은 폼을 전송할때 사용할 인코딩 밥법을 지정한다.  
	-. multipart/form-data : 어떠한 문자 인코딩도 하지 않는다. 주로 파일업로드 컨트롤을 제공하는 경우 사용
-->
<form name="form1" method="post" action="${path}/board_servlet/insert.do" enctype="multipart/form-data">
<table class="board_view">
<tbody>
	<tr> 
		<th align="center">작성자</th>
		<td><input name="writer" id="writer"></td>
	</tr>
	<tr>
		<th align="center">제목</th>
		<td><input name="subject" id="subject"  size="59"></td>
	</tr>
	<tr>
		<th align="center">본문</th>
		<td><textarea rows="5" cols="60" name="content" id="content"></textarea> </td>
	</tr>
	<tr>
		<th align="center">첨부파일</th>
		<td><input type="file" name="file1"></td>
	</tr>
	<tr>
		<th align="center">비밀번호</th>
		<td><input type="password" name="passwd" id="passwd"></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<button type="button" id="btnSave" class="btn">확인</button>
		</td>
	</tr>
	</tbody>
</table>
</form>
</body>
</html>
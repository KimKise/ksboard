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
	$("#btnUpdate").click(function(){
		var subject = $("#subject").val();
		var passwd = $("#passwd").val();
		var passwd2 = $("#passwd2").val();
		if(subject==""){
			alert("제목을 입력하세요");
			$("#subject").focus();
			return;
		}
		if(passwd==""){
			alert("비밀번호를 입력하세요");
			$("#passwd").focus();
			return;
		}
		if(passwd != passwd2){
			alert("비밀번호가 일치하지 않습니다.");
			$("#passwd").val('');
			$("#passwd2").val('');
			$("#passwd").focus();
			return;
		}
		document.form1.action ="${path}/board_servlet/update.do?num=${dto.num}";
		document.form1.submit();
	});
	$("#btnDelete").click(function(){
		var deleteCheck = confirm("해당 게시글을 삭제하겠습니까?");
		if(deleteCheck){
			location.href="${path}/board_servlet/delete.do?num=${dto.num}";
		}else{
			return;
		}
	});
	$("#btnClear").click(function(){
		document.form1.file1.select();
		document.form1.filename.select();
		document.getElementById('filename').select();
		document.selection.clear;
		
	});
});
</script>
</head>
<body>
<h2>게시글 수정/삭제</h2>
<hr size="5" color="#48586d">
<form name="form1" method="post" action="" enctype="multipart/form-data">
<table class="board_view">
<tbody>
	<tr>
		<th>글번</th>
		<td>${dto.num}</td>
		<th>조회수</th>
		<td>${dto.readcount}</td>
	</tr>
	<tr>
		<th align="center" width="20%">작성자</th>
		<td><input type="hidden" name="writer" value="${dto.writer}">${dto.writer}</td>
		<th align="center" width="20%">작성시간</th>
		<td>${dto.reg_date}</td>
	</tr>
	<tr>
		<th align="center">제목</th>
		<td colspan="3"><input id="subject" name="subject" value="${dto.subject}" size="48"></td>
	</tr>
	<tr>
		<th align="center">본문</th>
		<td colspan="3"><textarea rows="6" cols="50" name="content">${dto.content}</textarea> </td>
	</tr>
	<tr>
		<th align="center">첨부파일</th>
		<td>
			 <input type="file" name="file1">
		</td>
	</tr>
	<tr>
		<th align="center">새 비밀번호</th>
		<td colspan="3"><input type="password" name="passwd" id="passwd" value="${dto.passwd}"></td>
	</tr>
		<tr>
		<th align="center">비밀번호 확인</th>
		<td colspan="3"><input type="password" name="passwd" id="passwd2" value="${dto.passwd}"></td>
	</tr>
	</tbody>
	<tr>
		<td align="center" colspan="4">
			<input type="hidden" name="num" value="${dto.num}">
			<button type="button" id="btnUpdate" class="btn">수정</button>
			<button type="button" id="btnDelete" class="btn">삭제</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>
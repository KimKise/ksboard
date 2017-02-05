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
	
});
</script>
</head>
<body>
<h2>게시글 수정</h2>
<hr size="5" color="#48586d">
<form name="form1" method="post" action="">
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
		<td>${dto.writer}</td>
		<th align="center" width="20%">작성시간</th>
		<td>${dto.reg_date}</td>
	</tr>
	<tr>
		<th align="center">제목</th>
		<td colspan="3">${dto.subject}</td>
	</tr>
	<tr>
		<th align="center">본문</th>
		<td colspan="3">${dto.content}</td>
	</tr>
	<tr>
		<th align="center">비밀번호</th>
		<td colspan="3"><input type="password" id="passwd"></td>
	</tr>
	</tbody>
	<tr>
		<td align="center" colspan="4">
			<button type="button" id="btnEdit" class="btn">수정</button>
			<button type="button" id="btnReply" class="btn">답변</button>
			<button type="button" id="list" class="btn">목록</button>
		</td>
	</tr>

</table>
</form>
</body>
</html>
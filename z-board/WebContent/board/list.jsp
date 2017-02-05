<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file="../include/css.jsp" %>
<%@ include file ="../include/header.jsp" %>
<script>
$(document).ready(function(){
	$("#btnInsert").click(function(){
		location.href="${path}/board/insert.jsp";
	});
});
</script>
</head>
<body>
<h2>게시판 목록</h2>
<hr size="5" color="#48586d">
<table  class="board_list">
	<thead>
	<tr>
		<th width="10%">번호</th>
		<th width="20%">작성자</th>
		<th width="40%">제목</th>
		<th width="20%">날짜</th>
		<th width="10%">조회수</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="dto" items="${list}">
	<tr>
		<td>${dto.num}</td>
		<td>${dto.writer}</td>
		<td><a href="${path}/board_servlet/detail.do?num=${dto.num}">${dto.subject}</a></td>
		<td>${dto.reg_date}</td>
		<td>${dto.readcount}</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
<button type="button" id="btnInsert" class="btn">글쓰기</button>
</body>
</html>
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

<!---------------------------------------------------------------------------------->

<form  name="form1" method="post" action="${path}/board_servlet/search.do">
	<select name="search_option" >
		<option value="all" <c:if test="${search_option == 'all'}">selected</c:if>>전체검색</option>
		<option value="writer" <c:if test="${search_option == 'writer'}">selected</c:if>>작성자</option>
		<option value="subject" <c:if test="${search_option == 'subject'}">selected</c:if>>제목</option>
		<option value="content" <c:if test="${search_option == 'content'}">selected</c:if>>내용</option> 
	</select>
<input name="keyword">
<input type="submit" value="검색" id="btnSearch">
<button type="button" id="btnInsert" >글쓰기</button>
</form>

<!---------------------------------------------------------------------------------->
<table  class="board_list">
	<thead>
	<tr>
		<th>번호 </th>
		<th>작성자 </th>
		<th>제목 </th>
		<th>날짜 </th>
		<th>조회수&nbsp;&nbsp;</th>
		<th>첨부파일&nbsp;&nbsp;</th>
		<th>다운횟수&nbsp;&nbsp;</th>
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
		<td>
			<c:if test="${dto.filesize>0}">
				<%-- <a href="${path}/upload/${dto.filename}"><img src="${path}/images/download.png"></a> --%>
				<a href="${path}/board_servlet/download.do?num=${dto.num}">
					<img src="${path}/images/download.png">
				</a>
			</c:if>
		</td>
		<td>${dto.download}</td>
	</tr>
	</c:forEach>
	</tbody>
</table>

</body>
</html>
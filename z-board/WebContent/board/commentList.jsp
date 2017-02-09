<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/css.jsp" %>
</head>
<body>

<!-- 댓글목록  -->
<table class="board_view">
	<c:forEach var="row" items="${commentList}">
	<tr>
		<hr>
		<th>
			${row.writer}( <fmt:formatDate value="${row.reg_date}" pattern="yyy-MM-dd" /> )<br>
		</th>
		<td>
		: ${row.content}
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
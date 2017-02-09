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
	$("#list").click(function(){
		location.href ="${path}/board_servlet/list.do";
	});
	$("#btnEdit").click(function(){
		document.form1.action = "${path}/board_servlet/passwd_check.do";
		document.form1.submit();
	});
	
	//------------------------------댓글 관련------------------------------------
	//댓글리스트 가져오기
	comment_list();
	//댓글 버튼
	$("#btnComment").click(function(){
		var writer = $("#writer").val();
		var content = $("#content").val();
		if(writer == ""){
			alert("이름을 입력하세요.");
			return;
		}
		if(content == ""){
			alert("댓글 내용을 입력하세요.");
			return;
		}
		comment_write();
	});
});


function comment_write(){
		var param = "board_num=${dto.num}&writer="+$("#writer").val()+"&content="+$("#content").val();
	$.ajax({
		type	:	"post",
		url		:	"${path}/board_servlet/comment_add.do",
		data	:	param,
		success	:	function(){
			//글쓰고 남아 있는 값 초기화
			$("#writer").val("");
			$("#content").val("");
			comment_list();
		}
	});
	
}
function comment_list(){
	$.ajax({
		type	:	"post",
		url		:	"${path}/board_servlet/commentList.do",
		data	:	"board_num=${dto.num}",
		success	:	function(result){
			$("#commentList").html(result);
		}
	});
}

</script>
</head>
<body>
<h2>게시글 상세 목록</h2>
<hr size="5" color="#48586d">

<!-------------------------Detail Form and Table----------------->
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
		<th align="center">첨부파일</th>
		<td>
			<c:if test="${dto.filesize>0}">
				<%-- <a href="${path}/upload/${dto.filename}"><img src="${path}/images/download.png"></a> --%>
				<a href="${path}/board_servlet/download.do?num=${dto.num}">
					${dto.filename}
				</a>
			</c:if>
		</td>
		<th align="center">다운 횟수</th>
		<td>${dto.download}</td>
	</tr>
	<tr>
		<th align="center">비밀번호</th>
		<td colspan="3"><input type="password" name="passwd"></td>
			<c:if test="${param.message=='error'}">
				<span style="color : red">
					비밀번호가 일치하지 않습니다.
				</span>			
			</c:if>
	</tr>
	</tbody>
	<tr>
		<td align="center" colspan="4">
			<input type="hidden" name="num" value="${dto.num}">
			<button type="button" id="btnEdit" class="btn">수정/삭제</button>
			<button type="button" id="list" class="btn">목록</button>
		</td>
	</tr>
</table>
</form>

<!---------------------Comment List-------------------------------->
<h4>댓글 목록</h4>
<hr size="3" color="#48586d">
<div id="commentList"></div>


<!--------------------Comment Table : use Ajax-------------------->

<table>
	<tr>
		<td><input id="writer" placeholder="이름을 입력하세요."></td>
	</tr>
	<tr>
		<td><textarea rows="5" cols="80" id="content" placeholder="댓글 내용을 입력하세요."></textarea> </td>
		<td><button type="button" id="btnComment" class="btn">댓글 입력</button></td>
	</tr>
</table>

</body>
</html>
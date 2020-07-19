<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
<table class="board_view">
	<colgroup>
		<col width="15%" />
		<col width="35%" />
		<col width="15%" />
		<col width="35%" />
	</colgroup>
	<caption>게시글 상세</caption>
	<tbody>
		<tr>
			<th scope="row">글 번호</th>
			<td>${map.IDX}</td>
			<th scope="row">조회수</th>
			<td>${map.HIT_CNT}</td>
		</tr>
		<tr>
			<th scope="row">작성자</th>
			<td>${map.CREA_ID}</td>
			<th scope="row">작성시간</th>
			<td>${map.CREA_DTM}</td>
		</tr>
		<tr>
			<th scope="row">제목</th>
			<td colspan="3">${map.TITLE}</td>
		</tr>
		<tr>
			<td colspan="4">${map.CONTENTS}</td>
		</tr>
		<tr>
			<th scope="row">첨부파일</th>
			<c:choose>
				<c:when test="${fn:length(list) > 0}">
					<td colspan="3">
						<c:forEach var="row" items="${list}">
							<input type="hidden" id="idx" value="${row.IDX}" />
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME}</a>
							(${row.FILE_SIZE}kb)
						</c:forEach>
					</td>
					</c:when>
					<c:otherwise>
						<td colspan="3" >첨부파일이 없습니다.</td>
					</c:otherwise>
			</c:choose>
		</tr>
	</tbody>
</table>

<a href="#this" class="btn" id="list">목록으로</a>
<a href="#this" class="btn" id="update">수정하기</a>

<%@ include file="/WEB-INF/include/include-body.jspf" %>
<script type="text/javascript">
	$(document).ready(function() {
		$("#list").on("click", function(e) { //목록으로 버튼
			e.preventDefault(); //a태그의 href속성 중단.
			fn_openBoardList();
		});
		
		$("#update").on("click", function(e) { //수정하기 버튼
			e.preventDefault();
			fn_openBoardUpdate();
		});
		
		$("a[name='file']").on("click", function(e) { //파일 이름
			e.preventDefault();
			fn_downloadFile($(this));
		});
	});
	
	function fn_openBoardList() {
		var comSubmit = new ComSubmit(); //common.js에 Comsubmit 객체 생성.
		comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
		comSubmit.submit();
	}
	
	function fn_openBoardUpdate() {
		var idx = "${map.IDX}";
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/openBoardUpdate.do' />");
		comSubmit.addParam("idx", idx);
		comSubmit.submit();
	}
	
	function fn_downloadFile(obj) {
		var idx = obj.parent().find("#idx").val();
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
		 if (gfn_isNull($("[name='idx']").val()) == false){
			 $("[name='idx']").remove();
			comSubmit.addParam("idx", idx);
			comSubmit.submit();
		 }
	}
</script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
<form id="frm" name="frm" enctype="multipart/form-data">
	<table class="board_view">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<caption>게시글 수정</caption>
		<tbody>
			<tr>
				<th scope="row">글 번호</th>
				<td>${map.IDX}<input type="hidden" id="idx" name="idx" value="${map.IDX }"></td>
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
				<td colspan="3">
					<input type="text" id="title" name="title" class="wdp_90" value="${map.TITLE}" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<textarea rows="20" cols="100" title="내용" id="contents" name="contents">${map.CONTENTS}</textarea>
				</td>
			</tr>
			<tr>
				<th scope="row">첨부파일</th>
				<td colspan="3">
					<div id="fileDev">
						<c:forEach var="row" items="${list}" varStatus="var">
							<p>
								<input type="hidden" id="idx" name="idx_${var.index}" value="${row.IDX}">
								<a href="#this" id="name_${var.index}" name="name_${var.index}" >${row.ORIGINAL_FILE_NAME }</a>
								<input type="file" id="file_${var.index}" name="file_${var.index}" value="">(${row.FILE_SIZE }kb)
								<a href="#this" class="btn" id="delete_${var.index}" name="delete">X</a>
							</p>
						</c:forEach>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</form>

<a href="#this" class="btn" id="addFile">파일 추가</a>
<a href="#this" class="btn" id="list">목록으로</a>
<a href="#this" class="btn" id="update">저장하기</a>
<a href="#this" class="btn" id="delete">삭제하기</a>

<%@ include file="/WEB-INF/include/include-body.jspf" %>
<script type="text/javascript">
	var gfv_count = '${fn:length(list) + 1}';

	$(document).ready(function() {
		$("#list").on("click", function(e) { //목록으로 버튼
			e.preventDefault();
			fn_openBoardList();
		});
		
		$("#update").on("click", function(e) { //저장하기 버튼
			e.preventDefault();
			fn_updateBoard();
		});
		
		$("#delete").on("click", function(e) { //삭제하기 버튼
			e.preventDefault();
			fn_deleteBoard();
		})
		
		$("#addFile").on("click", function(e) { //파일 추가 버튼
			e.preventDefault();
			fn_addFile();
		})
		
		$("a[name='delete']").on("click", function(e) { //삭제 버튼
			e.preventDefault();
			fn_deleteFile($(this));
		})
	});		
	
	function fn_openBoardList() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
		comSubmit.submit();
	}
	
	function fn_updateBoard() {
		var comSubmit = new ComSubmit("frm");
		comSubmit.setUrl("<c:url value='/sample/updateBoard.do' />");
		comSubmit.submit();
	}
	
	function fn_deleteBoard() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/deleteBoard.do' />");
		comSubmit.addParam("idx", $("#idx").val());
		comSubmit.submit();
	}
	
	function fn_addFile() {
		var str = "<p>" +
							"<input type='file' id='file_" + (gfv_count) + "'name='file_" + (gfv_count) + "'>" +
							"<a href='#this' class='btn' id='delete_" + (gfv_count) + "' name='delete_" + (gfv_count) + "'>X</a>" +
						"</p>";
		$("#fileDev").append(str);
		$("#delete_" + (gfv_count++)).on("click", function(e) { //삭제버튼
			e.preventDefault();
			fn_deleteFile($(this));
		});
	}
	
	function fn_deleteFile(obj) {
		obj.parent().remove();
	}
</script>

</body>
</html>
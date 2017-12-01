<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   String path = request.getContextPath();
   String pg =  request.getParameter("page");
   String type =  request.getParameter("type");
   String keyword =  request.getParameter("keyword");
%>
<jsp:include page="../include/header.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 읽기</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<style>
	tr > th{
		text-align:center;
		vertical-align: center;
	}
</style>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	$(function(){
		fn_search();
	})
	
	function goBack(){
		
		var page = "<%= pg%>";
		var type = "<%= type%>";
		var keyword = "<%= keyword%>";
		
		 location.href="<%=path %>/board/main.do?page="+page+"&type="+type+"&keyword="+keyword
	}
	function fn_search(){
	    var param = {};
	    
		param["seq"] = "<%= request.getParameter("seq")%>";
		param["page"] = "<%= request.getParameter("page")%>";
		
	    $.ajax({
	       url:'<%= path%>/board/read.do',
	       data: {'param' : JSON.stringify(param)},
	       type:'POST',
	       contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	       dataType:'json',
	       error:function(request,status,error){
	           alert("[error code] : "+ request.status + "\n\n[message] :\n\n " + request.responseText + "\n[error msg] :\n " + error); //에러상황
	        },
	       success:function(data){
			if(data['error']){ alert(data['error']); return; }            
			if(data['success']){ alert(data['success']); }
				
			$("#reg_nm").html(data["brdInfo"]["REG_NM"]+"<small> ("+data["brdInfo"]["REG_ID"]+")</small>");
			$("#reg_date").html(data["brdInfo"]["REG_DATE"]);
			$("#title").html(data["brdInfo"]["TITLE"]);
			$("#contents").html(data["brdInfo"]["CONTENTS"]);
	       }
	    });
	 }
</script>
</head>
<body>
	<div class="container">
		<div class="container">
			<h2>
				게시글 조회 <small>Board Detail</small>
			</h2>
			<hr>
			<table id="form" class="table table-bordered">
				<tr>
					<th style="width: 25%">작성자</th>
					<td style="width: 25%"><span id="reg_nm"></span></td>
					<th style="width: 25%">작성일</th>
					<td style="width: 25%"><span id="reg_date"></span></td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3"><span id="title"></span></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3" style="height: 200px"><span id="contents"></span>
					</td>
				</tr>
			</table>
				
		</div>
		
		<div class="container">
			<div class="row">
				<div class="col-sm-10"></div>
				<div class="col-sm-2">
					<button type="button" class="btn btn-primary btn-block" onclick="goBack()">목록으로</button>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
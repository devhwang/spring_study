<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   String userId = (String) session.getAttribute("USER_ID");
   String path = request.getContextPath();
   String seq = request.getParameter("seq");
   String pg =  request.getParameter("page");
   String type =  request.getParameter("type");
   String keyword =  request.getParameter("keyword");
%>
<jsp:include page="../include/header.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<TITLE>게시글 읽기</TITLE>
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
	    
		param["seq"] = "<%= seq%>";
		
	    $.ajax({
	       url:'<%= path%>/board/read.do',
	       data: {'param' : JSON.stringify(param)},
	       type:'POST',
	       contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	       dataType:'json',
	       error:function(request,status,error){
	           //alert("[error code] : "+ request.status + "\n\n[message] :\n\n " + request.responseText + "\n[error msg] :\n " + error); //에러상황
	    	   alert("잘못된 요청입니다"); //에러상황
	        },
	       success:function(data){

			if(data["brdInfo"]["REG_ID"] == "<%= userId %>"){
				$("#del_btn, #mod_btn").show();
			}
			
			$("#REG_NM").html(data["brdInfo"]["REG_NM"]+"<small> ("+data["brdInfo"]["REG_ID"]+")</small>");
			$("#REG_DATE").html(data["brdInfo"]["REG_DATE"]);
			$("#TITLE").html(data["brdInfo"]["TITLE"]);
			$("#CONTENTS").html(data["brdInfo"]["CONTENTS"]);
	       }
	    });
	    
	 }
	
	function fn_modify(seq){
		location.href='<%= path%>/board/writeForm.do?seq='+seq;
	}

	function fn_delete(){
		
		if(!confirm("정말 삭제하시겠습니까?")){
			return;
		}
		
		var param = {
			"SEQ" : '<%= request.getParameter("seq")%>'
		}
	
		$.ajax({
			url:'<%= path%>/board/delete.do',
			data: {'param' : JSON.stringify(param)},
			type:'POST',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			dataType:'json',
			error:function(request,status,error){
		    	//alert("[error code] : "+ request.status + "\n\n[message] :\n\n " + request.responseText + "\n[error msg] :\n " + error); //에러상황
		    	alert("잘못된 요청입니다"); //에러상황
		    },
			success:function(data){
				if(data['error']){
					alert(data['error']);
					return;
				}
				
				if(data['success']){
					alert(data['success']);
				}
				
				location.href='<%= path%>/board/main.do'
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
					<td style="width: 25%"><span id="REG_NM"></span></td>
					<th style="width: 25%">작성일</th>
					<td style="width: 25%"><span id="REG_DATE"></span></td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3"><span id="TITLE"></span></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3" style="height: 200px"><span id="CONTENTS"></span>
					</td>
				</tr>
			</table>
				
		</div>
		
		<div class="container">
			<div class="row">
				<div class="col-sm-8"></div>
				<div class="col-sm-4">
					<button type="button" id="back_btn" class="btn btn-primary pull-right" onclick="goBack()" >목록</button>
					<button type="button" id="mod_btn" class="btn btn-primary pull-right" onclick="fn_modify(<%=seq%>)" style="display:none;">수정</button>
					<button type="button" id="del_btn" class="btn btn-primary pull-right" onclick="fn_delete()" style="display:none;">삭제</button>
					
					
					
				</div>
				
				
			</div>
		</div>
	</div>


</body>
</html>
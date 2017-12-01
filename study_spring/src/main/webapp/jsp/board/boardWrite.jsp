<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
%>
<jsp:include page="../include/header.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script>

	window.onload = function(){

	    if("${param.seq}" != ""){
			$("#menuTitle").html("게시글 수정 <small>Edit post</small>");
		}
		
		fn_search();
	}

	function fn_search(){
	    var param = {};
	    
	    param["seq"] = "${param.seq}";
	    
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
			$("#TITLE").val(data["brdInfo"]["TITLE"]);
			$("#CONTENTS").html(data["brdInfo"]["CONTENTS"].replace(/<br>/g, "\n"));
	       }
	    });
	    
	 }

	function fn_submit(){
		
		var title = $("#TITLE");
		var contents = $("#CONTENTS");
		
		if(title.val()==""){
			alert("제목을 입력하여 주십시오");
			title.focus();
			return;
		}else if(contents.val()==""){
			alert("내용을 입력하여 주십시오");
			contents.focus();
			return;
		}
		
		var param = {
			"TITLE" : title.val(),
			"CONTENTS" : contents.val().replace(/\n/g, "<br>"),
			"SEQ" : "${param.seq}"
		}
	
		$.ajax({
			url:'<%= path%>/board/write.do',
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
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="container">
			<h2>
				<span id="menuTitle">신규 게시글 작성 <small>New post</small></span>
			</h2>
			<hr>
			<div class="row">
				<div class="col-sm-12">
					<form id="boardWriteForm" class="form-horizontal">
						<div class="form-group">
							<label for="USER_NM" class="control-label col-sm-1">작성자</label>
							<div class="col-sm-11">
								<input type="text" id="USER_NM" class="form-control" name="USER_NM" autofocus="autofocus" placeholder="작성자"
								 value='<%= session.getAttribute("USER_NM") %> (<%=session.getAttribute("USER_ID")%>)' disabled>
							</div>
						</div>
						<div class="form-group">
							<label for="TITLE" class="control-label col-sm-1">제목</label>
							<div class="col-sm-11">
								<input type="text" id="TITLE" class="form-control" name="TITLE" autofocus="autofocus" placeholder="제목">
							</div>
						</div>
						<div class="form-group">
							<label for="CONTENTS" class="control-label col-sm-1">내용</label>
							<div class="col-sm-11">
								<textarea id="CONTENTS" class="form-control" name="CONTENTS" rows="" cols="" style="height: 200px;" placeholder="내용"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-sm-10"></div>
				<div class="col-sm-2">
					<button type="button" class="btn btn-default pull-right" onclick="history.back(-1)">취소</button>
					<button type="button" class="btn btn-primary pull-right" onclick="fn_submit()">저장</button>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
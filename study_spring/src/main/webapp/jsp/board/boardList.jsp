<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*, java.sql.*, java.util.*"%>
<%   String path = request.getContextPath(); %>
<jsp:include page="../include/header.jsp"/>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<style>
	.pagination{
		display:block;
		text-align:center;
	}
	
	.pagination > li > a{
		float : none;
	}
	
	tr, tr > th{
		text-align:center;
	}

</style>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
   var boardObj = {
      blockSize: 10,
      rowSize : 10,      
      totalCount : 0,
      totalPage : 0,
      pageNum : 0,
      page : "<%= request.getParameter("page")==null? 1 : request.getParameter("page")%>",
      type : "<%= request.getParameter("type")==null? "TITLE" : request.getParameter("type")%>",
      keyword : "<%= request.getParameter("keyword")==null? "" : request.getParameter("keyword")%>",
      contents : {}
   }
      
   $(function(){
	   $("#S_TYPE").val(boardObj.type);
	   $("#S_KEYWORD").val(boardObj.keyword);
	   
	   drawPage(boardObj.page);//출력할 페이지가 입력된다
      
   })
   
   function fn_search(){
	   
	  boardObj.keyword = $("#S_KEYWORD").val();
	  boardObj.type = $("#S_TYPE").val();
	   
      var param = {};
      param["BLOCKSIZE"] = boardObj.blockSize;
      param["ROWSIZE"] = boardObj.rowSize;
      param["PAGE"] = boardObj.page;
      param["TYPE"] = boardObj.type;
      param["KEYWORD"] = boardObj.keyword;
      
      $.ajax({
         url:'<%= path%>/board/list.do',
         data: {'param' : JSON.stringify(param)},
         type:'POST',
         contentType:'application/x-www-form-urlencoded; charset=UTF-8',
         dataType:'json',
         async : false,
         error:function(request,status,error){
             //alert("[error code] : "+ request.status + "\n\n[message] :\n\n " + request.responseText + "\n[error msg] :\n " + error); //에러상황
             alert("잘못된 요청입니다"); //에러상황
             location.href = "<%= path%>";
          },
         success:function(data){            
            boardObj.totalCount   = parseInt(data["searchInfo"]["TOTALCOUNT"]);
            boardObj.totalPage   = parseInt(data["searchInfo"]["TOTALPAGE"]);
            boardObj.contents = JSON.parse(JSON.stringify(data["list"]));
         }
      });
   }
   
   function drawContents(){
	  var str = "";
	  var board = boardObj.contents;
	  var totalCount = boardObj.totalCount;
	  var totalPage = boardObj.totalPage;
	  
	  str += "<tr><th style='width:10%'>글번호</th><th style='width:40%'>제목</th><th style='width:15%'>작성자</th>	<th style='width:15%'>작성일</th></tr>";
	
	  if(totalCount == 0){
		str += "<tr><td colspan='4'>조회 결과가 없습니다</td><tr>"
		totalPage = 1;
	  }else{	  
		  for(var i in board){
	         str += "<tr><td>"+board[i].SEQ+"</td>"
	         str += "<td><a href='javascript:goDetail("+board[i].SEQ+");'>"+board[i].TITLE+"</a></td>"
	         str += "<td>"+board[i].REG_NM+"<span><small>("+board[i].REG_ID+")</small></span></td>"
	         str += "<td>"+board[i].REG_DATE+"</td></tr>"
		  }
	  }
      $("#listview").html(str);
       
   }
   
   function drawPage(goTo){
	  boardObj.page = goTo;  
      fn_search();      
      drawContents();
      
      var blockSize = boardObj.blockSize;
      var totalCount   = boardObj.totalCount;
      var totalPage   = boardObj.totalPage;
      var pageNum      = boardObj.pageNum;
      var page      = boardObj.page;
      
      //페이징처리 관련변수      
      var pageGroup = Math.ceil(page/blockSize);/* Math.ceil(page/10); */
      var next = pageGroup*(blockSize)/* pageGroup*10; */
      var prev = next-(blockSize-1)/* next-9; */            
      var goNext = next+1;
        
      if(prev-1<=0){
          var goPrev = 1;
      }else{
          var goPrev = prev-1;
      }
      if(next>totalPage){
          var goNext = totalPage;
          next = totalPage;
      }else{
          var goNext = next+1;
      }
      
      if(pageGroup!=1){
	      var firstStep = "<li><a href='javascript:drawPage(1);'><strong>First</strong></a></li>";
	      var prevStep = " <li><a href='javascript:drawPage("+goPrev+");' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>";
      }
      
  	  if(next < totalPage){
	      var nextStep = " <li><a href='javascript:drawPage("+goNext+");'><span aria-hidden='true'>&raquo;</span></a></li>";
	      var lastStep = " <li><a href='javascript:drawPage("+totalPage+");'><strong>Last</strong></a></li>";
      }
      
      $("#navigator").empty();
      $("#navigator").append(firstStep);
      $("#navigator").append(prevStep);
      for(var i=prev; i<=next;i++){
          if(i == page ){
             pageNum = "<li class='active'><a href='#'><strong>"+i+"</strong></a><li>";   
          }else{
             pageNum = "<li><a href='javascript:drawPage("+i+")'>"+i+"</a></li>";
          }
          $("#navigator").append(pageNum);
      }      
      $("#navigator").append(nextStep);   
      $("#navigator").append(lastStep);
      
 	}    
   function fn_write(){
      location.href="<%=path %>/board/writeForm.do";
   }
   function goDetail(seq){
	   location.href="<%=path %>/board/readForm.do?seq="+seq+"&page="+boardObj.page+"&type="+boardObj.type+"&keyword="+boardObj.keyword;
   }
</script>
</head>
<meta charset="UTF-8">
<title>게시판 메인</title>
<body>

	<div class="container">
		<div class="container">
			<h2>
				게시판 <small>Board</small>
			</h2>
			<hr>
			<div class="row">
				<div class="form-group">
					<div class="col-sm-2">
						<select class="form-control" id="S_TYPE">
							<option value="TITLE">제목</option>
							<option value="NAME">작성자</option>
							<option value="USER_ID">아이디</option>
						</select>
					</div>
					<div class="col-sm-10">
						<div class="input-group">
							<input class="form-control" type="text" id="S_KEYWORD" value=""	onKeydown="javascript:if(event.keyCode == 13) drawPage(1);"	autofocus="autofocus">
							<span class="input-group-btn">
					        	<button class="btn btn-default" type="button" onclick="drawPage(1)"><span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;검색</button>
					      	</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="container">
			<table id="listview" class="table table-striped table-bordered text-center">
				<!-- 게시글 영역 -->
			</table>
			
			<div class="row">
				<div class="form-group">
					<div class="col-sm-10"></div>
					<div class="col-sm-2">
						<button type="button" class="btn btn-primary btn-block"
							onclick="fn_write()">글쓰기</button>
					</div>
				</div>
			</div>
			
			
			<div class="container">
				<div class="col-sm-2"></div>
				<div class="col-sm-8">
					<ul id="navigator" class="pagination">
						<!-- 페이징 영역 -->
					</ul>
				</div>
				<div class="col-sm-2"></div>
			</div>
		</div>
		
		
		
	</div>


</body>
</html>
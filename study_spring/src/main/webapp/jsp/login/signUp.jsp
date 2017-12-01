<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 화면</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<style>
	.login-button{
		margin-top: 5px;
	}
	
	.login-register{
		font-size: 11px;
		text-align: center;
	}
</style>
<script>
	function doJoin(){
		var userId = $("#USER_ID");
		var userPw = $("#USER_PW");
		var userPwAgain = $("#USER_PW_AGAIN");
		var userNm = $("#USER_NM");
		var email = $("#EMAIL");
		
		if(!userId.val()){
			alert("아이디를 입력하여 주십시오");
			userId.focus();
			return;
		}else if(!userNm.val()){
			alert("이름을 입력하여 주십시오");
			userNm.focus();
			return;
		}else if(!userPw.val()){
			alert("비밀번호를 입력하여 주십시오");
			userPw.focus();
			return;
		}else if(!userPwAgain.val()){
			alert("비밀번호 확인란을 입력하여 주십시오");
			userPwAgain.focus();
			return;
		}else  if(!email.val()){
			alert("이메일을 입력하여 주십시오");
			email.focus();
			return;
		}else 
			
		if(userPw.val() != userPwAgain.val()){
			alert("비밀번호와 비밀번호 확인란이 일치하지 않습니다. 확인해주세요");
			userPw.val() = "";
			userPwAgain.val() = "";
			userPw.focus();
			return;
		}
		
		//이메일 유효성 검사 정규식
		var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		if(email.val().match(regExp) == null){
			alert("올바르지 않은 이메일 형식입니다");
			email.val() = "";
			email.focus();
			return;
		}
		
		var param = {
			"USER_ID" : userId.val(),
			"USER_PW" : userPw.val(),
			"USER_NM" : userNm.val(),
			"EMAIL" : email.val()
		}
			
		$.ajax({
			url:'<%= request.getContextPath()%>/sign/signUp.do',
			data: {'param' : JSON.stringify(param)},
			type:'POST',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			dataType:'json',
			error:function(request,status,error){
		    	alert("[error code] : "+ request.status + "\n\n[message] :\n\n " + request.responseText + "\n[error msg] :\n " + error); //에러상황
		    },
			success:function(data){
				if(data['error']){
					alert(data['error']);
					return;
				}
				
				if(data['success']){
					alert(data['success']);
				}
				
				location.href='<%= request.getContextPath()%>/sign/main.do'
			}
		});
	}
	
	function checkDuplicateId(){
		
		if($("#USER_ID").val() == ""){
			return;
		}
		
		$.ajax({
			url:'<%= request.getContextPath()%>/sign/checkId.do',
			data: {'param' : JSON.stringify({
				"USER_ID" : $("#USER_ID").val()
			})},
			type:'POST',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			dataType:'json',
			error:function(request,status,error){
		    	alert("[error code] : "+ request.status + "\n\n[message] :\n\n " + request.responseText + "\n[error msg] :\n " + error); //에러상황
		    },
			success:function(data){
				if(data['error']){
					setAlertMsg($("#USER_ID"),data['error'],"error")
					return;
				}
				if(data['success']){
					setAlertMsg($("#USER_ID"),data['success'],"success")
				}
			}
		});
	}
	
	function checkValidation(targetEle){
		if(!targetEle.val()){
			/* alert("요기를 입력하여 주십시오"); */
			return;
		}
	}
	
	function setAlertMsg(targetInputBox,msg,type){
		
		targetInputBox.parent().parent().removeClass();
		
		if(type=="success"){
			targetInputBox.parent().parent().addClass("form-group has-success has-feedback");
			targetInputBox.next().next().addClass("glyphicon glyphicon-ok form-control-feedback");
		}else{
			targetInputBox.parent().parent().addClass("form-group has-error has-feedback");
			targetInputBox.next().next().addClass("glyphicon glyphicon-remove form-control-feedback");
		}
		
		//glyphicon 세팅
		targetInputBox.next(".glyphicon").removeClass();
		targetInputBox.next(".glyphicon").hide();
		targetInputBox.next(".glyphicon").fadeIn();
		
		if(type=="success"){
			targetInputBox.next(".glyphicon").addClass("glyphicon glyphicon-success form-control-feedback");
		}else{
			targetInputBox.next(".glyphicon").addClass("glyphicon glyphicon-remove form-control-feedback");
		}
		
		
		//help-block 세팅
		targetInputBox.next(".help-block").hide();
		targetInputBox.next(".help-block").text(msg);
		targetInputBox.next(".help-block").fadeIn(); 		
		
		
	}
</script>
</head>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<body>
	<div class="container ">
		<div class="col-sm-3"></div>

		<div class="col-sm-6">
			<div class="row">
				<form id="joinForm" class="form-horizontal" role="form">
					<h2>회원가입<small> Registration</small></h2>
					<hr>
					<div class="form-group"> 
						<label for="USER_ID" class="col-sm-3 control-label">아이디</label>
						<div class="col-sm-9">
							<input type="text" id="USER_ID" placeholder="아이디를 입력하세요" class="form-control" size="8" onblur="checkDuplicateId()" autofocus>
							<span class="help-block" style="display:none;">&nbsp;</span>
							<span class="glyphicon" style="display:none;"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="firstName" class="col-sm-3 control-label">이름</label>
						<div class="col-sm-9">
							<input type="text" id="USER_NM" placeholder="이름을 입력하세요"	class="form-control" size="30" onblur="checkValidation($(this))">
							<span class="help-block" style="display:none;">&nbsp;</span>
							<span class="glyphicon" style="display:none;"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-3 control-label">비밀번호</label>
						<div class="col-sm-9">
							<input type="password" id="USER_PW" placeholder="비밀번호를 입력하세요" class="form-control" size="8" onblur="checkValidation($(this))">
							<span class="help-block" style="display:none;">&nbsp;</span>
							<span class="glyphicon" style="display:none;"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-3 control-label">비밀번호 확인</label>
						<div class="col-sm-9">
							<input type="password" id="USER_PW_AGAIN" placeholder="비밀번호를 한번 더 입력하세요" class="form-control" size="8" onblur="checkValidation($(this))">
							<span class="help-block" style="display:none;">&nbsp;</span>
							<span class="glyphicon" style="display:none;"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="col-sm-3 control-label">이메일</label>
						<div class="col-sm-9">
							<input type="email" id="EMAIL" placeholder="이메일을 입력하세요" class="form-control" onblur="checkValidation($(this))">
							<span class="help-block" style="display:none;">&nbsp;</span>
							<span class="glyphicon" style="display:none;"></span>
						</div>
					</div>
				</form>
				</div>
				
				<div class="row">
					<div class="form-group">
						<button type="button"
							class="btn btn-success btn-lg btn-block login-button"
							onclick="doJoin()">회원가입</button>
					</div>
					<div class="login-register">
						<a href='<%= request.getContextPath()%>/sign/main.do'>로그인</a>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
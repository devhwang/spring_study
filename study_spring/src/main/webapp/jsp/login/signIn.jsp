<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">

.login-button{
	margin-top: 5px;
}

.login-register{
	font-size: 11px;
	text-align: center;
}

</style>
<script>
	function doLogin(){
		
		var userId = $("#USER_ID");
		var userPw = $("#USER_PW");
		
		if(!userId.val()){
			alert("아이디를 입력하여 주십시오");
			userId.focus();
			return;
			
		}else if(!userPw.val()){
			alert("패스워드를 입력하여 주십시오");
			userPw.focus();
			return;
		}
		
		var param = {
			'USER_ID' : userId.val(),
			'USER_PW' : userPw.val()
		}
		
		$.ajax({
			url:'<%= request.getContextPath()%>/sign/signIn.do',
			data: {'param' : JSON.stringify(param)},
			type:'POST',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			dataType:'json',
			error:function(request,status,error){
		    	alert("[error code] : "+ request.status + "\n\n[message] :\n\n " + request.responseText + "\n[error msg] :\n " + error); //에러상황
		    },
			success:function(data){
				if(data['error']){
					fn_setAlert("ERROR",data['error']);
					return;
				}
				
				if(data['success']){
					alert(data['success']);
				}
				
				location.href='<%= request.getContextPath()%>/board/main.do'
			}
		});
	}
	
	function fn_setAlert(alertType,msgStr){
		$("#alertDiv").hide(); 
		$("#alertDiv > strong").text(alertType+": ");//ERROR:
		$("#alertDiv > span").text(msgStr);//아이디나 비밀번호가 일치하지않습니다
		$("#alertDiv").fadeIn(); 
	}
	
</script>

<body>
	<div class="container ">
		<div class="col-sm-4"></div>
		
		<div class="col-sm-4">
			<div class="row">
				<h2>로그인 <small> Sign in</small></h2>
				<hr>
				<form id="loginForm">
					<div class="form-group">
						<input class="form-control" type="text" id="USER_ID" name="USER_ID" size="8" placeholder="아이디를 입력하세요">
					</div>
					<div class="form-group">
						<input class="form-control" type="password" id="USER_PW"  name="USER_PW" size="8" placeholder="암호를 입력하세요">
					</div>
				</form>
				<div id="alertDiv" class="alert alert-danger alert-dismissable fade in" style="display:none;">
				    <strong>ERROR: </strong><span>&nbsp;</span>
				</div>
			</div>
			<div class="row">
				
				<div class="form-group">
					<button type="button" class="btn btn-primary btn-lg btn-block login-button" onclick="doLogin()">로그인</button>
				</div>
				<div class="login-register">
			        <a href='<%= request.getContextPath()%>/sign/form.do'>가입하기</a>
			    </div>
			</div>
		</div>
		<div class="col-sm-4"></div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='js/jquery-3.1.1.min.js'></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<title>GSS System</title>
<style type="text/css">
#backPic {
	position: fixed;
	top: 0;
	z-index: -1;
	opacity: 0.4;
	height: 100%;
	width: 100%;
}
</style>
<script>
	window.onload = function() {
		if ('${error.act ne true}' != null && '${error.act}' != '') {
			alert('${error.act}');
		} else {
			;
		}
		document.getElementById('backPic').removeAttribute('style');
	}
	function ke(event) {
		if (event.which == 13) {
			document.getElementById('login.do').submit();
		}
	}
	function check() {
		if (document.getElementById('account').value == '') {
			alert('帳號未填');
			document.getElementById('account').focus();
		} else if (document.getElementById('pwd').value == '') {
			alert('密碼未填');
			document.getElementById('pwd').focus();
		} else {
			document.getElementById('login.do').submit();
		}
	}
	function imgload(){
		document.getElementById('backPic').removeAttribute('style');
		
	}
</script>
<style>
#backPic{
		position:fixed;
		top:0;
		z-index: -1;
		opacity: 0.2;
		height:100%;
		width: 100%;
	}
#title {
	margin-top: 15%;
}

form {
	margin-top: 2%;
}
.login {
 	position: relative; 
	width: 750px;
	height: 400px;
	border: 1px solid #ccc;
	border-radius: 18px;
	background: rgba(100%, 100%, 100%, 0.8);
 	top:25%; 
 	transform:translateX(50%); 
}
</style>
</head>
<body>
	<img src="images/Travel.jpg" id="backPic" style='display: none;' onload='imgload();'>
	<div class='container-fluid active'>
	<div class="login">
		<div class='row' id='title'>
			<div class='col-lg-4'></div>
			<div class='col-lg-4'>
				<img src="images/titel.png" style="display: inline-block; margin: auto auto 50px 0px;" />
			</div>
			<!-- 			<div class='col-lg-10'></div> -->
		</div>
		<form method="POST" action=<c:url value="/login.do" /> id="login.do"
			role='form' class='form-horizontal form-signin'>
			<div class='form-group'>
				<label for='account' class='control-label col-lg-4'>帳號:</label>
				<div class='col-lg-3'>
					<input type="text" name='account' id='account' value=""
						onkeydown="ke(event);" class='form-control' required autofocus
						placeholder='請輸入帳號' />
				</div>
			</div>
			<div class='form-group'>
				<label for='pwd' class='control-label col-lg-4'>密碼:</label>
				<div class='col-lg-3'>
					<input type="password" name='pwd' id='pwd' class='form-control'
						required placeholder='請輸入密碼' onkeydown="ke(event);" />
				</div>
			</div>
			<div class='form-group'>
				<div class='col-lg-4'></div>
				<div class='col-lg-7'>
					<input id='sub' type="button" value='登入' onclick='check()'
						class='btn btn-primary' /> <input type='reset'
						class='btn btn-primary' />
				</div>
			</div>
		</form>
	</div>
	</div>
</body>
</html>
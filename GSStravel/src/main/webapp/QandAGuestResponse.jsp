<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='js/jquery-3.1.1.min.js'></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<!-- <link rel="stylesheet"  -->
<!-- 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" -->
<!-- 	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" -->
<!-- 	crossorigin="anonymous"> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<script 
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<style>
table {
	color: #7F7F7F;
	font: 0.8em/1.6em "Trebuchet MS", Verdana, sans-serif;
	border-collapse: collapse;
	font-size: 15px;
}

caption, thead th, tfoot th, tfoot td {
	background-color: #1E90FF;
	font-weight: bold;
	text-transform: uppercase
}

thead th {
	background-color: #1E90FF;
	color: white;
	text-align: center
}

tbody tr.odd {
	background-color: #F7F7F7;
	color: #666
}

tbody a {
	padding: 1px 2px;
	color: #333;
	text-decoration: none;
	border-bottom: 1px dotted #E63C1E
}

tbody a:active, tbody a:hover, tbody a:focus, tbody a:visited {
	color: #666
}

tbody tr:hover {
	background-color: #EEE;
	color: #333
}

tbody tr:hover a {
	background-color: #FFF
}

tbody td+td+td+td a {
	color: #C30;
	font-weight: bold;
	border-bottom: 0
}

tbody td+td+td+td a:active, tbody td+td+td+td a:hover, tbody td+td+td+td a:focus,
	tbody td+td+td+td a:visited {
	color: #E63C1E
}

tbody a:visited:after {
	font-family: Verdana, sans-serif;
	content: "\00A0\221A"
}
</style>
<style type="text/css">
#backPic{
		position:fixed;
		top:0;
		z-index: -1;
		opacity: 0.4;
		height:100%;
		width: 100%;
	}
.color-red{
	border-color:red
}

.color-green {
	border-color: green
}

textarea {
	resize: none;
}

td {
border:1px solid #AAAAAA;
	padding: 10px;
}
h1, h2 {
	color: #7744FF;
	font-weight: bolder;
	border-left: 6px solid gray;
	padding-left: 10px;
}
</style>
<title>回應</title>
<style>
</style>
</head>
<body >
<%@include file="SelectBar.jsp"%>
	<script>
		$('.navbar-nav>li').removeClass('now');
		$('.navbar-nav>li:eq(1)').addClass('now');
	</script>
	<div class='container-fluid'>
		<div class='row'>
			<div class='col-md-1'></div>
			<div class='col-md-11'>
				<h2>問題內容</h2>
			</div>
		</div>
		<div class='row'>
			<div class='col-md-offset-3 col-md-5'>
				<h3></h3>
				<table class='table'>
					<thead>
					<tr>
					<th ><label style='text-align: center;'>標題</label></th>
					<td colspan="2"><b style='font-size:20px; font-weight:900;'>${list.question_Title}</b></td>
					</tr>
						<tr>
							<th>員工編號</th>
							<th>提問類型</th>
							<th>發問時間</th>
						</tr>
					</thead>

					<tr>
						<td>${list.question_No}</td>
						<td>
							<c:if test="${list.question_Category==0}">行程</c:if>
							<c:if test="${list.question_Category==1}">費用</c:if>
							<c:if test="${list.question_Category==2}">其他</c:if>	
						</td>
						<td>${list.question_Time}</td>
					</tr>
					<tr>
						<td colspan="3" style='text-align: center;'><textarea cols="70%" rows="8" readonly style='font-size: 20px;background-color: transparent;'>${list.question_Text}</textarea></td>
					</tr>
				</table>
				<form action='<c:url value="/QandAInsertServlet?role=true"/>' method="POST">
					<input type="hidden" name="qa_No" value="${list.qa_No}">
					<input type="hidden" name="answer_No" value="<%=session.getAttribute("emp_No")%>">
					<c:if test="${list.answer_No!=0}">
						<table class='table'>
							<h2>回應</h2>
							<tr>
								<td><textarea name="answer_Text" class="Ans_textarea" cols="70%" rows="8" style='font-size: 20px;background-color: transparent;' readonly="readonly">${list.answer_Text}</textarea></td>
							</tr>
						</table>
					</c:if>
					<br />
				</form>
				<c:if test="${list.question_No==emp_No}">
					<button type="button" class="notdisplaybutton btn btn-danger" onclick="checkdelete()">刪除</button>
				</c:if>
				<button type="button" class='btn btn-primary goback'>回上一頁</button>
			</div>
		</div>
	</div>
	<img src="images/Travel.jpg" id="backPic">
</body>
<script type="text/javascript">
	$(".goback").click(function(){
		window.location = '<c:url value="/QandAServlet?role=false" />';
	});
	if(${Msg!=null}){
		alert("${Msg.message}");
	}
	
	$(".displayclass").hide();
	$(".displaybutton").hide();
	var Ans_textarea;
	function Respose() {
		$(".displayclass").show();
		$(".notdisplayclass").hide();
	}
	function cancelrespose() {
		$(".answer_Text").val('');
		$(".displayclass").hide();
		$(".notdisplayclass").show();
		$(".answer_Text").removeClass("color-green");
		$(".answer_Text").removeClass("color-red");
	}
	function updateData() {
		Ans_textarea = $(".Ans_textarea").val();
		$(".displaybutton").show();
		$(".notdisplaybutton").hide();
		$(".Ans_textarea").prop("readonly", false);
	}
	function cancel() {
		$(".Ans_textarea").val(Ans_textarea);
		$(".displaybutton").hide();
		$(".notdisplaybutton").show();
		$(".Ans_textarea").prop("readonly", "readonly");
		$(".Ans_textarea").removeClass("color-green");
		$(".Ans_textarea").removeClass("color-red");
		
	}
	function checkdelete() {
		if (confirm("確定要刪除?")) {
			window.location = '/GSStravel/QandAInsertServlet?prodaction=deleteOne&qa_No='+ ${list.qa_No}+'&role='+${role};
		}
	}
	$(".answer_Text").blur(function(){
		$(".answer_Text").removeClass("color-green");
		$(".answer_Text").removeClass("color-red");
		if($(".answer_Text").val()==''){
			$(".answer_Text").addClass("color-red");
			$(".displaybb").prop("disabled", true);
		}
		else{
			$(".answer_Text").addClass("color-green");	
			$(".displaybb").prop("disabled", false);
		}		
	});
	$(".Ans_textarea").blur(function(){
		$(".Ans_textarea").removeClass("color-green");
		$(".Ans_textarea").removeClass("color-red");
		if($(".Ans_textarea").val()==''){
			$(".Ans_textarea").addClass("color-red");
			$(".displayaa").prop("disabled", true);
		}
		else{
			$(".Ans_textarea").addClass("color-green");	
			$(".displayaa").prop("disabled", false);
		}		
	});
</script>
</html>
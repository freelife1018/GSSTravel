<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='js/jquery-3.1.1.min.js'></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<!-- <link rel="stylesheet" -->
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
<link rel="stylesheet" type="text/css" href="" />
<title>罰則設定</title>
<style>
#backPic{
		position:fixed;
		top:0;
		z-index: -1;
		opacity: 0.4;
		height:100%;
		width: 100%;
	}
	input {
		text-align: center;
	}
	
	.error {
		color: red;
	}
	
	td {
		border: 2px outset black;
		text-align: center;
		/* 	padding: 0px 0px 0px 0px; */
		font-size: 15px;
	}
	
	table {
		margin-top: 2%;
	}
	
	input[type=text] {
		border: 0px;
	}
</style>
<script>
	$(document).ready(function() {
		$("#add").click(function() {
			$("#fineTable").append("<tr><td class='tdbtn'><input type='button' class='remove btn btn-info ' value='－'/></td><td><input name='day' type='text' autofocus value='${row.fine_Dates}' autocomplete='off' onblur='checkDay(this)' onfocus='focusDay(this)' /></td><td><input name='percent' type='text' value='${row.fine_Per}' autocomplete='off' onblur='checkPercent(this)' onfocus='focusPercent(this)' /></td></tr>");
		});
	
		$(document).on("click", ".remove", function() {
			$(this).parents("tr").remove();
		});
	});
</script>
<script>
	window.onload = function() {
		setFine();
		var $BodyWidth = $(document).width();
		var $ViewportWidth = $(window).width();
		var $ScrollLeft = $(this).scrollLeft();
		if ($BodyWidth > ($ViewportWidth + $ScrollLeft)) {
			$('#span').show();
		} else if ($BodyWidth == ($ViewportWidth + $ScrollLeft)) {
			$('#span').hide();
		}
	}

	var xh = new XMLHttpRequest();
	
	function setFine() {
		if (xh != null) {
			xh.addEventListener("readystatechange", setFineData, false);
			xh.open("GET", "FineServlet?FineSetting=罰則設定", true);
			xh.send();
		} else {
			alert("很抱歉，您的瀏覽器不支援AJAX功能！");
		}
	}

	function setFineData() {
		if (xh.readyState == 4) {
			if (xh.status == 200) {
				var fine = JSON.parse(xh.responseText);
				var body = document.querySelector("#fineTable>tbody");

				while (body.hasChildNodes()) {
					body.removeChild(body.lastChild);
				}

				for (var i = 0; i < fine.length; i++) {
					var tr = document.createElement("tr");
					var td = document.createElement("td");

					var buttonPlus = document.createElement("input");
					buttonPlus.setAttribute("type", "button");
					buttonPlus.setAttribute("class", "remove btn btn-info");
					buttonPlus.setAttribute("value", "－");
					td.setAttribute("class", "tdbtn")
					td.appendChild(buttonPlus);
					tr.appendChild(td);

					td = document.createElement("td");
					var buttonDay = document.createElement("input");
					buttonDay.setAttribute("type", "text");
					buttonDay.setAttribute("name", "day");
					buttonDay.setAttribute("value", fine[i].day);
					buttonDay.setAttribute("style", "border: 2px solid white");
					buttonDay.setAttribute("autocomplete", "off");
					buttonDay.setAttribute("onfocus", "focusDay(this)");
					buttonDay.setAttribute("onblur", "checkDay(this)");
					td.appendChild(buttonDay);
					tr.appendChild(td);

					td = document.createElement("td");
					var buttonPercent = document.createElement("input");
					buttonPercent.setAttribute("type", "text");
					buttonPercent.setAttribute("name", "percent");
					buttonPercent.setAttribute("value", fine[i].percent);
					buttonPercent.setAttribute("style", "border: 2px solid white");
					buttonPercent.setAttribute("autocomplete", "off");
					buttonPercent.setAttribute("onfocus", "focusPercent(this)");
					buttonPercent.setAttribute("onblur", "checkPercent(this)");
					td.appendChild(buttonPercent);
					tr.appendChild(td);

					body.appendChild(tr);
				}
			} else {
				alert("伺服器忙線中！");
			}
		}
	}
	
	function focusDay(day) {
		day.style.border = "";
	}
	
	function focusPercent(percent) {
		percent.style.border = "";
	}
	
	function checkDay(day) {
		var regDay = new RegExp("^[0-9]{1,}$");
		if (day.value == "") {
			day.style.border = "2px solid red";
		} else if (day.value <= 0 || !regDay.test(day.value)) {
			day.style.border = "2px solid red";
		} else {
			day.style.border = "2px solid green";
		}
	}

	function checkPercent(percent) {
		var regDay = new RegExp("^[0-9]{1,}$");
		var regPercent = new RegExp("^([0-9]{1,2})([.]{1})([0-9]{1,})$");
		if (percent.value == "") {
			percent.style.border = "2px solid red";
		} else if (percent.value <= 0 || !regDay.test(percent.value) || percent.value >= 100) {
			if (percent.value <= 0 || !regPercent.test(percent.value) || percent.value >= 100) {
				percent.style.border = "2px solid red";
			}else if (percent.value > 0 && regPercent.test(percent.value) && percent.value < 100) {
				percent.style.border = "2px solid green";
			}
		} else {
			percent.style.border = "2px solid green";
		}
	}

	function check() {
		var pk = 0;
		var step = 0;
		var day = document.getElementsByName("day");
		var percent = document.getElementsByName("percent");
		var regDay = new RegExp("^[0-9]{1,}$");
		var regPercent = new RegExp("^([0-9]{1,2})([.]{1})([0-9]{1,})$");
		for (var i = 0; i < (day.length) - 1; i++) {
			for (var j = i + 1; j < day.length; j++) {
				if (day[i].value == day[j].value) {
					pk = 1;
					step = i;
				}
			}
		}
		if (day.length == 0) {
			$("#FineSave").val("儲存");
			$("#DataForm").submit();
		}
		for (var i = 0; i < day.length; i++) {
			if (day[i].value == "") {
				alert("請輸入取消日！");
				break;
			} else if (percent[i].value == "") {
				alert("請輸入扣款比例！");
				break;
			} else if (day[i].value <= 0 || !regDay.test(day[i].value)) {
				alert("取消日必須為正整數！");
				break;
			} else if (i == step && pk == 1) {
				alert("取消日已存在！");
				break;
			} else if (percent[i].value <= 0 || !regDay.test(percent[i].value) || percent[i].value >= 100) {
				if (percent[i].value <= 0 || !regPercent.test(percent[i].value) || percent[i].value >= 100) {
					alert("扣款比例必須為小於100的正數！");
					break;
				} else if (percent[i].value > 0 && regPercent.test(percent[i].value) && percent[i].value < 100
						&& i == day.length - 1) {
					$("#FineSave").val("儲存");
					$("#DataForm").submit();
				}
			} else if (i == day.length - 1) {
				$("#FineSave").val("儲存");
				$("#DataForm").submit();
			}
		}
	}
</script>
</head>
<body>
	<%@include file="Manage.jsp"%>
	<script>
		$('.navbar-nav>li').removeClass('Mnow');
		$('.navbar-nav>li:eq(5)').addClass('Mnow');
	</script>
	<div class='container-fluid'>
		<div class='row'>
			<div class='col-md-1'></div>
			<div class='col-md-11'>
				<h2>罰則設定</h2>
			</div>
		</div>
		<form id="DataForm" action="<c:url value="/FineServlet" />"
			method="GET">
			<div class='row'>
				<div class='col-md-1'></div>
				<div class='col-md-2'>
					<br> <input type="button" value="罰則明細" name="FineShow"
						onclick="window.location.href=resultjs+'/FineShowServlet'"
						class='btn btn-primary' />
				</div>
				<div class='col-md-5'>
						<input type="button" value="重設" name="FineReset"
							onclick="window.location.href=resultjs+'/FineSetting.jsp'"
							class='btn btn-primary' /> <input type="button" value="儲存"
							onclick="check()" class='btn btn-success' />
					<input type="hidden" id="FineSave" name="FineSave" value="" />
					<table id="fineTable" class='table-responsive'>
						<thead>
							<tr>
								<th class='tdbtn thWH'><input type="button" value="＋"
									id="add" class='btn btn-info' /></th>
								<th class='thWH'><em style="color: red">* </em>取消日<br>
								<input type="text" value="（旅遊前 n 天通知）" readonly /></th>
								<th class='thWH'><em style="color: red">* </em>罰款扣款比例<br>
								<input type="text" value="（1.x ～ 99.x）%" readonly /></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<div class="error">${error.day}</div>
					<div class="error">${error.percent}</div>
					<div class="error">${error.pk}</div>
				</div>
				<div class='col-md-4'></div>
			</div>
		</form>
	</div>
	<img src="images/Travel.jpg" id="backPic">
</body>
</html>
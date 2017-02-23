<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<link rel="stylesheet" type="text/css" href="" />
<title>Announcement Maintain</title>
<style>
table, tr, td {
	border: 1px solid black;
}
.clock {
	border: 1px solid black;
	width: 200px;
	height: 70px;
	text-align: center;
}
</style>
<script>
	window.onload = function() {
		var title = document.getElementById('title');
		var startDay = document.getElementById('startDay');
		var endDay = document.getElementById('endDay');
		setBoard();
		showtime();
	}

	var xh = new XMLHttpRequest();

	function setBoard() {
		if (xh != null) {
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			var result = pathName.substr(0, index + 1);
			var url = result + "/AnnouncementServlet?select=查詢&";
			if (title.value != undefined && title.value != '') {
// 				url = url + "title=" + title.value + "&";
				url = url + "title=" + title.value;
			}
// 			if (startDay.value != undefined && startDay.value != '') {
// 				url = url + "startDay=" + startDay.value + "&";
// 			}
// 			if (endDay.value != undefined && endDay.value != '') {
// 				url = url + "endDay=" + endDay.value;	
// 			}
			xh.addEventListener("readystatechange", setBoardData, false);
			xh.open("GET", url, true);
			xh.send();
		} else {
			alert("很抱歉，您的瀏覽器不支援AJAX功能！");
		}
	}

	function setBoardData() {
		if (xh.readyState == 4) {
			if (xh.status == 200) {
				var board = JSON.parse(xh.responseText);
				var body = document.querySelector("#boardTable>tbody");

				while (body.hasChildNodes()) {
					body.removeChild(body.lastChild);
				}

				for (var i = 0; i < board.length; i++) {
					var tr = document.createElement("tr");
					var td = document.createElement("td");

					td = document.createElement("td");
					td.appendChild(document.createTextNode(board[i].time));
					tr.appendChild(td);

					td = document.createElement("td");
					td.appendChild(document.createTextNode(board[i].title));
					tr.appendChild(td);

					td = document.createElement("td");
					td.appendChild(document.createTextNode(board[i].content));
					tr.appendChild(td);

					body.appendChild(tr);
				}
			} else {
				alert(xh.status + ":" + xh.statusText);
			}
		}
	}

	var now = null;
	var hours = null;
	var minutes = null;
	var seconds = null;
	var timeValue = null;
	var clock = null;
	function showtime() {
		clock = document.getElementById("clock");
		now = new Date();
		hours = now.getHours();
		minutes = now.getMinutes();
		seconds = now.getSeconds();
		timeValue = hours + "：";
		if (minutes < 10) {
			timeValue += " 0" + minutes + "：";
			if (seconds < 10) {
				timeValue += " 0" + seconds;
			} else {
				timeValue += seconds;
			}
		} else {
			timeValue += minutes + "：";
			if (seconds < 10) {
				timeValue += " 0" + seconds;
			} else {
				timeValue += seconds;
			}
		}
		clock.innerHTML = timeValue;
		setTimeout("showtime()", 1000);
	}
</script>
</head>
<body>
	<%@include file="SelectBar.jsp"%>
	<script>
		$('.navbar-nav>li').removeClass('now');
		$('.navbar-nav>li:eq(7)').addClass('now');
	</script>
	<div class='container-fluid'>
		<div class='row'>
			<div class='col-md-1'></div>
			<div class='col-md-11'>
				<h2>公告維護</h2>
			</div>
		</div>
		<form action="<c:url value="/AnnouncementServlet" />" method="GET">
			<div class="clock"><h2 id="clock"></h2></div>
			<div>公告標題</div>
			<input type="text" id="title" name="title" value="" />
			<div id="sizing-addon3">起迄日期:</div>
			<input type='date' id='startDay' name='startDay' value='' /> <br>
			<input type='date' id='endDay' name='endDay' value='' /> <br> <br>
			<input type="button" value="查詢" name="select" onclick="setBoard()" />
			<input type="reset" value="重設" /> <input type="button" value="新增" name="insert" />
		</form>
		<br>
		<table id="boardTable">
			<thead>
				<tr>
					<th>公告時間</th>
					<th>公告標題</th>
					<th>公告內容</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
</html>
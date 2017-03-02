<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Q&A</title>
</head>
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
				<h2>Q&A維護</h2>
			</div>
		</div>
		<br>
		<div class='row'>
			<div class='col-md-offset-1 col-md-10'>
				<div class='row'>
					<div class='col-md-offset-0 col-md-1'>
						<select class="prodaction form-control" style='width: 130px;'>
							<option value="Years" selected>刪除前一年</option>
							<option value="9month">刪除9個月前</option>
							<option value="6month">刪除6個月前</option>
							<option value="3month">刪除3個月前</option>
						</select>
					</div>
					<div class=' col-md-1' style='margin-left: 3px;'>
						<button type="button" class='btn btn-danger'
							onclick="checkdelete()">刪除</button>
					</div>
				</div>
				<%
					String prodaction = request.getParameter("prodaction");
				%>
				<br>
				<select style='width:130px'
					onchange="window.location = '/GSStravel/QandAServlet?prodaction='+this.value;"
					class='form-control'>
					<option value="all"
						<%if ("all".equals(prodaction)) {
				out.print("selected");
			}%>>顯示全部</option>
					<option value="yes"
						<%if ("yes".equals(prodaction)) {
				out.print("selected");
			}%>>顯示已回應</option>
					<option value="no"
						<%if ("no".equals(prodaction)) {
				out.print("selected");
			}%>>顯示未回應</option>
				</select> <br />
				<table border="1" class='table'>
					<thead>
						<tr>
							<th>編號</th>
							<th>行程編號</th>
							<th>標題</th>
							<th>詢問人員</th>
							<th>詢問時間</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${list}">
							<tr>
								<td><input type="text" name="qa_No" value="${list.qa_No}"></td>
								<td><input type="text" value="${list.tra_No}"></td>
								<td><a
									href="/GSStravel/QandASelectServlet?qa_No=${list.qa_No}"><c:if
											test="${list.answer_No!=0}">
											<span>[已回應]</span>
										</c:if> ${list.question_Title} </a></td>
								<td><input type="text" value="${list.question_No}"></td>
								<td><input type="text" value="${list.question_Time}"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				共${count}筆 <br />
				<ul class="pagination">
					<li><a onclick="before()">&laquo;</a></li>
					<li class="page active" onclick="page(this)" value="0"><a>1</a></li>
					<c:if test="${Math.ceil(count/2)!=0}">
						<c:forEach var="i" begin="1" end="${Math.ceil(count/10)-1}">
							<li class="page" onclick="page(this)" value="${i}"><a>${i+1}</a></li>
						</c:forEach>
					</c:if>
					<li><a onclick="next()">&raquo;</a></li>
				</ul>
				<br />
				<c:if test="${emp_Role eq false}">
					<button
						onclick="window.location = '/GSStravel/QandAServlet?prodaction=question';">我要詢問問題</button>
				</c:if>
			</div>
		</div>
	</div>
	<script>
if(${Msg!=null}){
	alert("${Msg.message}");
}
var i;
var $page = $(".page");
$("tr:gt(10)").css("display", "none");
function next() {
	i = $(".active");
	$page.removeClass("active");
	if (i.val() < $page.length - 1) {
		$page[i.val() + 1].className = "active";
		light(i.val() + 1);
	} else {
		$page[0].className = "active";
		light(0);
	}
}
function before() {
	i = $(".active");
	$page.removeClass("active");
	if (i.val() < $page.length && i.val() > 0) {
		$page[i.val() - 1].className = "active";
		light(i.val() - 1);
	} else {
		$page[$page.length - 1].className = "active";
		light($page.length - 1);
	}
}
function page(obj) {
	$page.removeClass("active");
	$(obj).prop("class", "active");
	i = $(".active");
	light(i.val());
}
function light(i) {
	$("tr:gt(0)").css("display", "none");
	$("tr:gt(" + i * 10 + "):lt(" + 10 + ")").css("display", "");
}
function checkdelete() {
	if (confirm("確定要刪除?")){
// 		console.log($(".prodaction").val());
		window.location = '/GSStravel/QandAInsertServlet?prodaction='+$(".prodaction").val();
	}
}
</script>

</body>
</html>

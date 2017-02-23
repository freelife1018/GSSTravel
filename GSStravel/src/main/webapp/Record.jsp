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
<style type="text/css">
table, th, td {
	border: 1px solid black;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<%@include file="SelectBar.jsp"%>
	<script>
		$('.navbar-nav>li').removeClass('now');
		$('.navbar-nav>li:eq(1)').addClass('now');
	</script>
	<div class='container-fluid'>
		<div class='row'>
			<div class='col-lg-1'></div>
			<div class='col-lg-11'>
				<h2>歷史紀錄</h2>
			</div>
		</div>
		<div class='row'>
			<div class='col-md-offset-2 col-md-8'>
				<c:if test="${counts!=0}">
					<table class='table'>
						<thead>
							<tr>
								<th>行程編號</th>
								<th>行程名稱</th>
								<th>行程開始日</th>
								<th>行程結束日</th>
								<th>登記日</th>
								<th>取消日</th>
								<th>狀態</th>
								<th>費用</th>
							</tr>
						</thead>
						<c:forEach var="row" items="${record}">
							<tr>
								<td><a href="<c:url value="/FeeTravel?tra_No=${row[0]}" />">${row[0]}</a></td>
								<td>${row[1]}</td>
								<td>${row[2]}</td>
								<td>${row[3]}</td>
								<td>${row[4]}</td>
								<td>${row[5]}</td>
								<td>${row[6]}</td>
								<td>${row[7]}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${counts==0}">
					<h1 style='font-size: 60px;'>目前沒有任何歷史訊息</h1>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
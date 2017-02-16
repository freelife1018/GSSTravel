﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>罰則一覽表</title>
<style>
td, input, table {
	border: 1px solid black;
	text-align: center;
}

.a {
	text-decoration: none;
	color: black;
}

</style>


</head>
<body>
	<div class='container-fluid'>
		<h1>行程內容</h1>
		<button>
			<a href="<c:url value="/FeeTravel?tra_No=${traveResult.tra_NO}"/>"
				class="a">行程</a>
		</button>
		<button>
			<a
				href="<c:url value="/FineShowOneServlet?tra_No=${traveResult.tra_NO}"/>"
				class="a">罰則</a>
		</button>
		<form action="<c:url value="/FineShowOneServlet" />" method="GET">
		<c:if test="${countI+1 ne 0 && countJ+1 ne 0}">
			<table id="resultTable">
				<tr>
					<td>行程 ＼ 罰則</td>
					<c:forEach var="i" varStatus="statusI" begin="0" end="${countI}">
						<c:if test="${statusI.count==1}">
							<td>旅遊前${fSelect[i].fine_Dates}天通知<br>扣款總費用 * ${fSelect[i].fine_Per}%</td>
						</c:if>
						<c:if test="${statusI.count>1}">
							<td>旅遊前${fSelect[i].fine_Dates} ～ ${fSelect[i-1].fine_Dates-1}天通知<br>扣款總費用 * ${fSelect[i].fine_Per}%</td>
						</c:if>
					</c:forEach>
					<td>旅遊開始日<br>扣款總費用 * 100%</td>
				</tr>
				<tr>
					<td>${tSelect[0].tra_Name}<br>${totalDays[0][countI+1]}</td>
						<c:forEach var="j" varStatus="statusJ" begin="0" end="${countI}">
							<c:if test="${statusJ.count==1}">
								<td>報名截止日 ～ ${totalDays[0][j]}<br><fmt:formatNumber value="${iSelect[0].item_Money}" groupingUsed="true" type="currency" maxFractionDigits="0"/> * ${fSelect[j].fine_Per}% ＝ <fmt:formatNumber value="${iSelect[0].item_Money*fSelect[j].fine_Per/100}" groupingUsed="true" type="currency" maxFractionDigits="0"/></td>
							</c:if>
							<c:if test="${statusJ.count!=1}">
								<td>${afterDay[0][j-1]} ～ <fmt:formatNumber value="${totalDays[0][j]}" groupingUsed="true" type="currency" maxFractionDigits="0"/><br><fmt:formatNumber value="${iSelect[0].item_Money}" groupingUsed="true" type="currency" maxFractionDigits="0"/> * ${fSelect[j].fine_Per}% ＝ <fmt:formatNumber value="${iSelect[0].item_Money*fSelect[j].fine_Per/100}" groupingUsed="true" type="currency" maxFractionDigits="0"/></td>
							</c:if>
						</c:forEach>
					<td>${totalDays[0][countI+1]}<br><fmt:formatNumber value="${iSelect[0].item_Money}" groupingUsed="true" type="currency" maxFractionDigits="0"/></td>
				</tr>
			</table>
		</c:if>
		<c:if test="${countI+1 eq 0 && countJ+1 eq 0}">
				<h1>目前尚無行程或罰則資訊！</h1>
		</c:if>
	</form>
		<button>
			<a href="<c:url value="/AllTravel"></c:url>" class="a">回到報名/查詢</a>
		</button>
		<button>
			<a
				href="<c:url value="/Login_Information?tra_No=${traveResult.tra_NO}&emp_No=${emp_No}" ></c:url>"
				class="a">報名</a>
		</button>
	</div>
</body>
</html>
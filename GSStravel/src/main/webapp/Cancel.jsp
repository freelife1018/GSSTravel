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
#backPic{
		position:fixed;
		top:0;
		z-index: -1;
		opacity: 0.4;
		height:100%;
		width: 100%;
	}
.a {
	text-decoration: none;
	color: white;
}

.col-lg-2 {
	text-align: center;
}

.container-fluid {
	margin-top: 2%;
}

.table1, .table2 {
	font-size: 17px;
}

.table-bordered {
	text-align: center;
}

.form-control {
	font-size: 15px;
	background-color: white;
}

label {
	text-align: left;
}
.iii {
	text-align: left;
}
</style>
<title>報名取消</title>
<script type="text/javascript">
	function confirmComplete() {
		var answer = confirm("是否確定要取消???");
		if (answer == true) {
			document.getElementById("img").style.display = 'inline';
			document.getElementById("ckback").style.display = 'none';
			document.getElementById("back").innerHTML = "<input type='button' class='btn btn-primary' value='回上一頁' onclick='return false;'/>";
 			document.getElementById("ckCancel").style.display = 'none';
			wait();
			return true;
		}else{
			return false;
		}
	}
	var x = 1;
	function wait() {
		if (x == 1) {
			document.getElementById("ck").innerHTML = "<input type='button' class='btn btn-danger' value='取消報名中.'/>";
			x++;
		} else if (x == 2) {
			document.getElementById("ck").innerHTML = "<input type='button' class='btn btn-danger' value='取消報名中..'/>";
			x++;
		} else if (x == 3) {
			document.getElementById("ck").innerHTML = "<input type='button' class='btn btn-danger' value='取消報名中...'/>";
			x = 1;
		}
		setTimeout('wait()', 1000);
	}
</script>
</head>
<body>
	<div class='container-fluid'>
		<form action="<c:url value="Cancel"/>" method="get">
			<div class='row'>
				<div class='col-md-1'></div>
				<div class='col-md-4'>
					<div class='panel panel-primary'>
						<div class='panel-heading'>
							<h1>-本團報名資訊-</h1>
						</div>
						<div class='panel-body'>
							<table class='table table-condensed table1'>
								<tr>
									<td>活動名稱:</td>
									<td><input type="hidden" value="${tra_Vo.tra_Name}"
										name="tra_Name" readonly><label
										style="border-style: none; color: #7700BB;">${tra_Vo.tra_Name}</label></td>
								<tr>
									<td>活動代碼:</td>
									<td><input type="hidden" value="${tra_Vo.tra_NO}"
										name="tra_No" readonly><label
										style="border-style: none; color: #7700BB;">${tra_Vo.tra_NO}</label>
									</td>
								</tr>
								<tr>
									<td colspan="2">目前已報名人數為 <strong>${tra_count}</strong>人，依報名順序為:
									</td>
								</tr>
								<c:forEach var="names" items="${name}">
									<c:set var="nameKey" value="${names}" />
									<tr>
										<td colspan="2">${names}(共${mp[nameKey]}人)</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
				<div class='col-md-1'></div>
				<div class='col-md-4'>
					<div class='panel panel-primary'>
						<div class='panel-heading'>
							<h1>-報名人員-</h1>
						</div>
						<div class='panel-body'>
							<table class='table table-condensed table2'>
								<tr>
									<td>員編:</td>
									<td><input type="hidden" value="${emp_No}" name="emp_No"
										readonly><label
										style="border-style: none; color: #7700BB; width:150px;">${emp_No}</label>
									</td>
								</tr>
								<tr>
									<td>姓名:</td>
									<td><input type="hidden" value="${myName}" name="emp_No"
										readonly><label
										style="border-style: none; color: #7700BB;">${myName}</label>
									</td>
								</tr>
								<tr>
									<td>報名順序:</td>
									<td><input type="hidden" value="第${tra_order}名"
										name="emp_No" readonly><label
										style="border-style: none; color: #7700BB;">第${tra_order}位</label></td>
								</tr>
								<tr>
									<td colspan="2"><c:if test="${familySize>0}">
											<table class='table table-bordered'>
												<tr>
													<th></th>
													<th style="text-align: center;">眷屬/親友</th>
													<th style="text-align: center;">姓名</th>
												</tr>
												<c:forEach var="row" items="${familyVO}">
													<tr>
														<td><input type=checkbox value="${row.fam_Name}"
															onclick="return false" ${row.checked} name="fam"></td>
														<td>${row.fam_Rel}</td>
														<td>${row.fam_Name}</td>
													</tr>
												</c:forEach>
											</table>
										</c:if> <br> <c:if test="${tra_Vo.tra_On!=tra_Vo.tra_Off}">

											<table class='table table-bordered'>
												<tr>
													<th></th>
													<th>房型</th>
													<th>費用</th>
												</tr>
												<c:forEach var="room" items="${itemVo}">
													<tr>
														<td><input type=checkbox value="${room.item_Money} "
															onclick="return false" name="room"></td>
														<td>${room.item_Name}</td>
														<td>${room.item_Money}</td>
													</tr>
												</c:forEach>
											</table>

											<br>
											<br>
										</c:if></td>
								</tr>
								<tr>
									<td>團費試算:</td>
								</tr>
								<tr>
									<td>年度可使用補助金額:</td>
									<td><strong>${detail[0]}</strong></td>
								</tr>
								<tr>
									<td>個人團費:</td>
									<td><strong>${detail[1]}</strong></td>
								</tr>
								<tr>
									<td>個人補助金:</td>
									<td><strong>${detail[0]}</strong></td>
								</tr>
								<tr>
									<td>補團費:</td>
									<td><strong>${detail[2]}</strong></td>
								</tr>
								<tr>
									<td colspan="2" style="color: red">PS:團費試算僅供參考，需繳納費用以福委會通知為主</td>
									<script>
										var GSS = '<c:url value="/AllTravel" />'
									</script>
								<tr>
									<td><br> <input type="button" value='回上一頁'
										class='btn  btn-primary' onclick="window.location.href=GSS;" id="ckback" />
										<span id="back"></span></td>
									<c:if test="${a==1}">
									<td><br> <input type="submit" value="取消報名"
										class='btn  btn-danger' id="ckCancel"
										onclick="return confirmComplete();" /><span
										id="ck"></span><span>
									</c:if>
										<img src="images/redLoading.gif" id="img" style="display: none" /></span></td>
								</tr>
							</table>
						</div>
					</div>

				</div>
			</div>
		</form>
	</div>
	<img src="images/Travel.jpg" id="backPic">
</body>

</html>
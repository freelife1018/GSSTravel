<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='js/jquery-3.1.1.min.js'></script>

<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<title>旅費統計</title>
</head>

<style>
#backPic {
	position: fixed;
	top: 0;
	z-index: -1;
	opacity: 0.4;
	height: 100%;
	width: 100%;
}

tr, input {
	text-align: center;
	width: 100px;
}

textarea {
	text-align: center;
	border: 0;
	resize: none;
	font-size: 20px;
	width: 400px;
	overflow-y: visible;
	outline: none;
}

td {
	padding: 5px;
	text-align: center;
	vertical-align: middle;
	
}

table {
	font-size: 15px;
}

.color-red {
	border-color: red
}

.color-green {
	border-color: green
}

input[type='text'] {
	background-color: transparent;
	margin: auto;
}

input[name="det_note"],input[name="det_noteMoney"] {
	background-color: white;
}
</style>

<body>
	<%@include file="Manage.jsp"%>
	<script>
		$('.navbar-nav>li').removeClass('Mnow');
		$('.navbar-nav>li:eq(6)').addClass('Mnow');
	</script>
	<div class='container-fluid'>
		<div class='row'>
			<div class='col-md-1'></div>
			<div class='col-md-11'>
				<h2>旅費統計</h2>
				<br>
				<h4>活動代碼：${tra_No}</h4>
			</div>
		</div>
		<div class='row'>
			<div class='col-md-10 col-md-offset-1'>
				<form action="<c:url value='/TotalAmountServlet' />" method="POST">
					<div></div>
					<input type="hidden" name="tra_No" class="tra_No" value="${tra_No}">
					<input type="hidden" name="tra_Name" class="tra_Name"
						value="${tra_Name}">

					<ul class="pagination">
						<li class="active pagenumber" onclick="changepage(this)"
							value="10"><a role='button'>每頁10筆</a></li>
						<li class="pagenumber" onclick="changepage(this)" value="20"><a
							role='button'>每頁20筆</a></li>
						<li class="pagenumber" onclick="changepage(this)" value="50"><a
							role='button'>每頁50筆</a></li>
						<li class="pagenumber" onclick="changepage(this)" value="100"><a
							role='button'>每頁100筆</a></li>
					</ul>

					<table border="1" class="table-bordereds" id="table">
						<thead>
							<tr>
								<th><label style='width:80px;'>部門代碼</label></th>
								<th><label style='width:120px;'>員工<br>(/眷屬親友)編號</label></th>
								<th><label style='width:130px;'>姓名<br>(/隸屬於哪位員工)</label></th>
								<th><label style='width:250px;vertical-align: bottom;'>年度可補助金額<br /> <span
									style="color: #FFC0CB; font-size: 10px">未到職一年者按比例給予計算</span></label></th>
								<th><label style='width:130px;'>個人可補助金額</label></th>
								<th><label style='width:80px;'>個人團費</label></th>
								<th><label style='width:120px;'>其他增減費用<br>明細說明</label></th>
								<th><label style='width:100px;'>其他增減費用<br>總額</label></th>
								<th><label style='width:80px;'>應補團費</label></th>
							</tr>
						</thead>
						<c:forEach var="list" items="${list}">
							<tr>
								<td><input type="text" name="dept_No" class="dept_No"
									value="${list.dept_No}" style="border-width: 0" readonly></td>
								<td><c:if test="${list.fam_No==0}">
										<input type="text" name="emp_No" class="emp No"
											value="${list.emp_No}" style="border-width: 0" readonly>
										<input type="hidden" name="empfam" value="${list.emp_No}">
									</c:if> <c:if test="${list.fam_No!=0}">
										<input type="text" name="empfam" class="fam No"
											value="${list.emp_No}/${list.fam_No}" style="border-width: 0"
											readonly>
									</c:if></td>
								<td><c:if test="${list.fam_Name==NULL}">
										<input type="text" class="Name" value="${list.emp_Name}"
											style="border-width: 0" readonly>
									</c:if> <c:if test="${list.fam_Name!=NULL}">
										<input type="hidden" class="Name"
											value="${list.fam_Name}/${list.emp_Name}"
											style="border-width: 0" readonly>
											<p>${list.fam_Name}<br>(${list.emp_Name})</p>
									</c:if></td>
								<c:if test="${list.fam_Name==NULL}">
									<td><input type="text" class="years_money years_Money"
										value="${list.years_money}"
										style="border-width: 0; text-align: right;width:220px;" readonly></td>
									<td><input type="text" class="person_money person_Money"
										value="" style="border-width: 0; text-align: right;" readonly>
										<input type="hidden" class="person" value="${list.emp_No}"></td>
								</c:if>
								<c:if test="${list.fam_Name!=NULL}">
									<td><input type="text" class="years_Money" value="0.0"
										style="border-width: 0; text-align: right;width:220px;" readonly></td>
									<td><c:if test="${list.fam_sub}">
											<input type="text" class="personfam_money person_Money"
												style="border-width: 0; text-align: right;" value="0.0"
												readonly>
											<input type="hidden" class="person" value="${list.emp_No}">
										</c:if> <c:if test="${!list.fam_sub}">
											<input type="text" class="onmoney person_Money" value="0.0"
												style="border-width: 0; text-align: right;" readonly>
										</c:if></td>
								</c:if>
								<td><input type="text" name="money" class="money Money"
									value="${list.det_money}"
									style="border-width: 0; text-align: right;" readonly></td>
								<td><input type="text" name="det_note"
									class="det_note det_Note form-control" value="${list.det_note}"></td>
								<td><input type="text" name="det_noteMoney"
									style='text-align: right; width: 150px'
									class="det_noteMoney det_NoteMoney form-control"
									value="${list.det_noteMoney}" onkeyup="changeNotemoney()"
									onblur="checkmoney(this)"></td>
								<td><c:if test="${list.fam_No==0}">
										<input type="text" name="TA_money" class="TA_money ta_Money"
											value="" style="border-width: 0; text-align: right;" readonly>
									</c:if> <c:if test="${list.fam_No!=0}">
										<input type="hidden" class="ta_Money" value="0">
									</c:if></td>
							</tr>
						</c:forEach>
					</table>
					共${count}筆 <br />
					<div id='mydiv'>
						<ul id="myul" class="pagination">
							<li><a role='button' onclick="before()">&laquo;</a></li>
							<li class="page active" onclick="page(this)" value="0"><a
								role='button'>1</a></li>
							<c:if test="${Math.ceil(count/10)!=0}">
								<c:forEach var="i" begin="1" end="${Math.ceil(count/10)-1}">
									<li class="page" onclick="page(this)" value="${i}"><a
										role='button'>${i+1}</a></li>
								</c:forEach>
							</c:if>
							<li><a role='button' onclick="next()">&raquo;</a></li>
						</ul>
						<br />
						<c:if test="${travelVO!=null}">
							<button type="submit" name="prodaction" value="save"
								class='btn btn-success'>儲存</button>
						</c:if>
						<input type="button" onclick="excel()" value="匯出Excel"
							class='btn btn-primary'>
					</div>
				</form>
				<c:if test="${session!=null}">
					<script>
						alert('${session}');
					</script>
				</c:if>
			</div>
		</div>
	</div>
	<img src="images/Travel.jpg" id="backPic">
</body>
<script>
	var $money = $(".money"); //個人團費
	var $emp = $(".emp"); //emp
	var $fam = $(".fam"); //fam
	var $TAmoney = $(".TA_money"); //應補團費
	var $noteMoney = $(".det_noteMoney"); //減免費用
	var $yearsmoney = $(".years_money");
	var $personemp = $(".person");
	var $personmoney = $(".person_money"); //會員個人可補助金額	
	var a = 0;
	var b = 0;
	$().ready(function() {
		var $BodyWidth = $(document).width();
		var $ViewportWidth = $(window).width();
		var $ScrollLeft = $(this).scrollLeft();
		if ($BodyWidth > ($ViewportWidth + $ScrollLeft)) {
			$('#span').show();
		} else if ($BodyWidth == ($ViewportWidth + $ScrollLeft)) {
			$('#span').hide();
		}
		$.each($personmoney, function(i, value) {
			var sum = Number(0);
			$.each($personemp, function(k, value) {
				if ($emp[i].value == $personemp[k].value) {
					sum = sum + Number($money[a].value);
					a++;
				}
			});
			if ($yearsmoney[i].value <= sum) {
				sum = $yearsmoney[i].value;
			}
			$personmoney[i].value = sum;
		});
		changeNotemoney();
		
		var i;
		$("tr:gt(10)").css("display", "none");
		
	});
	function changeNotemoney() {
		a = 0;
		$.each($emp, function(keys, emp) {
			var sum = Number(0);
			sum = Number($money[keys].value) + Number($noteMoney[a].value)
					- Number($personmoney[keys].value);
			a++;
			$.each($fam, function(key, fam) {
				if ($fam[key].value.split("/")[0] == $emp[keys].value) {
					sum = sum + Number($money[a].value)
							+ Number($noteMoney[a].value);
					a++;
				}
			});
			if (sum <= 0) {
				sum = 0;
			}
			$TAmoney[keys].value = sum;
		});
	}
	function excel() {
		var $tra_No = $(".tra_No");
		var $tra_Name = $(".tra_Name");
		var $dept_No = $(".dept_No");
		var $No = $(".No");
		var $Name = $(".Name");
		var $years_Money = $(".years_Money");
		var $person_Money = $(".person_Money");
		var $Money = $(".Money");
		var $det_Note = $(".det_Note");
		var $det_NoteMoney = $(".det_NoteMoney");
		var $ta_Money = $(".ta_Money");

		var tra_No = $tra_No.val();
		var tra_Name = $tra_Name.val();

		var dept_No = new Array();
		var No = new Array();
		var Name = new Array();
		var years_Money = new Array();
		var person_Money = new Array();
		var Money = new Array();
		var det_Note = new Array();
		var det_NoteMoney = new Array();
		var ta_Money = new Array();

		for (var i = 0; i < $dept_No.length; i++) {
			dept_No[i] = $dept_No[i].value;
			No[i] = $No[i].value;
			Name[i] = $Name[i].value;
			years_Money[i] = $years_Money[i].value;
			person_Money[i] = $person_Money[i].value;
			Money[i] = $Money[i].value;
			if (!$det_Note[i]) {
				det_Note[i] = "";
			} else {
				det_Note[i] = $det_Note[i].value;
			}
			if (!$det_NoteMoney[i]) {
				det_NoteMoney[i] = 0;
			} else {
				det_NoteMoney[i] = $det_NoteMoney[i].value;
			}
			ta_Money[i] = $ta_Money[i].value;
		}
		$.ajax({
			type : "POST",
			url : "/GSStravel/TotalAmountServlet",
			data : {
				tra_No : tra_No,
				tra_Name : tra_Name,
				prodaction : "Excel",
				dept_No : dept_No,
				No : No,
				Name : Name,
				years_Money : years_Money,
				person_Money : person_Money,
				Money : Money,
				det_Note : det_Note,
				det_NoteMoney : det_NoteMoney,
				ta_Money : ta_Money
			},
			success:function(){
				window.location = "/GSStravel/File_Detail_Money.jsp?tra_No="+tra_No+"&tra_Name="+tra_Name;
			},
		})
	}
	
	var $page = $(".page");
	var $pagenumber=$(".pagenumber");
	
	
	function next() {
		i = $(".active");
		$page.removeClass("active");
		if (i[1].value < $page.length - 1) {
			$page[i[1].value + 1].className = "active";
			light(i[0].value,i[1].value + 1);
		} else {
			$page[0].className = "active";
			light(i[0].value,0);
		}
	}
	function before() {
		i = $(".active");
		$page.removeClass("active");
		if (i[1].value < $page.length && i[1].value > 0) {
			$page[i[1].value - 1].className = "active";
			light(i[0].value , i[1].value - 1);
		} else {
			$page[$page.length - 1].className = "active";
			light(i[0].value , $page.length - 1);
		}
	}
	function page(obj) {
		$page.removeClass("active");
		$(obj).prop("class", "active");
		i = $(".active");
		light(i[0].value,i[1].value);
	}
	function light(page,i) {
		$("tr:gt(0)").css("display", "none");
		$("tr:gt(" + i * page + "):lt(" + page +")").css("display", "");
	}
	function changepage(obj){
		$pagenumber.removeClass("active");
		$(obj).prop("class", "active");
		i = $(".active");
		var count=${count};
		$("#myul").find("li").remove();
		$("#myul").append('<li><a role="button" onclick="before()">&laquo;</a></li>');
			var sum=Math.ceil(count/i[0].value);
				for(var a=0;a<sum;a++){
					if(a==0){
						$("#myul").append('<li class="page active" onclick="page(this)" value="'+a+'"><a role="button">'+(a+1)+'</a></li>');	
					}else{
					$("#myul").append('<li class="page" onclick="page(this)" value="'+a+'"><a  role="button">'+(a+1)+'</a></li>');
					}
				}
		$("#myul").append('<li><a role="button" onclick="next()">&raquo;</a></li>');
		i = $(".active");
		$page = $(".page");
		light(i[0].value,i[1].value);
	}
	function checkmoney(obj){
		if(isNaN(obj.value)){
			$(obj).addClass("color-red");
			$(obj).removeClass("color-green");
		}
		else{
			$(obj).addClass("color-green");	
			$(obj).removeClass("color-red");
		}
		
	}
</script>
</html>
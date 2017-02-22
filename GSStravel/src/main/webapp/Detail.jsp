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
<title>報名明細</title>
<style>
td,th{
padding: 10px;
border:1px solid #DDDDDD;
}
table{
margin-right: 3%;
}
</style>
</head>
<body>
	<%@include file="SelectBar.jsp"%>
	<script>
			$('.navbar-nav>li').removeClass('now');
			$('.navbar-nav>li:eq(4)').addClass('now');
		</script>
	<div class='container-fluid'>
		<div class='row'>
			<div class='col-md-2 col-md-offset-1'>
				<h2>報名明細</h2>
			</div>
		</div>
		<div class='row'>
		<div class='col-md-1'></div>
			<div class='col-md-11'>
				<form id="myForm" action=<c:url value="/detail"/> method="post">
					<p>活動代碼：${param.tra_no}</p>
					<input type="hidden" name="tra_no" id="tra_no" value="${param.tra_no}">
						<select class="selectTable" >
								<option>顯示全部</option>
								<option>尚未取消</option>
								<option>已取消</option>
						</select>

					<table id="deailtable">
						<tr>
							<th></th>
							<th>員工編號</th>
							<th>身份</th>
							<th>姓名</th>
							<th>性別</th>
							<th>身份證字號</th>
							<th>生日</th>
							<th>手機</th>
							<th>用餐/車位</th>
							<th>特殊身份</th>
							<th>保險受益人</th>
							<th>與受益人關係</th>
							<th>緊急聯絡人</th>
							<th>緊急聯絡人手機</th>
							<th>報名時間</th>
							<th>取消日期</th>
							<th>備註</th>
							<th>取消原因</th>
						</tr>

						<c:if test="${not empty select}">
							<c:forEach var="row" items="${select}">
								<tr>
									<td><c:if test="${empty row.det_CanDate}">
											<c:if test="${traVO!=null}">
												<button name="cancel" id="cancel" type="button" class='cancel btn btn-success' value="${row.det_No}" onclick="open_Can(this)">取消</button>
												<button name="edit" class="detEdit btn btn-success" id="detEdit" type="button" value="${row.det_No}">編輯</button>
											</c:if>
											<button name="prodaction" class="save btn btn-success"type="submit" id='save' value="save" style='display: none;'>儲存</button>
										</c:if></td>
									<td>${row.emp_No}<input type="text"
										class="emp_No form-control" name="emp_No"
										value="${row.emp_No}" disabled style="display: none"><input
										type="text" class="fam_No form-control" name="fam_No"
										value="${row.fam_No}" disabled style="display: none"></td>
									<td><input type="text" name="trel" value="${row.rel}"
										class='form-control' style="display: none"> <c:if
											test="${row.rel == '員工'}">
		     ${row.rel}
		      <select style="display: none; width:90px;" class="fam_Rel form-control"
												name="fam_Rel" disabled>
												<option>員工</option>
												<option>眷屬</option>
												<option>親友</option>
											</select>
										</c:if> <c:if test="${row.rel != '員工'}">
											<select name="fam_Rel" disabled class='form-control' style="width:90px;">
												<option>眷屬</option>
												<option>親友</option>
											</select>
										</c:if></td>
									<td><input type="text" class="name form-control"  style="width:90px;"
										name="name" value="${row.name}" disabled></td>
									<td><input type="text" name="tsex" value="${row.sex}" 
										class='form-control' style="display:none;width:70px;">
										 <select style="width:70px;" name="sex" class="sex form-control" disabled>
											<option>男</option>
											<option>女</option>
									</select></td>
									<td><input type="text" name="ID" class="TWID form-control"  style='width:110px;'
										value="${row.ID}" disabled></td>
									<td><input type="date" name="Bdate" style='width:150px;'
										class="Bdate form-control" value="${row.bdate}" disabled></td>
									<td><input type="text" name="Phone" style='width:105px;'
										class="Phone form-control" value="${row.phone}" disabled></td>
									<td><input type="text" name="teat" value="${row.eat}"
										class='form-control' style="display: none"> <select
										name="eat" disabled class='form-control' style='width:100px;'>
											<option>葷</option>
											<option>素</option>
											<option>不佔餐</option>
									</select> <c:if test="${row.rel != '員工'}">
											<c:if test="${row.car == false}">
												<input type="checkbox" name="car" Checked disabled>不佔車位
				        </c:if>
											<c:if test="${row.car}">
												<input type="checkbox" name="car" disabled>不佔車位
				        </c:if>
										</c:if></td>
									<td><c:if test="${row.rel != '員工'}">
											<p style='width:130px;'>
												<c:if test="${row.fam_Bady == true}">
									幼童(0~3歲)<br>
								</c:if>
												<c:if test="${row.fam_kid == true}">
									兒童(4~11歲)<br>
								</c:if>
												<c:if test="${row.fam_Dis == true}">
									持身心障礙手冊<br>
								</c:if>
												<c:if test="${row.fam_Mom == true}">
									孕婦(媽媽手冊)
								</c:if>
											</p>
											<select class="multiselect form-control select" name="spe"
												multiple="multiple" data-placeholder="請選擇"
												style="width: 210px;" disabled>
												<c:if test="${row.fam_Bady == false}">
													<option>幼童(0~3歲)</option>
												</c:if>
												<c:if test="${row.fam_Bady}">
													<option Selected>幼童(0~3歲)</option>
												</c:if>
												<c:if test="${row.fam_kid == false}">
													<option>兒童(4~11歲)</option>
												</c:if>
												<c:if test="${row.fam_kid}">
													<option Selected>兒童(4~11歲)</option>
												</c:if>
												<c:if test="${row.fam_Dis == false}">
													<option>持身心障礙手冊</option>
												</c:if>
												<c:if test="${row.fam_Dis}">
													<option Selected>持身心障礙手冊</option>
												</c:if>
												<c:if test="${row.fam_Mom == false}">
													<option>孕婦(媽媽手冊)</option>
												</c:if>
												<c:if test="${row.fam_Mom}">
													<option Selected>孕婦(媽媽手冊)</option>
												</c:if>
											</select>
											<input type="hidden" class="text_multiselect"
												name="text_multiselect" disabled>
										</c:if></td>
									<td><input type="text" class="ben form-control" name="ben" style='width:90px;'
										value="${row.ben}" disabled></td>
									<td><input type="text" class="ben_Rel form-control" style='width:80px;'
										name="ben_Rel" value="${row.benRel}" disabled></td>
									<td><input type="text" class="emg form-control" name="emg" style='width:90px;'
										value="${row.emg}" disabled></td>
									<td><input type="text" name="emg_Phone" style='width:110px;'
										class="emg_Phone form-control" value="${row.emgPhone}"
										disabled></td>
									<td ><p style='width:110px;'>${row.det_Date}</p></td>
									<td ><em style='width:110px;'>${row.det_CanDate}</em></td>
									<td><input type="text" name="note" value="${row.note}" style='width:150px;'
										class='form-control' disabled></td>
									<td><p style='width:200px;'>${row.det_canNote}</p></td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					共 ${Count} 筆
					<br />	
					<ul class="pagination" id="pagination">
						<li><a onclick="before()">&laquo;</a></li>
						<li class="page active" onclick="page(this)" value="0"><a>1</a></li>
					</ul>
					<br />
					<input type="hidden" name="detailPage" class="detailPage">
					<button type="submit" name="prodaction" value="insert" class='btn btn-primary'>新增</button>
					<!-- 柯(請勿刪除) -->
					<input type="submit" name="excel" value="匯出Excel" class='btn btn-primary' />
					<!-- 柯(請勿刪除) -->
				</form>
			</div>
		</div>
		<link rel="stylesheet"
			href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.common-material.min.css" />
		<link rel="stylesheet"
			href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.min.css" />
		<link rel="stylesheet"
			href="https://kendo.cdn.telerik.com/2017.1.118/styles/kendo.material.mobile.min.css" />
		<script
			src="https://kendo.cdn.telerik.com/2017.1.118/js/kendo.all.min.js"></script>
		<script type="text/javascript">

var $page=$(".page");
var i;
$(function(){ 
	
	var allData = ${Count};
	var totalPage = Math.ceil(allData/10);
	for(var i=1; i<totalPage; i++){
		$(".pagination").append('<li class="page" onclick="page(this)" value="'+i+'"><a>'+(i+1)+'</a></li>');
	}
	var li = document.createElement('li');
	li.innerHTML='<a onclick="next()">&raquo;</a>';
	document.getElementById("pagination").appendChild(li);
	$page=$(".page");
	 $(".selectTable").on("change",function(){
		 if($(this).val() == "顯示全部"){
			 $("em:empty").parent().parent().show();
			 $("em:contains('-')").parent().parent().show();
		 }else if($(this).val() == "尚未取消"){
			 $("em:empty").parent().parent().show();
			 $("em:contains('-')").parent().parent().hide();
		 }else{
			 $("em:contains('-')").parent().parent().show();
			 $("em:empty").parent().parent().hide();
		 }
	})
	
	var $BodyWidth = $(document).width();  
	var $ViewportWidth=$(window).width();  
	var $ScrollLeft=$(this).scrollLeft();
	if($BodyWidth>($ViewportWidth+$ScrollLeft)){   
           $('#span').show();
	} 
	else if($BodyWidth==($ViewportWidth+$ScrollLeft)){   
          $('#span').hide();
	}
	
	 //多選下拉式選單
	 
	 $(".multiselect").hide();
    //性別選取
	 for(var i=0; i<document.getElementsByName("tsex").length; i++){
		if(document.getElementsByName("tsex")[i].value =="男"){
			document.getElementsByName("sex")[i].selectedIndex = 0; 
		}else{
			document.getElementsByName("sex")[i].selectedIndex = 1; 
		}
	 }
	 
	//身分選取
	 for(var i=0; i<document.getElementsByName("trel").length; i++){
			if(document.getElementsByName("trel")[i].value =="眷屬"){
				document.getElementsByName("fam_Rel")[i].selectedIndex = 0; 
			}else if(document.getElementsByName("trel")[i].value =="親友"){
				document.getElementsByName("fam_Rel")[i].selectedIndex = 1;
			}
			else{
				document.getElementsByName("fam_Rel")[i].selectedIndex = 0;
			}
		 }
	 
	//用餐選取
	 for(var i=0; i<document.getElementsByName("teat").length; i++){
			if(document.getElementsByName("teat")[i].value == "葷"){
				document.getElementsByName("eat")[i].selectedIndex = 0; 
			}else if(document.getElementsByName("teat")[i].value =="素"){
				document.getElementsByName("eat")[i].selectedIndex = 1; 
			}else{
				document.getElementsByName("eat")[i].selectedIndex = 2;
			}
	}	
	 $(".detEdit").click(function () {
		 var thisTr = $(this).parents("tr")
		 thisTr.find("input").removeAttr("disabled");
		 thisTr.find("select").removeAttr("disabled");
		 thisTr.find("radio").removeAttr("disabled");
		 thisTr.find(".save").show();
		 thisTr.find(".multiselect").show();
		 thisTr.find(".multiselect").kendoMultiSelect({
			 change: function(e) {
				 	var value = this.value();
				    thisTr.find(".text_multiselect").val(value);
				  }
		 });
		 thisTr.find(".text_multiselect").val(thisTr.find("p").text());
		 
		 thisTr.find(".TWID").on("blur",function (){
				  // 依照字母的編號排列，存入陣列備用。
				  var letters = new Array('A', 'B', 'C', 'D', 
				      'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 
				      'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
				      'X', 'Y', 'W', 'Z', 'I', 'O');
				  // 儲存各個乘數
				  var multiply = new Array(1, 9, 8, 7, 6, 5, 
				                           4, 3, 2, 1);
				  var nums = new Array(2);
				  var firstChar;
				  var firstNum;
				  var lastNum;
				  var total = 0;
				  // 撰寫「正規表達式」。第一個字為英文字母，
				  // 第二個字為1或2，後面跟著8個數字，不分大小寫。
				  var sex = thisTr.find(".sex").val();
				  if(sex == "男"){
				  var regExpID=/^[a-z](1)\d{8}$/i;
				  }else{
					  var regExpID=/^[a-z](2)\d{8}$/i;
				  }
				  // 使用「正規表達式」檢驗格式
				  if (regExpID.test($(this).val())){
				    
					// 取出第一個字元和最後一個數字。
						firstChar = $(this).val().charAt(0).toUpperCase();
						lastNum = $(this).val().charAt(9);
				   
				  } else {
					$(this).css("border-color","red");
					  thisTr.find(".save").attr("type","button");
				  }
				  // 找出第一個字母對應的數字，並轉換成兩位數數字。
				  for (var i=0; i<26; i++) {
					if (firstChar == letters[i]) {
					  firstNum = i + 10;
					  nums[0] = Math.floor(firstNum / 10);
					  nums[1] = firstNum - (nums[0] * 10);
					  break;
					} 
				  }
				  // 執行加總計算
				  for(var i=0; i<multiply.length; i++){
				    if (i<2) {
				      total += nums[i] * multiply[i];
				    } else {
				      total += parseInt( $(this).val().charAt(i-1)) * 
				               multiply[i];
				    }
				  }
				  var a = (10 - (total % 10));
				  // 和最後一個數字比對
				  if ( a != lastNum && (a-10) != lastNum) {
					  $(this).css("border-color","red");
					  thisTr.find(".save").attr("type","button");
				  }else{
				  $(this).css("border-color","green");
				  thisTr.find(".save").attr("type","submit");
				}})
				
				var cellPhone=/^09\d{2}-?\d{3}-?\d{3}$/;
				thisTr.find(".Phone").on("blur",function(){
					if(cellPhone.test($(this).val())){
						$(this).css("border-color","green")
						thisTr.find(".save").attr("type","submit");
					}else{
						$(this).css("border-color","red");
						thisTr.find(".save").attr("type","button");
					}
				});
				thisTr.find(".emg_Phone").on("blur",function(){
					if(cellPhone.test($(this).val())){
						$(this).css("border-color","green")
						 thisTr.find(".save").attr("type","submit");
					}else{
						$(this).css("border-color","red");
						thisTr.find(".save").attr("type","button");
					}
				});
				
				var thisName=/^.*\s*[^\s]/;
				thisTr.find(".name").blur(function(){
					if(thisName.test($(this).val())){
						$(this).css("border-color","green")
						thisTr.find(".save").attr("type","submit");
					}else{
						$(this).css("border-color","red");
						thisTr.find(".save").attr("type","button");
					}
				});
				thisTr.find(".ben").blur(function(){
					if(thisName.test($(this).val())){
						$(this).css("border-color","green")
						thisTr.find(".save").attr("type","submit");
					}else{
						$(this).css("border-color","red");
						thisTr.find(".save").attr("type","button");
					}
				});
				thisTr.find(".ben_Rel").blur(function(){
					if(thisName.test($(this).val())){
						$(this).css("border-color","green")
						thisTr.find(".save").attr("type","submit");
					}else{
						$(this).css("border-color","red");
						thisTr.find(".save").attr("type","button");
					}
				});
				thisTr.find(".emg").blur(function(){
					if(thisName.test($(this).val())){
						$(this).css("border-color","green")
						thisTr.find(".save").attr("type","submit");
					}else{
						$(this).css("border-color","red");
						thisTr.find(".save").attr("type","button");
					}
				});
				
				
				var fambdate=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
				thisTr.find(".Bdate").on("blur",function(){
					if(fambdate.test($(this).val())){
						thisTr.find(".save").attr("type","submit");
					}else{
						thisTr.find(".save").attr("type","button");
					}
				});
				
				$(this).parents("tr").find("p").hide();
				 $(this).hide();
				 $(".cancel").hide();
				 $(".detEdit").prop("disabled",true);
			     });  
			})

	 
	 $(".save").click(function () {
		 if(this.type=='button'){
			 alert("儲存失敗！");
		 }
	 });
function open_Can(obj) {
    var CanUrl = '/GSStravel/Detail_Cancel.jsp?can_detNo=' + obj.value + "&can_traNo=" + document.getElementById("tra_no").value;
    window.open(CanUrl, '_bank', 'width=300,height=250,top=100,left=400');
};


var CanError="<%=session.getAttribute("CanError")%>";
<%session.removeAttribute("CanError");%>
if(CanError!="null"){
	alert(CanError);
}
var Msg="<%=session.getAttribute("DetMsg")%>";
<%session.removeAttribute("DetMsg");%>
			if (Msg != "null") {
				if (confirm(Msg)) {
					var insert = "/GSStravel/detail?tra_no="
							+ $('#tra_no').val() + "&doInsert=1";
					window.location = insert;
				}
			}
function next(){
	i=$(".active");
	$page.removeClass("active");
	if(i.val()<$page.length-1){
		$(".detailPage").val(i.val()+2);
		$page[i.val()+1].className="active";
	}else{
		$page[0].className="active";
		$(".detailPage").val("1");
	}
	//document.getElementById('myForm').submit();
	
}
function before(){
	i=$(".active");
	$page.removeClass("active");
	if(i.val()<$page.length && i.val()>0){
		$(".detailPage").val(i.val());
		$page[i.val()-1].className="active";
	}else{
		$page[$page.length-1].className="active";
		$(".detailPage").val($page.length);
	}	
	//document.getElementById('myForm').submit();
}
function page(obj){
	$page.removeClass("active");
	$(obj).prop("class","active");
	i=$(".active");
	$(".detailPage").val(i.val()+1);
	//document.getElementById('myForm').submit();
}
		</script>
	</div>
</body>
</html>


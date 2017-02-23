package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import org.apache.el.parser.BooleanNode;
//import org.json.simple.JSONObject;


import ch.qos.logback.core.net.SyslogOutputStream;
import model.EmployeeService;
import model.EmployeeVO;
import model.FamilyService;
import model.FamilyVO;

@WebServlet("/FamilyServlet")
public class FamilyServlet extends HttpServlet {

	private static final ArrayList<Object> Integer = null;
	private EmployeeService employeeservice = new EmployeeService();
	private FamilyService familyservice = new FamilyService();

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
    	res.setContentType ("text/html;charset=utf-8");
		// 接收資料 驗證資料 轉換資料 呼叫Model 根據Model執行結果，決定需要顯示的View元件
		String empphone = req.getParameter("empphone");
		String empben = req.getParameter("empben");
		String empbenrel = req.getParameter("empbenrel");
		String empemg = req.getParameter("empemg");
		String empemgphone = req.getParameter("empemgphone");
		String empeat = req.getParameter("empeat");
		String empnote = req.getParameter("empnote");
		String empemail = req.getParameter("empemail");
		// 親屬
		String[] famrel = req.getParameterValues("famrel");
		String[] famname = req.getParameterValues("famname");
		String[] famsex = req.getParameterValues("famsex");
		String[] famid = req.getParameterValues("famid");
		String[] fambdatedate = req.getParameterValues("fambdate");
		String[] famphone = req.getParameterValues("famphone");
		String[] fameat = req.getParameterValues("fameat");
		String[] famben = req.getParameterValues("famben");
		String[] fambenrel = req.getParameterValues("fambenrel");
		String[] famemg = req.getParameterValues("famemg");
		String[] famemgphpone = req.getParameterValues("famemgphpone");
		String[] famemgrel = req.getParameterValues("famemgrel");
		String[] famnote = req.getParameterValues("famnote");
//		String[] selectvalue = req.getParameterValues("selectvalue");
		
		
		String buttondelete = req.getParameter("delete");
		String buttonsave = req.getParameter("button");
		String ajaxid = req.getParameter("id");//判斷前端寫親屬身分證有無重複
		String ajaxfamid = req.getParameter("famid");//用前端的親屬身分證去刪親屬
		String ajaxcheckbox =req.getParameter("checkbox");
		String ajaxmultiselect =req.getParameter("multiselect");
		String ajaxfamname=req.getParameter("famnameajax");
		String repeatselectjson =req.getParameter("repeatselectjson");
		String repeatfamnameajax =req.getParameter("repeatfamnameajax");
		PrintWriter out = res.getWriter();//ajax輸出???
		
		HttpSession session = req.getSession();
		Integer emp_No = (Integer) session.getAttribute("emp_No");
		List<String> id = familyservice.selectid(emp_No);

		
		if(ajaxmultiselect != null){
			 int fam_Nos= familyservice.selectfam_No(ajaxfamname);//4567
			 System.out.println(ajaxmultiselect);
			 System.out.println(ajaxfamname);
			 System.out.println(fam_Nos);
				 String xxx=ajaxmultiselect.replace("[", "").replace("]", "").replace(",","").replace("\"", "");					 			 
				 List<String>ssss=new ArrayList<>();
				 for(int x=0;x<xxx.length();){
					 String y=xxx.substring(x, x+3);//mom
					 x=x+3;
					 ssss.add(y);
				 }
				 if(ssss.equals(null)!=true){
					 if(ssss.contains("bab")){
						 familyservice.updatefambab(fam_Nos,true);
					 }else{
						 familyservice.updatefambab(fam_Nos,false);
					 }
					 if(ssss.contains("kid")){
						 familyservice.updatefamkid(fam_Nos, true);
					 }else{
						 familyservice.updatefamkid(fam_Nos, false);
					 }
					 if(ssss.contains("dis")){
						 familyservice.updatefamdis(fam_Nos, true);
					 }else{
						 familyservice.updatefamdis(fam_Nos, false);
					 }
					 if(ssss.contains("mom")){
						 familyservice.updatefammom(fam_Nos, true);
					 }else{
						 familyservice.updatefammom(fam_Nos, false);
					 }
				 }else{
					 familyservice.updatefambab(fam_Nos,false);
					 familyservice.updatefamkid(fam_Nos, false);
					 familyservice.updatefamdis(fam_Nos, false);
					 familyservice.updatefammom(fam_Nos, false);
				 }
				 
//				 String repeatselectjson =req.getParameter("repeatselectjson");
//					String repeatfamnameajax =req.getParameter("repeatfamnameajax");
				 if(repeatselectjson!=null){
					 int fam_Nos2= familyservice.selectfam_No(repeatfamnameajax);
					 System.out.println(repeatselectjson);
					 System.out.println(repeatfamnameajax);
					 System.out.println(fam_Nos2);
						 String xxx2=repeatselectjson.replace("[", "").replace("]", "").replace(",","").replace("\"", "");					 
						 System.out.println(xxx2);				 
						 List<String>ssss2=new ArrayList<>();
						 for(int x2=0;x2<xxx2.length();){
							 String y2=xxx2.substring(x2, x2+3);//mom
							 x2=x2+3;
							 ssss2.add(y2);
						 }
						 if(ssss2.equals(null)!=true){
							 if(ssss2.contains("bab")){
								 familyservice.updatefambab(fam_Nos2,true);
							 }else{
								 familyservice.updatefambab(fam_Nos2,false);
							 }
							 if(ssss2.contains("kid")){
								 familyservice.updatefamkid(fam_Nos2, true);
							 }else{
								 familyservice.updatefamkid(fam_Nos2, false);
							 }
							 if(ssss2.contains("dis")){
								 familyservice.updatefamdis(fam_Nos2, true);
							 }else{
								 familyservice.updatefamdis(fam_Nos2, false);
							 }
							 if(ssss2.contains("mom")){
								 familyservice.updatefammom(fam_Nos2, true);
							 }else{
								 familyservice.updatefammom(fam_Nos2, false);
							 }
						 }else{
							 familyservice.updatefambab(fam_Nos2,false);
							 familyservice.updatefamkid(fam_Nos2, false);
							 familyservice.updatefamdis(fam_Nos2, false);
							 familyservice.updatefammom(fam_Nos2, false);
						 }
				 }
		
		if(ajaxid != null) {//用來抓是否重複報名id會在前端顯示
			String[] items = ajaxid.replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").split(",");
			for (String dataid : items) {// 輸出
				if (id.contains(dataid)) {//
					out.print("親屬身分證字號重複");//用來傳出
					//setAttribute需要用方法重新導向 回jsp頁面才可以顯示 ajax只有送過來 
				}else{
					out.print("");
				}
			}
		}
		
//		if(ajaxfamid!=null){
//			if(ajaxfamid!="block"){//用ajax找到id 去刪家屬
//				
//				//Travel 的活動結束時間之後可以刪 並且在detail 的親屬no之外 才可以刪  join 方法?
//				//假如在 travel 當中有此親屬 活動結束後可以刪 如果還沒結束的不能刪
//				 int famno = familyservice.selectfam_byid(ajaxfamid);//用id找famno
//			 	
//				 List<java.sql.Date> listdate= familyservice.selectfam_Nodelete(famno);//用famno去找有活動的親屬
//				 long betweenDate=0;
//				 //假如無活動
//				 //重整時會直接進入save動作?
//				 if(famno!=0){
//					 for(Date date: listdate){
//						 System.out.println(date);
//						 Calendar calendar = Calendar.getInstance();
//						 long nowDate = calendar.getTime().getTime(); //Date.getTime() 獲得毫秒型 現在日期
//						 long specialDate = date.getTime();//把要比較的值放這(親屬日期)
//						 betweenDate = (specialDate - nowDate) / (1000 * 60 * 60 * 24);
//						 
//						 if(betweenDate>0){//字串輸出過去
//							 System.out.println(betweenDate);
//							 //不能刪除  只要有超出日期 即會有out.print出去
//							 	//不再detail裡面還是抓的到值? id新的 而且刪不掉
//							 out.print("xxxxxx");
//							 
//						 }else{
//							//可以刪除  全部過期才會到此 才能刪除
//							familyservice.delete(ajaxfamid);
//						 }
//					 }//迴圈結束
//				 }else{//!famno!=0
//					 out.print("");
//				 }
//			}else{
//				out.print("");
//			}
//		}
			
		
//		List<String> email = employeeservice.selectEmail();
//		System.out.println(email);
//		if(ajaxemail!=null){
//			String[] items = ajaxemail.replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").split(",");
//			for(String dataemail: items){
//				System.out.println(dataemail);
//				if(email.contains(dataemail)){
//					out.print("員工信箱重複");//一樣 可能會抓到兩個print 一起出來??
//				}
//			}
//		}
		
		
		if ("儲存".equals(buttonsave)) {//前面""抓value 
			Map<String, String> errormsg = new HashMap<String, String>();
			req.setAttribute("error", errormsg);

			// 員工 轉值
			if (empphone == null || empphone.length() == 0) {
				errormsg.put("empphone", "員工電話不能為空值");
			}
			if (empemgphone == null || empemgphone.length() == 0) {
				errormsg.put("empemgphone", "員工緊急聯絡人電話不能為空值");
			}
			if (empben == null || empben.length() == 0) {
				errormsg.put("empben", "員工保險受益人不能為空值");
			}
			if (empbenrel == null || empbenrel.length() == 0) {
				errormsg.put("empbenrel", "員工保險受益人關係不能為空值");
			}
			if (empemg == null || empemg.length() == 0) {
				errormsg.put("empemg", "員工緊急聯絡人不能為空值");
			}
			if (empemail == null || empemail.length() == 0) {
				errormsg.put("empemail", "員工信箱不能為空值");
			}


			// 親屬 轉值 家屬為null?
			// 判斷 假如只有一行空白刪除空白欄位 跟 兩行以上 一行有一行空白 把空白判斷掉
			List<Date> fambdate = new ArrayList<Date>();
			int idlength = 0;
			if (famid != null) {
				idlength = famid.length;
				for (int i = 0; i < idlength; i++) {

					if (!famid[i].equals("") || famid[i] != null || !famname[i].equals("") || famname[i] != null
							|| !fambdatedate[i].equals("") || fambdatedate[i] != null || !famphone[i].equals("")
							|| famphone[i] != null || !famben[i].equals("") || famben[i] != null
							|| !fambenrel[i].equals("") || fambenrel[i] != null || !famemg[i].equals("")
							|| famemg[i] != null || !famemgrel[i].equals("") || famemgrel[i] != null
							|| !famemgphpone[i].equals("") || famemgphpone[i] != null) {// 判斷空白
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

						for (String bdate : fambdatedate) {
							try {
								fambdate.add(sdf.parse(bdate));
							} catch (ParseException e) {
								errormsg.put("fambdate", "日期錯誤必須符合 年-月-日格式");
								System.out.println("使用者輸入日期錯誤");
							}
						}
					}
				}
				
				if (famname == null || famname.length == 0) {
					errormsg.put("famname", "親屬家人不能為空值");
				}
				if (famid == null || famid.length == 0) {
					errormsg.put("famid", "親屬身分證不能為空值");
				}
				if (famphone == null || famphone.length == 0) {
					errormsg.put("famphone", "親屬電話不能為空值");
				}
				if (famben == null || famben.length == 0) {
					errormsg.put("famben", "親屬保險受益人不能為空值");
				}
				if (fambenrel == null || fambenrel.length == 0) {
					errormsg.put("fambenrel", "親屬保險受益人關係不能為空值");
				}
				if (famemg == null || famemg.length == 0) {
					errormsg.put("famemg", "親屬緊急聯絡人不能為空值");
				}
				if (famemgphpone == null || famemgphpone.length == 0) {
					errormsg.put("famemgphpone", "親屬緊急聯絡人電話不能為空值");
				}
				if (famemgrel == null || famemgrel.length == 0) {
					errormsg.put("famemgrel", "親屬緊急聯絡人關係不能為空值");
				}

			}

			if (errormsg != null && !errormsg.isEmpty()) {
				System.out.println("親屬員工檢查錯誤");
				req.getRequestDispatcher("Register").forward(req, res);
				return;
			} else {
				System.out.println("親屬員工檢查完畢");
			}

			// 網路頁面上取值
			EmployeeVO employeevo = new EmployeeVO();
			employeevo.setEmp_No(emp_No);
			employeevo.setEmp_Phone(empphone);
			employeevo.setEmp_Ben(empben);
			employeevo.setEmp_BenRel(empbenrel);
			employeevo.setEmp_Emg(empemg);
			employeevo.setEmp_EmgPhone(empemgphone);
			employeevo.setEmp_Mail(empemail);
			employeevo.setEmp_Eat(empeat);
			if (empnote != null ) {
				employeevo.setEmp_Note(empnote);
			} else {
				employeevo.setEmp_Note(null);
			}
			employeeservice.update(employeevo);
			System.out.println("員工資料更改完畢");
			
			idlength = famid.length;
			if (famid != null) {// 這邊判斷匯錯 因為空值也算一筆?
				
				ArrayList<Boolean> carcheckbox =new ArrayList<Boolean>();
				String[] items = ajaxcheckbox.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
				String[] results = new String[items.length];
				 for (int j = 0; j < items.length; j++) {
						results[j] = String.valueOf(items[j].trim());
					 	}
				 for (String element : results) {
					 if(element.equals("0")){//有改
						 carcheckbox.add(false);
					 }
					 if(element.equals("1")){
						 carcheckbox.add(true);
					 }
				 }
				 
				idlength = famid.length;
				for (int i = 0; i < idlength; i++) {// 0 1 2 3
					FamilyVO familyvo = new FamilyVO();
					familyvo.setEmp_No(emp_No);
					// 這邊的famno 帶錯 如果用name來入找famno會讓famno變成0( 新改的name
					// 會讓famno變0(找不到)
					// 假如 name抓不到改成用其他直抓famno 4 5 6
					Integer famno = familyservice.selectfam_No(famname[i]);
					Integer famnobyid = familyservice.selectfam_byid(famid[i]);
					if (famno != 0) {
						familyvo.setFam_No(famno);
						familyvo.setFam_Name(famname[i]);
					} else {
						familyvo.setFam_No(famnobyid);
						familyvo.setFam_Name(famname[i]);
					}
					familyvo.setFam_Rel(famrel[i]);
					familyvo.setFam_Sex(famsex[i]);
					familyvo.setFam_Id(famid[i]);
					familyvo.setFam_Bdate(new java.sql.Date(fambdate.get(i).getTime()));
					familyvo.setFam_Phone(famphone[i]);
					familyvo.setFam_Eat(fameat[i]);

					// 測試
					if(carcheckbox!=null && carcheckbox.size()!=0){
						if(carcheckbox.get(i)==true){
							familyvo.setFam_Car(true);
						}
						if(carcheckbox.get(i)==false){
							familyvo.setFam_Car(false);
						}
					}else{
						familyvo.setFam_Car(false);
					}

					familyvo.setFam_Ben(famben[i]);
					familyvo.setFam_BenRel(fambenrel[i]);
					familyvo.setFam_Emg(famemg[i]);
					familyvo.setFam_EmgPhone(famemgphpone[i]);
					familyvo.setFam_EmgRel(famemgrel[i]);
					if (famnote[i] != null) {
						familyvo.setFam_Note(famnote[i]);
					} else {
						familyvo.setFam_Note(null);
					}

					if (id.contains(famid[i]) == true) {

						familyservice.update(familyvo);
						System.out.println("update " + famid[i] + " famno=" + famno);

					} else {
						familyservice.insert(familyvo);
						System.out.println("insert " + famid[i]);

					}

				} // 回圈結束
				req.getRequestDispatcher("Register").forward(req, res);
			} else {
				req.getRequestDispatcher("Register").forward(req, res);
			} // 判斷 有沒有親屬資料
			}
		} // 按下save的執行動作 結束
  
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doPost(req, res);
	}

}

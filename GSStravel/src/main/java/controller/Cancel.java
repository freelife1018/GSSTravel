package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DetailService;
import model.EmployeeService;
import model.TotalAmountService;
import model.TravelService;
import model.TravelVO;

@WebServlet("/Cancel")
public class Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DetailService detailService=new DetailService();
	private EmployeeService employeeService=new EmployeeService();
	private TotalAmountService totalAmountService=new TotalAmountService();
	private TravelService travelService=new TravelService();
	public Cancel() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tra_No=request.getParameter("tra_No");//旅遊編號
		String emp_No=request.getParameter("emp_No");//員工標號
		detailService.updateDet_CanDate(emp_No, tra_No);
		totalAmountService.deleteTotalAmount(emp_No, tra_No);
		if(totalAmountService.selectAll(emp_No)){
			employeeService.updateEmp_Sub(true,emp_No);
		}
		if(employeeService.select(emp_No).getEmp_SubTra().equals(tra_No)){
			if(totalAmountService.selectAll(emp_No)){
				employeeService.updateEmp_SubTra("null", emp_No);
			}else{	
				String emp_SubTra = employeeService.select(emp_No).getEmp_SubTra();
				if(emp_SubTra.equals("null")||emp_SubTra==null){
					tra_No=totalAmountService.selectTa_money(emp_No).getTra_No();
					employeeService.updateEmp_SubTra(tra_No, emp_No);
					totalAmountService.updateYearSub(true, Integer.parseInt(emp_No), tra_No);
				}else{
					String traNo=totalAmountService.selectTa_money(emp_No).getTra_No();
					TravelVO travelVo = travelService.select(traNo);
					java.sql.Date tra_Off = travelVo.getTra_Off();
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//現在系統時間
					java.sql.Date today = java.sql.Date.valueOf(date);
					if(tra_Off.after(today)){
						tra_No=totalAmountService.selectTa_money(emp_No).getTra_No();
						employeeService.updateEmp_SubTra(tra_No, emp_No);
						totalAmountService.updateYearSub(true, Integer.parseInt(emp_No), tra_No);
					}						
				}							
			}
		}

		 String email = employeeService.select(emp_No).getEmp_Mail();
		 String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
		 Date());// 現在系統時間
		 TravelVO travelVo = travelService.select(tra_No);
		 String title=travelVo.getTra_Name()+"已經取消!!!";
		 String
		 content="行程編號:"+tra_No+"\n"+"行程名稱:"+travelVo.getTra_Name()+"\n"+"在"+date+"做取消!!!"
		 +"\n"+"有問題請詢問福委會";
		 new email().send(email, title, content);
		response.sendRedirect(request.getContextPath() + "/AllTravel");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

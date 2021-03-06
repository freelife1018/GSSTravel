package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.ResolutionSyntax;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DetailService;
import model.TotalAmountService;
import model.TotalAmountVO;

@WebServlet(urlPatterns = { "/TotalAmountServlet" })
public class TotalAmountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TotalAmountService totalamountService;
	private DetailService detailService;
	
	//儲存旅費統計的相關資料 和 匯出Excel 
	//save -> 儲存  , excel -> 匯出Excel
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 接收資料
		String prodaction = request.getParameter("prodaction");
		String[] temp1 = request.getParameterValues("emp_No");
		String tra_No = request.getParameter("tra_No");
		String[] temp2 = request.getParameterValues("TA_money");
		String[] det_note = request.getParameterValues("det_note");
		String[] temp3 = request.getParameterValues("det_noteMoney");
		String[] temp4 = request.getParameterValues("empfam");

		// 轉換資料
		HttpSession session = request.getSession();
		session.removeAttribute("Msg");
		
		if ("save".equals(prodaction)) {
			int[] emp_No = new int[temp1.length];
			float[] TA_money = new float[temp2.length];
			float[] det_noteMoney = new float[temp3.length];
			int[][] empfam = new int[temp4.length][2];
			try {
				int a = 0;
				for (String i : temp1) {
					emp_No[a] = Integer.parseInt(i);
					a++;
				}
				a = 0;
				for (String k : temp2) {
					TA_money[a] = Float.parseFloat(k);
					a++;
				}
				a = 0;
				for (String j : temp3) {
					if (j != "" && j.trim().length() != 0) {
						det_noteMoney[a] = Float.parseFloat(j);
					} else {
						det_noteMoney[a] = 0;
					}
					a++;
				}
				a = 0;
				String[] temp = new String[2];
				for (String k : temp4) {
					try {
						empfam[a][0] = Integer.parseInt(k);
					} catch (NumberFormatException e) {
						temp = k.split("/");
						empfam[a][0] = Integer.parseInt(temp[0]); // emp_No
						empfam[a][1] = Integer.parseInt(temp[1]); // fam_No
					}
					a++;
				}
				TotalAmountVO totalAmountVO = new TotalAmountVO();
				boolean b = true;
				for (int i = 0; i < emp_No.length; i++) {
					totalamountService = new TotalAmountService();
					totalAmountVO.setTa_Money(TA_money[i]);
					totalAmountVO.setTra_No(tra_No);
					totalAmountVO.setEmp_No(emp_No[i]);
					if (b) {
						b = totalamountService.update(totalAmountVO);
					} else {
						session.setAttribute("Msg", "新增TOTAMOUNT失敗");
						request.getRequestDispatcher("TravelDetail").forward(request, response);
					}
				}
				for (int i = 0; i < det_note.length; i++) {
					if (det_note[i] != null && det_note[i].trim().length() != 0) {
						detailService = new DetailService();
						if (empfam[i][1] == 0) {
							b = detailService.update_empNo(det_note[i], det_noteMoney[i], tra_No, empfam[i][0]);
						} else {
							b = detailService.update_famNo(det_note[i], det_noteMoney[i], tra_No, empfam[i][1]);
						}
						if (b == false) {
							session.setAttribute("Msg", "更新DETAIL失敗");
							request.getRequestDispatcher("TravelDetail").forward(request, response);
						}
					} else if ((det_note[i] == null || det_note[i].trim().length() == 0) && det_noteMoney[i] == 0.0) {
						detailService = new DetailService();
						if (empfam[i][1] == 0) {
							b = detailService.update_empNo(det_note[i], det_noteMoney[i], tra_No, empfam[i][0]);
						} else {
							b = detailService.update_famNo(det_note[i], det_noteMoney[i], tra_No, empfam[i][1]);
						}
						if (b == false) {
							session.setAttribute("Msg", "更新DETAIL失敗");
							request.getRequestDispatcher("TravelDetail").forward(request, response);
						}

					} else {
						session.setAttribute("Msg", "沒寫明細說明不可扣減免費用");
					}
				}
				if (session.getAttribute("Msg") == null) {
					session.setAttribute("Msg", "更新成功");
				}
			} catch (NumberFormatException e) {
				session.setAttribute("Msg", "其他增減費用總額/應補團費  輸入非數字之字");
			}
			request.setAttribute("session", session.getAttribute("Msg"));
			request.getRequestDispatcher("/TravelDetail?tra_no=" + tra_No).forward(request, response);
			return;
		}
		
		// 匯出Excel
		if ("Excel".equals(prodaction)) {
			tra_No = request.getParameter("tra_No");
			String tra_Name = request.getParameter("tra_Name");
			String[] dept_No = request.getParameterValues("dept_No[]");
			String[] No = request.getParameterValues("No[]");
			String[] Name = request.getParameterValues("Name[]");
			String[] years_Money = request.getParameterValues("years_Money[]");
			String[] person_Money = request.getParameterValues("person_Money[]");
			String[] Money = request.getParameterValues("Money[]");
			String[] det_Note = request.getParameterValues("det_Note[]");
			String[] det_NoteMoney = request.getParameterValues("det_NoteMoney[]");
			String[] ta_Money = request.getParameterValues("ta_Money[]");
			int count = dept_No.length;
			File dir = new File("C:/totalAmount");
			Excel ex = new Excel(dir);
			ex.totalAmountExcel(count, tra_No, tra_Name, dept_No, No, Name, years_Money, person_Money, Money, det_Note, det_NoteMoney, ta_Money);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

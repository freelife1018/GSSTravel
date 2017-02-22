package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DetailService;
import model.TotalAmountFormBean;
import model.TravelVO;

@WebServlet("/TravelDetail")
public class TravelDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DetailService detailService = new DetailService();
	//private EmployeeService employeeService = new EmployeeService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		session.removeAttribute("nopeople");
		
		String tra_No = request.getParameter("tra_no");
		List<TotalAmountFormBean> list = detailService.select(tra_No);
		int count =detailService.selectDetail_by_Tra_No(tra_No);
		if (list.size() != 0) {
			TravelVO travelVO = detailService.Count(tra_No);
			String tra_Name = list.get(0).getTra_Name();
			
			request.setAttribute("count", count);
			request.setAttribute("travelVO", travelVO);
			request.setAttribute("list", list);
			request.setAttribute("tra_Name", tra_Name);
			request.setAttribute("tra_No", tra_No);
			request.getRequestDispatcher("Detail_Money.jsp").forward(request, response);
		} else{
			session.setAttribute("nopeople", "無人報名");
			response.sendRedirect("/GSStravel/search1.jsp");
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

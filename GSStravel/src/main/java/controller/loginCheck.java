package controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.HibernateUtil;

@WebFilter("/*")
public class loginCheck implements Filter {
	private SessionFactory factory = null;

	public loginCheck() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		// 請求源頭
		String path = servletRequest.getRequestURI();
		String pro = servletRequest.getContextPath();

		if (path.indexOf("login") > -1) {
			;
		}

		else if (session.getAttribute("emp_No") == null || "".equals(session.getAttribute("emp_No"))) {
			servletResponse.sendRedirect(pro + "/notlogin.jsp");
			return;
		} 
		Transaction tx = null;
		try {
			tx = factory.getCurrentSession().beginTransaction();
			chain.doFilter(request, response);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		factory = HibernateUtil.getSessionFactory();
	}

}

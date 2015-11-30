package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CustomerBean;
import model.CustomerService;
import model.dao.CustomerDAOJndi;



@WebServlet("/secure/login.controller1234569877777gggg")



public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request,response);
	}
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("1112256462654");
		HttpSession session = request.getSession();
		List<String> erroMsg = new ArrayList<String>();
		request.setAttribute("ErroMsgKey", erroMsg);
		String username = request.getParameter("username");
		String password =request.getParameter("password");
		if(username==null||username.trim().length()!=0){
			erroMsg.add("姓名ID長度不能為零");
		}
		if(password==null||password.trim().length()!=0){
			erroMsg.add("password長度不能為零");
		}
		CustomerDAOJndi cd = new CustomerDAOJndi();
		CustomerService cs = new CustomerService();
		CustomerBean cb = cs.login(username, password);
		if(cb!=null){
			session.setAttribute("loginOk", cb);
		}else{
			erroMsg.add("可能沒有此帳號");
		}
		request.setAttribute("cb", cb);
		request.setAttribute("hi", ".HI");

		if (cb!=null) {
			String target = (String)session.getAttribute("target");
			if(target!=null){
				session.removeAttribute("target");
				response.sendRedirect(getServletContext().getContextPath()+target);
			}else{
				response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		} 
		System.out.println("123456666987777777");
		
		
//		System.out.println(new String(cb.getPassword()));
		
		
//		if(password.equalsIgnoreCase()){
//			
//		}
//		System.out.println(cb.toString());
//		System.out.println(username+":"+password);
		
	}
}

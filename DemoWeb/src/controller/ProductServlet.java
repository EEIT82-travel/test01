package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProductBean;
import model.ProductService;
import model.dao.ProductDAOJndi;

@WebServlet("/pages/product.controller")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductService ps = new ProductService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session =request.getSession();
		
		String idS = request.getParameter("id");//
		String name = request.getParameter("name");//
		String priceS = request.getParameter("price");
		String makeS = request.getParameter("make");
		String expireS = request.getParameter("expire");//
		String prodaction = request.getParameter("prodaction");
		Map<String, String> errorMap = new HashMap<String, String>();
		if("Update".equals(prodaction)||"Delete".equals(prodaction)||"Insert".equals(prodaction)){
			if(idS==null||idS.length()==0){
				errorMap.put("erId","請輸入ID");
			}
		}

		int id = 0;
		if(idS!=null&&idS.length()!=0){
			id = ProductBean.convertInt(idS);
			if(id==-1000){
				errorMap.put("erId","ID必須是整數");
			}
		}
		Double price = 0.0;
		if(priceS!=null&&priceS.length()!=0){
			price = ProductBean.convertDouble(priceS);
			if(price==-1000){
				errorMap.put("erPrice","Price必須是浮點數");
			}
		}
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date make=null;
		if(makeS!=null&&makeS.length()!=0){
			try {
				make = sFormat.parse(makeS);
			} catch (ParseException e) {
				e.printStackTrace();
				errorMap.put("erMake","日期格式yyy-MM-dd");
			}
		}
		int expire=0;
		if(expireS!=null&&expireS.length()!=0){
			try {
				expire = Integer.parseInt(expireS);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errorMap.put("erExpire","ID必須是整數");
			}
		}
		if(!errorMap.isEmpty()){
			request.setAttribute("errorMap", errorMap);
			RequestDispatcher rd = request.getRequestDispatcher("product.jsp");
			rd.forward(request, response);
			return;
		}
		
		ProductBean bean =new ProductBean();
		bean.setId(id);
		bean.setMake(make);
		bean.setName(name);
		bean.setExpire(expire);
		bean.setPrice(price);
		
		
		if ("Select".equalsIgnoreCase(prodaction)) {
			List<ProductBean> list = ps.select(bean);
			if(list==null){
				request.setAttribute("delete","查詢失敗 可能查無此ID");
				request.getRequestDispatcher("display.jsp").forward(request, response);
			}
			session.setAttribute("list", list);
			response.sendRedirect("display.jsp");
//			select(request, response);
		} else if ("Update".equalsIgnoreCase(prodaction)) {
			ProductBean pb = ps.update(bean);
			if(pb==null){
				request.setAttribute("delete","更新失敗 可能查無此ID");
				request.getRequestDispatcher("display.jsp").forward(request, response);
			}
			List<ProductBean> list = ps.select(pb);
			session.setAttribute("list",list);
			response.sendRedirect("display.jsp");
//			updata(request, response);
		} else if ("Delete".equalsIgnoreCase(prodaction)){
			session.setAttribute("list",null);
			
			if(ps.delete(bean)){
				request.setAttribute("delete","刪除成功");
				request.getRequestDispatcher("display.jsp").forward(request, response);
			}else{
				request.setAttribute("delete","刪除失敗 可能查無此ID");
				request.getRequestDispatcher("display.jsp").forward(request, response);
			}
			
			response.sendRedirect("display.jsp");
			
//			delete(request, response);
		} else if ("Insert".equalsIgnoreCase(prodaction)){
			ProductBean pb = ps.insert(bean);
			if(pb==null){
				request.setAttribute("delete","新增失敗 可能查無此ID");
				request.getRequestDispatcher("display.jsp").forward(request, response);
			}
			List<ProductBean> list = ps.select(pb);
			session.setAttribute("list",list);
			response.sendRedirect("display.jsp");
//			insert(request, response);
		}

	}

}

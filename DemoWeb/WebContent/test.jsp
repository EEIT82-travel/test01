<%@page import="model.ProductService"%>
<%@page import="model.ProductDAO"%>
<%@page import="model.dao.ProductDAOJndi"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
<%	ProductDAOJndi pd = new ProductDAOJndi(); 
	ProductService ps = new ProductService();
	ps.select(null);
	
%>
<%=ps.select(null).toString() %>
<%=ps.select(null).toString() %>

<%@	page import="javax.naming.*" %>
<%@ page import="java.sql.*" %>



<%	InitialContext context = new InitialContext();
	DataSource db =(DataSource)context.lookup("java:comp/env/jdbc/CutomerDataBase");
	Connection conn = db.getConnection();
	PreparedStatement stam = conn.prepareStatement("select * from product");
	ResultSet rs = stam.executeQuery();
	while(rs.next()){
		String col1 = rs.getString(2);
		String col2 = rs.getString(2);
		%><%=col1%><%	
	}
	
%>

</body>
</html>
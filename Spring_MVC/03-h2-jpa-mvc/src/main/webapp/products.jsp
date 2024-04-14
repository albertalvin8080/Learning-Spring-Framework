<%@ page import="com.albert.h2jpamvc.entities.Product" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Albert
  Date: 4/14/2024
  Time: 4:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>H2 database testing</title>
</head>
<body>
Welcome, Gena!
<ol>
    <% for (Product p : (List<Product>) request.getAttribute("productList")) {
        out.println("<li>" + p + "</li>");
    } %>
</ol>
</body>
</html>

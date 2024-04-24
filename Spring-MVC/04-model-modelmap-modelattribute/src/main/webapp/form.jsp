<%--
  Created by IntelliJ IDEA.
  User: Albert
  Date: 4/15/2024
  Time: 4:57 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MVC Test</title>
</head>
<body>
    <form method="post" action="${pageContext.request.contextPath}/product/test2">
        <div>
            <label for="id">Id</label>
            <input type="number" name="id" id="id">
        </div>
        <div>
            <label for="name">Name</label>
            <input type="text" name="name" id="name">
        </div>
        <div>
            <label for="price">Price</label>
            <input type="number" name="price" id="price">
        </div>
        <div><input type="submit"></div>
    </form>
</body>
</html>

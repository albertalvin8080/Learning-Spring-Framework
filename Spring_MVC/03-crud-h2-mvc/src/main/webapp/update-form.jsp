<%--
  Created by IntelliJ IDEA.
  User: Albert
  Date: 4/14/2024
  Time: 4:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>H2 database testing</title>
    <style>
        label {
            display: block;
        }
    </style>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/product/update">
    <div>
        <label for="id">Id</label>
        <input type="number" name="id" id="id" value="${product.getId()}"/>
    </div>
    <div>
        <label for="name">Name</label>
        <input type="text" name="name" id="name" value="${product.getName()}"/>
    </div>
    <div>
        <label for="price">Price</label>
        <input type="number" name="price" id="price" value="${product.getPrice()}"/>
    </div>
    <div>
        <input type="submit" value="Submit">
    </div>
</form>

</body>
</html>

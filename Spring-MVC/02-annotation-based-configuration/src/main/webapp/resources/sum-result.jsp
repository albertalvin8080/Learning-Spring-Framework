<%--
  Created by IntelliJ IDEA.
  User: Albert
  Date: 4/12/2024
  Time: 7:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Sum result is: ${sum}<br>
    Sum result is: <%= request.getAttribute("sum") %>
</body>
</html>

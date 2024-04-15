<%--
  Created by IntelliJ IDEA.
  User: Albert
  Date: 4/14/2024
  Time: 4:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>H2 database testing</title>
</head>
<body>

<form:form method="post" action="${pageContext.request.contextPath}/product/update" modelAttribute="product">
    <div>
        <form:label path="id">Id</form:label><br>
        <form:input path="id" disabled="false"/>
    </div>
    <div>
        <form:label path="name">Name</form:label><br>
        <form:input path="name"/>
    </div>
    <div>
        <form:label path="price">Price</form:label><br>
        <form:input path="price"/>
    </div>
    <div>
        <form:button>Submit</form:button>
    </div>
</form:form>

</body>
</html>

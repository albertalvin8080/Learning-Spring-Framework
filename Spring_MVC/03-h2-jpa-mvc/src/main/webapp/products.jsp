<%@ page import="com.albert.h2jpamvc.entities.Product" %>
<%@ page import="java.util.List" %><%--
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
</head>
<body>
<h1>Welcome, Gena!</h1>

    <table border="1">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${productList}">
            <tr>
                <td><c:out value="${p.getId()}"/></td>
                <td><c:out value="${p.getName()}"/></td>
                <td><c:out value="${p.getPrice()}"/></td>
                <td>
                    <button type="button" onclick="deleteById(${p.getId()})">delete</button>
                </td>
                <td>
                    <button type="button"
                            onclick="window.location.href='/test'">update</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>

</form>
</table>

<%--<ol>--%>
<%--    <%--%>
<%--        for (Product p : (List<Product>) request.getAttribute("productList")) {--%>
<%--            out.println("<li>%s</li>".formatted(p));--%>
<%--        };--%>
<%--    %>--%>
<%--</ol>--%>

<script>
    // type="module" doesn't work here

    function deleteById(id)
    {
        fetch("${pageContext.request.contextPath}/deleteById", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({id: id})
        })
            .then(() => location.reload());
    }
</script>
</body>
</html>

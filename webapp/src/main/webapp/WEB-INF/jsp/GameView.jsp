<%--
  Created by IntelliJ IDEA.
  User: juanfra
  Date: 02/04/17
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <title>MAIN GAME VIEW</title>
</head>
<body>
<p>User: <c:out value="${user.username}"/></p>
<p>img: <c:out value="${user.profileImage}"/></p>
<h1>STORAGE</h1>
<c:forEach items="${storage}" var="resource">
    <p><c:out value="${resource.resource}"/> --- <c:out value="${resource.amount}"/></p>
</c:forEach>
<h1>PRODUCTIONS</h1>
<c:forEach items="${productions}" var="production">
    <p><c:out value="${production.resource}"/> --- <c:out value="${production.rate}"/></p>
</c:forEach>
<h1>FACTORIES</h1>
<c:forEach items="${factories}" var="factory">
<h2><c:out value="${factory.type}"/></h2>
    <h3>AMOUNT: <c:out value="${factory.amount}"/></h3>
    <h3>COST</h3>
    <c:forEach items="${factory.cost}" var="resourceCost">
        <p>Resource: <c:out value="${resourceCost.resource}"/></p>
        <p>Amount: <c:out value="${resourceCost.amount}"/></p>
        <p>---------</p>
    </c:forEach>
    <h3>RECIPE</h3>

    <c:forEach items="${factory.recipe.input}" var="input">
        <h4>Input</h4>
        <p>Resource: <c:out value="${input.resource}"/></p>
        <p>Amount: <c:out value="${input.rate}"/></p>
        <p>---------</p>
    </c:forEach>
    <c:forEach items="${factory.recipe.output}" var="output">
        <h4>OutPut</h4>
        <p>Resource: <c:out value="${output.resource}"/></p>
        <p>Amount: <c:out value="${output.rate}"/></p>
        <p>---------</p>
    </c:forEach>
    <h3>UPGRADE: <c:out value="${factory.upgrade}"/> </h3>
    <p>***********</p>
</c:forEach>


</body>
</html>

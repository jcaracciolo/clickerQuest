<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<h2>Hello ${username}, your id is ${userId}!</h2>
<c:url var="image" value="${userIcon}"/>
<img src="${image}"/>
</body>
</html>
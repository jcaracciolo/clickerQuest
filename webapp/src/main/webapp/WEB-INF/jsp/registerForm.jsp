<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/register.css"/>"
</head>
<body>
<h2><spring:message code="signUp.register"/></h2>
    <c:url value="/create" var="postPath"/>
    <form:form modelAttribute="registerForm" action="${postPath}" method="post">
    <div>
        <spring:message code="signUp.username" var="usernamePH"/>
        <form:input class="inputBox" type="text" path="username" placeholder="${usernamePH}"/>
        <form:errors path="username" cssClass="formError" element="p"/>
    </div>
    <div>
        <spring:message code="signUp.password" var="passwordPH"/>
        <form:input class="inputBox" type="password" path="password" placeholder="${passwordPH}"/>
        <form:errors path="password" cssClass="formError" element="p"/>
    </div>
    <div>
        <spring:message code="signUp.repeat-password" var="repeatPasswordPH"/>
        <form:input class="inputBox" type="password" path="repeatPassword" placeholder="${repeatPasswordPH}"/>
        <form:errors path="repeatPassword" cssClass="formError" element="p"/>
    </div>
    <div>
        <input type="submit" value="<spring:message code="signUp.register"/>!"/>
    </div>
</form:form>
</body>
</html>
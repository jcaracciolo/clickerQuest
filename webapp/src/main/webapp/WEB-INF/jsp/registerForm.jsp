<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/register.css"/>"
</head>
<body>
<title><spring:message code="register.title"/></title>
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
<c:set var="storageMap" value="${storage.getUpdatedStorage(productions)}"/>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/create.js"/>"></script>
</html>
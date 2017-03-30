<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <%--<link rel="stylesheet" href="<c:url value="/css/style.css"/>" />--%>
</head>
<body>
<h2><spring:message code="signUp.register"/></h2>
    <c:url value="/create" var="postPath"/>
    <form:form modelAttribute="registerForm" action="${postPath}" method="post">
    <div>
        <form:label path="username"><spring:message code="signUp.username"/>: </form:label>
        <form:input type="text" path="username"/>
        <form:errors path="username" cssClass="formError" element="p"/>
    </div>
    <div>
        <form:label path="username"><spring:message code="signUp.password"/>: </form:label>
        <form:input type="password" path="password" />
        <form:errors path="password" cssClass="formError" element="p"/>
    </div>
    <div>
        <form:label path="username"><spring:message code="signUp.repeat-password"/>: </form:label>
        <form:input type="password" path="repeatPassword"/>
        <form:errors path="repeatPassword" cssClass="formError" element="p"/>
    </div>
    <div>
        <input type="submit" value="<spring:message code="signUp.register"/>!"/>
    </div>
</form:form>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/index.css"/>" media="screen,projection"/>
</head>

<body>

<img id="logo" class="profile" src="<c:url value="/resources/clickerQuest_logo.png"/>" alt="logo"/>

<%--<input id="usernameInput" type="text" name="username" placeholder="username">--%>
<c:url value = "/login" var = "loginUrl"/>
    <form action = "${loginUrl}" method = "post" enctype = "application/x-www-form-urlencoded">
        <div>
            <label for="usernameInput">Username: </label>
            <input id = "usernameInput" name = "j_username" type ="text"/>
        </div>
        <div>
            <label for ="passwordInput">Password: </label>
            <input id = "passwordInput" name = "j_password" type = "password"/>
        </div>
        <div>
            <label>
                <input name = "j_rememberme" type = "checkbox"/>
                <spring:message code = "remember_me"/>
            </label>
        </div>
        <div>
            <button id="play" type= "submit" class="profile" src="<c:url value="/resources/play_button.png"/>" alt="factory_img" value="">Play!<button>
            <%--<input type = "submit" value = "Login!"/>--%>
        </div>
    </form>
<p id="register"><spring:message code="index.register"/></p>
</body>

<!--Import jQuery before materialize.js-->
<script type="text/javascript">
    contextPath = '<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>
</html>
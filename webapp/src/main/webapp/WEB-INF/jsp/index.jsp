<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/index.css"/>" media="screen,projection"/>
</head>

<body>
<title><spring:message code="index.title"/></title>
<img id="logo" class="profile" src="<c:url value="/resources/clickerQuest_logo.png"/>" alt="logo"/>

<%--<input id="usernameInput" type="text" name="username" placeholder="username">--%>
<c:url value = "/login" var = "loginUrl"/>
    <form class="formu" action = "${loginUrl}" method = "post" enctype = "application/x-www-form-urlencoded">
        <div class="container" id="inputContainer">
            <div>
                <label for="usernameInput"><spring:message code="signUp.username"/> :</label>
                <input id = "usernameInput" name = "j_username" type ="text"/>
            </div>
            <div>
                <label for ="passwordInput"><spring:message code="signUp.password"/></label>
                <input id = "passwordInput" name = "j_password" type = "password"/>
            </div>
            <p id="loginFailLabel" for ="inputContainer" class="formError invisible"><spring:message code="index.loginFail"/></p>
        </div>
        <div>
            <input id="rememberMe" name = "j_rememberme" type = "checkbox"/>
            <label for ="rememberMe"><spring:message code = "remember_me"/></label>
        </div>
        <div>
            <img id="playImg" type= "submit" class="profile" src="<c:url value="/resources/play_button.png"/>" alt="factory_img" />
        </div>
        <div class="invisible">
            <input id="play" type= "submit" />
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
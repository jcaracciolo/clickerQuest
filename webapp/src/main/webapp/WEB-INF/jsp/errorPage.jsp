<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title>Error ${errorMsg}</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/errorPage.css"/>"/>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main_screen.css"/>"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"
          media="screen,projection"/>

    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>



<body background="<c:url value="/resources/error_page.jpg"/>">
<h2>${errorMsg}</h2>
<div class="row">
    <%--<img class="col s6 offset-s1" src="<c:url value="/resources/error_page.jpg"/>"/>--%>
    <p class="col s6 offset-s3"><spring:message code="error.threat"/></p>
</div><%--<h2><spring:message code="${errorMsg}"/></h2>--%>
</body>



</html>
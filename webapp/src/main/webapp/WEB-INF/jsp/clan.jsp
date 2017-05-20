<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="game.title"/> - GROUP NAME</title>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/clan.css"/>"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"
          media="screen,projection"/>

    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<%-- NAVIGATION BAR --%>
<div class="row main-frame no-margins">
    <div class="navbar-fixed">
        <nav>
            <div class="nav-wrapper">
                <div>
                    <a href='<c:url value="/game"/>' id="logo-container" href="#" class="brand-logo">
                        <img class="upper-logo" src="<c:url value="/resources/clickerQuest_logo.png"/>" alt="logo"/>
                    </a>
                </div>
                <div id="search-user">
                    <form>
                        <div class="input-field search-user">
                            <input id="search" type="search" required>
                            <label class="label-icon" for="search"><i class="material-icons">search</i>
                            </label>
                            <i class="material-icons closed">close</i>
                        </div>
                    </form>
                </div>
                <div id="logout">
                    <ul id="nav-mobile">
                        <li><a href="<c:url value='/logout'/>"><spring:message code='logout'/> </a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>

<div class="main-container">
    <div class="ranking-table">
        <div class="title"><spring:message code="ranking.ranking"/></div>
        <div class="header">
            <div><spring:message code="ranking.rank"/></div>
            <div><spring:message code="ranking.user"/></div>
            <div><spring:message code="ranking.score"/></div>
        </div>
        <c:forEach items="${clan.users}" var="u">
            <div class="table-row">
                <p>1</p>
                <p>${u.username}</p>
                <p>5443</p>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>

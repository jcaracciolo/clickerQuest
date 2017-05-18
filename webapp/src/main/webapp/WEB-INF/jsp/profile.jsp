<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--<jsp:useBean id="user" type="ar.edu.itba.paw.model.User"/>--%>
<%--<jsp:useBean id="productions" type="ar.edu.itba.paw.model.packages.Implementations.Productions"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packages.Implementations.Storage"/>--%>
<%--<jsp:useBean id="factory" type="ar.edu.itba.paw.model.Factory"/>--%>

<html>
<head>
    <title><spring:message code="game.title"/> - ${user.username}</title>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/profile.css"/>"
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
                <a href='<c:url value="/game"/>' id="logo-container" href="#" class="brand-logo">
                    <img class="upper-logo" src="<c:url value="/resources/clickerQuest_logo.png"/>" alt="logo"/>
                </a>

                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="<c:url value='/logout'/>"><spring:message code='logout'/> </a></li>
                </ul>
            </div>
        </nav>
    </div>
</div>

<div class="row no-margins canvas">
    <div class="left-section">
        <div class="main-user-properties">
            <div>
                <img class="profile-img" src="<c:url value="/resources/profile_images/${user.profileImage}"/>"/>
            </div>
            <div>
                <p id="username">${user.username}</p>
            </div>
        </div>
        <div id="world-ranking">
            <%-- TODO: add: arguments="${user.getWorldRanking()}" --%>
            <p><spring:message code="profile.worldRanking" arguments="2"/> </p>
        </div>
        <%-- TODO: if(user.hasGroup()) --%>
        <div id="group-info">
            <img class="group-logo" src="<c:url value="/resources/group_icons/1.png"/>" alt="group_logo"/>
            <p>Super Group</p>
        </div>
    </div>
    <div class="right-section">
        <div class="unlocked-factories-section">
            <p class="title no-margins"><spring:message code="profile.unlockedFactories"/></p>
            <div class="unlocked-factory-cards">
                <c:forEach items="${factories}" var="factory">
                    <c:if test="${factory.amount != 0}">
                        <div class="unlocked-factory-card">
                            <div>
                                <p class="factory-card-title"><spring:message code="${factory.type.nameCode}"/></p>
                            </div>
                            <div>
                                <img class="factory-image" src="<c:url value="/resources/factory_images/${factory.image}"/>"/>
                            </div>
                            <div>
                                <p class="cant-factory no-margins">x<fmt:formatNumber pattern="#" value="${factory.amount}"/></p>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="resources-section">
            <p class="title no-margins"><spring:message code="profile.resources"/></p>
            <div class="resources">
                <c:set var="storageMap" value="${storage.getUpdatedStorage(productions)}"/>
                <c:forEach items="${productions.resources}" var="res">
                    <div class="resource">
                        <div>
                            <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                 data-tooltip='<spring:message code="${res.nameCode}"/>' src="<c:url value="/resources/resources_icon/${res.id}.png"/>"/>
                        </div>
                        <div>
                            <p><fmt:formatNumber pattern="#.##" value="${storage.getValue(res)}"/> + <fmt:formatNumber pattern="#.##" value="${productions.getValue(res)}"/>/s</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


</body>

<script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js"/>"></script>

</html>

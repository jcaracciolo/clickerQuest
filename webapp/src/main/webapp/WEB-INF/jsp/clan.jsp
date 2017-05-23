<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="game.title"/> - ${clan.name}</title>
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
                <div class="button last">
                    <ul>
                        <li>
                            <c:set var="userid" value="${user.id}"/>
                            <c:choose>
                                <c:when test="${clan.containsUser(userid)}">
                                    <a id="clan-action" data-action="leave"><spring:message code="clan.leave"/></a>
                                </c:when>
                                <c:otherwise>
                                    <a id="clan-action" data-action="join"><spring:message code="clan.join"/></a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>
                <div id="logout" class="button">
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
        <div class="title">${clan.name}</div>
        <div class="header">
            <div><spring:message code="ranking.rank"/></div>
            <div><spring:message code="ranking.user"/></div>
            <div><spring:message code='ranking.score'/></div>
        </div>
        <c:set var="pos" value="1"/>
        <c:forEach items="${clan.users}" var="u">
            <div class="table-row">
                <p><c:out value="${pos}"></c:out></p>
                <p class="username-link" data-username="${u.username}">${u.username}</p>
                <p><fmt:formatNumber pattern="#" value="${u.score}"/></p>
                <c:set var="pos" value="${pos + 1}"/>
            </div>
        </c:forEach>
    </div>
</div>

</body>
<script type="text/javascript">
    contextPath = '<%=request.getContextPath()%>';
    clanName = '<c:out value="${clan.name}"/>';
</script>
<script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/clan.js"/>"></script>
</html>

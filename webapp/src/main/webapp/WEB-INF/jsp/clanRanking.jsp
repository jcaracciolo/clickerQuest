<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="game.title"/> - <spring:message code="globalRanking.title"/></title>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href='<c:url value="/resources/css/common.css"/>'
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href='<c:url value="/resources/css/globalRanking.css"/>'
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href='<c:url value="/resources/css/clan.css"/>'
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href='<c:url value="/resources/css/materialize.min.css"/>'
          media="screen,projection"/>
    <script type="text/javascript" src="<c:url value='/resources/js/numberFormatter.js'/>"></script>
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
                    <form onSubmit="return false;" autocomplete="off">
                        <div class="input-field search-user">
                            <input id="search" type="search" required>
                            <label class="label-icon" for="search"><i class="material-icons">search</i>
                            </label>
                            <i class="material-icons closed">close</i>
                        </div>
                    </form>
                </div>
                <div id="clanRanking" class="button last">
                    <ul>
                        <li>
                            <a href="<c:url value='/clanRanking/1'/>"><spring:message code='game.seeClanRanking'/></a>
                        </li>
                    </ul>
                </div>
                <div id="globalRanking" class="button">
                    <ul>
                        <li>
                            <a href="<c:url value='/worldRanking/1'/>"><spring:message code='game.seeGlobalRanking'/></a>
                        </li>
                    </ul>
                </div>
                <c:if test="${user.clanId != null}">
                    <div id="createClan" class="button">
                        <ul>
                            <li>
                                <a href="<c:url value='/clan/${clan.name}'/>"><spring:message code='game.seeMyClan'/></a>
                            </li>
                        </ul>
                    </div>
                </c:if>
                <div id="logout" class="button">
                    <ul id="nav-mobile">
                        <li><a href="<c:url value='/logout'/>"><spring:message code='logout'/> </a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>

<div class="ranking-container">
    <div class="ranking-table">
        <div class="title"><spring:message code="clanRanking.title"/></div>
        <div class="header">
            <div><spring:message code="ranking.rank"/></div>
            <div><spring:message code="ranking.clan"/></div>
            <div><spring:message code="ranking.score"/></div>
        </div>
        <c:set var="pos" value="1"/>
        <c:forEach items="${globalRanking.items}" var="c">
            <div class="table-row">
                <p><c:out value="${pos + (page-1)*10}"></c:out></p>
                <p class="username-link" data-username="${c.name}">${c.name}</p>
                <p>
                        <%--<fmt:formatNumber pattern="#" value="${c.clanScore}"/>--%>
                    <script>document.write(abbreviateNumber(parseFloat(${c.clanScore}), false));</script>
                </p>
                <c:set var="pos" value="${pos + 1}"/>
            </div>
        </c:forEach>
        <div id="pagination">
            <c:set var="actualPage" value="${pageNumber}"/>
            <c:if test="${actualPage > 1}">
                <div>
                    <p id="prevPage"><spring:message code="globalRanking.prevPage"/> </p>
                </div>
            </c:if>
            <c:if test="${globalRanking.totalPages > actualPage}">
                <div>
                    <p id="nextPage"><spring:message code="globalRanking.nextPage"/> </p>
                </div>
            </c:if>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    contextPath = '<%=request.getContextPath()%>';
    pageNumber = ${pageNumber};
</script>

<script type="text/javascript" src='<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/materialize.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/clanRanking.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/autocomplete.js"/>'></script>
</html>

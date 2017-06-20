<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="game.title"/> - ${clan.name}</title>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href='<c:url value="/resources/css/common.css"/>'
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href='<c:url value="/resources/css/clan.css"/>'
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href='<c:url value="/resources/css/globalRanking.css"/>'
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
                <div class="button">
                    <ul>
                        <li>
                            <c:set var="userid" value="${user.id}"/>
                            <c:choose>
                                <c:when test="${clan.containsUser(userid)}">
                                    <a id="clan-action" data-action="leave"><spring:message code="clan.leave"/></a>
                                </c:when>
                                <c:when test="${user.clanId != null}">
                                    <a href="<c:url value='/clan/${userClan.name}'/>"><spring:message code='game.seeMyClan'/></a>
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
    <div class="left-section">
        <div>
            <img id="clanImg" src="<c:url value="/resources/group_icons/${clan.image}"/>"/>
        </div>
        <div class="clanName">${clan.name}</div>
        <div class="clanPoints">
            <div><spring:message code="clan.pointsdoubledot"/> <script>document.write(abbreviateNumber(parseFloat(${clan.score}), false));</script></div>
            <div><spring:message code="clan.rank"/> <script>document.write(abbreviateNumber(parseFloat(${ranking}), false));</script></div>
            <div><spring:message code="clan.wins"/> <script>document.write(abbreviateNumber(parseFloat(${clan.wins}), false));</script></div>
            <div><spring:message code="clan.battles"/> <script>document.write(abbreviateNumber(parseFloat(${clan.battles}), false));</script></div>
        </div>
    </div>
    <div class="right-section">
        <div class="subtitle"><spring:message code="clan.battleOfTheDay"/></div>
        <c:choose>
            <c:when test="${clanBattle1 != null && clanBattle1.versus == null}">
                <div class="noBattleText"><spring:message code="clan.abandon"/></div>
            </c:when>
            <c:when test="${clanBattle1 != null}">
                <div class="clanBattle">
                    <div class="vsClanImage">
                        <img id="vsMyClan" src="<c:url value="/resources/group_icons/${clan.image}"/>"/>
                        <div class="vsClanName">
                            <p class="clanname-link" data-clanname="${clan.name}">${clan.name}</p>
                        </div>
                        <div class="vsClanPoints"><spring:message code="clan.pointsToday"/> <script>document.write(abbreviateNumber(parseFloat(${clan.score - clanBattle1.initialScore}), false));</script></div>
                    </div>
                    <div class="vsIcon">
                        <img id="vsIcon" src="<c:url value="/resources/group_icons/swords.png"/>"/>
                    </div>
                    <div class="vsClanImage">
                        <img id="vsOtherClan" src="<c:url value="/resources/group_icons/${clanBattle2.clan.image}"/>"/>
                        <div class="vsClanName">
                            <p class="clanname-link" data-clanname="${clanBattle2.clan.name}">${clanBattle2.clan.name}</p>
                        </div>
                        <div class="vsClanPoints"><spring:message code="clan.pointsToday"/> <script>document.write(abbreviateNumber(parseFloat(${clanBattle2.clan.score - clanBattle2.initialScore}), false));</script></div>
                    </div>
                </div>
            </c:when>
            <c:when test="${clanBattle1 == null}">
                <div class="noBattleText"><spring:message code="clan.noBattleToday"/></div>
            </c:when>
        </c:choose>
        <div class="ranking-table">
            <div class="subtitle"><spring:message code="clan.members"/></div>
            <div class="header">
                <div><spring:message code="ranking.rank"/></div>
                <div><spring:message code="ranking.user"/></div>
                <div><spring:message code='ranking.score'/></div>
            </div>
            <c:set var="pos" value="1"/>
            <c:forEach items="${clan.users}" var="u">
                <div class="table-row">
                    <p><c:out value="${pos}"></c:out></p>
                    <div class="nameAndImage">
                        <img class="itemImg" src="<c:url value="/resources/profile_images/${u.profileImage}"/>"/>
                        <p class="username-link" data-username="${u.username}">${u.username}</p>
                    </div>
                    <p>
                            <%--<fmt:formatNumber pattern="#" value="${u.score}"/>--%>
                        <script>document.write(abbreviateNumber(parseFloat(${u.score}), false));</script>
                    </p>
                    <c:set var="pos" value="${pos + 1}"/>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    contextPath = '<%=request.getContextPath()%>';
    clanName = '<c:out value="${clan.name}"/>';
</script>
<script type="text/javascript" src='<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/materialize.min.js"/>'></script>

<script type="text/javascript" src='<c:url value="/resources/js/clan.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/autocomplete.js"/>'></script>
</html>

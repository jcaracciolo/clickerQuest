<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--<jsp:useBean id="user" type="ar.edu.itba.paw.model.User"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packages.Implementations.FactoriesProduction"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packages.Implementations.FactoryCost"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packages.Implementations.Productions"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packages.Implementations.BaseRecipe"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packages.Implementations.Storage"/>--%>
<%--<jsp:useBean id="factory" type="ar.edu.itba.paw.model.Factory"/>--%>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="game.title"/></title>
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/game.css"/>"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"
          media="screen,projection"/>
    <script type="text/javascript" src="<c:url value='/resources/js/numberFormatter.js'/>"></script>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>

<%-- LOADING --%>
<div id="loading-disabler" class="hidden"></div>
<div id="loading" class="preloader-wrapper big active hidden">
    <div class="spinner-layer spinner-blue-only">
        <div class="circle-clipper left">
            <div class="circle"></div>
        </div><div class="gap-patch">
        <div class="circle"></div>
    </div><div class="circle-clipper right">
        <div class="circle"></div>
    </div>
    </div>
</div>
<%-- Create Clan Modal --%>
<div id="clanModal" class="modal">
    <div class="modal-content">
        <spring:message code="game.createClan.selectName"/><br>
        <input class="inputClan" type="text" name="clanName" id="clanNameInput">
        <spring:message code="buttonFolder" var="buttonLocale"/>
        <img id="createClanSend" src="<c:url value="/resources/${buttonLocale}/create.png"/>"/>
    </div>
</div>
<!-- Market Modal -->
<div id="marketModal" class="modal">
    <div class="modal-content">
        <h4><spring:message code="game.market"/></h4>
        <p><spring:message code="game.market.welcome"/></p>

        <form action="" name="market.buy">
            <div class="row market-block">
                <div id="market-buy-resource-wrapper" class="col s3 dropdown-wrapper">
                    <select name="resources" id="market.buy.resources" required>
                        <option value="" disabled selected><spring:message code="game.market.selectResource"/></option>
                        <c:forEach items="${storage.resources}" var="resource">
                            <c:if test="${resource != 'MONEY'}">
                                <option value="<c:out value='${resource.id}'/>"><spring:message code="${resource.nameCode}"/><fmt:formatNumber pattern=" ($#.##)">${resource.price}</fmt:formatNumber></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="col s2">
                    <input maxlength="5" type="text" name="quantity" id="market.buy.quantity" placeholder="<spring:message code="game.market.quantity"/>">
                </div>
                <div id="market-buy-unit-wrapper" class="col s3 dropdown-wrapper">
                    <select name="unit" id="market.buy.unit">
                        <option value="" disabled selected><spring:message code="game.market.selectUnit"/></option>
                        <option value="none"><i><spring:message code="game.market.noUnit"/></i></option>
                        <option value="K">K</option>
                        <option value="M">M</option>
                        <option value="B">B</option>
                        <option value="T">T</option>
                    </select>
                </div>
                <div class="col s2">
                    <p id="market.buy.price" class="calculatedPrice"></p>
                </div>
                <div class="col s2">
                    <spring:message code="buttonFolder" var="buttonsFolder"/>
                    <img id="market.buy" class="marketButton" src='<c:url value="/resources/${buttonsFolder}/buy.png"/>'/>
                </div>
            </div>
        </form>

        <form action="" name="market.sell">
            <div class="row market-block">
                <div id="market-sell-resources-wrapper" class="col s3 dropdown-wrapper">
                    <select name="resources" id="market.sell.resources" required>
                        <option value="" disabled selected><spring:message code="game.market.selectResource"/> </option>
                        <c:forEach items="${storage.resources}" var="resource">
                            <c:if test="${resource != 'MONEY'}">
                                <option value="<c:out value='${resource.id}'/>"><spring:message code="${resource.nameCode}"/><fmt:formatNumber pattern=" ($#)">${resource.price}</fmt:formatNumber></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="col s2">
                    <input maxlength="5" type="text" name="quantity" id="market.sell.quantity" placeholder="<spring:message code="game.market.quantity"/>">
                </div>
                <div id="market-sell-unit-wrapper" class="col s3 dropdown-wrapper">
                    <select name="unit" id="market.sell.unit">
                        <option value="" disabled selected><spring:message code="game.market.selectUnit"/> </option>
                        <option value="none"><i><spring:message code="game.market.noUnit"/></i></option>
                        <option value="K">K</option>
                        <option value="M">M</option>
                        <option value="B">B</option>
                        <option value="T">T</option>
                    </select>
                </div>
                <div class="col s2">
                    <p id="market.sell.price" class="calculatedPrice"></p>
                </div>
                <div class="col s2">
                    <spring:message code="buttonFolder" var="buttonsFolder"/>
                    <img id="market.sell" class="marketButton" src='<c:url value="/resources/${buttonsFolder}/sell.png"/>'/>
                </div>
            </div>
        </form>
    </div>
</div>
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
                    <%--<select class="js-example-data-array-selected"><select/>--%>
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
                <c:choose>
                    <c:when test="${user.clanId == null}">
                        <div id="createClan" class="button">
                            <ul>
                                <li>
                                    <a href="#clanModal"><spring:message code='create.clan'/></a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div id="createClan" class="button">
                            <ul>
                                <li>
                                    <a href="<c:url value='/clan/${clan.name}'/>"><spring:message code='game.seeMyClan'/></a>
                                </li>
                            </ul>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div id="logout">
                    <ul id="nav-mobile">
                        <li><a href="<c:url value='/logout'/>"><spring:message code='logout'/> </a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>

<div class="row">
    <!-- LEFT PANEL -->
    <div class="col no-padding s2">
        <div class="scrollable-y card darken-1">
            <div class="card-content white-text">
                <div class="section">
                    <a href="<c:url value="/myProfile"/>" class="username" data-userid="${user.id}"><c:out value="${user.username}"/></a>
                    <div class="card-image profile-picture">
                        <img class="profile" src="<c:url value="/resources/profile_images/${user.profileImage}"/>"/>
                    </div>
                    <p id="score"><spring:message code="score"/>
                        <%--<fmt:formatNumber pattern="#">${user.score}</fmt:formatNumber>--%>
                        <script>document.write(abbreviateNumber(parseFloat(${user.score}), false));</script>
                    </p>
                </div>
            </div>
            <div class="divider"></div>
            <a class="waves-effect waves-light btn marketButton" href="#marketModal"><spring:message code="game.market"/></a>
            <div class="card-content white-text">
                <span class="card-title"><spring:message code="game.resources"/></span>
                <div id="storage">
                    <c:set var="storageMap" value="${storage.getUpdatedStorage(productions)}"/>
                    <c:forEach items="${storageMap.resources}" var="resource">
                        <div class="row no-margins">
                            <div class="col">
                                <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                     data-tooltip='<spring:message code="${resource.nameCode}"/>' src="<c:url value="/resources/resources_icon/${resource.id}.png"/>"/>
                            </div>
                            <div class="col">
                                <p class="resourcesValue" data-resource="${resource.id}">
                                        <%--<fmt:formatNumber value="${storageMap.getValue(resource)}" pattern="#" minFractionDigits="0" maxFractionDigits="0"/>--%>
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="divider last"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- CENTER PANEL -->
    <div class="scrollable-y col no-padding s7">
        <!-- FIRST ROW -->
        <div class="row factory-row">
            <c:forEach items="${factories}" var="factory" varStatus="loop">
            <c:if test="${factory.amount != 0}">
                <div class="col s3 factory-main">
                    <div class="card factory-central-card">
                        <div class="card-content">
                            <div class="center-card-title">
                                <h8 class="centered-text"><spring:message code="${factory.type.nameCode}"/></h8>
                            </div>
                            <c:set var="factoriesProduction" value="${factory.factoriesProduction}"/>
                            <c:set var="inputMap" value="${factoriesProduction.inputs}"/>
                            <div class="factory-consuming">
                                <c:if test="${inputMap.size() > 0}">
                                    <p><spring:message code="game.consuming"/></p>
                                    <c:forEach items="${inputMap.keySet()}" var="res">
                                        <div class="row no-margins">
                                            <div class="col no-padding">
                                                <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                                     data-tooltip='<spring:message code="${res.nameCode}"/>' src="<c:url value="/resources/resources_icon/${res.id}.png"/>"/>
                                            </div>
                                            <div class="col">
                                                <p>
                                                        <%--<fmt:formatNumber pattern="#.##/s " value="${inputMap.get(res)}"/>--%>
                                                    <script>document.write(abbreviateNumber(parseFloat(${inputMap.get(res)}), true)+"/s");</script>
                                                </p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <div class="card-image">
                                <img class="factory-image" src="<c:url value="/resources/factory_images/${factory.getImage()}"/>"/>
                            </div>
                            <p id="factoryCant${factory.getType().getId()}" class="centered-text">
                                <spring:message code="game.amount"/> <script>document.write(abbreviateNumber(parseFloat(${factory.amount}), true));</script></p>
                            <div class="factory-producing">
                                <p><spring:message code="game.producing"/></p>
                                <c:set var="outputMap" value="${factoriesProduction.getOutputs()}"/>
                                <c:forEach items="${outputMap.keySet()}" var="res">
                                    <div class="row no-margins">
                                        <div class="col no-padding">
                                            <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                                 data-tooltip='<spring:message code="${res.nameCode}"/>' src="<c:url value="/resources/resources_icon/${res.id}.png"/>"/>
                                        </div>
                                        <div class="col">
                                            <p>
                                                    <%--<fmt:formatNumber pattern="#.##/s " value="${outputMap.get(res)}"/>--%>
                                                <script>document.write(abbreviateNumber(parseFloat(${outputMap.get(res)}), true)+"/s");</script>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${loop.index % 4 == 3 && loop.index != 0}">
        </div>
        <div class="row factory-row">
            </c:if>
            </c:forEach>
            <div class="divider last"></div>
        </div>
    </div>
    <!-- RIGHT PANEL -->
    <div class="col no-padding s3">
        <div class="scrollable-y card darken-1">
            <div class="card-content white-text">
                <span class="card-title"><spring:message code="game.factories"/></span>
                <div class="divider"></div>
                <div class="right-description">
                    <div><spring:message code="game.factory"/></div>
                    <div><spring:message code="game.recipe"/></div>
                    <div><spring:message code="game.upgrade"/></div>
                </div>
                <c:forEach items="${factories}" var="factory" varStatus="loop">
                <div class="divider"></div>
                <div class="section">
                    <!-- BEGINING OF FACTORY CARD -->
                    <div class="factory-card-container">
                        <div class="row factory-card">
                            <div class="col s4 buyFactorySection">
                                <div class="card-image factory-icon">
                                    <p class="center-align"><spring:message code="${factory.type.nameCode}"/></p>
                                    <img src="<c:url value="/resources/factory_images/${factory.getImage()}"/>" alt="factory_icon"/>
                                </div>
                                <p><spring:message code="game.cost"/></p>
                                <c:set var="factoryCost" value="${factory.getCost()}"/>
                                <div>
                                    <c:forEach items="${factoryCost.resources}" var="res">
                                        <c:set var="costMap" value="${factoryCost.getCost()}"/>
                                        <div class="row no-margins">
                                            <div class="col no-padding">
                                                <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                                     data-tooltip='<spring:message code="${res.nameCode}"/>' src="<c:url value="/resources/resources_icon/${res.id}.png"/>"/>
                                            </div>
                                            <div class="col">
                                                <p>
                                                        <%--<fmt:formatNumber pattern="#.##" value="${costMap.get(res)}"/>--%>
                                                    <script>document.write(abbreviateNumber(parseFloat(${costMap.get(res)}), false));</script>
                                                </p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="col s4">
                                <c:set var="factoriesProduction" value="${factory.recipe}"/>
                                <c:set var="inputMap" value="${factoriesProduction.getInputs()}"/>
                                <c:forEach items="${inputMap.keySet()}" var="res">
                                    <div class="row no-margins">
                                        <div class="col no-padding">
                                            <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                                 data-tooltip='<spring:message code="${res.nameCode}"/>' src="<c:url value="/resources/resources_icon/${res.id}.png"/>"/>
                                        </div>
                                        <div class="col no-padding">
                                            <p>
                                                    <%--<fmt:formatNumber pattern="#.##/s " value="${inputMap.get(res)}"/>--%>
                                                <script>document.write(abbreviateNumber(${inputMap.get(res)}, true) + "/s");</script>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                                <div class="card-image col s12">
                                    <img src="<c:url value="/resources/arrow_ingredients.png"/>" alt="embudo"/>
                                </div>
                                <c:set var="outputMap" value="${factoriesProduction.getOutputs()}"/>
                                <c:forEach items="${outputMap.keySet()}" var="res">
                                    <div class="row no-margins">
                                        <div class="col no-padding">
                                            <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                                 data-tooltip='<spring:message code="${res.nameCode}"/>' src="<c:url value="/resources/resources_icon/${res.id}.png"/>"/>
                                        </div>
                                        <div class="col no-padding">
                                            <p>
                                                    <%--<fmt:formatNumber pattern="#.##/s " value="${outputMap.get(res)}"/>--%>
                                                <script>document.write(abbreviateNumber(${outputMap.get(res)}, true) + "/s");</script>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="col s4 button-container">
                                <div id="upgradeDisabler${factory.getType()}" class="box black upgradeDisability canBuy"></div>
                                <c:if test="${factory.amount != 0}">
                                <div class="upgrade-button-container">
                                    <c:choose>
                                    <c:when test='${factory.getNextUpgrade().getType().toString().equals("OUTPUT_INCREASE")}'>
                                    <button type="button" id="upgrade${factory.getType()}" data-factoryid="${factory.getType().getId()}"
                                            class="waves-effect waves-light upgradeButton btn ${factory.getNextUpgrade().getType()} tooltipped"
                                            data-position="down" data-delay="50"
                                            data-tooltip='<spring:message code="upgrade.outputIncrease"/>'>
                                        <div class="card-image"><img class="upgradeImage"
                                                                     src="<c:url value='/resources/upgrade_icons/increase.png'/>" alt="upgrade_icon"/>
                                            </c:when>
                                            <c:when test='${factory.getNextUpgrade().getType().toString().equals("INPUT_REDUCTION")}'>
                                            <button type="button" id="upgrade${factory.getType()}" data-factoryid="${factory.getType().getId()}"
                                                    class="waves-effect waves-light upgradeButton btn ${factory.getNextUpgrade().getType()} tooltipped"
                                                    data-position="down" data-delay="50"
                                                    data-tooltip='<spring:message code="upgrade.inputReduction"/>'>
                                                <div class="card-image"><img class="upgradeImage"
                                                                             src="<c:url value='/resources/upgrade_icons/reduce.png'/>" alt="upgrade_icon"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <button type="button" id="upgrade${factory.getType()}" data-factoryid="${factory.getType().getId()}"
                                                            class="waves-effect waves-light upgradeButton btn ${factory.getNextUpgrade().getType()} tooltipped"
                                                            data-position="down" data-delay="50"
                                                            data-tooltip='<spring:message code="upgrade.costReduction"/>'>
                                                        <div class="card-image"><img class="upgradeImage"
                                                                                     src="<c:url value='/resources/upgrade_icons/cost_reduction.png'/>" alt="upgrade_icon"/>
                                                            </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="row no-margins">
                                                            <div class="col no-padding">
                                                                <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                                                     data-tooltip='<spring:message code="money-type"/>'
                                                                     src="<c:url value="/resources/resources_icon/3.png"/>"/>
                                                            </div>
                                                            <div class="col">
                                                                <p class="no-margins">
                                                                        <%--<fmt:formatNumber pattern="#" value="${factory.getNextUpgrade().cost}"/>--%>
                                                                    <script>document.write(abbreviateNumber(parseFloat(${factory.getNextUpgrade().cost}), false));</script>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </button>
                                                </div>
                                                </c:if>
                                        </div>
                                </div>
                            </div>
                            <div class="allBuyButtonsContainer">
                                <div class="buyButtonsContainer">
                                    <div>
                                        <div id="factory1Disabler${factory.getType()}" data-position="top" data-delay="50"
                                             data-tooltip="<spring:message code='game.cantBuy'/>" class="tooltipped box black buy1Disability canBuy"></div>
                                        <img id="buyOne${factory.getType()}" data-amount="1" data-factoryid="${factory.getType().getId()}" class="buyFactory" src="<c:url value="/resources/${buttonsFolder}/buy1.png"/>"/>
                                    </div>
                                    <div>
                                        <div id="factory10Disabler${factory.getType()}"data-position="top" data-delay="50"
                                             data-tooltip="<spring:message code='game.cantBuy'/>" class="tooltipped box black buy10Disability canBuy"></div>
                                        <img id="buyTen${factory.getType()}" data-amount="10" data-factoryid="${factory.getType().getId()}" class="buyFactory" src="<c:url value="/resources/${buttonsFolder}/buy10.png"/>"/>
                                    </div>
                                    <div>
                                        <div id="factory100Disabler${factory.getType()}" data-position="top" data-delay="50"
                                             data-tooltip="<spring:message code='game.cantBuy'/>" class="tooltipped box black buy100Disability canBuy"></div>
                                        <img id="buyHundred${factory.getType()}" data-amount="100" data-factoryid="${factory.getType().getId()}" class="buyFactory" src="<c:url value="/resources/${buttonsFolder}/buy100.png"/>"/>
                                    </div>
                                </div>
                                <div id="maxButtonContainer">
                                    <div id="factoryMaxDisabler${factory.getType()}" class="box black buyMaxDisability canBuy"></div>
                                    <img id="buyMax${factory.getType()}" data-amount="max" data-factoryid="${factory.getType().getId()}" class="buyFactory" src="<c:url value="/resources/bigButton.png"/>"/>
                                    <p id="maxBuy${factory.type.id}" data-refersto="buyMax${factory.getType()}" class="maxText"><spring:message code="game.maximum"/></p>
                                </div>
                            </div>
                            <!-- END OF FACTORY CARD -->
                        </div>
                        </c:forEach>
                        <div class="divider last"></div>
                    </div>
                </div>
            </div>
        </div>

</body>


<!--Import jQuery before materialize.js-->
<script type="text/javascript">
    contextPath = '<%=request.getContextPath()%>';

    factInOrder = [
        <c:forEach items="${factories}" var="factory">
        "${factory.type}",
        </c:forEach> ];

    factNameToId = { // factName -> factId
        <c:forEach items="${factories}" var="factory">
        "${factory.type}" : "${factory.type.id}",
        </c:forEach>};

    factIdToName = { // factId -> factName
        <c:forEach items="${factories}" var="factory">
        "${factory.type.id}" : "${factory.type}",
        </c:forEach>};

    resIdToName = { // resId -> resName
        <c:forEach items="${storage.resources}" var="resource">
        "${resource.id}" : "${resource}",
        </c:forEach> };

    storagesMap = { // resource -> cant
        <c:forEach items="${storage.resources}" var="resource">
        "${resource.id}" : ${storage.getValue(resource)},
        </c:forEach> };

    productionsMap = { // resource -> rate
        <c:forEach items="${productions.resources}" var="resource">
        "${resource.id}" : ${productions.getValue(resource)},
        </c:forEach>};

    costBuyResources = { // resourceId ->costInMarket
        <c:forEach items="${productions.resources}" var="resource">
        "${resource.id}" : ${resource.price},
        </c:forEach>};

    factoriesCost = { // factoryName -> (resourceId -> cant)
    <c:forEach items="${factories}" var="factory"> ${factory.type} : {
        <c:set var="cost" value="${factory.cost}"/>
        <c:forEach items="${cost.resources}" var="resource">
        "${resource.id}" : ${cost.getValue(resource)},
        </c:forEach>
    },</c:forEach>
    };

    upgradesCost = { // factoryName -> cost
        <c:forEach items="${factories}" var="factory">
        "${factory.type}" : ${factory.getNextUpgrade().cost},
        </c:forEach>
    };

    factoriesRecipe = { // factoryName -> (resource -> rate)
    <c:forEach items="${factories}" var="factory">
    ${factory.type} : {
        <c:set var="recipe" value="${factory.recipe}"/>
        <c:forEach items="${recipe.resources}" var="resource">
        "${resource.id}" : ${recipe.getValue(resource)},
        </c:forEach> },
    </c:forEach>
    };



    messages = ${messages.toJSONString()};

</script>
<script type="text/javascript" src="<c:url value='https://code.jquery.com/jquery-2.1.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/materialize.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/clickspark.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/autocomplete.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/game.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-validator-framework/jquery.validate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-validator-framework/additional-methods.js'/>"></script>

</html>

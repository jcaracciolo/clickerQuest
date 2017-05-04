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
    <!--Import css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/game.css"/>"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"
          media="screen,projection"/>

    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<div class="row main-frame">
    <!-- LEFT PANEL -->
    <div class="col no-padding s2">
        <div class="scrollable-y card indigo darken-1">
            <div class="card-content white-text">
                <span class="card-title"><spring:message code="game.profile"/></span>
                <div class="section">
                    <div class="card-image profile-picture">
                        <img class="profile" src="<c:url value="/resources/profile_images/${user.profileImage}"/>"/>
                    </div>
                    <p class="username" data-userid="${user.id}"><c:out value="${user.username}"/></p>
                </div>
            </div>
            <div class="divider"></div>
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
                                <p class="resourcesValue" data-resource="${resource}">
                                    <fmt:formatNumber value="${storageMap.getValue(resource)}" pattern="#" minFractionDigits="0" maxFractionDigits="0"/>
                                </p>
                            </div>
                        </div>
                    </c:forEach>
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
                            <h8 class="centered-text"><spring:message code="${factory.type.nameCode}"/></h8>
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
                                                    <fmt:formatNumber pattern="#.##/s " value="${inputMap.get(res)}"/>
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
                                <spring:message code="game.amount"/> <fmt:formatNumber value="${factory.amount}" pattern="#" minFractionDigits="0" maxFractionDigits="0"/></p>
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
                                                <fmt:formatNumber pattern="#.##/s " value="${outputMap.get(res)}"/>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${loop.index % 4 == 0 && loop.index != 0}">
                </div>
                <div class="row factory-row">
            </c:if>
            </c:forEach>
        </div>
    </div>
    <!-- RIGHT PANEL -->
    <div class="col no-padding s3">
        <div class="scrollable-y card indigo darken-1">
            <div class="card-content white-text">
                <span class="card-title"><spring:message code="game.factories"/></span>
                <c:forEach items="${factories}" var="factory" varStatus="loop">
                    <div class="divider"></div>
                    <div class="section">
                        <!-- BEGINING OF FACTORY CARD -->
                        <div class="factory-card-container">
                            <div id="factoryDisabler${factory.getType()}" class="box black buyDisability canBuy"></div>
                            <div class="row factory-card">
                                <div id="buy${factory.getType()}" data-factoryid="${factory.getType().getId()}" class="buyFactory col s4 buyFactorySection">
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
                                                        <fmt:formatNumber pattern="#.##" value="${costMap.get(res)}"/>
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
                                             <div class="col">
                                                 <p>
                                                     <fmt:formatNumber pattern="#.##/s " value="${inputMap.get(res)}"/>
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
                                            <div class="col">
                                                <p>
                                                    <fmt:formatNumber pattern="#.##/s " value="${outputMap.get(res)}"/>
                                                </p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="col s4 button-container">
                                    <div id="upgradeDisabler${factory.getType()}" class="box black upgradeDisability canBuy"></div>
                                    <c:if test="${factory.amount != 0}">
                                        <div class="upgrade-button-container">
                                            <button type="button" id="upgrade${factory.getType()}" data-factoryid="${factory.getType().getId()}" class="waves-effect waves-light upgradeButton btn">
                                                <div class="card-image">
                                                    <img src="<c:url value="/resources/upgrade_icon.png"/>" alt="upgrade_icon"/>
                                                </div>
                                                <div class="row no-margins">
                                                    <div class="col">
                                                        <img class="resource-icon tooltipped" data-position="top" data-delay="50"
                                                            src="<c:url value="/resources/resources_icon/3.png"/>"/>
                                                    </div>
                                                    <div class="col">
                                                        <p class="no-margins">
                                                            <fmt:formatNumber pattern="#" value="${factory.getNextUpgrade().cost}"/>
                                                        </p>
                                                    </div>
                                                </div>
                                            </button>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <!-- END OF FACTORY CARD -->
                    </div>
                </c:forEach>
                <div class="divider"></div>
            </div>
        </div>
    </div>
    <div class="col s9 m8">
    </div>
</div>
</body>


<!--Import jQuery before materialize.js-->
<script type="text/javascript">
    contextPath = '<%=request.getContextPath()%>';
    storagesMap = { // resource -> cant
        <c:forEach items="${storage.resources}" var="resource">
        "${resource}" : ${storage.getValue(resource)},
        </c:forEach> };

    productionsMap = { // resource -> rate
        <c:forEach items="${productions.resources}" var="resource">
        "${resource}" : ${productions.getValue(resource)},
        </c:forEach>};

    factoriesCost = { // factoryId -> (resource -> cant)
    <c:forEach items="${factories}" var="factory"> ${factory.type} : {
        <c:set var="cost" value="${factory.cost}"/>
        <c:forEach items="${cost.resources}" var="resource">
        "${resource}" : ${cost.getValue(resource)},
        </c:forEach>
    },</c:forEach>
    };

    upgradesCost = { // factoryId -> cost
    <c:forEach items="${factories}" var="factory">
        "${factory.type}" : ${factory.getNextUpgrade().cost},
    </c:forEach>
    };

    factoriesRecipe = { // factoryId -> (resource -> rate)
    <c:forEach items="${factories}" var="factory">
    ${factory.type} : {
        <c:set var="recipe" value="${factory.recipe}"/>
        <c:forEach items="${recipe.resources}" var="resource">
        "${resource}" : ${recipe.getValue(resource)},
        </c:forEach> },
    </c:forEach>
    };

    function localizeRes(resNameCode) {
        switch (resNameCode) {
            <c:forEach items="${storage.resources}" var="res">
            case '${res}': return "<spring:message code="${res.nameCode}"/>"
            </c:forEach>
        }
        return undefined
    }
</script>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/clickspark.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/game.js"/>"></script>

</html>

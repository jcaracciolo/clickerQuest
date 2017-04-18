<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--<jsp:useBean id="user" type="ar.edu.itba.paw.model.User"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packagesntations.FactoriesProduction"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packagesntations.FactoryCost"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packagesntations.Productions"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packagesntations.BaseRecipe"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packagesntations.Storage"/>--%>
<%--<jsp:useBean id="factory" type="ar.edu.itba.paw.model.Factory"/>--%>

<!DOCTYPE html>
<html>
<head>
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
<body>
<div class="row main-frame">
    <!-- LEFT PANEL -->
    <div class="col no-padding s2">
        <div class="complete-height card indigo darken-1">
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
                <span class="card-title"><spring:message code="game.storage"/></span>
                <div id="storage">
                    <c:set var="storageMap" value="${storage.getUpdatedStorage(productions)}"/>
                    <c:forEach items="${storageMap.resources}" var="resource">
                        <p class="storageValue" data-resource="${resource}">
                            <fmt:formatNumber value="${storageMap.getValue(resource)}" pattern="#" minFractionDigits="0" maxFractionDigits="0"/>
                            <spring:message code="${resource.nameCode}"/>
                        </p>
                    </c:forEach>
                </div>
                <span class="card-title"><spring:message code="game.production"/></span>
                <div id="production">
                    <c:set var="rateMap" value="${productions.getProductions()}"/>
                    <c:forEach items="${productions.resources}" var="resource">
                        <p class="productionValue" data-resource="${resource}">
                            <c:out value="${rateMap.get(resource)} "/>
                            <spring:message code="${resource.nameCode}"/>
                        </p>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <!-- CENTER PANEL -->
    <div class="col no-padding s7">
        <!-- FIRST ROW -->
        <div class="row factory-row">
            <c:forEach items="${factories}" var="factory" varStatus="loop">
            <c:if test="${factory.amount != 0}">
                <div class="col s3 factory-main">
                    <div class="card">
                        <div class="card-content">
                            <h8 class="centered-text"><spring:message code="${factory.type.nameCode}"/></h8>
                            <p><spring:message code="game.consuming"/></p>
                            <c:set var="factoriesProduction" value="${factory.factoriesProduction}"/>
                            <c:set var="inputMap" value="${factoriesProduction.formattedInputs}"/>
                            <c:forEach items="${factoriesProduction.resources}" var="res">
                                <%-- TODO: arreglar esto que es asqueroso:--%>
                                <c:if test="${inputMap.get(res).toString().split('/')[0] > 0}">
                                    <p class="centered-text"><c:out value="${inputMap.get(res)} ${res}"/></p>
                                </c:if>
                            </c:forEach>
                            <div class="card-image">
                                <img class="factory-image" src="<c:url value="/resources/factory_images/${factory.getImage()}"/>"/>
                            </div>
                            <p id="factoryCant${factory.getType().getId()}" class="centered-text">
                                <spring:message code="game.amount"/> <fmt:formatNumber value="${factory.amount}" pattern="#" minFractionDigits="0" maxFractionDigits="0"/></p>
                            <p><spring:message code="game.producing"/></p>
                            <c:set var="outputMap" value="${factoriesProduction.formattedOutputs}"/>
                            <c:forEach items="${factoriesProduction.resources}" var="res">
                                <c:if test="${outputMap.get(res) != null}">
                                    <p class="centered-text"><c:out value="${outputMap.get(res)} ${res}"/></p>
                                </c:if>
                            </c:forEach>
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
        <div class="complete-height card indigo darken-1">
            <div class="card-content white-text">
                <span class="card-title"><spring:message code="game.factories"/></span>
                <c:forEach items="${factories}" var="factory" varStatus="loop">
                    <div class="divider"></div>
                    <div class="section">
                        <!-- BEGINING OF FACTORY CARD -->
                        <div class="factory-card-container">
                            <div id="factoryDisabler${factory.getType().getId()}" class="box black canBuy"></div>
                            <div class="row factory-card">
                                <div id="buyFactory${factory.getType().getId()}" class="col s4 offset-s1 buyFactorySection">
                                    <div class="card-image factory-icon">
                                        <p class="center-align"><spring:message code="${factory.type.nameCode}"/></p>
                                        <img src="<c:url value="/resources/factory_images/${factory.getImage()}"/>" alt="factory_icon"/>
                                    </div>
                                    <p>Cost:</p>
                                    <c:set var="factoryCost" value="${factory.getCost()}"/>
                                    <div>
                                        <c:forEach items="${factoryCost.resources}" var="res">
                                            <c:set var="costMap" value="${factoryCost.getCost()}"/>
                                            <p class="centered-text"><c:out value="${costMap.get(res)} ${res}"/></p>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="col offset-s1 s4">
                                    <c:set var="factoriesProduction" value="${factory.type.baseRecipe}"/>
                                    <c:forEach items="${factoriesProduction.resources}" var="res">
                                        <c:set var="inputMap" value="${factoriesProduction.getInputs()}"/>
                                        <c:if test="${inputMap.get(res) != null}">
                                            <p class="centered-text"><c:out value="${inputMap.get(res)} ${res}"/></p>
                                        </c:if>
                                    </c:forEach>
                                    <div class="card-image col s12">
                                        <img src="<c:url value="/resources/arrow_ingredients.png"/>" alt="embudo"/>
                                    </div>
                                    <c:forEach items="${factoriesProduction.resources}" var="res">
                                        <c:set var="outputMap" value="${factoriesProduction.formattedOutputs}"/>
                                        <c:if test="${outputMap.get(res) != null}">
                                            <p class="centered-text"><c:out value="${outputMap.get(res)} ${res}"/></p>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                    <%--<div class="col s4">--%>
                                    <%--<button type="button" class="waves-effect waves-light upgradeButton btn">--%>
                                    <%--<div class="card-image">--%>
                                    <%--<img src="/resources/upgrade_icon.png" alt="upgrade_icon"/>--%>
                                    <%--</div>--%>
                                    <%--<p>UPGRADE</p>--%>
                                    <%--</button>--%>
                                    <%--</div>--%>
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
    storagesMap = {
        <c:forEach items="${storage.resources}" var="resource">
        "${resource}" : ${storage.getValue(resource)},
        </c:forEach> }; // resource -> cant

    productionsMap = {
        <c:forEach items="${productions.resources}" var="resource">
        "${resource}" : ${productions.getValue(resource)},
        </c:forEach>}; // resource -> rate

    factoriesCost = {
    <c:forEach items="${factories}" var="factory"> ${factory.type} : {
        <c:set var="cost" value="${factory.cost}"/>
        <c:forEach items="${cost.resources}" var="resource">
        "${resource}" : ${cost.getValue(resource)},
        </c:forEach>
    },</c:forEach>

    }; // factoryId -> (resource -> cant)
    factoriesRequirement = {
    <c:forEach items="${factories}" var="factory">
    ${factory.type} : {
        <c:set var="recipe" value="${factory.recipe}"/>
        <c:forEach items="${recipe.resources}" var="resource">
        "${resource}" : ${recipe.getValue(resource)},
        </c:forEach> },
    </c:forEach>
    }; // factoryId -> (resource -> rate)

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

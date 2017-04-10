<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<jsp:useBean id="user" type="ar.edu.itba.paw.model.User"/>--%>
<%--<jsp:useBean id="storage" type="ar.edu.itba.paw.model.packagesntations.SingleProduction"/>--%>
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
                <span class="card-title">Profile</span>
                <div class="section">
                    <div class="card-image profile-picture">
                        <%--<img class="profile" src="/resources/profile_image_2.jpg" alt="factory_img"/>--%>
                        <img class="profile" src="/resources/${user.profileImage}" alt="factory_img"/>
                    </div>
                    <p class="username"><c:out value="${user.username}"/></p>
                </div>
            </div>
            <div class="divider"></div>
            <div class="card-content white-text">
                <span class="card-title">Storage</span>
                <div id="storage">
                    <c:set var="storageMap" value="${storage.getUpdatedStorage(productions)}"/>
                    <c:forEach items="${storageMap.resources}" var="resource">
                        <p><c:out value="${resource}"/>
                            <fmt:formatNumber value="${storageMap.getValue(resource)}" pattern="#" minFractionDigits="0" maxFractionDigits="0"/></p>
                        <%--<c:out value="${storageMap.get(resource)}"/></p>--%>
                    </c:forEach>
                </div>
                <span class="card-title">Production</span>
                    <div id="production">
                        <c:set var="rateMap" value="${productions.formatted()}"/>
                        <c:forEach items="${productions.resources}" var="resource">
                        <p><c:out value="${rateMap.get(resource)} ${resource}"/></p>
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
                <div class="col s4 factory-main">
                    <div class="card">
                        <div class="card-content">
                            <p>Consuming:</p>
                            <c:set var="factoryRecipe" value="${factory.singleProduction}"/>
                            <c:set var="inputMap" value="${factoryRecipe.formattedInputs}"/>
                            <c:forEach items="${factoryRecipe.resources}" var="res">
                                <p class="centered-text"><c:out value="${inputMap.get(res)} ${res}"/></p>
                            </c:forEach>
                            <div class="card-image">
                                <img class="factory-image" src="/resources/${factory.getImage()}" alt="factory_icon"/>
                            </div>
                            <p id="factoryCant${factory.getType().getId()}" class="centered-text">${factory.amount}</p>
                            <p>Producing:</p>
                            <c:set var="outputMap" value="${factoryRecipe.formattedOutputs}"/>
                            <c:forEach items="${factoryRecipe.resources}" var="res">
                                <c:if test="${outputMap.get(res) != null}">
                                    <p class="centered-text"><c:out value="${outputMap.get(res)} ${res}"/></p>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <!-- RIGHT PANEL -->
    <div class="col no-padding s3">
        <div class="complete-height card indigo darken-1">
            <div class="card-content white-text">
                <span class="card-title">Factories</span>
                <c:forEach items="${factories}" var="factory" varStatus="loop">
                <div class="divider"></div>
                <div class="section">
                    <!-- BEGINING OF FACTORY CARD -->
                    <div class="row factory-card">
                        <div class="col s3">
                            <div id="buyFactory${factory.getType().getId()}" class="buyFactoryBtn card-image factory-icon">
                                <img src="/resources/${factory.getImage()}" alt="factory_icon"/>
                            </div>
                            <div>
                                <p>Cost:</p>
                                <c:set var="factoryCost" value="${factory.cost}"/>
                                <c:forEach items="${factoryCost.resources}" var="res">
                                    <c:set var="costMap" value="${factoryCost.formatted()}"/>
                                    <p class="centered-text"><c:out value="${costMap.get(res)} ${res}"/></p>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col s4">
                            <c:set var="factoryRecipe" value="${factory.type.baseRecipe}"/>
                            <c:forEach items="${factoryRecipe.resources}" var="res">
                                <c:set var="inputMap" value="${factoryRecipe.formattedInputs}"/>
                                <c:if test="${inputMap.get(res) != null}">
                                    <p class="centered-text"><c:out value="${inputMap.get(res)} ${res}"/></p>
                                </c:if>
                            </c:forEach>
                            <div class="card-image col s12">
                                <img src="/resources/arrow_ingredients.png" t alt="embudo"/>
                            </div>
                            <c:forEach items="${factoryRecipe.resources}" var="res">
                                <c:set var="outputMap" value="${factoryRecipe.formattedOutputs}"/>
                                <c:if test="${outputMap.get(res) != null}">
                                    <p class="centered-text"><c:out value="${outputMap.get(res)} ${res}"/></p>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="col s4">
                            <button type="button" class="waves-effect waves-light upgradeButton btn">
                                <div class="card-image">
                                    <img src="/resources/upgrade_icon.png" alt="upgrade_icon"/>
                                </div>
                                <p>UPGRADE</p>
                            </button>
                        </div>
                    </div>
                    <!-- END OF FACTORY CARD -->
                    </c:forEach>
                </div>
                <div class="divider"></div>
            </div>
        </div>
    </div>
    <div class="col s9 m8">
    </div>
</div>
</body>


<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/materialize.min.js"></script>
<script type="text/javascript" src="/resources/js/game.js"></script>

</html>

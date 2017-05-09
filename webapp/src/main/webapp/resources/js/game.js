/**
 * Created by epord on 05/04/17.
 */

var url=window.location.href.split("/")
var userId =url[url.length - 2];

sessionStorage.removeItem("user");

$(document).ready(function(){
    $('.modal').modal();
    $('select').material_select();
});

refreshValues(false);
refreshView();
refreshFactoriesBuyability();
refreshUpgradesBuyability();

function refreshValues(update){
    Object.keys(storagesMap).forEach(function (key,value) {
        storagesMap[key] = storagesMap[key] + productionsMap[key]
    });
}

function refreshView() {
    unitPerSec= "/s"

    storageValues = $(".resourcesValue");
    for (var i = 0; i<storageValues.size() ; i++) {
        element = storageValues.eq(i);
        res = element.data("resource");
        element.text(String(abbreviateNumber(storagesMap[res], false)) + " + " + String(abbreviateNumber(productionsMap[res], true)) + unitPerSec)
    }
}

function refreshUpgradesBuyability() {
    var notBuyable= []
    var availableMoney = storagesMap[3] // 3: money
    for (factId in upgradesCost) {
        if (upgradesCost[factId] > availableMoney) {
            document.getElementById("upgradeDisabler" + factId).classList.remove("canBuy");
        } else {
            document.getElementById("upgradeDisabler" + factId).classList.remove("canBuy");
            document.getElementById("upgradeDisabler" + factId).classList.add("canBuy");
        }
    }
}

function refreshFactoriesBuyability() {
    var notBuyable = []
    for (var factId in factoriesCost) {
        for (var res in factoriesCost[factId]) {
            if (factoriesCost[factId][res] > storagesMap[res]) {
                notBuyable.push(factId)
                break;
            }
        }
        for (var res in factoriesRecipe[factId]) {
            if (factoriesRecipe[factId][res] < 0
                && Math.abs(factoriesRecipe[factId][res]) > productionsMap[res]) {
                notBuyable.push(factId)
                break;
            }
        }
        if (notBuyable.includes(factId)) {
            document.getElementById("factoryDisabler" + factId).classList.remove("canBuy");
        }
        else {
            document.getElementById("factoryDisabler" + factId).classList.remove("canBuy");
            document.getElementById("factoryDisabler" + factId).classList.add("canBuy");
        }
    }
    for (var i = 0; i < Object.keys(factoriesCost).length; i++) {
    }
}

setInterval(function(){
    refreshValues(true);
    refreshView();
    refreshFactoriesBuyability();
    refreshUpgradesBuyability();
}, 1000);


// Buy listener
$.each($(".buyFactory"),function (i,element){
    $("#" + element.id).clickSpark({
        particleImagePath: contextPath + '/resources/star_icon.png',
        particleCount: 55,
        particleSpeed: 10,
        particleSize: 12,
        particleRotationSpeed: 20,
        animationType:'explosion',
        callback: function() {
            buyFactory($("#"+element.id).data("factoryid"))
        }
    })
});


function buyFactory(id){
    $.post(contextPath + "/" + userId + "/buyFactory",
        {
            factoryId: id
        }, function(data) { window.location.reload() });
}

// Upgrade listener
$.each($(".upgradeButton"),function (i,element){
    $("#" + element.id).clickSpark({
        particleImagePath: contextPath + '/resources/star_icon.png',
        particleCount: 55,
        particleSpeed: 10,
        particleSize: 12,
        particleRotationSpeed: 20,
        animationType:'explosion',
        callback: function() {
            upgradeFactory($("#"+element.id).data("factoryid"))
        }
    })
});

function upgradeFactory(factId) {
    $.post(contextPath + "/" + userId + "/upgradeFactory",
        {
            factoryId: factId //$("#"+element.id).data("factoryid")
        }, function(data) { window.location.reload() });
}

function abbreviateNumber(value,decimals) {
    var newValue = value;
    if(! decimals || value>=10000) {
        newValue = truncate(value);
    }
    if (newValue >= 10000) {
        var suffixes = ["", "K", "M", "B","T"];
        var suffixNum = Math.floor( (""+newValue).length/3 );
        var shortValue = '';
        for (var precision = 2; precision >= 1; precision--) {
            shortValue = parseFloat( (suffixNum != 0 ? (newValue / Math.pow(1000,suffixNum) ) : newValue).toPrecision(precision));
            var dotLessShortValue = (shortValue + '').replace(/[^a-zA-Z 0-9]+/g,'');
            if (dotLessShortValue.length <= 2) { break; }
        }
        if (shortValue % 1 != 0)  shortNum = shortValue.toFixed(1);
        newValue = shortValue+suffixes[suffixNum];
    }
    return decimals ? Math.round(100* newValue)/100 : newValue;
}

function truncate(number)
{
    return number > 0
        ? Math.floor(number)
        : Math.ceil(number);
}


// // Wait for the DOM to be ready
$(function() {
    $("form[name='market.buy']").validate({
        resources:{
            required: true
        },
        rules: {
            quantity: {
                required: true,
                digits: true
            },
            unit:{
                required: true
            }
        },
        messages: {
            resources:{
                required: "Please, select a resource"
            },
            quantity: {
                required: "Please, enter a quantity",
                digits: "Only digits are allowed"
            },
            unit:{
                required: "Please, select an unit"
            }
        },
        submitHandler: function(form) {
            var unit = document.getElementById("market.buy.unit").value
            var multiplier
            switch (unit){
                case "K": multiplier = 1000; break;
                case "M": multiplier = 1000000; break;
                case "B": multiplier = 1000000000; break;
                case "T": multiplier = 1000000000000; break;
                case "none":
                default: multiplier = 1; break
            }
            var resourceId = document.getElementById("market.buy.resources").value;
            var quantity = parseFloat(document.getElementById("market.buy.quantity").value)*multiplier;

            if (validateBuy(resourceId, quantity)) {
                $.post(contextPath + "/" + userId + "/buyFromMarket",
                    {
                        resourceId: resourceId,
                        quantity: quantity
                    }, function (data) {
                });
            }
        }
    });


    function validateBuy(resourceId, quantity) {
        if (storagesMap[3] >= quantity * costBuyResources[resourceId]) { //3: MONEY
            return true;
        }
        return false
    }

    $("form[name='market.sell']").validate({
        resources:{
            required: true
        },
        rules: {
            quantity: {
                required: true,
                digits: true
            },
            unit:{
                required: true
            }
        },
        messages: {
            resources:{
                required: "Please, select a resource"
            },
            quantity: {
                required: "Please, enter a quantity",
                digits: "Only digits are allowed"
            },
            unit:{
                required: "Please, select an unit"
            }
        },
        submitHandler: function(form) {
            var unit = document.getElementById("market.sell.unit").value
            var multiplier
            switch (unit){
                case "K": multiplier = 1000; break;
                case "M": multiplier = 1000000; break;
                case "B": multiplier = 1000000000; break;
                case "T": multiplier = 1000000000000; break;
                case "none":
                default: multiplier = 1; break
            }
            var resourceId = document.getElementById("market.sell.resources").value;
            var quantity = parseFloat(document.getElementById("market.sell.quantity").value)*multiplier;

            if (validateSell(parseInt(resourceId), quantity)) {
                $.post(contextPath + "/" + userId + "/sellToMarket",
                    {
                        resourceId: resourceId,
                        quantity: quantity
                    }, function (data) {
                    });
            }
        }
    });


    function validateSell(resourceId, quantity) {
        if (storagesMap[resourceId] >= quantity) {
            return true;
        }
        return false
    }
});

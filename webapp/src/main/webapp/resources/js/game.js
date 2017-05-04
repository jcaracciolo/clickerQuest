/**
 * Created by epord on 05/04/17.
 */

var url=window.location.href.split("/")
var userId =url[url.length - 2];

var storage = document.getElementById("storage");
var production = document.getElementById("production");
sessionStorage.removeItem("user");

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
    var availableMoney = storagesMap["MONEY"]
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
        var suffixes = ["", "k", "m", "b","t"];
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

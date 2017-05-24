var url=window.location.href.split("/")
var userId =url[url.length - 2];
sessionStorage.removeItem("user");

$(document).ready(function(){
    $('.modal').modal();
    $('select').material_select();
});

window.onkeyup = function(e) {
    var key = e.keyCode ? e.keyCode : e.which;

    if (key == 13) {
        if($( "#clanNameInput" ).is(":focus")){
            createClanFunction();
        } else if($( "#search" ).is(":focus")){
            searchCommunity(true);
        }
    }
};

var toPrint = window.sessionStorage.getItem("message");

if(toPrint !== null){
    sessionStorage.removeItem("message");
    Materialize.toast(toPrint,4000);
}

refreshValues(false);
refreshView();
refreshFactoriesBuyability();
refreshUpgradesBuyability();

function refreshValues(update){
    Object.keys(storagesMap).forEach(function (key,value) {
        storagesMap[key] = storagesMap[key] + productionsMap[key]
    });
}

function dec(mesageCode, arg0, arg1, arg2) {
    var st = messages[mesageCode];
    if(st == null) {
        console.log(mesageCode + ":no message");
        return;
    }
    st = st.replace("{0}",arg0);
    st = st.replace("{1}",arg1);
    st = st.replace("{2}",arg2);
    return st;
}
function decRes(resourceId) {
    return messages.resources[resourceId];
}
function decFac(factoryId) {
    return messages.factories[factoryId];
}

function refreshView() {
    unitPerSec= "/s"

    storageValues = $(".resourcesValue");
    for (var i = 0; i<storageValues.size() ; i++) {
        element = storageValues.eq(i);
        res = element.data("resource");
        element.text(String(abbreviateNumber(storagesMap[res], false)) + " + " + String(abbreviateNumber(productionsMap[res], true)) + unitPerSec)
    }


    // TODO: make it different... but TIME IS NOT ENOUGH TO MAKE IT WELL
    // update sell price
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
    var quantity = document.getElementById("market.sell.quantity").value;
    if(resourceId != "" && quantity != "" && unit != "") {
        document.getElementById("market.sell.price").innerHTML = dec("game.market.profit") +abbreviateNumber(parseInt(quantity) * multiplier * costBuyResources[resourceId])
    } else {
        document.getElementById("market.sell.price").innerHTML = ""
    }

    // update buy price
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
    resourceId = document.getElementById("market.buy.resources").value;
    quantity = document.getElementById("market.buy.quantity").value;
    if(resourceId != "" && quantity != "" && unit != "") {
        document.getElementById("market.buy.price").innerHTML = dec("game.market.cost")  + abbreviateNumber(parseInt(quantity) * multiplier * costBuyResources[resourceId])
    } else {
        document.getElementById("market.buy.price").innerHTML = ""
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
}
setInterval(function(){
    refreshValues(true);
    refreshView();
    refreshFactoriesBuyability();
    refreshUpgradesBuyability();
}, 1000);

// Create clan listener
var createClanFunction = function () {
    var clanName = document.getElementById("clanNameInput").value;
    var ret;
    if (!clanName.match("^[a-zA-Z\+\-\.\_\*]+$")) {
        ret = true;
        document.getElementById("clanNameInput").classList.add("inputerror")
    } else {document.getElementById("clanNameInput").classList.remove("inputerror")}
    if (ret) {return;}

    $.post(contextPath + "/createClan",
        {
            clanName: clanName
        }, function(data) {
            var resp = JSON.parse(data);

            var msg;
            if (resp.result == "exists") {
                msg = dec("game.msg.clanAlreadyExists");
                window.sessionStorage.setItem("message", msg);
                location.reload();
            } else if(resp.result == "noUser") {
            } else {
                window.location.replace(contextPath + "/clan/" + clanName);
            }
        });
}
document.getElementById("createClanSend").addEventListener("click", createClanFunction);

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
            document.getElementById("loading").classList.remove("hidden");
            document.getElementById("loading-disabler").classList.remove("hidden");
            buyFactory($("#"+element.id).data("factoryid"))
        }
    })
});


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
            document.getElementById("loading").classList.remove("hidden");
            document.getElementById("loading-disabler").classList.remove("hidden");
            upgradeFactory($("#"+element.id).data("factoryid"))
        }
    })
});

function buyFactory(id){
    $.post(contextPath + "/buyFactory",
        {
            factoryId: id
        }, function(data) {
            var resp = JSON.parse(data);
            var msg ;
            if(resp.result){
                msg = dec("game.factoryBuySuccessful",dec(decFac(resp.factoryId)));
            } else {
                msg = dec("game.factoryBuyFail",dec(decFac(resp.factoryId)));
            }

            window.sessionStorage.setItem("message",msg);
            location.reload();
        });
}

function upgradeFactory(factId) {
    $.post(contextPath + "/upgradeFactory",
        {
            factoryId: factId //$("#"+element.id).data("factoryid")
        }, function(data) {
            var resp = JSON.parse(data);

            var msg ;
            if(resp.result){
                msg = dec("game.upgradeSuccessful",resp.level,dec(decFac(resp.factoryId)));
            } else {
                msg = dec("game.upgradeFail",resp.level,dec(decFac(resp.factoryId)));
            }
            window.sessionStorage.setItem("message",msg);
            location.reload();
    });
}

function abbreviateNumber(value,decimals) {
    var newValue = value;
    var suffixNum =0;
    var suffixes = ["", "K", "M", "B","T"];
    if(! decimals || value>=10000) {
        newValue = truncate(value);
    }
    if (newValue >= 10000) {
        suffixNum = Math.floor( (""+newValue).length/3 );
        var shortValue = '';
        for (var precision = 2; precision >= 1; precision--) {
            shortValue = parseFloat( (suffixNum != 0 ? (newValue / Math.pow(1000,suffixNum) ) : newValue).toPrecision(precision));
            var dotLessShortValue = (shortValue + '').replace(/[^a-zA-Z 0-9]+/g,'');
            if (dotLessShortValue.length <= 2) { break; }
        }
        if (shortValue % 1 != 0)  shortNum = shortValue.toFixed(1);
        newValue = shortValue;
    }
    return (decimals ? Math.floor(100* newValue)/100 : newValue)+suffixes[suffixNum];
}

function truncate(number) {
    return number > 0
        ? Math.floor(number)
        : Math.ceil(number);
}

document.getElementById("market.buy").addEventListener("click", function() {
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
        var quantity = document.getElementById("market.buy.quantity").value;

        var ret = false;
        if (resourceId == "") {
            ret = true;
            document.getElementById("market-buy-resource-wrapper").classList.add("error")
        } else {document.getElementById("market-buy-resource-wrapper").classList.remove("error")}
        if (!quantity.match("^[0-9]+$")) {
            ret = true;
            document.getElementById("market.buy.quantity").classList.add("inputerror")
        } else {document.getElementById("market.buy.quantity").classList.remove("inputerror")}
        if (unit == "") {
            ret = true;
            document.getElementById("market-buy-unit-wrapper").classList.add("error")
        } else {document.getElementById("market-buy-unit-wrapper").classList.remove("error")}
        if (ret) {return;}

        var quantity = parseFloat(quantity)*multiplier;

        if (validateBuy(resourceId, quantity)) {
            $.post(contextPath + "/buyFromMarket",
                {
                    resourceId: resourceId,
                    quantity: quantity
                }, function (data) {
                    var resp = JSON.parse(data);

                    var msg ;
                    if(resp.result){
                        msg = dec("game.market.buySuccessful",abbreviateNumber(resp.quantity,false),dec(decRes(resp.resourceId)));
                    } else {
                        msg = dec("game.market.buyFail",abbreviateNumber(resp.quantity,false),dec(decRes(resp.resourceId)));
                    }
                    window.sessionStorage.setItem("message",msg);
                    location.reload();
            });
        }
    });


function validateBuy(resourceId, quantity) {
    if (storagesMap[3] >= quantity * costBuyResources[resourceId]) { //3: MONEY
        return true;
    }
    Materialize.toast(dec("game.market.buyFail",abbreviateNumber(quantity,false),dec(decRes(resourceId))), 3000);

    return false;
}

document.getElementById("market.sell").addEventListener("click", function() {
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
            var quantity = document.getElementById("market.sell.quantity").value;

            var ret = false;
            if (resourceId == "") {
                ret = true;
                document.getElementById("market-sell-resources-wrapper").classList.add("error")
            } else {document.getElementById("market-sell-resources-wrapper").classList.remove("error")}
            if (!quantity.match("^[0-9]+$")) {
                ret = true;
                document.getElementById("market.sell.quantity").classList.add("inputerror")
            } else {document.getElementById("market.sell.quantity").classList.remove("inputerror")}
            if (unit == "") {
                ret = true;
                document.getElementById("market-sell-unit-wrapper").classList.add("error")
            } else {document.getElementById("market-sell-unit-wrapper").classList.remove("error")}

            if (ret) {return;}

            var quantity = parseFloat(quantity)*multiplier;

            if (validateSell(parseInt(resourceId), quantity)) {
                $.post(contextPath + "/sellToMarket",
                    {
                        resourceId: resourceId,
                        quantity: quantity
                    }, function (data) {
                        var resp = JSON.parse(data);

                        var msg ;
                        if(resp.result){
                            msg = dec("game.market.sellSuccessful",abbreviateNumber(resp.quantity,false),dec(decRes(resp.resourceId)));
                        } else {
                            msg = dec("game.market.sellFail",abbreviateNumber(resp.quantity,false),dec(decRes(resp.resourceId)));
                        }
                        window.sessionStorage.setItem("message",msg);
                        location.reload();
                    });
            }
        });


    function validateSell(resourceId, quantity) {
        if (storagesMap[resourceId] >= quantity) {
            return true;
        }
        Materialize.toast(dec("game.market.sellFail",abbreviateNumber(quantity,false),dec(decRes(resourceId))), 3000);
        return false
    }
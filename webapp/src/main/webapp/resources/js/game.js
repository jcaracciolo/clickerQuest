/**
 * Created by epord on 05/04/17.
 */

var url=window.location.href.split("/")
var userId =url[url.length - 2];

var storage = document.getElementById("storage");
var production = document.getElementById("production");
var storages = [];
var productions = [];
var resources = [];

if (storage.childElementCount != production.childElementCount) {
    console.log("Not same size of production and storage!!");
}

// Init storages[] and abbreviate format of storage
var storageChild = storage.firstElementChild;
var productionChild = production.firstElementChild;
var storagesIdx = 0;
while (storageChild != undefined) {
    storages[storagesIdx] = getNumberInString(storageChild.innerHTML);
    productions[storagesIdx] = getNumberFromProduction(productionChild.innerHTML);
    resources[storagesIdx] = storageChild.innerHTML.split(/\d/)[0].trim();
    storageChild = storageChild.nextElementSibling;
    productionChild = productionChild.nextElementSibling;
    storagesIdx++;
}

refreshView(false);

function refreshView(update){
    storageChild = storage.firstElementChild;
    productionChild = production.firstElementChild;
    var i = 0;
    while (storageChild != undefined) {
        if(update){
            // var num = productions[i];
            storages[i] += productions[i];
        }

        storageChild.innerHTML = resources[i] + ' ' + abbreviateNumber(storages[i],false);
        productionChild.innerHTML = resources[i] + ' ' + abbreviateNumber(productions[i],true)+"/s";
        storageChild = storageChild.nextElementSibling;
        productionChild = productionChild.nextElementSibling;
        i++
    }
}
// Refresh storage
setInterval(function(){
    refreshView(true)}, 1000);


// Buy listener
var buyFactoryButtons = document.getElementsByClassName("buyFactoryBtn");
for (var i = 0; i < buyFactoryButtons.length; i++) {
    buyFactoryButtons[i].addEventListener("click", function () {
        console.log("buying...");
        $.post(contextPath + "/" + userId + "/buyFactory",
            {
                factoryId: getNumberInString(this.id)
            });
    })

    $("#buyFactory" + i).clickSpark({
        particleImagePath: '/resources/star_icon.png',
        particleCount: 55,
        particleSpeed: 10,
        particleSize: 12,
        particleRotationSpeed: 20,
        animationType:'explosion',
        callback: function() { location.reload() }
    });
}



// Gets all numbers of a string consecutively in an int
// QW3RT4 -> 34
function getNumberInString(str) {
    return parseInt(str.match(/\d/g).join(""))
}

function getNumberFromStorage(str) {
    var split = str.split(" ");
    return parseFloat(split[split.length-1]);
}
function getNumberFromProduction(str) {
    var split = str.split("/");
    return parseFloat(split[0]);
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
    return newValue;
}

function truncate(number)
{
    return number > 0
        ? Math.floor(number)
        : Math.ceil(number);
}

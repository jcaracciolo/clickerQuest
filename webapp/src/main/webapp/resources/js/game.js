/**
 * Created by epord on 05/04/17.
 */

var userId =window.location.href.split("/")[3];

var storage = document.getElementById("storage");
var production = document.getElementById("production");
var storages = [];

if (storage.childElementCount != production.childElementCount) {
    console.log("Not same size of production and storage!!");
}

// Init storages[] and abbreviate format of storage
var storageChild = storage.firstElementChild;
var productionChild = production.firstElementChild;
var storagesIdx = 0;
while (storageChild != undefined) {
    storages[storagesIdx] = getNumberInString(storageChild.innerHTML);
    storageChild.innerHTML = storageChild.innerHTML.split(" ")[0] + ' ' + abbreviateNumber(getNumberInString(storageChild.innerHTML));
    storageChild = storageChild.nextElementSibling;
    storagesIdx++;
}


// Refresh storage
setInterval(function () {
    storageChild = storage.firstElementChild;
    productionChild = production.firstElementChild;
    var i = 0;
    while (storageChild != undefined) {
        var rate = getNumberFromProduction(productionChild.innerHTML);
        storages[i] += rate;
        storageChild.innerHTML = storageChild.innerHTML.split(" ")[0] + ' ' + abbreviateNumber(storages[i++]);
        storageChild = storageChild.nextElementSibling;
        productionChild = productionChild.nextElementSibling;
    }
}, 1000);


// Buy listener
var buyFactoryButtons = document.getElementsByClassName("buyFactoryBtn");
for (var i = 0; i < buyFactoryButtons.length; i++) {
    buyFactoryButtons[i].addEventListener("click", function () {
        console.log("buying...");
        $.post("/" + userId +"/buyFactory",
            {
                userId: userId,
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
    }); ;
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
function abbreviateNumber(value) {
    var newValue = truncate(value);
    if (value >= 10000) {
        var suffixes = ["", "k", "m", "b","t"];
        var suffixNum = Math.floor( (""+value).length/3 );
        var shortValue = '';
        for (var precision = 2; precision >= 1; precision--) {
            shortValue = parseFloat( (suffixNum != 0 ? (value / Math.pow(1000,suffixNum) ) : value).toPrecision(precision));
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

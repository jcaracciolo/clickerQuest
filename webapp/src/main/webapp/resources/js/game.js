/**
 * Created by epord on 05/04/17.
 */



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
        var rate = getNumberInString(productionChild.innerHTML);
        storages[i] += parseInt(rate);
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
        $.post("/buyFactory",
            {
                factoryID: getNumberInString(this.id)
            }, function() { location.reload() });
    })
}
    // for (var i = 0; i < buyFactoryButtons.length; i++) {
//     buyFactoryButtons[i].addEventListener("click", function () {
//         // $.ajax({
//         //     type: "POST",
//         //     url: "submit.htm",
//         //     data: { factoryID: 1 } // parameters
//         // });


//         // var factoryCount = getNumberInString(this.id);
//         // var elementID = "factoryCant" + factoryCount;
//         // var cantFactories = parseInt(document.getElementById(elementID).innerHTML);
//         // document.getElementById(elementID).innerHTML = (cantFactories + 1).toString()
//     })
// }


function refreshPageFunc() {
    alert("Refresh!!");
}

// Gets all numbers of a string consecutively in an int
// QW3RT4 -> 34
function getNumberInString(str) {
    return parseInt(str.match(/\d/g).join(""))
}

function abbreviateNumber(value) {
    var newValue = value;
    if (value >= 1000) {
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
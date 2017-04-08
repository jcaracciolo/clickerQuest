/**
 * Created by epord on 05/04/17.
 */


// Refresh storage
setInterval(function () {
    var storage = document.getElementById("storage");
    var production = document.getElementById("production");

    if (storage.childElementCount != production.childElementCount) {
        console.log("Not same size of production and storage!!");
    }

    var storageChild = storage.firstElementChild;
    var productionChild = production.firstElementChild;
    while (storageChild != undefined) {
        var rate = getNumberFromString(productionChild.innerHTML);
        storageChild.innerHTML = storageChild.innerHTML.split(" ")[0] + ' ' + (getNumberFromString(storageChild.innerHTML) + parseInt(rate));
        storageChild = storageChild.nextElementSibling;
        productionChild = productionChild.nextElementSibling;
    }
}, 1000);


// Buy logic
var buyFactoryButtons = document.getElementsByClassName("buyFactoryBtn")
for (var i = 0; i < buyFactoryButtons.length; i++) {
    buyFactoryButtons[i].addEventListener("click", function () {
        var factoryCount = getNumberFromString(this.id)
        var elementID = "factoryCant" + factoryCount
        console.log(elementID)
        var cantFactories = parseInt(document.getElementById(elementID).innerHTML)
        document.getElementById(elementID).innerHTML = (cantFactories + 1).toString()
    })
}

// Gets all numbers of a string consecutively in an int
// QW3RT4 -> 34
function getNumberFromString(str) {
    return parseInt(str.match(/\d/g).join(""))
}
/**
 * Created by epord on 05/04/17.
 */
//
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
    return str.match(/\d/g).join("")
}
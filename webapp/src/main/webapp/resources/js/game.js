/**
 * Created by epord on 05/04/17.
 */

document.getElementById("buyFactory").addEventListener("click", function() {
    cant = parseInt(document.getElementById("factoryCant").innerHTML);
    document.getElementById("factoryCant").innerHTML = (cant++).toString();
})

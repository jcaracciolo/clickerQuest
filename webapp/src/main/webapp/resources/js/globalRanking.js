
var prev = document.getElementById("prevPage")
if (prev != null) {
    prev.addEventListener("click", function () {
        window.location.replace(contextPath + "/worldRanking/" + (pageNumber - 1))
    });
}


var next = document.getElementById("nextPage")
if (next != null) {
    next.addEventListener("click", function () {
        window.location.replace(contextPath + "/worldRanking/" + (pageNumber + 1))
    });
}
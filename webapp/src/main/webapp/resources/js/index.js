

document.getElementById("playImg").addEventListener("mousedown", function () {
    $("#play").click();
});

// register listeners
document.getElementById("register").addEventListener("mouseover", function() {
   this.classList.add("mouseOver")
});

document.getElementById("register").addEventListener("mouseout", function() {
   this.classList.remove("mouseOver")
});
document.getElementById("register").addEventListener("click", function() {
    window.location = contextPath + "/create"
});

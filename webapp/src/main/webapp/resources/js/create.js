

document.getElementById("register").addEventListener("click", function () {
    document.getElementById("registerForm").submit()
})
window.onkeyup = function(e) {
    var key = e.keyCode ? e.keyCode : e.which;

    if (key == 13) {
        document.getElementById("registerForm").submit()
    }
};
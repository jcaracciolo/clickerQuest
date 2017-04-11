/**
 * Created by epord on 4/11/17.
 */

// play click listener
document.getElementById("play").addEventListener("mousedown", function () {
    this.src = "/resources/play_button_pressed.png"

});
document.getElementById("play").addEventListener("mouseup", function () {
    var username = document.getElementById("usernameInput").value;
    console.log("user: " + username);
    this.src = "/resources/play_button.png"
    $.post("/startGame",
        {
            username: username
        }, function(data, status) { this.src = "/resources/play_button.png" });
});

// register listeners
document.getElementById("register").addEventListener("mouseover", function() {
   this.classList.add("mouseOver")
});

document.getElementById("register").addEventListener("mouseout", function() {
   this.classList.remove("mouseOver")
});
document.getElementById("register").addEventListener("click", function() {
    $.post("/register",
        {
        }, function(response, textStatus, xhr) {
        console.log(textStatus);
        console.log(xhr.getAllResponseHeaders())
        console.log(response)
    });
});
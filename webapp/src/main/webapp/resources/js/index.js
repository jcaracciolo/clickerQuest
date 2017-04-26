/**
 * Created by epord on 4/11/17.
 */
console.log("version 1.1");

// play click listener
document.getElementById("play").addEventListener("mousedown", function () {
    this.src = contextPath + "/resources/play_button_pressed.png"

});
document.getElementById("play").addEventListener("mouseup", tryLogIn);

$('#usernameInput').on('keydown', function(e) {
    if (e.which == 13) {
        e.preventDefault();
        tryLogIn();
    }
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

function tryLogIn(){
    var username = document.getElementById("usernameInput").value;
    console.log("user: " + username);
    this.src = contextPath + "/resources/play_button.png"
    this.src = "/resources/play_button.png"
    sessionStorage.user = username;

    window.location = contextPath + "/startGame?username=" + username
    // $.get("/startGame",
    //     {
    //         username: username
    //     }, function(data, status) { this.src = "/resources/play_button.png" });
}

function appendError(elem, msg) {
    var volatileId = "error" + Math.floor(Math.random() * 1000000);


    elem.after("<div class='card mypopout volatilemypopout' id='" + volatileId + "'>" + msg + "</div>");
    elem.addClass("volatileHandler" + volatileId);
    var errordiv = $("#" + volatileId);
    errordiv.hide();
    errordiv.css({

        position: "relative",
        left: "auto",
        top: "auto",
        margin:"auto",
        "margin-top": "0.5em",
        "background-color": "red",
        width:"15em"
    });

    setTimeout(function () {
        errordiv.show(200);
        $(".volatileHandler" + volatileId).focus(function () {
            errordiv.slideUp(200);
            setTimeout(function () {
                //todo if you want to delete red banner when focus in here is where to do it
                elem.removeClass("invalid");
                errordiv.remove();
                $(".volatileHandler" + volatileId).off('focus').removeClass("volatileHandler" + volatileId);
            }, 200);
        });
    }, 200);

}

if( sessionStorage.user != null){
    if(sessionStorage.user.length > 0){
        appendError($("#usernameInput"),"The user \""+sessionStorage.user+"\" does not exist");
    } else {
        appendError($("#usernameInput"),"Please enter a username or register!");
    }
    sessionStorage.removeItem("user");
}



$(":input[type='submit']").click(function() {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        var repeatPass = document.getElementById("repeatPassword").value;


                          });

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

//
// var username;
// var password;
// var repeatPass;
//
//
// $(":input[type='submit']").click(function() {
//         validateData();
// });
//
// $('#usernameInput').on('keydown', function(e) {
//     if (e.which == 13) {
//         e.preventDefault();
//         validateData();
//     }
// });
//
// $("#username").focusout(validateUsername);
// $("#password").focusout(validatePassword);
// $("#repeatPassword").focusout(validateRepeatPassword);
//
// if( sessionStorage.usernameReg != null){
//     document.getElementById("username").value = sessionStorage.usernameReg;
//     if(getQueryParams("error") && validateUsername())  appendError($("#username"),"Nombre de usuario ya existente!");
// }
// function getQueryParams(qs) {
//     qs = qs.split('+').join(' ');
//
//     var params = {},
//         tokens,
//         re = /[?&]?([^=]+)=([^&]*)/g;
//
//     while (tokens = re.exec(qs)) {
//         params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
//     }
//
//     return params;
// }
//
// function validateData(){
//     var allValid = true;
//     if(!validateUsername()) allValid = false;
//     if(!validatePassword()) allValid = false;
//     if(!validateRepeatPassword()) allValid = false;
//     if(!allValid) {
//         sessionStorage.usernameReg = username;
//     }
// }
// function validateRepeatPassword(){
//     var allValid = true;
//     password = document.getElementById("password").value;
//     repeatPass = document.getElementById("repeatPassword").value;
//     if(password != repeatPass){
//         appendError($("#repeatPassword"),"Las contrase&#241;as no concuerdan!");
//         allValid= false;
//     }
//     return allValid;
// }
//
// function validatePassword(){
//     var allValid = true;
//     password = document.getElementById("password").value;
//     if(password.length === 0) {
//         appendError($("#password"),"La contrase&#241;a con letras y numeros por favor");
//     }
//     if(!passRegex.test(password)){
//             appendError($("#password"),"La contrase&#241;a con letras y numeros por favor");
//             allValid= false;
//     }
//     if(password.length < minPassLen || password.length > maxPassLen){
//         appendError($("#password"),"El largo de la contrase&#241;a debe tener mas de " + minPassLen +
//                         " y menos de "+ maxPassLen+" caracteres");
//         allValid= false;
//     }
//     return allValid;
// }
//
// function validateUsername(){
//     var allValid = true;
//     username = document.getElementById("username").value;
//
//     if(!userRegex.test(username)){
//         appendError($("#username"),"El nombre de usuario con letras y numeros por favor");
//         allValid= false;
//     }
//     if(username.length < minUserLen || username.length > maxUserLen){
//         appendError($("#username"),"El largo del nombre de usuario debe tener mas de " + minUserLen +
//                         " y menos de "+ maxUserLen+" caracteres");
//         allValid= false;
//     }
//         return allValid;
//
// }
//
//
// function appendError(elem, msg) {
//     var volatileId = "error" + Math.floor(Math.random() * 1000000);
//
//     elem.after("<div class='card mypopout volatilemypopout' id='" + volatileId + "'>" + msg + "</div>");
//     elem.addClass("volatileHandler" + volatileId);
//     var errordiv = $("#" + volatileId);
//     errordiv.hide();
//     errordiv.css({
//
//         position: "relative",
//         left: "auto",
//         top: "auto",
//         margin:"auto",
//         "margin-top": "0.5em",
//         "background-color": "red",
//         width:"15em"
//     });
//
//     setTimeout(function () {
//         errordiv.show(200);
//         $(".volatileHandler" + volatileId).focus(function () {
//             errordiv.slideUp(200);
//             setTimeout(function () {
//                 //todo if you want to delete red banner when focus in here is where to do it
//                 elem.removeClass("invalid");
//                 errordiv.remove();
//                 $(".volatileHandler" + volatileId).off('focus').removeClass("volatileHandler" + volatileId);
//             }, 200);
//         });
//     }, 200);
//
// }

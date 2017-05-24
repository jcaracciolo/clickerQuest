

$('.modal').modal();

var clan = document.getElementById("clanLink");
if(clan != null) {
    clan.addEventListener("click", function () {
        console.log("redirigiendo...!")
        window.location.replace(contextPath + "/clan/" + clan.dataset.clanname)
    })
}
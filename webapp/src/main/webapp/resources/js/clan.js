
document.getElementById("clan-action").addEventListener("click", function () {
    switch (document.getElementById("clan-action").dataset.action) {
        case "join":
            $.post(contextPath + "/joinClan",
                {
                    clanName: clanName
                }, function(data) {
                    window.location.replace(contextPath + "/clan/" + clanName);
                });
            break;
        case "leave":
            $.post(contextPath + "/leaveClan",
                {
                    clanName: clanName
                }, function(data) {
                    window.location.replace(contextPath + "/game");
                });
            break;
    }
});

var users = document.getElementsByClassName("username-link");
for(var i = 0; i<users.length; i++) {
    var user = users.item(i)
    user.addEventListener("click", function () {
        console.log(contextPath + "/u/" + user.dataset.username);
        window.location.replace(contextPath + "/u/" + user.dataset.username)
    })
}
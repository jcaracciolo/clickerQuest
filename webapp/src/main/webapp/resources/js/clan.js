
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
                    window.location.replace(contextPath + "/clan/" + clanName);
                });
            break;
    }
});
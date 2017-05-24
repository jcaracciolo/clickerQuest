
var clanImageSrc = "https://carlaspeaks.files.wordpress.com/2012/07/c1.png";
var userImageSrc = "http://www.drodd.com/images14/u27.jpg";
var autocomplete_id;


$(document).ready(function(){
    startAutocomplete($("#search"))
});

function startAutocomplete(id) {
    autocomplete_id = id;
    autocomplete_id.autocomplete();
    autocomplete_id.on('change textInput input', function () {
        var searchTerm = this.value;
        if(searchTerm.length > 2){
            searchCommunity();
        }
    });
}

var searchCommunity = function () {
// Create clan listener
    var search = autocomplete_id.val();
    console.log("Searching clan: " + search);
    $.post(contextPath + "/search",
        {
            search: search
        }, function(data) {
            var resp = JSON.parse(data);
            var autoData = {};
            $('#search').autocomplete({
                data: {}});
            var temp;
            for(var i=0;i<resp.users.length;i++){
                autoData[resp.users[i]]=userImageSrc;
            }
            for(var i=0;i<resp.clans.length;i++){
                autoData[resp.clans[i]+' ']=clanImageSrc;
            }
            $('#search').autocomplete({
                data: autoData,
                onAutocomplete: function(val) {
                    // Callback function when value is autcompleted.
                    if(this.firstChild.src === userImageSrc){
                        window.location = contextPath + "/u/" + val.trim();
                    } else {
                        window.location = contextPath + "/clan/" + val.trim();
                    }
                }
            });
        });
}
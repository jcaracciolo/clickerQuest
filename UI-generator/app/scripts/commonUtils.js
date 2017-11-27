

function getParameterByName(name, url) {
  if (!url) url = window.location.href;
  name = name.replace(/[\[\]]/g, "\\$&");
  var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
    results = regex.exec(url);
  if (!results) return null;
  if (!results[2]) return '';
  return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function getClanImage(clanId) {
  return "images/clan_icons/" + (clanId % 8 + 1) + ".jpg"
}

function formatDecimal(num, decimals) {
  factor = 10 * decimals;
  return parseFloat(Math.round(num * factor) / factor).toFixed(decimals);
}
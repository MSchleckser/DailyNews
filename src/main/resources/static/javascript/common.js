function clearCookies(){
    setCookie("stayLoggedIn", "0", -1);
    setCookie("username", "", -1);
    setCookie("password", "", -1);
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

$("#logout").click(function(){
    clearCookies();
    $.get("/logout");
    window.location = "/";
});
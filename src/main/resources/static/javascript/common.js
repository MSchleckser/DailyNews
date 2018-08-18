$("#logout").click(function(){
    clearCookies();
    $.get("/logout");
    window.location = "/";
});

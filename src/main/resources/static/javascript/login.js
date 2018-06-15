
$(document).ready(function(){
});

$("#loginBtn").click(function(event){
    var username = $("#username").val();
    var password = $("#password").val();
    login(username, password);
    event.preventDefault();
    
});

function login(username, password){
    $.post("login?user=" + username + "&pass=" + password, function(data){
        if(data == "success"){
            console.log(document.getElementById("stay-loggedIn").checked);
            updateCookies(document.getElementById("stay-loggedIn").checked);
            window.location = "/";
        } else {
            displayError(data);
        }
    });
}

function updateCookies(stayLoggedIn){
    if(stayLoggedIn){
        setCookie("stayLoggedIn", "1", 30);
        setCookie("username", username, 30);
        setCookie("password", password, 30);
    } else {
        setCookie("stayLoggedIn", "0", -1);
        setCookie("username", username, -1);
        setCookie("password", password, -1);
    }
}

$("#errorMessage").click(function(){
    $("#errorMessage").text("");
    $("#password").val("");
});

function displayError(errorMessage){
    document.getElementById("errorMessage").innerText = errorMessage;
}

//https://stackoverflow.com/questions/31542032/how-to-add-default-values-to-input-fields-in-thymeleaf?rq=1
//Many thanks to Kingamere on stack overflow.
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function getQueries(){
    let errors = getParameterByName("errors");
    let username = getParameterByName("username");

    displayError(errors);
    document.getElementById("username").value = username;
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
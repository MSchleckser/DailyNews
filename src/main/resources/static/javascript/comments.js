let commentsPath = "article/rest/comments/";
let rolePath = "login/getRole";
let role = "";

let id = 0;
$(document).ready(function(){
    id = getArticleIdFromUrl();

    $.get(rolePath, function(data){
        role = data;
    });

    $.get(commentsPath + id, function(data){
        let article = parseArticleData(data);
        displayArticleData(article); 
        createPostForm();
        if(role == "ADMIN") {
            displayDeleteGlyph();
        }
    })
});

function displayDeleteGlyph(){

    var comments = $(".comment");

    for(var i = 0; i < comments.length; i++){
        var id = comments.eq(i).attr("id");
        $(".comment").get(i).append(getDeleteGlyph(id).get(0));
    }
}

function getDeleteGlyph(id){
    var div = $("<div></div>").attr("class", "glyphicon glyphicon-remove");
    div.css("font-size", "1.8em");

    div.attr("onclick", "removeComment(" + id + ")");

    return div;
}

function getArticleIdFromUrl(){
    let url = window.location.href;
    let query = url.split("?")[1];
    let id = query.split("=")[1];
    return id;
}

function parseArticleData(data){
    let retArticle;

    let xmlDoc = $.parseXML(data);
    $xml = $( xmlDoc );
    let element = $xml.find("Container");
    let article = new Article(element);
    parseCommentData(article, element);
    return article;
}

function parseCommentData(article, element){
    $.each($(element).find("Comment"), function(index, value){
        article.addComment(value);
    });
}

function displayArticleData(article){
    $("#header").text(article.title + " - " + article.publisher).css("font-size", "2.5em");
    
    article.comments.forEach(function(value){
        displayComment(value);
    });
}

function displayComment(comment){
    var div = $("<div></div>").attr("id", comment.id);
    div.attr("class", "container comment");
    div.css("margin-top", "2%");
    div.css("border-top", "1px solid");
    div.css("padding", "1.5%");

    var body = $("<div></div>").attr("class", "row");
    body.text(comment.body);
    body.css("font-size", "1.5em");

    var footer = $("<div></div>").attr("class", "row");
    footer.text("Poster: " + comment.user);

    div.append(body);
    div.append(footer);
    $("#comments").append(div);
    return div;
}

function createPostForm(){
    $.get("login/getRole", function(data){
        if(data == "Not logged in."){
            loggedOutForm();
        } else {
            loggedInForm();
        }
    });
}

function loggedInForm(){
    var form = $("<form></form>");
    var div = $("<div></div>");
    var textBox = $("<textarea></textarea>").attr("rows", 8);
    textBox.attr("cols", 80);
    textBox.attr("id", "commentbody");
    textBox.css("margin-top", "3%");

    div.append(textBox);
    form.append(div);

    div = $("<div></div>").css("margin-top", "3%").css("width", "40%");
    var btn = $("<input></input>").attr("type", "submit").attr("class", "form-control");
    div.append(btn);
    form.append(div);

    btn.click(function(event){
        event.preventDefault();
        postComment();
    });

    $("#PostForm").append(form);
}

function loggedOutForm(){
    var div = $("<h2></h2>").text("Log in to post a comment");
    $("#PostForm").append(div);
}

function postComment(){
    var text = $("textarea").val();
    $("textarea").val("");

    $.post("article/CreateComment", {ArticleId: id, body: text}, function(id){
        $("textarea").val();

        $.get("login/getUsername", function(username){
            var comment = new Comment();
            comment.id = id;
            comment.user = username;
            comment.body = text;

            var commentDiv = displayComment(comment);

            if(role == "ADMIN") {
                let div = getDeleteGlyph(id);
                commentDiv.append(div);
            }
        });
    });
}

function removeComment(id){
    $.post("article/removeComment", {id: id}, function(data){
        if(data == "success"){
            $("#"+id).remove();
        } else {
            alert(data);
        }
    });
}

$("#logout").click(function(){
    clearCookies();
    $.get("/logout");
    window.location = "/";
});

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

class Article {
    constructor(element){
        this.id = $(element).find("ID").text();
        this.title = $(element).find("Title").text();
        this.publisher = $(element).find("Publisher").text();
        this.comments = [];
    }

    addComment(element){
        var comment = new Comment(element);
        this.comments.push(comment);
    }
}

class Comment {
    constructor(element){
        this.id = $(element).find("ID").text();
        this.user = $(element).find("User").text();
        this.body = $(element).find("Body").text();
    }
}
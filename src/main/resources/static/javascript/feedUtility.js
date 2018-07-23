

function createTableRow(element){
    let feedRow = "<tr id=\"" + element.id + "\" scope = \"row\">";

    feedRow += "<td>" + element.publisher + "</td>";
    feedRow += "<td>" + element.title + "</td>";

    if(element.has){
        feedRow += "<td class=\"button\">" + "<span class=\"glyphicon glyphicon-ok\" onclick = unsubscribe(" +element.id + ") />" + "</td>";
    } else {
        feedRow += "<td class=\"button\">" + "<span class=\"glyphicon glyphicon-remove\" onclick = subscribe(" + element.id + ") />" + "</td>";
    }

    feedRow += "</tr>";

    $(".table").append(feedRow);
}

function subscribe(id){
    $.get("/rest/managefeeds/user/subscribe", {id: id}, function(success){
        var button = $("#" + id).children(".button").children("span");
        button.val("Unsubscribe");
        button.attr("onClick", "unsubscribe(" + id + ")");
        button.removeClass("glyphicon glyphicon-remove");
        button.addClass("glyphicon glyphicon-ok");
    });
}

function unsubscribe(id){
    $.get("rest/managefeeds/user/unsubscribe", {id: id}, function(success){
        if(success) {
            var button = $("#" + id).children(".button").children("span");
            button.val("Subscribe");
            button.attr("onClick", "subscribe(" + id + ")");
            button.removeClass("glyphicon glyphicon-ok");
            button.addClass("glyphicon glyphicon-remove");
        }
    });
}

function fetchFeeds(data){
    var retFeeds = [];

    xmlDoc = $.parseXML(data);
    $xml = $( xmlDoc );

    $links = $xml.find("links");
    
    $("link", $links).each(function(i, element) {
        var nfeed = new Feed(element);
        retFeeds.push(nfeed);
    })

    return retFeeds;
}

function fetchSubscribedIds(data){
    var ids = [];
    
    xmlDoc = $.parseXML(data);
    $xml = $( xmlDoc );

    $xmlIds = $xml.find("feedIds");

    $("id", $xmlIds).each(function(i, element){
        ids.push($(element).text());
    });
        
    return ids;
}

class Feed {

    constructor(element){
        this.id= $(element).find("id").text();
        this.title= $(element).find("title").text();
        this.publisher= $(element).find("publisher").text();
        this.has = false;
    }

    checkSubscribed(subscribedIds) {
        subscribedIds.forEach((subId) => {
            if(subId == this.id){
                this.has = true;
                return;
            }
        });
    }
}
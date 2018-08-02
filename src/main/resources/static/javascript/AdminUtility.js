

    $(document).ready(function(){

        $.get("rest/feed/all", function(data){
            var list = fetchFeeds(data);
            for(p in list){
                createPublisherElements(list[p]);
            }
        });
    });

    $("#addPub").click(function(event) {
        var publisherTitle = $("#pubName").val();

        $.post("admin/addPublisher", {publisher : publisherTitle}, function(data){
            if(isNaN(data)){
                displayError(data);
            } else {
                var element = createPublisherElement(publisherTitle, data);
                element[0].scrollIntoView();

                $("#pubName").val("");
            }
        });
        
        event.preventDefault();
    });
    
    $("#errorMessage").click(function() {
        $("#errorMessage").text("");
        $("#password").val("");
    });

    $("#addLinkSub").click(addFeed);

    function displayError(errorMessage) {
        document.getElementById("errorMessage").innerText = errorMessage;
    }

    function createPublisherElement(publisherTitle, publisherId){
        var removePublisherGlyph = $("<span></span>").attr("class", 'glyphicon glyphicon-remove removePublisher');
        removePublisherGlyph.attr("onclick","removePublisher("+publisherId+")");

        $("#publisherDd").append($("<option id=" + publisherId + "></option>").html(publisherTitle).val(publisherId));

        $("#removeLinkForm").append($("<div class='card card-body'></div>").attr("id", publisherId).append($("<h1></h1>").text(publisherTitle)));

        return $("#removeLinkForm #" + publisherId).append(removePublisherGlyph).append("<ul class='list-group'></ul>");
    }

    function createPublisherElements(publisher) {
        createPublisherElement(publisher.title, publisher.id);

        for(var l in publisher.feeds){
            var feed = publisher.feeds[l];
            createFeedElement(publisher.id, feed);
        }

    }

    function createFeedElement(publisherId, feed){
            var glyph = $("<span></span>").attr("class", 'glyphicon glyphicon-remove');
            glyph.attr("onclick", "removeFeed(" + publisherId + ", " + feed.id + ")");

            var listItem = $("<li></li>").text(feed.title)
            listItem.attr("id", feed.id);
            listItem.attr("class","list-group-item");

            $("#removeLinkForm #" + publisherId + " ul").append(listItem);

            listItem.append(glyph);
            return listItem;
    }

    function fetchFeeds(data) {
        var retPublishers = [];

        xmlDoc = $.parseXML(data);
        $xml = $( xmlDoc );

        $publishers = $xml.find("subscriptions");
        
        $("publisherXml", $publishers).each(function(i, element) {
            var nPub = new Publisher(element);

            var i = 0;
            $(element).find("link").each(function(i, element) {
                i++;
                var feed = new Feed(element);
                nPub.feeds.push(feed);
            });
            retPublishers.push(nPub);
        })

        return retPublishers;
    }

    function removePublisher(id) {
        $.post("/admin/removePublisher", {id: id}, function(data){
            if(data == "deleted"){
                $("#publisherDd #" + id).remove();
                $("#removeLinkForm #"+id).remove();
            }
        });
    }

    function removeFeed(pubId, feedId) {
        $.post("/admin/removeFeed", {id: feedId}, function(data){
            if(data == "deleted"){
                $("#removeLinkForm" + " #" + pubId + " ul" + " #"+feedId).remove();
            }
        });
    }

    function addFeed(event){
        var publisherId = $("#publisherDd option:selected").attr("value");
        var feedTitle = document.getElementById("feedTitle").value;
        var feedLink = document.getElementById("feedLink").value;

        $.post("admin/addFeed", {publisherId: publisherId, title: feedTitle, link: feedLink}, function(data){
            if(isNaN(data)){
                displayError(data);
            } else {
                var listItem = createFeedElement(publisherId, new Feed(data, feedTitle));
                listItem[0].scrollIntoView();

                $("#feedTitle").val("");
                $("#feedLink").val("");
            }
        });
        event.preventDefault();
    }

    class Publisher {
        constructor(element) {
            this.id = $(element).find("id").first().text();
            this.title = $(element).find("name").text();
            this.feeds = [];
        }
    }

    class Feed {
        constructor(element, title) {
            if(title === undefined) {
                this.id = $(element).find("id").text();
                this.title = $(element).find("title").text();
            } else {
                this.id = element;
                this.title = title;
            }
        }
    }
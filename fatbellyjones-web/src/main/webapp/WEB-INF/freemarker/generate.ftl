<#import "layout/adminlayout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js", "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js" "/js/jquery.ui.datepicker.js"] 
    css=["/css/admin.css" "/css/fullcalendar.css" "/css/jquery-ui-1.8.4.custom.css"]
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">

<script type="text/javascript">
var arr = [["#twitter-tweet", "Twitter tweet", "${rc.contextPath}/admin/protected/json/artifact/twitter-tweet/create.json?id=${event.id}"], ["#facebook-image", "Facebook image", "${rc.contextPath}/admin/protected/json/artifact/facebook-image/create.json?id=${event.id}"], ["#facebook-event", "Facebook event", "${rc.contextPath}/admin/protected/json/artifact/facebook-event/create.json?id=${event.id}"], ["#poster", "poster", "${rc.contextPath}/admin/protected/json/artifact/poster/create.json?id=${event.id}"], ["#confirm", "confirmed", "${rc.contextPath}/admin/protected/json/event/confirm.json?id=${event.id}"]];

var facebookImage = "";

$(document).ready(function() {
  loadArtifacts(arr, 0);
});

function loadArtifacts(loadArray, index) {
    $(loadArray[index][0]).html("<p>Initialising " + loadArray[index][1] + " <img src=\"${rc.contextPath}/images/ajax-loader.gif\"/></p>");
     $.ajax( {
              type : 'POST',
              url : loadArray[index][2],
              dataType : 'json',
              data : { "eventId" : "${event.id}", "image" : facebookImage, "force" : "true" },
              success : function(data) {
				if (data.result == "success") {
                   facebookImage = data.link;
                   $(loadArray[index][0]).html("<p>Successfully created " + loadArray[index][1] + " <a href=\"" + data.link + "\">View</a></p>");
                   if (index < (loadArray.length -1)) {
                       loadArtifacts(loadArray, (1+index));
                   } else {
                       var timeoutID = window.setTimeout(function() {
                       $(location).attr("href", "${rc.contextPath}/admin/protected/gig/${event.id}.html");
                       }, 10000);
                   }
                } else {
                   $(loadArray[index][0]).html("<p>There was an error creating " + loadArray[index][1] + ".</p>");
                }
			 },
			 error : function(XMLHttpRequest, textStatus, errorThrown) {
				$(loadArray[index][0]).html("<p>There was an error creating " + loadArray[index][1] + ".</p>");
			 }
     });
     
}
</script>

<div id="add-event-wrapper">
   <div id="event-inner">
       <h4>Creating artifacts</h4>
         <div class="gig-div" id="twitter-tweet">
             <p>Waiting to Tweet on Twitter.</p>
         </div>
         <div class="gig-div" id="facebook-image">
             <p>Waiting to generate Facebook image.</p>
         </div>
         <div class="gig-div" id="facebook-event">
             <p>Waiting to generate Facebook event.</p>
         </div>
         <div class="gig-div" id="poster">
             <p>Waiting to generate poster.</p>
        </div>
        <div id="redirect">
             
        </div>
   </div>
</div>
</@security.authorize>

</@layout.admin>

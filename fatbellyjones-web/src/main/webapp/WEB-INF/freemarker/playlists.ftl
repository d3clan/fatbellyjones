<#import "layout/adminlayout.ftl" as layout>
<#import "/spring.ftl" as spring>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js" "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js" "/js/jquery.ui.datepicker.js" "/js/jquery.form.js" "/js/jquery.hoverIntent.min.js" "/js/jquery.metadata.js" "/js/mbExtruder.js" "/js/jquery.mb.flipText.js"] 
    css=["/css/admin.css" "/css/fullcalendar.css" "/css/jquery-ui-1.8.4.custom.css" "/css/mbExtruder.css"]          
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">


<script type="text/javascript">

var extruder = null;

$(document).ready(function() {
    loadExtruder();
    $("#play-list-items").sortable({ 
       handle : '.handle', 
       update : function (data) {
          var order = $('#play-list-items').sortable('serialize');
          $.ajax( {
          type : 'POST',
          url : '${rc.contextPath}/admin/protected/json/playlist/order.json',
          dataType : 'json',
          data: {'eventId' : '${event.id}', 'serializedOrder' : order},
          success : function(data) {
          $.ajax( {
            type : 'POST',
            url : '${rc.contextPath}/admin/protected/json/ordered.json',
            dataType : 'json',
            data: {'eventId' : '${event.id}'},
            success : function(data) { updatePlaylist(data); },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
               $('#remaininglist').html("<p>An error has occured. Could not download the tunes.</p>");
               return new Array();
              }
            });
          },
          error : function(XMLHttpRequest, textStatus, errorThrown) {
            $('#remaininglist').html("<p>An error has occured. Could not download the tunes.</p>");
            return new Array();
         }
       });
       } 
    });
    
});

function loadExtruder() {
    $("#extruderSide").buildMbExtruder({
      positionFixed:true,
      width:208,
      sensibility:800,
      position:"left",
      extruderOpacity:1,
      flapDim:100,
      textOrientation:"bt",
      onExtOpen:function(){},
      onExtContentLoad:function() {},
      onExtClose:function(){},
      hidePanelsOnClose:true,
      autoCloseTime:0,
      slideTimer:300
    });
  }

</script>

<div id="playlist">
<p>To add tunes, open the Tune Panel by selecting the tab on the left, selecting the green + buttons to add. Use the red - buttons in the main display to remove a tune.</p>
<p>Note: <em>you can edit tunes by selecting the title in the Tune Panel.</em></p>
<p><strong>To order the tunes, hover your mouse over the title or list number and drag n' drop</strong></p>
<h3>Playlist for <a href="${rc.contextPath}/admin/protected/gig/${event.id}.html">${event.title}</a></h3>
<p><a href="${rc.contextPath}/admin/protected/pdf/playlist/${event.id}.pdf">Download as PDF</a></p>
<ul id="play-list-items">
<#assign count = 0/>
	<#list event.playlist as item>
	    <#if (item.tune.status != "break")>
	        <#assign count = count + 1/>	        
	    </#if>
	    <li id="listItem_${item.tune.id?c!}" class="playlist ${item.tune.status}"><#if (item.tune.status != 'break')><span class="handle number">${count}</span></#if><span class="handle title">${item.tune.title!} - ${item.tune.artist!}</span><span class="remove-span"><a href="#" id="order-${item.tune.id?c!}" class="remove-tune"><img src="${rc.contextPath}/images/remove.png"/></a></span></li>
	</#list>
</ul>
</div>
<div id="extruderSide" class="{title:'Tune Panel', url:'${rc.contextPath}/admin/protected/playlisttunes.html?id=${event.id}' }">
</div>
</@security.authorize>
</@layout.admin>
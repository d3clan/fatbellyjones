<#import "/spring.ftl" as spring>

<h4>Available tunes</h4>
<div id="remaininglist">
 <#list tunes as tune>
   <div class="tune ${tune.status}">
       <span><a href="${rc.contextPath}/admin/protected/tune/edit/${tune.id?c}.html">${tune.title}</a> </span><br/>
       <span>${tune.artist}</span> <a href="#" id="tune-${tune.id?c}" class="add-tune"><img src="${rc.contextPath}/images/add.png"/></a><br/>
   </div>
 </#list>
</div>

<script type="text/javascript">

$(document).ready(function() {
    activateClicks();
});

function activateClicks() {
    $('.add-tune').click(function() {
       var tune = this.id.split('-')[1];
       $.ajax( {
	       type : 'POST',
	       url : '${rc.contextPath}/admin/protected/json/playlist/add.json',
	       dataType : 'json',
	       data: {'eventId' : '${event.id}', 'tuneId' : tune },
	       success : function(data) { updatePlaylist(data) },
           error : function(XMLHttpRequest, textStatus, errorThrown) {
	            $('#remaininglist').html("<p>An error has occured. Could not download the tunes.</p>");
	            return new Array();
             }
       });
       return false;
    });
    
    $('.remove-tune').click(function() {
       var position = this.id.split('-')[1];
       $.ajax( {
	       type : 'POST',
	       url : '${rc.contextPath}/admin/protected/json/playlist/remove.json',
	       dataType : 'json',
	       data: {'eventId' : '${event.id}', 'position' : position },
	       success : function(data) { updatePlaylist(data) },
           error : function(XMLHttpRequest, textStatus, errorThrown) {
	            $('#remaininglist').html("<p>An error has occured. Could not download the tunes.</p>");
	            return new Array();
             }
       });
       return false;
    });
}

function updateRemaining() {
   $.ajax( {
       type : 'POST',
       url : '${rc.contextPath}/admin/protected/json/tunes/remaining.json',
       dataType : 'json',
       data: {'eventId' : '${event.id}'},
       success : function(data) {updateRemainingFields(data);},
       error : function(XMLHttpRequest, textStatus, errorThrown) {
            $('#remaininglist').html("<p>An error has occured. Could not download the tunes.</p>");
            return new Array();
         }
   });
}
    
function updatePlaylist(data) {
     var counter = 0;
      for (i=0;i<data.length;i++) {
          var dat = data[i];
          if (dat.className != "break") {
             counter++;
          }
          if (i == 0) {
              $('#play-list-items').html(getTuneDiv(i, counter, data[i]));
          } else {
              $('#play-list-items').append(getTuneDiv(i, counter, data[i]));
          }
      }
    updateRemaining();
}
    
function updateRemainingFields(data) {
    if (data.length == 0) {
       $('#remaininglist').html("<p>No tunes to show</p>");
    } else {
       for (i=0;i<data.length;i++) {
         if (i == 0) {
             $('#remaininglist').html(getPlaylistDiv(data[i]));
         } else {
             $('#remaininglist').append(getPlaylistDiv(data[i]));
          }
        }
    }
    activateClicks();
    
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
}

function getTuneDiv(index, counter, data) {
    var str = '<li id="listItem_' + data.id + '" class="playlist ' + data.className + '">';
    if (data.className != "break") {
       str += '<span class="handle number">' + counter + '</span>';
    }
    str += '<span class="handle title">' + data.title + ' - ' + data.artist + '</span><span class="remove-span"><a href="#" id="order-' + data.id + '" class="remove-tune"><img src="${rc.contextPath}/images/remove.png"/></a></span></li>';
    return str;
}

function getPlaylistDiv(data) {
    return '<div class="tune ' + data.status + '"><span><a href="${rc.contextPath}/admin/protected/tune/edit/' + data.id + '.html">' + data.title + '</span><br/><span>' + data.artist + '</span><span><a href="#" id="tune-' + data.id + '" class="add-tune"><img src="${rc.contextPath}/images/add.png"/></a></span><br/></div>';
}
    
</script>

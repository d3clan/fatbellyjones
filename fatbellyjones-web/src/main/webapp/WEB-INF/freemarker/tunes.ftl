<#import "layout/adminlayout.ftl" as layout>
<#import "/spring.ftl" as spring>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js" "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js" "/js/jquery.ui.datepicker.js" "/js/jquery.form.js"] 
    css=["/css/admin.css" "/css/fullcalendar.css" "/css/jquery-ui-1.8.4.custom.css"]          
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">

<script type='text/javascript'>

$(document).ready(function() {

    var dialogOpts = {
        title: "Add tune",
        modal: true,
        autoOpen: false,
        height: 200,
        width: 500,
        resizable : false,
        open: function() {
        //display correct dialog content
        $("#dialog").load("${rc.contextPath}/admin/protected/addtuneform.html");}
    };
        
    $("#dialog").dialog(dialogOpts);    //end dialog
    
    $('.addtune').click(
        function (){
            $("#dialog").dialog("open");
            return false;
        }
    );
    
    updateTunes();
});

function updateTunes() {
$.ajax( {
              type : 'POST',
              url : '${rc.contextPath}/admin/protected/json/tunes/list.json',
              dataType : 'json',
              success : function(data) {
                  for (i=0;i<data.length;i++) {
                     if (i == 0) {
                        $('#tuneslist').html(getTuneDiv(data[i]));
                     } else {
                         $('#tuneslist').append(getTuneDiv(data[i]));
                     }
                  }
			 },
			 error : function(XMLHttpRequest, textStatus, errorThrown) {
				$('#tuneslist').html("<p>An error has occured. Could not download the tunes.</p>");
			 }
     });
}

function getTuneDiv(data) {
    var retVal = "<div class=\"tune " + data.status + "\"><span><a href=\"${rc.contextPath}/admin/protected/tune/edit/" + data.id + ".html\">" +  data.title + "</a> </span><br/><span>" + data.artist + " </span>";
    if (data.countIn) {
        retVal += "<span> Count in: " + data.countIn + "</span>"; 
    }
    if (data.key) {
        retVal += "<span> Key: " + data.key  + "</span>";
    }
    return retVal + "</div>";
}
</script>
<div id="status-div">
<p>Legend</p>
<#list status as st>
    <span class="status ${st}">${st}</span>
</#list>
</div>
<div class="addtune-div">
<a class="addtune fatbelly-button" href="${rc.contextPath}/admin/protected/tune/add.html">Add tune</a>
</div>
<div id="tuneslist">

</div>
<div class="addtune-div">
<a class="addtune fatbelly-button" href="${rc.contextPath}/admin/protected/tune/add.html">Add tune</a>
</div>
<div id="dialog"></div>
</@security.authorize>
</@layout.admin>
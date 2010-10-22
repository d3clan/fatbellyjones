<#import "layout/adminlayout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js", "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js" "jquery.ui.datepicker.js"] 
    css=["/css/admin.css" "/css/fullcalendar.css" "/css/jquery-ui-1.8.4.custom.css"]
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">

<script type='text/javascript'>
   $(function() {
        $("#from-date").datepicker({ dateFormat: 'dd/mm/yy' });
	});
	
	$(function() {
	    $("#to-date").datepicker({ dateFormat: 'dd/mm/yy' });
	});
   
</script>

<div id="add-event-wrapper">
   <form action="${rc.contextPath}/admin/protected/events/save-event.html" method="post">
      <div id="event-inner">
         <label for="title">Title:</label><input type="text" id="title" name="title" /><br/>
         <label for="from-date">From:</label><input type="text" id="from-date" name="from-date" value="${from}" /><br/>
         <label for="to-date">To:</label><input type="text" id="to-date" name="to-date" value="${to}" /><br/>
         <input type="submit" value="Save event" />
      </div>
   </form>
</div>
</@security.authorize>

</@layout.admin>

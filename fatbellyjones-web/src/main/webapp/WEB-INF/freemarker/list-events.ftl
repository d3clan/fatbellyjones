<#import "layout/adminlayout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js", "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js"] 
    css=["/css/admin.css", "/css/fullcalendar.css"]
    >

<@security.authorize ifAnyGranted="ROLE_ADMIN">
<div id="calendar">
</div>
</@security.authorize>

<script type='text/javascript'>

    $.fn.qtip.styles.tipstyle = {
    width: 100,
    height:100,
    padding: 0,
    background: '#FFFFFF',
    zIndex:3,
    color: 'black',
    textAlign: 'center',
    border: {
        width: 3,
        radius: 6
    },
    tip: 'bottomMiddle',
    name: 'dark'
    }
    

	$(document).ready(function() {
	
			$('#calendar').fullCalendar({
			
				editable: true,
				selectHelper: true,
				selectable : true,
				dayClick: function(date, allDay, jsEvent, view) {
                    $(this).qtip({ content: {
	                        prerender: false, 
	                        text: "<div><a href=\"${rc.contextPath}/admin/protected/events/new.html?day=" + date.getTime() + "\">Add calendar entry</a></div>"
	                    },
                        position: {corner: {tooltip: 'topLeft', target: 'topLeft'}},
                        show: { when: { event: 'click' },
                        ready: true },
                        hide: { fixed: true }
                    });
                },
                
                eventClick: function(calEvent, jsEvent, view) {
                    $(this).qtip({ content: {
	                       prerender: false, 
	                       text: "<div><a href=\"${rc.contextPath}/admin/protected/events/edit.html?id=" + calEvent.id + "\">Edit calendar entry</a></div>" 
	                    },
                        position: {corner: {tooltip: 'bottomMiddle', target: 'topMiddle'}},
                        show: { when: { event: 'click' },
                        ready: true },
                        hide: { fixed: true }
                    });
                },
			    renderEvent: function(event, element, view) {
			        element.qtip({ content: "My Event: " + event.title });
			    },
				eventSources: [
					        '${rc.contextPath}/admin/protected/json/events/list.json'],
				
				eventDrop: function(event, delta) {
				    $.ajax({ url: "${rc.contextPath}/admin/protected/json/events/update.json?id=" + event.id + "&delta=" + delta, 
				    context: document.body, success: function(){
                        $(this).addClass("done");
                    }});
				},
				loading: function(bool) {
					if (bool) $('#loading').show();
					else $('#loading').hide();
				},
				year : ${year}, 
				month : ${month}, 
				date : ${day}
			})
		
	});

$(document).ready(function() {
    // check name availability on focus lost
    checkAvailability();
});

function checkAvailability() {
    $.getJSON("${rc.contextPath}/admin/protected/json/events/list.json", { name: $('#title').val() }, function(availability) {
        if (availability.title) {
            fieldValidated("name", { valid : true });
        } else {
            fieldValidated("name", { valid : false,
                message : $('#name').val() + " is not available, try " + availability.suggestions });
        }
    });
}

function fieldValidated(name) {
    return true;
}
</script>
</@layout.admin>

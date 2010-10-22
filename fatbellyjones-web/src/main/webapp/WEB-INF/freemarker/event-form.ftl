<#import "layout/adminlayout.ftl" as layout>
<#import "/spring.ftl" as spring>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js", "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js" "/js/jquery.ui.datepicker.js"] 
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
	
	
	$(document).ready(function() {
        $('input#is-gig').click(function() {
           if ($('#is-gig:checked').val()) {
               $('#gig-fields').css('display', 'none').fadeIn(1000);
               $('#save-event-div').css('visibility', 'normal').fadeOut(1000);
               $('#to-field').css('display', 'block').fadeOut(1000);
               $('#from-date-label').text('Date:');
           } else {
               $('#gig-fields').css('display', 'block').fadeOut(1000);
               $('#save-event-div').css('visibility', 'normal').fadeIn(1000);
               $('#to-field').css('display', 'none').fadeIn(1000);
               $('#from-date-label').text('From:');
           }
        });
    });
    
    $(document).ready(function() {
        if ($('#is-gig:checked').val()) {
           $('#gig-fields').css('display', 'block');
           $('#save-event-div').css('visibility', 'hidden');
           $('#to-field').css('display', 'none');
           $('#from-date-label').text('Date:');
        }
    });
	
</script>

<div id="add-event-wrapper">
   <div class="heading">
    <h1>${title}</h1>
   </div>
   <form id="event-add-form" action="${rc.contextPath}/admin/protected/events/add.html" method="post">
   <input type="hidden" name="id" value="${eventBean.id!}"/><br/>
   <@spring.bind "eventBean" />
      <div id="event-inner">
         <div class="form-div">
             <input type="checkbox" id="is-gig" name="isGig" <#if eventBean.isGig>checked="checked"</#if> />
             <label class="header-font" for="is-gig">Is this a gig?</label>
         </div>
         <fieldset class="generic-fields">
               <fieldset class="generic-fields">
                   <div class="form-div">
                       <@spring.bind "eventBean.title" />
                       <label for="title">Title:</label>
                       <input placeholder="Title of the event" type="text" id="title" size="60" name="title" value="${eventBean.title!}"/><br/>
                       <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
                   </div>
                   <div class="form-div">
                      <@spring.bind "eventBean.start" />
                      <label id="from-date-label" for="from-date">From:</label>
                      <input type="text" id="from-date" size="12" name="formattedStart" value="${eventBean.formattedStart!}" /><br/>
                      <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
                   </div>
                   <div id="to-field" class="form-div">
                       <@spring.bind "eventBean.end" />
                       <label for="to-date">To:</label>
                       <input type="text" id="to-date" size="12" name="formattedEnd" value="${eventBean.formattedEnd!}" /><br/>
                       <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
                   </div>
               </fieldset>
               <div id="save-event-div" class="submit-area">
                    <input id="save-event" name="save-event" class="fatbelly-button" type="submit" value="Save event" />
                 </div>
               <fieldset id="gig-fields">
                  <legend>Gig details</legend>
                   <div class="form-div">
                     <@spring.bind "eventBean.startHour" />
		             <label for="startHour">Start Time (hours:mins):</label>
		             <div class="first-select">
		                 <select name="startHour">
		                 <#list hours as h>
		                    <option <#if h == eventBean.startHour!>selected="selected"</#if> value="${h}">${h}</option>
                         </#list>  
		                 </select>
		                 <select name="startMin">
		                 <#list mins as m>
		                    <option <#if m == eventBean.startMin!>selected="selected"</#if> value="${m}">${m}</option>
                         </#list>  
		                 </select>
		             </div>
		             <br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		         <label for="endHour">End Time (hours:mins):</label>
		         <div class="first-select">
		             <select name="endHour">
		                 <#list hours as h>
		                    <option <#if h == eventBean.endHour!>selected="selected"</#if> value="${h}">${h}</option>
                         </#list>  
		             </select>
		             <select name="endMin">
		                 <#list mins as m>
		                    <option <#if m == eventBean.endMin!>selected="selected"</#if> value="${m}">${m}</option>
                         </#list>  
		             </select>
		             </div>
		         </div>
                 <div class="form-div">
                     <@spring.bind "eventBean.host" />
		             <label for="host">Host:</label>
		             <input type="text" placeholder="Host e.g. The Hampden" id="host" size="60" name="host" value="${eventBean.host!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		             <@spring.bind "eventBean.location" />
		             <label for="location">Location:</label>
		             <input type="text" placeholder="Location" id="location" size="60" name="location" value="${eventBean.location!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		             <@spring.bind "eventBean.city" />
		             <label for="city">Town:</label>
		             <input type="text" placeholder="Town" id="city" size="25" name="city" value="${eventBean.city!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		             <@spring.bind "eventBean.street" />
		             <label for="street">Street:</label>
		             <input type="text" placeholder="Street address" id="street" size="45" name="street" value="${eventBean.street!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		             <@spring.bind "eventBean.phone" />
		             <label for="phone">Phone:</label>
		             <input type="text" placeholder="Phone" id="phone" size="14" name="phone" value="${eventBean.phone!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		             <@spring.bind "eventBean.email" />
		             <label for="email">Email:</label>
		             <input type="text" placeholder="Email" id="email" size="45" name="email" value="${eventBean.email!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div id="gig-description" class="form-div">
		            <@spring.bind "eventBean.description" />
		            <label for="description">Description:</label><br/>
		            <textarea id="description" placeholder="Description" rows="2" cols="39" name="description">${eventBean.description!}</textarea>
		            <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		             <@spring.bind "eventBean.tagline" />
		             <label for="tagline">Tagline:</label>
		             <input type="text" id="tagline" placeholder="Tagline" size="60" name="tagline" value="${eventBean.tagline!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div class="form-div">
		             <@spring.bind "eventBean.tagline" />
		             <label for="tagline">Charge:</label>
		             <input type="text" id="charge" placeholder="Charge for the gig" size="10" name="charge" value="${eventBean.charge!}" /><br/>
		             <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
		         </div>
		         <div id="save-gig-div" class="submit-area">
                    <input id="save-gig" name="save-gig" class="fatbelly-button" type="submit" value="Next Step &gt;&gt;" />
                 </div>
             </fieldset>
         </fieldset>
      </div>
   </form>
</div>
</@security.authorize>

</@layout.admin>

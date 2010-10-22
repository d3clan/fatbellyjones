<#import "layout/adminlayout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>
<@layout.admin 
    js=["/js/jquery/jquery.js", "/js/jquery/jquery-ui-custom.js"  "/js/jquery-ui-1.8.4.custom.min.js"] 
    css=["/css/admin.css" "/css/jquery-ui-1.8.4.custom.css"]
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">

<script type='text/javascript'>
   $(document).ready(function() {
        $('input#override').click(function() {
           if ($('#override:checked').val()) {
               $("input[name*='user-confirm']").attr('checked', true);
               $(".is-confirmed").css("color", "green");
               $(".is-confirmed").text("Confirmed");
           } else {
               $('#confirm-form')[0].reset();
               $("input[name*='user-confirm']").each(function(index, element) {
                  var lomp = $(element);
                  //alert("VAL " + $(this).attr('checked') + " : index " + index + " : this " + $(this).val());
                  //alert($('#is-confirmed-' + index).text());
                  if (!$(this).attr('checked')) {
                     $('#is-confirmed-' + index).css("color", "red");
                     $('#is-confirmed-' + index).text("Not confirmed");
                  }
               });
           }
        });
   });
   
   $(document).ready(function() {
        $("input[name*='user-confirm']").click(function() {
            var theId = $(this).attr('id');
            var arr = theId.split(':'); 
           //alert(arr[1]);
           if ($(this).attr('checked')) {
               $('#is-confirmed-' + arr[1]).css("color", "green");
               $('#is-confirmed-' + arr[1]).text("Confirmed");
           } else {
               $('#is-confirmed-' + arr[1]).css("color", "red");
               $('#is-confirmed-' + arr[1]).text("Not confirmed");
           }
           
        });
   });
</script>

<div id="add-event-wrapper">
   <div class="heading">
    <h1>${title}</h1>
   </div>
<#if event.confirmed>
<h4>This is a confirmed gig and cannot be edited.</h4>
<#else>
<h4>Please confirm the details below. If anything is incorrect, please <a href="${rc.contextPath}/admin/protected/events/edit.html?id=${event.id}">go to the edit page</a> before proceeding.</h4>
</#if>
<div class="gig-div"><span class="gig-value">${event.title}</span></div>
<#if !event.confirmed>
    <div class="gig-tip">These details will be used to post on Facebook, the poster, Twitter and on the website</div>
    <div class="gig-tip">If what you see below makes sense and the details are correct, click on the next button at the bottom of the page.</div>
<#else>
 <div class="gig-tip"><a href="${event.facebookEvent!}">Facebook event</a></div>
 <div class="gig-tip"><a href="${event.poster!}">Poster</a></div>
 <div class="gig-tip"><a href="http://twitter.com/fatbellies/status/${event.tweet!}">Twitter tweet</a></div>
</#if>
<div class="gig-div">
<p><span class="gig-value">${event.tagline!}</span></p>
<p><span class="gig-value">${event.description!}</span></p>
<p><span class="gig-value">Starting ${event.start!}</span> and ending <span class="gig-value"> ${event.endDate!}</span></p>
<p>Hosted by<span class="gig-value"> ${event.host!}</span> at the <span class="gig-value"> ${event.location!}, </span> <span class="gig-value"> ${event.street!}, ${event.city!}.</span></p>
<p>
The host can be contact via email at <span class="gig-value"><a href="mailto:${event.email!}">${event.email!}</a></span> or on the phone at <span class="gig-value">${event.phone!}.</span>
</p>
</div>
<#if event.confirmed>
<div class="gig-div">
   <p><a href="${rc.contextPath}/admin/protected/playlists/${event.id}.html">Playlist </a>.</p>
</div>
</#if>
<div class="gig-div">
   <p>The charge for the gig is <span class="gig-value">£${event.charge!}</span>. This equates to <span class="gig-value">£${perPerson!}</span> per person.</p>
</div>
<div class="gig-div">
<h4>Band member status</h4>
<#if !event.confirmed>
<p><em>Please note: If the gig is confirmed and all members have agreed, you can &lsquo;Override all&rsquo;. A &lsquo;confirmation request&rsquo; email will not be sent. However, an email will be sent to alert members that a new gig has been created.</em></p>
</#if>
<form id="confirm-form" method="post" action="${rc.contextPath}/admin/protected/gig/finish.html">

<table class="gig-para">
<#if !event.confirmed>
<tr><td style="text-align:right"><label for="override">Override all</label></td><td><input id="override" name="override" type="checkbox"/></td><td>Check this to confirm all members.</td></tr>
</#if>
<tr><td colspan="3"><hr />

<input type="hidden" name="id" value="${event.id}"/>
</td></tr>
<#list allUsers as user>
    <#assign isC = functions.isConfirmed(user, confirmedUsers)>
        <tr>
            <td style="text-align:right"><label for="user-confirm:${user_index}">${user.firstName} ${user.surname}</label></td>
            <td><span class="gig-input"><input id="user-confirm:${user_index}" <#if event.confirmed>disabled="disabled"</#if> name="user-confirm:${user.id}" type="checkbox" <#if isC>checked="checked"</#if>/></span></td>
            <td id="is-confirmed-${user_index}" class="is-confirmed" style="<#if isC>color:green;<#else>color:red</#if>"><#if isC>Confirmed<#else>Not confirmed</#if></td>
        </tr>
</#list>
<tr><td colspan="3"><hr /></td></tr>
</table>
<#if !event.confirmed>
<div><input id="save-gig" name="save-gig" class="fatbelly-button" type="submit" value="Next Step &gt;&gt;" /></div>
</#if>
</form>
</div>
<div class="gig-div"><p>Added by <span class="gig-value"><#if event.user.id == currentUser.id>you<#else>${event.user.firstName!}</#if></span> on <span class="gig-value">${event.dateCreated}</span></p></div>
</div>
</@security.authorize>
</@layout.admin>
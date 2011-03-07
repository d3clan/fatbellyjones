<#import "layout/adminlayout.ftl" as layout>
<#import "/spring.ftl" as spring>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js" "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js" "/js/jquery.ui.datepicker.js" "/js/jquery.form.js"] 
    css=["/css/admin.css" "/css/fullcalendar.css" "/css/jquery-ui-1.8.4.custom.css"]          
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">

<div id="content">
<div id="add-event-wrapper">
<div class="heading">
    <h1>${title}</h1>
</div>
<form id="addtuneform" action="${rc.contextPath}/admin/protected/tune/edit.html" method="post">
<fieldset class="generic-fields">
 <div class="form-div">
     <input id="title-id" type="hidden" name="id" value="${tune.id!}" />
    <@spring.bind "tune.title" />
    <label for="title">Tune name</label> 
    <input maxlength="60" size="60" id="title" type="text" name="title" value="${tune.title!}" /><br/>
    <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
</div>
 <div class="form-div">
    <@spring.bind "tune.artist" />
    <label for="artist">Artist</label>
    <input maxlength="60" size="60" id="artist" type="text" name="artist" value="${tune.artist!}" /><br/>
    <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
</div>
<div class="form-div">
    <@spring.bind "tune.countIn" />
    <label for="count-in">Count in</label>
    <input maxlength="10" size="10" id="count-in" type="text" name="countIn" value="${tune.countIn!}" /><br/>
    <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
</div>
<div class="form-div">
    <@spring.bind "tune.status" />
    <label for="tune-status">Status</label>
    <select id="tune-status" name="status">
       <#list status as st>
           <option <#if tune.status == st>selected="selected"</#if> value="${st}">${st}</option>
       </#list>
    </select> 
    <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
</div>
<div class="form-div">
    <@spring.bind "tune.key" />
    <label for="key">Key</label>
    <input id="key" size="5" type="text" name="key" value="${tune.key!}" />
    <#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list>
</div>
<div class="form-div">
    <input class="fatbelly-button" type="submit" value="Save Tune" />
</div>
</tbody>
</table>
</fieldset>
</form>
</div>
</div>
<div id="dialog"></div>
</@security.authorize>
</@layout.admin>
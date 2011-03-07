<#import "layout/adminlayout.ftl" as layout>
<#import "/spring.ftl" as spring>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js" "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js" "/js/jquery.ui.datepicker.js" "/js/jquery.form.js"] 
    css=["/css/admin.css" "/css/fullcalendar.css" "/css/jquery-ui-1.8.4.custom.css"]          
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">

<#list newsItems as news>
    <p>Hello</p>
</#list>

</@security.authorize>

</@layout.admin>
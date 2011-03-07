<#import "layout/adminlayout.ftl" as layout>
<@layout.admin>

<ol>
<#list gigs as event>
   <li><a href="${rc.contextPath}/admin/protected/playlist/${event.id}.html">${event.title}</a></li>
</#list>
</ol>
</@layout.admin>
<#import "/spring.ftl" as spring>
<#assign xhtmlCompliant = true in spring>

<#macro admin css=["/css/admin.css"] js=[] other=[]>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <title>${title!}</title>
   
    <#list js as file>	
	    <script type="text/javascript" src="${rc.contextPath}${file}"></script>
    </#list>

	<#list other as passthrough>   		
        ${passthrough}
    </#list>	          
			
	<#list css as file>
	    <link rel="stylesheet" type="text/css" href="${rc.contextPath}${file}?v=1" media="screen, print" />
    </#list>
    
  </head>
  <body>
    <div id="main-header">
      <#include "/common/admin-header.ftl">
    </div>
    <hr/>
    <div id="content">
      <#nested>
    </div>
    <div id="footer">
        <#include "/common/admin-footer.ftl">
    </div>
  </body>
</html>
</#macro>
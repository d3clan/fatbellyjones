<#import "layout/public-layout.ftl" as layout>

<@layout.public 
     js=["/js/jquery/jquery.js" "/js/jquery/jquery.twitter.js" "/js/jquery/jquery.nivo.slider.js"] 
     css=["/css/main.css" "/css/jquery.twitter.css" "/css/nivo-slider.css"]
>

<div id="body-text">
   <div class="padded-div" id="bottom-text">
      <div class="padded-div-left">
         <h1>Latest news</h1>
         <#include "/common/add-this.ftl">
         <ul>
         <#list news as n>
		   <li>${n.heading}</li>
         </#list>
         </ul>  
      </div>
      <div class="padded-div-right">
         <img src="${rc.contextPath}/images/about-us-right.jpg" alt="Spence (Bass), Nick (Vocals), Kevin (Keyboard) and Declan (Alto Saxophone)" />
      </div>
   </div>
</div>

</@layout.public>
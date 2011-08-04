<#import "layout/public-layout.ftl" as layout>

<@layout.public 
     js=["/js/jquery/jquery.js" "/js/jquery/jquery.nivo.slider.js" "/js/jquery/jquery.twitter.js"] 
     css=["/css/main.css"]
>
<div id="body-text">
   <div class="padded-div">
      <div class="padded-div-left">
         <h1>Gigs</h1>
         <#include "/common/add-this.ftl">
      </div>
   </div>
   <div class="padded-div" id="bottom-text" style="margin-top:0px;">
      <div class="padded-div-left">
         <#list gigs?keys as key>
         <div>
         <h2>${key}</h2>
         <#assign gigList = gigs[key]>
            <#list gigList as gig>
               <a href="public-gig/${gig.id}.html">${gig.start?string.short} &ndash; ${gig.title} ${gig.host} ${gig.location} ${gig.endDate?time}</a>
            </#list>
         </div>
         </#list>
      </div>
      <div class="padded-div-right">
         <div id="twitter">
         </div>
      </div>
   </div>
</div>

</@layout.public>
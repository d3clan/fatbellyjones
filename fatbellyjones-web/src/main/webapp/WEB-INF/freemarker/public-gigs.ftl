<#import "layout/public-layout.ftl" as layout>

<@layout.public 
     js=["/js/jquery/jquery.js" "/js/jquery/jquery.nivo.slider.js" "/js/jquery/jquery.twitter.js"] 
     css=["/css/main.css"]
>
<div id="body-text">
   <div class="padded-div">
      <div class="padded-div-left">
         
      </div>
      <div class="padded-div-right">
         
         <#include "/common/add-this.ftl">
      </div>
   </div>
   <div class="padded-div" id="bottom-text">
      <div class="padded-div-left">
         <#list gigs?keys as key>
         <div>
         ${key}
         <#assign gigList = gigs[key]>
            <#list gigList as gig>
               ${gig.location}
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
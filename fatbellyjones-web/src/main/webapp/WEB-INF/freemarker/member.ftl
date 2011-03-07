<#import "layout/public-layout.ftl" as layout>

<@layout.public 
     js=["/js/jquery/jquery.js" "/js/jquery/jquery.twitter.js" "/js/jquery/jquery.nivo.slider.js"] 
     css=["/css/main.css" "/css/jquery.twitter.css" "/css/nivo-slider.css"]
>

<div id="body-text">
   <div class="padded-div" id="bottom-text">
      <div class="padded-div-left">
         <h1>${user.firstName} &ndash; ${user.instrument}</h1>
         <#include "/common/add-this.ftl">
         <div id="statement">${user.statement!}</div>
      </div>
      <div class="padded-div-right">
         <img src="${rc.contextPath}/images/${user.photo!}" alt="${user.firstName} &ndash; ${user.instrument}" />
      </div>
   </div>
</div>

</@layout.public>
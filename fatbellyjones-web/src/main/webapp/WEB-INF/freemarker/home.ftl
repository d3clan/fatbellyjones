<#import "layout/public-layout.ftl" as layout>

<@layout.public 
     js=["/js/jquery/jquery.js" "/js/jquery/jquery.nivo.slider.js" "/js/jquery/jquery.twitter.js"] 
     css=["/css/main.css"]
>

<div id="top-title" title="We play the toons that you just can't help jumpin' to.">
   <span class="offset-text">We play the toons that you just can't help jumpin' to.</span>
</div>
<div id="body-text">
   <div class="padded-div">
      <div class="padded-div-left">
         <div id="slider">
              <img src="${rc.contextPath}/images/front-slide-1.png" alt="" title="There's never a dull moment with Fat Belly Jones" />
              <img src="${rc.contextPath}/images/front-slide-2.png" alt="" title="You can't beat live music with real instruments." />
              <img src="${rc.contextPath}/images/front-slide-3.png" alt="" title="You just can't help dancing." />
          </div>
          <div id="htmlcaption" class="nivo-html-caption">
            <strong>This</strong> is an example of a <em>HTML</em> caption with <a href="#">a link</a>.
         </div>
      </div>
      <div class="padded-div-right">
         <div id="what-can-we-do">
              <ul>
                <li>Parties</li>
                <li>Pubs</li>
                <li>Weddings</li>
                <li>Clubs</li>
                <li>Anniversaries</li>
              </ul>
         </div>
         <#include "/common/add-this.ftl">
      </div>
   </div>
   <div class="padded-div" id="bottom-text">
      <div class="padded-div-left">
         <h1>That's Entertainment!</h1>
         <p>Take seven disparate but hugely experienced hand-picked musicians, corn-feed for two years.. add a sprinkle of Ska, a twist of Soul then marinade in Disco overnight and you end up with the musical extravaganza, that is Fat Belly Jones! </p>
         <h2>Planning An Event?</h2>
         <p>We have a massive collection of songs in our repertoire, so if you're planning a wedding,  party, corporate bash or charity event we will subtly blend our sets to give your guests the best experience ever as they dance the night away..!</p>
         <h2>The Whole Caboodle</h2>
         <p>Whilst passionate playing and carefully crafted sets are our hallmark, we aim to give you more! Top quality sound and light equipment compliment our show.</p>
         <p>We'll leave you breathless...</p>
      </div>
      <div class="padded-div-right">
         <div id="twitter">
         </div>
      </div>
   </div>
</div>

</@layout.public>
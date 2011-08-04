<#import "layout/public-layout.ftl" as layout>

<@layout.public 
     js=["/js/jquery/jquery.js" "/js/jquery/jquery.twitter.js" "/js/jquery/jquery.nivo.slider.js"] 
     css=["/css/main.css" "/css/jquery.twitter.css" "/css/nivo-slider.css"]
>

<div id="body-text">
   <div class="padded-div" id="bottom-text">
      <div class="padded-div-left">
         <h1>About us</h1>
         <#include "/common/add-this.ftl">
         <p>Aenean dui massa, luctus non auctor ac, gravida ut justo. Mauris hendrerit erat vehicula dui tristique lobortis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis congue est mi. Etiam congue mattis erat, eget eleifend tellus aliquam id. Maecenas libero mauris, molestie nec scelerisque eget, lobortis non tortor. Vestibulum ac purus diam.</p>
         <h2>Vestibulum ante ipsum</h2>
         <p>Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque odio nisl, rutrum eget hendrerit eu, mollis vitae lorem. Duis imperdiet velit sit amet risus egestas ornare. Fusce nisi tortor, blandit nec vehicula vitae, consectetur eget nunc. Aenean nibh sem, auctor sed tristique rhoncus, mattis id enim. Suspendisse potenti. Duis vel erat ac dolor volutpat viverra. Maecenas sem risus, scelerisque vel mattis eu, pulvinar ac orci.</p>
         <h2>Maecenas sem risus</h2>
         <p>Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque odio nisl, rutrum eget hendrerit eu, mollis vitae lorem. Duis imperdiet velit sit amet risus egestas ornare. Fusce nisi tortor, blandit nec vehicula vitae, consectetur eget nunc. Aenean nibh sem, auctor sed tristique rhoncus, mattis id enim. Suspendisse potenti. Duis vel erat ac dolor volutpat viverra. Maecenas sem risus, scelerisque vel mattis eu, pulvinar ac orci.</p>
         <div id="meet-the-band">
            <h2>Meet the band</h2>
            <ul>
               <#list users as user>
                 <li><a href="${rc.contextPath}/about/member/${user.username}.html">${user.firstName} ${user.surname} - ${user.instrument}</a></li>
               </#list>
            </ul>
        </div>
        <img src="${rc.contextPath}/images/about-us-bottom.jpg" alt="Keith (Tenor Saxophone), Pete (Guitar) and Tony (Drums)" />
      </div>
      <div class="padded-div-right">
         <img src="${rc.contextPath}/images/about-us-right.jpg" alt="Spence (Bass), Nick (Vocals), Kevin (Keyboard) and Declan (Alto Saxophone)" />
      </div>
   </div>
</div>

</@layout.public>
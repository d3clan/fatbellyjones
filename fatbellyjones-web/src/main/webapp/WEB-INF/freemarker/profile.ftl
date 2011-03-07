<#import "layout/adminlayout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>

<@layout.admin 
    js=["/js/jquery/jquery.js", "/js/jquery/jquery-ui-custom.js" "/js/fullcalendar.min.js" "/js/jquery.qtip-1.0.0-rc3.min.js" "/js/jquery-ui-1.8.4.custom.min.js"] 
    css=["/css/admin.css", "/css/fullcalendar.css"]
    >
<@security.authorize ifAnyGranted="ROLE_ADMIN">
<div id="add-event-wrapper">
	<div id="profile-wrapper">
	<form enctype="multipart/form-data" method="post" action="${rc.contextPath}/admin/protected/profile/save.html">
	<fieldset class="generic-fields">
	       <input id="email" type="hidden" name="id" value="${userBean.id}"/>
	       <div class="form-div"><label for="email">Email</label><input id="email" type="text" name="email" value="${userBean.email!}"/></div>
	       <div class="form-div"><label for="instrument">Instrument</label><input id="instrument" type="text" name="instrument" value="${userBean.instrument!}"/></div>
	       <div class="form-div"><label for="firstName">First Name</label><input id="firstName" type="text" name="firstName" value="${userBean.firstName!}"/></div>
	       <div class="form-div"><label for="surname">Second Name</label><input id="surname" type="text" name="surname" value="${userBean.surname!}"/></div>
	       <div class="form-div"><label for="mobile">Mobile</label><input id="mobile" type="text" name="mobile" value="${userBean.mobile!}"/></div>
	       <div class="form-div"><label for="town">Town</label><input id="town" type="text" name="town" value="${userBean.town!}"/></div>
	       <div class="form-div"><label for="image">Image</label><input id="image" type="file" name="image"/></div>
	       <div class="form-div"><input class="fatbelly-button" type="submit" value="Save"/></div>
	   </tbody>
	</table>
	</fieldset>
	</form>
	</div>
	<div id="photo">
		<#if userBean.photo??>
		   <img src="${userBean.photo}" alt="${userBean.firstName}" title="Yup, that is you!"/>
		<#else>
		   <img src="${rc.contextPath}/images/no-profile-image.png" alt="No image available"/>
		</#if>
	</div>
</div>
</@security.authorize>
</@layout.admin>
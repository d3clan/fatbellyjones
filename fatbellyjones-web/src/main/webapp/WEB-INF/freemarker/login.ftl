<#import "layout/admin-logged-out-layout.ftl" as layout>
<@layout.adminloggedout>
<div id="admin-outer">
   <div id="inner">
		<#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
		    <div><span class="error">Wrong username or password. Please try again.</span></div>
		</#if>
		<form action="${rc.contextPath}/j_spring_security_check" method="post">
		    <fieldset>
		        <label for="username">Username</label><input type="text" id="username" name="j_username"/><br/>
		        <label for="password">Password</label><input type="password" id="password" name="j_password"/><br/>
		        <label for="remember-me">Rember Me</label><input id="remember-me" type="checkbox" name="fatbellyjones"/>
		        <input type="submit" value="Login!"/>
		    </fieldset>
		</form>
	</div>
</div>
</@layout.adminloggedout>
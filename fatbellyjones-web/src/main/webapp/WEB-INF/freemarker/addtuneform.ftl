<#import "/spring.ftl" as spring>
<div class="dialog-div">
<form id="addtuneform" action="${rc.contextPath}/admin/protected/tune/add.html" method="post">
<table>
<tbody>
<tr>
    <@spring.bind "tuneBean.title" />
    <td><label for="title">Tune name</label></td> 
    <td><input maxlength="60" size="60" id="title" type="text" name="title" /></td>
    <td colspan="2"><#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list></td>
</tr>
<tr>
    <td colspan="2"><span id="error-title" class="error"></span></td>
</tr>
<tr>
    <@spring.bind "tuneBean.artist" />
    <td><label for="artist">Artist</label></td>
    <td><input maxlength="60" size="60" id="artist" size="20" type="text" name="artist" /></td>
    <td colspan="2"><#list spring.status.errorMessages as error> <strong class="error">${error}</strong></#list></td>
</tr>
<tr>
    <td colspan="2"><span id="error-artist" class="error"><span></td>
</tr>
<tr>
    <td colspan="2"><input type="submit" value="Add Tune" /></td>
</tr>
</tbody> 
</form>
<script type='text/javascript'>
    $('#addtuneform').ajaxForm(function() {
        var emptyTextBoxes = $('input:text').filter(function() { return this.value == ""; });

        emptyTextBoxes.each(function() {
            $('#error-' + this.id).html('Please enter a value for ' + this.id);
        });
        if (emptyTextBoxes.length == 0) {
           $("#dialog").dialog("close");
           updateTunes();
            return false;
        }
    });
</script>
</div>
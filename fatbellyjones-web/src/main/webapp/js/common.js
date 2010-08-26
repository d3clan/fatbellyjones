// -----------------------------------------------------------------------------
// Common helper functions -- to facilitate our working with Javascript only!
// -----------------------------------------------------------------------------
var cham_cham_loadedJavaScripts = {};
function cham_loadJavaScript(url) {
    if(cham_cham_loadedJavaScripts[url] != 'loaded') {
        cham_cham_loadedJavaScripts[url] = 'loaded';
        var e = document.createElement("script");
        e.src = url;
        e.type="text/javascript";   
        document.getElementsByTagName("head")[0].appendChild(e);        
    }    
}
// -----------------------------------------------------------------------------
function byId(id) { return document.getElementById(id); }
// -----------------------------------------------------------------------------
function cham_showEmptyFields(list) {
    for(var i=0; i<list.length; i++) {
        cham_showEmptyField(list[i]);
    }
}
function cham_showEmptyField(field) {
    byId(field).style.display='block';
}
function cham_hideFieldsIfEmpty(list) {
    for(var i=0; i<list.length; i++) {
        cham_hideFieldIfEmpty(list[i]);
    }
}
function cham_hideFieldIfEmpty(field) {
    var a = byId(field);
    if(a.innerHTML=='' || a.innerHTML=='<P></P>') {
        a.style.display='none';
    } else {
        a.style.display='block';
    }
    
}
function cham_hideById(field) { byId(field).style.display='none'; }
// -----------------------------------------------------------------------------
function cham_openFakeModalWindow(target,name,height,width,position){

    var isIE = false;
    // IE 5.5+ check 
    if(navigator.appVersion.indexOf("MSIE")!=-1) { var isIE = true; }

    // Netscape 5+ (i.e. Mozilla/Firefox)
    var isMoz = false;    
    if(navigator.appName=="Netscape"&&parseFloat(navigator.appVersion)>=5){
	    isMoz = true;
    }
    
    var newTop, newLeft, selfTop, selfLeft;
    
	if (position == "center"){

		newTop = (screen.height - height)/2;
		newLeft = (screen.width - width)/2;

	} else {
		
		if(isIE){ // for IE 5+

			selfTop = window.screenTop ;
			selfLeft = window.screenLeft;
			newTop = selfTop + 5;
			newLeft = selfLeft + 15;	

		} else if(isMoz){ // For Mozilla, Firefox & Netscape 5+

			selfTop = this.window.screenY;
			selfLeft = this.window.screenX;
			newTop = selfTop + 33;
			newLeft = selfLeft + 20;	

		} 

	}

	if(isMoz || isIE){ // For Mozilla, Firefox & Netscape 5+
	
		var windowReference = window.open(target, name,"width=" + width + ",height=" + height + ",top=" + newTop + ", left=" + newLeft + ", scrollbars=yes,statusbar=no,modal=yes,location=no,menubar=no,resizable=yes,toolbar=no,dependent=yes,dialog=yes,minimizable=no,modal=yes,alwaysRaised=yes");
	
	} else { // other browsers	
	
		alert('Oh dear! Your browser seems to be incompatible with this action.')
	
	}
}
// -------------------------------------------------------------------------------

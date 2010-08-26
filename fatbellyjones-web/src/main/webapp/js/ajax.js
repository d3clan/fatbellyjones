var isbn = null;

function checkAvailability(param) {
	setLoadingImage(true);
	isbn = param;
	widgetEnabled.isWidgetEnabled(isbn, callback);
}

function callback(data) {
    var rbooksLink = document.getElementById("rbooksLink");
    var widgetLink = document.getElementById("widgetLink");
    if (data) {
    	rbooksLink.style.display = "none";
    	widgetLink.style.display = "block";
    } else {
    	rbooksLink.style.display = "block";
		widgetLink.style.display = "none";
    }
    setLoadingImage(false);
}

function setLoadingImage(isLoading) {
	var loadingDiv = document.getElementById("loadingImage");
	if (isLoading) {
		loadingDiv.style.display = "block";
	} else {
		loadingDiv.style.display = "none";
	}
}
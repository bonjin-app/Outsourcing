/**
 * Go Detail Page
 * @param element
 */
function goDetailPage(element) {
    location.href = element.getAttribute("data-href")
}

/**
 * Image Preview
 * @param input
 */
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            var imagePreview = document.getElementById("imagePreview");

            // $(imagePreview).css('background-image', 'url('+e.target.result +')');

            $(imagePreview).attr('src', e.target.result);
            $(imagePreview).hide();
            $(imagePreview).fadeIn(650);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

/**
 * Default IMG Tag Image
 * @param element
 */
function defaultImage(element) {
    element.src = "/images/default_image.png";
}



/**************
 * START_COOKIE
 **************/
/**
 * Set Cookie
 * @param key
 * @param value
 * @param expires
 */
function setCookie(key, value, expires) {
    var date = new Date();
    date.setDate(date.getDate() + expires);

    var cookie = key + "=" + escape(value) + "; path=/; expires=" + date.toGMTString() + ";";
    document.cookie = cookie;
}


/**
 * Get Cookie
 * @param key
 */
function getCookie(key) {
    var search = name + "=";

    if (document.cookie.length > 0) {
        var offset = document.cookie.indexOf(search);

        if (offset != -1) {
            offset += search.length;
            var end = document.cookie.indexOf(";", offset);

            if (end == -1) {
                end = document.cookie.length;
            }
            return unescape(document.cookie.substring(offset, end));
        }
    }
}

/**
 * Delete Cookie
 * @param key
 */
function deleteCookie(key) {
    var date = new Date();
    date.setDate(date.getDate()-1);
    setCookie(key, "", date.toGMTString());
}
/**************
 * END_COOKIE
 **************/
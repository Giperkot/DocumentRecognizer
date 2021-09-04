(function () {
    let helper = {};
    let userHelper = {
        userInfo: {},
        userAbilities: {}
    };

    helper.getHttpPromise = function (config) {
        return new Promise(function(resolve, reject) {

            let xhr = new XMLHttpRequest();
            xhr.open(config.method, config.url, true);
            xhr.setRequestHeader("Content-type", config.contentType);

            if (config.accept) {
                xhr.setRequestHeader("Accept", config.accept);
            }

            xhr.onload = function() {
                if (this.status == 200) {
                    resolve({
                        response: this.response,
                        xhr: this
                    });
                } else {
                    let error = new Error(this.statusText);
                    error.code = this.status;
                    reject(this.response, error);
                }
            };

            xhr.onerror = function() {
                reject(new Error("Network Error"));
            };

            xhr.send(JSON.stringify(config.jsonData));
        });
    };

    helper.getParameterByName = function (name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    };


    userHelper.getUserInfo = function () {

    };

    userHelper.getUserAbilities = function () {

    };

    window.helper = helper;
})();

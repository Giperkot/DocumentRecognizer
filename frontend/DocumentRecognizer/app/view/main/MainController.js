/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 */
Ext.define('DocumentRecognizer.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

    onItemSelected: function (sender, record) {
        Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
    },

    onConfirm: function (choice) {
        if (choice === 'yes') {
            //
        }
    },
    onExitUser: function () {
        Ext.Ajax.request({
            method: 'POST',
            url: '/api/auth/logout',
            jsonData: JSON.stringify({
            }),
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            },
            success: function (response) {
                var resp = [];
                try {
                    resp = JSON.parse(response.responseText);
                } catch (ex) {
                    console.log("Ошибка при загрузке специалистов");
                }

                window.location.href = "/login";
            },
            failure: function () {
                window.location.href = "/login";
            }
        });
    }
});

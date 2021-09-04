/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('DocumentRecognizer.view.main.Main', {
    extend: 'Ext.panel.Panel',
    xtype: 'app-main',

    requires: [
        'Ext.plugin.Viewport',
        'Ext.window.MessageBox',

        'DocumentRecognizer.view.main.MainController',
        'DocumentRecognizer.view.main.MainModel',
        'DocumentRecognizer.view.main.List'
    ],

    controller: 'main',
    viewModel: 'main',

    defaults: {
        tab: {
            iconAlign: 'left'
        },
        styleHtmlContent: true
    },

    tabBarPosition: 'left',

    layout: 'vbox',

    items: [
        {
            xtype: "container",
            height: 40,
            width: '100%',
            layout: {
                type: "hbox",
                pack: "center",
                align: "middle"
            },
            items: [
                {
                    xtype: "container",
                    padding: "7 10",
                    height: 30,
                    minWidth: 150,
                    html: "admin@mail.ru"
                }, {
                    xtype: "container",
                    padding: "7 10",
                    height: 30,
                    minWidth: 300,
                    html: "Добро пожаловать, admin"
                    /*bind: {
                        html: "{userInfo}"
                    }*/
                }, {
                    xtype: "button",
                    height: 30,
                    width: 100,
                    text: "Выход",
                    handler: 'onExitUser'
                }
            ]
        }, {
            xtype: 'menu-main',
            reference: 'mainTabPanel',
            flex: 1
        }
    ]
});

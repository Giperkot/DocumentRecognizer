Ext.define('DocumentRecognizer.view.parser.DirectoryForm', {
    extend: 'Ext.form.Panel',
    xtype: 'directory-form',

    requires: [
        'Ext.plugin.Viewport',
        'Ext.window.MessageBox',

        'DocumentRecognizer.view.main.MainController',
        'DocumentRecognizer.view.main.MainModel',
        'DocumentRecognizer.view.main.List'
    ],

    items: [
        {
            xtype: 'filefield',
            name: 'photo',
            fieldLabel: 'Каталог',
            labelWidth: 50,
            msgTarget: 'side',
            allowBlank: false,
            anchor: '100%',
            buttonText: 'Выбрать каталог'
        }, {
            xtype: "button",
            text: 'Распознать файлы',
            margin: '0 10 0 10',
            handler: function () {
                let self = this;

                Ext.Ajax.request({
                    method: 'GET',
                    url: '/api/recognition/scan',
                    headers: {
                        'Content-Type': 'application/json',
                        Accept: 'application/json'
                    },
                    success: function (response) {

                        var data = JSON.parse(response.responseText);
                        self.lookupViewModel().set("uploadResultData", data);
                        self.up("directory-form").down("upload-grid").up().setHidden(false);
                    }
                });
            }
        }, {
            xtype: "panel",
            margin: '20 10',
            reference: "resultPanel",
            hidden: true,
            items: [
                {
                    xtype: "upload-grid",
                    bind: {
                        store: "{uploadResultData}"
                    }
                }
            ]

        }
    ]


});



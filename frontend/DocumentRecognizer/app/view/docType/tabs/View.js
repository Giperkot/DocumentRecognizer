

Ext.define('DocumentRecognizer.view.docType.tabs.View', {
    extend: 'Ext.form.Panel',
    requires: [

    ],

    alias: 'widget.doctype-card-view',
    controller: 'doctype-card-controller',
    viewModel: 'doctype-card-view-model',
    autoScroll: true,

    maxHeight: window.innerHeight - 80,

    items: [
        {
            xtype: "container",
            padding: '20 0 0 20',
            scrollable: true,
            autoScroll: true,

            items: [
                {
                    xtype: "container",
                    bind: {
                        html: "<h2>{cardTitle}</h2>"
                    }
                },
                {
                    xtype: "textfield",
                    width: 550,
                    fieldLabel: "Имя",
                    bind: {
                        value: "{item.name}"
                    }
                }, {
                    xtype: "textfield",
                    width: 550,
                    fieldLabel: "Идентификатор",
                    bind: {
                        value: "{item.uuid}"
                    }
                }, {
                    xtype: "textfield",
                    width: 550,
                    fieldLabel: "Название",
                    bind: {
                        value: "{item.title}"
                    }
                }, {
                    xtype: "panel",
                    layout: "hbox",
                    items: [
                        {
                            xtype: "button",
                            text: 'Сохранить',
                            handler: 'saveCard'
                        },
                        {
                            xtype: "button",
                            text: 'Отмена',
                            hidden: true,
                            listeners: {
                                click: function(el) {

                                }
                            }
                        }
                    ]
                }
            ]
        }
    ],
    listeners: {
        // activate: 'onActivate'
    }


});

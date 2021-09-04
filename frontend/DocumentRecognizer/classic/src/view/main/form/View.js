

Ext.define('DocumentRecognizer.view.main.form.View', {
    extend: 'Ext.form.Panel',
    requires: [

    ],

    alias: 'widget.client-card-view',
    controller: 'client-card-controller',
    viewModel: 'client-card-view-model',
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
                    fieldLabel: "Название организации",
                    bind: {
                        value: "{item.name}"
                    }
                }, {
                    xtype: "textfield",
                    width: 550,
                    fieldLabel: "ИНН",
                    bind: {
                        value: "{item.inn}"
                    }
                }, {
                    xtype: "textfield",
                    width: 550,
                    fieldLabel: "КПП",
                    bind: {
                        value: "{item.kpp}"
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

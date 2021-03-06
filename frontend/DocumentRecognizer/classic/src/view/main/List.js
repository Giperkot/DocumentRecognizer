/**
 * This view is an example list of people.
 */
Ext.define('DocumentRecognizer.view.main.List', {
    extend: 'Ext.grid.Panel',
    xtype: 'mainlist',

    requires: [
        'DocumentRecognizer.store.Personnel'
    ],

    controller: 'user-controller',

    title: 'Клиенты',

    tbar: {
        items: [
            {
                xtype: 'container',
                items: [
                    {
                        xtype: 'container',
                        layout: 'hbox',
                        margin: '2 0',
                        items: [
                            {
                                xtype: "button",
                                text: "Создать",
                                handler: "onCreate",
                                bind: {
                                    hidden: '{!isAdmin}'
                                }
                            }, {
                                xtype: "button",
                                margin: '0 10 0 10',
                                text: "Обновить",
                                handler: "updateGrid"
                            }
                        ]
                    }
                ]
            }
        ]

    },

    columns: [
        {
            text: '№',
            renderer: function (one, two, three, idx) {
                return idx + 1;
            }
        },
        { text: 'Название организации',  dataIndex: 'name', flex: 1 },
        { text: 'ИНН', dataIndex: 'inn', flex: 1 },
        { text: 'КПП', dataIndex: 'kpp'}
    ],

    listeners: {
        rowdblclick: 'gridRowDoubleClick'
    }
});

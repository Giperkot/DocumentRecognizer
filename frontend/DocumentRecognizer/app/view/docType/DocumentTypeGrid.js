/**
 *
 */
Ext.define('DocumentRecognizer.view.docType.DocumentTypeGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'doc-type-grid',

    requires: [
        'DocumentRecognizer.store.Personnel'
    ],

    title: 'Результат',

    controller: "doctype-controller",

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
        { text: 'Название', dataIndex: 'title', flex: 1},
        { text: 'Имя',  dataIndex: 'name', flex: 1 },
        { text: 'uuid', dataIndex: 'uuid', flex: 1 }
    ],

    listeners: {
        rowdblclick: 'gridRowDoubleClick'
    }
});
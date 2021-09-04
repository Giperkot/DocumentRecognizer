/**
 * This view is an example list of people.
 */
Ext.define('DocumentRecognizer.view.main.List', {
    extend: 'Ext.grid.Panel',
    xtype: 'mainlist',

    requires: [
        'DocumentRecognizer.store.Personnel'
    ],

    title: 'Personnel',

    store: {
        type: 'personnel'
    },

    columns: [
        {
            text: '№',
            renderer: function (arguments) {
                console.log(arguments);
                return arguments[1];
            }
        },
        { text: 'Название организации',  dataIndex: 'name' },
        { text: 'ИНН', dataIndex: 'inn', flex: 1 },
        { text: 'КПП', dataIndex: 'kpp', flex: 1 }
    ],

    listeners: {
        select: 'onItemSelected'
    }
});

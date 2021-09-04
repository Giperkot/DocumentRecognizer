/**
 * Результаты обработки
 */
Ext.define('DocumentRecognizer.view.main.UploadGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'upload-grid',

    requires: [
        'DocumentRecognizer.store.Personnel'
    ],

    title: 'Результат',

    columns: [
        {
            text: '№',
            renderer: function (one, two, three, idx) {
                return idx + 1;
            }
        },
        { text: 'Путь',  dataIndex: 'path', flex: 1 },
        { text: 'uuid', dataIndex: 'uuid', flex: 1 },
        { text: 'Тип файла', dataIndex: 'categoryName'}
    ],

    listeners: {
        select: 'onItemSelected'
    }
});

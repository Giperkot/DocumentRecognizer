Ext.define('DocumentRecognizer.store.ClientStore', {
    extend: 'Ext.data.Store',

    alias: 'store.studentGroupListStore',
    storeId: 'studentGroupListStore',
    model: 'DocumentRecognizer.model.ClientModel',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        async: true,
        actionMethods: {
            read: 'POST'
        },
        url: "/api/clients/clientList",
        reader: {
            type: 'json',
            rootProperty: 'result',
            totalProperty: 'total'
        },
        headers: {
            Accept: "application/json"
        },
        paramsAsJson: true
    }

});
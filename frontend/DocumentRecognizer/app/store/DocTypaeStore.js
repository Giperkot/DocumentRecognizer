Ext.define('DocumentRecognizer.store.DocTypeStore', {
    extend: 'Ext.data.Store',

    alias: 'store.docTypeStore',
    storeId: 'docTypeStore',
    model: 'DocumentRecognizer.model.ClientModel',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        async: true,
        actionMethods: {
            read: 'POST'
        },
        url: "/api/clients/documentTypeList",
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
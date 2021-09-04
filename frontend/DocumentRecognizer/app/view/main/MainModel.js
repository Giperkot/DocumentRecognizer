/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('DocumentRecognizer.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.main',

    data: {
        name: 'DocumentRecognizer',
        uploadResultDataObj: [],
        loremIpsum: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
    },

    stores: {
        clientStore: {
            type: 'clientStore',
            autoLoad: true
        },
        docTypeStore: {
            type: 'docTypeStore',
            autoLoad: true
        }
    },
    formulas: {
        uploadResultData: {
            bind: {
                uploadResultDataObj: '{uploadResultDataObj}'
            },
            get: function (data) {

                let store = Ext.create("Ext.data.Store");

                if (data.uploadResultDataObj) {
                    for (let i = 0; i < data.uploadResultDataObj.length; i++) {
                        store.insert(Ext.create("Ext.data.Model", data.uploadResultDataObj[i]));
                    }
                }

                return store;
            }
        },
        /*uploadResultDataEmpty: {
            bind: {
                uploadResultDataObj: '{uploadResultDataObj}'
            },
            get: function (data) {

                return data.uploadResultDataObj.length < 1;
            }
        }*/
    }


    //TODO - add data, formulas and/or methods to support your view
});

Ext.define('DocumentRecognizer.view.main.Controller', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.user-controller',

    requires: [

    ],

    onViewReady: function () {

    },
    gridRowDoubleClick: function  (_this, record, target, index, e, eOpts ) {
        var mainTabPanel = this.getView().up("app-main").lookupReference("mainTabPanel");

        var addIndex = mainTabPanel.items.length;
        mainTabPanel.insert(addIndex,{
            title: "Клиент: " + record.get("id"),
            closable: true,
            items: [
                {
                    xtype: 'client-card-view',
                    gridController: this
                }
            ]
        });

        mainTabPanel.items.items[addIndex].down('client-card-view').getViewModel().set("item", record);

        mainTabPanel.setActiveItem(addIndex);
    },
    onCreate: function () {
        var mainTabPanel = this.getView().up("app-main").lookupReference("mainTabPanel");

        var addIndex = mainTabPanel.items.length;
        mainTabPanel.insert(addIndex,{
            title: "Клиент: Новый",
            closable: true,
            items: [
                {
                    xtype: 'client-card-view',
                    gridController: this
                }
            ]
        });

        var emptyRecord = Ext.create("Ext.data.Model", {
            workDate: new Date(),
            id: -1
        });

        mainTabPanel.items.items[addIndex].down('client-card-view').getViewModel().set("item", emptyRecord);

        mainTabPanel.setActiveItem(addIndex);
    },
    updateGrid: function () {
        let vm = this.getViewModel();
        /*let filterDateFrom = vm.get("filterDateFrom");
        let filterDateTo = vm.get("filterDateTo");*/

        let store = vm.getStore("clientStore");
        /*store.proxy.extraParams = {
            start: filterDateFrom,
            end: filterDateTo
        };*/

        store.reload();
    },
    resetFilter: function () {

    }
});

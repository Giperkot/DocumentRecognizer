Ext.define('DocumentRecognizer.view.docType.tabs.Controller', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.doctype-card-controller',

    onChangeStudentGroup: function (_this, id, value) {
        let vm = this.getViewModel();

        let studentGroupListStore = Ext.getStore("studentGroupListStore");

        if (!studentGroupListStore) {
            return;
        }

        let record = studentGroupListStore.getById(id);

        if (record) {
            vm.set("item.studentYearNumber", record.get("yearNumber"));
            vm.set("item.studentStartYear", record.get("startYear"));
            vm.set("item.specialityName", record.get("specialityName"));
        }
    },
    updateGrid: function () {
        this.getView().initialConfig.gridController.updateGrid();
    },
    saveCard: function () {
        var self = this;
        var vm = this.getViewModel();
        var item = vm.get("item");

        let method;
        let url;
        if (item.get("id") && item.get("id") > 0) {
            method = "PUT";
            url = '/api/dictionary/updateAuthor'
        } else {
            method = "POST";
            url = '/api/dictionary/createAuthor'
        }

        Ext.Ajax.request({
            method: method,
            url: url,
            jsonData: JSON.stringify(item.data),
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            },
            success: function (response) {
                Ext.Msg.alert('Успех', 'Запись успешно сохранена!');
                self.updateGrid();
            }
        });

    },
    onActivate: function () {

    }
});

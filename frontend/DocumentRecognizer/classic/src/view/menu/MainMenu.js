Ext.define('DocumentRecognizer.view.menu.MainMenu', {
    extend: 'Ext.tab.Panel',
    xtype: 'menu-main',

    requires: [
        'Ext.plugin.Viewport',
        'Ext.window.MessageBox',

        'DocumentRecognizer.view.main.MainController',
        'DocumentRecognizer.view.main.MainModel',
        'DocumentRecognizer.view.main.List'
    ],

    ui: 'navigation',

    tabBarHeaderPosition: 1,
    titleRotation: 0,
    tabRotation: 0,

    width: '100%',

    header: {
        layout: {
            align: 'stretchmax'
        },
        title: {
            text: "DocumentRecognizer",
            flex: 0
        },
        iconCls: 'fa fa-th-list'
    },

    tabBar: {
        flex: 1,
        layout: {
            align: 'stretch',
            overflowHandler: 'none'
        }
    },

    responsiveConfig: {
        tall: {
            headerPosition: 'top'
        },
        wide: {
            headerPosition: 'left'
        }
    },

    defaults: {
        bodyPadding: 20,
        tabConfig: {
            responsiveConfig: {
                wide: {
                    iconAlign: 'left',
                    textAlign: 'left'
                },
                tall: {
                    iconAlign: 'top',
                    textAlign: 'center',
                    width: 120
                }
            }
        }
    },

    items: [{
        title: 'Клиенты',
        iconCls: 'fa fa-home',
        layout: 'fit',
        items: [{
            xtype: 'mainlist',
            minHeight: 500,
            width: '100%',
            bind: {
                store: '{clientStore}'
            }
        }]
    }, {
        title: 'Распознавание',
        iconCls: 'fa fa-user',
        items: [
            {
                xtype: "directory-form",
            }
        ]
    }, {
        title: 'Типы документов',
        iconCls: 'fa fa-users',
        items: [
            {
                xtype: "doc-type-grid",
                minHeight: 500,
                width: '100%',
                bind: {
                    store: '{docTypeStore}'
                }
            }
        ]
    }, {
        title: 'Настройки',
        iconCls: 'fa fa-cog',
        bind: {
            html: '{loremIpsum}'
        }
    }]
});

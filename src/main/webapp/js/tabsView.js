var tabsView = {
	initialize : function() {
		$('#tabs').buttonset();
		$('#editTabs').button({
			icons : {
				primary : "ui-icon-pencil",
			},
			width : "25px",
			height : "25px",
			text : false
		}).css('vertical-align', 'super').click(function() {
			tabsView.show();
		});

		$('#editTabsGrid').sortable({
			update : function(event, ui) {
				tabsController.applyOrder();
			}
		});

		$('#editTabsAddTab').button({
			icons : {
				primary : "ui-icon-plus",
				width : "100px"
			}
		}).click(function() {
			var tab = tabsView.addTab(tabsController.createBlankTab());
			tab.setEditMode();

			tabsController.applyOrder();
			return false;
		});
		
		
	},

	addTab : function(model) {
		var newId = '';
		$('#editTabsTabTemplate').clone().css('display', 'block').attr('id',
				newId = 'tab-' + model.id).appendTo('#editTabsGrid');

		var taskElem = getJqTskComp(newId)[0];

		$(taskElem).addClass('editableTab');

		getJqTskComp(newId, 'editTabsTabId');

		getJqTskComp(newId, 'editTabsEdit').button({
			icons : {
				primary : "ui-icon-pencil",
			},

			text : false
		}).addClass('taskButton').click(function() {
			taskElem.editAction();
			return false;
		});

		getJqTskComp(newId, 'editTabsDelete').button({
			icons : {
				primary : "ui-icon-trash",
			},
			text : false
		}).addClass('taskButton').click(function() {
			taskElem.deleteAction();
			return false;
		});

		getJqTskComp(newId, 'editTabsCancel').button({
			icons : {
				primary : "ui-icon-cancel",
			},
			text : false
		}).addClass('taskButton').click(function() {
			taskElem.cancelAction();
			return false;
		});

		getJqTskComp(newId, 'editTabsAccept').button({
			icons : {
				primary : "ui-icon-check",
			},
			text : false
		}).addClass('taskButton').click(function() {
			taskElem.acceptAction();
			return false;
		});

		getJqTskComp(newId, 'editTabsNameEditor').keypress(function(e) {
			if (e.keyCode == $.ui.keyCode.ENTER) {
				taskElem.acceptAction();
			}
		}).css({
			'border' : 'solid 1px',
			'padding' : '0px'
		});

		$.extend(taskElem, {
			tabModel : model,

			initialize : function() {
				this.setName(model.name);
			},

			setName : function(name) {
				getJqTskComp(newId, 'editTabsName').text(name);
				getJqTskComp(newId, 'editTabsNameEditor').val(name);
			},

			setEditMode : function() {
				getJqTskComp(newId, 'editTabsName').hide();
				getJqTskComp(newId, 'normalMode').hide();
				getJqTskComp(newId, 'editTabsNameEditor').show();
				getJqTskComp(newId, 'editMode').show();

				var editor = getJqTskComp(newId, 'editTabsNameEditor')[0];
				editor.setSelectionRange(0, editor.value.length)

				getJqTskComp(newId, 'editTabsNameEditor').focus();
			},

			setNormalMode : function() {
				getJqTskComp(newId, 'editTabsName').show();
				getJqTskComp(newId, 'normalMode').show();
				getJqTskComp(newId, 'editTabsNameEditor').hide();
				getJqTskComp(newId, 'editMode').hide();

				getJqTskComp(newId, 'editTabsNameEditor').focus();
			},

			deleteAction : function() {
				taskElem.remove();
				tabsController.deleteTab(model);
			},

			editAction : function() {
				taskElem.setEditMode();
			},

			acceptAction : function() {
				var val = getJqTskComp(newId, 'editTabsNameEditor').val();

				this.setName(val);
				this.setNormalMode();

				model.name = val;
			},

			cancelAction : function() {
				this.setName(model.name);
				this.setNormalMode();
			},

			endDragFunction : function() {
				tabsController.applyOrder();
			}
		});

		taskElem.initialize();

		return taskElem;
	},

	show : function() {
		$('.editableTab').remove();

		tabsModel = tabsController.loadNewEditor();

		for (var i = 0; i < tabsModel.length; ++i) {
			this.addTab(tabsModel[i]);
		}

		$('#editTabsDialog').css('visibility', 'visible').css('display',
				'block').dialog({
			modal : true,
			width : '450px',
			buttons : [ {
				text : 'save',
				click : function() {
					tabsController.save(function() {
						location.reload();
					});
				}
			}, {
				text : 'cancel',
				click : function() {
					$(this).dialog("close");
				}
			}, ]
		});
	}
};
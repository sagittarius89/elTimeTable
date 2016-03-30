var tabsController = {
	getActiveTabId: function() {
		return $("input[name=selectedTab]:checked").val();
	},

	createBlankTab: function() {
		var tabModel = {
			id: guid(),
			name: 'new tab',
			order: 0,
			isDefault: false
		};
		
		model.tabsEdit.push(tabModel);
		
		return tabModel;
	},
	
	deleteTab: function(tabModel) {
		removeFromTable(model.tabsEdit, tabModel);
	},
	
	applyOrder: function() {
		$('.editableTab').each(function(index) {
			this.tabModel.order = index;
		});
	},
	
	loadNewEditor: function() {
		model.tabsEdit = [];
		
		for(var i=0; i<model.tabs.length; ++i) {
			model.tabsEdit.push({
				id: model.tabs[i].id,
				name: model.tabs[i].name,
				order: model.tabs[i].order,
				isDefault: model.tabs[i].isDefault
			});
		}
		
		return model.tabsEdit;
	},
	
	save: function(callback) {
		model.updateTabs(callback);
	}
};
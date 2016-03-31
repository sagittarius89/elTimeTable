var model = {
	tasks: [],
	tabs: [],
	tabsEdit: [],
	
	synchronize: function(orderBy, direction, tabId, callback) {
		$.ajax({
			type: "POST", 
			cache: false, 
			url: '../tasks/synchronizeTasks.do', 
			async: true,
			data: {'tasks': JSON.stringify(model.tasks), 'tabId': tabId, 'orderBy': orderBy, 'direction': direction}, 
			complete: function(jqXHR, textStatus) {
				model.tasks = $.parseJSON(jqXHR.responseText);
				callback();
			},
			error: function(jqXHR, textStatus) {
				console.log('sync error');
			}
		});
	},
	
	deleteTask: function(id) {
		model.changeStatus(id, 4);
	},
	
	markDone: function(id) {
		model.changeStatus(id, 2);
	},
	
	undoDone: function(id) {
		model.changeStatus(id, 1);
	},
	
	changeStatus: function(id, status) {
		$.ajax({
			type: "POST", 
			cache: false, 
			url: '../tasks/changeStatus.do', 
			async: false,
			data: {'id': JSON.stringify(id), 'status': status},
			error: function(jqXHR, textStatus) {
				console.log('change status error');
			}
		});
	},
	
	changeProgress: function(task, type) {
		$.ajax({
			type: "POST", 
			cache: false, 
			url: '../tasks/changeProgress.do', 
			async: false,
			data: {'id': task.id, 'value': task.progress, 'type': type},
			complete: function(jqXHR, textStatus) {
				var json = JSON.parse(jqXHR.responseText);
				task.progress = json.progress;
				listView.refreshTask(task);
			},
			error: function(jqXHR, textStatus) {
				console.log('change progress error');
			}
		});
	},
	
	updateTabs: function(callback) {
		var newTabs = [];
		var deletedTabs = [];
		var updatedTabs = [];
		
		if(this.tabs.length == 0)
		{
			newTabs = this.tabsEdit;
		}
		else
		{
			for(var i=0; i<this.tabs.length; ++i) {
				var tabA = this.tabs[i];
				var isDeleted = true;
				
				for(var j=0; j<this.tabsEdit.length; ++j) {
					var tabB = this.tabsEdit[j];						
					
					if(tabA.id == tabB.id)
					{
						updatedTabs.push(tabB);	
						isDeleted = false;
						break;
					}
				}				
				if(isDeleted)
					deletedTabs.push(tabA.id);
			}
			
			for(var i=0; i<this.tabsEdit.length; ++i) {
				var tabB = this.tabsEdit[i];
				
				if(isNaN(tabB.id))
					newTabs.push(tabB);
			}
		}
		
		var sendChanges = function() {
			$.ajax({
				type: "POST", 
				cache: false, 
				url: '../tabs/updateTabs.do',
				data: {'newTabs': JSON.stringify(newTabs), 
					'deletedTabs': JSON.stringify(deletedTabs),
					'updatedTabs': JSON.stringify(updatedTabs)
				},
				complete: function(jqXHR, textStatus) {
					var json = JSON.parse(jqXHR.responseText);
					
					if(json.status == "OK")
					{
						callback();
					}
				},
				error: function(jqXHR, textStatus) {
					console.log('validating tabs changes error');
				}
			});
		};
		
		if(deletedTabs.length != 0)
		{
			$.ajax({
				type: "POST", 
				cache: false, 
				url: '../tabs/validateTabs.do',
				data: {'ids': JSON.stringify(deletedTabs)},
				complete: function(jqXHR, textStatus) {
					var json = JSON.parse(jqXHR.responseText);
					
					if(json.status == "OK")
					{
						sendChanges();
					}
					else
					{
						//error dialog
					}
				},
				error: function(jqXHR, textStatus) {
					console.log('validating tabs changes error');
				}
			});
		}
		else
			sendChanges();
	}
};

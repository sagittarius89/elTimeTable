var listController = {
	forceRefresh : function() {
		$('#list').mask('refreshing...');
		var orderBy = $('#sortBySelect').val();
		var tabId = tabsController.getActiveTabId();
		
		model.synchronize(orderBy, tabId, function() {
			listView.removeAll();

			for (i = 0; i < model.tasks.length; ++i) {
				listView.addTask(model.tasks[i]);
			}
			$('#list').unmask();
		});
	},

	deleteTask : function(id) {

		showConfirmDialog("Are you sure to delete this task?", function(value) {
			if (value) {
				model.deleteTask(id);
				listController.forceRefresh();
			}
		});
	},

	editTask : function(model) {
		editTaskView.editTask(model);
	},

	markDone : function(id) {
		showConfirmDialog("Are you sure to mark this task as done?", function(
				value) {
			model.markDone(id);
			listController.forceRefresh();
		});
	},

	undoDone : function(id) {
		showConfirmDialog("Are you sure to return this task as active?",
				function(value) {
					model.undoDone(id);
					listController.forceRefresh();
				});
	},

	changeProgress : function(task, type) {
		// @settings
		if (type == 'DEC' && task.progress - 10 < 0)
			return;

		// @settings
		if (type == 'INC' && task.progress + 10 >= 100) {
			showConfirmDialog(
					"If you now increment this task, it's value will be 100%. Do you want set this task as done?",
					function(value) {
						if (value) {
							model.changeProgress(task, type);
							listController.markDone(task.id);
						}
					});
		} else {
			model.changeProgress(task, type);
		}
	},

};
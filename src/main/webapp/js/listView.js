var listView = {

	initialize : function() {
		$("#addTaskButton").button({
			icons : {
				primary : "ui-icon-plus",
				width : "100px"
			}
		}).click(listView.addTaskInitial);

		$("#froceRefreshButton").button({
			icons : {
				primary : "ui-icon-arrowrefresh-1-s"
			},
			width : "70px"
		}).click(listController.forceRefresh);

		$("#sortBySelect").selectmenu({
			width : '100px'
		}).on('selectmenuchange', function() {
			listController.forceRefresh();
		});
		
		$('#sortArrow').button({
			icons : {
				primary : "ui-icon-arrowthick-1-n",
			},
			text : false
		});

		listController.forceRefresh();
	},

	addTask : function(model) {
		var newId = '';
		$('#taskTemplate').clone().css('visibility', 'visible').css('display',
				'block').attr('id', newId = 'task-' + model.id).appendTo(
				'#taskContainer');

		var taskElem = getJqTskComp(newId);
		taskElem.addClass('activeTask');

		getJqTskComp(newId, 'id').text(model.id);

		getJqTskComp(newId, 'nameLabel').val(model.name);


		if(model.status == 4)
		{
			getJqTskComp(newId, 'deleteButton').css('visibility', 'hidden');
		}
		else
		{
			getJqTskComp(newId, 'deleteButton').button({
				icons : {
					primary : "ui-icon-close",
				},
				text : false
			}).addClass('taskButton').click(function() {
				$(".ui-dialog-content").dialog("close");
				listController.deleteTask(model.id);
				return false;
			});
		}
		

		taskElem.mouseenter(function() {
			editTaskView.previewTask(model);
			editTaskView.setPosition($('#' + newId));
		});

		taskElem.mouseleave(function() {
			if (editTaskView.lock == false) {
				editTaskView.hide();
			}
		});

		taskElem.click(function() {
			editTaskView.lockPreview();
		});

		if (model.status != 1) {
			listView.addDisabledTask(model, newId, taskElem)
		} else {
			listView.addActiveTask(model, newId, taskElem);
		}
	},

	addDisabledTask : function(model, newId, taskElem) {
		taskElem.css('background-color', '#D3D3D3');
		getJqTskComp(newId, 'colorBox').css('background-color', '#D3D3D3');
		getJqTskComp(newId, 'nameLabel').addClass('crossWord');

		
		if(model.status == 4)
		{
			getJqTskComp(newId, 'editButton').css('visibility', 'hidden');
		}
		else
		{
			getJqTskComp(newId, 'editButton').button({
				icons : {
					primary : "ui-icon-pencil"
				},
				text : false,
			}).addClass('taskButton').attr('disabled', true).click(returnFalse);
		}

		getJqTskComp(newId, 'doneButton').button({
			icons : {
				primary : "ui-icon-arrowreturnthick-1-w",
			},
			text : false
		}).css('width', '28px').css('height', '28px').css('background',
				'inherit').css('border', 'none').click(function() {

			$(".ui-dialog-content").dialog("close");
			listController.undoDone(model.id);
			return false;
		});

		getJqTskComp(newId, 'progressbar').addClass('hidden');
		getJqTskComp(newId, 'decProgress').addClass('hidden');
		getJqTskComp(newId, 'incProgress').addClass('hidden');

		getJqTskComp(newId, 'prior').text(priorMap[model.priority]);
	},

	addActiveTask : function(model, newId, taskElem) {
		getJqTskComp(newId, 'colorBox').css('background-color', model.color);
		getJqTskComp(newId, 'editButton').button({
			icons : {
				primary : "ui-icon-pencil"
			},
			text : false
		}).addClass('taskButton').click(function() {
			$(".ui-dialog-content").dialog("close");
			listController.editTask(model);
			return false;
		});

		getJqTskComp(newId, 'doneButton').button({
			icons : {
				primary : "ui-icon-check",
			},
			text : false
		}).addClass('taskButton').click(function() {

			$(".ui-dialog-content").dialog("close");
			listController.markDone(model.id);
			return false;
		});

		getJqTskComp(newId, 'progressbar').progressbar({
			value : model.progress
		}).addClass('taskProgressBar');

		getJqTskComp(newId, 'incProgress').button({
			icons : {
				primary : "ui-icon-plus",
			},
			text : false
		}).addClass('changeProgressButton').click(function() {
			listController.changeProgress(model, "INC");

			return false;
		});

		getJqTskComp(newId, 'decProgress').button({
			icons : {
				primary : "ui-icon-minus",
			},
			text : false
		}).addClass('changeProgressButton').click(function() {
			listController.changeProgress(model, "DEC");

			return false;
		});

		taskElem.draggable({
			containment : '#taskContainer',
			axis : 'y'
		});

		getJqTskComp(newId, 'prior').text(priorMap[model.priority]);
	},

	refreshTask : function(model) {
		getJqTskComp('task-' + model.id, 'nameLabel').val(model.name);
		getJqTskComp('task-' + model.id + '', 'progressbar').progressbar(
				'value', parseInt(model.progress));
	},

	addTaskInitial : function() {
		editTaskView.addTask();
	},

	removeAll : function() {
		$(".activeTask").remove();
	}
}

var editTaskView = {
	lock : false,
	mode : 'preview',
	title : '',

	initialize : function() {
		$('[name=date]').datetimepicker({
			step : 15
		});
		$('input').button();
		//$('textarea').button();
		$('[name=color]').simplecolorpicker();
		$('[name=duration]').spinner();
		$('[name=exp]').spinner();
		$('#saveTask').button().click(function() {
			editTaskView.submit();

			return false;
		});
		
		$('#editTask-tabs').buttonset();
		this.setCheckedButton(tabsController.getActiveTabId());
	},

	clearForm : function() {
		$('[name=id]').val('-1');
		$('[name=taskName]').val('');
		$('[name=description]').val('');
		$('[name=date]').datetimepicker('setDate', new Date());
		$('[name=duration]').val(0);
		$('select[name="color"]').simplecolorpicker('selectColor', '#7bd148');
		$('[name=exp]').val('0');
		$('[name=prior]').val('3');
		$('[name=notes]').val('');
		$('[name=tabId]').val(tabsController.getActiveTabId());
		
		this.setCheckedButton(tabsController.getActiveTabId());
	},

	setReadOnly : function(isReadonly) {
		$('[name=taskName]').attr('readonly', isReadonly);
		$('[name=description]').attr('readonly', isReadonly);
		$('[name=date]').attr('readonly', isReadonly);
		$('[name=date]').button(isReadonly ? 'disable' : 'enable');
		$('[name=duration]').spinner(isReadonly ? "disable" : "enable");
		$('.simplecolorpicker').parent().css('visibility',
				isReadonly ? 'hidden' : 'visible');
		$('[name=exp]').spinner(isReadonly ? "disable" : "enable");

		if (isReadonly)
			$('[name=prior]').attr('disabled', isReadonly);
		else {
			$('[name=prior]').removeAttr('disabled');
			$('#prior').selectmenu('refresh');
		}
		$('[name=notes]').attr('readonly', isReadonly);
		$('#saveTask').parent().css('visibility',
				isReadonly ? 'hidden' : 'visible');
		$('#editTask-tabs').buttonset( isReadonly ? "disable" : "enable");

	},

	loadModel : function(model) {
		$('[name=id]').val(model.id);
		$('[name=taskName]').val(model.name);
		$('[name=description]').val(model.description);
		$('[name=date]').datetimepicker('setDate', model.time);
		$('[name=duration]').val(0);
		$('select[name="color"]').simplecolorpicker('selectColor', model.color);
		$('[name=exp]').val(model.exp);
		$('[name=prior]').val(model.priority);
		$('[name=notes]').val(model.notes);
		$('[name=status]').val(model.status);
		$('[name=progress]').val(model.progress);
		$('[name=tabId]').val(model.tabId);

		this.setCheckedButton(model.tabId);
	},

	hide : function() {
		$('#editTaskDialog').dialog('close');
	},

	show : function(isModal, pmtitle) {
		editTaskView.lock = isModal;

		$('#editTaskDialog').dialog({
			height : 'auto',
			width : 'auto',
			modal : isModal ? true : false,
			title: pmtitle
		});

		$('[name=unit]').selectmenu();
		$('[name=unit]').selectmenu('refresh');
		$('#prior').selectmenu();
		$('#prior').selectmenu('refresh');
	},

	submit : function() {
		var selectedUrl = null;
		if (editTaskView.mode == 'add')
			selectedUrl = '../tasks/create.do';
		else if (editTaskView.mode == 'edit')
			selectedUrl = '../tasks/edit.do';

		$.ajax({
			type : "POST",
			cache : false,
			url : selectedUrl,
			data : $('#addTaskForm').serialize(),
			complete : function(jqXHR, textStatus) {
				editTaskView.hide();
				listController.forceRefresh();
			},
			error : function(jqXHR, textStatus) {
				console.log('something went wrong');
			}
		});

		return false;
	},

	addTask : function() {
		editTaskView.clearForm();
		editTaskView.setTitle('Add task');
		editTaskView.show(true, 'Add task');
		editTaskView.resetPosition();
		editTaskView.setReadOnly(false);
		editTaskView.mode = 'add';
		$('#timeComponents').css('display', 'block');
	},

	editTask : function(model) {
		editTaskView.hide();
		editTaskView.clearForm();
		editTaskView.resetPosition();
		editTaskView.setTitle('Edit task');
		editTaskView.show(true, 'Edit task');
		editTaskView.loadModel(model);
		editTaskView.setReadOnly(false);
		editTaskView.mode = 'edit';
		$('#timeComponents').css('display', 'block');
	},

	previewTask : function(model) {
		editTaskView.clearForm();
		editTaskView.setTitle('Task preview');
		editTaskView.loadModel(model);
		editTaskView.setReadOnly(true);
		editTaskView.show(false, 'Task preview');
		editTaskView.mode = 'preview';
		
		if(isNull(model.time)) {
			$('#timeComponents').css('display', 'none');
		}
		else
		{
			$('#timeComponents').css('display', 'block');
		}
	},

	lockPreview : function() {
		editTaskView.hide();
		editTaskView.setReadOnly(true);
		editTaskView.show(true, 'Task preview');
	},

	setPosition : function(task) {
		$('#editTaskDialog').dialog("option", "position", {
			of : task,
			my : 'right center',
			at : 'left top',
			collision : 'fit'
		});
	},

	resetPosition : function() {
		$('#editTaskDialog').dialog("option", "position", {
			of : document,
			my : 'center center',
			at : 'center center',
			collision : 'fit'
		});
	},

	setTitle : function(title) {
		$('#editTaskDialog').attr('title', title);
	},
	
	setCheckedButton: function(value) {
		$('.selectedEditTab').removeAttr('checked').button('refresh');
		
		var tab = $('#editTask-tabs').children('[value=' + value + ']');
		$(tab).prop('checked', true).button('refresh');
	},
	
	setTab: function(value) {
		$('[name=tabId]').val(value);
	}
}

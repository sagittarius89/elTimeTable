var relatedTasksView = {
	mode: 'preview',
	lock: false,
	
	initialize : function() {
		$('#leftContainer').droppable({
		    tolerance: "intersect",
		    accept: ".relatedTask",
		    activeClass: "ui-state-default",
		    hoverClass: "ui-state-hover",
		    drop: function(event, ui) {        
		        $(this).append($(ui.draggable));
		    }
		});
		
		$('#rightContainer').droppable({
		    tolerance: "intersect",
		    accept: ".relatedTask",
		    activeClass: "ui-state-default",
		    hoverClass: "ui-state-hover",
		    drop: function(event, ui) {        
		        $(this).append($(ui.draggable));
		    }
		});
	},
	
	hide : function() {
		$('#relatedTasksDialog').dialog('close');
	},

	show : function(isModal, pmtitle) {
		relatedTasksView.lock = isModal;

		$('#relatedTasksDialog').dialog({
			height : 'auto',
			width : 'auto',
			modal : isModal ? true : false,
			title: pmtitle
		});
	},
	
	submit : function() {
		
	},
	
	setReadOnly : function(isReadonly) {
		
	},
	
	loadModel : function(model) {
		
	},
	
	clearForm : function() {
		$(".relatedTask").remove();
	},
	
	loadModel : function(taskModel) {
		for(var i=0; i<model.tasks.length;++i)
			this.addRelatedTask(model.tasks[i], $('#rightContainer'));
	},
	
	addRelatedTask: function(model,panel) {
		var relatedTask = $('#relatedTaskTemplate').clone();
		var newId = '';
		
		relatedTask.draggable({
		    appendTo: 'body',
		    cursor: 'move',
		    helper: 'clone',
		    revert: 'invalid',
		    start: function(e, ui)
		    {
		        $(ui.helper).css('z-index', '5000');
		    }
		});
		
		relatedTask
			.css('visibility', 'visible')
			.css('display', 'flex')
			.attr('id', newId = 'relatedTask-' + model.id)
			.addClass('relatedTask');
		
		$.fn.extend(relatedTask[0], {
			initialize: function(model) {
				$(this).find("[name=relatedTaskId]").val(model.id);
				$(this).find("[name=relatedTaskName]").text(model.name);
				$(this).find("[name=relatedTaskGuid]").text(model.guid);
				$(this).find("[name=relatedTaskDate]").text(model.creationTime);
				$(this).find("[name=relatedTaskName]").click(function() {return true;});
			}
		});
		
		relatedTask[0].initialize(model);
		$(panel).append(relatedTask);
	},
	
	edit : function(model) {
		relatedTasksView.hide();
		relatedTasksView.clearForm();
		relatedTasksView.resetPosition();
		relatedTasksView.setTitle('Edit related tasks');
		relatedTasksView.show(true, 'Edit related tasks');
		relatedTasksView.loadModel(model);
		relatedTasksView.setReadOnly(false);
		relatedTasksView.mode = 'edit';
		
		$('#rightPanel').css('display', 'block');
	},

	preview : function(model) {
		relatedTasksView.clearForm();
		relatedTasksView.setTitle('Related tasks preview');
		relatedTasksView.loadModel(model);
		relatedTasksView.setReadOnly(true);
		relatedTasksView.show(false, 'Related tasks preview');
		relatedTasksView.mode = 'preview';
		
		$('#rightPanel').css('display', 'none');
	},
	
	setPosition : function(task) {
		$('#relatedTasksDialog').dialog("option", "position", {
			of : task,
			my : 'left top',
			at : 'right top',
			collision : 'flipfit flipfit'
		});
	},
	
	resetPosition : function() {
		$('#relatedTasksDialog').dialog("option", "position", {
			of : document,
			my : 'center center',
			at : 'center center',
			collision : 'fit'
		});
	},

	setTitle : function(title) {
		$('#relatedTasksDialog').attr('title', title);
	},
};
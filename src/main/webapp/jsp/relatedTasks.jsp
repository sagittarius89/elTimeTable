<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<div id="relatedTasksDialog" style="display: none;overflow: hidden;">
	<div id="rightPanel" class="dotted" style="margin: 2px; padding: 2px; border: solid 1px; float: right; width: 350px; height: 600px">
		<div style="margin-bottom: 5px; background-color: #002147; color: #666666;text-align:center">All tasks</div>
		<div id="rightContainer" style="overflow-y: scroll;height: 580px">
		</div>
	</div>
	<div id="leftPanel" class="dotted" style="margin: 2px; padding: 2px; border: solid 1px; float: left; width: 350px; height: 600px">
		<div style="margin-bottom: 5px; background-color: #002147; color: #666666;text-align:center">Related tasks</div>
		<div id="leftContainer" style="overflow-y: scroll;height: 580px">
		</div>
	</div>
	<div id="relatedTaskTemplate" style="display: none; margin-bottom: 5px; height: 38px; border: 1px solid black; background-color: #002147; color: #666666;">
			<div style="
				flex: 0 0 auto; 
				align-self: stretch;
				display: flex;
				flex-direction: column;
			    flex-wrap: wrap;
			    justify-content: flex-end;
			    align-content: flex-end;
			    align-items: flex-end;">
			    <div class="ui-button-icon-primary ui-icon ui-icon-newwin" style="display: flex; flex: 0 0 auto; align-self: stretch"></div>
				<div class="" style="display: flex; flex: 1 0 auto; align-self: stretch"></div>
				<div class="ui-icon ui-icon-triangle-1-sw" style=" display: flex; flex: 0 0 auto; align-self: stretch"></div>
			</div>
			<input type="hidden" name="relatedTaskId">
			<div style="flex: 1 0 auto; align-self: stretch;">
				<div style="flex: 0 1 auto; align-self: flex-start;">
					<textarea name="relatedTaskName" readonly="readonly" style="
						max-height:23px;
						min-height:23px;
						background-color: inherit; 
						color: #666666; 
						border: none; 
						resize: none; 
						font-family: Arial, 'MS Sans Serif'; 
						font-size: 16px;
						width:100%;
						readonly: true">Task 1</textarea>
				</div>
				<div style="flex: 0 1 auto; align-self: flex-end;font-size: 10px">
					<label name="relatedTaskGuid"></label>|
					<label name="relatedTaskDate"></label>
				</div>
			</div>
	</div>
	<div class="addTaskFullRow">
			<button id="saveRelatedTasks">Save</button>
	</div>
</div>
<script>
	$(function() {
		relatedTasksView.initialize();
	});
</script>
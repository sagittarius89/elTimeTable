<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="pl.org.elzylab.eltimetable.beans.tabs.Tabs"%>
<%@ page import="pl.org.elzylab.eltimetable.beans.tabs.TabDTO"%>
<%@ page import="java.util.List"%>
<%@ page import="pl.org.elzylab.eltimetable.tc.Utils"%>
<%@ page import="pl.org.elzylab.eltimetable.tc.auth.UserData"%>
<%@ page import="pl.org.elzylab.eltimetable.beans.task.TaskPriority"%>

<form id="addTaskForm">
	<input type="hidden" name="id"> 
	<input type="hidden" name="status"> 
	<input type="hidden" name="progress"> 
	<input type="hidden" name="tabId">

	<div id="addTaskForm">
		<div class="addTaskLeftColumn">Name</div>
		<div class="addTaskRightColumn">
			<input type="text" name="taskName"
				class="addTaskComponentFull formWidget">
		</div>

		<div class="addTaskLeftColumn">Description</div>
		<div class="addTaskRightColumn">
			<textarea name="description" class="addTaskComponentFull formWidget"></textarea>
		</div>

		<div id="timeComponents">
			<div class="addTaskLeftColumn">Timestamp</div>
			<div class="addTaskRightColumnSmall">
				<input type="text" name="date"
					class="addTaskComponentFull formWidget" style="width: 145px;">
			</div>
			<div class="addTaskRightTitleColumn" style="width: 98px">Duration</div>

			<div class="addTaskRightColumnSmall" style="width: 224px;">
				<input type="text" name="duration" class="formWidget"
					style="width: 80px; margin-left: 6px"> <select name="unit"
					class="formWidget" style="width: 110px">
					<option value="minutes">minutes</option>
					<option value="hours">hours</option>
				</select>
			</div>

			<div class="addTaskLeftColumn">Color</div>
			<div class="addTaskRightColumn">
				<select name="color">
					<option value="#7bd148">Green</option>
					<option value="#5484ed">Bold blue</option>
					<option value="#a4bdfc">Blue</option>
					<option value="#46d6db">Turquoise</option>
					<option value="#7ae7bf">Light green</option>
					<option value="#51b749">Bold green</option>
					<option value="#fbd75b">Yellow</option>
					<option value="#ffb878">Orange</option>
					<option value="#ff887c">Red</option>
					<option value="#dc2127">Bold red</option>
					<option value="#dbadff">Purple</option>
					<option value="#e1e1e1">Gray</option>
				</select>
			</div>

			<div class="addTaskLeftColumn">Exp</div>
			<div class="addTaskRightColumnSmall">
				<input type="text" name="exp"
					class="addTaskComponentFull formWidget">
			</div>
			<div class="addTaskRightTitleColumn"
				style="width: 118px; height: 26px;">Priority</div>

			<div class="addTaskRightColumnSmall"
				style="width: 202px; height: 37px;">
				<select name="prior" id="prior" style="width: 210px;">
					<%
						for(TaskPriority priority: TaskPriority.values()) {
					%>
					<option value="<%=priority.getValue()%>"><%=priority.getDescription()%></option>
					<%
						}
					%>
				</select>
			</div>
		</div>

		<div class="addTaskLeftColumn">Tab</div>
		<div class="addTaskRightColumn">
			<div id="editTask-tabs" style="float: left">
				<input type="radio" id="editTask-All"
					name="selectedEditTab" value="ALL" checked onclick="editTaskView.setTab($(this).val())"> 
				<label for="editTask-All">All</label>
				<%
					UserData ud = Utils.getUserData(request);
							List<TabDTO> listOfTabs = Tabs.getTabsForUser(ud.getId());
							for(TabDTO tab: listOfTabs) {
				%>
				<input type="radio" id="editTask-<%=tab.getName()%>"
					name="selectedEditTab"
					value="<%=tab.getId()%>" onclick="editTaskView.setTab($(this).val())"> 
				<label for="editTask-<%=tab.getName()%>"
					onclick=""><%=tab.getName()%></label>
				<%
					}
				%>
			</div>
		</div>
		<div class="addTaskLeftColumn">Notes</div>
		<div class="addTaskRightColumn">
			<textarea name="notes" class="addTaskComponentFull formWidget"></textarea>
		</div>

		<div class="addTaskFullRow">
			<button id="saveTask">Save</button>
		</div>

		<script>
			$(function() {
				editTaskView.initialize();
			});
		</script>
	</div>
</form>
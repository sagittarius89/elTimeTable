<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<div id="listStrip" class="ui-resizable-handle ui-resizable-w stripped"
	style="z-index: 90;"></div>
<div id="listContainer">
	<div id="topMenu">
		<div style="float: left">
			<button id="addTaskButton">add task</button>
			<button id="froceRefreshButton">force refresh</button>
		</div>
		<div style="float: left">
			<div>
				<label style="font-size: 10px;">sort by:</label>
			</div>
			<select id="sortBySelect">
				<option value="PRIOR" >priority</option>
				<option value="DATE" >creation date</option>
				<!-- @TODO -->
				<!-->option>own</option-->
			</select>
			<button id="sortArrow"> </button>
		</div>
	</div>
	<div id="taskContainer">
		<div id="taskTemplate" class="task"
			style="display: none; visibility: hidden; background: #d3d3d3; background-color: rgb(68, 106, 129); height: 92px;">
			<input type="hidden" name="id" value=""> 
			<input type="hidden" name="name" value="">
			<div
				name="colorBox" style="width: 42px; float: right; background-color: rgb(0, 64, 99); height: 92px;">
				<div style="height: 28px; margin-top: 2px; margin-left: 6px;">
					<button name="doneButton"></button>
				</div>
				<div style="height: 28px; margin-top: 2px; margin-left: 6px;">
					<button name="editButton"></button>
				</div>
				<div
					style="height: 28px; margin-top: 2px; margin-left: 6px; margin-bottom: 2px;">
					<button name="deleteButton"></button>
				</div>
			</div>
			<div style="width: auto;height: 92px;float: none;overflow: hidden;">
				<div style="padding: 5px;">
					<textarea name="nameLabel" readonly="readonly"
						style="min-height: 45px; width: 100%; background-color: inherit; border: none; resize: none; font-family: Arial, 'MS Sans Serif'; font-size: 16px; font-weight: 500;"></textarea>
				</div>
				<div style="font-size: 10px;text-align: right;padding-right: 5px;">
					<label name="prior"></label>
				</div>
				<div style="overflow: hidden; display: block;">
					<div style="float: left; width: 24px; height: 24px; margin-left:5px">
						<button name="decProgress"></button>
					</div>
					<div style="height: 26px; float: none; width: auto; overflow: hidden;">
						<div style="float: right; width: 26px; height: 26px; margin-left: 5px">
							<button name="incProgress"></button>
						</div>
						<div name="progressbar"></div>
						
					</div>
				</div>
			</div>
		</div>

	</div>

	<script>
		$(function() {
			listView.initialize();
		});
	</script>

</div>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="editTabsDialog" title="Edit tabs" style="visibility:hidden;display:none">
	<div id="editTabsGrid"></div>
	<div id="editTabsOperations"><button id="editTabsAddTab">add tab</button></div>
		<div id="editTabsTabTemplate" style="display:none; margin-bottom: 5px; height: 30px; border: 1px solid black; background-color: #002147">
			<div style="float: left;height: 100%; display: flex;"><div class="ui-icon ui-icon-triangle-1-sw" style="display: flex;align-self: flex-end"></div></div>
			<input type="hidden" name="editTabsTabId">
			<div style="float: left; padding-top: 7px; padding-left: 5px;">
				<input type="text" name="editTabsNameEditor" style="display: none">
				<label name="editTabsName" style="color: #DDDDDD"></label>
			</div>
			<div style="float: right">
				<div name="editMode" style="display: none">
					<button name="editTabsAccept"></button>
					<button name="editTabsCancel"></button>
				</div>
				<div name="normalMode">
					<button name="editTabsEdit"></button>
					<button name="editTabsDelete"></button>
				</div>
			</div>
		</div>
</div>
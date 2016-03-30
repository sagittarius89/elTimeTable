<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="pl.org.elzylab.eltimetable.beans.tabs.TabDTO"%>
<%@page import="pl.org.elzylab.eltimetable.tc.auth.UserData"%>
<%@page import="pl.org.elzylab.eltimetable.tc.Utils"%>
<%@page import="pl.org.elzylab.eltimetable.beans.tabs.Tabs"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div style="float: right">
	<button id="settings"></button>
	<button id="logout"></button>
	<script>
		$('#settings').button({
			icons : {
				primary : "ui-icon-wrench"
			},
			text : false
		}).css('width', '25px').css('height', '25px').click(
				settings);
		$('#logout').button({
			icons : {
				primary : "ui-icon-key"
			},
			text : false
		}).css('width', '25px').css('height', '25px').click(logout);

		function logout() {
			window.location.href = '/eltimetable/logout';
		}

		function settings() {

		}
	</script>
</div>
<div id="tabs" style="float: left">
	<input type="radio" id="All" onclick="listController.forceRefresh()" name="selectedTab" value="ALL" checked> 
	<label for="All">All</label> 
	<input type="radio" id="Archived" onclick="listController.forceRefresh()" name="selectedTab" value="ARCHIVED"> 
	<label for="Archived" onclick="">Archived</label>
	<%
		UserData ud = Utils.getUserData(request);
		List<TabDTO> listOfTabs = Tabs.getTabsForUser(ud.getId());
		for(TabDTO tab: listOfTabs) {
	%>
			<input type="radio" id="<%=tab.getName()%>" onclick="listController.forceRefresh()" name="selectedTab" value="<%=tab.getId()%>"> 
			<label for="<%=tab.getName()%>" onclick=""><%=tab.getName()%></label>
	<%
		}
	%>
	<script>
		$(function() {
	<%
		for(TabDTO tab: listOfTabs) {
	%>
			model.tabs.push({
				id: '<%=tab.getId()%>',
				order: <%=tab.getOrder()%>,
				isDefault: <%=tab.isDefault()%>,
				name: '<%=tab.getName()%>'
			});
	<%
		}
	%>
		});
	</script>
	<input type="radio" id="Deleted" onclick="listController.forceRefresh()" name="selectedTab" value="DELETED"> 
	<label for="Deleted" onclick="">Deleted</label>
	<button id="editTabs"></button>
</div>
<jsp:include page="editTabsForm.jsp"></jsp:include>
<script>
$(function() {
	tabsView.initialize();
});
</script>
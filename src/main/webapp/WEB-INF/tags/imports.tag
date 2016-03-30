<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="jquery" required="false" type="java.lang.Boolean"%>
<%@ attribute name="jqueryUI" required="false" type="java.lang.Boolean"%>
<%@ attribute name="minimizeSrc" required="false"
	type="java.lang.Boolean"%>
<c:if test="<%=jquery%>">
	<script src="/eltimetable/js/lib/jquery.js"></script>
</c:if>
<c:if test="<%=jqueryUI%>">
	<c:choose>
		<c:when test="<%=minimizeSrc%>">
			<script src="/eltimetable/js/lib/jquery-ui.min.js"></script>
			<script src="/eltimetable/js/lib/jquery.datetimepicker.full.min.js"></script>
			<script src="/eltimetable/js/lib/jquery.loadmask.min.js"></script>
			<script src="/eltimetable/js/lib/jsgrid.min.js"></script>
			<link rel="stylesheet" type="text/css"
				href="/eltimetable/js/lib/jquery-ui.structure.min.css">
			<link rel="stylesheet" type="text/css"
				href="/eltimetable/js/lib/jquery-ui.theme.min.css">
		</c:when>
		<c:otherwise>
			<script src="/eltimetable/js/lib/jquery-ui.js"></script>
			<script src="/eltimetable/js/lib/jquery.datetimepicker.full.js"></script>
			<script src="/eltimetable/js/lib/jquery.loadmask.js"></script>
			<link rel="stylesheet" type="text/css"
				href="/eltimetable/js/lib/jquery-ui.structure.css">
			<link rel="stylesheet" type="text/css"
				href="/eltimetable/js/lib/jquery-ui.theme.css">
		</c:otherwise>
	</c:choose>


	<script src="/eltimetable/js/lib/jquery.simplecolorpicker.js"></script>
	<link rel="stylesheet" type="text/css"
		href="/eltimetable/js/lib/jquery.datetimepicker.css">
	<link rel="stylesheet" type="text/css"
		href="/eltimetable/js/lib/jquery.simplecolorpicker.css">
	<link rel="stylesheet" type="text/css"
		href="/eltimetable/js/lib/jquery.loadmask.css">
	<link rel="stylesheet" type="text/css"
		href="/eltimetable/css/style.css">

	<script src="/eltimetable/js/utils.js"></script>
	<script src="/eltimetable/js/model.js"></script>
	<script src="/eltimetable/js/listController.js"></script>
	<script src="/eltimetable/js/listView.js"></script>
	<script src="/eltimetable/js/tabsController.js"></script>
	<script src="/eltimetable/js/tabsView.js"></script>
</c:if>


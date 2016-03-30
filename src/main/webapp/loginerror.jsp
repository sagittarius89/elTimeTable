<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<html>
<head>
<tag:imports jquery="true" jqueryUI="true" minimizeSrc="false" />
<style>
body {
	margin: 0px;
	overflow: hidden;
}

#all {
	width: 100%;
	height: 100%;
}

.dotted {
	background-image: -webkit-repeating-radial-gradient(center center, rgba(0, 0, 0, .2),
		rgba(0, 0, 0, .2) 1px, transparent 1px, transparent 100%);
	background-image: -moz-repeating-radial-gradient(center center, rgba(0, 0, 0, .2),
		rgba(0, 0, 0, .2) 1px, transparent 1px, transparent 100%);
	background-image: -ms-repeating-radial-gradient(center center, rgba(0, 0, 0, .2),
		rgba(0, 0, 0, .2) 1px, transparent 1px, transparent 100%);
	background-image: repeating-radial-gradient(center center, rgba(0, 0, 0, .2),
		rgba(0, 0, 0, .2) 1px, transparent 1px, transparent 100%);
	-webkit-background-size: 3px 3px;
	-moz-background-size: 3px 3px;
	background-size: 3px 3px;
}

.ui-widget {
	font-family: Arial, MS Sans Serif;
    font-size: 12px;
}

input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px rgb(0, 33, 71) inset;
    font-family: Arial, 'MS Sans Serif';
    color: #666666
}

</style>
</head>

<body>
	<div id="all" class="dotted"></div>

	<div id="loginDialog" title="elTimeTable Log In">
			<fieldset style="border: 1px solid;">
			Log in failure. Wrong user-name or password. <br><br><br><br><br>
			<span><a href="loginform.jsp">Return to log-in page</a></span>
			</fieldset>
	</div>
	
	<script>
	$(function(){
		$('#loginDialog').dialog({
			resizable: false,
			draggable: false,
			modal: true
		});
		
		$('.ui-dialog-titlebar-close').css('visibility', 'hidden');
		
	});
	</script>
</body>
</html>

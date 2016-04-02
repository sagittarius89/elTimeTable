<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<html>
<head>
<tag:imports jquery="true" jqueryUI="true" minimizeSrc="false" />
<jsp:include page="parametrizedScripts.jsp"></jsp:include>
</head>
<body>
	<div id="all">
		<div id="mainMenu">
			<jsp:include page="mainMenu.jsp"></jsp:include>
		</div>
		<div id="calendar" class="dotted">
			<jsp:include page="taskCalendar.jsp"></jsp:include>
		</div>
		<div id="list" class="dotted">
			<jsp:include page="taskList.jsp"></jsp:include>
		</div>
	</div>
	<script>
		$(function() {
			setupListContainer();
			
			setInterval(ping, 10000);
			//setupListMenu();
		});

		var resizeLock = false;
		var resizeRatio = 3 / 10;

		function setupListContainer() {
			var width = document.body.clientWidth;

			//list container setup
			var list = $("#list");
			list.resizable({
				handles : {
					w : '#listStrip'
				},
				resize : function(event, ui) {
					var width = document.body.clientWidth;
					var size = ui.size;
					var newCalendarSize = width - size.width;

					$('#calendar').css('width', newCalendarSize + 'px');
					$('#topMenu').css('width', size.width - 7 + 'px');
				},
				start : function(event, ui) {
					resizeLock = true;
				},
				stop : function(event, ui) {
					resizeLock = false;
				},
				minWidth : 400,
				maxWidth : width / 2
			});

			$(".ui-resizable-handle").addClass("stripped");

			$(window).resize(function() {
				adjustContainers();
			});

			adjustContainers();
		}

		function adjustContainers() {
			if (resizeLock)
				return;

			var width = document.body.clientWidth;
			var height = document.body.clientHeight;
			
			$('#all').css('width', width);
			$('#all').css('height', height);
			
			var menuHeight = $('#mainMenu').outerHeight();
			height -= menuHeight;
			var topMenuHeight = $('#topMenu').outerHeight();
			
			if(width >= 1024) {
				$('#calendar').css('display', 'block');
				
				var listWidth = width * resizeRatio;
				var calendarWidth = width - listWidth;
				
				$('#calendar').css('width', calendarWidth + 'px');
				$('#calendar').css('height', height + 'px');
				$('#list').css('width', listWidth + 'px');
				$('#list').css('height', height + 'px');
				$('#listContainer').css('height', height + 'px');
				$('#taskContainer').css('height', height - topMenuHeight + 'px');
				$('#topMenu').css('width', listWidth - 7 + 'px');
			}
			else
			{
				$('#calendar').css('display', 'none');
				$('#list').css('width', width + 'px');
				$('#list').css('height', height + 'px');
				$('#taskContainer').css('height', height - topMenuHeight + 'px');
				$('#topMenu').css('width', width - 7 + 'px');
				$('#listContainer').css('width', width - 7 + 'px');
			}
		}

		
	</script>


	<div id="editTaskDialog" title="Edit task" style="display: none">
		<jsp:include page="addTaskForm.jsp"></jsp:include>
	</div>
</body>
</html>
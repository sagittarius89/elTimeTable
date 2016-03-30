function guid() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
		function(c) {
			var r = Math.random() * 16 | 0, v = c == 'x' ? r
						: r & 0x3 | 0x8;
				return v.toString(16);
			});
}

function showConfirmDialog(content, callback)
{
	$('<div></div>').appendTo('body')
    .html('<div><h6>' + content +'</h6></div>')
    .dialog({
        modal: true,
        title: 'Confirm dialog',
        autoOpen: true,
        width: 'auto',
        resizable: false,
        buttons: {
            Yes: function () {
                $(this).dialog("close");
                callback(true);
            },
            No: function () {
                $(this).dialog("close");
            }
        },
        close: function (event, ui) {
            $(this).remove();
        }
    });
}

function getJqTskComp(id, name)
{
	var str = '#' + id;
	
	if(typeof(name) != undefined && name != null)
		str +=' [name=' + name + ']';
	
	return $(str);
}

function returnFalse() {
	return false;
}

function isNull(value) {
	return typeof(value) == 'undefined' || value == null || value == '';
}

function removeFromTable(array, object) {
	var index = array.indexOf(object);
	
	if(index != -1)
		array.splice(index, 1);
}

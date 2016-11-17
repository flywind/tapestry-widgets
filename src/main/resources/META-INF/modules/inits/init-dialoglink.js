define(["jquery"],function($){
	return function(spec){
		$('#' + spec.triggerId).click(function(e) {
            $('#' + spec.dialogId).dialog('open');
        });
	}
})
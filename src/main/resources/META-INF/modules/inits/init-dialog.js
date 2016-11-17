define(["jquery","plugin/easyui"],function($,easyui){
	var dialog;
	dialog = function(spec){
		var id = spec.id;
		$('#' + id).dialog(spec.params);
		$('#' + id).find('.dialog-content').css({'display':'block'});
	}
	
	return {
		dialog:dialog
	}
})
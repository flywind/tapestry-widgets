(function(){
	define(["jquery","plugin/easyui"],function($,easyui){
		return function(spec){
			var obj = $('#'+spec.id);
			obj.removeClass('form-control').numberspinner(spec.params);
		}
	});
}).call(this);

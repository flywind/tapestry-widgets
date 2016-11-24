(function(){
	define(["plugin/datetimepicker"],function(datetimepicker){
		return function(spec){
			var obj = spec.id;
			jQuery("#"+obj).datetimepicker(spec.params);
		}
	});
}).call(this);

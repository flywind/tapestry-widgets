(function(){
	requirejs.config({
		shim: {
			'bootstrap/tags': ['jquery']
		},
		paths: {
			'bootstrap/tags': 'plugins/bootstrap-tagsinput/bootstrap-tagsinput.min'
		},
		waitSeconds: 0
	});
	
	define(['jquery','bootstrap/tags'],function($,tags){
		var init;
		init = function(spec){
			$('#'+spec.id).tagsinput(spec.params);
		};
		
		return {
			init: init
		}
	});
	
}).call(this);
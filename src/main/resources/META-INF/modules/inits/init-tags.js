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
		var tagsinput = {};
		tagsinput.tagsfield = function(spec){
			$('#'+spec.id).tagsinput(spec.params);
		};
		
		return {
			tagsfield: tagsinput.tagsfield
		}
	});
	
}).call(this);
requirejs.config({
	"shim" : {
		"ckeditor": ["jquery"],
		"config": ["jquery","ckeditor"]
	},
	"paths" : {
		"config" : "plugins/editor/ckeditor/config",
		"ckeditor" : "plugins/editor/ckeditor/ckeditor"
		
	}
});
define(["jquery","config"],function($,c){
	var init;
	init = function(id,name,params,p){
		
		window.CKEDITOR_BASEPATH = p;
		
		CKEDITOR.replace(name, params);
	}
	
	return {
		init :init
	}
})
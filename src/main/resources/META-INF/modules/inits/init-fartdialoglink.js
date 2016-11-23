(function(){
	define(["jquery","plugin/adialog"],function($,adialog){
		var init;
		
		init = function(spec){
			var id = spec.id;
			var dialogId = spec.dialog;
			$('#'+id).click(function(){
				var opts = {
				    content: document.getElementById(dialogId),
					id:dialogId
				}
				var nopts = $.extend(opts,spec.params);
				art.dialog(nopts);
			})
			
		}
		
		return {
			init:init
		}
	});
}).call(this);

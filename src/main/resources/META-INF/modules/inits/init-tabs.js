(function(){
	define(["jquery","plugin/easyui"],function($,easyui){
		var tabs;
		tabs = function(spec) {
			var obj = $("#"+spec.id),
				hover = spec.params.hover;
			var opts = {};
			if(spec.params.tabStyle){
				if(spec.params.tabStyle == "plain"){
					opts.plain = true	
				}
				if(spec.params.tabStyle == "narrow"){
					opts.narrow = true
				}
				if(spec.params.tabStyle == "pill"){
					opts.pill = true
				}
				if(spec.params.tabStyle == "justified"){
					opts.justified = true
				}
			}
			
			if(spec.params.tabPosition){
				if(spec.params.tabPosition == "top"){
					opts.tabPosition = "top"
				}
				if(spec.params.tabPosition == "bottom"){
					opts.tabPosition = "bottom"
				}
				if(spec.params.tabPosition == "left"){
					opts.tabPosition = "left"
				}
				if(spec.params.tabPosition == "right"){
					opts.tabPosition = "right"
				}
			}
			
			var newOpts = $.extend({},spec.params,opts);
			
			obj.tabs(newOpts);
			
			if(hover == true){
				var tabs = obj.tabs().tabs('tabs');
	            for(var i=0; i<tabs.length; i++){
	                tabs[i].panel('options').tab.unbind().bind('mouseenter',{index:i},function(e){
	                	obj.tabs('select', e.data.index);
	                });
	            }
			}
			
			
		};
		return{
			tabs:tabs
		}
	});
}).call(this);

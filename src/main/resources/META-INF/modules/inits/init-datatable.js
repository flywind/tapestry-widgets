define(["t5/core/pageinit", "plugin/datatable", "plugin/json"], function(pageinit) {
	return function(spec) {
		jQuery.extend(spec.params,{
    			/**
    			 * 在 ajax mode, 需要调用 Tapestry.loadScriptsInReply 在回调时执行propertyoverrides渲染在服务器端
    			 * */
    			fnDrawCallback: function( oSettings ) {
    				if(oSettings.jqXHR){
    					json = {
    						json: jQuery.evalJSON(oSettings.jqXHR.responseText)
    					};
    					
    					pageinit.handlePartialPageRenderResponse(json, function(){});
    				}
        	      }
    		});
    		
			 jQuery("#" + spec.id).dataTable(spec.params);
	  };
});
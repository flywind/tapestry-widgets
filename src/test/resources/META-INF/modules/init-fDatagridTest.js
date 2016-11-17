define(["t5/core/dom","t5/core/events","jquery","plugin/easyui","plugin/jqueryui"],function(dom,events,$,easyui,ui){
	var init;
	init = function(url){
		var ok = url;
		$('#del').click(function(){
			var row = $('#newContracts').datagrid('getSelected');
			if(!row){
				$('#noselected').dialog('open');
				return false;
			};
			var ajaxurl = url
		});
		
		
	};
	
	return{
		init:init
	}
})
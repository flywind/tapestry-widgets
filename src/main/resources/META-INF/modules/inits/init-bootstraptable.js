(function(){
	var tableLangId = document.getElementById('tableLangId'),
	langJS;
	
	if(tableLangId.value === 'zh-cn'){
		langJS = 'bootstrap-table-zh-CN.min';
	}else{
		langJS = 'bootstrap-table-en-US.min';
	};
	requirejs.config({
		shim:{
			"bootstrapTable":["jquery"],
			"bootstrapEditable":["jquery","bootstrapTable","bootstrap/popover","bootstrap/dropdown"],
			"lang":["jquery","bootstrapEditable"]
		},
		paths:{
			"lang":"plugins/bootstrap-table/locale/"+langJS,
			"bootstrapTable":"plugins/bootstrap-table/bootstrap-table.min",
			"bootstrapEditable":"plugins/x-editable/js/bootstrap-editable.min"
			
		},
		waitSeconds:0
	});
	define(["jquery","lang"],function($,l){
		var init;
		
		init = function(data){
			var id = data.id;
			var cols = {
				columns:[data.columns],
				pageList:[10, 25, 50, 100]
			};
			
			var opts = $.extend(data.params,cols);
			
			$('#' + id).bootstrapTable(opts);
		};
		
		return {
			init : init
		}
	})
}).call(this);
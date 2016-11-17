define(["jquery","t5/core/dom","t5/core/events","t5/core/zone","plugin/easyui"],function($,dom,events,zone,easyui){
	return function(spec){
		var triggerId=spec.triggerId, 
			gridId=spec.gridId,
			dialogId=spec.dialogId,
			alertMsg=spec.alertMsg,
			zoneId=spec.zoneId,
			alertTitle=spec.alertTitle;
		
		var noSelectDialog = $('#'+triggerId+'noSelected');
		
        
		$('#' + triggerId).click(function(e) {
            var row = $('#'+gridId).datagrid('getSelected');
			var rowArray = $('#'+gridId).datagrid('getSelections');
			
			if(!row){
				noSelectDialog.dialog({
					modal:true,
					width:300,
					height:105,
					title:alertTitle,
					draggable:true
				}).find('.dialog-content').css({'display':'block'}).text(alertMsg);
				return false;
		}
		$('#' + dialogId).dialog('open');
		trigger(row,rowArray);
  
        function trigger(row,rowArray){
			var url = spec.url;
			var newurl = urlConvert(url, rowArray);
			zone.deferredZoneUpdate(zoneId,newurl);
		}
		
		function urlConvert(url, rowArray){
			if(!rowArray){
				return;
			}
			var newurl = "", params = "";
			if(rowArray.length > 1){
				$.each(rowArray,function(i,v){
					params += "/"+v.id;
				});
				newurl = url+params;
			}else{
				newurl = url+"/"+row.id;
			}
			return newurl;
		}	

        });
	}
})
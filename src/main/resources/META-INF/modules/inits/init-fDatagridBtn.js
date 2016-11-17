define(["t5/core/dom","t5/core/events","t5/core/zone","plugin/easyui","jquery"],function(dom,events,zone,easyui,$){
	return function(spec){
		var obj = spec.id,
		linkType=spec.linkType,
		gridId=spec.gridId,
		zoneId=spec.zoneId,
		clientEvent=spec.clientEvent,
		alertMsg=spec.alertMsg,
		title=spec.title,
		confirmTitle=spec.confirmTitle,
		confirmMsg=spec.confirmMsg,
		submitMsg=spec.submitMsg,
		cancelMsg=spec.cancelMsg,
		closed=spec.closed;
		
		if(spec.editorMode){
			alert('editorMode')
		}
		
		var noSelectDialog = $('#'+obj+'noSelected'),
			confirmObj = $('#'+obj+'confirm');
		
		$('#'+obj).bind(clientEvent, function(e){

			var row = $('#'+gridId).datagrid('getSelected');
			var rowArray = $('#'+gridId).datagrid('getSelections');
			if(!row){
				noSelectDialog.dialog({
					modal:true,
					width:300,
					height:105,
					title:title,
					draggable:true
				}).find('.dialog-content').css({'display':'block'}).text(alertMsg);
				
				setTimeout(function(){
					noSelectDialog.dialog('close');
				},2000)
				return false;
			}

			if(linkType == "event"){
				confirmObj.dialog({
					modal:true,
					width:300,
					height:150,
					title:confirmTitle,
					draggable:true,
					buttons : [ {
	                    text : submitMsg,
	                    iconCls:'icon-ok',
	                    handler : function() {
	                    	confirmObj.dialog("close");
	                        trigger(row,rowArray);
	                        if(closed != null){
	                        	$('#'+closed).dialog("close");
	                        }
	                    }
	                }, {
	                    text : cancelMsg,
	                    handler : function() {
	                    	confirmObj.dialog("close");
	                    }
	                } ]
				}).find('.dialog-content').css({'display':'block'}).text(confirmMsg);
			}else if(linkType == "page"){
				var url = spec.url;
				var newurl = urlConvert(url, rowArray);
				window.location.href = newurl;
			}
			
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
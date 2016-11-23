(function(){
	define(["t5/core/dom","t5/core/events","t5/core/zone","plugin/adialog","jquery"],function(dom,events,zone,medialog,$){
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
			
			var noSelectDialog = $('#'+obj+'noSelected'),
				confirmObj = $('#'+obj+'confirm');
			
			$('#'+obj).bind(clientEvent, function(e){

				var row = $('#'+gridId).datagrid('getSelected');
				var rowArray = $('#'+gridId).datagrid('getSelections');
				if(!row){
					var o = {
						id:	obj+'noSelected',
						content: alertMsg,
						lock: true,
						padding: '20px',
						title:title,
						time: 3
					};
					art.dialog(o);
					return false;
				}

				if(linkType == "event"){
					art.dialog({
					    id: obj+'confirm',
					    title: confirmTitle,
					    content: confirmMsg,
					    padding: '20px',
					    lock: true,
					    button: [
					        {
					            name: submitMsg,
					            callback: function () {
					            	trigger(row,rowArray);
					                //return false;
					            },
					            focus: true
					        },
					        {
					            name: cancelMsg
					        }
					    ]
					});
					
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
	});
}).call(this);

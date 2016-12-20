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
			closed=spec.closed,
			editMsg=spec.editMsg;
			
			$('#'+obj).bind(clientEvent, function(e){

				var rows = $.map($('#'+gridId).bootstrapTable('getSelections'), function (row) {
                    return row.id;
                });
				if(rows.length < 1){
					var o = {
						id:	obj+'noSelected',
						content: alertMsg,
						lock: true,
						padding: '20px 150px',
						title:title,
						time: 3
					};
					art.dialog(o);
					return;
				}
				if(linkType == "event"){
					art.dialog({
					    id: obj+'confirm',
					    title: confirmTitle,
					    content: confirmMsg,
					    padding: '20px 150px',
					    lock: true,
					    button: [
					        {
					            name: submitMsg,
					            callback: function () {
					            	trigger(rows);
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
					if(rows.length > 1){
						var o = {
							id:	obj+'moreSelected',
							content: editMsg,
							lock: true,
							padding: '20px 150px',
							title:title,
							time: 3
						};
						art.dialog(o);
						return;
					};
					
					var url = spec.url;
					var newurl = urlConvert(url, rows);
					window.location.href = newurl;
				}
				
				function trigger(rowArray){
					var url = spec.url;
					var newurl = urlConvert(url, rowArray);
					zone.deferredZoneUpdate(zoneId,newurl);
				}
				
				function urlConvert(url, rowArray){
					if(!rowArray){
						return;
					}
					var params = "";
					$.each(rowArray,function(i,v){
						params += "/"+v;
					})
					return url+params;
					
				}	
				
			});
		}
	});
}).call(this);

define(["t5/core/dom", "t5/core/events", "jquery"],function(dom,events,$){
	return function(spec){
		var element = spec.element,zoneId = spec.zoneId,dialogId = spec.dialogId,url = spec.url;
		var $element = $('#'+element), $dialog = $('#'+dialogId);
		
		$element.click(function(e){
			var z = dom.wrap(zoneId);
			if(z){			
				z.on(events.zone.didUpdate, function() {
                    $dialog.dialog('open');
                });
                z.trigger(events.zone.refresh, {
                    url : url

                });
			}
		});
		
	};
});
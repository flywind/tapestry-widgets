(function(){
	define([ "t5/core/dom", "t5/core/zone", "t5/core/events", "plugin/adialog"], function(dom, zone, events, mdialog){
		var init = function(specs) {

	        jQuery('#' + specs.id).bind("click", function(event) {

	            event.preventDefault();
	            event.stopImmediatePropagation();

	            if (specs.useDefaultConfirm) {
	                if(confirm(specs.message)){
	                    trigger(dom.wrap(specs.id));
	                    return true;
	                }
	            } else {
	            	art.dialog({
					    id: 'dialogConfirmation',
					    title: specs.title,
					    content: specs.message,
					    padding: specs.padding,
					    lock: specs.lock,
					    button: [
					        {
					            name: specs.validationMsg,
					            callback: function () {
					            	trigger(dom.wrap(specs.id));
					            },
					            focus: true
					        },
					        {
					            name: specs.cancelMsg
					        }
					    ]
					});
	            }
	        });

	    };

	    function trigger(element) {
	        var tagName = element.$.prop("tagName");
	        switch (tagName) {
	            //如 link (pagelink, actionlink, 等...)
	            case "A":
	                var href = element.$.prop("href");
	                if (href != undefined) {
	                    if (element.attr("data-update-zone")) {
	                        //ActionLink
	                        var z = zone.findZone(element);
	                        if(z){
	                            z.trigger(events.zone.refresh, {
	                                url: element.$.prop("href")
	                            });
	                        }

	                    } else {
	                        window.location.href = href;
	                    }
	                }
	                break;
	            case "INPUT":
	                element.$.parents("form").submit();
	                break;
	            default:
	                break;
	        }
	    }
		
		return init;
		
		
	});
}).call(this);

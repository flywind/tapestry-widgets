define([ "t5/core/dom", "t5/core/zone", "t5/core/events", "plugin/easyui"], function(dom, zone, events, ui){
	var init = function(specs) {

        //使用JQuery dialog box configuration.
        if (!specs.useDefaultConfirm) {
            var dialogBox = jQuery('<div id=\'dialogConfirmationJQuery\' />').dialog({
                resizable : specs.isResizable,
                closed:true,
                height : specs.height,
                width : specs.width,
                title : specs.title,
                modal : specs.isModal,
                draggable : specs.isDraggable,
                cache:false,
                buttons : [ {
                    text : specs.validationMsg,
                    iconCls:'icon-ok',
                    handler : function() {
                        jQuery('#dialogConfirmationJQuery').dialog("close");
                        trigger(dom.wrap(specs.id));
                    }
                }, {
                    text : specs.cancelMsg,
                    handler : function() {
                    	jQuery('#dialogConfirmationJQuery').dialog("close");
                    }
                } ]
            }).find('.dialog-content').text(specs.message);
        }

        jQuery('#' + specs.id).bind("click", function(event) {

            event.preventDefault();
            event.stopImmediatePropagation();

            if (specs.useDefaultConfirm) {
                //默认 javascript confirmation box.
                if(confirm(specs.message)){
                    trigger(dom.wrap(specs.id));
                }
            } else {
            	jQuery('#dialogConfirmationJQuery').dialog('open');
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
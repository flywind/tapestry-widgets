define(["jquery","t5/core/zone"],function($,zoneManager){
	return function(spec) {
        var $element = $("#" + spec.id),
        clientEvent = spec.clientEvent,
        listenerURI = spec.listenerURI,
        zoneElementId = spec.zoneElementId,
        tableName = spec.tableName,
        field = spec.field,
        customerCode = spec.customerCode;

        if (clientEvent) {
            $element.on(clientEvent, updateZone);
        }
    
        function updateZone() {
            var listenerURIWithValue = listenerURI;
    
            if ($element.val()) {
                listenerURIWithValue = appendQueryStringParameter(listenerURIWithValue, $element.val(), tableName, field, customerCode);
            }
    
            zoneManager.deferredZoneUpdate(zoneElementId, listenerURIWithValue);
        }
    }

    function appendQueryStringParameter(url, value, tableName, field, customerCode) {
        if (url.indexOf('?') < 0) {
            url += '?'
        }
        else {
            url += '&';
        }
        url += "fieldValue" + '=' + value;
        url += "&tableName" + '=' + tableName;
        url += "&field" + '=' + field;
        url += "&customerCode" + '=' + customerCode;
        return url;
    }

})
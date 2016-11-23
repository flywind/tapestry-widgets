(function(){
	define(["t5/core/dom","t5/core/events","t5/core/zone","t5/core/forms","plugin/pager","jquery"],function(dom,events,zoneManager,formManager,pager,$){
		var fpaginate,ajaxData,ajaxRequestUrl,paramToString;
		
		fpaginate = function(spec){
			var obj = $('#'+spec.id),
			zoneId = spec.params.zoneId,
			totalPages = spec.params.totalPages,
			ajaxurl = spec.params.url,
			pageSize = spec.params.pageSize,
			size = spec.params.size,
			queryParams = spec.params.queryParams;

			var oneUrl = ajaxRequestUrl(ajaxurl,1,queryParams);
			ajaxData(zoneId,oneUrl);

			var options = {
	            currentPage: 1,
	            totalPages: totalPages,
	            numberOfPages:pageSize,
	            size: size,
	            pageUrl: function(type, page, current){
	            	return ajaxRequestUrl(ajaxurl,page,queryParams);
	            },
	            onPageClicked:function(e,originalEvent,type,page){
	                originalEvent.preventDefault();
	                originalEvent.stopPropagation();

	                var target = originalEvent.currentTarget;

	                var url = $(target).attr("href");
	                ajaxData(zoneId,url);
	            }
	        }
			
			obj.bootstrapPaginator(options);
			
		}
		ajaxRequestUrl = function(ajaxurl,page,queryParams){
			var requestUrl = "";
			var params = {};
			if(queryParams){
				var p = queryParams.split(',');
				for(var i=0;i<p.length;i++){
					var item = p[i];
					var itemObj = $('#'+item).serializeObject()
					$.extend(params,itemObj);
				}
			}
			var pStr = paramToString(params);

	    	if(ajaxurl.lastIndexOf(";")>0){
	    		requestUrl = ajaxurl.substr(0,ajaxurl.lastIndexOf(";"))+"?pageNumber="+page+pStr;
	    	}else{
	    		requestUrl = ajaxurl+"?pageNumber="+page+pStr;
	    	}
	    	
	    	return requestUrl;
		}
		
		ajaxData = function(zoneId,ajaxurl){
			zoneManager.deferredZoneUpdate(zoneId, ajaxurl);
			
		}
		
		paramToString = function (param) {
		    if (!param) {
		        return '';
		    }

		    var str = '';
		    $.each(
		        param,
		        function (k, v) {
		            str += '&' + k + '=' + v;
		        }
		    )

		    return str;
		};
		
		$.prototype.serializeObject=function(){  
		    var obj=new Object();  
		    $.each(this.serializeArray(),function(index,param){  
		        if(!(param.name in obj)){  
		            obj[param.name]=param.value;  
		        }  
		    });  
		    return obj;  
		};  
		
		function appendQueryStringParameter(url, name, value) {
	        if (url.indexOf('?') < 0) {
	            url += '?'
	        }
	        else {
	            url += '&';
	        }
	        value = escape(value);
	        url += name + '=' + value;
	        return url;
	    }
		
		return {
			fpaginate :fpaginate
		}
	});
}).call(this);

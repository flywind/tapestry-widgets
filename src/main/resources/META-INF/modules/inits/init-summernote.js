(function(){
	var summernoteLangId = document.getElementById('summernoteLangId'),
	langJS;
	
	if(summernoteLangId.value === 'zh-cn'){
		langJS = 'summernote-zh-CN.min';
	}else{
		langJS = 'summernote-en-US';
	};
	requirejs.config({
		shim:{
			"summernote":["jquery","bootstrap/tooltip","bootstrap/modal","bootstrap/dropdown"],
			"lang":["jquery","summernote"]
		},
		paths:{
			"lang":"plugins/summernote/lang/"+langJS,
			"summernote":"plugins/summernote/summernote.min"
		},
		waitSeconds:0
	});
	
	define(["jquery","lang"],function($,l){
		var init,objId;
		
		init = function(data){
			var uploadToServer = data.uploadToServer,
				url = data.url,
				objId = data.id;
			var newParams = {};
			if(summernoteLangId.value === 'zh-cn'){
				data.params.lang = "zh-CN";	
			}
			   
			if(uploadToServer){
				newParams = {
					callbacks: {  
			            onImageUpload: function(files) {
			            	
			            	var fd = new FormData();
			            	fd.append('filedata',files[0]);
			
			                var opts = {
			                		url:data.url,
			                		type : 'POST',
			                		data : fd,
			                		cache: false,
			                		processData:false,
			                		contentType:false,
			                		beforeSend:function(request){
			                			request.setRequestHeader("S-File-Name",files[0].name);
			                			request.setRequestHeader("S-File-Type",files[0].type);
			                		},
					                success : function(data) {
					                	if(!(data.err) || data.err == ""){
					                		$('#'+objId).summernote('insertImage',data.url,'img');
					                	}else{
					                		alert(data.err);
					                	}
					                	
					                }	
			                }
			                $.ajax(opts);
			                //dom.ajaxRequest(data.url,{});
			            }
			        }  
				}
			}
			
			var endParams = $.extend(data.params,newParams)
			
			$('#'+data.id).summernote(data.params);
		};
		
		
		return {
			init:init
		}
	});
	
}).call(this);
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
		var init,objId,createModal;
		
		init = function(data){
			var uploadToServer = data.uploadToServer,
				url = data.url,
				objId = data.id,
				modalImageTitle = data.modalImageTitle,
				modalImageContent = data.modalImageContent;
			var newParams = {};
			if(summernoteLangId.value === 'zh-cn'){
				data.params.lang = "zh-CN";	
			}
			if(data.params.tools == "full"){
				data.params.toolbar=[
     			    ['codeview', ['codeview']],             
     			    ['font', ['bold', 'italic', 'fontname', 'fontsize', 'strikethrough', 'underline']], 
     			    ['font', ['superscript', 'subscript', 'clear']],
     			    ['color', ['color', 'undo', 'redo']], 
     			    ['insert', ['picture', 'video', 'link', 'table', 'hr']],
     			    ['style', ['style', 'height']],         
     			    ['style', ['ol', 'ul', 'paragraph']],
     			    ['misc', ['fullscreen','help']]
     			];
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
					                		var e = modalImageContent + " "+data.err;
					                		createModal(objId,modalImageTitle,e);
					                		$('#'+objId+'Modal').modal('show');
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
			
			$('#'+data.id).summernote(endParams);
		};
		
		createModal = function(id,title,content){
			var modalId = id+'Modal';
			var html =  "";
			html += '<div class="modal fade" id="'+modalId+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
			html += '<div class="modal-dialog"><div class="modal-content">';
			html += '<div class="modal-header">';
			html += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
			html += '<h4 class="modal-title" id="myModalLabel">'+title+'</h4>';
			html += '</div>';
			html += '<div class="modal-body">'+content+'</div>'
			html += '</div></div>';
			html += '</div>';
			
			$('body').append(html);
		}
		
		
		return {
			init:init
		}
	});
	
}).call(this);
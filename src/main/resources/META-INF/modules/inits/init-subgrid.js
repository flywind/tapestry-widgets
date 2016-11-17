define(["jquery","plugin/easyui","plugin/griddetailview","t5/core/dom","t5/core/events"],function($,easyui,griddetailview,dom,events){
	var init,cutHeight,fixed,toggleMenu;
	init = function(spec){
		var obj = $("#"+spec.id),
			subUrl = spec.params.subUrl,
			loadMsg = spec.params.loadMsg,
			subLoadMsg = spec.params.subLoadMsg,
			emptyMsg = spec.params.emptyMsg,
			subEmptyMsg = spec.params.subEmptyMsg,
			subColumns =spec.subColumns;
		
		if(spec.params.language == "zh-CN"){
			if ($.fn.pagination){
				$.fn.pagination.defaults.beforePageText = '第';
				$.fn.pagination.defaults.afterPageText = '共{pages}页';
				$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
			}
		}
		
		var config = {
			view: detailview,
			loadMsg:loadMsg,
            emptyMsg:emptyMsg,
			detailFormatter:function(index,row){
                return '<div style="padding:2px"><table class="ddv"></table></div>';
            },
            onExpandRow: function(index,row){
            	var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
                ddv.datagrid({
                    url:subUrl+'/'+row.id,
                    fitColumns:true,
                    singleSelect:true,
                    rownumbers:true,
                    loadMsg:subLoadMsg,
                    emptyMsg:subEmptyMsg,
                    height:'auto',
                    onClickRow: function (rowIndex, rowData) {
                        $(this).datagrid('unselectRow', rowIndex);
                    },
                    columns:[subColumns],
                    onResize:function(){
                    	obj.datagrid('fixDetailRowHeight',index);
                    },
                    onLoadSuccess:function(row){
                    	var i = $(this).datagrid('getData').total;
                    	if(i == 0){
                    		var opts = $(this).datagrid('options');
                            var vc = $(this).datagrid('getPanel').children('div.datagrid-view').css({'height':'65px'});
                            
                            var e = vc.children('div.datagrid-view2').find('div.datagrid-body').css({'height':'50px'}).empty();
                            var d = $('<div style="padding:3px 10px;"></div>').html(opts.emptyMsg).prependTo(e);
                            d.css({
                                width:'100%',
                                textAlign:'left'
                            });
  
                            $(this).datagrid('options').height=65;
                    		obj.datagrid('fixDetailRowHeight',index);
                    		return false;
                    	}
                    	
                        setTimeout(function(){
                        	obj.datagrid('fixDetailRowHeight',index);
                        },0);
                    }
                   
                });
                obj.datagrid('fixDetailRowHeight',index);      	
                
            }
		}
		
		
		var newOpts = $.extend({},spec.params,config)
		
		var fixLayout = spec.params.fixLayout;
		
		if(fixLayout == false){
			obj.datagrid(newOpts);
			
			$(window).resize(function(){
				if(spec.params.width == "auto"){
					var reswidth = "auto";
					$(obj).datagrid('resize',{
						width:reswidth
					})
				}
			})
		}else{
			fixed(obj,newOpts,spec.params.cutWidth);
			toggleMenu(obj,newOpts,spec.params.cutWidth);
		}

			
	}
	
	cutHeight = function(layoutW){
		var obj = $('#selectCondition');
		if(obj.size() < 0){
			return;
		}
		var selectH = obj.height();
		return 168+selectH;
	};
	
	fixed = function(obj,params,cutWidth,layoutW){
		var cutH = cutHeight();
		var lw = layoutW || 0;
		var h = $(window).height()-cutH;
		var w = $(window).width()-cutWidth+lw;
		var gridCotent = $('#gridContent');
		if(gridCotent.size() == 0){
			alert('datagrid没有父级id=gridContent的div,无法初始化datagrid的高度!');
			return;
		}
		gridCotent.css({
			'height':h+'px',
			'width': w+'px'
		});
		$(obj).datagrid(params);
		$(obj).datagrid('options').width="auto";
		$(obj).datagrid('options').height=h;
		$(obj).datagrid('reload');
		
		$(window).resize(function(){
			h = $(window).height()-cutH;
			var w = $(window).width()-cutWidth+lw;
			gridCotent.css({
				'height':h+'px',
				'width': w+'px'
			});
			$(obj).datagrid("resize",{
				width:'auto',
				height:h
			});
		});
	};
	
	toggleMenu = function(obj,params,cutWidth,layoutW){
		var memuBtn = $("[data-toggle='offcanvas']");
		if(memuBtn.size() == 0){
			//alert("没有找到属性[data-toggle='offcanvas']的html元素!");
			return;
		}
		memuBtn.click(function(){
			if ($(window).width() > 767) {
				if($("body").is('.sidebar-collapse')){
					fixed(obj,params,cutWidth,layoutW);
				}else{
					fixed(obj,params,cutWidth,180);
				}
				$("body").toggleClass('sidebar-collapse');
			}
		})	
	}
	
	return{
		init:init
	}
})